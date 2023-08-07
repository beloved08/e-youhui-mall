package com.eyh.mall.rabbitmq;

import com.eyh.mall.entity.marketing.CouponMQData;
import com.eyh.mall.service.UserCouponService;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME CouponListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 21:23:08
 * @Description 优惠券RabbitMQ消息队列监听类
 */
@Component
public class CouponListener {

    @Autowired
    private JsonUtil<CouponMQData> couponMQDataJsonUtil;

    @Autowired
    private UserCouponService userCouponService;

    /**
     * 更新优惠券是状态
     *
     * @param data 数据
     */
    @RabbitListener(queues = CouponConstant.UPDATE_COUPON_IS_USED_QUEUE)
    public void updateCouponIsStatus(String data){
        CouponMQData couponMQData = couponMQDataJsonUtil.toJavaBean(data, CouponMQData.class);
        userCouponService.updateUserCouponIsUse(couponMQData.getCouponId());
    }

}
