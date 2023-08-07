package com.eyh.mall.entity.redis;

/**
 * @Author 李平
 * @NAME ClassificationRedis
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-18 16:23:03
 * @Description 商品分类Redis的key封装类
 */
public class ClassificationRedis {

    /**
     * 分类id
     */
    public static final String CLASSIFICATION_ID = "classification_id:";
    /**
     * 分类id ttl
     */
    public static final long CLASSIFICATION_ID_TTL = 300L;

    /**
     * excel分类列表
     */
    public static final String CLASSIFICATION_EXCEL_LIST = "classification_excel_list:";

    /**
     * 分类excel ttl
     */
    public static final long CLASSIFICATION_EXCEL_LIST_TTL = 30*60L;

    /**
     * excel分类列表
     */
    public static final String COMMODITY_EXCEL_LIST = "commodity_excel_list:";

    /**
     * 分类excel ttl
     */
    public static final long COMMODITY_EXCEL_LIST_TTL = 30*60L;

    /**
     * 二级分类
     */
    public static final String CLASSIFICATION_TWO_NAME_LIST = "classification_two_name_list:";

    /**
     * 二级分类TTL
     */
    public static final long CLASSIFICATION_TWO_NAME_LIST_TTL = 200L;

    /**
     * 一级分类
     */
    public static final String CLASSIFICATION_ONE_NAME_LIST = "classification_one_name_list:";

    /**
     * 一级分类TTL
     */
    public static final long CLASSIFICATION_ONE_NAME_LIST_TTL = 200L;

    /**
     * 商品分类id列表
     */
    public static final String COMMODITY_CLASSIFICATION_ID_LIST = "commodity_classification_id_list:";

    /**
     * 商品分类ttl id列表
     */
    public static final long COMMODITY_CLASSIFICATION_ID_LIST_TTL = 200L;

    /**
     * 商品细节
     */
    public static final String COMMODITY_DETAIL = "commodity_detail:";

    /**
     * 商品细节ttl
     */
    public static final long COMMODITY_DETAIL_TTL = 200L;

}
