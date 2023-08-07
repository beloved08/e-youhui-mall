package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.config.JWTConfig;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.redis.SendCode;
import com.eyh.mall.entity.sms.SmsData;
import com.eyh.mall.entity.tdo.WxAccountPwdRegister;
import com.eyh.mall.entity.tdo.WxLoginTdo;
import com.eyh.mall.entity.vo.WxLoginUserVo;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.mapper.MallUserMapper;
import com.eyh.mall.rabbitmq.ShopMallUserConstant;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.MallUserService;
import com.eyh.mall.util.HttpClientUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 李平
 * @Date 2023-3-5
 */
@Service
public class MallUserServiceImpl extends ServiceImpl<MallUserMapper, MallUser> implements MallUserService {

    @Value("${wx.login.appid}")
    private String appid;

    @Value("${wx.login.secret}")
    private String secret;

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 微信用户一键快捷登录
     * @param wxLoginTdo
     * @return Result
     */
    @Override
    public Result wxLogin(WxLoginTdo wxLoginTdo) {
        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject(wxLoginTdo.getRawData());

        JSONObject jsonObject = getSessionKeyOrOpenId(wxLoginTdo.getCode(),appid,secret);
        String openid = jsonObject.getString("openid");
        String unionid = jsonObject.getString("unionid");
        String sessionKey = jsonObject.getString("session_key");

        // 校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(wxLoginTdo.getRawData() + sessionKey);
        if (!wxLoginTdo.getSignature().equals(signature2)) {
            return Result.err("签名校验失败");
        }

        // MallUser userByPhone1 = userApiClient.getMallUserByPhone(wxAccountPwdRegister.getPhone());

        // 根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；
        MallUser mallUser = userApiClient.getMallUserByOpenId(openid);

        if(mallUser == null){
            String userId = UUID.randomUUID().toString();
            String readName = ".u-" + UUID.randomUUID().toString().substring(0, 6);
            // 利用RabbitMQ异步消息将用户信息入库
            MallUser mu = new MallUser(
                    UUID.randomUUID().toString(),
                    userId,
                    "",
                    "",
                    "https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/avatar/wx.jpg",
                    "",
                    openid,
                    rawDataJson.getString("nickName"),
                    rawDataJson.getInteger("gender"),
                    "",
                    unionid,
                    0,
                    readName,
                    0,
                    ""
            );
            // 发送消息
            rabbitTemplate.convertAndSend(ShopMallUserConstant.SAVE_MALL_USER_QUEUE,JSON.toJSONString(mu));
            // mallUserMapper.insert(mallUser);
            JSONObject accessTokenJson = getAccessToken(wxLoginTdo.getCode(), appid, secret);
            String accessToken = accessTokenJson.getString("access_token");
            String expiresIn = accessTokenJson.getString("expires_in");

            WxLoginUserVo wxLoginUserVo = new WxLoginUserVo();
            wxLoginUserVo.setAccessToken(accessToken);
            wxLoginUserVo.setExpiresIn(expiresIn);
            wxLoginUserVo.setUserId(userId);
            wxLoginUserVo.setNickName(mu.getNickName());
            wxLoginUserVo.setAvatar(mu.getAvatar());
            wxLoginUserVo.setSex(mu.getSex());
            wxLoginUserVo.setRealName(mu.getRealName());
            wxLoginUserVo.setType(mu.getType());
            wxLoginUserVo.setStatus(mu.getStatus());
            //去绑定
            wxLoginUserVo.setIsBindPhone(0);

            return Result.ok(wxLoginUserVo);
        }else{
            // 已经注册过
            JSONObject accessTokenJson = getAccessToken(wxLoginTdo.getCode(), appid, secret);
            String accessToken = accessTokenJson.getString("access_token");
            String expiresIn = accessTokenJson.getString("expires_in");

            WxLoginUserVo wxLoginUserVo = new WxLoginUserVo();
            wxLoginUserVo.setAccessToken(accessToken);
            wxLoginUserVo.setExpiresIn(expiresIn);
            wxLoginUserVo.setUserId(mallUser.getUserId());
            wxLoginUserVo.setNickName(mallUser.getNickName());
            wxLoginUserVo.setAvatar(mallUser.getAvatar());
            wxLoginUserVo.setSex(mallUser.getSex());
            wxLoginUserVo.setRealName(mallUser.getRealName());
            wxLoginUserVo.setType(mallUser.getType());
            wxLoginUserVo.setStatus(mallUser.getStatus());
            wxLoginUserVo.setPhone(mallUser.getPhone());
            wxLoginUserVo.setIsBindPhone(1);
            wxLoginUserVo.setPurchaseVipTime(mallUser.getPurchaseVipTime());

            return Result.ok(wxLoginUserVo);
        }

    }

