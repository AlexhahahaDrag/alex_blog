drop table if exists `alex_blog`.`t_sys_log`;

CREATE TABLE `alex_blog`.`t_sys_log`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `admin_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员uid',
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求ip地址',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `class_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求类路径',
  `method` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法名',
  `params` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `operation` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
	`operator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `operate_time` timestamp NULL COMMENT '操作时间',
  `ip_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `spend_time` int NULL DEFAULT 0 COMMENT '方法请求花费的时间',
  `deleter` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `t_web_navbar`;

CREATE TABLE `t_web_navbar` (
    `id` varchar(96) NOT NULL,
    `name` varchar(765) DEFAULT NULL,
    `navbar_level` tinyint(1) DEFAULT NULL,
    `summary` varchar(600) DEFAULT NULL,
    `parent_uid` varchar(96) DEFAULT NULL,
    `url` varchar(765) DEFAULT NULL,
    `icon` varchar(150) DEFAULT NULL,
    `is_show` tinyint(1) DEFAULT NULL,
    `is_jump_external_url` tinyint(1) DEFAULT NULL,
    `sort` int DEFAULT NULL,
    `status` tinyint(1) DEFAULT NULL,
    `creator` VARCHAR ( 50 ) CHARACTER
        SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time` TIMESTAMP NULL COMMENT '创建时间',
    `updater` VARCHAR ( 50 ) CHARACTER
        SET utf8 COLLATE utf8_general_ci NULL COMMENT '修改人',
    `update_time` TIMESTAMP NULL COMMENT '更新时间',
    `deleter` VARCHAR ( 50 ) CHARACTER
        SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人',
    `delete_time` TIMESTAMP NULL COMMENT '删除时间',
    `is_delete` TINYINT ( 1 ) NULL DEFAULT 0 COMMENT '是否删除',
    `operator` VARCHAR ( 50 ) CHARACTER
        SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `operate_time` TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;