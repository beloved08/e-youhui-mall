package com.eyh.mall.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 李平
 * @Date 2023-2-14
 */
public class TimeDateFormat {

    public static String customFormat(Date date) {
        // 2023-02-13T23:02:47.769+00:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(date);
        return dateNowStr;
    }
}
