package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-4-6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder {

  /**
   * id
   */
  private String id;
  /**
   * 用户id
   */
  private String userId;
  /**
   * 订单id
   */
  private String orderId;
  /**
   * 订单状态(0：待付款，1：待发货，2：待收货，3：已完成，4：售后)
   */
  private Integer orderStatus;
  /**
   * 订单号
   */
  private String orderNumber;
  /**
   * 创建时间
   */
  private String createTime;

}
