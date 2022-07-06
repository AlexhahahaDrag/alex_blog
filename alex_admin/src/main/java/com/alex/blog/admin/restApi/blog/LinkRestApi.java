package com.alex.blog.admin.restApi.blog;


import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.blog.LinkVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.blog.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 博客友情链接表 前端控制器
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:28
 */
@RestController
@RequestMapping("/link")
@Api(value = "友情链接相关接口", tags = {"友情链接相关接口"})
public class LinkRestApi {

    @Autowired
    private LinkService linkService;

    @AuthorityVerify
    @ApiOperation(value = "获取友情链接列表", notes = "获取友情链接列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody LinkVo linkVo) {
        return ResultUtil.resultSuccessWithData(linkService.getPageList(linkVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增友情链接", notes = "新增友情链接", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增友情链接")
    public String addLink(@Validated({Insert.class}) @RequestBody LinkVo linkVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return linkService.addLink(linkVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改友情链接", notes = "修改友情链接", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改友情链接")
    public String editLink(@Validated({Update.class}) @RequestBody LinkVo linkVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return linkService.editLink(linkVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除友情链接", notes = "删除友情链接", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除友情链接")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "友情链接ids", name = "ids", required = true)
                              @RequestParam(value = "ids") List<Long> ids) {
        return linkService.deleteBatchLink(ids);
    }

    @AuthorityVerify
    @ApiOperation(value = "置顶友情链接", notes = "置顶友情链接", response = String.class)
    @PutMapping(value = "/stick")
    @OperationLogger(value = "置顶友情链接")
    public String stickLink(@Validated({Update.class}) @RequestBody LinkVo LinkVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return linkService.stickLink(LinkVo);
    }
}

