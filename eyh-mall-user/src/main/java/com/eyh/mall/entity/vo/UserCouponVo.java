package com.eyh.mall.entity.vo;

import com.eyh.mall.entity.UserCoupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UserCouponVo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 20:41:34
 * @Description 用户领取优惠券
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponVo extends UserCoupon {

    /**
     * 类型
     */
    private Integer type;

}
