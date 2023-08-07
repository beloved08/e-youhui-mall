package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME CommodityCommentVo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-11 16:24:22
 * @Description 商品评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityCommentVo {

    private String userId;
    private String commentContent;
    private String commodityId;

}
