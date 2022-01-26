package com.alex.blog.search.service;

import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.feign.WebFeignClient;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.search.pojo.ESBlogIndex;
import com.alex.blog.search.repository.BlogRepository;
import com.alex.blog.search.utils.ElasticsearchUtils;
import com.alex.blog.utils.utils.JsonUtils;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *description:  elastic search service
 *author:       alex
 *createDate:   2022/1/21 17:47
 *version:      1.0.0
 */
@Service
@Slf4j
public class ElasticSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final String UID = "_id";

    @Autowired
    private BlogRepository blogRepository;

    @Value(value = "${elasticsearch.index}")
    private String indexName;

    @Resource
    private WebFeignClient webFeignClient;

    @Autowired
    private ElasticsearchUtils elasticsearchUtils;

    public ESBlogIndex buildBlog(Blog blog) {
        ESBlogIndex esBlogIndex = new ESBlogIndex();
        BeanUtils.copyProperties(blog, esBlogIndex);
        return esBlogIndex;
    }

    public Map<String, Object> search(String keyword, Integer pageSize, Object[] lastSortValue) throws IOException {
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页
        searchSourceBuilder.sort(UID, SortOrder.ASC);
        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span class='key' style='color:red'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("name");
        searchSourceBuilder.highlighter(highlightBuilder);
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("title", keyword);
        searchRequest.source(SearchSourceBuilder.searchSource()
                .query(termQueryBuilder)
                .size(pageSize)
                .docValueField("sessionId", "use_field_mapping")
                .docValueField("createTime", "use_field_mapping")
                .timeout(TimeValue.timeValueSeconds(10))
                .sort(UID, SortOrder.DESC)
                .sort("createTime", SortOrder.DESC)
                .sort("sessionId", SortOrder.DESC)
        );
        if (lastSortValue != null && lastSortValue.length > 0) {
            // searchAfter 将上一轮结果的最后一个值放到这里
            searchRequest.source().searchAfter(lastSortValue);
        }
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hitsArray = search.getHits().getHits();
        List<ESBlogIndex> res = new ArrayList<>();
        if (hitsArray.length > 0) {
            for (SearchHit hit : hitsArray) {
                ESBlogIndex content = JsonUtils.jsonToPojo(hit.getSourceAsString(), ESBlogIndex.class);
                HighlightField name = hit.getHighlightFields().get(keyword);
                System.out.println(name.getFragments()[0]);
                content.setContent(name.getFragments()[0].string());
                res.add(content);
            }
        }
        Map<String, Object> map  = new HashMap<>();
        map.put(SysConf.BLOG_LIST, res);
        map.put("last_sort_value", hitsArray[hitsArray.length - 1].getSortValues());
        return map;
    }

    /**
     * @param ids
     * @description: 批量删除blog
     * @author:      alex
     * @return:      void
     */
    public void deleteBlogByIds(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return;
        }
        List<String> idList = StringUtils.splitStringByCode(ids, SysConf.FILE_SEGMENTATION);
        if (idList == null || idList.size() == 0) {
            return;
        }
        for(String id : idList) {
            blogRepository.deleteById(id);
        }
    }

    /**
     * @param id
     * @description: 新增blog
     * @author:      alex
     * @createDate:  2022/1/25 16:54
     * @return:      void
     */
    public String addBlog(String id) {
        Blog blog = webFeignClient.getBlogById(id);
        if (blog == null) {
            return ResultUtil.resultErrorWithMessage("id为空");
        }
        ESBlogIndex esBlogIndex = buildBlog(blog);
        blogRepository.save(esBlogIndex);
        return ResultUtil.resultSuccessWithData(MessageConf.INSERT_SUCCESS);
    }

    public String initElasticSearchIndex() {
        elasticsearchUtils.deleteIndex(indexName);
        elasticsearchUtils.createIndex(indexName);
        int size = 0;
        long currentpage = 1l;
        long pageSize = 10l;
        do {
            IPage<Blog> blogResult = webFeignClient.getBlogPage(currentpage, pageSize);
            List<Blog> records = blogResult.getRecords();
            List<ESBlogIndex> collect = records.stream().map(this::buildBlog).collect(Collectors.toList());
            //存入索引库
            blogRepository.saveAll(collect);
        } while (++currentpage < 15);
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.OPERATION_SUCCESS);
    }
}
