package com.eyh.mall.rabbitmq;

/**
 * @Author 李平
 * @NAME CouponConstant
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 21:19:48
 * @Description 优惠券队列
 */
public class CouponConstant {

    /**
     * 息队列数量更新
     */
    public static final String UPDATE_COUPON_NUMBER_QUEUE = "update.coupon.number.queue";

    /**
     * 更新队列使用优惠券
     */
    public static final String UPDATE_COUPON_IS_USED_QUEUE = "update.coupon.is.used.queue";

}
