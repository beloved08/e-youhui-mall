package com.eyh.mall.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结果
 *
 * @author 李平
 * @Date 2023-1-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Integer code;
    private Object data;
    private String msg;

    public static Result ok(Object data){
        Result r = new Result();
        r.setCode(0);
        r.setData(data);
        r.setMsg("操作执行成功");
        return r;
    }

    public static Result ok(String msg, Object data){
        Result r = new Result();
        r.setCode(0);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static Result err(String msg){
        Result r = new Result();
        r.setCode(-1);
        r.setData(null);
        r.setMsg(msg);
        return r;
    }

    public static Result err(String msg, Object data){
        Result r = new Result();
        r.setCode(-1);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static Result err(Integer code, String msg){
        Result r = new Result();
        r.setCode(code);
        r.setData(null);
        r.setMsg(msg);
        return r;
    }

    public static Result pk(int code, String msg, Object data){
        Result r = new Result();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

}
