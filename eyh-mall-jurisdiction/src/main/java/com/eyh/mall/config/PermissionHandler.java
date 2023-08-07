package com.eyh.mall.config;

import com.eyh.mall.entity.common.Result;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 李平
 * @Date 2023-1-29
 */
@RestControllerAdvice
public class PermissionHandler {

    @ExceptionHandler(value = { SignatureException.class })
    @ResponseBody
    public Result authorizationException(SignatureException e){
        return Result.err(1008,e.getMessage());
    }

}
