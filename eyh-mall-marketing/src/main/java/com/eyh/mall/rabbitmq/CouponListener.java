package com.eyh.mall.rabbitmq;

import com.eyh.mall.entity.marketing.CouponMQData;
import com.eyh.mall.service.MerchantCouponService;
import com.eyh.mall.service.UniversalCouponService;
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
    private MerchantCouponService merchantCouponService;

    @Autowired
    private UniversalCouponService universalCouponService;

    /**
     * 优惠券号码更新
     *
     * @param data 数据
     */
    @RabbitListener(queues = CouponConstant.UPDATE_COUPON_NUMBER_QUEUE)
    public void updateCouponNumber(String data){
        CouponMQData couponMQData = couponMQDataJsonUtil.toJavaBean(data, CouponMQData.class);
        if (couponMQData.getType() == 0){
            universalCouponService.updateUniversalCoupon(couponMQData.getCouponId());
        }else{
            merchantCouponService.updateMerchantCoupon(couponMQData.getCouponId());
        }
    }

}