    /**
     * 微信用户账号注册
     * @param wxAccountPwdRegister
     * @return Result
     */
    @Override
    public Result registerWxAccountPwd(WxAccountPwdRegister wxAccountPwdRegister) {
        // 校验参数
        if (StringUtils.isEmpty(wxAccountPwdRegister)){
            return Result.err("请求参数为空");
        }
        // 判断该手机号码是否已注册

        MallUser userByPhone = userApiClient.getMallUserByPhonePwd(wxAccountPwdRegister.getPhone(),
                new Md5Hash(wxAccountPwdRegister.getPassword(), "slat", 3).toString());

        if (userByPhone != null){
            // 已注册，更新openID信息
            return Result.err(wxAccountPwdRegister.getPhone() + "已注册，请更换其他手机号");
        }

        MallUser userByPhone1 = userApiClient.getMallUserByPhone(wxAccountPwdRegister.getPhone());
        if (userByPhone1 != null){
            //更新密码
            userApiClient.updatePwd(wxAccountPwdRegister.getPhone(),
                    new Md5Hash(wxAccountPwdRegister.getPassword(), "slat", 3).toString());

            WxLoginUserVo wxLoginUserVo = new WxLoginUserVo();
            wxLoginUserVo.setAccessToken(jwtConfig.createToken(JSON.toJSONString(userByPhone1)));
            wxLoginUserVo.setExpiresIn("7200");
            wxLoginUserVo.setUserId(userByPhone1.getUserId());
            wxLoginUserVo.setNickName(userByPhone1.getNickName());
            wxLoginUserVo.setAvatar(userByPhone1.getAvatar());
            wxLoginUserVo.setSex(userByPhone1.getSex());
            wxLoginUserVo.setRealName(userByPhone1.getRealName());
            wxLoginUserVo.setType(userByPhone1.getType());
            wxLoginUserVo.setStatus(userByPhone1.getStatus());
            wxLoginUserVo.setPhone(userByPhone1.getPhone());

            return Result.ok(wxLoginUserVo);

        }

        // 未注册
        String userId = UUID.randomUUID().toString();
        String readName = ".u-" + UUID.randomUUID().toString().substring(0, 6);

        // 插入
        MallUser mallUser = new MallUser(
                    UUID.randomUUID().toString(),
                    userId,
                    wxAccountPwdRegister.getPhone(),
                    new Md5Hash(wxAccountPwdRegister.getPassword(),"slat",3).toString(),
                    "https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/avatar/register.jpg",
                    "",
                    wxAccountPwdRegister.getOpenid(),
                    "手机用户",
                    0,
                    "",
                    "",
                    0,
                    readName,
                    0,
                ""
            );
        // 发送消息
        rabbitTemplate.convertAndSend(ShopMallUserConstant.SAVE_MALL_USER_QUEUE,JSON.toJSONString(mallUser));

        WxLoginUserVo wxLoginUserVo = new WxLoginUserVo();
        wxLoginUserVo.setAccessToken(jwtConfig.createToken(JSON.toJSONString(mallUser)));
        wxLoginUserVo.setExpiresIn("7200");
        wxLoginUserVo.setUserId(userId);
        wxLoginUserVo.setNickName(mallUser.getNickName());
        wxLoginUserVo.setAvatar(mallUser.getAvatar());
        wxLoginUserVo.setSex(mallUser.getSex());
        wxLoginUserVo.setRealName(mallUser.getRealName());
        wxLoginUserVo.setType(mallUser.getType());
        wxLoginUserVo.setStatus(mallUser.getStatus());
        wxLoginUserVo.setPhone(mallUser.getPhone());
        wxLoginUserVo.setPurchaseVipTime(userByPhone.getPurchaseVipTime());

        return Result.ok(wxLoginUserVo);
    }

