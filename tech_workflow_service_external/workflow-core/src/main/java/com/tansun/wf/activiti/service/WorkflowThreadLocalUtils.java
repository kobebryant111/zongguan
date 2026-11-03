package com.tansun.wf.activiti.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作流本地线程变量工具类，可以将接口的无需入库的参数传递至事件监听方法中
 * 如果中间出现子事务，线程切换后将无法获取值
 * 在WorkflowIdempotentAspectService 切面中统一清理线程中的值
 * 不允许直接return map
 */
public class WorkflowThreadLocalUtils {

    private static final ThreadLocal<Map> localMap = new ThreadLocal<Map>() {
        @Override
        protected Map initialValue() {
            return new HashMap();
        }
    };

    public static void put(String key, Object value) {
        localMap.get().put(key, value);
    }

    public static Object get(String key) {
        return localMap.get().get(key);
    }

    public static String getString(String key) {
        Object o = localMap.get().get(key);
        if (o instanceof String){
            return (String) o;
        }
        return null;
    }

    public static void removeAll() {
        localMap.remove();
    }

}
