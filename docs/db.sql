CREATE TABLE `alex_blog`.`t_blog`  (
 `id` varchar(32) NOT NULL COMMENT '唯一id',
 `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客标题',
 `summary` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客简介',
 `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '博客内容',
 `tag_id` varchar(200) COMMENT '标签id',
 `praise_count` int(11) NULL DEFAULT 0 COMMENT '博客点赞数',
 `click_count` int(11) NULL DEFAULT 0 COMMENT '博客点击数',
 `collect_count` int(11) NULL DEFAULT 0 COMMENT '博客收藏数',
 `file_id` int(11) NULL DEFAULT NULL COMMENT '标题图片uid',
 `status` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态',
 `creator` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
 `updater` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
 `update_time` timestamp NOT NULL COMMENT '更新时间',
 `deleter` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人',
 `delete_time` timestamp NOT NULL COMMENT '更新时间',
 `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除',
 `operator` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
 `operate_time` timestamp NOT NULL COMMENT '操作时间',
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
 `id` varchar(32) AUTO_INCREMENT COMMENT '唯一id',
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
 `operator` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
 `operate_time` timestamp NOT NULL COMMENT '操作时间',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';

DROP TABLE IF EXISTS `t_blog_sort`;

CREATE TABLE `t_blog_sort` (
`id` varchar(32) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
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
`is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除',
`operator` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
`operate_time` timestamp NOT NULL COMMENT '操作时间'
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博客分类表';


DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
 `id` varchar(32) NOT NULL COMMENT '唯一id',
 `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
 `to_id` varchar(32) DEFAULT NULL COMMENT '回复某条评论的id',
 `to_user_id` varchar(32) DEFAULT NULL COMMENT '回复某个人的id',
 `content` varchar(2048) DEFAULT NULL COMMENT '评论内容',
 `blog_id` varchar(32) DEFAULT NULL COMMENT '博客id',
 `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态',
 `create_time` timestamp COMMENT '创建时间',
 `creator` varchar(32) COMMENT '创建人',
 `update_time` timestamp COMMENT '更新时间',
 `updater` varchar(32) COMMENT '更新人',
 `delete_time` timestamp COMMENT '删除时间',
 `deleter` varchar(32) COMMENT '删除人',
 `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除',
 `operator` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
 `operate_time` timestamp NOT NULL COMMENT '操作时间'
 `source` varchar(255) NOT NULL COMMENT '评论来源： MESSAGE_BOARD，ABOUT，BLOG_INFO 等',
 `TYPE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '评论类型 1:点赞 0:评论',
 `first_comment_id` varchar(32) DEFAULT NULL COMMENT '一级评论ID',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

DROP TABLE IF EXISTS `t_sys_params`;
CREATE TABLE `t_sys_params` (
    `id` VARCHAR ( 32 ) NOT NULL COMMENT '主键',
    `params_type` VARCHAR ( 1 ) NOT NULL DEFAULT '1' COMMENT '配置类型 是否系统内置(1:，是 0:否)',
    `params_name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '参数名称',
    `params_key` VARCHAR ( 255 ) DEFAULT NULL COMMENT '参数键名',
    `remark` VARCHAR ( 255 ) DEFAULT NULL COMMENT '备注',
    `params_value` VARCHAR ( 255 ) DEFAULT NULL COMMENT '参数键值',
    `status` TINYINT ( 1 ) DEFAULT '1' COMMENT '状态',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR ( 200 ) CHARACTER
        SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `update_time` TIMESTAMP NOT NULL COMMENT '更新时间',
    `deleter` VARCHAR ( 200 ) CHARACTER
        SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人',
    `delete_time` TIMESTAMP NOT NULL COMMENT '更新时间',
    `is_delete` TINYINT ( 4 ) NULL DEFAULT NULL COMMENT '是否删除',
    `operator` VARCHAR ( 200 ) CHARACTER
        SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
    `operate_time` TIMESTAMP NOT NULL COMMENT '操作时间',
    `sort` INT NOT NULL DEFAULT '0' COMMENT '排序字段',
    PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '参数配置表';