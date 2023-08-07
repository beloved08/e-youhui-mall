package com.eyh.mall.entity.tdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME CommodityResords
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-03-20 20:41:14
 * @Description 商品数据前端表格展示字段属性实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityResords {
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 一级分类名称
     */
    private String oneClassificationName;
    /**
     * 两级分类名称
     */
    private String twoClassificationName;
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
     * 商家名称
     */
    private String businessName;

    /**
     * 库存数量
     */
    private Integer commodityStock;
}
