package com.richard.config.filter;

import com.richard.constant.Constants;
import com.richard.model.TUser;
import com.richard.result.R;
import com.richard.service.RedisService;
import com.richard.utils.JWTUtils;
import com.richard.utils.JsonUtils;
import com.richard.utils.ResponseUtils;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.richard.result.CodeEnum.*;


@Component
public class TokenVerifyFilter extends OncePerRequestFilter {


    @Resource
    private RedisService redisService;

    // spring boot 提前创建好的线程池 可以直接注入使用
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (Constants.LOGIN_URL.equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization");

        if (StringUtil.isNullOrEmpty(token)) {
            System.out.println(token);
            R result = R.Fail(TOKEN_EMPTY.getCode(), TOKEN_EMPTY.getMsg());
            String resultJson = JsonUtils.toJson(result);
            ResponseUtils.write(response, resultJson);
            return;

        }

        if (!JWTUtils.verifyJWT(token)) {
            R result = R.Fail(TOKEN_INVALID.getCode(), TOKEN_INVALID.getMsg());
            String resultJson = JsonUtils.toJson(result);
            ResponseUtils.write(response, resultJson);
            return;
        }

        TUser user = JWTUtils.parseTUserFromJWT(token);

        String redisToken = (String) redisService.getValue(Constants.REDIS_JWT_KEY + user.getId());

        System.out.println("redisToken:" + redisToken);
        System.out.println("token:" + token);
        if (StringUtil.isNullOrEmpty(redisToken)) {
            R result = R.Fail(TOKEN_EXPIRED.getCode(), TOKEN_EMPTY.getMsg());
            String resultJson = JsonUtils.toJson(result);
            ResponseUtils.write(response, resultJson);
            return;
        }

        if (!redisToken.equals(token)) {
            R result = R.Fail(TOKEN_ERROR.getCode(), TOKEN_ERROR.getMsg());
            String resultJson = JsonUtils.toJson(result);
            ResponseUtils.write(response, resultJson);
        }


        // 刷新一下Token 异步处理
//        new Thread(() -> {
//            String rememberMe = request.getHeader("rememberMe");
//            if (Boolean.parseBoolean(rememberMe)) {
//                redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.JWT_REDIS_EXPIRE_TIME, TimeUnit.SECONDS);
//            } else {
//                redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.DEFAULT_JWT_REDIS_EXPIRE_TIME, TimeUnit.SECONDS);
//            }
//        }).start();

        threadPoolTaskExecutor.execute(() -> {
            String rememberMe = request.getHeader("rememberMe");
            if (Boolean.parseBoolean(rememberMe)) {
                redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.JWT_REDIS_EXPIRE_TIME, TimeUnit.SECONDS);
            } else {
                redisService.expire(Constants.REDIS_JWT_KEY + user.getId(), Constants.DEFAULT_JWT_REDIS_EXPIRE_TIME, TimeUnit.SECONDS);
            }
        });

        // token 验证通过，在SpringSecurity的上下文环境中设置 当前用户已登录 后续不需要再拦截
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
