package com.richard.aspect;


import com.richard.commons.DataScope;
import com.richard.constant.Constants;
import com.richard.model.TUser;
import com.richard.query.BaseQuery;
import com.richard.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;


// aspectJ 实现aop
@Aspect
@Component
public class DataScopeAspect {

    // 切入点
    @Pointcut(value = "@annotation(com.richard.commons.DataScope)")
    private void pointCut(JoinPoint joinPoint) {

    }

    // 环绕增强 方法执行前后 都做
    @Around(value = "pointCut()")
    // 连接点
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataScope dataScope = methodSignature.getMethod().getAnnotation(DataScope.class);

        String tableAlias = dataScope.tableAlias();
        String tableField = dataScope.tableField();

        //  SpringWeb 容器中可以拿到 当前请求的request 对象
        HttpServletRequest resuest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String token = resuest.getHeader(Constants.DEFAULT_TOKEN_NAME);

        // 从Token中获取用户
        TUser user = JWTUtils.parseTUserFromJWT(token);

        List<String> roles = user.getRoles();

        // 不包括admin权限 只查当前用户
        if (!roles.contains("admin")) {

            Object parameter = joinPoint.getArgs()[0]; // 获取方法的第一个参数
            if (parameter instanceof BaseQuery baseQuery) {
                baseQuery.setFilterSql(" and " + tableAlias + "." + tableField + " = " + user.getId());
            }
        }


        // 目标方法执行之前
        System.out.println("目标方法执行之前...");
        // 执行目标方法
        Object result = joinPoint.proceed();
        // 目标方法执行之后
        System.out.println("目标方法执行之后...");
        return result;
    }
}
