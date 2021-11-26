package com.alex.blog.xo.service.admin.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.admin.Admin;
import com.alex.blog.common.entity.admin.Role;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SQLConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.admin.AdminVo;
import com.alex.blog.utils.utils.*;
import com.alex.blog.xo.entity.OnlineAdmin;
import com.alex.blog.xo.mapper.admin.AdminMapper;
import com.alex.blog.xo.service.admin.AdminService;
import com.alex.blog.xo.service.admin.RoleService;
import com.alex.blog.xo.utils.WebUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *description:  管理员service实现类
 *author:       alex
 *createDate:   2021/7/11 20:13
 *version:      1.0.0
 */
@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private AdminService adminService;

    @Autowired
    private WebUtils webUtils;

    @Autowired
    private RoleService roleService;

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.getAdminById(id);
    }

    /**
     * @param adminVo
     * @description:  获取在线用户列表
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String getOnlineAdminList(AdminVo adminVo) {
        //获取redis中匹配的所有key
        Set<String> keys = redisUtils.keys(RedisConf.LOGIN_TOKEN_KEY + "*");
        List<String> onlineAdminJsonList = redisUtils.muliGet(keys);
        int pageSize = adminVo.getPageSize().intValue();
        int currentPage = adminVo.getCurrentPage().intValue();
        int total = onlineAdminJsonList.size();
        int start = Math.max((currentPage - 1) * pageSize, 0);
        int end = Math.min(currentPage * pageSize, total);
        List<String> onlineAdminSubList = onlineAdminJsonList.subList(start, end);
        List<OnlineAdmin> onlineAdminList = onlineAdminSubList.stream().map(item -> {
            OnlineAdmin onlineAdmin = JsonUtils.jsonToPojo(item, OnlineAdmin.class);
            onlineAdmin.setToken("");
            return onlineAdmin;
        }).collect(Collectors.toList());
        Page<OnlineAdmin> page = new Page<>();
        page.setCurrent(currentPage);
        page.setTotal(total);
        page.setSize(pageSize);
        page.setRecords(onlineAdminList);
        return ResultUtil.resultWithData(SysConf.SUCCESS, page);
    }

    @Override
    public Admin getAdminByUser(String username) {
        QueryWrapper<Admin> query = new QueryWrapper<>();
        query.eq(SysConf.USERNAME, username);
        query.last(SysConf.LIMIT_ONE);
        Admin admin = adminService.getOne(query);
        //清空密码，防止密码泄露
        admin.setPassword(null);
        // TODO: 2021/7/14 获取图片信息
        if (StringUtils.isNotEmpty(admin.getAvatar())) {

        }
        Admin res = new Admin();
        res.setOccupation(admin.getOccupation());
        res.setNickName(admin.getNickName());
        res.setPersonResume(admin.getPersonResume());
        res.setAvatar(admin.getAvatar());
        res.setSummary(admin.getSummary());
        res.setPhotoList(admin.getPhotoList());
        return res;
    }

    /**
     * @description:  通过request对象获取当前登录管理员信息
     * @author:       alex
     * @return:       com.alex.blog.common.entity.admin.Admin
     */
    @Override
    public Admin getMe() {
        String adminId = RequestHolder.getAdminId();
        if (StringUtils.isEmpty(adminId)) {
            return new Admin();
        }
        Admin admin = adminService.getById(adminId);
        admin.setPassword("");
        // TODO: 2021/7/15获取图片
        if (StringUtils.isNotEmpty(admin.getAvatar())) {

        }
        return admin;
    }

    @Override
    public void addOnLineAdmin(Admin admin, Long expirationSecond) {
        HttpServletRequest request = RequestHolder.getRequest();
        if (request != null) {
            Map<String, String> map = IpUtils.getOsAndBrowserInfo(request);
            String os = map.get(SysConf.OS);
            String browser = map.get(SysConf.BROWSER);
            String ip = map.get(SysConf.IP);
            //设置在线管理员信息
            OnlineAdmin onlineAdmin = new OnlineAdmin();
            onlineAdmin.setAdminId(admin.getId());
            onlineAdmin.setTokenId(admin.getTokenId());
            onlineAdmin.setToken(admin.getValidCode());
            onlineAdmin.setOs(os);
            onlineAdmin.setBrowser(browser);
            onlineAdmin.setIpAddr(ip);
            onlineAdmin.setLoginTime(DateUtils.getNowDate());
            //onlineAdmin.setRoleName(admin.getRole().getRoleName());
            onlineAdmin.setUsername(admin.getUsername());
            onlineAdmin.setExpireTime(DateUtils.addTime(LocalDateTime.now(), expirationSecond, ChronoUnit.MILLIS));
            //从redis中获取ip来源
            String jsonResult = redisUtils.get(RedisConf.IP_SOURCE + RedisConf.SEGMENTATION + ip);
            if (StringUtils.isEmpty(jsonResult)) {
                String addresses = IpUtils.getAddresses(SysConf.IP + RedisConf.EQUAL_TO + ip, SysConf.UTF_8);
                if (StringUtils.isNotEmpty(addresses)) {
                    jsonResult = addresses;
                    redisUtils.setEx(RedisConf.IP_SOURCE + RedisConf.SEGMENTATION + ip, addresses, 24, TimeUnit.HOURS);
                }
            }
            onlineAdmin.setLoginLocation(jsonResult);
            //将登陆的管理员储存到在线用户列表中
            redisUtils.setEx(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + admin.getValidCode(), JsonUtils.objectToJson(onlineAdmin), expirationSecond, TimeUnit.MINUTES);
            //在维护一张用于tokenid - toekn转化的表
            redisUtils.setEx(RedisConf.LOGIN_ID_KEY + RedisConf.SEGMENTATION + admin.getTokenId(), admin.getValidCode(), expirationSecond, TimeUnit.MINUTES);
        }
    }

    @Override
    public String getList(AdminVo adminVo) {
        QueryWrapper<Admin> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(adminVo.getKeyword())) {
            query.like(SQLConf.USERNAME, adminVo.getKeyword()).or().like(SQLConf.USER_EMAIL, adminVo.getKeyword()).or().like(SQLConf.MOBILE, adminVo.getKeyword());
        }
        Page<Admin> page = new Page<>();
        page.setCurrent(adminVo.getCurrentPage());
        page.setSize(adminVo.getPageSize());
        //去除密码
        query.select(Admin.class, i -> !i.getProperty().equals(SQLConf.PASSWORD));
        query.eq(SQLConf.STATUS, EStatus.ENABLE);
        IPage<Admin> pageList = adminService.page(page, query);
        StringBuffer fileIds = new StringBuffer();
        List<Admin> list = pageList.getRecords();
        List<Long> adminIdList = list.stream()
                .map(admin -> {
                    if (StringUtils.isNotEmpty(admin.getAvatar())) {
                        fileIds.append(admin.getAvatar()).append(SysConf.FILE_SEGMENTATION);
                    }
                    return admin.getId();
                }).collect(Collectors.toList());
        Map<String, String> pictureMap = new HashMap<>(Constants.NUM_TEN);
        //获取图片信息
        String pictureResult = null;
        // TODO: 2021/9/17  
