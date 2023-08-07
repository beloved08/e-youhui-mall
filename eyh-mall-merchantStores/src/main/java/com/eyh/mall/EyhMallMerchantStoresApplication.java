package com.eyh.mall;

import com.alibaba.fastjson.JSON;
import com.eyh.mall.entity.MerchantSettlementChannelData;
import com.eyh.mall.feign.client.merchantStores.MerchantStoresApiClient;
import com.eyh.mall.feign.config.FeignConfig;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.util.TimeUtil;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 李平
 * @Date 2023-3-10
 */
@EnableRabbit
@EnableFeignClients(defaultConfiguration = FeignConfig.class, clients = MerchantStoresApiClient.class)
@EnableDiscoveryClient
@SpringBootApplication
public class EyhMallMerchantStoresApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyhMallMerchantStoresApplication.class, args);
    }

}
