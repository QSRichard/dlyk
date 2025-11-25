package com.richard.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CodeEnum {

    OK(200, "成功"),
    FAIL(500, "失败");

    private int code;
    private String msg;
}
