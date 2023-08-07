package com.eyh.mall.controller;

import com.eyh.mall.entity.UserCollect;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UserAddressPage;
import com.eyh.mall.entity.vo.UserCollectPage;
import com.eyh.mall.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME UserCollectController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-03-27 21:12:08
 * @Description 用户收藏控制类
 */
@RestController
@RequestMapping("/user")
public class UserCollectController {

    @Autowired
    private UserCollectService userCollectService;

    /**
     * 添加用户收集
     *
     * @param userCollect 用户收集
     * @return {@link Result}
     */
    @PostMapping("/addUserCollect")
    public Result addUserCollect(@RequestBody UserCollect userCollect){
        return userCollectService.addUserCollect(userCollect);
    }

    /**
     * 让所有用户收集
     *
     * @param userCollectPage 用户收集页面
     * @return {@link Result}
     */
    @PostMapping("/getAllUserCollect")
    public Result getAllUserCollect(@RequestBody UserCollectPage userCollectPage) {
        return userCollectService.getAllUserCollect(userCollectPage);
    }

    /**
     * 检查用户收集业务
     *
     * @param userId     用户id
     * @param businessId 业务标识
     * @return boolean
     */
    @GetMapping("/checkUserIsCollectBusiness/{userId}/{businessId}")
    public Result checkUserIsCollectBusiness(@PathVariable String userId, @PathVariable String businessId){
        return userCollectService.checkUserIsCollectBusiness(userId, businessId);
    }

    /**
     * 得到收集数
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getCollectCount/{userId}")
    public Result getCollectCount(@PathVariable String userId){
        return userCollectService.getCollectCount(userId);
    }

    /**
     * 得到收集名单
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getUserCollectList/{userId}")
    public Result getCollectList(@PathVariable String userId){
        return userCollectService.getCollectList(userId);
    }

    /**
     * 取消收藏
     *
     * @param collectId 收集id
     * @return {@link Result}
     */
    @GetMapping("/cancelCollect/{collectId}")
    public Result cancelCollect(@PathVariable String collectId){
        return userCollectService.cancelCollect(collectId);
    }

}
