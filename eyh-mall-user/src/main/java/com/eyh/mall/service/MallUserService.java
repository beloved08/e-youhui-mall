package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.OrdinaryMallUserPage;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.feign.entity.MallUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-3-5
 */
public interface MallUserService extends IService<MallUser> {

    /**
     * 通过openid获取用户
     * @param openid
     * @return MallUser
     */
    MallUser getMallUser(String openid);

    /**
     * 通过phone获取用户
     * @param phone
     * @return MallUser
     */
    MallUser getMallUserByPhone(String phone);

    /**
     * 通过phone,password获取用户
     * @param phone
     * @param password
     * @return MallUser
     */
    MallUser getMallUserByPhonePwd(String phone, String password);

    /**
     * 商城用户用户id
     *
     * @param userId 用户id
     * @return {@link MallUser}
     */
    MallUser getMallUserByUserId(String userId);

    /**
     * wx绑定手机
     *
     * @param phone  电话
     * @param userId 用户id
     * @return {@link Result}
     */
    Result wxBindingPhone(@PathVariable String phone, @PathVariable String userId);

    /**
     * 得到所有普通商城用户页面
     *
     * @param ordinaryMallUserPage 普通商城用户页面
     * @return {@link Result}
     */
    Result getAllOrdinaryMallUserByPage(OrdinaryMallUserPage ordinaryMallUserPage);

    /**
     * 更新pwd
     *
     * @param phone 电话
     * @param pwd   松材线虫病
     * @return {@link Result}
     */
    Result updatePwd(String phone, String pwd);

    /**
     * 让商城用户名字
     *
     * @param userName 用户名
     * @return {@link Result}
     */
    MallUser getMallUserByName(String userName);

    /**
     * 得到所有商城用户名
     *
     * @return {@link Result}
     */
    Result getAllMallUserName();

    /**
     * 用户购买贵宾
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result userPurchaseVip(String userId);

    /**
     * 得到总成员
     *
     * @return {@link Result}
     */
    Result getTotalUser();

    /**
     * 得到总成员
     *
     * @return {@link Result}
     */
    Result getTotalMember();

    /**
     * 今天得到添加成员
     *
     * @return {@link Result}
     */
    Result getTodayAddMember();

}
