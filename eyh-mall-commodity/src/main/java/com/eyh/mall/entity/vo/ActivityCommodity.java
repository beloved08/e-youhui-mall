package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME ActivityCommodity
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-18 10:21:58
 * @Description 活动商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityCommodity {

    private List<String> commodityId;

}
