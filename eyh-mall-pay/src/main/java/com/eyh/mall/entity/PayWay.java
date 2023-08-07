package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-4-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayWay {

  /**
   * id
   */
  private String id;
  /**
   * 支付方式id
   */
  private String payWayId;
  /**
   * 支付方式名称
   */
  private String payWayName;
  /**
   * 支付方式desc
   */
  private String payWayDesc;

}
