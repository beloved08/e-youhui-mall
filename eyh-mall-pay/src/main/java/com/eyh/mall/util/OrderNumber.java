package com.eyh.mall.util;

/**
 * @Author 李平
 * @NAME OrderNumber
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 13:29:40
 * @Description 生成订单号
 */
public class OrderNumber {

    /**
     * 创建订单号
     *
     * @return {@link String}
     */
    public static String createOrderNumber(){
        StringBuilder orderNum = new StringBuilder();
        for(int i = 0; i < 19; i++){
            orderNum.append((int) (Math.random() * 10));
        }
        return orderNum.toString();
    }
}
