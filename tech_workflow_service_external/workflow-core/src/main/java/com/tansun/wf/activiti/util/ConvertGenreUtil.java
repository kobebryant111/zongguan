package com.tansun.wf.activiti.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/6/2
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class ConvertGenreUtil {
    /**
     * json string  -->  list
     * @param jsonStr
     * @return
     */
    public static List<Map<String, Object>> convertJsonStrArrayListToListMap(String jsonStr){
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Iterator<Object> it = jsonArray.iterator();

        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();
            Map<String, Object> map = JSON.parseObject(
                    jsonObject.toString(),new TypeReference<Map<String, Object>>(){} );
            listMap.add(map);
        }
        return listMap;
    }
}
