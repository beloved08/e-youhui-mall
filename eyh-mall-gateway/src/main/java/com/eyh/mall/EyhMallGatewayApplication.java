package com.eyh.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 李平
 * @Date 2023-1-13
 */
@EnableDiscoveryClient
@SpringBootApplication
public class EyhMallGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyhMallGatewayApplication.class, args);
    }

}
