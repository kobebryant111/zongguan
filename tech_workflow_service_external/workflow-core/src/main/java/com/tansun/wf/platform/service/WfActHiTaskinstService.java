package com.tansun.wf.platform.service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.platform.mapper.WfActHiTaskinstMapper;
import com.tansun.wf.platform.model.WfActHiTaskinstEntity;
import com.tansun.wf.platform.query.WfActHiTaskinstQuery;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/6/1
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfActHiTaskinstService {

    @Autowired
    WfActHiTaskinstMapper wfActHiTaskinstMapper;

    public PageInfo  findPageByWfSuccess(WfActHiTaskinstQuery query){
        PageHelper.startPage(Integer.parseInt(query.getPageNum()),Integer.parseInt(query.getPageSize()));
        List<WfActHiTaskinstEntity> list = wfActHiTaskinstMapper.findPageByWfSuccessList(query);
        if(null!=list&&list.size()>0){
            for(WfActHiTaskinstEntity aht:list){
                try {
                    aht.setAvgTimeStr(this.durationFormart(aht.getAvgTime()));
                    aht.setMaxTimeStr(this.durationFormart(aht.getMaxTime()));
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return new PageInfo<>(list);
    }


    public Map<String, Object> getWfProEfflInfo(WfActHiTaskinstQuery query){
        Map<String, Object> result = new HashMap<>(4);
        JSONArray assigneeJsonArr = new JSONArray();
        JSONArray avgTimeJsonArr = new JSONArray();
        JSONArray countNumJsonArr = new JSONArray();
        List<WfActHiTaskinstEntity> ahtList;
        if("personnel".equals(query.getType())){
            ahtList = wfActHiTaskinstMapper.getWfProEffPersonnelInfo(query);
        } else if("organization".equals(query.getType())){
            ahtList = wfActHiTaskinstMapper.getWfProEffOrganizaInfo(query);
        } else {
            ahtList = wfActHiTaskinstMapper.getWfProEffProcessInfo(query);
        }
        //默认取前15条数据,如需灵活可配置，只需修改系统参数字典配置表中value
//		int size = this.getTopNum(WF_PRO_EFF_RED_TOP);
        int size  = 15;
        if(null!=ahtList && ahtList.size()<size){
            size = ahtList.size();
        }
        if(null!=ahtList){
            for(int i=0;i<size;i++){
                WfActHiTaskinstEntity aht = ahtList.get(i);
                assigneeJsonArr.add(aht.getAssignee());
                //保存小数点后2位
                BigDecimal bd = BigDecimal.valueOf(Double.valueOf(aht.getAvgTime()) / 60000d);
                double avgTimeDouble = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                avgTimeJsonArr.add(avgTimeDouble);
                countNumJsonArr.add(aht.getCountNum());
            }
        }
        result.put("xValue", assigneeJsonArr);
        result.put("y1Value", avgTimeJsonArr);
        result.put("y2Value", countNumJsonArr);
        result.put("top", size);
        return result;
    }

    /**
     * 时间转换
     */
    public String durationFormart(long millis)  {
        millis = millis < 0 ? 0 : millis;
        long oneDay = Duration.standardDays(1).getMillis();
        long oneHour = Duration.standardHours(1).getMillis();
        long oneMinute = Duration.standardMinutes(1).getMillis();

        Duration duration = new Duration(millis);
        //天
        int days = duration.toStandardDays().getDays();
        String formatStr = "";
        duration = duration.minus(oneDay * days);
        formatStr = days > 0 ? days + "天" : "";
        //小时
        long hours = duration.getStandardHours();
        duration = duration.minus(oneHour * hours);
        if (days > 0 || hours > 0) {
            formatStr += hours + "小时";
        }
        //分钟
        long minutes = duration.getStandardMinutes();
        duration = duration.minus(oneMinute * minutes);
        if (days > 0 || hours > 0 || minutes > 0) {
            formatStr += minutes + "分钟";
        }
        //秒
        formatStr += duration.getMillis() / 1000.0 + "秒";
        return formatStr;
    }
}
