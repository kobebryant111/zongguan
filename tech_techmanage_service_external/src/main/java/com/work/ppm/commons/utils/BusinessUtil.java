package com.work.ppm.commons.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 业务相关工具类
 */
public class BusinessUtil {

    public static final String REGEX_COMMA = ",";
    public static final String REGEX_COMMA_UN = "\\,";
    public static final String REGEX_ROD = "-";
    public static final String REGEX_NULL = "";
    public static final String REGEX_COLON = ":";
    public static final String REGEX_SLASH = "/";
    public static final String REGEX_LINE = "|";
    public static final String REGEX_LINE_UN = "\\|";
    public static final String REGEX_LEFT_BRACKETS = "(";
    public static final String REGEX_RIGHT_BRACKETS = ")";
    public static final String REGEX_BLACK_GRID = "■";
    public static final String REGEX_WHITE_GRID = "□";
    public static final String REGEX_UNDERLINE = "_";
    public static final String SUFFIX_DOCX = ".docx";
    public static final String SUFFIX_DOC = ".doc";
    public static final String SUFFIX_XLSX = ".xlsx";
    public static final String SUFFIX_XLS = ".xls";

    /**
     * 获取部门名称
     * @param deptName eg:一级部名称-二级部名称
     * @param index 0 一级部 1 二级部
     * @return 部门名称
     */
    public static String getDeptNameValue(String deptName,int index){
        if(StringUtils.isNotEmpty(deptName)){
            String[] s = deptName.split(REGEX_ROD);
            if(s.length > 1){
                return s[index];
            }
        }
        return deptName;
    }

    /**
     * 合并字符串，用于返回字符串
     * @param value 数组
     * @param regex 连接符
     * @return 字符串
     */
    public static String getStringArray(String[] value,String regex){
        if(value == null || value.length == 0){
            return null;
        }

        StringJoiner stringJoiner = new StringJoiner(regex);
        for (String str : value){
            stringJoiner.add(str);
        }

        return stringJoiner.toString();
    }

    /**
     * 拆分字符串，用于返回数组集合
     * @param value 字符串
     * @param regex 连接符
     * @return 数组
     */
    public static String[] getArrayString(String value,String regex){
        if(StringUtils.isEmpty(value)){
            return new String[]{};
        }

        return value.split(regex);
    }

    /**
     * 用于下拉框label将两个字符串用括号拼接
     * @param left 括号外的字符串
     * @param right 括号内的字符串
     * @return 拼接后的字符串
     */
    public static String getComboBoxLabel(String left,String right){
        if(left == null && right == null){
            return REGEX_NULL;
        }

        return new StringBuffer().
                append(left).
                append(REGEX_LEFT_BRACKETS).
                append(right).
                append(REGEX_RIGHT_BRACKETS).
                toString();
    }


    /**
     * 用于将map容器中的数据做多次封装
     * @param map 所要合并的map容器
     * @param regexs 分割符
     * @return 封装后的字符串
     */
    public static String mapRegexString(List<String> list,Map<String, Object> map, String[] regexs){
        StringBuilder stringBuilder = new StringBuilder();

        if(list != null && !list.isEmpty()){
            for(String key : list){
                stringBuilder.append(key).append(regexs[0]).append(map.get(key)).append(regexs[1]);
            }
        }
        //list.forEach(key -> stringBuffer.append(key).append(regexs[0]).append(map.get(key)).append(regexs[1]));

        return stringBuilder.toString();
    }

    /**
     * 用于将string字符串中的数据做多次拆分
     * @param value 所要拆分的字符串
     * @param regexs 分割符
     * @return Map<String,Object> 拆分后的map容器
     */
    public static Map<String,Object> stringRegexMap(String value, String[] regexs){
        Map<String,Object> map = new LinkedHashMap<>();
        if(StringUtils.isNotEmpty(value)){
            String[] scoresArray = value.split(regexs[0]);
            for(String score : scoresArray){
                String[] scoreVal = score.split(regexs[1]);
                if(scoreVal.length == 2){
                    map.put(scoreVal[0],scoreVal[1]);
                }
            }
        }
        return map;
    }

    /**
     * 数组替换
     * @param map key:value
     * @param str string
     * @param regex
     * @return 字符串
     */
    public static String replaceArrays(Map<String,Object> map ,String str,String regex){
        if (CollectionUtils.isEmpty(map) || StringUtils.isEmpty(str)){
            return "";
        }

        String[] arrays = str.split(regex);
        StringJoiner stringJoiner = new StringJoiner(regex);

        for (String key : arrays){
            stringJoiner.add(map.get(key).toString());
        }

        return stringJoiner.toString();
    }

}
