package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME CommodityBatch
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-03-21 14:39:56
 * @Description 批量操作数据接收封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityBatch {

    private List<String> batchCommodityId;
    private Integer commodityNumber;

}
