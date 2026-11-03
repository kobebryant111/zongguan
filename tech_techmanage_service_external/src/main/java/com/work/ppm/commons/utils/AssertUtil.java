package com.work.ppm.commons.utils;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.validator.DateValidator;
import com.work.ppm.commons.validator.impl.DateValidatorImpl;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * 断言工具
 */
public class AssertUtil extends Assert {
    /**
     * 断言为空异常
     * @param object 对象
     * @param message 消息
     */
    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new BizException(ApiCode.PARAMETER_ERROR,message);
        }
    }
    /**
     * 断言为空异常
     * @param str 文本
     * @param message 消息
     */
    public static void notNull(@Nullable String str, String message) {
        if(str == null || str.length() <= 0) {
            throw new BizException(ApiCode.PARAMETER_ERROR, message);
        }
    }

    /**
     * 断言为空异常
     * @param str 字符传数组
     * @param message 消息
     */
    public static void notNull(@Nullable String[] str, String message) {
        if(str == null || str.length <= 0) {
            throw new BizException(ApiCode.PARAMETER_ERROR, message);
        }
    }

     /**
     * 断言为校验失败异常
     * @param str 字符传数组
     * @param message 消息
     * @param format 校验格式
     */
    public static void notValidator(@Nullable String str, String message,String format) {
        final DateValidator validator = new DateValidatorImpl(format);

        if(!validator.isValid(str)){
            throw new BizException(ApiCode.PARAMETER_ERROR, message);
        }
    }

    /**
     * 断言为校验失败异常
     * @param str 字符传数组
     * @param message 消息
     * @param format 校验格式
     * @param special 特殊字符判断
     */
    public static void notValidator(@Nullable String str, String message,String format,String special) {
        final DateValidator validator = new DateValidatorImpl(format);

        if(!validator.isValid(str) && !special.equals(str)){
            throw new BizException(ApiCode.PARAMETER_ERROR, message);
        }
    }


}
