package com.eyh.mall.entity.tdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME CommodityCommentDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-15 16:11:10
 * @Description 商品评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityCommentDto {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 头像url
     */
    private String avatarUrl;
    /**
     * 时间
     */
    private String time;
    /**
     * 评论内容
     */
    private String commentContent;

}
