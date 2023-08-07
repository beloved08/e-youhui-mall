package com.eyh.mall.feign.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类
 *
 * @author 李平
 * @date 2023/03/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classification {

  /**
   * 分类id
   */
  private String classificationId;

  /**
   * id
   */
  private String id;

  /**
   * 分类名称
   */
  private String classificationName;

  /**
   * 分类描述
   */
  private String classificationDescribe;

  /**
   * 分类图标
   */
  private String classificationIcon;

  /**
   * 分类等级
   * 0：一级分类，1：二级分类
   */
  private Integer classificationGrade;

  /**
   * 父id
   */
  private String parentId;

  /**
   * 是否是批量导入
   * 0：不是（管理员手动上传），1：是（Excel批量导入）
   */
  private Integer isBatch;

}
