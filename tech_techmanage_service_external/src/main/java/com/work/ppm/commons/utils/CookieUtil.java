package com.work.ppm.commons.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie工具类
 */
public class CookieUtil {
    /**
     * 根据名称获取值
     * @param name 名称
     * @param request request
     * @return String
     */
    public static String getValue(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(null == cookies) {
            return "";
        }
        for (int i = cookies.length - 1; i >= 0; i--) {
            if(name.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }
}
