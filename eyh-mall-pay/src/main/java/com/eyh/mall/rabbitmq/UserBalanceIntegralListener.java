package com.eyh.mall.rabbitmq;

import com.eyh.mall.entity.UserIntegral;
import com.eyh.mall.entity.marketing.CouponMQData;
import com.eyh.mall.entity.user.UserBalanceIntegralMqData;
import com.eyh.mall.service.UserBalanceService;
import com.eyh.mall.service.UserIntegralService;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME UserBalanceIntegralListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-01 15:49:56
 * @Description 用户余额积分监听
 */
@Component
public class UserBalanceIntegralListener {

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private UserIntegralService userIntegralService;

    @Autowired
    private JsonUtil<UserBalanceIntegralMqData> userBalanceIntegralMqDataJsonUtil;

    /**
     * 更新用户平衡积分侦听器
     *
     * @param data 数据
     */
    @RabbitListener(queues = UserBalanceIntegralConstant.USER_BALANCE_INTEGRAL_QUEUE)
    public void updateUserBalanceIntegralListener(String data){
        //解析数据
        UserBalanceIntegralMqData userBalanceIntegralMqData = userBalanceIntegralMqDataJsonUtil.toJavaBean(data, UserBalanceIntegralMqData.class);
        //扣减余额
        userBalanceService.userBalanceDeduction(userBalanceIntegralMqData.getUserId(),userBalanceIntegralMqData.getUserBalanceDeduction());
        //增加积分
        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserId(userBalanceIntegralMqData.getUserId());
        userIntegral.setAvailableIntegral(userBalanceIntegralMqData.getUserIntegralDeduction());
        userIntegralService.userIntegralRecharge(userIntegral);
    }

}
