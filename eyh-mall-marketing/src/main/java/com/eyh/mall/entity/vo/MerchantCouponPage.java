package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME MerchantCouponPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 16:14:44
 * @Description 商家优惠券
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantCouponPage {

    /**
     * 当前页面
     */
    private Integer currentPage;
    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 商家优惠券名称
     */
    private String merchantCouponName;

    /**
     * 状态
     * 0：使用中，1：未开始，2：已结束，3：所有
     */
    private Integer status;
    /**
     * 业务名称
     */
    private String businessName;
}
