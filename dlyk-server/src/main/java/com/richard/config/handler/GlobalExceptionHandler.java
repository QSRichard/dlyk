package com.richard.config.handler;

import com.richard.result.R;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// 统一异常处理类，当Controller发生异常时，统一调用该方法
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R hasException(Exception e) {
        e.printStackTrace();
        return R.Fail(e.getMessage());
    }

    @ExceptionHandler(value = DataAccessException.class)
    public R hasDataAccessException(DataAccessException e) {
        e.printStackTrace();
        return R.Fail(500, "数据库操作失败");
    }
}