    /**
     * 微信用户账号注册时发送短信验证码
     * @param phone
     * @return Result
     */
    @Override
    public Result wxAccountPwdRegisterSendCode(String phone) {
        // 校验参数
        if ("".equals(phone)){
            return Result.err("参数为空");
        }
        // // 判断该手机号码是否已注册
        // MallUser userByPhone = userApiClient.getMallUserByPhone(phone);
        // if (userByPhone != null){
        //     // 已注册
        //     return Result.err(phone + "已注册，请更换其他手机号");
        // }

        String key = SendCode.ACCOUNT_PWD_REGISTER_CODE + phone;
        Integer code = (Integer)redisUtil.get(key);
        if (code != null){
            return Result.err("验证码未过期", null);
        }

        // 发送消息到消息队列，发送短信验证码
        // 发送消息
        rabbitTemplate.convertAndSend(ShopMallUserConstant.MALL_USER_REGISTER_SEND_CODE_QUEUE,JSON.toJSONString(
                new SmsData(phone, "SMS_272581070", key,SendCode.ACCOUNT_PWD_REGISTER_CODE_TTL)
        ));
        return Result.ok("短信验证码发送成功", null);
    }

    /**
     * 校验微信用户账号注册时发送的短信验证码
     * @param code
     * @param phone
     * @return Result
     */
    @Override
    public Result checkWxRegisterCode(String code,String phone) {
        String key = SendCode.ACCOUNT_PWD_REGISTER_CODE + phone;
        Integer redisCode = (Integer)redisUtil.get(key);
        if(redisCode != null && code.equals(redisCode.toString())){
            return Result.ok("验证码校验成功", null);
        }
        return Result.err("验证码校验失败", null);
    }

    /**
     * 手机用户账号登录
     * @param wxAccountPwdRegister
     * @return Result
     */
    @Override
    public Result loginWxAccountPwd(WxAccountPwdRegister wxAccountPwdRegister) {
        // 校验参数
        if (wxAccountPwdRegister == null){
            return Result.err("参数为空");
        }
        // 判断该手机号码是否已注册
        MallUser userByPhone = userApiClient.getMallUserByPhone(wxAccountPwdRegister.getPhone());
        if (userByPhone == null){
            // 未注册
            return Result.err(wxAccountPwdRegister.getPhone() + "未注册，请先注册");
        }
        MallUser mallUserByPhonePwd = userApiClient.getMallUserByPhonePwd(
                wxAccountPwdRegister.getPhone(),
                new Md5Hash(wxAccountPwdRegister.getPassword(),"slat",3).toString());
        if (mallUserByPhonePwd == null){
            // 密码错误
            return Result.err("密码错误");
        }

        WxLoginUserVo wxLoginUserVo = new WxLoginUserVo();
        wxLoginUserVo.setAccessToken(jwtConfig.createToken(JSON.toJSONString(mallUserByPhonePwd)));
        wxLoginUserVo.setExpiresIn("7200");
        wxLoginUserVo.setUserId(mallUserByPhonePwd.getUserId());
        wxLoginUserVo.setNickName(mallUserByPhonePwd.getNickName());
        wxLoginUserVo.setAvatar(mallUserByPhonePwd.getAvatar());
        wxLoginUserVo.setSex(mallUserByPhonePwd.getSex());
        wxLoginUserVo.setRealName(mallUserByPhonePwd.getRealName());
        wxLoginUserVo.setType(mallUserByPhonePwd.getType());
        wxLoginUserVo.setStatus(mallUserByPhonePwd.getStatus());
        wxLoginUserVo.setPhone(mallUserByPhonePwd.getPhone());
        wxLoginUserVo.setPurchaseVipTime(userByPhone.getPurchaseVipTime());

        return Result.ok(wxLoginUserVo);
    }

