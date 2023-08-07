package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.CommodityComment;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.tdo.CommodityCommentDto;
import com.eyh.mall.entity.vo.CommodityCommentVo;
import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.merchantStores.MerchantStoresApiClient;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.mapper.ClassificationMapper;
import com.eyh.mall.mapper.CommodityCommentMapper;
import com.eyh.mall.mapper.CommodityMapper;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.CommodityCommentService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 李平
 * @Date 2023-2-18
 */
@Service
public class CommodityCommentServiceImpl extends ServiceImpl<CommodityCommentMapper, CommodityComment> implements CommodityCommentService {

    @Autowired
    private CommodityCommentMapper commodityCommentMapper;

    @Autowired
    private UserApiClient userApiClient;

    /**
     * 添加商品评论
     *
     * @param commodityCommentVo 商品评论签证官
     * @return {@link Result}
     */
    @Override
    public Result addCommodityComment(CommodityCommentVo commodityCommentVo) {
        CommodityComment commodityComment = new CommodityComment();

        BeanUtils.copyProperties(commodityCommentVo,commodityComment);
        commodityComment.setId(UUID.randomUUID().toString());
        commodityComment.setCommentId(UUID.randomUUID().toString());
        commodityComment.setCommentTime(TimeUtil.getCurrentTime());

        commodityCommentMapper.insert(commodityComment);
        return Result.ok(null);
    }

    /**
     * 通过id获取商品评论
     *
     * @param commodityId 商品id
     * @return {@link Result}
     */
    @Override
    public Result getCommodityCommentById(String commodityId) {
        LambdaQueryWrapper<CommodityComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(commodityId),CommodityComment::getCommodityId,commodityId);

        List<CommodityCommentDto> list = new ArrayList<CommodityCommentDto>();
        commodityCommentMapper.selectList(queryWrapper).forEach(c -> {
            CommodityCommentDto dto = new CommodityCommentDto();
            MallUser mallUser = userApiClient.getMallUserByUserId(c.getUserId());

            dto.setUserName(mallUser.getRealName());
            dto.setAvatarUrl(mallUser.getAvatar());
            dto.setCommentContent(c.getCommentContent());
            dto.setTime(c.getCommentTime());

            list.add(dto);
        });

        return Result.ok(list);
    }
}
