package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.LogisticsTrack;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.logistics.ShipImmediatelyData;

/**
 * @Author 李平
 * @NAME LogisticsTrackService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 11:11:44
 * @Description 快递物流轨迹
 */
public interface LogisticsTrackService extends IService<LogisticsTrack> {

    /**
     * 节省物流跟踪
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     */
    void saveLogisticsTrack(String id, ShipImmediatelyData shipImmediatelyData);

    /**
     * 把物流跟踪细节
     *
     * @param expressOrderId 快递订单id
     * @return {@link Result}
     */
    Result getLogisticsTrackDetail(String expressOrderId);

    /**
     * 用户确认收到任务
     *
     * @param expressOrderId 快递订单id
     */
    void userConfirmReceiptTask(String expressOrderId);
}
