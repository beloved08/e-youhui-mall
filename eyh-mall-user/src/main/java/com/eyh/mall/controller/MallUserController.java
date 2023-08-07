package com.eyh.mall.controller;

import com.eyh.mall.entity.OrdinaryMallUserPage;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.service.MallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-3-6
 */
@RestController
@RequestMapping("/user")
public class MallUserController {

    @Autowired
    private MallUserService mallUserService;

    /**
     * 通过openid获取用户
     * @param openid
     * @return MallUser
     */
    @GetMapping("/getMallUserByOpenId/{openid}")
    public MallUser getMallUserByOpenId(@PathVariable String openid) {
        return mallUserService.getMallUser(openid);
    }

    /**
     * 通过phone获取用户
     * @param phone
     * @return MallUser
     */
    @GetMapping("/getMallUserByPhone/{phone}")
    public MallUser getMallUserByPhone(@PathVariable String phone) {
        return mallUserService.getMallUserByPhone(phone);
    }

    /**
     * 通过phone,password获取用户
     * @param phone
     * @param password
     * @return MallUser
     */
    @GetMapping("/getMallUserByPhonePwd")
    public MallUser getMallUserByPhonePwd(@RequestParam(value = "phone") String phone,
                                          @RequestParam(value = "password") String password) {
        return mallUserService.getMallUserByPhonePwd(phone,password);
    }

    /**
     * 商城用户用户id
     *
     * @param userId 用户id
     * @return {@link MallUser}
     */
    @GetMapping("/getMallUserByUserId/{userId}")
    public MallUser getMallUserByUserId(@PathVariable String userId){
        return mallUserService.getMallUserByUserId(userId);
    }

    /**
     * wx绑定手机
     *
     * @param phone  电话
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/wxBindingPhone/{phone}/{userId}")
    public Result wxBindingPhone(@PathVariable String phone, @PathVariable String userId){
        return mallUserService.wxBindingPhone(phone,userId);
    }

    /**
     * 得到所有普通商城用户页面
     *
     * @param ordinaryMallUserPage 普通商城用户页面
     * @return {@link Result}
     */
    @PostMapping("/getAllOrdinaryMallUserByPage")
    public Result getAllOrdinaryMallUserByPage(@RequestBody OrdinaryMallUserPage ordinaryMallUserPage){
        return mallUserService.getAllOrdinaryMallUserByPage(ordinaryMallUserPage);
    }

    /**
     * 更新pwd
     *
     * @param phone 电话
     * @param pwd   松材线虫病
     * @return {@link Result}
     */
    @GetMapping("/updatePwd/{phone}/{pwd}")
    public Result updatePwd(@PathVariable String phone, @PathVariable String pwd){
        return mallUserService.updatePwd(phone, pwd);
    }

    /**
     * 让商城用户名字
     *
     * @param userName 用户名
     * @return {@link Result}
     */
    @GetMapping("/getMallUserByName/{userName}")
    public MallUser getMallUserByName(@PathVariable String userName){
        return mallUserService.getMallUserByName(userName);
    }

    /**
     * 得到所有商城用户名
     *
     * @return {@link Result}
     */
    @GetMapping("/getAllMallUserName")
    public Result getAllMallUserName(){
        return mallUserService.getAllMallUserName();
    }

    /**
     * 用户购买贵宾
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/userPurchaseVip/{userId}")
    public Result userPurchaseVip(@PathVariable String userId){
        return mallUserService.userPurchaseVip(userId);
    }

    /**
     * 得到总成员
     *
     * @return {@link Result}
     */
    @GetMapping("/getTotalUser")
    public Result getTotalUser(){
        return mallUserService.getTotalUser();
    }

    /**
     * 得到总成员
     *
     * @return {@link Result}
     */
    @GetMapping("/getTotalMember")
    public Result getTotalMember(){
        return mallUserService.getTotalMember();
    }

    /**
     * 今天得到添加成员
     *
     * @return {@link Result}
     */
    @GetMapping("/getTodayAddMember")
    public Result getTodayAddMember(){
        return mallUserService.getTodayAddMember();
    }
}
