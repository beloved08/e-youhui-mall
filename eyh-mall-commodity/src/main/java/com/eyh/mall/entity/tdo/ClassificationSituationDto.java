package com.eyh.mall.entity.tdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME ClassificationSituationDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-15 14:35:02
 * @Description 分类情况
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationSituationDto {

    /**
     * 名字
     */
    private String name;
    /**
     * 数
     */
    private Long count;
}
