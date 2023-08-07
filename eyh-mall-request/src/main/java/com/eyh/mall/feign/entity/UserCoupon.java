package com.eyh.mall.feign.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-3-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCoupon {

  /**
   * id
   */
  private String id;
  /**
   * 用户优惠券id
   */
  private String userCouponId;
  /**
   * 用户id
   */
  private String userId;
  /**
   * 优惠券id
   */
  private String couponId;
  /**
   * 画时间
   */
  private String drawTime;
  /**
   * 是使用
   * 0:未使用，1：已使用
   */
  private Integer isUse;
  /**
   * 所属类型（0：通用，1：商家）
   */
  private Integer isType;
  /**
   * 使用时间
   */
  private String useTime;

}
