package com.eyh.mall.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UserBalanceIntegralMqData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-01 15:30:37
 * @Description 用户扣减余额积分数据传输封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceIntegralMqData {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户余额扣除
     */
    private double userBalanceDeduction;
    /**
     * 用户积分扣除
     */
    private double userIntegralDeduction;

}
