//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baomidou.mybatisplus.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.builder.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class StrategyConfig {
    private boolean isCapitalMode;
    private boolean skipView;
    private final Set<String> tablePrefix;
    private final Set<String> tableSuffix;
    private final Set<String> fieldPrefix;
    private final Set<String> fieldSuffix;
    private final Set<String> include;
    private final Set<String> exclude;
    private boolean enableSqlFilter;
    private boolean enableSchema;
    private LikeTable likeTable;
    private LikeTable notLikeTable;
    private final com.baomidou.mybatisplus.generator.config.builder.Entity.Builder entityBuilder;
    private final com.baomidou.mybatisplus.generator.config.builder.Controller.Builder controllerBuilder;
    private final com.baomidou.mybatisplus.generator.config.builder.Mapper.Builder mapperBuilder;
    private final com.baomidou.mybatisplus.generator.config.builder.Service.Builder serviceBuilder;
    private final com.baomidou.mybatisplus.generator.config.builder.Vo.Builder voBuilder;
    private final com.baomidou.mybatisplus.generator.config.builder.Client.Builder clientBuilder;
    private Entity entity;
    private Controller controller;
    private Mapper mapper;
    private Service service;
    private Vo vo;
    private Client client;

    private StrategyConfig() {
        this.tablePrefix = new HashSet();
        this.tableSuffix = new HashSet();
        this.fieldPrefix = new HashSet();
        this.fieldSuffix = new HashSet();
        this.include = new HashSet();
        this.exclude = new HashSet();
        this.enableSqlFilter = true;
        this.entityBuilder = new com.baomidou.mybatisplus.generator.config.builder.Entity.Builder(this);
        this.controllerBuilder = new com.baomidou.mybatisplus.generator.config.builder.Controller.Builder(this);
        this.mapperBuilder = new com.baomidou.mybatisplus.generator.config.builder.Mapper.Builder(this);
        this.serviceBuilder = new com.baomidou.mybatisplus.generator.config.builder.Service.Builder(this);
        this.voBuilder = new com.baomidou.mybatisplus.generator.config.builder.Vo.Builder(this);
        this.clientBuilder = new com.baomidou.mybatisplus.generator.config.builder.Client.Builder(this);
    }

    @NotNull
    public com.baomidou.mybatisplus.generator.config.builder.Entity.Builder entityBuilder() {
        return this.entityBuilder;
    }

    @NotNull
    public Entity entity() {
        if (this.entity == null) {
            this.entity = this.entityBuilder.get();
        }

        return this.entity;
    }

    @NotNull
    public com.baomidou.mybatisplus.generator.config.builder.Controller.Builder controllerBuilder() {
        return this.controllerBuilder;
    }

    @NotNull
    public Controller controller() {
        if (this.controller == null) {
            this.controller = this.controllerBuilder.get();
        }

        return this.controller;
    }

    @NotNull
    public com.baomidou.mybatisplus.generator.config.builder.Mapper.Builder mapperBuilder() {
        return this.mapperBuilder;
    }

    @NotNull
    public Mapper mapper() {
        if (this.mapper == null) {
            this.mapper = this.mapperBuilder.get();
        }

        return this.mapper;
    }

    @NotNull
    public com.baomidou.mybatisplus.generator.config.builder.Service.Builder serviceBuilder() {
        return this.serviceBuilder;
    }

    @NotNull
    public Service service() {
        if (this.service == null) {
            this.service = this.serviceBuilder.get();
        }

        return this.service;
    }

    @NotNull
    public com.baomidou.mybatisplus.generator.config.builder.Vo.Builder voBuilder() {
        return this.voBuilder;
    }

    @NotNull
    public Vo vo() {
        if (this.vo == null) {
            this.vo = this.voBuilder.get();
        }

        return this.vo;
    }

    @NotNull
    public com.baomidou.mybatisplus.generator.config.builder.Client.Builder clientBuilder() {
        return this.clientBuilder;
    }

    @NotNull
    public Client client() {
        if (this.client == null) {
            this.client = this.clientBuilder.get();
        }

        return this.client;
    }

    public boolean isCapitalModeNaming(@NotNull String word) {
        return this.isCapitalMode && StringUtils.isCapitalMode(word);
    }

    public boolean startsWithTablePrefix(@NotNull String tableName) {
        Stream<String> var10000 = this.tablePrefix.stream();
        tableName.getClass();
        return var10000.anyMatch(t -> tableName.startsWith(t));
    }

    public void validate() {
        boolean isInclude = this.getInclude().size() > 0;
        boolean isExclude = this.getExclude().size() > 0;
        if (isInclude && isExclude) {
            throw new IllegalArgumentException("<strategy> 标签中 <include> 与 <exclude> 只能配置一项！");
        } else if (this.getNotLikeTable() != null && this.getLikeTable() != null) {
            throw new IllegalArgumentException("<strategy> 标签中 <likeTable> 与 <notLikeTable> 只能配置一项！");
        }
    }

    public boolean matchIncludeTable(@NotNull String tableName) {
        return this.matchTable(tableName, this.getInclude());
    }

    public boolean matchExcludeTable(@NotNull String tableName) {
        return this.matchTable(tableName, this.getExclude());
    }

    private boolean matchTable(@NotNull String tableName, @NotNull Set<String> matchTables) {
        return matchTables.stream().anyMatch((t) -> {
            return this.tableNameMatches(t, tableName);
        });
    }

    private boolean tableNameMatches(@NotNull String matchTableName, @NotNull String dbTableName) {
        return matchTableName.equalsIgnoreCase(dbTableName) || StringUtils.matches(matchTableName, dbTableName);
    }

    public boolean isCapitalMode() {
        return this.isCapitalMode;
    }

    public boolean isSkipView() {
        return this.skipView;
    }

    @NotNull
    public Set<String> getTablePrefix() {
        return this.tablePrefix;
    }

    @NotNull
    public Set<String> getTableSuffix() {
        return this.tableSuffix;
    }

    @NotNull
    public Set<String> getFieldPrefix() {
        return this.fieldPrefix;
    }

    @NotNull
    public Set<String> getFieldSuffix() {
        return this.fieldSuffix;
    }

    @NotNull
    public Set<String> getInclude() {
        return this.include;
    }

    @NotNull
    public Set<String> getExclude() {
        return this.exclude;
    }

    public boolean isEnableSqlFilter() {
        return this.enableSqlFilter;
    }

    public boolean isEnableSchema() {
        return this.enableSchema;
    }

    @Nullable
    public LikeTable getLikeTable() {
        return this.likeTable;
    }

    @Nullable
    public LikeTable getNotLikeTable() {
        return this.notLikeTable;
    }

    public static class Builder extends BaseBuilder {
        private final StrategyConfig strategyConfig = super.build();

        public Builder() {
            super(new StrategyConfig());
        }

        public StrategyConfig.Builder enableCapitalMode() {
            this.strategyConfig.isCapitalMode = true;
            return this;
        }

        public StrategyConfig.Builder enableSkipView() {
            this.strategyConfig.skipView = true;
            return this;
        }

        public StrategyConfig.Builder disableSqlFilter() {
            this.strategyConfig.enableSqlFilter = false;
            return this;
        }

        public StrategyConfig.Builder enableSchema() {
            this.strategyConfig.enableSchema = true;
            return this;
        }

        public StrategyConfig.Builder addTablePrefix(@NotNull String... tablePrefix) {
            this.strategyConfig.tablePrefix.addAll(Arrays.asList(tablePrefix));
            return this;
        }

        public StrategyConfig.Builder addTableSuffix(String... tableSuffix) {
            this.strategyConfig.tableSuffix.addAll(Arrays.asList(tableSuffix));
            return this;
        }

        public StrategyConfig.Builder addFieldPrefix(@NotNull String... fieldPrefix) {
            this.strategyConfig.fieldPrefix.addAll(Arrays.asList(fieldPrefix));
            return this;
        }

        public StrategyConfig.Builder addFieldSuffix(@NotNull String... fieldSuffix) {
            this.strategyConfig.fieldSuffix.addAll(Arrays.asList(fieldSuffix));
            return this;
        }

        public StrategyConfig.Builder addInclude(@NotNull String... include) {
            this.strategyConfig.include.addAll(Arrays.asList(include));
            return this;
        }

        public StrategyConfig.Builder addInclude(@NotNull List<String> includes) {
            this.strategyConfig.include.addAll(includes);
            return this;
        }

        public StrategyConfig.Builder addExclude(@NotNull String... exclude) {
            this.strategyConfig.exclude.addAll(Arrays.asList(exclude));
            return this;
        }

        public StrategyConfig.Builder likeTable(@NotNull LikeTable likeTable) {
            this.strategyConfig.likeTable = likeTable;
            return this;
        }

        public StrategyConfig.Builder notLikeTable(@NotNull LikeTable notLikeTable) {
            this.strategyConfig.notLikeTable = notLikeTable;
            return this;
        }

        @NotNull
        public StrategyConfig build() {
            this.strategyConfig.validate();
            return this.strategyConfig;
        }
    }
}