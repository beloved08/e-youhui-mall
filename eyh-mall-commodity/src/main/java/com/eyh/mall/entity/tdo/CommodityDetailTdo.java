package com.eyh.mall.entity.tdo;

import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.CommodityComment;
import com.eyh.mall.entity.merchantStores.Business;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME CommodityDetailTdo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-03-23 14:57:26
 * @Description 商品详细信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityDetailTdo {

    /**
     * 商品
     */
    private Commodity commodity;
    /**
     * 业务
     */
    private Business business;
    /**
     * 商品评论dto
     */
    private List<CommodityComment> commodityCommentList;

}
