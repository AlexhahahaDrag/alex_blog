package com.alex.blog.search.repository;

import com.alex.blog.search.pojo.ESBlogIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *description:  blog repository 操作elastic search
 *author:       alex
 *createDate:   2022/1/25 16:49
 *version:      1.0.0
 */
public interface BlogRepository extends ElasticsearchRepository<ESBlogIndex, String> {
}
