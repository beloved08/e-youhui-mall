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
public class CommodityStock {

  private String id;
  private String stockId;
  /**
   * 库存变动类型（0：入库，1：出库）
   */
  private Integer type;
  private String changeTime;
  private Integer quota;
  private String orderNumber;
  private String commodityId;

}
