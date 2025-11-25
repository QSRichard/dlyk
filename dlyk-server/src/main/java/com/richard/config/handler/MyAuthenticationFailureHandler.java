package com.richard.config.handler;

import com.richard.result.R;
import com.richard.utils.JsonUtils;
import com.richard.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        R result = R.Fail(exception.getMessage());
        String resJson = JsonUtils.toJson(result);
        ResponseUtils.write(response, resJson);
    }
}
