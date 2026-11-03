package com.work.ppm.commons.security;

import java.text.MessageFormat;

/**
 * SecurityRuntimeException
 */
public class SecurityRuntimeException extends RuntimeException {
    /**
     * 变量
     */
    Object[] params = new Object[0];

    /**
     * 构造函数
     * @param msg 信息
     */
    public SecurityRuntimeException(String msg)
    {
        super(msg);
    }
    /**
     * 构造函数
     * @param params 数组
     * @param msg 字符串
     */
    public SecurityRuntimeException(String msg, Object[] params)
    {
        super(msg);
        this.params = params;
    }
    /**
     * 构造函数
     * @param cause 异常
     */
    public SecurityRuntimeException(Throwable cause)
    {
        super(cause);
    }
    /**
     * 构造函数
     * @param cause 异常
     * @param msg 文本
     */
    public SecurityRuntimeException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
    /**
     * 构造函数
     * @param cause 异常
     * @param msg 文本
     * @param params  数组
     */
    public SecurityRuntimeException(String msg, Object[] params, Throwable cause)
    {
        super(msg, cause);
        this.params = params;
    }

    public String getMessage()
    {
        return format(super.getMessage(), this.params);
    }
    /**
     * format
     * @param s 字符串
     * @param params 对象数据
     * @return string
     */
    private static String format(String s, Object[] params)
    {
        String message = s;
        if (message == null) {
            return null;
        }


        if ((params != null) && (params.length > 0))
        {
            message = new MessageFormat(message).format(params);
        }

        return message;
    }
}
