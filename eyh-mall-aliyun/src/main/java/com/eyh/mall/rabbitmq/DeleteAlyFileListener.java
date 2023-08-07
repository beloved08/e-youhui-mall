package com.eyh.mall.rabbitmq;

import com.eyh.mall.service.OSSClientService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME DeleteAlyFileListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-18 23:54:20
 * @Description 删除阿里云文件的监听类
 */
// @Component
public class DeleteAlyFileListener {

    @Autowired
    private OSSClientService ossClientService;

    /**
     * 删除阿里云文件
     *
     * @param msg 消息
     */
    @RabbitListener(queues = CommodityConstant.DELETE_ALY_FILE_QUEUE)
    public void deleteAlyFile(String msg){
        System.out.println("------------" + msg + "--------------");
        String s = ossClientService.deleteDirectoryFile(msg);

        System.out.println(s);
    }
}
