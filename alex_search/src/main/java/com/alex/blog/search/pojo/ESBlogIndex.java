package com.alex.blog.search.pojo;

import com.alex.blog.common.entity.blog.BlogSort;
import com.alex.blog.common.entity.blog.Tag;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 *description:
 *              @Document  ESBlogIndex
 *              indexName: 项目空间
 *              indexStoreType： 类型
 *              shards： 分区数
 *              replicas： 备份数
 *author:       alex
 *createDate:   2022/1/24 17:39
 *version:      1.0.0
 */
@Data
@Document(indexName = "#{elasticsearch.index}", indexStoreType = "docs", shards = 1, replicas = 0)
public class ESBlogIndex {

    private String id;

    private String uid;

    private Integer oid;

    private String type;

    private String title;

    private String summary;

    private String content;

    private List<String> sortIdList;

    private List<String> sortNameList;

    private String isPublish;

    private LocalDateTime createTime;

    private LocalDateTime operateTime;

    private String author;

    private List<String> tagIdList;

    private List<String> tagNameList;

    private String photoUrl;
}
