package com.eyh.mall.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME ExcelCommodity
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-03-21 21:36:27
 * @Description 批量导入商品数量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExcelCommodity {

    @ExcelProperty(value = "商品名称", index = 0)
    private String commodityName;

    @ExcelProperty(value = "所属商家", index = 1)
    private String businessName;

    @ExcelProperty(value = "一级分类名称", index = 2)
    private String oneClassificationName;

    @ExcelProperty(value = "二级分类名称", index = 3)
    private String twoClassificationName;

    @ExcelProperty(value = "商品图片", index = 4)
    private String commodityImageUrl;

    @ExcelProperty(value = "商品状态(0:立即上架,1:放入仓库)", index = 5)
    private String commodityStatus;

    @ExcelProperty(value = "商品类型(0:虚拟商品,1:实物商品)", index = 6)
    private String commodityType;

    @ExcelProperty(value = "销售模式(0:零售型,1:批发型)", index = 7)
    private String salesModel;

    @ExcelProperty(value = "零售价/元", index = 8)
    private String retailPrice;

    @ExcelProperty(value = "批发价/元", index = 9)
    private String wholesalePrice;

    @ExcelProperty(value = "商品库存", index = 10)
    private String commodityStock;

    @ExcelProperty(value = "计量单位", index = 11)
    private String meterCompany;

    @ExcelProperty(value = "商品重量", index = 12)
    private String commodityWeight;

    @ExcelProperty(value = "推荐新品(0:推荐,1:不推荐) ", index = 13)
    private String commodityRecommend;

    @ExcelProperty(value = "商品描述", index = 14)
    private String commodityDescribe;

}
