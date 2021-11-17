package com.alex.blog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 *description:  swagger配置类
 *author:       alex
 *createDate:   2021/6/6 15:17
 *version:      1.0.0
 */
@Configuration
@EnableSwagger2WebMvc
@Profile({"test", "dev"})
public class SwaggerConfig {

    @Bean
    public Docket buildDocket() {
        //添加head参数配置start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.alex.blog.admin.restApi"))
                .paths(PathSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);//注意这里
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("alex blog document")
                .description("ha ha ha ! be happy")
                .termsOfServiceUrl("www.baidu.com")
                .version("1.0.0-SNAPSHOT")
                .build();
    }
}
