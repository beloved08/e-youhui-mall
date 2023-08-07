package com.eyh.mall.feign.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-3-5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MallUser {

  private String id;
  private String userId;
  private String phone;
  private String password;
  private String avatar;
  private String email;
  private String openId;
  private String nickName;
  private Integer sex;
  private String address;
  private String unionId;
  private Integer status;
  private String realName;
  private Integer type;
  private String purchaseVipTime;

}
