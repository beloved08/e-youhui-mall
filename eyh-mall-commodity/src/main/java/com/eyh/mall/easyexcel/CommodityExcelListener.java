package com.eyh.mall.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eyh.mall.entity.Classification;
import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.CommodityStock;
import com.eyh.mall.entity.ExcelCommodity;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.mapper.ClassificationMapper;
import com.eyh.mall.service.CommodityService;
import com.eyh.mall.service.CommodityStockService;
import com.eyh.mall.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author 李平
 * @NAME CommodityExcelListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-03-30 17:32:19
 * @Description easyExcel导入商品表格数据监听类
 */
@Slf4j
public class CommodityExcelListener implements ReadListener<ExcelCommodity> {

    private final CommodityService commodityService;

    private final CommodityStockService commodityStockService;

    private final ClassificationMapper classificationMapper;

    private final CommodityApiClient commodityApiClient;

    public CommodityExcelListener(CommodityStockService commodityStockService,CommodityApiClient commodityApiClient,CommodityService commodityService,ClassificationMapper classificationMapper) {
        this.commodityApiClient = commodityApiClient;
        this.commodityService = commodityService;
        this.commodityStockService = commodityStockService;
        this.classificationMapper = classificationMapper;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<ExcelCommodity> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 调用
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context 上下文
     */
    @Override
    public void invoke(ExcelCommodity data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 毕竟做分析
     * 所有数据解析完成了 都会来调用
     *
     * @param context 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("---------------------所有数据解析完成---------------------");
    }

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());

        // 数据解析
        List<Commodity> c = new ArrayList<>();
        List<CommodityStock> s = new ArrayList<>();
        cachedDataList.forEach(e -> {
            // 获取商家
            Business business = commodityApiClient.getBusiness(e.getBusinessName());

            // 获取分类
            LambdaQueryWrapper<Classification> classificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            classificationLambdaQueryWrapper.eq(
                    !"".equals(e.getTwoClassificationName()),
                    Classification::getClassificationName,
                    e.getTwoClassificationName()
            );
            //二级分类
            classificationLambdaQueryWrapper.eq(true,Classification::getClassificationGrade,1);
            Classification classification = classificationMapper.selectOne(classificationLambdaQueryWrapper);

            String groundTime = "";
            if ("0".equals(e.getCommodityStatus())){
                // 立即上架
                groundTime = TimeUtil.getCurrentTime();
            }
            Commodity commodity = new Commodity(
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    classification.getParentId(),
                    classification.getClassificationId(),
                    e.getCommodityName(),
                    e.getRetailPrice(),
                    e.getCommodityImageUrl(),
                    e.getCommodityDescribe(),
                    Integer.valueOf(e.getCommodityStatus()),
                    Integer.valueOf(e.getCommodityType()),
                    e.getMeterCompany(),
                    Integer.valueOf(e.getSalesModel()),
                    e.getCommodityWeight(),
                    Integer.valueOf(e.getCommodityRecommend()),
                    e.getWholesalePrice(),
                    TimeUtil.getCurrentTime(),
                    groundTime,
                    "",
                    "",
                    1,
                    business.getBusinessId(),
                    Integer.valueOf(e.getCommodityStock()),
                    2
            );
            c.add(commodity);
            s.add(new CommodityStock(
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    0,
                    TimeUtil.getCurrentTime(),
                    commodity.getCommodityStock(),
                    "",
                    commodity.getCommodityId()
            ));
        });
        boolean b = commodityService.saveBatch(c);
        commodityStockService.saveBatch(s);
        if (b) {
            System.out.println("--------商品信息成功批量导入数据库--------");
        }else{
            System.err.println("--------商品信息批量导入数据库失败--------");
        }
        log.info("存储数据库成功！");
    }

}
