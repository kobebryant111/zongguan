package com.work.ppm.commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工调休功能相关工具类
 */
@Slf4j
public class BankTxUtil {

    /**
     * 计算积分
     *
     * @param startTime
     * @param endTime
     * @param isHoliday
     * @return
     */
    public static Map<String , Object> computeIntegNum(String startTime, String endTime , String isHoliday) throws ParseException {
        String integNum = "";
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtil.isStringNotEmpty(startTime) && StringUtil.isStringNotEmpty(endTime)) {
            //如果为节假日 加班4小时则加0.5个积分 最多加1个积分
            if (StringUtil.isStringNotEmpty(isHoliday) && "1".equals(isHoliday)) {
                long timeDifference = getTimeDifference(startTime, endTime, "yyyy-MM-dd HH:mm:ss");
                if (timeDifference >= (4 * 60 * 60) && timeDifference < (8 * 60 * 60)) {
                    integNum = "0.5";
                } else if (timeDifference >= (8 * 60 * 60)) {
                    integNum = "1";
                }
            } else if (StringUtil.isStringNotEmpty(isHoliday) && "0".equals(isHoliday)) {
                //如果为非节假日 加班到23点后则加0.5
                String today23Hours = DateUtil.getNextDayMorning(startTime, 23, 0);
                long difference = getTimeDifference(today23Hours, endTime, "yyyy-MM-dd HH:mm:ss");
                if (difference >= 0) {
                    integNum = "0.5";
                }
            }
            resultMap.put("integNum" , integNum);
        }
        return resultMap;
    }


    /**
     * 获取时间差
     *
     * @param startTimeString
     * @param endTimeString
     * @param format
     * @return
     * @throws ParseException
     */
    public static long getTimeDifference(String startTimeString, String endTimeString, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date startTime = sdf.parse(startTimeString);
        Date endTime = sdf.parse(endTimeString);
        long startTimeMillis = startTime.getTime();
        long endTimeMillis = endTime.getTime();
        return (endTimeMillis - startTimeMillis) / 1000;
    }

}
