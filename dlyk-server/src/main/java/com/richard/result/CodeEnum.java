package com.richard.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CodeEnum {

    OK(200, "成功"),
    FAIL(500, "失败"),
    TOKEN_EMPTY(901, "token 为空"),
    TOKEN_EXPIRED(902, "token 已过期"),
    TOKEN_ERROR(903, "token 错误"),
    TOKEN_INVALID(904, "token 不匹配"),
    ;
    private int code;
    private String msg;
}
