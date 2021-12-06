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
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Generate {

    public static void main(String[] args) {

        List<IFill> list = new ArrayList<>();
        list.add(new Column("create_time", FieldFill.INSERT));
        list.add(new Column("update_time", FieldFill.INSERT_UPDATE));
        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/alex_blog", "root", "mysql")
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());

        FastAutoGenerator.create(dataSourceConfig)
                .globalConfig(builder -> {
                    builder.fileOverride()
                            .outputDir("F:\\alex\\alex_blog\\alex_xo\\src\\main\\java")
                            .author("alex")
//                            .enableKotlin()
                            .enableSwagger()
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                    ;
                })
                .packageConfig(builder -> {
                    builder.parent("com.alex.blog.xo") // 设置父包名
                            .moduleName("generate") // 设置父包模块名
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .controller("controller")
                            .other("other")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "F:\\alex\\alex_blog\\alex_xo\\src\\main\\java\\com\\alex\\blog\\xo\\generate\\mapper\\mapper")
                            ); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_sys_params")
                            .addTablePrefix("t_")
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
                            .addSuperEntityColumns("id", "creator", "create_time", "updater", "update_time", "deleter", "delete_time", "status")
////                            .addIgnoreColumns("age")
                            .addTableFills(list)
////                            .idType(IdType.AUTO)
//                            .formatFileName("%sEntity")
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
//                            .formatMapperFileName("%Mapper")
//                            .formatXmlFileName("%sXml")
                            .build()
                    ; // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
