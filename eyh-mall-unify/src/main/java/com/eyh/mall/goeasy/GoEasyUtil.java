package com.eyh.mall.goeasy;

import com.alibaba.fastjson.JSON;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

/**
 * @Author 李平
 * @NAME GoEasy
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-03-26 09:42:45
 * @Description goeasy消息推送类
 */
public class GoEasyUtil {

    /**
     * 消息推送
     *
     * @param channel 通道
     * @param data    数据
     */
    public static void messagePush(String channel, Object data){
        io.goeasy.GoEasy goEasy = new io.goeasy.GoEasy("https://rest-hz.goeasy.io", "BC-901623b011ed45b688b8fc505db60d26");
        goEasy.publish(channel, JSON.toJSONString(data),new PublishListener(){
            @Override
            public void onSuccess() {
                System.out.println("消息推送成功");
            }
            @Override
            public void onFailed(GoEasyError error) {
                System.err.println("消息推送失败:" + error.getCode() + " , " + error.getContent());
            }
        });
    }
}
