package com.richard.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.richard.model.TUser;

import java.util.HashMap;
import java.util.Map;


public class JWTUtils {

    public static final String JWT_SECRET = "jwt_secret";

    public static String createJWT(String userJson) {
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "HS256");
        return JWT.create().withHeader(header).withClaim("user", userJson).sign(Algorithm.HMAC256(JWT_SECRET));
    }


    public static Boolean verifyJWT(String token) {

        try {

            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(token)).build();

            // 没有抛出异常 说明验证通过
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void parseJWT(String jwt) {
        try {
            // 使用秘钥创建一个验证器对象
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();

            // 验证JWT 得到一个解码后的JWT对象
            DecodedJWT decodedJWT = verifier.verify(jwt);

            // 通过解码后的JWT对象获取负载数据
            Claim nickClaim = decodedJWT.getClaim("nick");
            Claim nameClaim = decodedJWT.getClaim("name");
            Claim phoneClaim = decodedJWT.getClaim("phone");
            Claim birthDayClaim = decodedJWT.getClaim("birthDay");

            String nickname = nickClaim.asString();
            String name = nameClaim.asString();
            String phone = phoneClaim.asString();
            String birthday = birthDayClaim.asString();
            System.out.println(nickname + " " + name + " " + phone + " " + birthday);
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static TUser parseTUserFromJWT(String jwt) {
        try {

            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();

            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

            Claim claim = decodedJWT.getClaim("user");
            String userJson = claim.asString();

            return JsonUtils.toBean(userJson, TUser.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
