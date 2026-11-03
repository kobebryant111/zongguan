package com.work.ppm.commons.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 去重方法封装
 */
public class StreamUtils {

    public static <T> Predicate<T> distinctByKey(Function<? super T,?> keyExtractor){
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t),Boolean.TRUE) == null;
    }
}
