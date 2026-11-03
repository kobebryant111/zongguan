package com.tansun.workflow.web;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.base.utils.CommonUtils;
import com.jeedev.msdp.base.web.ResponseVO;
import com.tansun.wf.activiti.service.EditorServiceProxyService;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.mapper.WfTaskExpandDataMapper;
import com.tansun.wf.activiti.model.TaskExpandDataEntity;
import com.tansun.wf.platform.service.WfPendTaskService;
import com.tansun.workflow.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/wf/wfPendOrgTask")
public class WfPendOrgTaskController {
    @Autowired
    private WfPendTaskService wfPendTaskService;
    
    @Autowired
	private  CommonUtils  commonUtils;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private WfTaskExpandDataMapper wfTaskExpandDataMapper;
    
    @Autowired
    private  EditorServiceProxyService   editorServiceProxyService; 

	  
    @RequestMapping("/orglist")
    public WfResponseVO queryOrg(@RequestParam Map<String,Object> reqMap, PageInfo page) throws Exception {
        TaskInfo taskInfo = new TaskInfo();
        Set<String> notExpandConditionSet = new HashSet<>();

        // 如果有传用户名称的参数
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "userName"))){
            EditorInfo editorInfo = new EditorInfo();
            editorInfo.setUserId(MapUtils.getString(reqMap, "userName"));
            EditorBean allPostInfo = editorServiceProxyService.findAllPostInfo(editorInfo);
            taskInfo.setPostInfos(allPostInfo.getPostInfos());
        }else{
            // 如果没有填用户名称的参数，默认查询当前登陆人的任务池待办任务
            // 当前用户的机构，角色，部门，用户名等信息
            taskInfo.setUserId(this.commonUtils.getCurrLoginUser().getLoginName());
            taskInfo.setRoleCodes(Arrays.asList(this.commonUtils.getCurrLoginUser().getRoles().split(",")));
            taskInfo.setOrgCode((String)this.commonUtils.getUser().get("currInstCd"));
            taskInfo.setDeptCode((String)this.commonUtils.getUser().get("currDeptCd"));
        }

        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "processInstanceId"))){
            taskInfo.setProcessInstanceId(reqMap.get("processInstanceId").toString());
            notExpandConditionSet.add("processDefineId");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "processDefineId"))){
            taskInfo.setProcessDefineId(reqMap.get("processDefineId").toString());
            notExpandConditionSet.add("processDefineId");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "userName"))){
            taskInfo.setUserId(reqMap.get("userName").toString());
            notExpandConditionSet.add("userName");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskPoolStatus"))){
            taskInfo.setTaskPoolStatus(reqMap.get("taskPoolStatus").toString());
            notExpandConditionSet.add("taskPoolStatus");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskId"))){
            taskInfo.setTaskId(reqMap.get("taskId").toString());
            notExpandConditionSet.add("taskId");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "claimStatus"))){
            taskInfo.setClaimStatus(Integer.valueOf(reqMap.get("claimStatus").toString()));
            notExpandConditionSet.add("claimStatus");
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap,"businessKey"))){
            taskInfo.setBusinessKey(reqMap.get("businessKey").toString());
            notExpandConditionSet.add("businessKey");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskName"))){
            taskInfo.setTaskName(reqMap.get("taskName").toString());
            notExpandConditionSet.add("taskName");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "classifyCode"))){
            taskInfo.setWfClassifyCode(reqMap.get("classifyCode").toString());
            notExpandConditionSet.add("classifyCode");
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "tenantId"))) {
        	taskInfo.setTenantId(MapUtils.getString(reqMap, "tenantId"));
        	notExpandConditionSet.add("tenantId");
        	
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "queryAllTask"))) {
        	taskInfo.setQueryAllTask(Boolean.valueOf(MapUtils.getString(reqMap, "queryAllTask")));
        }
        Map<String, Object> result = new HashMap(1);
        Map<String, String> taskExpandCondition = new HashMap<>();
//        //其他额外条件
        for(String key:reqMap.keySet()){
            if (!notExpandConditionSet.contains(key)) {
                taskExpandCondition.put(key, reqMap.get(key) == null?"":reqMap.get(key).toString());
            }
        }
