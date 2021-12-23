package com.alex.blog.picture.security;

import com.alex.blog.common.config.jwt.Audience;
import com.alex.blog.common.config.jwt.JwtTokenUtil;
import com.alex.blog.utils.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器 【验证token有效性】
 *
 * @author 陌溪
 * @date 2020年9月19日10:05:40
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private Audience audience;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @Value(value = "${tokenHead}")
//    private String tokenHead;
//
//    @Value(value = "${tokenHeader}")
//    private String tokenHeader;
//
//    /**
//     * token过期的时间
//     */
//    @Value(value = "${audience.expiresSecond}")
//    private Long expiresSecond;
//
//    /**
//     * token刷新的时间
//     */
//    @Value(value = "${audience.refreshSecond}")
//    private Long refreshSecond;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

//        //得到请求头信息authorization信息
//        String authHeader = request.getHeader(tokenHeader);
////
////        //TODO 判断是否触发 mogu-picture发送的请求【图片上传鉴权，需要用户登录，携带token请求admin，后期考虑加入OAuth服务统一鉴权】
////        final String pictureToken = request.getHeader("pictureToken");
//////        if (StringUtils.isNotEmpty(pictureToken)) {
//////            authHeader = pictureToken;
//////        }
////
//        //请求头 'Authorization': tokenHead + token
//        if (authHeader != null && authHeader.startsWith(tokenHead)) {
//            log.error("传递过来的token为: {}", authHeader);
//            final String token = authHeader.substring(tokenHead.length());
//            // 私钥
//            String base64Secret = audience.getBase64Secret();
//            // 获取在线的管理员信息
//            String onlineAdmin = redisUtils.get(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + authHeader);
//            if (StringUtils.isNotEmpty(onlineAdmin) && !jwtTokenUtil.isExpiration(token, base64Secret)) {
//                //得到过期时间
//                Date expiration = jwtTokenUtil.getExpiration(token, base64Secret);
//                Instant instant = expiration.toInstant();
//                ZoneId zoneId = ZoneId.systemDefault();
//                LocalDateTime expirationDate = LocalDateTime.ofInstant(instant, zoneId);
//                LocalDateTime nowDate = LocalDateTime.now();
//                // 得到两个日期相差的间隔，秒
//                long survivalSecond = DateUtils.diffSecondByTwoDays(nowDate, expirationDate);
//                // 当存活时间小于更新时间，那么将颁发新的Token到客户端，同时重置新的过期时间
//                // 而旧的Token将会在不久之后从Redis中过期
//                if (survivalSecond < refreshSecond) {
//                    // 生成一个新的Token
////                    String newToken = tokenHead + jwtTokenUtil.refreshToken(token, base64Secret, expiresSecond * 1000);
////                    // 生成新的token，发送到客户端
//////                    CookieUtils.setCookie("Admin-Token", newToken, expiresSecond.intValue());
////                    OnlineAdmin newOnlineAdmin = JsonUtils.jsonToPojo(onlineAdmin, OnlineAdmin.class);
////                    // 获取旧的TokenUid
////                    String oldTokenUid = newOnlineAdmin.getTokenId();
////                    // 随机生成一个TokenUid，用于换取Token令牌
////                    String tokenUid = StringUtils.getUUID();
////                    newOnlineAdmin.setTokenId(tokenUid);
////                    newOnlineAdmin.setToken(newToken);
////                    newOnlineAdmin.setExpireTime(DateUtils.addTime(LocalDateTime.now(), expiresSecond, ChronoUnit.SECONDS));
////                    newOnlineAdmin.setLoginTime(DateUtils.getNowDate());
////                    // 移除原来的旧Token和TokenUid
////                    redisUtils.delete(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + authHeader);
////                    redisUtils.delete(RedisConf.LOGIN_ID_KEY + RedisConf.SEGMENTATION + oldTokenUid);
////                    // 将新token赋值，用于后续使用
////                    authHeader = newToken;
////
////                    // 将新的Token存入Redis中
////                    redisUtils.setEx(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + newToken, JsonUtils.objectToJson(newOnlineAdmin), expiresSecond, TimeUnit.SECONDS);
////                    // 维护 uuid - token 互相转换的Redis集合【主要用于在线用户管理】
////                    redisUtils.setEx(RedisConf.LOGIN_ID_KEY + RedisConf.SEGMENTATION + tokenUid, newToken, expiresSecond, TimeUnit.SECONDS);
//                }
//            } else {
//                chain.doFilter(request, response);
//                return;
//            }
//
//            String username = jwtTokenUtil.getUsername(token, base64Secret);
//            Integer adminId = jwtTokenUtil.getUserId(token, base64Secret);
//            //把adminUid存储到request中
//            request.setAttribute(SysConf.ADMIN_ID, adminId);
//            request.setAttribute(SysConf.USERNAME, username);
//            request.setAttribute(SysConf.TOKEN, authHeader);
//            log.info("解析出来用户: {}", username);
//            log.info("解析出来的用户Uid: {}", adminId);
//
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                // 通过用户名加载SpringSecurity用户
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                // 校验Token的有效性
//                if (jwtTokenUtil.validateToken(token, userDetails, base64Secret)) {
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
//                            request));
//                    //以后可以security中取得SecurityUser信息
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
        chain.doFilter(request, response);
    }
}
		

