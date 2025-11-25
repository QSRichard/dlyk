package com.richard.constant;

public class Constants {


    public static final String SESSION_CAPTCHA = "captcha";

    public static final String LOGIN_URL = "/api/login";

    public static final String REDIS_JWT_KEY = "dlyk:user:login:";

    public static final Long JWT_REDIS_EXPIRE_TIME = 7 * 24 * 60 * 60L;

    public static final Long DEFAULT_JWT_REDIS_EXPIRE_TIME = 3 * 60L;
}
