package com.eyh.mall.entity.tdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UserPurchaseVip
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-01 16:03:42
 * @Description 用户购买会员消息通知
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPurchaseVip {

    private String userName;
    private String currentTime;

}
