package com.eyh.mall;

import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.client.jurisdiction.JurisdictionAdminClient;
import com.eyh.mall.feign.client.marketing.CouponApiClient;
import com.eyh.mall.feign.client.pay.UserBalanceApiClient;
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
 * @Date 2023-1-13
 */
@EnableRabbit
@EnableFeignClients(defaultConfiguration = FeignConfig.class, clients = {
        JurisdictionAdminClient.class,
        CommodityApiClient.class,
        UserBalanceApiClient.class,
        CommodityServiceApiClient.class,
        CouponApiClient.class})
@EnableDiscoveryClient
@SpringBootApplication
public class EyhMallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyhMallUserApplication.class, args);
    }

    // @Bean
    // public MessageConverter messageConverter(){
    //     return new Jackson2JsonMessageConverter();
    // }

}
