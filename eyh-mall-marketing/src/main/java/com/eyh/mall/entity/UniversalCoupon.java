package com.eyh.mall.entity;

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
public class UniversalCoupon {

  /**
   * id
   */
  private String id;
  /**
   * 通用券id
   */
  private String universalCouponId;
  /**
   * 普遍优惠券名称
   */
  private String universalCouponName;
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

}
