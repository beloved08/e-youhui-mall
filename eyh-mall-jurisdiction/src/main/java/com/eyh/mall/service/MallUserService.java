package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.tdo.WxAccountPwdRegister;
import com.eyh.mall.entity.tdo.WxLoginTdo;
import com.eyh.mall.feign.entity.MallUser;

/**
 * @author 李平
 * @Date 2023-3-5
 */
public interface MallUserService extends IService<MallUser> {

    /**
     * 微信用户一键快捷登录
     * @param wxLoginTdo
     * @return Result
     */
    Result wxLogin(WxLoginTdo wxLoginTdo) throws Exception;

    /**
     * 微信用户账号注册
     * @param wxAccountPwdRegister
     * @return Result
     */
    Result registerWxAccountPwd(WxAccountPwdRegister wxAccountPwdRegister);

    /**
     * 微信用户账号注册时发送短信验证码
     * @param phone
     * @return Result
     */
    Result wxAccountPwdRegisterSendCode(String phone);

    /**
     * 校验微信用户账号注册时发送的短信验证码
     * @param code
     * @param phone
     * @return Result
     */
    Result checkWxRegisterCode(String code,String phone);

    /**
     * 手机用户账号登录
     * @param wxAccountPwdRegister
     * @return Result
     */
    Result loginWxAccountPwd(WxAccountPwdRegister wxAccountPwdRegister);

    /**
     * 手机用户手机号码验证码验证登录
     * @param code
     * @param phone
     * @return Result
     */
    Result wxLoginPhoneCode(String phone, String code);

    /**
     * 手机用户手机号码验证码验证登录时发送验证码
     * @param phone
     * @return Result
     */
    Result wxPhoneLoginSendCode(String phone);

    /**
     * wx绑定手机
     *
     * @param phone  电话
     * @param code   代码
     * @param userId 用户id
     * @return {@link Result}
     */
    Result wxBindPhone(String phone, String code, String userId);

    /**
     * 得到wx绑定电话号码
     *
     * @param phone 电话
     * @return {@link Result}
     */
    Result getWxBindPhoneCode(String phone);
}
