package com.tansun.workflow.web;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.base.utils.CommonUtils;
import com.tansun.wf.activiti.model.WfInstListQo;
import com.tansun.wf.activiti.service.EditorServiceProxyService;
import com.tansun.wf.activiti.service.WorkflowProcessInstanceService;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.service.WfPendTaskService;
import com.tansun.workflow.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 高级查询
 */
@RestController
@RequestMapping("/wf/advanced")
public class AdvancedQueryController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WfPendTaskService wfPendTaskService;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private EditorServiceProxyService editorServiceProxyService;

    @Autowired
    private WorkflowProcessInstanceService workflowProcessInstanceService;

    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 运行中的流程列表
     */
    @RequestMapping("/wfInstList")
    public WfResponseVO query(WfInstListQo param) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (StringUtils.isBlank(param.getStartTimeSort())&&StringUtils.isBlank(param.getEndTimeSort())){
                param.setEndTimeSort("desc");
            }
            ProcessBean processBean = workflowProcessInstanceService.queryWfInstList(param);
            PageInfo<Map<String, Object>> page = new PageInfo<>();
            page.setTotal(Long.parseLong(processBean.getTotal()));
            page.setList(processBean.getResultList());
            result.put("grid", page);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
        return WfResponseVO.successResponse(result);
    }

    @RequestMapping("/wfWorkItemList")
    public WfResponseVO queryOrg(@RequestParam Map<String,Object> reqMap, PageInfo page) throws Exception {
        TaskInfo taskInfo = new TaskInfo();
        Set<String> notExpandConditionSet = new HashSet<>();
        taskInfo.setUserId( this.commonUtils.getCurrLoginUser().getLoginName());

        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "tenantId"))) {
            taskInfo.setTenantId(MapUtils.getString(reqMap, "tenantId"));
            notExpandConditionSet.add("tenantId");
        }else{
            return WfResponseVO.errorResponse("参数tenantId不可为空");
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
            // 如果前端查询选择具体用户，设置userId
            taskInfo.setUserId(reqMap.get("userName").toString());

            // 根据用户取其机构、角色、部门等数据作为查询条件
            EditorInfo editorInfo = new EditorInfo();
            editorInfo.setUserId(reqMap.get("userName").toString());
            EditorBean allPostInfo = editorServiceProxyService.findAllPostInfo(editorInfo);
            List<PostInfo> postInfos = allPostInfo.getPostInfos();
            if(CollectionUtils.isNotEmpty(postInfos)){
                taskInfo.setPostInfos(postInfos);
            }
        }else{
            // 默认没有指定查询用户，查询所有人
            taskInfo.setQueryAllTask(true);
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskPoolStatus"))){
            taskInfo.setTaskPoolStatus(reqMap.get("taskPoolStatus").toString());
            notExpandConditionSet.add("taskPoolStatus");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskId"))){
            taskInfo.setTaskId(reqMap.get("taskId").toString());
            notExpandConditionSet.add("taskId");
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap,"businessKey"))){
//            taskInfo.setBusinessKey(reqMap.get("businessKey").toString());
            taskInfo.setBusinessKeyLike("%"+reqMap.get("businessKey").toString()+"%");
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
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "createTimeBefore"))){
            taskInfo.setCreateTimeBefore(conversionTime(MapUtils.getString(reqMap, "createTimeBefore")));
            notExpandConditionSet.add("createTimeBefore");
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "createTimeAfter"))){
            taskInfo.setCreateTimeAfter(conversionTime(MapUtils.getString(reqMap, "createTimeAfter")));
            notExpandConditionSet.add("createTimeAfter");
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "endTimeBefore"))){
            taskInfo.setEndTimeBefore(conversionTime(MapUtils.getString(reqMap, "endTimeBefore")));
            notExpandConditionSet.add("endTimeBefore");
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "endTimeAfter"))){
            taskInfo.setEndTimeAfter(conversionTime(MapUtils.getString(reqMap, "endTimeAfter")));
            notExpandConditionSet.add("endTimeAfter");
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "processCreateTimeBefore"))){
            taskInfo.setProcessCreateTimeBefore(conversionTime(MapUtils.getString(reqMap, "processCreateTimeBefore")));
            notExpandConditionSet.add("processCreateTimeBefore");
        }
        if (StringUtils.isNotBlank(MapUtils.getString(reqMap, "processCreateTimeAfter"))){
            taskInfo.setProcessCreateTimeAfter(conversionTime(MapUtils.getString(reqMap, "processCreateTimeAfter")));
            notExpandConditionSet.add("processCreateTimeAfter");
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

        taskInfo.setTaskExpandCondition(taskExpandCondition);
        result.put("grid", wfPendTaskService.findWfPendOrgHiTaskPage(taskInfo,page));

        return WfResponseVO.successResponse(result);
    }

    private String conversionTime(String source){
        try{
            Date temp = sdf1.parse(source);
            return sdf2.format(temp);
        } catch (ParseException e) {
            logger.error("时间参数转换失败", e);
            throw new RuntimeException("时间参数必须是：yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        }
    }
}
