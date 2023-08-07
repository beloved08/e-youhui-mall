package com.eyh.mall.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李平
 * @Date 2023-2-17
 */
@Component
public class JsonUtil<T> {

    /**
     * 将Redis查询的结果转换为实体对象
     * @return T
     */
    public T toJavaBean(Object obj, Class<T> clazz) {
        return JSON.parseObject(String.valueOf(JSON.parse(JSON.toJSONString(obj))),clazz);
    }

    /**
     * 将Redis查询List<Object>的结果转换为List<T>的实体集合
     * @return List<T>
     */
    public List<T> toListBean(List<Object> list, Class<T> clazz) {

        List<T> tList = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(list.get(0).toString());
        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            tList.add(JSON.parseObject(obj.toJSONString(), clazz));
        }

        return tList;
    }
}
