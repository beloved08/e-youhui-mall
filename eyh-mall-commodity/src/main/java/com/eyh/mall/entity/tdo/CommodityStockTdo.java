package com.eyh.mall.entity.tdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME CommodityStockTdo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 11:08:23
 * @Description 商品库存
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityStockTdo {

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
     * 大宗商品股票dto列表
     */
    private List<CommodityStockDto> commodityStockDtoList;
}
