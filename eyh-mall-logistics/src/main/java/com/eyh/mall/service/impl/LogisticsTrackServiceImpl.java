package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.ExpressOrder;
import com.eyh.mall.entity.LogisticsTrack;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.logistics.ShipImmediatelyData;
import com.eyh.mall.entity.sms.SmsData;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.feign.entity.UserAddress;
import com.eyh.mall.mapper.ExpressOrderMapper;
import com.eyh.mall.mapper.LogisticsTrackMapper;
import com.eyh.mall.rabbitmq.LogisticsConstant;
import com.eyh.mall.service.LogisticsTrackService;
import com.eyh.mall.util.CityRandom;
import com.eyh.mall.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author 李平
 * @NAME LogisticsTrackServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 11:12:15
 * @Description 快递物流轨迹
 */
@Service
public class LogisticsTrackServiceImpl extends ServiceImpl<LogisticsTrackMapper, LogisticsTrack> implements LogisticsTrackService {

    @Autowired
    private LogisticsTrackMapper logisticsTrackMapper;

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ExpressOrderMapper expressOrderMapper;

    /**
     * 节省物流跟踪
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     */
    @Override
    public void saveLogisticsTrack(String id, ShipImmediatelyData shipImmediatelyData) {
        save(id,shipImmediatelyData);
        save1(id,shipImmediatelyData,"已揽件-" + id);
        save2(id,shipImmediatelyData,"已发货-" + id);
        save3(id,shipImmediatelyData,"运输中-1" + id);
        save4(id,shipImmediatelyData,"运输中-2" + id);
        save5(id,shipImmediatelyData,"运输中-3" + id);
        save6(id,shipImmediatelyData,"运输中-4" + id);
        save7(id,shipImmediatelyData,"派件中-" + id);
    }

    /**
     * 把物流跟踪细节
     *
     * @param expressOrderId 快递订单id
     * @return {@link Result}
     */
    @Override
    public Result getLogisticsTrackDetail(String expressOrderId) {
        LambdaQueryWrapper<LogisticsTrack> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(expressOrderId),LogisticsTrack::getExpressOrderId,expressOrderId);
        queryWrapper.orderByAsc(true,LogisticsTrack::getUpdateTime);

