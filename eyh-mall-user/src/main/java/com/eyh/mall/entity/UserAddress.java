package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-3-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {

  /**
   * id
   */
  private String id;
  /**
   * 地址标识
   */
  private String addressId;
  /**
   * 经度
   */
  private String longitude;
  /**
   * 纬度
   */
  private String latitude;
  /**
   * 收货人
   */
  private String consignee;
  /**
   * 电话
   */
  private String phone;
  /**
   * 用户id
   */
  private String userId;
  /**
   * 是默认0:默认，1：不默认
   */
  private Integer isDefault;
  /**
   * 位置
   */
  private String location;
  /**
   * 详细地址
   */
  private String detailedAddress;

}
