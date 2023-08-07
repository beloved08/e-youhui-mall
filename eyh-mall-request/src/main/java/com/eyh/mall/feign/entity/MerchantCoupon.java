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
public class MerchantCoupon {

  /**
   * id
   */
  private String id;
  /**
   * 商家优惠券id
   */
  private String merchantCouponId;
  /**
   * 商家优惠券名称
   */
  private String merchantCouponName;
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
  /**
   * 业务标识
   */
  private String businessId;

}
