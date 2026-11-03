package com.tansun.wf.platform.web;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.service.WfCompleteTaskService;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/29
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/wfCompleteTask")
public class WfCompleteTaskController {


    @Autowired
    WfCompleteTaskService wfCompleteTaskService;

    @RequestMapping("/list")
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo page) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setState("3");
        int pageNum = page.getPageNum();
        int pageSize = page.getPageSize() == 0? 999:page.getPageSize();
        taskInfo.setCurrentPage(String.valueOf(pageNum));
        taskInfo.setPageSize(String.valueOf(pageSize));
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "processDefineId"))){
            taskInfo.setProcessDefineId(reqMap.get("processDefineId").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "userId"))){
            taskInfo.setUserId(reqMap.get("userId").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "processInstanceId"))){
            taskInfo.setProcessInstanceId(reqMap.get("processInstanceId").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "isEnd"))){
            taskInfo.setIsEnd(reqMap.get("isEnd").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskId"))){
            taskInfo.setTaskId(reqMap.get("taskId").toString());
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "classifyCode"))){
            taskInfo.setWfClassifyCode(reqMap.get("classifyCode").toString());
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "tenantId"))) {
        	taskInfo.setTenantId(MapUtils.getString(reqMap, "tenantId"));
        }
        result.put("grid", wfCompleteTaskService.findWfCompleteTaskPage(taskInfo,page));
        return WfResponseVO.successResponse(result);
    }

    @RequestMapping( "/executeOperation")
    public WfResponseVO executeOperation(@RequestParam Map<String,Object> reqMap) throws Exception {
        Object object = reqMap.get("planstr");
        TaskBean taskBean = new TaskBean();
        String assignee = (String) JSONObject.parseObject(reqMap.get("paramStr").toString()).get("assignee");
        String userId = (String) JSONObject.parseObject(reqMap.get("paramStr").toString()).get("userId");
        if (StringUtils.isBlank(assignee)) {
//            Session session = SessionUtil.getSession();
//            Map<String,Object> user = (Map<String, Object>) session.getAttribute(Constants.CURRENT_USER);
//            assignee = (String) user.get("loginName");
            assignee = userId;
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskId"))
                && StringUtils.isNotBlank(MapUtils.getString(reqMap, "button")) && StringUtils.isNotBlank(userId)) {
            TaskBean executeTaskBean = new TaskBean();
            if (StringUtils.isNotBlank(MapUtils.getString(reqMap,"planstr"))) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String,String> plan = mapper.readValue(reqMap.get("planstr").toString(), Map.class);
                executeTaskBean.setTaskExpandData(plan);
            }
            if("getBack".equals(MapUtils.getString(reqMap, "button"))){
                executeTaskBean.setTaskId(MapUtils.getString(reqMap, "taskId"));
                executeTaskBean.setComment(MapUtils.getString(reqMap, "remarks"));
                executeTaskBean.setUserId(assignee);
                taskBean = wfCompleteTaskService.executeOperation(executeTaskBean);
            } else {
                return WfResponseVO.errorResponse( "执行失败");
            }
            if ("1".equals(taskBean.getRetCode())) {
                return  WfResponseVO.errorResponse(taskBean.getRetMsg());
            }
            return  WfResponseVO.successResponse(taskBean.getRetMsg());
        }else{
            return WfResponseVO.errorResponse( "任务ID和执行操作,用户ID不能为空");
        }
    }

    @RequestMapping(value = "/operation")
    public WfResponseVO operation(@RequestParam Map<String,Object> reqMap) throws Exception {
        String taskId = MapUtils.getString(reqMap, "taskId");
        String processDefinitionId = MapUtils.getString(reqMap, "processDefinitionId");
        String activityId = MapUtils.getString(reqMap, "activityId");
        if (StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(processDefinitionId)
                && StringUtils.isNotBlank(activityId)) {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskId(taskId);
            taskInfo.setProcessDefineId(processDefinitionId);
            taskInfo.setActivityId(activityId);
            Map<String, Object> map = wfCompleteTaskService.getWfCompleteTaskButton(taskInfo);
            if (map.keySet().size() == 0) {
                return WfResponseVO.errorResponse("无权限操作，请联系管理员在任务节点上分配按钮权限");
            }
            return WfResponseVO.successResponse(map);
        }else{
            return WfResponseVO.errorResponse("操作失败");
        }
    }
}
