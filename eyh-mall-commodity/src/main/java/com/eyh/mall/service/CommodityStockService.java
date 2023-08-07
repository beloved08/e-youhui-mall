package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.CommodityStock;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.CommodityStockPage;

/**
 * @Author 李平
 * @NAME CommodityStockService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 09:57:56
 * @Description 商品库存
 */
public interface CommodityStockService extends IService<CommodityStock> {

    /**
     * 扣除库存商品
     *
     * @param commodityId      商品id
     * @param orderNumber      订单号
     * @param purchaseQuantity 采购量
     */
    void deductionInventoryGoods(String commodityId, String orderNumber, Integer purchaseQuantity);

    /**
     * 增加产品库存
     *
     * @param commodityId     商品id
     * @param commodityNumber 商品数量
     */
    void increaseProductInventory(String commodityId, Integer commodityNumber);

    /**
     * 获得大宗商品股票页面
     *
     * @param commodityStockPage 大宗商品股票页面
     * @return {@link Result}
     */
    Result getCommodityStockPage(CommodityStockPage commodityStockPage);

    /**
     * 获得大宗商品股票细节
     *
     * @param orderNumber 订单号
     * @return {@link Result}
     */
    Result getCommodityStockDetail(String orderNumber);

}
