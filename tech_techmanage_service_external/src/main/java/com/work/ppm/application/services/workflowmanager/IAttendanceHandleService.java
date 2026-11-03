package com.work.ppm.application.services.workflowmanager;

import com.alibaba.fastjson.JSONArray;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;

import java.util.Map;

/**
 * 异常考勤 - 对接手机工作平台
 */
public interface IAttendanceHandleService extends IWorkflowOutputService{

    /**
     * 异常考勤处理接口
     * @param kqRecordDto 对象
     * @return map
     */
    Map<String,Object> abnormalAttendanceHandle(PpmKqRecordDto kqRecordDto);

    /**
     * 异常考勤详情接口
     * @param pmId 经理工号
     * @return map
     */
    Map<String,Object> queryAbnormalAttendanceDetail(String pmId);

    /**
     * 消息推送接口
     * @param jsonArray 经理工号
     */
    void sendMessage(JSONArray jsonArray);

}
