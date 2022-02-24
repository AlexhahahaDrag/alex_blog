package com.alex.generate;

import com.alex.blog.base.entity.BaseEntity;
import com.alex.blog.base.mapper.SuperMapper;
import com.alex.blog.base.service.SuperService;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Generate {

    public static void main(String[] args) {
        String fileName = "blog";
        String moduleName = "alex_xo";
        String javaFileName = "com/alex/blog/xo";
        // TODO: 2022/2/24 添加多表信息
        String tableName = "t_subject_item";

        List<IFill> list = new ArrayList<>();
        list.add(new Column("create_time", FieldFill.INSERT));
        list.add(new Column("update_time", FieldFill.UPDATE));
        list.add(new Column("operate_time", FieldFill.INSERT_UPDATE));
        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/alex_blog", "root", "mysql")
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());
        String separator = System.getProperty("file.separator");
        String basePath = System.getProperty("user.dir");
        String projectPath = basePath + separator + moduleName + getPath("/src/main", separator);
        String javaPath = projectPath + getPath("java/" + javaFileName, separator);
        Map<OutputFile, String> pathMap = new HashMap<>();
        pathMap.put(OutputFile.mapperXml, projectPath + getPath("resources/mapper", separator) + separator + fileName);
        pathMap.put(OutputFile.service, javaPath + separator + "service" + separator + fileName);
        pathMap.put(OutputFile.serviceImpl, javaPath + separator + "service" + separator + fileName + separator + "impl");
        pathMap.put(OutputFile.mapper, javaPath + separator + "mapper" + separator + fileName);
        pathMap.put(OutputFile.entity, basePath + getPath("alex_common/src/main/java/com/alex/blog/common/entity", separator) + separator + fileName);
        pathMap.put(OutputFile.other, basePath + getPath("alex_common/src/main/java/com/alex/blog/common/vo", separator) + separator + fileName);
        pathMap.put(OutputFile.controller, basePath + getPath("alex_admin/src/main/java/com/alex/blog/admin/restApi", separator) + separator + fileName);
        Map<String, Object> map = new HashMap<>();
        map.put("time", new SimpleDateFormat("HH:mm").format(new Date()));
        FastAutoGenerator.create(dataSourceConfig)
                .globalConfig(builder -> {
                    builder.disableOpenDir()
                            .fileOverride()
                            .outputDir(projectPath + "\\java")
                            .author("alex")
                            .enableSwagger()
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                    ;
                })
                .packageConfig(builder -> {
                    builder.parent("com.alex.blog") // 设置父包名
                            .entity("common.entity." + fileName)
                            .service("xo.service."+ fileName)
                            .serviceImpl("xo.service."+ fileName + ".impl")
                            .mapper("xo.mapper."+ fileName)
                            .other("common.vo." + fileName)
                            .controller("admin.restApi." + fileName)
                            .pathInfo(pathMap); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName)
                            .addTablePrefix("t_")
                            //配置entity
                            .entityBuilder()
                            .superClass(BaseEntity.class)
////                            .disableSerialVersionUID()
                            .enableChainModel()
                            .enableLombok()
//                            .enableRemoveIsPrefix()
                            .enableTableFieldAnnotation()
//                            .enableActiveRecord()
//                            .versionColumnName("version")
//                            .versionPropertyName("version")
                            .logicDeleteColumnName("is_delete")
                            .logicDeletePropertyName("isDelete")
//                            .naming(NamingStrategy.no_change)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .addSuperEntityColumns("id", "creator", "create_time", "updater", "update_time",
                                    "deleter", "delete_time", "`status`", "operator", "operate_time")
////                            .addIgnoreColumns("age")
                            .addTableFills(list)
                            //配置controller
                            .controllerBuilder()
                            .formatFileName("%sRestApi")
                            .enableRestStyle()
//                            //配置service
                            .serviceBuilder()
                            .superServiceClass(SuperService.class)
                            .superServiceImplClass(SuperServiceImpl.class)
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImp")
                            //配置mapper
                            .mapperBuilder()
                            .superClass(SuperMapper.class)
                            .enableMapperAnnotation()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper")
                            .build()
                    ; // 设置过滤表前缀
                })
                .injectionConfig(builder -> {
                    builder.beforeOutputFile((tableInfo, objectMap) -> {
                        System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                        ConfigBuilder config = (ConfigBuilder) objectMap.get("config");
                        Map<String, String> customFile = Objects.requireNonNull(config.getInjectionConfig()).getCustomFile();
                        customFile.put(tableInfo.getEntityName() + "Vo.java", "/templates/vo.java.btl");
                        Map<String, Object> customMap = Objects.requireNonNull(config.getInjectionConfig()).getCustomMap();
                        customMap.put("vo", tableInfo.getEntityName() + "Vo");
                    })
//                            .customMap(Collections.singletonMap("vo11", "aaaVo"))
                            .build();
                })
                .templateEngine(new BeetlTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    private static String getPath(String add, String separator) {
        if (StringUtils.isEmpty(add)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        String[] split = add.split("/");
        for (int i = 0; i < split.length; i++) {
            sb.append(separator).append(split[i]);
        }
        return sb.toString();
    }
}
