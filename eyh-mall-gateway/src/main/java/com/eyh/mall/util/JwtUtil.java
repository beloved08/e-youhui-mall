package com.eyh.mall.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;

/**
 * @author 李平
 * @Date 2023-1-30
 */
public class JwtUtil {
    /**
     * 获取token中注册信息
     * @param token
     * @return Claims
     */
    public static Claims getTokenClaim (String token,String secret) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 是否过期
     *
     * @param claims
     * @return -1：有效，0：有效，1：过期，2：过期
     */
    public static boolean verifyToken(Claims claims) {
        if(claims == null){
            return false;
        }
        try {
            // 判断当前日期是否在过期日期之前
            return claims.getExpiration()
                    .before(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 验证token是否过期失效
     * @param expirationTime
     * @return boolean
     */
    public static boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }
}
