package com.eyh.mall;

import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.client.jurisdiction.JurisdictionAdminClient;
import com.eyh.mall.feign.client.marketing.CouponApiClient;
import com.eyh.mall.feign.client.pay.UserBalanceApiClient;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.UUID;

/**
 * @author 李平
 */
@EnableRabbit
@EnableScheduling
@EnableFeignClients(defaultConfiguration = FeignConfig.class, clients = {
        JurisdictionAdminClient.class,
        CommodityApiClient.class,
        UserApiClient.class,
        UserBalanceApiClient.class,
        CommodityServiceApiClient.class,
        CouponApiClient.class})
@EnableDiscoveryClient
@SpringBootApplication
public class EyhMallLogisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyhMallLogisticsApplication.class, args);
    }

}
