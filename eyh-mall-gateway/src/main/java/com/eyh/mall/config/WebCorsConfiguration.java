package com.eyh.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;

/**
 * @author 李平
 * @Date 2023-1-14
 */
@Configuration
public class WebCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //配置跨域
        //允许任意请求头跨域
        corsConfiguration.addAllowedHeader("*");
        //允许任何请求方式跨域
        corsConfiguration.addAllowedMethod("*");
        //允许任意请求来源跨域
        corsConfiguration.addAllowedOrigin("*");
        //允许携带cookie信息跨域
        corsConfiguration.setAllowCredentials(true);
        //准备响应前的缓存持续的最大时间（以秒为单位）
        corsConfiguration.setMaxAge(3600L);

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }




}
