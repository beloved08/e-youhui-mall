package com.eyh.mall.feign.client.commodity;

import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author 李平
 * @NAME CommodityApiClient
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-18 11:26:46
 * @Description 商品服务feign api接口
 */
@FeignClient(value = "eyh-mall-merchantStores", configuration = FeignConfig.class)
public interface CommodityApiClient {

    /**
     * 获得业务
     *
     * @param businessShopName 企业字号
     * @return {@link Business}
     */
    @GetMapping("/merchantStores/wx/getBusinessByName")
    Business getBusiness(@RequestParam(value = "businessShopName",required = true) String businessShopName);


    /**
     * 通过id获取业务
     *
     * @param businessId 业务标识
     * @return {@link Business}
     */
    @GetMapping("/merchantStores/wx/getBusinessById")
    Business getBusinessById(@RequestParam(value = "businessId",required = true) String businessId);

}
