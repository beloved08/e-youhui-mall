package com.eyh.mall.entity.tdo;

import com.eyh.mall.entity.CommodityStock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME CommodityStockDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 11:06:59
 * @Description 商品库存
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityStockDto extends CommodityStock {

    private String commodityName;

}
