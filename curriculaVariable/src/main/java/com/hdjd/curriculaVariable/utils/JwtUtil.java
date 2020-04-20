package com.hdjd.curriculaVariable.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hdjd.curriculaVariable.CurriculaVariableApplication;
import com.hdjd.curriculaVariable.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtUtil {
    private static Logger logger = LoggerFactory.getLogger(CurriculaVariableApplication.class);
    private static final long EXPIRE_TIME= 15*60*1000;
    private static final String TOKEN_SECRET="ecjtuVergil";

    /**
     * 签名生成
     * @param user
     * @return
     */
    public static String sign(UserModel user){

        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", user.getUserId())
                    .withClaim("role", user.getRole())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;

    }


    /**
     * 签名验证
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            logger.info("token认证通过打印信息");
            logger.info("用户id: " + jwt.getClaim("userId").asString());
            logger.info("用户权限: " + jwt.getClaim("role").asString());
            logger.info("过期时间：" + jwt.getExpiresAt());
            return true;
        } catch (Exception e){
            logger.warn("token认证失败,请尝试重新登陆");
            e.printStackTrace();
            return false;
        }
    }

    public static long getUserId(String token){
        JWTVerifier verifier = null;
        try {
            verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("userId").asLong();
    }
}