package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物流公司
 *
 * @author 李平
 * @Date 2023-4-7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsCompany {

  /**
   * id
   */
  private String id;
  /**
   * 物流公司id
   */
  private String logisticsCompanyId;
  /**
   * 物流公司名称
   */
  private String logisticsCompanyName;
  /**
   * 标志
   */
  private String logo;
  /**
   * 网站
   */
  private String website;
  /**
   * 电话
   */
  private String phone;
  /**
   * 是被禁止
   */
  private Integer isForbidden;

}
