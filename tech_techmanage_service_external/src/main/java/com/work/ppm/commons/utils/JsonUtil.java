package com.work.ppm.commons.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json 工具类
 */
@Slf4j
public class JsonUtil {

    /**
     * json对象
     */
    private ObjectMapper mapper;

    /**
     * 类型转换
     * @param object
     * @param distCls
     * @return T
     */
    public static <T> T toClass(Object object,Class<T> distCls) {
        if(object instanceof String){
            return JsonUtil.build().fromJson((String)object,distCls);
        }
        return JsonUtil.build().fromJson(JsonUtil.build().toJson(object),distCls);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Binder.
     * @return	JsonUtil
     */
    public static JsonUtil build() {
        return new JsonUtil();
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     *
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     * @param jsonString	String
     * @param <T>	<T>
     * @param clazz			Class<T>
     * @return	 T	<T>
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     *  如果对象为Null,返回"null".
     * 如果集合为空集合,返回"[]".
     * @param object	Object
     * @return			String
     */
    public String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 转换集合泛型
     * @param obj 对象
     * @param clazz 类名.class
     * @param <T> 泛型
     * @return List
     */
    public static <T> List<T> jsonToList(Object obj, Class<T> clazz) {
        String jsonStr = JSONObject.toJSONString(obj);
        return JSONObject.parseArray(jsonStr,clazz);
    }

    /**
     * 单个转换
     * @param obj 对象
     * @param clazz 类
     * @param <T> 泛型
     * @return T
     */
    public static <T> T jsonToClass(Object obj, Class<T> clazz) {
        String jsonStr = JSONObject.toJSONString(obj);
        return JSONObject.parseObject(jsonStr,clazz);
    }

    /**
     * 转javaBean
     * @param json 字符串
     * @param clazz 类型
     * @return T
     */
    public static<T> T jsonToClass(String json, Class<T> clazz) {
        Object obj = JSONObject.parse(json);
        String str = JSONObject.toJSONString(obj);
        return JSONObject.parseObject(str, clazz);
    }

    /**
     * 对象转Map
     * @param obj 对象
     * @param clazz 类
     * @param <T> 泛型
     * @return map
     */
    public static <T,E> Map<String,E> objectTOMap(Object obj, Class<T> clazz) {
        Map<String,E> map = new HashMap<>();
       try{

           for (Field field: clazz.getDeclaredFields()) {
               field.setAccessible(true);
               String fieldName = field.getName();
               Object value = field.get(obj);
               if(null == value) {
                   continue;
               }
               map.put(fieldName, (E) value);
           }
       }catch (Exception e){
           log.error("JsonUtil",e);
       }
       return map;
    }

    /**
     * json字符串转map
     * @param json 字符串
     * @return map
     */
    public static Map<String,Object> jsonToMap(String json) {
        Map<String,Object> map = (Map<String,Object>) JSONObject.parse(json);
        for(Map.Entry<String,Object> obj : map.entrySet()) {
            log.info("============> key:{}，value:{}", obj.getKey(), obj.getValue());
        }
        return map;
    }


    /**
     * map 转对象
     * @param map map
     * @param clazz 类
     * @param <T> 泛型
     * @return T
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     * @throws InstantiationException InstantiationException
     */
    public static <T> T mapToObject(Map<String,Object> map, Class<T> clazz) {
        if(null == map) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            BeanUtils.populate(obj, map);
        }catch (InvocationTargetException e) {
            log.error("map 转对象异常", e);
        } catch (InstantiationException e) {
            log.error("map 转对象异常", e);
        } catch (IllegalAccessException e) {
            log.error("map 转对象异常", e);

        }

        return (T)obj;
    }

    /**
     * json字符串转对象
     * @param jsonStr 外包
     * @param clazz 类
     * @param <T> 泛型
     * @return T
     */
    public static <T> T stringToObject(String jsonStr, Class<T> clazz) {
        return JSONObject.parseObject(jsonStr, clazz);
    }

    /**
     * 针对特殊json， 带有转义符，可转为正常的json
     * @param json 字符串
     * @return string
     */
    public static String toJSONString(String json) {
        Object obj = JSONObject.parse(json);
        return JSONObject.toJSONString(obj);
    }

    /**
     * 对象转json字符串
     * @param object 对象
     * @return String
     */
    public static String toJSONString(Object object) {
        return JSONObject.toJSONString(object);
    }
}
