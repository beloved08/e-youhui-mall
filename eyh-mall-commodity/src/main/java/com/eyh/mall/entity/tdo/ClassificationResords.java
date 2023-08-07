package com.eyh.mall.entity.tdo;

import com.eyh.mall.entity.Classification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME ClassificationResords
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-18 19:07:09
 * @Description 商品分类数据二级分类数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationResords {

    /**
     * 分类id
     */
    private String classificationId;

    /**
     * 分类名称
     */
    private String classificationName;

    /**
     * 分类描述
     */
    private String classificationDescribe;
    /**
     * 分类等级
     */
    private Integer classificationGrade;

    /**
     * 分类列表
     */
    private List<Classification> children;

}
