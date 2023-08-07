package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME NationalPromotionGoEasyData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-04-13 14:00:45
 * @Description 促销人员申请推送消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NationalPromotionGoEasyData {

    /**
     * 电话
     */
    private String phone;
    /**
     * 当前时间
     */
    private String currentTime;

}
