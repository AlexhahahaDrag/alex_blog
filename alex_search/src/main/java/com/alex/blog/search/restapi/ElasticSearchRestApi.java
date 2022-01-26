package com.alex.blog.search.restapi;

import com.alex.blog.common.global.MessageConf;
import com.alex.blog.search.service.ElasticSearchService;
import com.alex.blog.utils.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 *description:  elastic controller
 *author:       alex
 *createDate:   2022/1/21 17:25
 *version:      1.0.0
 */
@RestController
@RequestMapping(value = "/search")
@Api(value = "ElasticSearch相关接口", tags = {"ElasticSearch相关接口"})
public class ElasticSearchRestApi {

    @Autowired
    private ElasticSearchService searchService;

    @ApiOperation(value = "通过elasticsearch搜索博客", notes = "通过elasticsearch搜索博客")
    @GetMapping(value = "/elasticSearchBlog")
    public String searchBlog(@ApiParam(value = "keyword", name = "关键字", required = true) @RequestParam(value = "keyword") String keyword,
                             @ApiParam(value = "pageSize", name = "当前页大小") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @ApiParam(value = "lastSortValue", name = "最后的索引") @RequestParam(value = "lastSortValue", required = false) Object[] lastSortValue) throws IOException {
        return ResultUtil.resultSuccessWithData(searchService.search(keyword, pageSize, lastSortValue));
    }

    @ApiOperation(value = "通过ids删除elasticsearch博客索引", notes = "通过ids删除elasticsearch博客索引")
    @PostMapping(value = "/deleteElasticSearchByIds")
    public String deleteElasticSearchByIds(@ApiParam(value = "ids", name = "ids", required = true) @RequestParam(value = "ids") String ids) {
        searchService.deleteBlogByIds(ids);
        return ResultUtil.resultErrorWithMessage(MessageConf.DELETE_SUCCESS);
    }

    @ApiOperation(value = "通过ids新增elasticsearch博客索引", notes = "通过ids新增elasticsearch博客索引")
    @PostMapping(value = "/addElasticSearchIndexById")
    public String initElasticSearchIndex(@ApiParam(value = "id", name = "id", required = true) @RequestParam(value = "id") String id) {
        return searchService.addBlog(id);
    }

    @ApiOperation(value = "ElasticSearch初始化索引", notes = "ElasticSearch初始化索引")
    @PostMapping(value = "/initElasticSearchIndex")
    public String initElasticSearchIndex() {
        return searchService.initElasticSearchIndex();
    }
}
