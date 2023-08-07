package com.eyh.mall;

import com.alibaba.fastjson.JSON;
import com.eyh.mall.mapper.ClassificationMapper;
import com.eyh.mall.rabbitmq.CommodityConstant;
import com.eyh.mall.util.HanyuPinyinUtil;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class EyhMallCommodityApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ClassificationMapper classificationMapper;

    @Test
    void contextLoads() {

        String msg = "manage/classification_icon/" + HanyuPinyinUtil.toHanyuPinyin("火锅");
        rabbitTemplate.convertAndSend(CommodityConstant.DELETE_ALY_FILE_QUEUE, JSON.toJSONString(msg));
    }

    @Test
    public void get(){
        System.out.println(UUID.randomUUID().toString());
    }


}
