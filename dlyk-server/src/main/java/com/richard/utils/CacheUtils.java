package com.richard.utils;

import org.springframework.util.ObjectUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;


// 带有返回缓存功能的查询方法
public class CacheUtils {

    public static <T> T getCacheData(Supplier<T> cacheSelector, Supplier<T> dbSelector, Consumer<T> saveCache) {

        T data = cacheSelector.get();
        if (ObjectUtils.isEmpty(data)) {
            data = dbSelector.get();
            if (!ObjectUtils.isEmpty(data)) {
                saveCache.accept(data);
            }
        }
        return data;
    }

}
