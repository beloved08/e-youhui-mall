package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME CommodityPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-03-20 19:19:20
 * @Description 分页-条件搜索商品数据列表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityPage {

    /**
     * 当前页面
     */
    private Integer currentPage;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 商品名称
     */
    private String commodityName;
    /**
     * 商品状态
     */
    private String commodityStatus;
    /**
     * 两个分类名称
     */
    private String twoClassificationName;
    /**
     * 一个分类名称
     */
    private String oneClassificationName;
    /**
     * 商品类型
     */
    private String commodityType;
    /**
     * 销售模式
     */
    private String salesModel;
    /**
     * 商品推荐
     */
    private String commodityRecommend;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 创建开始时间
     */
    private String createStartTime;
    /**
     * 创建结束时间
     */
    private String createEndTime;
    /**
     * 把架子结束时间
     */
    private String putShelfEndTime;
    /**
     * 把架子开始时间
     */
    private String putShelfStartTime;
    /**
     * 从架子上开始时间
     */
    private String offShelfStartTime;
    /**
     * 从架子上结束时间
     */
    private String offShelfEndTime;

}
