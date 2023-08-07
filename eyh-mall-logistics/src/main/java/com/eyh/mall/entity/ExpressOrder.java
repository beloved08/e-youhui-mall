package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 快递单表
 *
 * @author 李平
 * @Date 2023-4-7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressOrder {

  private String id;
  private String expressOrderId;
  private String orderNumber;
  private String logisticsCompanyId;
  private String senderName;
  private String senderPhone;
  private String senderAddress;
  private String receiverName;
  private String receiverPhone;
  private String receiverAddress;
  private String createTime;

}
