package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.ExpressOrder;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.logistics.ShipImmediatelyData;
import com.eyh.mall.entity.vo.ExpressOrderPage;

/**
 * @Author 李平
 * @NAME ExpressOrderService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 11:08:28
 * @Description 快递物流
 */
public interface ExpressOrderService extends IService<ExpressOrder> {

    /**
     * 节省表达顺序
     *
     * @param shipImmediatelyData 船立即数据
     * @param id                  id
     */
    void saveExpressOrder(ShipImmediatelyData shipImmediatelyData,String id);

    /**
     * 得到快递订单页面
     *
     * @param expressOrderPage 快递订单页面
     * @return {@link Result}
     */
    Result getExpressOrderPage(ExpressOrderPage expressOrderPage);

    /**
     * 得到快递订单细节
     *
     * @param orderNumber 订单号
     * @return {@link Result}
     */
    Result getExpressOrderDetail(String orderNumber);
}
