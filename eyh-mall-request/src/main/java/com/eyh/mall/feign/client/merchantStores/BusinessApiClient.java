package com.eyh.mall.feign.client.merchantStores;

import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-3-29
 */
@FeignClient(value = "eyh-mall-merchantStores", configuration = FeignConfig.class)
public interface BusinessApiClient {

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

    /**
     * 得到所有业务
     *
     * @return {@link List}<{@link Business}>
     */
    @GetMapping("/merchantStores/wx/getAllBusiness")
    List<Business> getAllBusiness();
}
