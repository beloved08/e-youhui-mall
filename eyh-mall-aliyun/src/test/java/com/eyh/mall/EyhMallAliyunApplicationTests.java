package com.eyh.mall;

import com.alibaba.fastjson.JSON;
import com.eyh.mall.rabbitmq.CommodityConstant;
import com.eyh.mall.service.OSSClientService;
import com.eyh.mall.service.SMSService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
class EyhMallAliyunApplicationTests {

    @Autowired
    private SMSService smsService;

    @Autowired
    private OSSClientService ossClientService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() throws ExecutionException, InterruptedException {
        ossClientService.deleteDirectoryFile("manage/classification_icon/Aziqun");
    }

}
