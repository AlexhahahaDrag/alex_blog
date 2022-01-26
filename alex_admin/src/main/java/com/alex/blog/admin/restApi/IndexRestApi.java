package com.alex.blog.admin.restApi;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.admin.AdminService;
import com.alex.blog.xo.service.blog.BlogService;
import com.alex.blog.xo.service.blog.CommentService;
import com.alex.blog.xo.service.sys.WebVisitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *description:  首页相关接口
 *author:       alex
 *createDate:   2022/1/26 10:34
 *version:      1.0.0
 */
@RestController
@Api(value = "首页相关接口", tags = {"首页相关接口"})
@RequestMapping(value = "/index")
public class IndexRestApi {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private WebVisitService webVisitService;

    @ApiOperation(value = "首页初始化数据", notes = "首页初始化数据", response = String.class)
    @GetMapping(value = "/init")
    public String init() {
        Map<String, Object> map = new HashMap<>(Constants.NUM_FOUR);
        map.put(SysConf.BLOG_COUNT, blogService.getBlogCount(EStatus.ENABLE.getCode()));
        map.put(SysConf.COMMENT_COUNT, commentService.getCommentCount(EStatus.ENABLE.getCode()));
        map.put(SysConf.USER_COUNT, adminService.getUserCount(EStatus.ENABLE.getCode()));
        map.put(SysConf.VISIT_COUNT, webVisitService.getWebVisitCount());
        return ResultUtil.resultSuccessWithData(map);
    }
}
