package com.eyh.mall.entity.dto;

import com.eyh.mall.entity.UserBalanceChange;
import com.eyh.mall.entity.UserIntegralChange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME UserBalanceDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-01 10:07:09
 * @Description 用户余额
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceIntegralDto {

    /**
     * 积分id
     */
    private String integralId;
    /**
     * 可用积分
     */
    private double availableIntegral;

    /**
     * 平衡id
     */
    private String balanceId;
    /**
     * 用户id
     */
    private String userName;
    /**
     * 可用余额
     */
    private double availableBalance;

    /**
     * 用户平衡改变充电列表
     */
    private List<UserBalanceChange> userBalanceChangeRechargeList;
    /**
     * 用户平衡变化演绎列表
     */
    private List<UserBalanceChange> userBalanceChangeDeductionList;

    /**
     * 用户积分改变充电列表
     */
    private List<UserIntegralChange> userIntegralChangeRechargeList;
    /**
     * 用户积分变化演绎列表
     */
    private List<UserIntegralChange> userIntegralChangeDeductionList;
}
