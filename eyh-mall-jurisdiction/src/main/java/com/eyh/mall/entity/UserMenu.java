package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-2-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMenu {

  private String id;
  private String roleId;
  private String menuId;

}
