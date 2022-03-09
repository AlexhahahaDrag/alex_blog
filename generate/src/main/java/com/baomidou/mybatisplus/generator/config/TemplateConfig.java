//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baomidou.mybatisplus.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateConfig.class);
    private String entity;
    private String entityKt;
    private String controller;
    private String mapper;
    private String xml;
    private String service;
    private String serviceImpl;
    private String vo;
    private String client;
    private boolean disableEntity;

    private TemplateConfig() {
        this.entity = "/templates/entity.java";
        this.entityKt = "/templates/entity.kt";
        this.controller = "/templates/controller.java";
        this.mapper = "/templates/mapper.java";
        this.xml = "/templates/mapper.xml";
        this.service = "/templates/service.java";
        this.serviceImpl = "/templates/serviceImpl.java";
        this.vo = "/templates/vo.java";
        this.client = "/templates/feignClient.java";
    }

    private void logger(String value, TemplateType templateType) {
        if (StringUtils.isBlank(value)) {
            LOGGER.warn("推荐使用disable(TemplateType.{})方法进行默认模板禁用.", templateType.name());
        }

    }

    public String getEntity(boolean kotlin) {
        if (!this.disableEntity) {
            if (kotlin) {
                return StringUtils.isBlank(this.entityKt) ? "/templates/entity.kt" : this.entityKt;
            } else {
                return StringUtils.isBlank(this.entity) ? "/templates/entity.java" : this.entity;
            }
        } else {
            return null;
        }
    }

    public TemplateConfig disable(@NotNull TemplateType... templateTypes) {
        if (templateTypes != null && templateTypes.length > 0) {
            TemplateType[] var2 = templateTypes;
            int var3 = templateTypes.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                TemplateType templateType = var2[var4];
                switch(templateType) {
                    case ENTITY:
                        this.entity = null;
                        this.entityKt = null;
                        this.disableEntity = true;
                        break;
                    case CONTROLLER:
                        this.controller = null;
                        break;
                    case MAPPER:
                        this.mapper = null;
                        break;
                    case XML:
                        this.xml = null;
                        break;
                    case SERVICE:
                        this.service = null;
                        break;
                    case SERVICEIMPL:
                        this.serviceImpl = null;
                    case VO:
                        this.vo = null;
                    case CLIENT:
                        this.client = null;
                }
            }
        }

        return this;
    }

    public TemplateConfig disable() {
        return this.disable(TemplateType.values());
    }

    public String getService() {
        return this.service;
    }

    public String getServiceImpl() {
        return this.serviceImpl;
    }

    public String getMapper() {
        return this.mapper;
    }

    public String getXml() {
        return this.xml;
    }

    public String getController() {
        return this.controller;
    }

    public String getVo() {
        return vo;
    }

    public String getClient() {
        return client;
    }

    public static class Builder implements IConfigBuilder<TemplateConfig> {
        private final TemplateConfig templateConfig = new TemplateConfig();

        public Builder() {
        }

        public TemplateConfig.Builder disable() {
            this.templateConfig.disable();
            return this;
        }

        public TemplateConfig.Builder disable(@NotNull TemplateType... templateTypes) {
            this.templateConfig.disable(templateTypes);
            return this;
        }

        public TemplateConfig.Builder entity(@NotNull String entityTemplate) {
            this.templateConfig.disableEntity = false;
            this.templateConfig.entity = entityTemplate;
            return this;
        }

        public TemplateConfig.Builder entityKt(@NotNull String entityKtTemplate) {
            this.templateConfig.disableEntity = false;
            this.templateConfig.entityKt = entityKtTemplate;
            return this;
        }

        public TemplateConfig.Builder service(@NotNull String serviceTemplate) {
            this.templateConfig.service = serviceTemplate;
            return this;
        }

        public TemplateConfig.Builder serviceImpl(@NotNull String serviceImplTemplate) {
            this.templateConfig.serviceImpl = serviceImplTemplate;
            return this;
        }

        public TemplateConfig.Builder mapper(@NotNull String mapperTemplate) {
            this.templateConfig.mapper = mapperTemplate;
            return this;
        }

        public TemplateConfig.Builder mapperXml(@NotNull String mapperXmlTemplate) {
            this.templateConfig.xml = mapperXmlTemplate;
            return this;
        }

        public TemplateConfig.Builder controller(@NotNull String controllerTemplate) {
            this.templateConfig.controller = controllerTemplate;
            return this;
        }

        public TemplateConfig.Builder vo(@NotNull String voTemplate) {
            this.templateConfig.vo = voTemplate;
            return this;
        }

        public TemplateConfig.Builder client(@NotNull String clientTemplate) {
            this.templateConfig.client = clientTemplate;
            return this;
        }

        public TemplateConfig build() {
            return this.templateConfig;
        }
    }
}
