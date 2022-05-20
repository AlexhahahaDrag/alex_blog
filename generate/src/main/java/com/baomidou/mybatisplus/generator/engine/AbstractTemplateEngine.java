//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baomidou.mybatisplus.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.util.FileUtils;
import com.baomidou.mybatisplus.generator.util.RuntimeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractTemplateEngine {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ConfigBuilder configBuilder;

    public AbstractTemplateEngine() {
    }

    @NotNull
    public abstract AbstractTemplateEngine init(@NotNull ConfigBuilder configBuilder);

    protected void outputCustomFile(@NotNull Map<String, String> customFile, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String otherPath = this.getPathInfo(OutputFile.other);
        String feignPath = this.getPathInfo(OutputFile.feign);
        customFile.forEach((key, value) -> {
            String fileName = String.format(feignPath + File.separator + File.separator + "%s", key);
            this.outputFile(new File(fileName), objectMap, value);
        });
    }

    protected void outputEntity(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String entityPath = this.getPathInfo(OutputFile.entity);
        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(entityPath)) {
            this.getTemplateFilePath((template) -> {
                return template.getEntity(this.getConfigBuilder().getGlobalConfig().isKotlin());
            }).ifPresent((entity) -> {
                String entityFile = String.format(entityPath + File.separator + "%s" + this.suffixJavaOrKt(), entityName);
                this.outputFile(new File(entityFile), objectMap, entity);
            });
        }

    }

    protected void outputMapper(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String mapperPath = this.getPathInfo(OutputFile.mapper);
        if (StringUtils.isNotBlank(tableInfo.getMapperName()) && StringUtils.isNotBlank(mapperPath)) {
            this.getTemplateFilePath(TemplateConfig::getMapper).ifPresent((mapper) -> {
                String mapperFile = String.format(mapperPath + File.separator + tableInfo.getMapperName() + this.suffixJavaOrKt(), entityName);
                this.outputFile(new File(mapperFile), objectMap, mapper);
            });
        }

        String xmlPath = this.getPathInfo(OutputFile.mapperXml);
        if (StringUtils.isNotBlank(tableInfo.getXmlName()) && StringUtils.isNotBlank(xmlPath)) {
            this.getTemplateFilePath(TemplateConfig::getXml).ifPresent((xml) -> {
                String xmlFile = String.format(xmlPath + File.separator + tableInfo.getXmlName() + ".xml", entityName);
                this.outputFile(new File(xmlFile), objectMap, xml);
            });
        }

    }

    protected void outputService(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String servicePath = this.getPathInfo(OutputFile.service);
        if (StringUtils.isNotBlank(tableInfo.getServiceName()) && StringUtils.isNotBlank(servicePath)) {
            this.getTemplateFilePath(TemplateConfig::getService).ifPresent((service) -> {
                String serviceFile = String.format(servicePath + File.separator + tableInfo.getServiceName() + this.suffixJavaOrKt(), entityName);
                this.outputFile(new File(serviceFile), objectMap, service);
            });
        }

        String serviceImplPath = this.getPathInfo(OutputFile.serviceImpl);
        if (StringUtils.isNotBlank(tableInfo.getServiceImplName()) && StringUtils.isNotBlank(serviceImplPath)) {
            this.getTemplateFilePath(TemplateConfig::getServiceImpl).ifPresent((serviceImpl) -> {
                String implFile = String.format(serviceImplPath + File.separator + tableInfo.getServiceImplName() + this.suffixJavaOrKt(), entityName);
                this.outputFile(new File(implFile), objectMap, serviceImpl);
            });
        }

    }

    protected void outputController(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String controllerPath = this.getPathInfo(OutputFile.controller);
        if (StringUtils.isNotBlank(tableInfo.getControllerName()) && StringUtils.isNotBlank(controllerPath)) {
            this.getTemplateFilePath(TemplateConfig::getController).ifPresent((controller) -> {
                String entityName = tableInfo.getEntityName();
                String controllerFile = String.format(controllerPath + File.separator + tableInfo.getControllerName() + this.suffixJavaOrKt(), entityName);
                this.outputFile(new File(controllerFile), objectMap, controller);
            });
        }

    }

    protected void outputVo(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String voName = tableInfo.getVoName();
        String voPath = this.getPathInfo(OutputFile.vo);
        if (StringUtils.isNotBlank(voName) && StringUtils.isNotBlank(voPath)) {
            this.getTemplateFilePath((template) -> {
                return template.getVo();
            }).ifPresent((entity) -> {
                String voFile = String.format(voPath + File.separator + "%s" + this.suffixJavaOrKt(), voName);
                this.outputFile(new File(voFile), objectMap, entity);
            });
        }

    }

    protected void outputClient(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String clientPath = this.getPathInfo(OutputFile.client);
        if (StringUtils.isNotBlank(tableInfo.getClientName()) && StringUtils.isNotBlank(clientPath)) {
            this.getTemplateFilePath(TemplateConfig::getClient).ifPresent((client) -> {
                String clientName = tableInfo.getClientName();
                String clientFile = String.format(clientPath + File.separator + tableInfo.getClientName() + this.suffixJavaOrKt(), clientName);
                this.outputFile(new File(clientFile), objectMap, client);
            });
        }

    }

    protected void outputFile(@NotNull File file, @NotNull Map<String, Object> objectMap, @NotNull String templatePath) {
        if (this.isCreate(file)) {
            try {
                boolean exist = file.exists();
                if (!exist) {
                    File parentFile = file.getParentFile();
                    FileUtils.forceMkdir(parentFile);
                }

                this.writer(objectMap, templatePath, file);
            } catch (Exception var6) {
                throw new RuntimeException(var6);
            }
        }

    }

    @NotNull
    protected Optional<String> getTemplateFilePath(@NotNull Function<TemplateConfig, String> function) {
        TemplateConfig templateConfig = this.getConfigBuilder().getTemplateConfig();
        String filePath = (String)function.apply(templateConfig);
        return StringUtils.isNotBlank(filePath) ? Optional.of(this.templateFilePath(filePath)) : Optional.empty();
    }

    @Nullable
    protected String getPathInfo(@NotNull OutputFile outputFile) {
        return (String)this.getConfigBuilder().getPathInfo().get(outputFile);
    }

    @NotNull
    public AbstractTemplateEngine batchOutput() {
        try {
            ConfigBuilder config = this.getConfigBuilder();
            List<TableInfo> tableInfoList = config.getTableInfoList();
            tableInfoList.forEach((tableInfo) -> {
                Map<String, Object> objectMap = this.getObjectMap(config, tableInfo);
                Optional.ofNullable(config.getInjectionConfig()).ifPresent((t) -> {
                    t.beforeOutputFile(tableInfo, objectMap);
                    this.outputCustomFile(t.getCustomFile(), tableInfo, objectMap);
                });
                this.outputEntity(tableInfo, objectMap);
                this.outputMapper(tableInfo, objectMap);
                this.outputService(tableInfo, objectMap);
                this.outputController(tableInfo, objectMap);
                this.outputVo(tableInfo, objectMap);
                this.outputClient(tableInfo, objectMap);
            });
            return this;
        } catch (Exception var3) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", var3);
        }
    }

    /** @deprecated */
    @Deprecated
    protected void writerFile(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        if (StringUtils.isNotBlank(templatePath)) {
            this.writer(objectMap, templatePath, outputFile);
        }

    }

    /** @deprecated */
    @Deprecated
    public void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull String outputFile) throws Exception {
    }

    public void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile) throws Exception {
        this.writer(objectMap, templatePath, outputFile.getPath());
        this.logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }

    public void open() {
        String outDir = this.getConfigBuilder().getGlobalConfig().getOutputDir();
        if (!StringUtils.isBlank(outDir) && (new File(outDir)).exists()) {
            if (this.getConfigBuilder().getGlobalConfig().isOpen()) {
                try {
                    RuntimeUtils.openDir(outDir);
                } catch (IOException var3) {
                    this.logger.error(var3.getMessage(), var3);
                }
            }
        } else {
            System.err.println("未找到输出目录：" + outDir);
        }

    }

    @NotNull
    public Map<String, Object> getObjectMap(@NotNull ConfigBuilder config, @NotNull TableInfo tableInfo) {
        StrategyConfig strategyConfig = config.getStrategyConfig();
        Map<String, Object> controllerData = strategyConfig.controller().renderData(tableInfo);
        Map<String, Object> objectMap = new HashMap(controllerData);
        Map<String, Object> mapperData = strategyConfig.mapper().renderData(tableInfo);
        objectMap.putAll(mapperData);
        Map<String, Object> serviceData = strategyConfig.service().renderData(tableInfo);
        objectMap.putAll(serviceData);
        Map<String, Object> entityData = strategyConfig.entity().renderData(tableInfo);
        objectMap.putAll(entityData);
        Map<String, Object> voData = strategyConfig.vo().renderData(tableInfo);
        objectMap.putAll(voData);
        Map<String, Object> clientData = strategyConfig.client().renderData(tableInfo);
        objectMap.putAll(clientData);
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageConfig().getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger", globalConfig.isSwagger());
        objectMap.put("date", globalConfig.getCommentDate());
        String schemaName = "";
        if (strategyConfig.isEnableSchema()) {
            schemaName = config.getDataSourceConfig().getSchemaName();
            if (StringUtils.isNotBlank(schemaName)) {
                schemaName = schemaName + ".";
                tableInfo.setConvert(true);
            }
        }

        objectMap.put("schemaName", schemaName);
        objectMap.put("table", tableInfo);
        objectMap.put("entity", tableInfo.getEntityName());
        return objectMap;
    }

    @NotNull
    public abstract String templateFilePath(@NotNull String filePath);

    /** @deprecated */
    @Deprecated
    protected boolean isCreate(String filePath) {
        return this.isCreate(new File(filePath));
    }

    protected boolean isCreate(@NotNull File file) {
        return !file.exists() || this.getConfigBuilder().getGlobalConfig().isFileOverride();
    }

    protected String suffixJavaOrKt() {
        return this.getConfigBuilder().getGlobalConfig().isKotlin() ? ".kt" : ".java";
    }

    @NotNull
    public ConfigBuilder getConfigBuilder() {
        return this.configBuilder;
    }

    @NotNull
    public AbstractTemplateEngine setConfigBuilder(@NotNull ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }
}