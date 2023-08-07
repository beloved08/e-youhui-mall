package com.eyh.mall.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME ExcelClassification
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-03-19 10:17:04
 * @Description Excel批量导入商品分类数据封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExcelClassification {

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称", index = 0)
    private String classificationName;

    /**
     * 分类等级
     * 0：一级分类，1：二级分类
     */
    @ExcelProperty(value = "分类等级", index = 1)
    private String classificationGrade;

    /**
     * 父id
     */
    @ExcelProperty(value = "所属上级", index = 2)
    private String parentName;

    /**
     * 分类图标
     */
    @ExcelProperty(value = "分类图标", index = 3)
    private String classificationIcon;

    /**
     * 分类描述
     */
    @ExcelProperty(value = "分类描述", index = 4)
    private String classificationDescribe;
}
