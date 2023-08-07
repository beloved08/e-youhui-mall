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
public class UserBalance {

  /**
   * id
   */
  private String id;
  /**
   * 平衡id
   */
  private String balanceId;
  /**
   * 用户id
   */
  private String userId;
  /**
   * 可用余额
   */
  private double availableBalance;

}
