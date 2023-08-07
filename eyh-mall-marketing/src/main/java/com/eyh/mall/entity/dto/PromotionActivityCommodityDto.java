package com.eyh.mall.entity.dto;

import com.eyh.mall.feign.entity.Commodity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME PromotionActivityCommodityDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-18 11:17:56
 * @Description 促销活动商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionActivityCommodityDto {

    /**
     * 当前页面
     */
    private long currentPage;
    /**
     * 页面大小
     */
    private long pageSize;
    /**
     * 大小
     */
    private long size;
    /**
     * 总计
     */
    private long total;

    /**
     * 商品列表
     */
    private List<Commodity> commodityList;
}
