package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品
 *
 * @author 李平
 * @date 2023/03/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityComment {

  private String id;
  private String commentId;
  private String commodityId;
  private String userId;
  private String commentTime;
  private String commentContent;

}
