package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.tdo.WxAccountPwdRegister;
import com.eyh.mall.entity.tdo.WxLoginTdo;
import com.eyh.mall.service.MallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李平
 * @Date 2023-3-5
 */
@RestController
@RequestMapping("/jurisdiction/wx")
public class WxLoginController {

    @Autowired
    private MallUserService mallUserService;

    /**
     * 微信用户一键登录
     * @param wxLoginTdo
     * @return Result
     */
    @PostMapping("/wxQuickLogin")
    public Result wxLogin(@RequestBody WxLoginTdo wxLoginTdo) {
        try {
            return mallUserService.wxLogin(wxLoginTdo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err(e.getMessage());
        }
    }

    /**
     * 微信用户账号注册时发送短信验证码
     * @param phone
     * @return Result
     */
    @GetMapping("/wxAccountPwdRegisterSendCode/{phone}")
    public Result wxAccountPwdRegisterSendCode(@PathVariable String phone) {
        try {
            return mallUserService.wxAccountPwdRegisterSendCode(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err(e.getMessage());
        }
    }

    /**
     * 手机用户手机号码验证码验证登录时发送验证码
     * @param phone
     * @return Result
     */
    @GetMapping("/wxPhoneLoginSendCode/{phone}")
    public Result wxPhoneSendCode(@PathVariable String phone) {
        try {
            return mallUserService.wxPhoneLoginSendCode(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err(e.getMessage());
        }
    }

    /**
     * 校验微信用户账号注册时发送的短信验证码
     * @param code
     * @param phone
     * @return Result
     */
    @GetMapping("/checkWxRegisterCode")
    public Result checkWxRegisterCode(@RequestParam(value = "code",required = true) String code,
                                      @RequestParam(value = "phone",required = true) String phone) {
        try {
            return mallUserService.checkWxRegisterCode(code, phone);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err(e.getMessage());
        }
    }

    /**
     * 手机用户账号注册
     * @param wxAccountPwdRegister
     * @return Result
     */
    @PostMapping("/wxAccountPwdRegister")
    public Result wxAccountPwdRegister(@RequestBody WxAccountPwdRegister wxAccountPwdRegister) {
        try {
            return mallUserService.registerWxAccountPwd(wxAccountPwdRegister);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err(e.getMessage());
        }
    }

    /**
     * 手机用户账号密码登录
     * @param wxAccountPwdRegister
     * @return Result
     */
    @PostMapping("/wxAccountPwdLogin")
    public Result wxAccountPwdLogin(@RequestBody WxAccountPwdRegister wxAccountPwdRegister) {
        try {
            return mallUserService.loginWxAccountPwd(wxAccountPwdRegister);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err(e.getMessage());
        }
    }

    /**
     * 手机用户手机号码验证码验证登录
     * @param code
     * @param phone
     * @return Result
     */
    @GetMapping("/wxPhoneCodeLogin")
    public Result wxPhoneCodeLogin(@RequestParam(value = "code",required = true) String code,
                                   @RequestParam(value = "phone",required = true) String phone) {
        try {
            return mallUserService.wxLoginPhoneCode(phone,code);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err(e.getMessage());
        }
    }

    /**
     * 得到wx绑定电话号码
     *
     * @param phone 电话
     * @return {@link Result}
     */
    @GetMapping("/getWxBindPhoneCode/{phone}")
    public Result getWxBindPhoneCode(@PathVariable String phone){
        return mallUserService.getWxBindPhoneCode(phone);
    }

    /**
     * 绑定手机
     *
     * @param phone  电话
     * @param code   代码
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/wxBindPhone/{phone}/{code}/{userId}")
    public Result bindPhone(@PathVariable String phone, @PathVariable String code, @PathVariable String userId){
        return mallUserService.wxBindPhone(phone, code, userId);
    }


}
