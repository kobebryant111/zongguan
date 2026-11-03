package org.work.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 时间转字符串
     * @param date 时间
     * @param pattern 时间格式
     * @return
     */
    public static String dateToStr(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
