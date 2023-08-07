package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

  /**
   * id
   */
  private String id;
  /**
   * 购物车id
   */
  private String cartId;
  /**
   * 用户id
   */
  private String userId;
  /**
   * 商品id
   */
  private String commodityId;
  /**
   * 添加时间
   */
  private String addTime;
  /**
   * 业务标识
   */
  private String businessId;

}
