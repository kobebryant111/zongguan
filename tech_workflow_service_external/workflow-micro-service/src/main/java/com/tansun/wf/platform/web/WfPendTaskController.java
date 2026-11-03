package com.tansun.wf.platform.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.base.utils.CommonUtils;
import com.jeedev.msdp.base.web.ResponseVO;
import com.tansun.wf.activiti.service.EditorServiceProxyService;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfClassifyEntity;
import com.tansun.wf.platform.model.WfProcessVariableConfigEntity;
import com.tansun.wf.platform.model.WfTaskExpandPlanEntity;
import com.tansun.wf.platform.query.WfProcessVariableConfigQuery;
import com.tansun.wf.platform.query.WfTaskExpandPlanQuery;
import com.tansun.wf.platform.service.WfClassifyService;
import com.tansun.wf.platform.service.WfPendTaskService;
import com.tansun.wf.platform.service.WfProcessVariableConfigService;
import com.tansun.wf.platform.service.WfTaskExpandPlanService;
import com.tansun.workflow.model.*;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;



/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/21
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/wfPendTask")
public class WfPendTaskController {

    private Logger logger = LoggerFactory.getLogger(WfPendTaskController.class);

    @Autowired
    WfPendTaskService wfPendTaskService;
    @Autowired
    WorkFlowClientService workFlowClientService;

    @Autowired
    EditorServiceProxyService editorServiceProxyService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;

    @Autowired
    WfTaskExpandPlanService wfTaskExpandPlanService;

    @Autowired
    WfProcessVariableConfigService wfProcessVariableConfigService;

    @Autowired
    WfClassifyService wfClassifyService;     
    
    @Autowired
	private  CommonUtils  commonUtils;
    
    


  

