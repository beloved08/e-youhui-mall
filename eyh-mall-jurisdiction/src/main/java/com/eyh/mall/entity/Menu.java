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
public class Menu {

  private String id;
  private long mid;
  private long pid;
  private String cid;
  private String value;
  private String path;
  private String name;
  private String isLogin;
  private String component;
  private String icon;
  private String label;

  @Override
  public String toString() {
    return "{" +
            "id='" + id + '\'' +
            ", mid=" + mid +
            ", pid=" + pid +
            ", cid='" + cid + '\'' +
            ", value='" + value + '\'' +
            ", path='" + path + '\'' +
            ", name='" + name + '\'' +
            ", isLogin='" + isLogin + '\'' +
            ", component='" + component + '\'' +
            ", icon='" + icon + '\'' +
            ", label='" + label + '\'' +
            '}';
  }
}
