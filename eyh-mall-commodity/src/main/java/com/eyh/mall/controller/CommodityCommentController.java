package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.CommodityCommentVo;
import com.eyh.mall.service.CommodityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME CommodityCommentController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-22 17:55:28
 * @Description 商品评论控制类
 */
@RestController
@RequestMapping("/commodity")
public class CommodityCommentController {

    @Autowired
    private CommodityCommentService commodityCommentService;

    /**
     * 添加商品评论
     *
     * @param commodityCommentVo 商品评论签证官
     * @return {@link Result}
     */
    @PostMapping("/addCommodityComment")
    public Result addCommodityComment(@RequestBody CommodityCommentVo commodityCommentVo){
        return commodityCommentService.addCommodityComment(commodityCommentVo);
    }

    /**
     * 通过id获取商品评论
     *
     * @param commodityId 商品id
     * @return {@link Result}
     */
    @GetMapping("/getCommodityCommentById/{commodityId}")
    public Result getCommodityCommentById(@PathVariable String commodityId){
        return commodityCommentService.getCommodityCommentById(commodityId);
    }

}
