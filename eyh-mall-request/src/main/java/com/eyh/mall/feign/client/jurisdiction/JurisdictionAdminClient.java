package com.eyh.mall.feign.client.jurisdiction;

import com.eyh.mall.feign.config.FeignConfig;
import com.eyh.mall.feign.entity.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 李平
 * @Date 2023-2-14
 */
@FeignClient(value = "eyh-mall-jurisdiction", configuration = FeignConfig.class)
public interface JurisdictionAdminClient {

    /**
     * 查询用户角色信息
     * @param account
     * @return Role
     */
    @GetMapping("/jurisdiction/selectRole/{account}")
    Role selectRole(@PathVariable(value = "account") String account);
}