        return Result.ok(logisticsTrackMapper.selectList(queryWrapper));
    }

    /**
     * 用户确认收到任务
     *
     * @param expressOrderId 快递订单id
     */
    @Override
    public void userConfirmReceiptTask(String expressOrderId) {
        LambdaQueryWrapper<ExpressOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(expressOrderId),ExpressOrder::getExpressOrderId,expressOrderId);
        ExpressOrder expressOrder = expressOrderMapper.selectOne(queryWrapper);

        LogisticsTrack logisticsTrack = new LogisticsTrack();
        logisticsTrack.setId(UUID.randomUUID().toString());
        logisticsTrack.setLogisticsTrackId(UUID.randomUUID().toString());
        logisticsTrack.setExpressOrderId(expressOrderId);
        logisticsTrack.setCreateTime(TimeUtil.getCurrentTime());
        logisticsTrack.setLogisticsStatus("已签收-" + expressOrderId);
        logisticsTrack.setLogisticsDesc("您已在" + expressOrder.getReceiverAddress() + "完成取件，感谢使用，期待再次为您服务");
        logisticsTrack.setLogisticsTime(TimeUtil.getCurrentTime());
        logisticsTrack.setUpdateTime(TimeUtil.getCurrentTime());
        logisticsTrackMapper.insert(logisticsTrack);

        //发送短信通知
        SmsData smsData = new SmsData();
        smsData.setPhone(expressOrder.getReceiverPhone());
        smsData.setTemplate("SMS_276255397");
        rabbitTemplate.convertAndSend(LogisticsConstant.EXPRESS_DELIVERY_STATION_SIGNATURE_QUEUE,
                JSON.toJSONString(smsData));

    }

    /**
     * 保存
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     */
    void save(String id, ShipImmediatelyData shipImmediatelyData){
        LogisticsTrack logisticsTrack = new LogisticsTrack();
        logisticsTrack.setId(UUID.randomUUID().toString());
        logisticsTrack.setLogisticsTrackId(UUID.randomUUID().toString());
        logisticsTrack.setExpressOrderId(id);
        logisticsTrack.setCreateTime(TimeUtil.getCurrentTime());
        logisticsTrack.setLogisticsStatus("已揽件-" + id);
        logisticsTrack.setLogisticsDesc("快递在" + shipImmediatelyData.getSenderAddress() + "已揽件");
        logisticsTrack.setLogisticsTime(TimeUtil.getCurrentTime());
        logisticsTrack.setUpdateTime(TimeUtil.getCurrentTime());
        logisticsTrackMapper.insert(logisticsTrack);
    }

    /**
     * save1
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     * @param status              状态
     */
    void save1(String id, ShipImmediatelyData shipImmediatelyData,String status){
        System.err.println("-------------------------已发货---------------------------");
        //已发货
        LogisticsTrack logisticsTrack = new LogisticsTrack();
        logisticsTrack.setId(UUID.randomUUID().toString());
        logisticsTrack.setLogisticsTrackId(UUID.randomUUID().toString());
        logisticsTrack.setExpressOrderId(id);
        logisticsTrack.setCreateTime(TimeUtil.getCurrentTime());
        logisticsTrack.setLogisticsStatus("已发货-" + id);
        logisticsTrack.setLogisticsDesc("快递在" + shipImmediatelyData.getSenderAddress() + "已发货");

        LambdaQueryWrapper<LogisticsTrack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,LogisticsTrack::getLogisticsStatus,status);
        LogisticsTrack track = logisticsTrackMapper.selectOne(wrapper);
        long millis = TimeUtil.getTimeMillis(track.getUpdateTime());
        String time = TimeUtil.getCurrentTimeAfterRandomTime(millis);
        logisticsTrack.setLogisticsTime(time);
        logisticsTrack.setUpdateTime(time);

        logisticsTrackMapper.insert(logisticsTrack);

        // 发送RabbitMQ发送短信
        LambdaQueryWrapper<ExpressOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(id),ExpressOrder::getExpressOrderId,id);
        ExpressOrder one = expressOrderMapper.selectOne(queryWrapper);

        SmsData smsData = new SmsData();
        smsData.setPhone(one.getReceiverPhone());
        MallUser mallUser = userApiClient.getMallUserByUserId(shipImmediatelyData.getPlaceOrderUserId());
        UserAddress userAddress = userApiClient.getUserAddressById(shipImmediatelyData.getAddresseeId());
        if (mallUser.getPhone().equals(userAddress.getPhone())) {
            //同一人
            smsData.setTemplate("SMS_276245121");
        }else{
            smsData.setTemplate("SMS_276115234");
        }
        rabbitTemplate.convertAndSend(LogisticsConstant.EXPRESS_DELIVERY_STATION_SIGNATURE_QUEUE,
                JSON.toJSONString(smsData));
    }

    /**
     * save2
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     * @param status              状态
     */
    void save2(String id, ShipImmediatelyData shipImmediatelyData,String status){
        //运输中，到达某个地点
        String randomCity = CityRandom.getRandomCity();
        LogisticsTrack logisticsTrack = new LogisticsTrack();
        logisticsTrack.setId(UUID.randomUUID().toString());
        logisticsTrack.setLogisticsTrackId(UUID.randomUUID().toString());
        logisticsTrack.setExpressOrderId(id);
        logisticsTrack.setCreateTime(TimeUtil.getCurrentTime());
        logisticsTrack.setLogisticsStatus("运输中-1" + id);
        logisticsTrack.setLogisticsDesc("快递送达" + randomCity + "市");

        LambdaQueryWrapper<LogisticsTrack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,LogisticsTrack::getLogisticsStatus,status);
        LogisticsTrack track = logisticsTrackMapper.selectOne(wrapper);
        long millis = TimeUtil.getTimeMillis(track.getUpdateTime());
        String time = TimeUtil.getCurrentTimeAfterRandomTime(millis);
        logisticsTrack.setLogisticsTime(time);
        logisticsTrack.setUpdateTime(time);

        logisticsTrackMapper.insert(logisticsTrack);
        System.err.println("------------------------- "+ randomCity + "---------------------------");
    }

    /**
     * save3
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     * @param status              状态
     */
    void save3(String id, ShipImmediatelyData shipImmediatelyData,String status){
        //运输中，到达某个地点
        String randomCity = CityRandom.getRandomCity();
        LogisticsTrack logisticsTrack = new LogisticsTrack();
        logisticsTrack.setId(UUID.randomUUID().toString());
        logisticsTrack.setLogisticsTrackId(UUID.randomUUID().toString());
        logisticsTrack.setExpressOrderId(id);
        logisticsTrack.setCreateTime(TimeUtil.getCurrentTime());
        logisticsTrack.setLogisticsStatus("运输中-2" + id);
        logisticsTrack.setLogisticsDesc("快递送达" + randomCity + "市");

        LambdaQueryWrapper<LogisticsTrack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,LogisticsTrack::getLogisticsStatus,status);
        LogisticsTrack track = logisticsTrackMapper.selectOne(wrapper);
        long millis = TimeUtil.getTimeMillis(track.getUpdateTime());
        String time = TimeUtil.getCurrentTimeAfterRandomTime(millis);
        logisticsTrack.setLogisticsTime(time);
        logisticsTrack.setUpdateTime(time);

        logisticsTrackMapper.insert(logisticsTrack);
        System.err.println("------------------------- "+ randomCity + "---------------------------");
    }

    /**
     * save4
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     * @param status              状态
     */
    void save4(String id, ShipImmediatelyData shipImmediatelyData,String status){
        //运输中，到达某个地点
        String randomCity = CityRandom.getRandomCity();
        LogisticsTrack logisticsTrack = new LogisticsTrack();
        logisticsTrack.setId(UUID.randomUUID().toString());
        logisticsTrack.setLogisticsTrackId(UUID.randomUUID().toString());
        logisticsTrack.setExpressOrderId(id);
        logisticsTrack.setCreateTime(TimeUtil.getCurrentTime());
        logisticsTrack.setLogisticsStatus("运输中-3" + id);
        logisticsTrack.setLogisticsDesc("快递送达" + randomCity + "市");

        LambdaQueryWrapper<LogisticsTrack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,LogisticsTrack::getLogisticsStatus,status);
        LogisticsTrack track = logisticsTrackMapper.selectOne(wrapper);
        long millis = TimeUtil.getTimeMillis(track.getUpdateTime());
        String time = TimeUtil.getCurrentTimeAfterRandomTime(millis);
        logisticsTrack.setLogisticsTime(time);
        logisticsTrack.setUpdateTime(time);

        logisticsTrackMapper.insert(logisticsTrack);
        System.err.println("------------------------- "+ randomCity + "---------------------------");
    }

    /**
     * save5
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     * @param status              状态
     */
    void save5(String id, ShipImmediatelyData shipImmediatelyData,String status){

        //运输中，到达某个地点
        String randomCity = CityRandom.getRandomCity();
        LogisticsTrack logisticsTrack = new LogisticsTrack();
        logisticsTrack.setId(UUID.randomUUID().toString());
        logisticsTrack.setLogisticsTrackId(UUID.randomUUID().toString());
        logisticsTrack.setExpressOrderId(id);
        logisticsTrack.setCreateTime(TimeUtil.getCurrentTime());
        logisticsTrack.setLogisticsStatus("运输中-4" + id);
        logisticsTrack.setLogisticsDesc("快递送达" + randomCity + "市");

        LambdaQueryWrapper<LogisticsTrack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,LogisticsTrack::getLogisticsStatus,status);
        LogisticsTrack track = logisticsTrackMapper.selectOne(wrapper);
        long millis = TimeUtil.getTimeMillis(track.getUpdateTime());
        String time = TimeUtil.getCurrentTimeAfterRandomTime(millis);
        logisticsTrack.setLogisticsTime(time);
        logisticsTrack.setUpdateTime(time);

        logisticsTrackMapper.insert(logisticsTrack);

        System.err.println("------------------------- "+ randomCity + "---------------------------");

    }

    /**
     * save6
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     * @param status              状态
     */
    void save6(String id, ShipImmediatelyData shipImmediatelyData,String status){
        System.err.println("------------------------- 派件中 ---------------------------");

        LambdaQueryWrapper<ExpressOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(id),ExpressOrder::getExpressOrderId,id);
        ExpressOrder one = expressOrderMapper.selectOne(queryWrapper);
        //已送达，正在派件中
        LogisticsTrack logisticsTrack = new LogisticsTrack();
        logisticsTrack.setId(UUID.randomUUID().toString());
        logisticsTrack.setLogisticsTrackId(UUID.randomUUID().toString());
        logisticsTrack.setExpressOrderId(id);
        logisticsTrack.setCreateTime(TimeUtil.getCurrentTime());
        logisticsTrack.setLogisticsStatus("派件中-" + id);
        logisticsTrack.setLogisticsDesc("快递送达" + one.getReceiverAddress() + "，正在为您派件");

        LambdaQueryWrapper<LogisticsTrack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,LogisticsTrack::getLogisticsStatus,status);
        LogisticsTrack track = logisticsTrackMapper.selectOne(wrapper);
        long millis = TimeUtil.getTimeMillis(track.getUpdateTime());
        String time = TimeUtil.getCurrentTimeAfterRandomTime(millis);
        logisticsTrack.setLogisticsTime(time);
        logisticsTrack.setUpdateTime(time);

        logisticsTrackMapper.insert(logisticsTrack);
    }

    /**
     * save7
     *
     * @param id                  id
     * @param shipImmediatelyData 船立即数据
     * @param status              状态
     */
    void save7(String id, ShipImmediatelyData shipImmediatelyData,String status){
        System.err.println("------------------------- 待签收 ---------------------------");

        LambdaQueryWrapper<ExpressOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(id),ExpressOrder::getExpressOrderId,id);
        ExpressOrder one = expressOrderMapper.selectOne(queryWrapper);

        //派送中，通知签收
        LogisticsTrack logisticsTrack = new LogisticsTrack();
        logisticsTrack.setId(UUID.randomUUID().toString());
        logisticsTrack.setLogisticsTrackId(UUID.randomUUID().toString());
        logisticsTrack.setExpressOrderId(id);
        logisticsTrack.setCreateTime(TimeUtil.getCurrentTime());
        logisticsTrack.setLogisticsStatus("待签收-" + id);
        logisticsTrack.setLogisticsDesc("快递已送达" + one.getReceiverAddress() + "收件快递点，请尽快前往签收");

        LambdaQueryWrapper<LogisticsTrack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,LogisticsTrack::getLogisticsStatus,status);
        LogisticsTrack track = logisticsTrackMapper.selectOne(wrapper);
        long millis = TimeUtil.getTimeMillis(track.getUpdateTime());
        String time = TimeUtil.getCurrentTimeAfterRandomTime(millis);
        logisticsTrack.setLogisticsTime(time);
        logisticsTrack.setUpdateTime(time);

        logisticsTrackMapper.insert(logisticsTrack);

        // 发送RabbitMQ发送短信
        SmsData smsData = new SmsData();
        smsData.setPhone(one.getReceiverPhone());
        MallUser mallUser = userApiClient.getMallUserByUserId(shipImmediatelyData.getPlaceOrderUserId());
        UserAddress userAddress = userApiClient.getUserAddressById(shipImmediatelyData.getAddresseeId());
        if (mallUser.getPhone().equals(userAddress.getPhone())) {
            //同一人
            smsData.setTemplate("SMS_276230156");
        }else{
            smsData.setTemplate("SMS_276035263");
        }
        rabbitTemplate.convertAndSend(LogisticsConstant.EXPRESS_DELIVERY_STATION_SIGNATURE_QUEUE,
                JSON.toJSONString(smsData));
    }

}
