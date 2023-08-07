package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.PayWay;
import com.eyh.mall.mapper.PayWayMapper;
import com.eyh.mall.service.PayWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 李平
 * @NAME PayWayServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 15:53:51
 * @Description 支付方式
 */
@Service
public class PayWayServiceImpl extends ServiceImpl<PayWayMapper, PayWay> implements PayWayService {

    @Autowired
    private PayWayMapper payWayMapper;

}
