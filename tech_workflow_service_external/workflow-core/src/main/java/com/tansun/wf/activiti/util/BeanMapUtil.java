package com.tansun.wf.activiti.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanMap;

import java.util.List;
import java.util.Map;

/**
 * 对象转map工具类
 */
public class BeanMapUtil {
    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     * @param objList
     * @return
     */
    public static <T> List<Map> objectsToMaps(List<T> objList) {
        List<Map> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0,size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

}
