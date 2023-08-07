package com.eyh.mall.feign.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-4-5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  /**
   * id
   */
  private String id;
  /**
   * 订单id
   */
  private String orderId;
  /**
   * 创建时间
   */
  private String createTime;
  /**
   * 订单状态(0：待付款，1：待发货，2：待收货，3：已完成，4：售后)
   */
  private Integer orderStatus;
  /**
   * 订单号
   */
  private String orderNumber;
  /**
   * 成交时间
   */
  private String strikeBargainTime;
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
  /**
   * 用户id
   */
  private String userId;

}
