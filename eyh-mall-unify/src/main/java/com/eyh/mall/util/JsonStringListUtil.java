package com.eyh.mall.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @Author 李平
 * @NAME JsonStringListUtil
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-03-20 09:50:21
 * @Description List<String>与String与String[]转换
 */
public class JsonStringListUtil {

    /**
     * 数组字符串
     *
     * @param str str
     * @return {@link String[]}
     */
    public static String[] toArrayString(String str) {
        JSONArray jsonArray = JSON.parseArray(str);
        return jsonArray.toArray(new String[jsonArray.size()]);
    }
}
