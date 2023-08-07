package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-4-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentActivity {

  /**
   * id
   */
  private String id;
  /**
   * 推广活动id
   */
  private String promotionActivityId;
  /**
   * 更新时间
   */
  private String insertTime;

  /**
   * 当前活动id
   */
  private String currentActivityId;

}
