package com.eyh.mall.feign.client;

import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 李平
 * @Date 2023-1-13
 */
@FeignClient(value = "eyh-mall-test", configuration = FeignConfig.class)
public interface TestApiClient {

    /**
     * 测试接口
     * @return String
     */
    @GetMapping("/test/start")
    String test();

}
