package com.work.ppm.commons.utils;

import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 */
@Slf4j
public class DateUtil {
    /**
     * 时间格式对象
     */
    public static FastDateFormat ISO_DATE_NO_BAR_FORMAT;
    /**
     * 工作日接口
     */
    private static IPpmKqCalendarService ppmKqCalendarService;

    /**
     * 日期格式
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 时间格式
     */
    public static final String HH_MM_SS = "HH:mm:ss";

    static {
        ISO_DATE_NO_BAR_FORMAT = FastDateFormat.getInstance("yyyyMMdd");
        ppmKqCalendarService = (IPpmKqCalendarService) SpringUtil.getBean("ppmKqCalendarService");
    }

    /**
     * 时间转字符串 且没有连接符 yyyyMMdd
     *
     * @param date 时间
     * @return String
     */
    public static String dateToNoBar(Date date) {
        return DateFormatUtils.format(date, ISO_DATE_NO_BAR_FORMAT.getPattern());
    }

    /**
     * 时间格式化 jdk8以前
     *
     * @param dateStr 字符串时间
     * @param format  格式化
     * @return Date
     */
    public static Date dataFormat(String dateStr, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            log.error("DateUtil", e);
        }
        return date;
    }

    /**
     * 时间转字符串 yyyy-MM-dd
     *
     * @param date 时间
     * @return string
     */
    public static String dateToIsoDateFormat(Date date) {
        return DateFormatUtils.format(date, DateFormatUtils.ISO_DATE_FORMAT.getPattern());
    }

    /**
     * 时间转字符串 yyyy-MM-dd'T'HH:mm:ssZZ
     *
     * @param date 时间
     * @return string
     */
    public static String dateToIsoDatetimeTimeZoneFormat(Date date) {
        return DateFormatUtils.format(date, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern());
    }

    /**
     * 格式化时间
     *
     * @param date   时间
     * @param format 格式化
     * @return date
     */
    public static Date formatDate(Date date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDate localDate = LocalDate.parse(DateUtil.dateToIsoDatetimeTimeZoneFormat(date), formatter);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 字符串转时间 年月日
     *
     * @param dateStr 时间文本
     * @param format  格式化
     * @return date
     */
    public static Date strToDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * 字符串转时间 年月日时分秒
     *
     * @param dateStr 时间文本
     * @param format  格式化
     * @return date
     */
    public static Date strToDateTime(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime time = LocalDateTime.parse(dateStr, formatter);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = time.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * Date 转 字符串时间
     *
     * @param date   时间
     * @param format 时间格式化类型
     * @return String
     */
    public static String dateToStr(Date date, String format) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        return format1.format(date);
    }

    /**
     * 字符串时间格式化 转 字符串时间
     *
     * @param date   时间
     * @param format1 时间格式化类型
     * @param format 时间格式化类型
     * @return String
     */
    public static String strToStr(String date, String format1,String format) {
        if (null == date) {
            return "";
        }
        Date time = dataFormat(date,format1);

        return dateToStr(time,format);
    }

    /**
     * 获取几天后的时间
     *
     * @param date 时间
     * @param days 天数
     * @return Date
     */
    public static Date getNextTime(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date nowdate = date;
        //Date date = null;
        while (!isWorkingDay(nowdate)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            nowdate = calendar.getTime();
        }
        for (int i = 0; i < days; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            nowdate = calendar.getTime();

            while (!isWorkingDay(nowdate)) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                nowdate = calendar.getTime();
            }
        }

        return nowdate;

    }

    /**
     * 是否为工作日
     *
     * @param date 时间
     * @return true/false
     */
    public static boolean isWorkingDay(Date date) {
        PpmKqCalendarDto calendarDto = new PpmKqCalendarDto();
        calendarDto.setCalendarDate(date);
        PpmKqCalendarDto dto = ppmKqCalendarService.queryDetail(calendarDto);
        return null != dto && dto.getIsHoliday() == "N";
    }

    /**
     * 增加或者减少自然日
     *
     * @param date 时间
     * @param days 天数
     * @param format 格式化
     * @return String
     */
    public static String addOrSubDateToString(Date date, int days,String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return DateUtil.dateToStr(c.getTime(), format);
    }

    /**
     * 增加或者减少自然日
     *
     * @param date 时间
     * @param days 天数
     * @return Date
     */
    public static Date addOrSubDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    /**
     * 增加或者减少小时
     *
     * @param date 时间
     * @param hour 天数
     * @return Date
     */
    public static Date addOrSubHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hour);
        return c.getTime();
    }

    /**
     * 间隔年
     *
     * @param date 时间
     * @param year 年数
     * @return date
     */
    public static Date intervalYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 间隔月
     *
     * @param date  时间
     * @param month 月数
     * @return date
     */
    public static Date intervalMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 取上月的第一天
     * @return
     */
    public static Date getFirstDayOfPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 取上月的最后一天
     * @return
     */
    public static Date getLastDayOfPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 取当前月的第一天
     *
     * @return
     */
    public static Date getFirstDayByMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 取当前月的最后一天
     *
     * @return
     */
    public static Date getLasttDayByMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }
    /**
     * 取当前月的第一天
     * @return String("yyyy-MM-dd")
     */
    public static String getFirstDay(){
        final LocalDate localDate = LocalDate.now().withDayOfMonth(1);
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    /**
     * 取当前月的最后一天
     * @return String("yyyy-MM-dd")
     */
    public static String getLastDay(){
        final LocalDate localDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    /**
     * 获取年份
     *
     * @return
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 是否白天
     *
     * @param date 时间
     * @return 1：白天，2：晚上
     */
    public static int isDaytime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour24 = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (hour24 == 8 && minute >= 30) {//8:30-9:00
            return 1;
        } else if (hour24 > 8 && hour24 < 17) {//9:00-17:00
            return 1;
        } else if (hour24 == 17 && minute < 30) {//17:00-17:30
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 是否为工作日
     *
     * @param date 时间
     * @return true/false
     */
    public static boolean isWorkingDayNew(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        PpmKqCalendarDto calendarDto = new PpmKqCalendarDto();
        calendarDto.setYearOfCalendar((long)calendar.get(Calendar.YEAR));
        calendarDto.setMonthOfYear((long)calendar.get(Calendar.MONTH));
        calendarDto.setDayOfMonth((long)calendar.get(Calendar.DAY_OF_MONTH));
        PpmKqCalendarDto dto = ppmKqCalendarService.queryDetail(calendarDto);
        return null != dto && "N".equals(dto.getIsHoliday());
    }

    /**
     * JDK8 取在特定日期增加 月份
     * @param date 特定日期
     * @param num 增加月份数
     * @return Date
     */
    public static Date getPlusDays(Date date,Long num){
        if(date == null){
            return null;
        }
        if(num == 0){
            return date;
        }

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.plusDays(1);
        Date temp = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        if(isWorkingDayNew(temp)){
            return getPlusDays(temp,num-1);
        }else {
            return getPlusDays(temp,num);
        }
    }

    /**
     * 将时间变量按照固定格式转换成新的时间
     * @param date 时间字符串
     * @param format 格式化
     * @return 转换后的时间类型变量
     */
    public static Date dateToDate(Date date,String format){
        if(date == null){
            return null;
        }

        return strToDate(dateToStr(date,format),format);
    }


    /**
     * 获取某年的第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getFirstOfYear(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        return calendar.getTime();
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getLastOfYear(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.roll(Calendar.DAY_OF_YEAR,-1);
        return calendar.getTime();
    }
    /**
     * 获取上一季度最后一月的最后一天
     * @return 转换后的时间类型变量
     */
    public static String lastDayOfPreviousQuarter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate nowDate = LocalDate.now();
        Month firstMonthOfQuarter = nowDate.minusMonths(3).getMonth().firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        int year = nowDate.minusMonths(2).getYear();
        LocalDate beginDate = LocalDate.of(year, firstMonthOfQuarter, 1);
        LocalDate endDate = LocalDate.of(year, endMonthOfQuarter, endMonthOfQuarter.length(beginDate.isLeapYear()));
        LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.MAX);
        String dateStr = endTime.format(dateTimeFormatter);
        return dateStr;
    }

    /**
     * 获取季度首末的日期
     * @param year 年份
     * @param quarter 第一季度 1:1 第二季度 2:4 第三季度 3:7 第四季度 4:10
     * @param state 0 :开始 -1:结束
     * @return 日期
     */
    public static String getYearOfQuarter(String year,String quarter,String state){
        if(StringUtils.isEmpty(year) || StringUtils.isEmpty(quarter) || StringUtils.isEmpty(state)){
            return null;
        }

        String quarterSt;
        String quarterEn;

        switch (quarter){
            case "1":
                quarterSt = "1";
                quarterEn = "3";
                break;
            case "2":
                quarterSt = "4";
                quarterEn = "6";
                break;
            case "3":
                quarterSt = "7";
                quarterEn = "9";
                break;
            case "4":
                quarterSt = "10";
                quarterEn = "12";
                break;
            default:
                return null;
        }

        if("0".equals(state)){
            return getStartDayOfMonth(Integer.parseInt(year),Integer.parseInt(quarterSt));
        }

        if("-1".equals(state)){
            return getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(quarterEn));
        }

        return null;
    }

    /**
     * 获取某月第一天
     * @param year 年
     * @param month 月
     * @return 日期
     */
    public static String getStartDayOfMonth(int year ,int month){
        LocalDate localDate = LocalDate.of(year,month,1);
        LocalDate date = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return date.toString();
    }

    /**
     * 获取某月最后一天
     * @param year 年
     * @param month 月
     * @return 日期
     */
    public static String getLastDayOfMonth(int year ,int month){
        LocalDate localDate = LocalDate.of(year,month,1);
        LocalDate date = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return date.toString();
    }

    /**
     * 获取随机时间戳
     * @return id
     */
    public static String getDateRandom(){
        String date = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        String random1 = String.format("%03d",(int)(Math.random()*1000));
        String random2 = String.format("%03d",(int)(Math.random()*1000));
        return date+random1+random2;
    }

    /**
     * 获取当前日期 YYYY-MM-DD
     *
     * @return 当前日期的字符串
     */
    public static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        return now.format(dateTimeFormatter);
    }

    /**
     * 获取ESB时间
     *
     * @return esbtime
     */
    public static String getNewEsbTime() {
        String dateTime = "";
        Calendar today = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat(HH_MM_SS);
        dateTime = formater.format(today.getTime());
        return dateTime;
    }
    /**
     * 描述:  LocalDateTime转字符
     *
     * @param dateTime
     * @return LocalDateTime LocalDateTime
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime == null ? null : getDataFormat(dateTime, pattern);
    }

    /**
     * 日期解析字符串
     *
     * @param dateTime
     * @param pattern
     * @return LocalDateTime
     */
    public static String getDataFormat(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 通过给定格式,获取给你时间的上个月的最后一天日期
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static String getLastDayOfLastMonth(String dateStr , String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate givenDate = LocalDate.parse(dateStr, dateTimeFormatter);
        LocalDate firstDayOfPreviousMonth = givenDate.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfPreviousMonth = firstDayOfPreviousMonth.with(TemporalAdjusters.lastDayOfMonth());
        return lastDayOfPreviousMonth.format(DateTimeFormatter.ofPattern(dateFormat));
    }


    /**
     * 转化日期格式
     * @param strDate
     * @param originalFormat
     * @param targetFormat
     * @return
     * @throws ParseException
     */
    public static String strToFormatDate(String strDate , String originalFormat , String targetFormat) throws ParseException {
        SimpleDateFormat originalSimpleDateFormat = new SimpleDateFormat(originalFormat);
        Date originalDate = originalSimpleDateFormat.parse(strDate);
        SimpleDateFormat targetSimpleDateFormat = new SimpleDateFormat(targetFormat);
        return  targetSimpleDateFormat.format(originalDate);
    }

    /**
     * 获取给定日期的第二天上午五点的时间
     * @param dateTimeStr
     * @return
     */
    public static String getNextDayMorning(String dateTimeStr , int hour , int addDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        LocalDate nextDay = dateTime.toLocalDate().plusDays(addDay);
        LocalTime moring5 = LocalTime.of(hour, 0);

        LocalDateTime resultData = LocalDateTime.of(nextDay, moring5);
        String format = resultData.format(formatter);

        return format;
    }

}
