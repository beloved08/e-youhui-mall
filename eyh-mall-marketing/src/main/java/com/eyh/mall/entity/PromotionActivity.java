package com.eyh.mall.entity;

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
public class PromotionActivity {

  private String id;
  private String promotionActivityId;
  private String promotionActivityName;
  private String promotionActivityCategory;
  private String startTime;
  private String endTime;
  private String createTime;
  private String activityDescribe;

}
