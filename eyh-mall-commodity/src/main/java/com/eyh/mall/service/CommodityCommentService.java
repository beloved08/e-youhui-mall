package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.CommodityComment;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.CommodityCommentVo;

/**
 * @author 李平
 * @Date 2023-2-18
 */
public interface CommodityCommentService extends IService<CommodityComment> {

    /**
     * 添加商品评论
     *
     * @param commodityCommentVo 商品评论签证官
     * @return {@link Result}
     */
    Result addCommodityComment(CommodityCommentVo commodityCommentVo);

    /**
     * 通过id获取商品评论
     *
     * @param commodityId 商品id
     * @return {@link Result}
     */
    Result getCommodityCommentById(String commodityId);
}