//        taskInfo.setQueryAllTask(true);
        taskInfo.setTaskExpandCondition(taskExpandCondition);
        result.put("grid", wfPendTaskService.findWfPendOrgTaskPage(taskInfo,page));

        return WfResponseVO.successResponse(result);
    }
    
    
    @RequestMapping("/userlist")
    public  ResponseVO  findUser() {
    	EditorInfo  editorInfo = new  EditorInfo();
    	editorInfo.setTenantId(this.commonUtils.getCurrLoginUser().getTenantId());
    	EditorBean editBean = this.editorServiceProxyService.findUsersList(editorInfo);

    	// 去重
        List<Map<String, Object>> userList = editBean.getUserList();
        Iterator<Map<String, Object>> iterator = userList.iterator();
        Set<String> userName = new HashSet<>();
        if(CollectionUtils.isNotEmpty(userList)){
            while(iterator.hasNext()){
                Map<String, Object> next = iterator.next();
                if(!userName.add(MapUtils.getString(next,"userLoginName"))){
                    iterator.remove();
                }
            }
        }
        ResponseVO  res  = new  ResponseVO();
    	res.setStatus(ResponseVO.OK);
    	res.setData(editBean.getUserList());
    	return  res;
    	
    }

    /**
     * 领取工作项
     */
    @RequestMapping("/claimWorkInfo")
    public  ResponseVO claimWorkInfo(@RequestBody TaskInfo taskInfo){
        ResponseVO  res  = new  ResponseVO();
        if (StringUtils.isBlank(taskInfo.getTaskId())){
            res.setStatus(ResponseVO.ERROR);
            res.setDescription("任务id不能为空");
            return res;
        }
        if (StringUtils.isBlank(taskInfo.getUserId())){
            res.setStatus(ResponseVO.ERROR);
            res.setDescription("任务处理人不能为空");
            return res;
        }
        if (StringUtils.isBlank(taskInfo.getTenantId())){
            res.setStatus(ResponseVO.ERROR);
            res.setDescription("租户id不能为空");
            return res;
        }
        TaskBean taskBean = wfPendTaskService.claimWorkInfo(taskInfo);
        if ("0".equals(taskBean.getRetCode())){
            res.setStatus(ResponseVO.OK);
            res.setDescription(taskBean.getRetMsg());
            return res;
        }else {
            res.setStatus(ResponseVO.ERROR);
            res.setDescription(taskBean.getRetMsg());
            return res;
        }
    }


    /**
     * 改派任务
     */
    @RequestMapping("/reAssignmentTask")
    public ResponseVO reAssignmentTask(@RequestBody TaskInfo taskInfo){
        ResponseVO  res  = new  ResponseVO();
        if (StringUtils.isBlank(taskInfo.getTaskId())){
            res.setStatus(ResponseVO.ERROR);
            res.setDescription("任务id不能为空");
            return res;
        }
        if (StringUtils.isBlank(taskInfo.getUserId())){
            res.setStatus(ResponseVO.ERROR);
            res.setDescription("任务处理人不能为空");
            return res;
        }
        if (StringUtils.isBlank(taskInfo.getTenantId())){
            res.setStatus(ResponseVO.ERROR);
            res.setDescription("租户id不能为空");
            return res;
        }
        //判断该任务是否被领取
//        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskInfo.getTaskId()).singleResult();
//        if (historicTaskInstance.getAssignee()==null){
//            res.setStatus(ResponseVO.ERROR);
//            res.setDescription("该任务还未被领取");
//            return res;
//        }
        Boolean reAssignmentFlag=wfPendTaskService.reAssignmentTask(taskInfo);
        if (reAssignmentFlag){
            res.setStatus(ResponseVO.OK);
            res.setDescription("任务改派成功");
        }else {
            res.setStatus(ResponseVO.ERROR);
            res.setDescription("任务改派失败");
        }
        return res;
    }


    /**
     * 编辑工作项
     */
    @RequestMapping("/EditTaskInfo")
    public ResponseVO EditTaskInfo(@RequestBody TaskInfo taskInfo){
        ResponseVO  res  = new  ResponseVO();
        if (StringUtils.isBlank(taskInfo.getTaskId())){
            res.setStatus(ResponseVO.ERROR);
            res.setDescription("任务id不能为空");
            return res;
        }
        if (StringUtils.isBlank(taskInfo.getTenantId())){
            res.setStatus(ResponseVO.ERROR);
            res.setDescription("租户id不能为空");
            return res;
        }
        TaskBean taskBean = wfPendTaskService.editInfoTask(taskInfo);
        if ("0".equals(taskBean.getRetCode())){
            res.setStatus(ResponseVO.OK);
            res.setDescription(taskBean.getRetMsg());
            return res;
        }else {
            res.setStatus(ResponseVO.ERROR);
            res.setDescription(taskBean.getRetMsg());
            return res;
        }
    }

    /**
     * 根据任务id查询扩展字段的url和name
     */
    @RequestMapping("/findTaskUrlAndNameInfo")
    public ResponseVO findTaskUrlAndNameInfo(@RequestBody TaskInfo taskInfo){
        ResponseVO responseVO=new ResponseVO();
        if (StringUtils.isBlank(taskInfo.getTaskId())){
            responseVO.setDescription("taskId不能为空");
            responseVO.setStatus(ResponseVO.ERROR);
            return responseVO;
        }
        TaskExpandDataEntity one = wfTaskExpandDataMapper.findTaskUrlAndNameInfo(taskInfo);
        responseVO.setStatus(ResponseVO.OK);
        responseVO.setData(one);
        return responseVO;
    }
}
