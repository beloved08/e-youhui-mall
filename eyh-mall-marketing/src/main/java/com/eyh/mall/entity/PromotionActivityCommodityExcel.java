package com.eyh.mall.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME PromotionActivityCommodityExcel
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 09:54:34
 * @Description 促销活动商品excel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PromotionActivityCommodityExcel {

    @ExcelProperty(value = "商品名称", index = 0)
    private String commodityName;

    @ExcelProperty(value = "商品图片", index = 1)
    private String commodityImageUrl;

    @ExcelProperty(value = "商品类型(0:虚拟商品,1:实物商品)", index = 2)
    private String commodityType;

    @ExcelProperty(value = "零售价/元", index = 3)
    private String retailPrice;

    @ExcelProperty(value = "商品库存", index = 4)
    private String commodityStock;

    @ExcelProperty(value = "计量单位", index = 5)
    private String meterCompany;

    @ExcelProperty(value = "商品重量", index = 6)
    private String commodityWeight;

    @ExcelProperty(value = "商品描述", index = 7)
    private String commodityDescribe;

}