//        if (fileIds.length() > 0) {
//            pictureResult = pictureFeignClient.getPicture(fileIds.toString());
//        }
        return ResultUtil.resultWithData(SysConf.SUCCESS, pageList);
    }

    /**
     * @param adminVo
     * @description:  创建管理员
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String addAdmin(AdminVo adminVo) {
        String username = adminVo.getUsername();
        String mobile = adminVo.getMobile();
        String email = adminVo.getEmail();
        if (StringUtils.isEmpty(username)) {
            return ResultUtil.result(SysConf.ERROR, "用户名必填");
        }
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)) {
            return ResultUtil.result(SysConf.ERROR, "邮箱和手机号至少有一项不能为空!");
        }
        // TODO: 2021/9/17 添加手机号和邮箱验证
        // TODO: 2021/9/5 默认配置信息
        String defaultPassword = "1234@com";
        QueryWrapper<Admin> query = new QueryWrapper<>();
        query.eq(SysConf.USERNAME, username);
        Admin one = adminService.getOne(query);
        if (one != null) {
            return ResultUtil.result(SysConf.ERROR, "用户名称已经存在！！");
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminVo, admin);
        admin.setStatus(EStatus.ENABLE.getCode());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(defaultPassword));
        adminService.save(admin);
        // TODO: 2021/9/6 通过sms模块发送给邮件
        // TODO: 2021/9/6 申请网盘储存空间
        return ResultUtil.result(SysConf.SUCCESS, "创建管理员成功!!");
    }

    /**
     * @param adminVo
     * @description:  修改管理员信息
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String editAdmin(AdminVo adminVo) {
        QueryWrapper<Admin> query = new QueryWrapper<>();
        query.eq(SQLConf.USERNAME, adminVo.getUsername());
        query.eq(SQLConf.STATUS, EStatus.ENABLE.getCode());
        List<Admin> adminList = adminService.list(query);
        //判断用户名是否存在
        if (adminList != null && (adminList.size() > 1 || !adminList.get(0).getId().equals(adminVo.getId()))) {
            return ResultUtil.result(SysConf.ERROR, "修改失败，用户名已存在！");
        }
        Admin admin = adminService.getById(adminVo.getId());
        if (adminVo.getRoleId() != null && !adminVo.getRoleId().equals(admin.getRoleId())) {
            redisUtils.delete(RedisConf.ADMIN_VISIT_MENU + RedisConf.SEGMENTATION + admin.getId());
        }
        BeanUtils.copyProperties(adminVo, admin);
        //不直接修改密码
        admin.setPassword(null);
        admin.updateById();
        // TODO: 2021/9/10 判断是否调整网盘大小
        return ResultUtil.result(SysConf.SUCCESS, "修改管理员成功!!");
    }

    /**
     * @param adminVo
     * @description:  修改自己
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String editMe(AdminVo adminVo) {
        String adminId = RequestHolder.getAdminId();
        if (StringUtils.isEmpty(adminId)) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.INVALID_TOKEN);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminVo, admin, SysConf.STATUS);
        admin.setPassword(null);
        admin.updateById();
        return ResultUtil.result(SysConf.SUCCESS, "修改自己成功!");
    }

    /**
     * @param oldPwd  旧密码
     * @param newPwd  新密码
     * @description:  修改密码
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String changePwd(String oldPwd, String newPwd) {
        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
            return ResultUtil.result(SysConf.ERROR, "密码不能为空！");
        }
        String adminId = RequestHolder.getAdminId();
        Admin admin = adminService.getById(adminId);
        //判断密码
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean judge = encoder.matches(oldPwd, admin.getPassword());
        if (!judge) {
            return ResultUtil.result(SysConf.ERROR, "密码输入错误!");
        }
        admin.setPassword(encoder.encode(newPwd));
        admin.updateById();
        return ResultUtil.result(SysConf.SUCCESS, "修改密码成功!");
    }

    /**
     * @param adminVo
     * @description:  重置密码
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String resetPwd(AdminVo adminVo) {
        // TODO: 2021/9/13 获取初始密码
        String defaultPassword = "1234@com";
        String adminId = RequestHolder.getAdminId();
        Admin admin = adminService.getById(adminId);
        // TODO: 2021/9/13 上帝可以修改一切，超级管理员不可以修改上帝的密码
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(defaultPassword));
        admin.updateById();
        return ResultUtil.result(SysConf.SUCCESS, "重置密码成功!");
    }

    /**
     * @param ids
     * @description:  批量删除管理员
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String deleteBatchAdmin(List<Long> ids) {
        boolean res = StringUtils.checkIdList(ids);
        if (!res) {
            return ResultUtil.result(SysConf.ERROR, "id为空!");
        }
        // TODO: 2021/9/13 上帝管理员不能被删除
        List<Admin> adminList = ids.stream().map(id -> {
            Admin admin = new Admin();
            admin.setId(id);
            admin.setStatus(EStatus.DISABLED.getCode());
            return admin;
        }).collect(Collectors.toList());
        adminService.updateBatchById(adminList);
        return ResultUtil.result(SysConf.SUCCESS, "批量删除成功!");
    }

    /**
     * @param tokenIdList
     * @description:  强制登出
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String forceLogout(List<String> tokenIdList) {
        if (tokenIdList == null || tokenIdList.size() == 0) {
            return ResultUtil.result(SysConf.ERROR, "token不能为空！");
        }
        // TODO: 2021/9/13 上帝管理员不能被强制登出
        String keyPrefix = RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION;
        List<String> keyList = tokenIdList.stream().map(idToken -> {
            String token = redisUtils.get(RedisConf.LOGIN_ID_KEY + RedisConf.SEGMENTATION + idToken);
            return keyPrefix + (token == null ? "" : token);
        }).collect(Collectors.toList());
        redisUtils.delete(keyList);
        return ResultUtil.result(SysConf.SUCCESS, "强制登出成功！");
    }

    /**
     * @param token
     * @description:  根据token获取信息
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String info(String token) {
        String adminId = RequestHolder.getAdminId();
        if (StringUtils.isEmpty(adminId)) {
            // TODO: 2021/9/22 修改提示信息 
            return ResultUtil.result(SysConf.ERROR, "token用户过期!");
        }
        Map<String, Object> map = new HashMap<>(Constants.NUM_THREE);
        map.put(SysConf.TOKEN, token);
        Admin admin = adminService.getById(adminId);
        //获取头像
        // TODO: 2021/9/22 获取头像图片数据
        if(StringUtils.isNotEmpty(admin.getAvatar())) {

        }
        Role role = roleService.getById(admin.getRoleId());
        map.put(SysConf.ROLES, new ArrayList<>().add(role));
        return ResultUtil.result(SysConf.SUCCESS, map);
    }

    @Override
    public String getMenu() {
        String adminId = RequestHolder.getAdminId();
        if(StringUtils.isEmpty(adminId)) {
            return ResultUtil.result(SysConf.ERROR, "用户已过期!");
        }
        Admin admin = adminService.getById(adminId);
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add(admin.getRoleId());
        List<Role> roleList = roleService.listByIds(roleIdList);
        // TODO: 2021/9/25 添加菜单
        List<String> menuIdList = new ArrayList<>();
        roleList.forEach(role -> {
            String[] menuIds = role.getCategoryMenuIds().split(",");
            menuIdList.addAll(Arrays.asList(menuIds));
        });
        return null;
    }
}