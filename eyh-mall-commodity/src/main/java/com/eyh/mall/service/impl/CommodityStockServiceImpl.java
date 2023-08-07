package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.CommodityStock;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.tdo.CommodityStockDto;
import com.eyh.mall.entity.tdo.CommodityStockTdo;
import com.eyh.mall.entity.vo.CommodityStockPage;
import com.eyh.mall.feign.client.pay.OrderApiClient;
import com.eyh.mall.feign.entity.Order;
import com.eyh.mall.mapper.CommodityMapper;
import com.eyh.mall.mapper.CommodityStockMapper;
import com.eyh.mall.service.CommodityStockService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author 李平
 * @NAME CommodityStockServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 09:58:27
 * @Description 商品库存
 */
@Service
public class CommodityStockServiceImpl extends ServiceImpl<CommodityStockMapper, CommodityStock> implements CommodityStockService {

    @Autowired
    private CommodityStockMapper commodityStockMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private OrderApiClient orderApiClient;

    /**
     * 扣除库存商品
     *
     * @param commodityId      商品id
     * @param orderNumber      订单号
     * @param purchaseQuantity 采购量
     */
    @Override
    public void deductionInventoryGoods(String commodityId, String orderNumber, Integer purchaseQuantity) {
        commodityStockMapper.insert(new CommodityStock(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                1,
                TimeUtil.getCurrentTime(),
                purchaseQuantity,
                orderNumber,
                commodityId
        ));
    }

    /**
     * 增加产品库存
     *
     * @param commodityId     商品id
     * @param commodityNumber 商品数量
     */
    @Override
    public void increaseProductInventory(String commodityId, Integer commodityNumber) {
        commodityStockMapper.insert(new CommodityStock(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                0,
                TimeUtil.getCurrentTime(),
                commodityNumber,
                "",
                commodityId
        ));
    }

    /**
     * 获得大宗商品股票页面
     *
     * @param commodityStockPage 大宗商品股票页面
     * @return {@link Result}
     */
    @Override
    public Result getCommodityStockPage(CommodityStockPage commodityStockPage) {
        IPage<CommodityStock> page = new Page<CommodityStock>(commodityStockPage.getCurrentPage(), commodityStockPage.getPageSize());

        LambdaQueryWrapper<CommodityStock> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(true,CommodityStock::getChangeTime);

        //时间
        queryWrapper.ge(!"".equals(commodityStockPage.getStartTime()), CommodityStock::getChangeTime, commodityStockPage.getStartTime());
        queryWrapper.le(!"".equals(commodityStockPage.getEndTime()), CommodityStock::getChangeTime, commodityStockPage.getEndTime());

        //订单号
        queryWrapper.eq(!"".equals(commodityStockPage.getOrderNumber()), CommodityStock::getOrderNumber, commodityStockPage.getOrderNumber());
        //类型 -1：全部
        queryWrapper.eq(commodityStockPage.getType() != -1, CommodityStock::getType, commodityStockPage.getType());

        IPage<CommodityStock> selectPage = commodityStockMapper.selectPage(page, queryWrapper);
        CommodityStockTdo tdo = new CommodityStockTdo();
        tdo.setCurrentPage(selectPage.getCurrent());
        tdo.setPageSize(selectPage.getPages());
        tdo.setTotal(selectPage.getTotal());
        List<CommodityStockDto> list = new ArrayList<CommodityStockDto>();
        selectPage.getRecords().forEach(s -> {
            CommodityStockDto commodityStockDto = new CommodityStockDto();
            BeanUtils.copyProperties(s, commodityStockDto);

            //查询商品名称
            LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();

            wrapper.eq(!"".equals(s.getCommodityId()), Commodity::getCommodityId, s.getCommodityId());
            Commodity commodity = commodityMapper.selectOne(wrapper);
            commodityStockDto.setCommodityName(commodity.getCommodityName());

            list.add(commodityStockDto);
        });

        tdo.setCommodityStockDtoList(list);

        return Result.ok(tdo);
    }

    /**
     * 获得大宗商品股票细节
     *
     * @param orderNumber 订单号
     * @return {@link Result}
     */
    @Override
    public Result getCommodityStockDetail(String orderNumber) {
        if ("".equals(orderNumber)){
            return Result.err("订单号不能为空");
        }
        String orderByNumber = orderApiClient.getOrderByNumber(orderNumber);
        if ("".equals(orderByNumber)){
            return Result.err("数据为空");
        }
        return orderApiClient.getOrderDetail(orderByNumber);
    }
}
