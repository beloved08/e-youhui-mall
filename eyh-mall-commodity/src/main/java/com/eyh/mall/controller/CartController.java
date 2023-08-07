package com.eyh.mall.controller;

import com.eyh.mall.entity.Cart;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.CardPage;
import com.eyh.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME CartController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-25 13:41:19
 * @Description 购物车控制类
 */
@RestController
@RequestMapping("/commodity")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     *
     * @param cart 车
     * @return {@link Result}
     */
    @PostMapping("/addCart")
    public Result addCart(@RequestBody Cart cart) {
        return cartService.addCart(cart);
    }

    /**
     * 获取用户购物车
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getUserCart/{userId}")
    public Result getUserCart(@PathVariable String userId){
        return cartService.getUserCart(userId);
    }

    /**
     * 删除购物车
     *
     * @param cart 车
     * @return {@link Result}
     */
    @PostMapping("/deleteCart")
    public Result deleteCart(@RequestBody Cart cart) {
        return cartService.deleteCart(cart);
    }

    /**
     * 得到所有购物车页面
     *
     * @param cardPage 卡页面
     * @return {@link Result}
     */
    @PostMapping("/getAllCartByPage")
    public Result getAllCartByPage(@RequestBody CardPage cardPage) {
        return cartService.getAllCartByPage(cardPage);
    }

}