    @RequestMapping("/list")
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap, PageInfo page) throws Exception {
        TaskInfo taskInfo = new TaskInfo();
        Set<String> notExpandConditionSet = new HashSet<>();

        // 如果有传用户名称的参数
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "userId"))){
            taskInfo.setUserId(MapUtils.getString(reqMap, "userId"));
        }else{
            String loginName = commonUtils.getCurrLoginUser().getLoginName();
            String tenantId = commonUtils.getCurrLoginUser().getTenantId();
            taskInfo.setUserId(loginName);
            taskInfo.setTenantId(tenantId);
        }

        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "processInstanceId"))){
            taskInfo.setProcessInstanceId(reqMap.get("processInstanceId").toString());
            notExpandConditionSet.add("processDefineId");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "processDefineId"))){
            taskInfo.setProcessDefineId(reqMap.get("processDefineId").toString());
            notExpandConditionSet.add("processDefineId");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "claimStatus"))){
            taskInfo.setClaimStatus(Integer.valueOf(reqMap.get("claimStatus").toString()));
            notExpandConditionSet.add("claimStatus");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskPoolStatus"))){
            taskInfo.setTaskPoolStatus(reqMap.get("taskPoolStatus").toString());
            notExpandConditionSet.add("taskPoolStatus");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "taskId"))){
            taskInfo.setTaskId(reqMap.get("taskId").toString());
            notExpandConditionSet.add("taskId");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "userId"))){
            taskInfo.setUserId(reqMap.get("userId").toString());
            notExpandConditionSet.add("userId");
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
//        taskInfo.setQueryAllTask(true);
        Map<String, Object> result = new HashMap(1);
        Map<String, String> taskExpandCondition = new HashMap<>();
//        //其他额外条件
        for(String key:reqMap.keySet()){
            if (!notExpandConditionSet.contains(key)) {
                taskExpandCondition.put(key, reqMap.get(key) == null?"":reqMap.get(key).toString());
            }
        }
        taskInfo.setTaskExpandCondition(taskExpandCondition);
        result.put("grid", wfPendTaskService.findWfPendTaskPage(taskInfo,page));

        return WfResponseVO.successResponse(result);
    }

    @ResponseBody
    @RequestMapping("/getAllClassifyPendTasks")
    public WfResponseVO getAllClassifyPendTasks(@RequestBody TaskInfo info) throws Exception {
        try {
            List<TaskBean> allClassifyPendTasks = workFlowClientService.getAllClassifyPendTasks(info);
            return WfResponseVO.successResponse(allClassifyPendTasks);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse("查询失败");
        }
    }

    @RequestMapping(value = "/queryNextTaskList")
    public WfResponseVO queryNextTaskList(@RequestBody Map<String,Object> reqMap) throws Exception {
        String taskId = MapUtils.getString(reqMap, "taskId");
        String mPara = MapUtils.getString(reqMap, "mPara");
        TaskInfo info = new TaskInfo();
        info.setTaskId(taskId);
        if(!"".equals(mPara)){
            List<ValueType> mPara1 = JSON.parseArray(mPara,ValueType.class);
            info.setmPara(mPara1);
        }
        TaskBean taskBean = null;
        try {
            taskBean = workFlowClientService.queryNextTaskList(info);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse("查询下一审批任务失败：" + e.getMessage());
        }

        return WfResponseVO.successResponse(taskBean.getTaskList());
    }

    @RequestMapping(value = "/executeOperation")
    public WfResponseVO executeOperation(@RequestParam Map<String,Object> reqMap) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(reqMap.get("paramStr").toString());
        String assignee = (String) jsonObject.get("assignee");
        String userId = (String) jsonObject.get("userId");
        String mParaString = JSONObject.toJSONString(jsonObject.get("mPara"));
        HashMap mPara = null;
        if(StringUtils.isNotBlank(mParaString)){
             mPara = JSONObject.parseObject(mParaString,HashMap.class);
        }
        if (StringUtils.isBlank(assignee)) {
//            Session session = SessionUtil.getSession();
//            Map<String,Object> user = (Map<String, Object>) session.getAttribute(Constants.CURRENT_USER);
//            assignee = (String) user.get("loginName");
        	if (StringUtils.isNotBlank(userId)) {
        		assignee = userId;
        	}else 
            assignee =(String) commonUtils.getUser().get("loginName");
        }
        TaskBean taskBean;
        reqMap.put("userId",assignee);
        reqMap.put("mPara",mPara!=null?mPara:new HashMap<>());
        if (!StringUtils.isBlank(MapUtils.getString(reqMap, "taskId"))
                && !StringUtils.isBlank(MapUtils.getString(reqMap, "button"))) {
            try {
               taskBean = wfPendTaskService.executeOperation(reqMap);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                return WfResponseVO.errorResponse("系统异常:" +e.getMessage());
            }
            if ("1".equals(taskBean.getRetCode())) {
                return WfResponseVO.errorResponse(taskBean.getRetMsg());
            }
            return  WfResponseVO.successResponse(taskBean.getRetMsg());
        }else{
            return  WfResponseVO.errorResponse("任务ID和执行操作不能为空");
        }
    }

    @RequestMapping(value = "/taskNodeList")
    public WfResponseVO taskNodeList(@RequestParam Map<String,Object> reqMap) throws Exception {
        String processInstanceId = MapUtils.getString(reqMap, "processInstanceId");
        String taskId = MapUtils.getString(reqMap, "taskId");
        if(StringUtils.isBlank(processInstanceId)){
            return WfResponseVO.successResponse(new ArrayList());
        }
        ProcessInfo param = new ProcessInfo();
        param.setProcessInstanceId(processInstanceId);
        param.setTaskId(taskId);
        TaskBean result = workFlowClientService.findHiTaskByProcessInstanceId(param);
        return WfResponseVO.successResponse(result.getCreditHiTaskList());
    }

    @RequestMapping(value = "/workflowUser")
    public WfResponseVO workflowUser(@RequestParam Map<String,Object> reqMap,PageInfo page) throws Exception {
        EditorInfo info = new EditorInfo();
        info.setPageSize(page.getPageSize());
        info.setCurrentPage(page.getPageNum());
        String deptCodes = MapUtils.getString(reqMap, "deptCodes");
        if(StringUtils.isNotBlank(deptCodes)) {
            info.setDeptCodes(Arrays.asList(deptCodes.split(",")));
        }

        String orgCodes = MapUtils.getString(reqMap, "orgCodes");
        if (StringUtils.isNotBlank(orgCodes)){
            info.setOrgCodes(Arrays.asList(orgCodes.split(",")));
        }
        String userLoginNames = MapUtils.getString(reqMap, "userLoginNames");
        if (StringUtils.isNotBlank(userLoginNames)){
            info.setUserLoginNames(Arrays.asList(userLoginNames.split(",")));
        }

        String roleCodes = MapUtils.getString(reqMap, "roleCodes");
        if (StringUtils.isNotBlank(roleCodes)){
            info.setRoleCodes(Arrays.asList(roleCodes.split(",")));
        }
        EditorBean usersList = editorServiceProxyService.findUsersList(info);
        Map<String, Object> result = new HashMap<>();
        PageInfo pageInfo = new PageInfo(usersList.getUserList());
        pageInfo.setTotal(usersList.getTotal());
        result.put("grid", pageInfo );
        return WfResponseVO.successResponse(result);
    }



    @RequestMapping("/claimTask")
    public WfResponseVO claimTask(@RequestBody TaskInfo taskInfo) throws Exception {
    	if (StringUtils.isBlank(taskInfo.getUserId())) {
	    	Map<String,Object> map =commonUtils.getUser();
	    	if (map != null) {
	    		taskInfo.setUserId((String) map.get("loginName"));
	    	}
    	}
        TaskBean taskBean = workFlowClientService.claimWorkInfo(taskInfo);
        if("0".equals(taskBean.getRetCode())){
            return WfResponseVO.successResponse("认领成功");
        } else {
            return WfResponseVO.successResponse(taskBean.getRetMsg());
        }
    }
    
    @RequestMapping("/releaseTask")
    public WfResponseVO releaseTask(TaskInfo taskInfo) throws Exception {
        TaskBean taskBean = workFlowClientService.releaseTask(taskInfo);
        if("0".equals(taskBean.getRetCode())){
            return WfResponseVO.successResponse("释放成功");
        } else {
            return WfResponseVO.successResponse(taskBean.getRetMsg());
        }
    }
    
    

    @ResponseBody
    @RequestMapping("/getTaskExpandDetail")
    public WfResponseVO getTaskExpandDetail(@RequestBody TaskInfo info) throws Exception {
//        if (info.getTaskId() == null){
//            return WfResponseVO.successResponse(new HashMap<>());
//        }
//        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(info.getTaskId()).singleResult();
//        String id = null;
//        if (historicTaskInstance != null) {
//            id = historicTaskInstance.getProcessInstanceId();
//        }else {
//            return WfResponseVO.successResponse(new HashMap<>());
//        }
//        Object taskExpandData = null;
//        try {
//
//            taskExpandData = runtimeService.getVariable(id, "_TASK_EXPAND_DATA");
//
//        } catch (Exception e) {
//            HistoricVariableInstance task_expand_data = historyService.createHistoricVariableInstanceQuery().processInstanceId(id).variableName("_TASK_EXPAND_DATA").singleResult();
////            taskExpandData = task_expand_data.getValue();
//
//        }
//        if (taskExpandData != null) {
//            return WfResponseVO.successResponse(JSON.parseObject((String) taskExpandData));
//        }
        return WfResponseVO.successResponse(new HashMap<>());
    }

    /**
     * 扩展字段和流程变量组模拟表单
     * @param reqMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/virtualForm")
    @ResponseBody
    public WfResponseVO virtualForm(@RequestParam Map<String,Object> reqMap) throws Exception {

        String wfClassId = MapUtils.getString(reqMap, "wfClassId");
        String classifyCode = MapUtils.getString(reqMap, "classifyCode");
        if(StringUtils.isNotBlank(classifyCode)){
            WfClassifyEntity classifyEntity = wfClassifyService.findClassifyByCode(classifyCode);
            if (classifyEntity != null) {
                wfClassId = classifyEntity.getId();
            }
        }

        WfTaskExpandPlanQuery query = new WfTaskExpandPlanQuery();

        if(StringUtils.isNotBlank(wfClassId)){
            query.setWfClassId(wfClassId);
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "fieldKey"))){
            query.setFieldKey(MapUtils.getString(reqMap, "fieldKey"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "fieldStatus"))){
            query.setFieldStatus(MapUtils.getString(reqMap, "fieldStatus"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(reqMap, "fieldName"))){
            query.setFieldName(MapUtils.getString(reqMap, "fieldName"));
        }
        // camunda-TODO
//        List<WfTaskExpandPlanEntity> expandPlanEntities = wfTaskExpandPlanService.findList(query);
        List<WfTaskExpandPlanEntity> expandPlanEntities = new ArrayList<>();



        WfProcessVariableConfigQuery wfProcessVariableConfigQuery = new WfProcessVariableConfigQuery();
        if(StringUtils.isNotBlank(wfClassId)){
            wfProcessVariableConfigQuery.setClassifyId(wfClassId);
        }
        // camunda-TODO
        List<WfProcessVariableConfigEntity> variableConfigEntities = wfProcessVariableConfigService.findList(wfProcessVariableConfigQuery);

        //合并 去除
        for (final WfProcessVariableConfigEntity variableConfigEntity : variableConfigEntities) {
            boolean exists = CollectionUtils.exists(expandPlanEntities, new Predicate() {
                @Override
                public boolean evaluate(Object object) {
                    WfTaskExpandPlanEntity o = (WfTaskExpandPlanEntity) object;
                    return StringUtils.equals(o.getFieldKey(), variableConfigEntity.getVarCode());
                }
            });
            if (exists){
                continue;
            }
            WfTaskExpandPlanEntity e = new WfTaskExpandPlanEntity();
            e.setWfClassId(variableConfigEntity.getClassifyId());
            e.setWfClassName(variableConfigEntity.getVarName());
            e.setFieldKey(variableConfigEntity.getVarCode());
            e.setFieldName(variableConfigEntity.getVarName());
            e.setFieldType(variableConfigEntity.getVarType());
            e.setFieldStatus("1");
            e.setIsQueryCondition("Y");
            e.setFieldValue("var");
            expandPlanEntities.add(e);
        }

        if (variableConfigEntities == null && expandPlanEntities ==null) {
            return WfResponseVO.successResponse(new ArrayList<>());
        }

        if (variableConfigEntities == null){
            return WfResponseVO.successResponse(expandPlanEntities);
        }


        return WfResponseVO.successResponse(expandPlanEntities);
    }

    /**
     * 可到达的任务
     */
    @RequestMapping("/getAllPath")
    @ResponseBody
    public WfResponseVO wfResponseVO(@RequestBody TaskInfo taskInfo){
        try {
            TaskBean allNextTaskInfo = workFlowClientService.getAllNextTaskInfo(taskInfo);
            Map result = new HashMap();
            result.put("grid", new PageInfo<>(allNextTaskInfo.getTaskList()));
            return WfResponseVO.successResponse(result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }

    }


        /**
     * 提交任务
     * 可以使用固定变量nextUser
     * 也可以添加流程变量
     */
    @ResponseBody
    @RequestMapping("/submitTask")
    public ResponseVO submitTask(@RequestBody TaskBean taskBean) {
        ResponseVO res=new ResponseVO();
        try {
            Map mPara = taskBean.getmPara();
            if (mPara != null) {
                for (Object key : mPara.keySet()) {
                    try {
                        //修复数组变成字符串问题
                        List<String> list = JSON.parseArray((String) mPara.get(key), String.class);
                        mPara.put(key, list);
                    } catch (Exception e) {

                    }
                }
            }
            TaskBean taskBean1 = workFlowClientService.submitTask(taskBean);
            if ("0".equals(taskBean.getRetCode())){
                res.setStatus(ResponseVO.OK);
                res.setDescription(taskBean.getRetMsg());
                return res;
            }else {
                res.setStatus(ResponseVO.ERROR);
                res.setDescription(taskBean.getRetMsg());
                return res;
            }
        }catch (Exception e){
            res.setStatus(ResponseVO.ERROR);
            res.setDescription(e.getMessage());
            return res;
        }

    }

}
