package com.eyh.mall.rabbitmq.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eyh.mall.entity.Classification;
import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.ExcelClassification;
import com.eyh.mall.entity.ExcelCommodity;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.mapper.ClassificationMapper;
import com.eyh.mall.rabbitmq.CommodityConstant;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.ClassificationService;
import com.eyh.mall.service.CommodityService;
import com.eyh.mall.util.TimeUtil;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author 李平
 * @NAME SaveClassifitionListListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-03-19 15:30:34
 * @Description 保存商品分类数据监听类
 */
@Component
public class SaveClassifitionListListener {

    @Autowired
    private ClassificationMapper classificationMapper;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private JsonUtil<ExcelClassification> jsonUtil;

    @Autowired
    private JsonUtil<ExcelCommodity> excelCommodityJsonUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommodityApiClient commodityApiClient;

    /**
     * 保存分类列表
     *
     * @param key 关键
     */
    @RabbitListener(queues = CommodityConstant.SAVE_CLASSIFICATION_LIST_QUEUE)
    public void saveClassificationList(String key){
        // 从redis中取出分类数据
        List<ExcelClassification> list = jsonUtil.toListBean(
                redisUtil.lGet(key, 0, -1),
                ExcelClassification.class);
        // 数据解析
        List<Classification> c = new ArrayList<>();

        for (ExcelClassification ec : list) {
            // 判断该条分类数据为一级分类还是二级分类
            if ("0".equals(ec.getClassificationGrade())){
                //一级分类
                c.add(new Classification(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        ec.getClassificationName(),
                        ec.getClassificationDescribe(),
                        "",
                        0,
                        "",
                        1
                ));
            }else{
                // 二级分类
                LambdaQueryWrapper<Classification> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(!"".equals(ec.getParentName()),Classification::getClassificationName,ec.getParentName());
                wrapper.eq(true,Classification::getClassificationGrade,0);
                c.add(new Classification(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        ec.getClassificationName(),
                        ec.getClassificationDescribe(),
                        ec.getClassificationIcon(),
                        1,
                        classificationMapper.selectOne(wrapper).getClassificationId(),
                        1
                ));
            }
        }
        //批量导入数据库中
        boolean b = classificationService.saveBatch(c);

        if (b) {
            System.out.println("--------商品分类数量成功批量导入数据库--------");
        }else{
            System.err.println("--------商品分类数量批量导入数据库失败--------");
        }
    }

    // /**
    //  * 保存商品列表
    //  *
    //  * @param key 关键
    //  */
    // @RabbitListener(queues = CommodityConstant.SAVE_COMMODITY_LIST_QUEUE)
    // public void saveCommodityList(String key){
    //     // 从redis中取出分类数据
    //     List<ExcelCommodity> list = excelCommodityJsonUtil.toListBean(
    //             redisUtil.lGet(key, 0, -1),
    //             ExcelCommodity.class);
    //
    //     // 数据解析
    //     List<Commodity> c = new ArrayList<>();
    //     list.forEach(e -> {
    //         // 获取商家
    //         Business business = commodityApiClient.getBusiness(e.getBusinessName());
    //
    //         // 获取分类
    //         LambdaQueryWrapper<Classification> classificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
    //         classificationLambdaQueryWrapper.eq(
    //                 !"".equals(e.getTwoClassificationName()),
    //                 Classification::getClassificationName,
    //                 e.getTwoClassificationName()
    //         );
    //         //二级分类
    //         classificationLambdaQueryWrapper.eq(true,Classification::getClassificationGrade,1);
    //         Classification classification = classificationMapper.selectOne(classificationLambdaQueryWrapper);
    //
    //         String groundTime = "";
    //         if ("0".equals(e.getCommodityStatus())){
    //             // 立即上架
    //             groundTime = TimeUtil.getCurrentTime();
    //         }
    //
    //         c.add(new Commodity(
    //                 UUID.randomUUID().toString(),
    //                 UUID.randomUUID().toString(),
    //                 classification.getParentId(),
    //                 classification.getClassificationId(),
    //                 e.getCommodityName(),
    //                 e.getRetailPrice(),
    //                 e.getCommodityImageUrl(),
    //                 e.getCommodityDescribe(),
    //                 Integer.valueOf(e.getCommodityStatus()),
    //                 Integer.valueOf(e.getCommodityType()),
    //                 e.getMeterCompany(),
    //                 Integer.valueOf(e.getSalesModel()),
    //                 e.getCommodityWeight(),
    //                 Integer.valueOf(e.getCommodityRecommend()),
    //                 e.getWholesalePrice(),
    //                 TimeUtil.getCurrentTime(),
    //                 groundTime,
    //                 "",
    //                 "",
    //                 1,
    //                 business.getBusinessId(),
    //                 Integer.valueOf(e.getCommodityStock()),
    //                 2
    //         ));
    //     });
    //     boolean b = commodityService.saveBatch(c);
    //     if (b) {
    //         System.out.println("--------商品信息成功批量导入数据库--------");
    //     }else{
    //         System.err.println("--------商品信息批量导入数据库失败--------");
    //     }
    // }

}
