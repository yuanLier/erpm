package edu.cqupt.mislab.erp.commons.config;

import edu.cqupt.mislab.erp.commons.filter.LoginStatusCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chuyunfei
 * @description 
 * @date 12:26 2019/5/3
 **/

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    public FilterRegistrationBean filterRegistrationBean(){

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        //添加登录状态过滤器
        filterRegistrationBean.setFilter(new LoginStatusCheckFilter());
        //设置过滤路径
        filterRegistrationBean.addUrlPatterns("/**");

        //填充初始化参数
        Map<String,String> initParameters = new HashMap<>();

        filterRegistrationBean.setInitParameters(initParameters);

        return filterRegistrationBean;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){

        /*
        添加跨域配置
         */
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }
}
