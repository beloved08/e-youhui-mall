package com.eyh.mall;

import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.merchantStores.BusinessApiClient;
import com.eyh.mall.feign.client.merchantStores.MerchantStoresApiClient;
import com.eyh.mall.feign.client.pay.OrderApiClient;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 商场商品应用程序
 *
 * @author 李平
 * @date 2023/03/18
 */
@EnableRabbit
@EnableFeignClients(defaultConfiguration = FeignConfig.class, clients = {
        MerchantStoresApiClient.class,
        UserApiClient.class,
        BusinessApiClient.class,
        OrderApiClient.class,
        CommodityApiClient.class
})
@EnableDiscoveryClient
@SpringBootApplication
public class EyhMallCommodityApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyhMallCommodityApplication.class, args);
    }

}
