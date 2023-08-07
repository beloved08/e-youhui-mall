package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-3-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollect {

  /**
   * id
   */
  private String id;
  /**
   * 收集id
   */
  private String collectId;
  /**
   * 用户id
   */
  private String userId;
  /**
   * 商品id
   */
  private String commodityId;
  /**
   * 收集时间
   */
  private String collectTime;
  /**
   * 业务标识
   */
  private String businessId;
  /**
   * 是类型
   */
  private Integer isType;

}
