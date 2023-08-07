package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME OrderVo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 11:10:45
 * @Description 创建订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 订单状态(0：待付款，1：待发货，2：待收货，3：已完成，4：售后)
     */
    private Integer orderStatus;

    /**
     * 运输费用
     */
    private String transportationExpenses;
    /**
     * 总价格
     */
    private String totalPrice;
    /**
     * 会员折扣
     */
    private String memberDiscount;
    /**
     * 地址标识
     */
    private String addressId;
    /**
     * 普遍优惠券
     */
    private String universalCoupon;
    /**
     * 商家优惠券
     */
    private String merchantCoupon;
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 业务标识
     */
    private String businessId;
    /**
     * 采购量
     */
    private Integer purchaseQuantity;
    /**
     * 订单话
     */
    private String orderRemarks;
}
