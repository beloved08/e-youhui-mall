package com.eyh.mall.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author 李平
 * @Date 2023-3-6
 */
public interface SMSService {

    /**
     * 发送短信验证码
     * @param phone
     * @param signName
     * @param templateCode
     * @param code
     * @return Boolean
     */
    Boolean sendShortMsg(String phone,String signName, String templateCode, Map<String, Object> code);

    /**
     * 发送通知味精
     *
     * @param phone    电话
     * @param template 模板
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    void sendNoticeMsg(String phone,String template) throws ExecutionException, InterruptedException;

}
