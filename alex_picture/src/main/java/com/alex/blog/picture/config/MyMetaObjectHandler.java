package com.alex.blog.picture.config;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.common.utils.UserUtil;
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
        this.fillStrategy(metaObject, "status", EStatus.ENABLE.getCode());
        this.fillStrategy(metaObject, "isDelete", 0);
        this.fillStrategy(metaObject, "createTime", LocalDateTime.now());
        this.fillStrategy(metaObject, "updateTime",  LocalDateTime.now());
        this.fillStrategy(metaObject, "operateTime",  LocalDateTime.now());
        // TODO: 2021/12/2 测试是否可以通过
        this.fillStrategy(metaObject, "creator",  UserUtil.getLoginUser() == null ? "" : UserUtil.getLoginUser().getId());
        this.fillStrategy(metaObject, "operator",  UserUtil.getLoginUser().getId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.fillStrategy(metaObject, "updateTime",  LocalDateTime.now());
        this.fillStrategy(metaObject, "operateTime",  LocalDateTime.now());
        // TODO: 2021/12/2 测试是否可以通过
        this.fillStrategy(metaObject, "updater",  UserUtil.getLoginUser() == null ? "" : UserUtil.getLoginUser().getId());
        this.fillStrategy(metaObject, "operator",  UserUtil.getLoginUser().getId());
    }
}