package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.ActivityCommodity;
import com.eyh.mall.entity.vo.CommodityBatch;
import com.eyh.mall.entity.vo.CommodityPage;
import com.eyh.mall.entity.vo.CommodityVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-2-18
 */
public interface CommodityService extends IService<Commodity> {

    /**
     * 上传商品图片
     *
     * @param file                  文件
     * @param commodityName         商品名称
     * @param businessName          业务名称
     * @param oneClassificationName 一个分类名称
     * @param twoClassificationName 两个分类名称
     * @return {@link Result}
     */
    Result uploadCommodityImage(MultipartFile file,
                                String businessName,
                                String oneClassificationName,
                                String twoClassificationName,
                                String commodityName);

    /**
     * 添加商品
     *
     * @param commodityVo 商品签证官
     * @return {@link Result}
     */
    Result addCommodity(CommodityVo commodityVo);

    /**
     * 得到商品页面
     *
     * @param commodityPage 商品页面
     * @return {@link Result}
     */
    Result getCommodityByPage(CommodityPage commodityPage);

    /**
     * 批量发布商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    Result batchLaunchCommodity(CommodityBatch commodityBatch);

    /**
     * 批量下架
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    Result batchOffShelfCommodity(CommodityBatch commodityBatch);


    /**
     * 批量推荐商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    Result batchRecommendationCommodity(CommodityBatch commodityBatch);

    /**
     * 批量把推荐商品修改为不推荐上
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    Result batchNoRecommendationCommodity(CommodityBatch commodityBatch);

    /**
     * 批量购买商品
     *
     * @param commodityBatch  商品批量
     * @return {@link Result}
     */
    Result bulkPurchaseCommodity(CommodityBatch commodityBatch);

    /**
     * 放入回收站商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    Result putInRecycleBinCommodity(CommodityBatch commodityBatch);

    /**
     * 商品批量上传
     *
     * @param file 文件
     * @return {@link Result}
     */
    Result commodityBatchUpload(MultipartFile file);

    /**
     * 被分类商品id
     *
     * @param classificationId 分类id
     * @return {@link Result}
     */
    Result getCommodityByClassificationId(String classificationId);

    /**
     * 获得商品细节
     *
     * @param commodityId 商品id
     * @return {@link Result}
     */
    Result getCommodityDetail(String commodityId);

    /**
     * 获得商品推荐
     *
     * @return {@link Result}
     */
    Result getCommodityRecommend();

    /**
     * 要旋转图商品
     *
     * @return {@link Result}
     */
    Result getRotationChartCommodity();

    /**
     * 得到指数展示商品
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}
     */
    Result getIndexShowCommodity(Integer currentPage, Integer pageSize);

    /**
     * 通过id获取商品
     *
     * @param commodityId 商品id
     * @return {@link Commodity}
     */
    Commodity getCommodityById(String commodityId);

    /**
     * 被商业商品id
     *
     * @param businessId 业务标识
     * @return {@link List}<{@link Commodity}>
     */
    List<Commodity> getCommodityByBusinessId(String businessId);

    /**
     * 获得商品列表
     *
     * @return {@link List}<{@link Commodity}>
     */
    List<Commodity> getCommodityList();

    /**
     * 由业务id获取商品页面
     *
     * @param businessId  业务标识
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}
     */
    Result getCommodityByBusinessIdPage(String businessId, Integer currentPage, Integer pageSize);

    /**
     * 扣除库存商品
     *
     * @param orderNumber 订单号
     * @param orderId     订单id
     */
    void deductionInventoryCommodity(String orderNumber,String orderId);

    /**
     * 搜索商品
     *
     * @param commodityName 商品名称
     * @return {@link Result}
     */
    Result searchCommodity(String commodityName);

    /**
     * 得到畅销商品
     *
     * @return {@link Result}
     */
    Result getBestSellersCommodity();

    /**
     * 让时间杀了商品
     *
     * @return {@link List}<{@link Commodity}>
     */
    List<Commodity> getTimeKillCommodity();

    /**
     * 获得促销活动商品
     *
     * @param activityCommodity 活动商品
     * @return {@link List}<{@link Commodity}>
     */
    List<Commodity> getPromotionActivityCommodity(ActivityCommodity activityCommodity);

    /**
     * 获得促销活动商品限制三个
     *
     * @param activityCommodity 活动商品
     * @return {@link List}<{@link Commodity}>
     */
    List<Commodity> getPromotionActivityCommodityLimitThree(ActivityCommodity activityCommodity);

    /**
     * 获得商品数据vlist
     *
     * @return {@link Result}
     */
    Result getCommodityDataVList();

    /**
     * 得到分类商品比例
     *
     * @return {@link Result}
     */
    Result getClassificationCommodityProportion();

}
