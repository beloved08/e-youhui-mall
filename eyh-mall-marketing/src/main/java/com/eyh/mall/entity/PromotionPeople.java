package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-4-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionPeople {

  /**
   * id
   */
  private String id;
  /**
   * 促进人们id
   */
  private String promotionPeopleId;
  /**
   * 促进人电话
   */
  private String promotionPeoplePhone;
  /**
   * 申请时间
   */
  private String applyTime;
  /**
   * 验证
   * 审核状态(0：待审核，1：审核通过，2：审核不通过)
   */
  private Integer verify;
  /**
   * 代码
   */
  private String code;
  /**
   * 用户id
   */
  private String userId;

}
