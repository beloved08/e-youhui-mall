package com.eyh.mall.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间工具类
 * @author 李平
 * @Date 2023-3-11
 */
public class TimeUtil {

    /**
     * 一天之前
     *
     * @param num 全国矿工工会
     * @return {@link String}
     */
    public static List<String> getBeforeDay(int num){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        calendar.setTime(currentDate);

        List<String> list = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            calendar.add(Calendar.DATE, -1);
            Date previousDate = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            list.add(sdf.format(previousDate));
        }
        return list;
    }

    /**
     * 获取系统当前时间
     * @return String
     */
    public static String getCurrentTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        return sdf.format(d);
    }

    /**
     * 日期时间比较
     *
     * @param currentTime 当前时间
     * @param compareTime 比较时间
     * @return boolean
     * @throws ParseException 解析异常
     */
    public static boolean dateTimeCompare(String currentTime, String compareTime) {
       try {
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
           long current = sdf.parse(currentTime).getTime();
           long compare = sdf.parse(compareTime).getTime();

           return current > compare;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }

    /**
     * 获得当前时间后随机时间
     *
     * @return {@link String}
     */
    public static String getCurrentTimeAfterRandomTime(long currentTime){

        long randomTime = currentTime + getRandomTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(randomTime));
    }

    /**
     * 有时间,米尔斯
     *
     * @param dateString 日期字符串
     * @return long
     * @throws ParseException 解析异常
     */
    public static long getTimeMillis(String dateString)  {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(dateString);
            return date.getTime();
        }catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 得到随机时间
     *
     * @return long
     */
    private static long getRandomTime() {
        Random random = new Random();
        // 生成1到24小时之间的随机时间
        long randomHour = random.nextInt(24) + 1;
        // 生成1到60分钟之间的随机时间
        long randomMinute = random.nextInt(60) + 1;
        // 生成1到60秒之间的随机时间
        long randomSecond = random.nextInt(60) + 1;
        // 计算随机时间的毫秒数
        return randomHour * 60 * 60 * 1000
                + randomMinute * 60 * 1000
                + randomSecond * 1000;
    }

}