    /**
     * 手机用户手机号码验证码验证登录
     * @param code
     * @param phone
     * @return Result
     */
    @Override
    public Result wxLoginPhoneCode(String phone, String code) {
        // 校验参数
        if (phone == null || code == null){
            return Result.err("参数为空");
        }
        String key = SendCode.PHONE_LOGIN_CODE + phone;
        Integer redisCode = (Integer)redisUtil.get(key);

        // 判断Redis是否存在短信验证码
        if(redisCode == null){
            return Result.err("验证码已过期", null);
        }

        if(!code.equals(redisCode.toString())){
            return Result.err("验证码错误", null);
        }

        // 判断该手机号码是否已注册
        MallUser userByPhone = userApiClient.getMallUserByPhone(phone);
        if (userByPhone == null){
            // 未注册,插入一条信息
            String userId = UUID.randomUUID().toString();
            String readName = ".u-" + UUID.randomUUID().toString().substring(0, 6);

            // 插入
            MallUser mallUser = new MallUser(
                    UUID.randomUUID().toString(),
                    userId,
                    phone,
                    "",
                    "https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/avatar/user.jpg",
                    "",
                    "",
                    "手机验证用户",
                    0,
                    "",
                    "",
                    0,
                    readName,
                    0,
                    ""
            );
            // 发送消息
            rabbitTemplate.convertAndSend(ShopMallUserConstant.SAVE_MALL_USER_QUEUE,JSON.toJSONString(mallUser));
            WxLoginUserVo wxLoginUserVo = new WxLoginUserVo();
            wxLoginUserVo.setAccessToken(jwtConfig.createToken(JSON.toJSONString(mallUser)));
            wxLoginUserVo.setExpiresIn("7200");
            wxLoginUserVo.setUserId(mallUser.getUserId());
            wxLoginUserVo.setNickName(mallUser.getNickName());
            wxLoginUserVo.setAvatar(mallUser.getAvatar());
            wxLoginUserVo.setSex(mallUser.getSex());
            wxLoginUserVo.setRealName(mallUser.getRealName());
            wxLoginUserVo.setType(mallUser.getType());
            wxLoginUserVo.setStatus(mallUser.getStatus());
            wxLoginUserVo.setPhone(mallUser.getPhone());

            return Result.ok(wxLoginUserVo);
        }else{

            WxLoginUserVo wxLoginUserVo = new WxLoginUserVo();
            wxLoginUserVo.setAccessToken(jwtConfig.createToken(JSON.toJSONString(userByPhone)));
            wxLoginUserVo.setExpiresIn("7200");
            wxLoginUserVo.setUserId(userByPhone.getUserId());
            wxLoginUserVo.setNickName(userByPhone.getNickName());
            wxLoginUserVo.setAvatar(userByPhone.getAvatar());
            wxLoginUserVo.setSex(userByPhone.getSex());
            wxLoginUserVo.setRealName(userByPhone.getRealName());
            wxLoginUserVo.setType(userByPhone.getType());
            wxLoginUserVo.setStatus(userByPhone.getStatus());
            wxLoginUserVo.setPhone(userByPhone.getPhone());
            wxLoginUserVo.setPurchaseVipTime(userByPhone.getPurchaseVipTime());

            return Result.ok(wxLoginUserVo);
        }
    }

