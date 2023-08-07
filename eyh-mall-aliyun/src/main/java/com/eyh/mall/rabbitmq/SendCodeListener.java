package com.eyh.mall.rabbitmq;

import com.eyh.mall.entity.sms.SmsData;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.SMSService;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author 李平
 * @Date 20123-3-7
 */
@Component
public class SendCodeListener {

    @Autowired
    private SMSService smsService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JsonUtil<SmsData> jsonUtil;

    /**
     * 发送短信验证码
     * @param msg
     */
    @RabbitListener(queues = ShopMallUserConstant.MALL_USER_REGISTER_SEND_CODE_QUEUE)
    public void sendCode(String msg){

        SmsData smsData = jsonUtil.toJavaBean(msg, SmsData.class);
        String signName = "E优汇";
        Integer code = (int)((Math.random()*9+1)*100000);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);

        Boolean aBoolean = smsService.sendShortMsg(smsData.getPhone(), signName, smsData.getTemplate(), map);
        if (aBoolean){
            // 发送成功，将此验证码存入Redis，并设置过期时间为5分钟
            redisUtil.set(smsData.getKey(),code,smsData.getTtl());
        }

    }
}
