DROP TABLE IF EXISTS `t_web_visit`;

CREATE TABLE `t_web_visit` (
   `id` varchar(32) NOT NULL COMMENT '主键',
   `admin_id` varchar(255) DEFAULT NULL COMMENT '用户id',
   `ip` varchar(255) DEFAULT NULL COMMENT '访问ip地址',
   `behavior` varchar(255) DEFAULT NULL COMMENT '用户行为',
   `module_id` varchar(255) DEFAULT NULL COMMENT '模块id（文章id，标签id，分类id）',
   `other_data` varchar(255) DEFAULT NULL COMMENT '附加数据(比如搜索内容)',
   `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
   `creator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
   `create_time` timestamp NULL COMMENT '创建时间',
   `updater` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
   `update_time` timestamp NOT NULL COMMENT '更新时间',
   `deleter` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人',
   `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
   `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
   `operator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
   `operate_time` timestamp NOT NULL COMMENT '更新时间',
   `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
   `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
   `ip_source` varchar(255) DEFAULT NULL COMMENT 'ip来源',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Web访问记录表';