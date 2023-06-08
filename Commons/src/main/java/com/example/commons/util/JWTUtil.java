package com.example.commons.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.commons.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JWTUtil {
    //30分钟
    public final static long EXPIRE_TIME = 30 * 60 * 1000;
    public static final String USER_NAME = "username";
    public static final String KEY = "changeId";
    public static final String ISSUER = "yan";

    /**
     * 生成token header.payload.sing
     */
    public static String token(User user) {
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(KEY);

        String token = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + EXPIRE_TIME))
                .withClaim(USER_NAME, user.getUsername())
//                .withClaim("ROLE", "")
                .sign(algorithm);

        log.info("jwt generated user={}", user.getUsername());
        return token;
    }

    /**
     * 验证token合法性
     */
    public static boolean verify(String token) {
        try {
//如果抛出异常，证明签名不一致 / token过期
            JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     */
    public static String getUserName(String token) {
        DecodedJWT decode = JWT.decode(token);
        String username = decode.getClaim(USER_NAME).asString();
        return username;
    }
}