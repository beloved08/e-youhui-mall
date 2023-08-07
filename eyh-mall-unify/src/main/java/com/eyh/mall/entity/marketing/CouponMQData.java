package com.eyh.mall.entity.marketing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME CouponMQData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 21:28:03
 * @Description 优惠券
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponMQData {

    /**
     * 优惠券id
     */
    private String couponId;
    /**
     * 类型
     * 0:通用优惠券，1：商家优惠券
     */
    private Integer type;
}
