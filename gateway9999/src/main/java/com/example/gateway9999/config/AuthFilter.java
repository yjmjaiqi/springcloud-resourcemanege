package com.example.gateway9999.config;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.example.commons.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component("authFilter")
@Slf4j
@Configuration
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    private static final String AUTH = "token";
    private static final String[] URL_WHITELIST = {
            "/resource/register",
            "/resource/login",
            "/resource/updateUser",
            "/resource/userDetail"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Auth start");
        //获取request
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders header = request.getHeaders();
        String token = header.getFirst(AUTH);
//        String token = request.getQueryParams().getFirst(AUTH);
        String username = header.getFirst(JWTUtil.USER_NAME);
        ServerHttpResponse response = exchange.getResponse();
        //获取当前访问的路径
        String path = request.getURI().getPath();
        /**
         * 放行白名单
         */
        if (Arrays.asList(URL_WHITELIST).contains(path))
        {
            log.info("放行白名单{}",path);
            return chain.filter(exchange);
        }
        /**
         * 判断token是否为空
         */
        if (StringUtils.isBlank(token)) {
            log.error("token not found");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        boolean flag = JWTUtil.verify(token);
        /**
         * 验证token的合法性
         */
        if (!flag) {
            log.error("invalid token");
            log.error("token{}",token);
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        /**
         * 判断token是否过期
         */
        String s = stringRedisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(s))
        {
            log.error("登录状态已过期");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}