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
public class Commodity {

  /**
   * id
   */
  private String id;
  /**
   * 商品id
   */
  private String commodityId;
  /**
   * 分类id
   */
  private String oneClassificationId;
  /**
   * 分类id
   */
  private String twoClassificationId;
  /**
   * 商品名称
   */
  private String commodityName;
  /**
   * 零售价格
   */
  private String retailPrice;
  /**
   * 商品图像url
   */
  private String commodityImageUrl;
  /**
   * 商品描述
   */
  private String commodityDescribe;
  /**
   * 商品状态
   * 0：上架，1：下架
   */
  private Integer commodityStatus;
  /**
   * 商品类型
   * 0：虚拟商品，1：实物商品
   */
  private Integer commodityType;

  /**
   * 计量单位
   */
  private String meterCompany;
  /**
   * 销售模式
   * 0：零售型，1：批发型
   */
  private Integer salesModel;
  /**
   * 商品重量
   */
  private String commodityWeight;

  /**
   * 商品推荐
   * 0：推荐，1：不推荐
   */
  private Integer commodityRecommend;
  /**
   * 批发价格
   */
  private String wholesalePrice;
  /**
   * 创建时间
   */
  private String createTime;
  /**
   * 上架时间
   */
  private String putShelfTime;
  /**
   * 下架时间
   */
  private String offShelfTime;
  /**
   * 下架原因
   */
  private String offShelfReason;

  /**
   * 是否是批量导入
   * 0：不是（管理员手动上传），1：是（Excel批量导入）
   */
  private Integer isBatch;

  /**
   * 业务标识
   */
  private String businessId;

  /**
   * 库存数量
   */
  private Integer commodityStock;

  /**
   * 是否删除
   * 是否删除(0：是，1：否-放入回收站，2：正常)
   */
  private Integer isDelete;

}
