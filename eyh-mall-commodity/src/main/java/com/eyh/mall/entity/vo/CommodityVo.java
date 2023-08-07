package com.eyh.mall.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME CommodityVo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-03-19 20:42:16
 * @Description 商品数据通信封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CommodityVo {

    /**
     * 一个分类名称
     */
    // @ExcelProperty(value = "分类名称", index = 0)
    private String oneClassificationName;
    /**
     * 两个分类名称
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
    private String commodityStatus;
    /**
     * 商品类型
     * 0：虚拟商品，1：实物商品
     */
    private String commodityType;

    /**
     * 计量单位
     */
    private String meterCompany;
    /**
     * 销售模式
     * 0：零售型，1：批发型
     */
    private String salesModel;
    /**
     * 商品重量
     */
    private String commodityWeight;
    /**
     * 商品发布
     * 0：立即发布，1：放入仓库
     */
    private String commodityRelease;
    /**
     * 商品推荐
     * 0：推荐，1：不推荐
     */
    private String commodityRecommend;
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
     * 业务标识
     */
    private String businessName;

    /**
     * 库存
     */
    private String commodityStock;
}
