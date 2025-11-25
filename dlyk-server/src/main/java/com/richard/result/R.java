package com.richard.result;

/*
web 层返回结果的统一封装
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {

    private int code;
    private String msg;
    private Object data;

    public static R OK(int code, String msg) {

        return R.builder().code(code).msg(msg).build();
    }

    public static R OK(Object data) {
        return R.builder().code(CodeEnum.OK.getCode()).msg(CodeEnum.OK.getMsg()).data(data).build();
    }


    public static R Fail(int code, String msg) {
        return R.builder().code(code).msg(msg).build();
    }

    public static R Fail(Object data) {
        return R.builder().code(CodeEnum.FAIL.getCode()).msg(CodeEnum.FAIL.getMsg()).data(data).build();
    }
}
