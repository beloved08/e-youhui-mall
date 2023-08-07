package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME MerchantStorePage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-15 16:07:25
 * @Description 商家店铺分页查询封装实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantStorePage {

    private String businessName;
    private String shopName;
    private Integer businessStatus;
    private Integer verifyStatus;
    private Integer currentPage;
    private Integer pageSize;

}
