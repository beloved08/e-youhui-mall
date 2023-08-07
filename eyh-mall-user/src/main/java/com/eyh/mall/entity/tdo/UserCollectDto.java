package com.eyh.mall.entity.tdo;

import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.feign.entity.MallUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UserCollectDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-03-27 21:53:31
 * @Description 表格显示的字段数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectDto {

    /**
     * 用户名
     */
    private String username;
    /**
     * 收集id
     */
    private String collectId;

    /**
     * 商品名称
     */
    private String commodityName;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 收集时间
     */
    private String collectTime;
    /**
     * 类型
     */
    private Integer type;

    /**
     * 商品
     */
    private Commodity commodity;

    /**
     * 业务
     */
    private Business business;

    /**
     * 商城用户
     */
    private MallUser mallUser;

}
