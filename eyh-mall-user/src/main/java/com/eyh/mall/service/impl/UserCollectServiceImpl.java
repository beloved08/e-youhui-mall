package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.UserAddress;
import com.eyh.mall.entity.UserCollect;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.entity.tdo.UserCollectDto;
import com.eyh.mall.entity.tdo.UserCollectTdo;
import com.eyh.mall.entity.vo.UserCollectPage;
import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.mapper.MallUserMapper;
import com.eyh.mall.mapper.UserCollectMapper;
import com.eyh.mall.service.UserCollectService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 李平
 * @Date 2023-3-27
 */
@Service
public class UserCollectServiceImpl extends ServiceImpl<UserCollectMapper, UserCollect> implements UserCollectService {

    @Autowired
    private UserCollectMapper userCollectMapper;

    @Autowired
    private MallUserMapper mallUserMapper;

    @Autowired
    private CommodityServiceApiClient commodityServiceApiClient;

    @Autowired
    private CommodityApiClient commodityApiClient;

    /**
     * 添加用户收集
     *
     * @param userCollect 用户收集
     * @return {@link Result}
     */
    @Override
    public Result addUserCollect(UserCollect userCollect) {

        //判断当前收藏的商家或商品是否已经被收藏
        LambdaQueryWrapper<UserCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userCollect.getUserId()),UserCollect::getUserId,userCollect.getUserId());

        if (userCollect.getIsType() == 0){
            //商家收藏
            queryWrapper.eq(!"".equals(userCollect.getBusinessId()),UserCollect::getBusinessId,userCollect.getBusinessId());
        }else{
            //商品收藏
            queryWrapper.eq(!"".equals(userCollect.getCommodityId()),UserCollect::getCommodityId,userCollect.getCommodityId());
        }

        UserCollect collect = userCollectMapper.selectOne(queryWrapper);
        if (collect != null){
            return Result.err("您已收藏，不能重复收藏");
        }

        userCollect.setId(UUID.randomUUID().toString());
        userCollect.setCollectId(UUID.randomUUID().toString());
        userCollect.setCollectTime(TimeUtil.getCurrentTime());
        int insert = userCollectMapper.insert(userCollect);

        return insert > 0 ? Result.ok("收藏成功") : Result.err("收藏失败");

    }

    /**
     * 让所有用户收集
     *
     * @param userCollectPage 用户收集页面
     * @return {@link Result}
     */
    @Override
    public Result getAllUserCollect(UserCollectPage userCollectPage) {
        IPage<UserCollect> page = new Page<UserCollect>(userCollectPage.getCurrentPage(), userCollectPage.getPageSize());
        //条件查询
        LambdaQueryWrapper<UserCollect> wrapper = new LambdaQueryWrapper<>();
        //用户名
        if(!"".equals(userCollectPage.getUserName())){
            LambdaQueryWrapper<MallUser> mallUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
            mallUserLambdaQueryWrapper.eq(!"".equals(userCollectPage.getUserName()),MallUser::getRealName,userCollectPage.getUserName());
            MallUser mallUser = mallUserMapper.selectOne(mallUserLambdaQueryWrapper);
            wrapper.eq(!"".equals(mallUser.getUserId()),UserCollect::getUserId,mallUser.getUserId());
        }
        IPage<UserCollect> selectPage = userCollectMapper.selectPage(page, wrapper);

        UserCollectTdo tdo = new UserCollectTdo();
        tdo.setCurrentPage(selectPage.getCurrent());
        tdo.setSize(selectPage.getSize());
        tdo.setTotal(selectPage.getTotal());
        tdo.setPageSize(selectPage.getPages());

        List<UserCollect> records = selectPage.getRecords();

        List<UserCollectDto> list = new ArrayList<>();
        records.forEach(u->{
            UserCollectDto collectDto = new UserCollectDto();
            //查询用户名
            LambdaQueryWrapper<MallUser> mallUserQW = new LambdaQueryWrapper<>();
            mallUserQW.eq(!"".equals(u.getUserId()),MallUser::getUserId,u.getUserId());
            MallUser mallUser = mallUserMapper.selectOne(mallUserQW);
            collectDto.setUsername(mallUser.getRealName());
            collectDto.setMallUser(mallUser);

            // TODO 商品名称
            if (!"".equals(u.getCommodityId())){
                Commodity commodityById = commodityServiceApiClient.getCommodityById(u.getCommodityId());
                collectDto.setCommodityName(commodityById.getCommodityName());
                collectDto.setCommodity(commodityById);
            }

            // TODO 商家名称
            if (!"".equals(u.getBusinessId())){
                Business businessById = commodityApiClient.getBusinessById(u.getBusinessId());
                collectDto.setBusinessName(businessById.getBusinessName());
                collectDto.setBusiness(businessById);
            }

            collectDto.setType(u.getIsType());
            collectDto.setCollectTime(u.getCollectTime());
            list.add(collectDto);
        });
        tdo.setUserCollectDtoList(list);

        return Result.ok(tdo);
    }

    /**
     * 得到收集数
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getCollectCount(String userId) {
        LambdaQueryWrapper<UserCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserCollect::getUserId,userId);

        return Result.ok(userCollectMapper.selectCount(queryWrapper));
    }

    /**
     * 得到收集名单
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getCollectList(String userId) {
        LambdaQueryWrapper<UserCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserCollect::getUserId,userId);
        List<UserCollect> userCollects = userCollectMapper.selectList(queryWrapper);
        UserCollectTdo tdo = new UserCollectTdo();

        ArrayList<UserCollectDto> userCollectDtos = new ArrayList<>();
        userCollects.forEach(u -> {
            UserCollectDto dto = new UserCollectDto();
            dto.setCollectTime(u.getCollectTime());
            dto.setType(u.getIsType());
            dto.setCollectId(u.getCollectId());
            //商品信息
            if (!"".equals(u.getCommodityId())){
                Commodity commodityById = commodityServiceApiClient.getCommodityById(u.getCommodityId());
                dto.setCommodity(commodityById);
                dto.setCommodityName(commodityById.getCommodityName());
            }
            // 商家名称
            if (!"".equals(u.getBusinessId())){
                Business businessById = commodityApiClient.getBusinessById(u.getBusinessId());
                dto.setBusiness(businessById);
                dto.setBusinessName(businessById.getBusinessName());
            }

            userCollectDtos.add(dto);
        });
        tdo.setUserCollectDtoList(userCollectDtos);
        return Result.ok(tdo);
    }

    /**
     * 检查用户收集业务
     *
     * @param userId     用户id
     * @param businessId 业务标识
     * @return boolean
     */
    @Override
    public Result checkUserIsCollectBusiness(String userId, String businessId) {
        LambdaQueryWrapper<UserCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserCollect::getUserId,userId);
        queryWrapper.eq(!"".equals(businessId),UserCollect::getBusinessId,businessId);

        UserCollect collect = userCollectMapper.selectOne(queryWrapper);
        return collect == null ? Result.ok(false) : Result.ok(true);
    }

    /**
     * 取消收藏
     *
     * @param collectId 收集id
     * @return {@link Result}
     */
    @Override
    public Result cancelCollect(String collectId) {
        LambdaQueryWrapper<UserCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(collectId),UserCollect::getCollectId,collectId);
        int delete = userCollectMapper.delete(queryWrapper);

        return delete > 0 ? Result.ok("您已取消收藏") : Result.err("取消失败");
    }
}
