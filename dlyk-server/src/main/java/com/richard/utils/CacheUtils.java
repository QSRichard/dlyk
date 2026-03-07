package com.richard.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CacheUtils {


    public static <T> T getCacheData(Supplier<T> cacheSelector, Supplier<T> daSelector, Consumer<T> saveCache) {

        T data = cacheSelector.get();
        if (Objects.isNull(data)) {
            data = daSelector.get();
            if (!Objects.isNull(data)) {
                saveCache.accept(data);
            }
        }
        return data;
    }

}
