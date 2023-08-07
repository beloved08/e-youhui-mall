package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-3-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIntegralChange {

  /**
   * id
   */
  private String id;
  /**
   * 积分变化id
   */
  private String integralChangeId;
  /**
   * 改变时间
   */
  private String changeTime;
  /**
   * 变化类型
   */
  private Integer changeType;
  /**
   * 改变配额
   */
  private double changeQuota;
  /**
   * 用户id
   */
  private String userId;
  /**
   * 订单号
   */
  private String orderNumber;

}
