package com.alex.blog.admin.config;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.common.utils.UserUtil;
import com.alex.blog.utils.utils.StringUtils;
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
        LocalDateTime now = LocalDateTime.now();
        if (metaObject.hasSetter("status")) {
            this.strictInsertFill(metaObject, "status", Integer.class, EStatus.ENABLE.getCode());
        }
        if (metaObject.hasSetter("isDelete")) {
            this.strictInsertFill(metaObject, "isDelete", Integer.class, 0);
        }
        if (metaObject.hasSetter("createTime")) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        }
        if (metaObject.hasSetter("operateTime")) {
            metaObject.setValue("operateTime", null);
            this.strictInsertFill(metaObject, "operateTime", LocalDateTime.class, now);
        }
        if (UserUtil.getLoginUser() != null && UserUtil.getLoginUser().getId() != null) {
            Long id = UserUtil.getLoginUser().getId();
            if (metaObject.hasSetter("creator")) {
                this.strictInsertFill(metaObject, "creator", Long.class, id);
            }
            if (metaObject.hasSetter("operator")) {
                this.strictInsertFill(metaObject, "operator", Long.class, id);
            }
        }
    }

    // TODO: 2022/4/25 配置删除自动填充 
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        LocalDateTime now = LocalDateTime.now();
        if (metaObject.hasSetter("operateTime")) {
            metaObject.setValue("operateTime", null);
            this.strictUpdateFill(metaObject, "operateTime", LocalDateTime.class,  now);
        }
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", null);
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class,  now);
        }
        if (UserUtil.getLoginUser() != null && UserUtil.getLoginUser().getId() != null) {
            Long id = UserUtil.getLoginUser().getId();
            if (metaObject.hasSetter("updater")) {
                metaObject.setValue("updater", null);
                this.strictUpdateFill(metaObject, "updater", Long.class, id);
            }
            if (metaObject.hasSetter("operator")) {
                metaObject.setValue("operator", null);
                this.strictUpdateFill(metaObject, "operator", Long.class, id);
            }
        }
    }
}