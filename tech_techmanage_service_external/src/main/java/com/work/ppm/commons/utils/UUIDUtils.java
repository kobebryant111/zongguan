package com.work.ppm.commons.utils;

import java.util.UUID;

/**
 * 主键生成器
 */
public class UUIDUtils {
    /**
     * 主键生成器
     * @return String
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
