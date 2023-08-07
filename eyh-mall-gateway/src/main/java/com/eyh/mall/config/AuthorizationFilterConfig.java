package com.eyh.mall.config;

import com.eyh.mall.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * @author 李平
 * @Date 2023-1-18
 */
@Component
// @Order(-1)
public class AuthorizationFilterConfig implements GlobalFilter, Ordered {

    @Value("${admin.login}")
    private String loginUrl;

    @Value("${config.jwt.secret}")
    private String secret;

    /**
     * 通过api列表
     */
     String []passApiList = new String[]
            {       "/jurisdiction/wx",
                    "/commodity/getClassificationByPage",
                    "/commodity/getCommodityByClassificationId",
                    "/commodity/getCommodityDetail",
                    "/commodity/getRecommendCommodity",
                    "/commodity/getRotationChartCommodity",
                    "/commodity/getIndexShowCommodity",
                    "/marketing/getLimitedTimeFlashKillCommodity",
                    "/commodity/getBestSellersCommodity",
                    "/marketing/getTimeKillCommodityPage",
                    "/marketing/getProgressPromotionActivity",
                    "/marketing/getPromotionActivityCommodityById"
            };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        RequestPath path = request.getPath();
        if (loginUrl.equals(path.toString())) {
            // 放行
            return chain.filter(exchange);
        }
        // 微信小程序白名单
        for (int i = 0; i < passApiList.length; i++){
            if(path.toString().startsWith(passApiList[i])){
                // 放行
                return chain.filter(exchange);
            }
        }

        HttpHeaders headers = request.getHeaders();
        String token = Objects.requireNonNull(headers.get("authorization")).get(0);

        // 判断token是否为空
        if(StringUtils.isEmpty(token)){
            // 拦截
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        try {
            Claims claim = JwtUtil.getTokenClaim(token, secret);
            boolean b = JwtUtil.verifyToken(claim);
            if (!b){
                // 放行
                return chain.filter(exchange);
            }else{
                // token过期
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }catch (Exception e){
            // 拦截
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    /**
     * 过滤器执行顺序
     * @return int
     */
    @Override
    public int getOrder() {
        return -1;
    }

}
