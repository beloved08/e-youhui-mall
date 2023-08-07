package com.eyh.mall;

import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.client.merchantStores.BusinessApiClient;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * eyh商场营销应用程序
 *
 * @author 李平
 * @date 2023/03/29
 */
@EnableRabbit
@EnableFeignClients(defaultConfiguration = FeignConfig.class, clients = {BusinessApiClient.class, UserApiClient.class, CommodityServiceApiClient.class})
@EnableDiscoveryClient
@SpringBootApplication
public class EyhMallMarketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyhMallMarketingApplication.class, args);
    }

}
