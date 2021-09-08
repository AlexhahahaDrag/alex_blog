package com.alex.blog.admin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *description:  设置新增和修改的默认时间
 *author:       alex
 *createDate:   2021/6/6 15:16
 *version:      1.0.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createDate", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "isDelete", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictInsertFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "isDelete", Integer.class, 0);
    }
}