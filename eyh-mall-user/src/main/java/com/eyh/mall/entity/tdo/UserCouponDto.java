package com.eyh.mall.entity.tdo;

import com.eyh.mall.entity.UserCoupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UserCouponDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-03-30 10:14:58
 * @Description 用户优惠券
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponDto extends UserCoupon {

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 是到期
     */
    private Integer isExpire;
    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 折扣金额
     */
    private Integer discountAmount;
    /**
     * 完整可用
     */
    private Integer fullAvailable;
    /**
     * 业务标识
     */
    private String businessId;

}
