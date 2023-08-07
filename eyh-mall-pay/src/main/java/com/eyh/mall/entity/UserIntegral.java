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
public class UserIntegral {

  /**
   * id
   */
  private String id;
  /**
   * 积分id
   */
  private String integralId;
  /**
   * 可用积分
   */
  private double availableIntegral;
  /**
   * 用户id
   */
  private String userId;

}
