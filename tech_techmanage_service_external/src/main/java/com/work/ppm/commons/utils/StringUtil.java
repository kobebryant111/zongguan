package com.work.ppm.commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 */
@Slf4j
public class StringUtil {

    /**
     * 对象转Map
     *
     * @param obj   对象
     * @param clazz 类
     * @param <T>   泛型
     * @return Map
     */
    public static <T> Map<String, Object> objectTOMap(Object obj, Class<T> clazz) {

        return JsonUtil.objectTOMap(obj, clazz);
    }

    /**
     * 变量
     *
     * @javadoc
     */
    private static final String REGES = "(_([a-zA-Z-0-9]{1}))";

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return str
     */
    public static String upperName(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);

    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return string
     */
    public static String lowerName(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 类名转化成数据库表名
     *
     * @param clazzName
     * @return String
     */
    public static String clazzNameToTableName(String clazzName) {
        List<String> list = splitByUpperCase(clazzName);
        StringBuilder sb = new StringBuilder(list.size());
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append("_");
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将驼峰字符串按大写字母分割数组
     *
     * @param str
     * @return List
     */
    public static List<String> splitByUpperCase(String str) {
        List<String> list = new ArrayList<>();
        int index = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                String temp = str.substring(index, i);
                if (temp.length() <= 0) {
                    continue;
                }
                list.add(temp);
                index = i;
            }
        }
        list.add(str.substring(index, len));
        return list;
    }

    /**
     * 将文件名改成驼峰形式
     *
     * @param fileName 文件名称
     * @return String
     */
    public static String processClassName(String fileName) {
        String newFileName = "";
        while (fileName.indexOf("_") != -1) {
            Pattern pat = Pattern.compile(REGES);
            Matcher mat = pat.matcher(fileName);
            boolean falg = mat.find();
            if (falg) {
                String index = mat.group(2);
                fileName = fileName.replace(mat.group(1), index.toUpperCase());
            }
        }
        newFileName = fileName;
        return newFileName;
    }

    /**
     * equalsIgnoreCase
     *
     * @param str1 文本
     * @param str2 文本
     * @return boolean
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return ((str1 == null) ? false : (str2 == null) ? true : str1.equalsIgnoreCase(str2));
    }

    /**
     * 对象不为空
     *
     * @param obj 对象
     * @return boolean
     */
    public static boolean isNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        }
        return true;
    }

    /**
     * 字符串不为空
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isStringNotEmpty(String str) {
        if (str != null && str.length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否为数字
     *
     * @param str 字符串
     * @return boolean
     */
    public static final boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 字符串不为空
     *
     * @param str 字符串
     * @return true ：不为空，false：空
     */
    public static boolean isNotBlank(String str) {
        return isStringNotEmpty(str);
    }


    /**
     * 获得主键
     *
     * @return String
     */
    public static String getPrimarykey() {
        String result = UUID.randomUUID().toString().replace("-", "");
        return result;
    }

    /**
     * 空转‘’
     *
     * @param str
     * @return String
     */
    public static String nullTo(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * 获取当前日期
     *
     * @param format 传入的格式
     * @return
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date currentDate = new Date();
        String resultDate = dateFormat.format(currentDate);
        return resultDate;
    }

    /**
     * 获取之前的日期
     *
     * @param num
     * @return
     * @throws ParseException
     */
    public static String getbeforeDate(int num) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(StringUtil.getCurrentDate("yyyy-MM-dd")));
        calendar.add(Calendar.DATE, num);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }


    /**
     * 转化时间格式
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static String formatDate(Date date, String format) throws ParseException {
        SimpleDateFormat targetFormat = new SimpleDateFormat(format);
        String resultDate = targetFormat.format(date);
        return resultDate;
    }

    /**
     * 转化字符串格式
     *
     * @param str
     * @return String
     */
    public static String transStr(String str) {
        if (!StringUtil.isStringNotEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("'");
        if (str.contains(",")) {
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                sb.append(split[i]).append("'");
                if (i < split.length - 1) {
                    sb.append(",'");
                }
            }
        } else {
            sb.append(str).append("'");
        }
        return sb.toString();
    }

    /**
     * @param list: 需要拼接的对象
     * @return string: 返回拼接后的对象
     * @date 2025/2/19
     * @version JDK 8
     * @description 将list中的集合进行拼接成sql
     */
    public static String listToSql(List list) {
        if (list == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("('");

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append("'");
            if (i < list.size() - 1) {
                sb.append(",'");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 将字符串转化成SQL条件
     *
     * @param str
     * @return
     */
    public static String strToSql(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(str).append(")");
        return sb.toString();
    }

    /**
     * 将传进来的参数进行格式转化
     *
     * @param obj
     * @return
     */
    public static Long objToLong(Object obj) {
        if (obj == null) {
            return Long.valueOf(0);
        }
        return Long.valueOf(String.valueOf(obj));
    }

    /**
     * 判断字符串是否为有效的非负数字（整数或最多两位小数）
     *
     * @param str 待校验的字符串
     * @return 若为有效数字返回 true，否则返回 false
     */
    public static boolean isNumericWithTwoDecimals(String str) {
        if (str == null || str.isEmpty()) return false;
        // 正则说明：
        // ^              → 开头锚点
        // \d+            → 至少一位数字（整数部分）
        // (?:\.\d{1,2})? → 可选小数部分：小数点 + 1~2 位数字
        // $              → 结尾锚点（保证全匹配）
        return str.matches("^\\d+(\\.\\d{1,2})?$");
    }

    /**
     * 校验字符串是否为非负数字（整数或小数），不限制小数位数。
     *
     * @param str 待校验的字符串
     * @return 若为有效数字返回 true，否则返回 false
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // 正则说明：
        // ^            → 开头锚点
        // \d+          → 至少 1 位数字（整数部分）
        // (?:\.\d+)?   → 可选小数部分：小数点 + 至少 1 位数字
        // $            → 结尾锚点（确保全匹配）
        return str.matches("^\\d+(?:\\.\\d+)?$");
    }


    /**
     * 校验字符串是否为 "yyyy-MM-dd" 格式（仅格式校验，不校验日期合法性）
     *
     * @param str 待校验的字符串
     * @return 若为格式合法的日期字符串返回 true，否则返回 false
     */
    public static boolean isDateYmdFormat(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // 核心正则：匹配 yyyy-MM-dd 格式，并对月份 [01–12]、日期 [01–31] 做范围限制
        return str.matches("^\\d{4}-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12]\\d|3[01])$");
    }

    /**
     * 校验字符串是否为有效的 "yyyy-MM-dd" 日期（格式 + 语义双重校验）
     * 会检查日期是否存在（如拒绝 2 月 30 日、4 月 31 日等非法日期）
     * 使用 LocalDate.parse(CharSequence, DateTimeFormatter) 验证语义合法性
     *
     * @param str 待校验的字符串
     * @return 若为格式和语义均合法的日期字符串返回 true，否则返回 false
     */
    public static boolean isValidDateYmd(String str) {
        // 格式校验
        if (!isDateYmdFormat(str)) {
            return false;
        }

        try {
            // 使用 ISO_LOCAL_DATE（即 yyyy-MM-dd）解析，确保格式严格一致
            LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            // 捕获解析失败（如 2023-02-30）
            return false;
        }
    }
    /**
     * 生成大写的UUID
     * @return
     */
    public static String UUID32( ) {
        return   UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
}
