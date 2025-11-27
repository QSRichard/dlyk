package com.richard.config.handler;

import com.richard.constant.Constants;
import com.richard.model.TUser;
import com.richard.result.R;
import com.richard.service.RedisService;
import com.richard.utils.JsonUtils;
import com.richard.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private RedisService redisService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        TUser user = (TUser) authentication.getPrincipal();
        redisService.removeValue(Constants.REDIS_JWT_KEY + user.getId());

        R result = R.OK(200, "退出成功");
        ResponseUtils.write(response, JsonUtils.toJson(result));
    }
}
