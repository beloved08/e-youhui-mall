package com.eyh.mall.rabbitmq;

import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.mapper.MallUserMapper;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME ShopMallUserListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-15 08:37:33
 * @Description 微信登录用户信息入库消费类
 */
@Component
public class ShopMallUserListener {

    @Autowired
    private MallUserMapper mallUserMapper;

    @Autowired
    private JsonUtil<MallUser> jsonUtil;

    /**
     * 接收微信登录用户信息并存入数据库中
     * @param msg
     */
    @RabbitListener(queues = ShopMallUserConstant.SAVE_MALL_USER_QUEUE)
    public void saveMallUserListener(String msg){
        MallUser mallUser = jsonUtil.toJavaBean(msg, MallUser.class);
        mallUserMapper.insert(mallUser);


    }

}
