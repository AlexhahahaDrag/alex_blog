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
        this.strictInsertFill(metaObject, "status", Integer.class, EStatus.ENABLE.getCode());
        this.strictInsertFill(metaObject, "isDelete", Integer.class,0);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        metaObject.setValue("operateTime", null);
        this.strictInsertFill(metaObject, "operateTime", LocalDateTime.class,  now);
        if (UserUtil.getLoginUser() != null && StringUtils.isNotEmpty(UserUtil.getLoginUser().getId())) {
            String id = UserUtil.getLoginUser().getId();
            this.strictInsertFill(metaObject, "creator", String.class, id);
            this.strictInsertFill(metaObject, "operator", String.class, id);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        LocalDateTime now = LocalDateTime.now();
        metaObject.setValue("operateTime", null);
        metaObject.setValue("updateTime", null);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class,  now);
        this.strictUpdateFill(metaObject, "operateTime", LocalDateTime.class,  now);
        if (UserUtil.getLoginUser() != null && StringUtils.isNotEmpty(UserUtil.getLoginUser().getId())) {
            String id = UserUtil.getLoginUser().getId();
            metaObject.setValue("updater", null);
            metaObject.setValue("operator", null);
            this.strictUpdateFill(metaObject, "updater", String.class, id);
            this.strictUpdateFill(metaObject, "operator", String.class, id);
        }

    }
}