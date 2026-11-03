package com.tansun.wf.activiti.util;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分表工具类，sliceColumnName为动态字段
 */
public class SliceServiceUtils implements InitializingBean {

    private static final ThreadLocal<Map<String,String>> sliceLocal = new ThreadLocal<Map<String,String>>();

    private static SliceServiceUtils instance;

    private  String sliceTablesStr;

    private  String sliceCheckMethodsStr;

    private  String sliceOpen;

    private  String sliceColumnName;

    private String sliceParent;

    public static boolean isInit() {
        return ! (SliceServiceUtils.instance == null);
    }

    /**
     * 判断是否需要分片的表
     * @param tableName
     * @return
     */
    public static boolean isInSliceTables(String tableName) {
        String sliceTablesStr = SliceServiceUtils.instance.sliceTablesStr;
            if (StringUtils.isNotBlank(sliceTablesStr)) {
                List<String> tableNames = Arrays.asList(sliceTablesStr.split(","));
                for (String name : tableNames) {
                    if (StringUtils.upperCase(name).equals(StringUtils.upperCase(tableName))){
                        return true;
                    }
                }
                return false;
            }else {
                throw new RuntimeException("slice.tables is null");
            }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SliceServiceUtils.instance = this;
    }

    /**
     * 判断方法是不是需要传切分字段值
     * @param methodName
     * @return
     */
    public static boolean isCheckMethod(String methodName) {
        String sliceCheckMethodsStr = SliceServiceUtils.instance.sliceCheckMethodsStr;
        if (StringUtils.isNotBlank(sliceCheckMethodsStr)) {
            List<String> methodNames = Arrays.asList(sliceCheckMethodsStr.split(","));
            return methodNames.contains(methodName);
        }else {
            throw new RuntimeException("slice.check.methods is null");
        }
    }

    /**
     * 获取分库表的切分字段值
     * @param obj
     * @return
     */
    public static String getSliceValue(Object obj) {
        Method method = ReflectionUtils.findMethod(obj.getClass(), "getSliceValue");
        return method == null ? null : (String)ReflectionUtils.invokeMethod(method, obj);
    }

    /**
     * 是否在全部数据节点查询
     * @param sliceValue
     */
    public static boolean isParent(String sliceValue) {
        return SliceServiceUtils.instance.sliceParent == null ? false : SliceServiceUtils.instance.sliceParent.equals(sliceValue);
    }


    /**
     * 是否开启分库
     * @return
     */
    public static  boolean isOpen() {
        return SliceServiceUtils.instance == null ? false : "Y".equals(SliceServiceUtils.instance.sliceOpen);
    }

    /**
     * 将分片值设置到线程变量中
     * @param sliceValue
     */
    public static void setLocalSliceValue(String sliceValue){
        if (StringUtils.isNotBlank(sliceValue)){
            initLocal();
            Map<String, String> sliceLocalMap = sliceLocal.get();
            sliceLocalMap.put("sliceValue", sliceValue);
        }
    }

    /**
     * 将分片值从线程变量中移除
     */
    public static void removeLocalSliceValue(){
        Map<String, String> sliceLocalMap = sliceLocal.get();
        if (sliceLocalMap != null) {
            sliceLocalMap.clear();
            sliceLocal.remove();
        }
    }

    /**
     * 从线程变量中获取分片值
     * @return
     */
    public static String getLocalSliceValue(){
        initLocal();
        Map<String, String> sliceLocalMap = sliceLocal.get();
        return sliceLocalMap.get("sliceValue");
    }

    /**
     * 获取db分片字段名称
     */
    public static String getJavaSliceColumnName() {
        String sliceColumnName = SliceServiceUtils.instance.sliceColumnName;
        if (StringUtils.isNotBlank(sliceColumnName)) {
            String upperCase = sliceColumnName.toUpperCase();
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, upperCase);
        }else {
            throw new RuntimeException("slice.column.name is null");
        }

    }

    /**
     * 获取java分片字段名称
     */
    public static String getDbSliceColumnName() {
        String sliceColumnName = SliceServiceUtils.instance.sliceColumnName;
        if (StringUtils.isNotBlank(sliceColumnName)) {
            return sliceColumnName.toUpperCase();
        }else {
            throw new RuntimeException("slice.column.name is null");
        }
    }



    private static void initLocal(){
        Map<String, String> localMap = sliceLocal.get();
        if (localMap == null) {
            sliceLocal.set(new HashMap<String, String>());
        }
    }

    public void setSliceTablesStr(String sliceTablesStr) {
        this.sliceTablesStr = sliceTablesStr;
    }

    public void setSliceCheckMethodsStr(String sliceCheckMethodsStr) {
        this.sliceCheckMethodsStr = sliceCheckMethodsStr;
    }

    public void setSliceOpen(String sliceOpen) {
        this.sliceOpen = sliceOpen;
    }

    public void setSliceParent(String sliceParent) {
        this.sliceParent = sliceParent;
    }

    public void setSliceColumnName(String sliceColumnName) {
        this.sliceColumnName = sliceColumnName;
    }


}
