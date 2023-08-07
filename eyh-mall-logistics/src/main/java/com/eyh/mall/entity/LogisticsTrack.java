package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物流跟踪
 *
 * @author 李平
 * @Date 2023-4-7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsTrack {

  private String id;
  private String logisticsTrackId;
  private String expressOrderId;
  private String logisticsStatus;
  private String logisticsTime;
  private String logisticsDesc;
  private String createTime;
  private String updateTime;

}
