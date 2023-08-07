package com.eyh.mall;

import com.alibaba.fastjson.JSON;
import com.eyh.mall.entity.MerchantSettlementChannelData;
import com.eyh.mall.entity.sms.SmsData;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.rabbitmq.ShopMallUserConstant;
import com.eyh.mall.util.TimeUtil;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EyhMallMerchantStoresApplicationTests {

    @Test
    void contextLoads() {

    }

}
