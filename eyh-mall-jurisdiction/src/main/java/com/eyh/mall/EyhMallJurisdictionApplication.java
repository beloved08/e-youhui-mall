package com.eyh.mall;

import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author 李平
 * @Date 2023-1-19
 */
@EnableRabbit
@EnableFeignClients(defaultConfiguration = FeignConfig.class, clients = UserApiClient.class)
@EnableDiscoveryClient
@SpringBootApplication
public class EyhMallJurisdictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyhMallJurisdictionApplication.class, args);
    }

    // @Bean
    // public MessageConverter messageConverter(){
    //     return new Jackson2JsonMessageConverter();
    // }

}
