package com.work.ppm.commons.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@UtilityClass
public class NumberUtil {

    public Long getLong(String value) {
        if (!StringUtil.isNotBlank(value)) return null;
        return Long.parseLong(value);
    }

    /**
     * 字符串转时间 年月日时分秒
     *
     * @param dateStr 时间文本
     * @param format  格式化
     * @return date
     */
    public static Date strToDateTime(String dateStr, String format) {
        if (!StringUtil.isNotBlank(dateStr)) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime time = LocalDateTime.parse(dateStr, formatter);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = time.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }
}
