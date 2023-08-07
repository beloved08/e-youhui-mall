package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.eyh.mall.service.SMSService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author 李平
 * @Date 2023-3-6
 */
@Service
public class SMSServiceImpl implements SMSService {

    /**
     * 阿里云账号AccessKey
     */
    private static final String  ACCESS_KEY_ID = "ACCESS_KEY_ID";
    /**
     * 阿里云账号AccessKeySecret
     */
    private static final String ACCESS_KEY_SECRET = "ACCESS_KEY_SECRET";

    /**
     * 发送短味精
     *
     * @param phone        电话
     * @param signName     签名字
     * @param templateCode 模板代码
     * @param code         代码
     * @return {@link Boolean}
     */
    @Override
    public Boolean sendShortMsg(String phone,String signName, String templateCode, Map<String, Object> code) {
        // 连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-shenzhen", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        // 构建请求
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        // 自定义的参数,手机号,验证码,签名,模板
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(code));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            // 构建成功返回true
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        // 构建失败返回false
        return false;
    }

    /**
     * 发送通知味精
     *
     * @param phone    电话
     * @param template 模板
     */
    @Override
    public void sendNoticeMsg(String phone,String template) {
        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(ACCESS_KEY_ID)
                .accessKeySecret(ACCESS_KEY_SECRET)
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                .region("cn-shenzhen")
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();

        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(phone)
                .signName("E优汇")
                .templateCode(template)
                .build();

        // Asynchronously get the return value of the API request
        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        // Synchronously get the return value of the API request
        try {
            SendSmsResponse resp = response.get();
            System.out.println(new Gson().toJson(resp));
        }catch (Exception e){
            e.printStackTrace();
        }

        client.close();
    }

}
