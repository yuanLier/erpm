package edu.cqupt.mislab.erp.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author： chuyunfei
 * @date：2019/3/15
 */
@Configuration
public class AsynchronousConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){

        //线程池对象
        return new ThreadPoolExecutor(
                0,
                Integer.MAX_VALUE,
                60,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>()
        );
    }
}
