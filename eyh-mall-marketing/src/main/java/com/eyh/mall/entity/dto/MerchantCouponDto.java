package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME MerchantCouponDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 16:22:07
 * @Description 商家优惠券
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantCouponDto {

    /**
     * 商家优惠券id
     */
    private String merchantCouponId;
    /**
     * 商家优惠券名称
     */
    private String merchantCouponName;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 折扣金额
     */
    private Integer discountAmount;
    /**
     * 完整可用
     */
    private Integer fullAvailable;
    /**
     * 总量
     */
    private Integer totalQuantity;
    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 状态
     */
    private Integer status;

}
