//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baomidou.mybatisplus.generator.config.builder;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.ITemplate;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;
import com.baomidou.mybatisplus.generator.util.ClassUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Client implements ITemplate {
    private boolean restStyle;
    private boolean hyphenStyle;
    private String superClass;
    private ConverterFileName converterFileName;

    private Client() {
        this.converterFileName = (entityName) -> {
            return entityName + "Client";
        };
    }

    public boolean isRestStyle() {
        return this.restStyle;
    }

    public boolean isHyphenStyle() {
        return this.hyphenStyle;
    }

    @Nullable
    public String getSuperClass() {
        return this.superClass;
    }

    @NotNull
    public ConverterFileName getConverterFileName() {
        return this.converterFileName;
    }

    @NotNull
    public Map<String, Object> renderData(@NotNull TableInfo tableInfo) {
        Map<String, Object> data = new HashMap();
        data.put("ClientMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
        data.put("ClientMappingHyphenStyle", this.hyphenStyle);
        data.put("restClientStyle", this.restStyle);
        data.put("superClientClassPackage", StringUtils.isBlank(this.superClass) ? null : this.superClass);
        data.put("superClientClass", ClassUtils.getSimpleName(this.superClass));
        return data;
    }

    public static class Builder extends BaseBuilder {
        private final Client Client = new Client();

        public Builder(@NotNull StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        public Client.Builder superClass(@NotNull Class<?> clazz) {
            return this.superClass(clazz.getName());
        }

        public Client.Builder superClass(@NotNull String superClass) {
            this.Client.superClass = superClass;
            return this;
        }

        public Client.Builder enableHyphenStyle() {
            this.Client.hyphenStyle = true;
            return this;
        }

        public Client.Builder enableRestStyle() {
            this.Client.restStyle = true;
            return this;
        }

        public Client.Builder convertFileName(@NotNull ConverterFileName converter) {
            this.Client.converterFileName = converter;
            return this;
        }

        public Client.Builder formatFileName(@NotNull String format) {
            return this.convertFileName((entityName) -> {
                return String.format(format, entityName);
            });
        }

        @NotNull
        public Client get() {
            return this.Client;
        }
    }
}
