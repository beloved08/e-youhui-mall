package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.UserCollect;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UserCollectPage;

/**
 * @author 李平
 * @Date 2023-3-27
 */
public interface UserCollectService extends IService<UserCollect> {

    /**
     * 添加用户收集
     *
     * @param userCollect 用户收集
     * @return {@link Result}
     */
    Result addUserCollect(UserCollect userCollect);

    /**
     * 让所有用户收集
     *
     * @param userCollectPage 用户收集页面
     * @return {@link Result}
     */
    Result getAllUserCollect(UserCollectPage userCollectPage);

    /**
     * 得到收集数
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result getCollectCount(String userId);

    /**
     * 得到收集名单
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result getCollectList(String userId);

    /**
     * 检查用户收集业务
     *
     * @param userId     用户id
     * @param businessId 业务标识
     * @return boolean
     */
    Result checkUserIsCollectBusiness(String userId, String businessId);

    /**
     * 取消收藏
     *
     * @param collectId 收集id
     * @return {@link Result}
     */
    Result cancelCollect(String collectId);
}
