package com.eyh.mall.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李平
 * @Date 2023-1-18
 */
@Configuration
public class FeignConfig {

    /**
     * 日志输出打印级别：BASIC
     * @return Logger.Level
     */
    @Bean
    public Logger.Level loggerLevel(){
        return Logger.Level.BASIC;
    }

}
