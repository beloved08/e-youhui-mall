package com.eyh.mall.entity.commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME DeductionCommodityData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 14:21:30
 * @Description 扣除商品库存队列数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionCommodityData {

    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 数
     */
    private Integer count;

}
