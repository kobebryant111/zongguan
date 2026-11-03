package com.tansun.wf.activiti.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author lijiang
 * @create 2019-10-16 17:43
 */
public class TransformUtil {

    public static Set<String> getValues(List<Map> data,String[] transKeys){
        Set<String> userNames = new HashSet<>();
        for (Map map : data) {
            for (String transKey : transKeys) {
                Object obj = map.get(transKey);
                if(obj instanceof String){
                    String[] split = String.valueOf(obj).split(",");
                    if(split!=null&&split.length>0){
                        userNames.addAll(Arrays.asList(split));
                    }
                }
                if(obj instanceof Collection){
                    Collection<String> collection = (Collection) obj;
                    for (String val : collection) {
                        if(StringUtils.isNotEmpty(val)){
                            userNames.add(val);
                        }
                    }
                }
            }
        }
        return userNames;
    }

    /**
     * 把查询出来的用户根据id-name放入map
     * @return
     */
    public static Map extract(List<Map<String,Object>> resultList,String key,String value){
        Map<String,Object> resultMap = new HashMap<>();
        if(resultList!=null){
            for (Map<String, Object> map : resultList) {
                String val = MapUtils.getString(map, value);
                if(StringUtils.isNotEmpty(val)){
                    resultMap.put(MapUtils.getString(map,key),map.get(value));
                }
            }
        }
        return resultMap;
    }

    public static List<Map> transfromString(List<Map> data,Map<String,Object> fromMap,String toKey,String toValue){
        for (Map map : data) {
            String value = MapUtils.getString(map, toKey);
            List<String> list = new ArrayList<>();
            if(StringUtils.isNotEmpty(value)){
                String[] vals = value.split(",");
                if(vals!=null&&vals.length>0){
                    for (String s : vals) {
                        if(fromMap.containsKey(s)){
                            list.add((String)fromMap.get(s));
                        }else{
                            list.add(s);
                        }
                    }
                }
            }
            map.put(toValue,StringUtils.join(list,","));
        }
        return data;
    }

    public static List<Map> transfromList(List<Map> data,Map fromMap,String toKey,String toValue){
        for (Map<String, Object> map : data) {
            List<String> ids = (List<String>)map.get(toKey);
            List<String> names = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(ids)){
                for (String id : ids) {
                    if(StringUtils.isNotEmpty(id)){
                        if(fromMap.containsKey(id)){
                            names.add((String)fromMap.get(id));
                        }else{
                            names.add(id);
                        }
                    }
                }
                map.put(toValue,names);
            }
        }
        return data;
    }
}
