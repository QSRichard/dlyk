package com.richard.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class ActivityQuery extends BaseQuery {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Integer ownerId;

    private String name;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 前端传的是yyyy-MM-dd HH:mm:ss 格式的字符串，使用DateTimeFormat 注解转为Date类型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    private BigDecimal cost;

    private String description;

}
