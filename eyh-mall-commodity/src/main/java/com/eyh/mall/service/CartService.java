package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.Cart;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.CardPage;

/**
 * @Author 李平
 * @NAME CartService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-25 13:39:10
 * @Description 购物车接口
 */
public interface CartService extends IService<Cart> {

    /**
     * 添加购物车
     *
     * @param cart 车
     * @return {@link Result}
     */
    Result addCart(Cart cart);

    /**
     * 删除购物车
     *
     * @param cart 车
     * @return {@link Result}
     */
    Result deleteCart(Cart cart);

    /**
     * 得到所有购物车页面
     *
     * @param cardPage 卡页面
     * @return {@link Result}
     */
    Result getAllCartByPage(CardPage cardPage);

    /**
     * 获取用户购物车
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result getUserCart(String userId);
}
