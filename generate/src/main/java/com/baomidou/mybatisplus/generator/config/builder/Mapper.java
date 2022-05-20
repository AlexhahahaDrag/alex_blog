//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baomidou.mybatisplus.generator.config.builder;

import com.baomidou.mybatisplus.generator.ITemplate;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;
import com.baomidou.mybatisplus.generator.util.ClassUtils;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LoggingCache;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Mapper implements ITemplate {
    private String superClass;
    private boolean mapperAnnotation;
    private boolean baseResultMap;
    private boolean baseColumnList;
    private ConverterFileName converterMapperFileName;
    private ConverterFileName converterXmlFileName;
    private Class<? extends Cache> cache;

    private Mapper() {
        this.superClass = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
        this.converterMapperFileName = (entityName) -> {
            return entityName + "Mapper";
        };
        this.converterXmlFileName = (entityName) -> {
            return entityName + "Mapper";
        };
    }

    @NotNull
    public String getSuperClass() {
        return this.superClass;
    }

    public boolean isMapperAnnotation() {
        return this.mapperAnnotation;
    }

    public boolean isBaseResultMap() {
        return this.baseResultMap;
    }

    public boolean isBaseColumnList() {
        return this.baseColumnList;
    }

    public ConverterFileName getConverterMapperFileName() {
        return this.converterMapperFileName;
    }

    public ConverterFileName getConverterXmlFileName() {
        return this.converterXmlFileName;
    }

    public Class<? extends Cache> getCache() {
        return this.cache == null ? LoggingCache.class : this.cache;
    }

    @NotNull
    public Map<String, Object> renderData(@NotNull TableInfo tableInfo) {
        Map<String, Object> data = new HashMap();
        boolean enableCache = this.cache != null;
        data.put("enableCache", enableCache);
        data.put("mapperAnnotation", this.mapperAnnotation);
        data.put("baseResultMap", this.baseResultMap);
        data.put("baseColumnList", this.baseColumnList);
        data.put("superMapperClassPackage", this.superClass);
        if (enableCache) {
            Class<? extends Cache> cacheClass = this.getCache();
            data.put("cache", cacheClass);
            data.put("cacheClassName", cacheClass.getName());
        }

        data.put("superMapperClass", ClassUtils.getSimpleName(this.superClass));
        return data;
    }

    public static class Builder extends BaseBuilder {
        private final Mapper mapper = new Mapper();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        public Mapper.Builder superClass(@NotNull String superClass) {
            this.mapper.superClass = superClass;
            return this;
        }

        public Mapper.Builder superClass(@NotNull Class<?> superClass) {
            return this.superClass(superClass.getName());
        }

        public Mapper.Builder enableMapperAnnotation() {
            this.mapper.mapperAnnotation = true;
            return this;
        }

        public Mapper.Builder enableBaseResultMap() {
            this.mapper.baseResultMap = true;
            return this;
        }

        public Mapper.Builder enableBaseColumnList() {
            this.mapper.baseColumnList = true;
            return this;
        }

        public Mapper.Builder cache(@NotNull Class<? extends Cache> cache) {
            this.mapper.cache = cache;
            return this;
        }

        public Mapper.Builder convertMapperFileName(@NotNull ConverterFileName converter) {
            this.mapper.converterMapperFileName = converter;
            return this;
        }

        public Mapper.Builder convertXmlFileName(@NotNull ConverterFileName converter) {
            this.mapper.converterXmlFileName = converter;
            return this;
        }

        public Mapper.Builder formatMapperFileName(@NotNull String format) {
            return this.convertMapperFileName((entityName) -> {
                return String.format(format, entityName);
            });
        }

        public Mapper.Builder formatXmlFileName(@NotNull String format) {
            return this.convertXmlFileName((entityName) -> {
                return String.format(format, entityName);
            });
        }

        @NotNull
        public Mapper get() {
            return this.mapper;
        }
    }
}