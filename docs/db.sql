CREATE TABLE `alex_blog`.`t_blog`  (
                                         `id` int(11) NOT NULL COMMENT '唯一id',
                                         `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客标题',
                                         `summary` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客简介',
                                         `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '博客内容',
                                         `tag_id` varchar(200) COMMENT '标签id',
                                         `click_count` int(11) NULL DEFAULT 0 COMMENT '博客点击数',
                                         `collect_count` int(11) NULL DEFAULT 0 COMMENT '博客收藏数',
                                         `file_id` int(11) NULL DEFAULT NULL COMMENT '标题图片uid',
                                         `status` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态',
                                         `creator` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                         `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `updater` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
                                         `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
                                         `deleter` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人',
                                         `delete_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
                                         `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除',
                                         `admin_id` int(11) NULL DEFAULT NULL COMMENT '管理员id',
                                         `is_original` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否原创（0:不是 1：是）',
                                         `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
                                         `articles_part` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章出处',
                                         `blog_sort_id` varchar(200) COMMENT '博客分类ID',
                                         `level` tinyint(1) NULL DEFAULT 0 COMMENT '推荐等级(0:正常)',
                                         `is_publish` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否发布：0：否，1：是',
                                         `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序字段',
                                         `open_comment` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启评论(0:否 1:是)',
                                         `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '类型【0 博客， 1：推广】',
                                         `outside_link` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外链【如果是推广，那么将跳转到外链】',
                                         `oid` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一oid',
                                         PRIMARY KEY (`id`, `oid`) USING BTREE,
                                         INDEX `oid`(`oid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客表' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
                         `id` int4 AUTO_INCREMENT COMMENT '唯一id',
                         `content` varchar(1000) DEFAULT NULL COMMENT '标签内容',
                         `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
                         `click_count` int DEFAULT '0' COMMENT '标签简介',
                         `sort` int DEFAULT '0' COMMENT '排序字段，越大越靠前',
                         `creator` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
                         `create_time` timestamp COMMENT '创建时间',
                         `updater` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
                         `update_time` timestamp COMMENT '更新时间',
                         `deleter` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人',
                         `delete_time` timestamp COMMENT '更新时间',
                         `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';

DROP TABLE IF EXISTS `t_blog_sort`;

CREATE TABLE `t_blog_sort` (
                               `uid` int4 NOT NULL AUTO_INCREMENT COMMENT '唯一id',
                               `sort_name` varchar(255) DEFAULT NULL COMMENT '分类内容',
                               `content` varchar(255) DEFAULT NULL COMMENT '分类简介',
                               `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
                               `sort` int DEFAULT '0' COMMENT '排序字段，越大越靠前',
                               `click_count` int DEFAULT '0' COMMENT '点击数',
                               `creator` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
                               `create_time` timestamp COMMENT '创建时间',
                               `updater` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
                               `update_time` timestamp COMMENT '更新时间',
                               `deleter` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人',
                               `delete_time` timestamp COMMENT '删除时间',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博客分类表';