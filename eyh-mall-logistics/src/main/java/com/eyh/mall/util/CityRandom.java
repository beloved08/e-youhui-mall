package com.eyh.mall.util;

import java.util.Random;

/**
 * @Author 李平
 * @NAME CityRandom
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 15:16:29
 * @Description 生成随机城市节点
 */
public class CityRandom {

    private static final String[] CITIES = {"北京", "上海", "广州", "深圳", "成都", "重庆", "武汉", "南京", "杭州", "西安", "长沙", "厦门", "青岛", "大连", "沈阳", "哈尔滨", "南宁", "昆明", "贵阳", "福州", "南昌", "济南", "石家庄", "太原", "呼和浩特", "兰州", "西宁", "银川", "乌鲁木齐"};

    /**
     * 得到随机城市
     *
     * @return {@link String}
     */
    public static String getRandomCity() {
        Random random = new Random();
        int index = random.nextInt(CITIES.length);
        return CITIES[index];
    }


    /**
     * 排除元素
     * int[] arr = {1, 2, 3, 4, 5};
     * int[] result = excludeElements(arr, 2, 4);
     * System.out.println(Arrays.toString(result)); // [1, 3, 5]
     *
     * @param exclude 排除
     * @return {@link String[]}
     */
    public static String[] excludeElements( String... exclude) {
        String[] result = new String[CITIES.length - exclude.length];
        int index = 0;
        for (int i = 0; i < CITIES.length; i++) {
            boolean shouldExclude = false;
            for (int j = 0; j < exclude.length; j++) {
                if (CITIES[i] == exclude[j]) {
                    shouldExclude = true;
                    break;
                }
            }
            if (!shouldExclude) {
                result[index++] = CITIES[i];
            }
        }
        return result;
    }
}
