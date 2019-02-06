package edu.cqupt.mislab.erp.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean//用户管理API文档
    public Docket userApi() {

        String description = "用户管理的API文档，主要记录的是学生、教师、管理员的登录、注册、个人信息修改服务";

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(description))
                .groupName("user")
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.cqupt.mislab.erp.user.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(String description) {
        return new ApiInfoBuilder()
                .title("ERP电子沙盘模拟系统在线API文档")
                .description(description)
                .version("2.0")
                .build();
    }
}
