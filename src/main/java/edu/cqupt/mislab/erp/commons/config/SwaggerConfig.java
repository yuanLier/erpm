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

/**
 * @author chuyunfei
 * @description 
 * @date 12:26 2019/5/3
 **/

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * @author chuyunfei
     * @description 用户管理API文档
     * @date 12:10 2019/5/3
     **/
    @Bean
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

    /**
     * @author chuyunfei
     * @description 比赛管理API文档
     * @date 12:10 2019/5/3
     **/
    @Bean
    public Docket gameManageApi(){

        String description = "比赛管理的API文档，主要记录的是比赛浏览、比赛创建、历史信息浏览等比赛管理服务";

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(description))
                .groupName("gama_manage")
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.cqupt.mislab.erp.game.manage.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @author chuyunfei
     * @description 比赛运行API文档
     * @date 12:10 2019/5/3
     **/
    @Bean
    public Docket gameCompeteApi(){

        String description = "比赛操作的API文档，所有的比赛操作";

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(description))
                .groupName("gama_compete")
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.cqupt.mislab.erp.game.compete"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(final String description) {
        return new ApiInfoBuilder()
                .title("ERP电子沙盘模拟系统在线API文档")
                .description(description)
                .version("2.0")
                .build();
    }
}
