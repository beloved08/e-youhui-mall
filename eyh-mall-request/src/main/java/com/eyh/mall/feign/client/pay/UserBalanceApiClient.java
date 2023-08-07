package com.eyh.mall.feign.client.pay;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 李平
 * @NAME UserBalanceApiClient
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-01 15:17:00
 * @Description 用户余额接口抽取
 */
@FeignClient(value = "eyh-mall-pay", configuration = FeignConfig.class)
public interface UserBalanceApiClient {

    /**
     * 获取用户平衡数量
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/pay/getUserBalanceNumber/{userId}")
    Result getUserBalanceNumber(@PathVariable String userId);

}
