package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.ExpressOrder;
import com.eyh.mall.entity.LogisticsCompany;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.ExpressOrderDto;
import com.eyh.mall.entity.dto.ExpressOrderTdo;
import com.eyh.mall.entity.logistics.ShipImmediatelyData;
import com.eyh.mall.entity.vo.ExpressOrderPage;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.entity.UserAddress;
import com.eyh.mall.mapper.ExpressOrderMapper;
import com.eyh.mall.mapper.LogisticsCompanyMapper;
import com.eyh.mall.service.ExpressOrderService;
import com.eyh.mall.service.LogisticsCompanyService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author 李平
 * @NAME ExpressOrderServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 11:09:18
 * @Description 快递物流
 */
@Service
public class ExpressOrderServiceImpl extends ServiceImpl<ExpressOrderMapper, ExpressOrder> implements ExpressOrderService {

    @Autowired
    private ExpressOrderMapper expressOrderMapper;

    @Autowired
    private LogisticsCompanyService logisticsCompanyService;

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private LogisticsCompanyMapper logisticsCompanyMapper;


    /**
     * 节省表达顺序
     *
     * @param shipImmediatelyData 船立即数据
     * @param id                  id
     */
    @Override
    public void saveExpressOrder(ShipImmediatelyData shipImmediatelyData, String id) {
        ExpressOrder expressOrder = new ExpressOrder();
        expressOrder.setId(UUID.randomUUID().toString());

        expressOrder.setExpressOrderId(id);
        expressOrder.setOrderNumber(shipImmediatelyData.getOrderNumber());
        expressOrder.setCreateTime(TimeUtil.getCurrentTime());
        // 物流公司
        LogisticsCompany logisticsCompany = logisticsCompanyService.selectLogisticsCompanyByName(shipImmediatelyData.getLogisticsCompany());
        expressOrder.setLogisticsCompanyId(logisticsCompany.getLogisticsCompanyId());
        //发送者
        expressOrder.setSenderName(shipImmediatelyData.getSenderName());
        expressOrder.setSenderPhone(shipImmediatelyData.getSenderPhone());
        expressOrder.setSenderAddress(shipImmediatelyData.getSenderAddress());
        //收件者
        UserAddress userAddress = userApiClient.getUserAddressById(shipImmediatelyData.getAddresseeId());
        expressOrder.setReceiverName(userAddress.getConsignee());
        expressOrder.setReceiverPhone(userAddress.getPhone());
        expressOrder.setReceiverAddress(userAddress.getDetailedAddress());

        expressOrderMapper.insert(expressOrder);

    }

    /**
     * 得到快递订单页面
     *
     * @param expressOrderPage 快递订单页面
     * @return {@link Result}
     */
    @Override
    public Result getExpressOrderPage(ExpressOrderPage expressOrderPage) {
        Page<ExpressOrder> page = new Page<>(expressOrderPage.getCurrentPage(), expressOrderPage.getPageSize());
        LambdaQueryWrapper<ExpressOrder> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(true,ExpressOrder::getCreateTime);
        queryWrapper.eq(!"".equals(expressOrderPage.getOrderNumber()), ExpressOrder::getOrderNumber, expressOrderPage.getOrderNumber());
        queryWrapper.eq(!"".equals(expressOrderPage.getSenderPhone()), ExpressOrder::getSenderPhone, expressOrderPage.getSenderPhone());
        queryWrapper.eq(!"".equals(expressOrderPage.getReceiverPhone()), ExpressOrder::getReceiverPhone, expressOrderPage.getReceiverPhone());
        queryWrapper.like(!"".equals(expressOrderPage.getSenderName()), ExpressOrder::getSenderName, expressOrderPage.getSenderName());
        queryWrapper.like(!"".equals(expressOrderPage.getReceiverName()), ExpressOrder::getReceiverName, expressOrderPage.getReceiverName());
        queryWrapper.like(!"".equals(expressOrderPage.getReceiverAddress()), ExpressOrder::getReceiverAddress, expressOrderPage.getReceiverAddress());
        queryWrapper.like(!"".equals(expressOrderPage.getSenderAddress()), ExpressOrder::getSenderAddress, expressOrderPage.getSenderAddress());

        Page<ExpressOrder> selectPage = expressOrderMapper.selectPage(page, queryWrapper);
        ExpressOrderTdo tdo = new ExpressOrderTdo();
        tdo.setCurrentPage(selectPage.getCurrent());
        tdo.setPageSize(selectPage.getPages());
        tdo.setTotal(selectPage.getTotal());
        tdo.setSize(selectPage.getSize());

        List<ExpressOrderDto> list = new ArrayList<>();
        selectPage.getRecords().forEach(e -> {
            ExpressOrderDto dto = new ExpressOrderDto();
            BeanUtils.copyProperties(e,dto);
            // 获取物流公司名称
            LambdaQueryWrapper<LogisticsCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(!"".equals(e.getLogisticsCompanyId()),LogisticsCompany::getLogisticsCompanyId,e.getLogisticsCompanyId());

            dto.setLogisticsCompanyName(logisticsCompanyMapper.selectOne(wrapper).getLogisticsCompanyName());
            list.add(dto);
        });

        tdo.setExpressOrderDtoList(list);
        return Result.ok(tdo);
    }

    /**
     * 得到快递订单细节
     *
     * @param orderNumber 订单号
     * @return {@link Result}
     */
    @Override
    public Result getExpressOrderDetail(String orderNumber) {
        LambdaQueryWrapper<ExpressOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(orderNumber),ExpressOrder::getOrderNumber,orderNumber);
        return Result.ok(expressOrderMapper.selectOne(queryWrapper));
    }
}
