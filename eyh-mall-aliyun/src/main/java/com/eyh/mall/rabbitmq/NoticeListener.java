package com.eyh.mall.rabbitmq;

import com.eyh.mall.entity.sms.SmsData;
import com.eyh.mall.service.SMSService;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME NoticeListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-03-16 10:22:44
 * @Description 阿里云发送通知短信监听消费类
 */
@Component
public class NoticeListener {

    @Autowired
    private SMSService smsService;

    @Autowired
    private JsonUtil<SmsData> jsonUtil;

    /**
     * 发送通知
     *
     * @param msg 消息
     */
    @RabbitListener(queues = ShopMallUserConstant.VERIFY_BUSINESS_QUEUE)
    public void sendNotice(String msg){
        SmsData smsData = jsonUtil.toJavaBean(msg, SmsData.class);
        try{
            smsService.sendNoticeMsg(smsData.getPhone(),smsData.getTemplate());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发送推广人通过通知
     *
     * @param msg 味精
     */
    @RabbitListener(queues = NationalPromotionConstant.PROMOTION_PEOPLE_PASS_QUEUE)
    public void sendPromotionPeoplePassNotice(String msg){
        SmsData smsData = jsonUtil.toJavaBean(msg, SmsData.class);
        try{
            smsService.sendNoticeMsg(smsData.getPhone(),smsData.getTemplate());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 签收通知
     *
     * @param msg 味精
     */
    @RabbitListener(queues = LogisticsConstant.EXPRESS_DELIVERY_STATION_SIGNATURE_QUEUE)
    public void sendNoticeShip(String msg){
        SmsData smsData = jsonUtil.toJavaBean(msg, SmsData.class);
        try{
            smsService.sendNoticeMsg(smsData.getPhone(),smsData.getTemplate());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
