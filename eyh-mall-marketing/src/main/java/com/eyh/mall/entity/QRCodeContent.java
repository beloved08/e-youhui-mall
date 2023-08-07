package com.eyh.mall.entity;

import com.eyh.mall.feign.entity.MallUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME QRCodeContent
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-18 16:14:34
 * @Description 分享二维码内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeContent {

    /**
     * 商城用户
     */
    private MallUser mallUser;
    /**
     * 推广活动
     */
    private PromotionActivity promotionActivity;

    /**
     * 推广人
     */
    private PromotionPeople promotionPeople;
}
