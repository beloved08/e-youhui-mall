package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME PromotionPeoplePage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-14 13:20:06
 * @Description 促销人员分页
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionPeoplePage {

    /**
     * 当前页面
     */
    private Integer currentPage;
    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 促进人电话
     */
    private String promotionPeoplePhone;

    /**
     * 验证
     * 审核状态(0：待审核，1：审核通过，2：审核不通过)
     * -1 全部
     */
    private Integer verify;
}
