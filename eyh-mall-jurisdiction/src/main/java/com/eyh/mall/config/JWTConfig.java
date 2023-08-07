package com.eyh.mall.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * @author 李平
 * @Date 2023-1-29
 */
@Component
public class JWTConfig {

    @Value("${config.jwt.secret}")
    private String secret;

    @Value("${config.jwt.expire}")
    private long expire;

    /**
     * 生成token
     * @param subject
     * @return String
     */
    public String createToken (String subject){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("eyh", "MALL")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 获取token中注册信息
     * @param token
     * @return Claims
     */
    public Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 验证token是否过期失效
     * @param expirationTime
     * @return boolean
     */
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }


}
