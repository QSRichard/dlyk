package com.richard.config.handler;

import com.richard.constant.Constants;
import com.richard.model.TUser;
import com.richard.result.R;
import com.richard.service.RedisService;
import com.richard.utils.JWTUtils;
import com.richard.utils.JsonUtils;
import com.richard.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Resource
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        TUser tuser = (TUser) authentication.getPrincipal();

        String userJson = JsonUtils.toJson(tuser);
        String jwt = JWTUtils.createJWT(userJson);
        redisService.setValue(Constants.REDIS_JWT_KEY + tuser.getId(), jwt);

        // 设置过期时间
        String rememberMe = request.getParameter("rememberMe");
        if (Boolean.parseBoolean(rememberMe)) {
            redisService.expire(Constants.REDIS_JWT_KEY + tuser.getId(), Constants.JWT_REDIS_EXPIRE_TIME, TimeUnit.SECONDS);
        } else {
            redisService.expire(Constants.REDIS_JWT_KEY + tuser.getId(), Constants.DEFAULT_JWT_REDIS_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        R result = R.OK(jwt);
        String resJson = JsonUtils.toJson(result);

        System.out.println("Hello" + resJson);
        ResponseUtils.write(response, resJson);
    }
}
