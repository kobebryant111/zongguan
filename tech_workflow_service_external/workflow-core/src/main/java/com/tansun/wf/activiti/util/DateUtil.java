package com.tansun.wf.activiti.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间转换工具类
 */
public class DateUtil {

    /**
     * 把时间字符串转换为 Date 类型，默认格式：yyyy-MM-dd HH:mm:ss
     */
    public static Date stringToDate(String str) {
        return stringToDate(str,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据 pattern 指定格式转换为 Date 类型
     */
    public static Date stringToDate(String str,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把 Date 类型转换为 String，默认格式：yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(Date date) {
        return formatDate(date,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 把 Date 类型根据 pattern 指定格式转换为 String 类型
     */
    public static String formatDate(Date date,String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 把从数据库查询出的时间类型转换为 Date 类型
     */
    public static Date toDate(Object sqlDate) {
        if (sqlDate == null) {
            return null;
        }
        if (sqlDate instanceof Date) {
            return (Date) sqlDate;
        } else {
            if (("oracle.sql.TIMESTAMP").equals(sqlDate.getClass().getName())) {
                Method method = ReflectionUtils.findMethod(sqlDate.getClass(), "timestampValue");
                Timestamp timestampValue = (Timestamp) ReflectionUtils.invokeMethod(method, sqlDate);
                return new Date(timestampValue.getTime());
            }
        }
        return null;
    }

    /**
     * 获取当年的最后时间戳
     */
    public static Date getYearLastTime(String yyyy){
        return getLastTime(yyyy, "12", "31");
    }

    public static String getYearLastTimeString(String yyyy){
        return formatDate(getLastTime(yyyy, "12", "31"));
    }

    /**
     * 获取当年的最早时间戳
     */
    public static Date getYearFirstTime(String yyyy){
        return getFirstTime(yyyy, "1", "1");
    }

    public static String getYearFirstTimeString(String yyyy){
        return formatDate(getYearFirstTime(yyyy));
    }

    /** 获取月最早时间戳 */
    public static Date getMonthFirstTime(String yyyy, String mm){
        return getFirstTime(yyyy, mm, "1");
    }

    /** 获取月最晚时间戳 */
    public static Date getMonthLastTime(String yyyy, String mm){
        if(";1;3;5;7;8;10;12".contains(";"+mm+";")){
            return getLastTime(yyyy, mm, "31");
        }else{
            return getLastTime(yyyy, mm, "30");
        }
    }

    public static Date getFirstTime(String yyyy, String mm, String dd){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(yyyy));
        cal.set(Calendar.MONTH, Integer.valueOf(mm)-1);
        cal.set(Calendar.DATE, Integer.valueOf(dd));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /** 获取最晚时间戳 */
    public static Date getLastTime(String yyyy, String mm, String dd){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(yyyy));
        cal.set(Calendar.MONTH, Integer.valueOf(mm)-1);
        cal.set(Calendar.DATE, Integer.valueOf(dd));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
