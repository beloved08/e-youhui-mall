package com.eyh.mall;

import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.client.jurisdiction.JurisdictionAdminClient;
import com.eyh.mall.feign.client.marketing.CouponApiClient;
import com.eyh.mall.feign.client.merchantStores.BusinessApiClient;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 李平
 */
@EnableRabbit
@EnableFeignClients(defaultConfiguration = FeignConfig.class, clients = {
        JurisdictionAdminClient.class,
        CommodityApiClient.class,
        UserApiClient.class,
        BusinessApiClient.class,
        CommodityServiceApiClient.class,
        CouponApiClient.class})
@EnableDiscoveryClient
@SpringBootApplication
public class EyhMallPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyhMallPayApplication.class, args);
    }

}
