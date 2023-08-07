package com.eyh.mall.entity.merchantStores;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * @author 李平
 * @Date 2023-3-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessUser {

  /**
   * id
   */
  private String id;
  /**
   * 业务用户id
   */
  private String businessUserId;
  /**
   * 业务用户名
   */
  private String businessUserName;
  /**
   * 业务用户身份证
   */
  private String businessUserIdCard;
  /**
   * 业务用户电话
   */
  private String businessUserPhone;
  /**
   * 业务用户电子邮件
   */
  private String businessUserEmail;
  /**
   * 商家员工性别
   */
  private String businessUserSex;
  /**
   * 业务用户年龄
   */
  private Integer businessUserAge;
  /**
   * 业务标识
   */
  private String businessId;

}