    /**
     * 手机用户手机号码验证码验证登录时发送验证码
     * @param phone
     * @return Result
     */
    @Override
    public Result wxPhoneLoginSendCode(String phone) {
        // 校验参数
        if ("".equals(phone)){
            return Result.err("参数为空");
        }
        String key = SendCode.PHONE_LOGIN_CODE + phone;
        Integer code = (Integer)redisUtil.get(key);
        if (code != null){
            return Result.err("验证码未过期", null);
        }

        // 发送消息到消息队列，发送短信验证码
        // 发送消息
        rabbitTemplate.convertAndSend(ShopMallUserConstant.MALL_USER_REGISTER_SEND_CODE_QUEUE,JSON.toJSONString(
                new SmsData(phone,"SMS_272491041",key,SendCode.PHONE_LOGIN_CODE_TTL)
        ));
        return Result.ok("短信验证码发送成功", null);
    }

    /**
     * wx绑定手机
     *
     * @param phone  电话
     * @param code   代码
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result wxBindPhone(String phone, String code, String userId) {
        //校验验证码
        if (phone == null || code == null){
            return Result.err("参数为空");
        }
        String key = SendCode.WX_BIND_PHONE_CODE + phone;
        Integer redisCode = (Integer)redisUtil.get(key);

        // 判断Redis是否存在短信验证码
        if(redisCode == null){
            return Result.err("验证码已过期", null);
        }

        if(!code.equals(redisCode.toString())){
            return Result.err("验证码错误", null);
        }
        // 绑定
        if ("".equals(userId)){
            return Result.err("参数为空");
        }
        MallUser mallUserByUserId = userApiClient.getMallUserByUserId(userId);
        if (mallUserByUserId == null){
            return Result.err("绑定错误，您的当前账号出错");
        }
        if (!"".equals(mallUserByUserId.getPhone())){
            return Result.err("您已绑定其他手机号码");
        }
        // 绑定
        return userApiClient.wxBindingPhone(phone, userId);
    }

    /**
     * 得到wx绑定电话号码
     *
     * @param phone 电话
     * @return {@link Result}
     */
    @Override
    public Result getWxBindPhoneCode(String phone) {
        // 校验参数
        if ("".equals(phone)){
            return Result.err("参数为空");
        }
        String key = SendCode.WX_BIND_PHONE_CODE + phone;
        Integer code = (Integer)redisUtil.get(key);
        if (code != null){
            return Result.err("验证码未过期", null);
        }
        // 发送消息到消息队列，发送短信验证码
        // 发送消息
        rabbitTemplate.convertAndSend(ShopMallUserConstant.MALL_USER_REGISTER_SEND_CODE_QUEUE,JSON.toJSONString(
                new SmsData(phone,"SMS_272491041",key,SendCode.WX_BIND_PHONE_CODE_TTL)
        ));
        return Result.ok("短信验证码发送成功", null);
    }

    /**
     * 获取 sessionKey 和 openID
     * @param code
     * @param appid
     * @param secret
     * @return JSONObject
     */
    public static JSONObject getSessionKeyOrOpenId(String code,String appid,String secret) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" +
                secret + "&js_code=" + code + "&grant_type=authorization_code";
        return handleRequest(requestUrl);
    }

    /**
     * 获取接口调用凭据
     * @param code
     * @param appid
     * @param secret
     * @return JSONObject
     */
    public static JSONObject getAccessToken(String code,String appid,String secret) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?appid=" + appid + "&secret=" +
                secret + "&js_code=" + code + "&grant_type=client_credential";
        return handleRequest(requestUrl);
    }

    /**
     * 获取手机号码
     * @param access_token
     * @return JSONObject
     */
    public static JSONObject getPhoneNumber(String access_token) {
        String requestUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + access_token;
        return handleRequest(requestUrl);
    }

    /**
     * 处理httpRequest请求
     * @param requestUrl
     * @return JSONObject
     */
    public static JSONObject handleRequest(String requestUrl){
        String request = "";
        try {
            request = HttpClientUtil.httpGetRequest(requestUrl);
        }catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(request);
        return jsonObject;
    }

}
