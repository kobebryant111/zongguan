package com.tansun.wf.platform.service;

import com.tansun.wf.platform.mapper.WfReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2021/1/12
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfReportService {
    @Autowired
    WfReportMapper wfReportMapper;
    public List<Map<String,Object>> queryProcessNumber(String tenantId,Integer number){
//        List<Map<String,Object>> list = wfReportMapper.queryProcessNumber(tenantId);
        List<Map<String,Object>> list = new ArrayList<>();
        if(list == null){
            return new ArrayList<>();
        }
        if(list.size() <= number) {
            return list;
        }else {
           int sum = list.stream()
                   .skip(number -1)
                   .mapToInt(map -> Integer.parseInt(map.get("processNumber").toString()))
                   .sum();
            List<Map<String,Object>> resultList = list.subList(0,number - 1);
            Map<String,Object> map = new HashMap<>(2);
            map.put("processName","其他");
            map.put("processNumber",sum);
            resultList.add(map);
            return resultList;
        }
    }

    public List<Map<String,Object>> queryRunProcessNumber(String tenantId){
       List<Map<String, Object>> list = wfReportMapper.queryRunProcessNumber(tenantId);
       list.forEach(map -> map.put("runProcessNumber",Integer.toString(Integer.parseInt(map.get("processNumber").toString())
               - Integer.parseInt(map.get("endProcessNumber").toString()))));
       return list;
    }


    public List<Map<String,Object>> queryProcessState(String tenantId) {
        List<Map<String, Object>> list = wfReportMapper.queryProcessState(tenantId);
        // 查询异常流程数
        Map<String,Object> map = list.get(0);
        map.put("异常流程数",10);
        return list;
    }

    public List<Map<String,Object>> queryUserProcessNumber(String tenantId, String userId,String year) {
        List<Map<String, Object>> list = wfReportMapper.queryUserProcessNumber(tenantId, userId);
        List<Map<String, Object>> resultList = new ArrayList<>();
        list.forEach(map ->{
            if(year.equals(map.get("mont").toString().substring(0,4))){
                resultList.add(map);
            }
        });
        return resultList;
    }
}
