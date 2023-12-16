package com.employment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @program: StudentEmploymentSystem
 * @ClassName Knife4jConfig
 * @author: c9noo
 * @create: 2023-11-06 17:20
 * @Version 1.0
 * 配置接口文档信息
 **/
@Configuration
@EnableSwagger2WebMvc
@Slf4j
public class Knife4jConfig {
    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docketUser() {
        log.info("生成接口文档");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("学生就业管理系统接口文档")
                .version("1.0")
                .description("学生就业管理系统接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.employment.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
