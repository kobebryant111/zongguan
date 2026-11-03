package com.work.ppm.commons.utils;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.flowmanager.PpmFlowShouquanLog;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.flowmanager.IPpmFlowShouquanLogService;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.application.services.systemmanager.impl.CommEmployeeService;
import com.work.ppm.application.services.workflowmanager.IWorkflowAccessCardService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.exception.BizException;
import com.tansun.workflow.model.PostInfo;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Camunda 工作流辅助类
 */
@Slf4j
public class CamundaUtils {

    public static final String PAGE_SIZE = "100000";
    /**
     * 行员服务
     */
    private static CommEmployeeService commEmployeeService = SpringUtil.getBean(CommEmployeeService.class);
    /**
     * 流程授权日志
     */
    protected static IPpmFlowShouquanLogService ppmFlowShouquanLogService = SpringUtil.getBean(IPpmFlowShouquanLogService.class);

    /**
     * 工作流服务
     */
    private static WorkFlowHttpClientService workFlowHttpClientService = SpringUtil.getBean(WorkFlowHttpClientService.class);
    /**
     * 员工登录
     */
    protected static ICommOperatorService commOperatorService = SpringUtil.getBean(ICommOperatorService.class);
    /**
     * 流程对象
     */
    protected static IPpmProjectFlowService ppmProjectFlowService = SpringUtil.getBean(IPpmProjectFlowService.class);
    /**
     * 流程日志
     */
    protected static IPpmFlowLogService ppmFlowLogService = SpringUtil.getBean(IPpmFlowLogService.class);
    /**
     * 流程授权日志
     */
    protected static IWorkflowAccessCardService workflowAccessCardService =
            SpringUtil.getBean(IWorkflowAccessCardService.class);

    /**
     * 获取流程信息
     *
     * @param processInstanceId
     */
    public static TaskBean getTasksByProcessInstanceId(String processInstanceId) {
        try {
            return workFlowHttpClientService.getTasksByProcessInstanceId(processInstanceId);
        } catch (Exception e) {
            log.error("获取流程信息失败：{}", e);
        }
        return null;
    }

    /**
     * 获取已办任务列表
     *
     * @param instanceId
     * @param userId
     * @return
     */
    public static TaskBean findTaskList(String instanceId, String userId) {
        try {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskInfo.setProcessInstanceId(instanceId);
            taskInfo.setUserId(userId);
            return workFlowHttpClientService.findTaskList(taskInfo);
        } catch (Exception e) {
            log.error("查询已办任务失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询已办任务失败!");
        }
    }

    /**
     * 终止流程
     *
     * @param processInstanceId
     */
    public static void terminateProcess(String processInstanceId) {
        try {
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID);
            processInfo.setProcessInstanceId(processInstanceId);
            workFlowHttpClientService.terminateProcess(processInfo);
        } catch (Exception e) {
            log.error("终止流程失败：{}", e);
        }
    }

    /**
     * 删除流程
     *
     * @param processInstanceId
     */
    public static void deleteProcessInstance(String processInstanceId) {
        try {
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID);
            processInfo.setProcessInstanceId(processInstanceId);
            workFlowHttpClientService.deleteProcessInstance(processInfo);
        } catch (Exception e) {
            log.error("删除流程失败：{}", e);
        }
    }

    /**
     * 取下一步任务描述
     *
     * @param taskBean
     * @return
     */
    public static String findNextTaskInfo(TaskBean taskBean, String[] msgIds) {
        try {
            // 如果流程已完成成返回空
            if (ObjectUtils.isEmpty(taskBean) || taskBean.getIsFinished().equals("Y")) {
                return "流程已结束";
            }
            String taskId = (String) taskBean.getTaskList().get(0).get("taskId");
            // 取任务信息
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);
            taskInfo.setTaskId(taskId);
            taskBean = workFlowHttpClientService.findTaskInfo(taskInfo);
            if (ObjectUtils.isEmpty(taskBean)) return null;
            // 处理返回消息
            String nextActId = null;
            String nextActName = null;
            JSONArray nextUsers = null;
            List<String> nextUserIds = new ArrayList<>();
            List<String> nextUserNames = new ArrayList<>();
            //遍历任务
            for (Map task : taskBean.getTaskList()) {
                nextActId = (String) task.get("activityId");
                nextActName = (String) task.get("activityName");
                nextUsers = (JSONArray) task.get("powerUserNames");
            }

            if (null != msgIds && msgIds.length > 0) {
                for (String msgId : msgIds) {
                    nextUserIds.add(msgId);
                    CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(msgId);
                    nextUserNames.add(commEmployee.getEmpname());
                }
            } else {
                if (!CollectionUtils.isEmpty(nextUsers)) {
                    for (Object user : nextUsers) {
                        nextUserIds.add(user.toString());
                        CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(user.toString());
                        nextUserNames.add(commEmployee.getEmpname());
                    }
                }
            }
            return "流程ID:" + taskBean.getProcessInstanceId()
                    + "未结束,当前环节:" + nextActName
                    + ",当前活动定义ID:" + nextActId
                    + ",参与者:" + StringUtils.join(nextUserIds, ", ")
                    + "-" + StringUtils.join(nextUserNames, ", ");
        } catch (Exception e) {
            log.error("取任务描述失败：{}", e.getCause());
        }
        return null;
    }

    /**
     * 查询待办任务
     *
     * @param taskInfo
     * @return
     * @throws Exception
     */
    public static TaskBean findTodoList(TaskInfo taskInfo) throws Exception {
        taskInfo.setPageSize(PAGE_SIZE);//设置分页大小
        TaskBean taskBean = workFlowHttpClientService.findTodoList(taskInfo);
        if (!CollectionUtils.isEmpty(taskBean.getTaskList())) {
            Set set = new HashSet<String>();
            List<Map> taskList = Lists.newArrayList();
            for (Map task : taskBean.getTaskList()) {
                Object processId = task.get("processInstanceId");
                if (set.add(processId)) {
                    PpmProjectFlow projectFlow = ppmProjectFlowService.queryDetail(String.valueOf(processId));
                    if(!ObjectUtils.isEmpty(projectFlow) && "xxkjwblx".equals(projectFlow.getFlowid())) {
                        continue;
                    }
                    if (!ObjectUtils.isEmpty(projectFlow)) {
                        task.put("creators", projectFlow.getCreators());
                        task.put("flowTitle", projectFlow.getFlowtitle());
                        taskList.add(task);
                    }
                }
            }
            taskBean.setTaskList(taskList);
            taskBean.setTotal(String.valueOf(taskList.size()));
        }
        return taskBean;
    }
    public static TaskBean findXkTodoList(TaskInfo taskInfo) throws Exception {
        taskInfo.setPageSize(PAGE_SIZE);//设置分页大小
        TaskBean taskBean = workFlowHttpClientService.findTodoList(taskInfo);
        if (!CollectionUtils.isEmpty(taskBean.getTaskList())) {
            Set set = new HashSet<String>();
            List<Map> taskList = Lists.newArrayList();
            for (Map task : taskBean.getTaskList()) {
                Object processId = task.get("processInstanceId");
                if (set.add(processId)) {
                    PpmProjectFlow projectFlow = ppmProjectFlowService.queryDetail(String.valueOf(processId));
                    if(!ObjectUtils.isEmpty(projectFlow)  && !"xxkjwblx".equals(projectFlow.getFlowid())) {
                        continue;
                    }
                    if (!ObjectUtils.isEmpty(projectFlow)) {
                        task.put("creators", projectFlow.getCreators());
                        task.put("flowTitle", projectFlow.getFlowtitle());
                        taskList.add(task);
                    }
                }
            }
            taskBean.setTaskList(taskList);
            taskBean.setTotal(String.valueOf(taskList.size()));
        }
        return taskBean;
    }
    /**
     * 查询已办任务并去掉重复
     *
     * @param taskInfo
     * @return
     * @throws Exception
     */
    public static TaskBean findTaskList(TaskInfo taskInfo) throws Exception {
        taskInfo.setSingleHiTaskStatus("1");//去除重复
        taskInfo.setIsShowCurrentTask("Y");//显示当前任务
        taskInfo.setPageSize(PAGE_SIZE);//设置分页大小
        TaskBean taskBean = workFlowHttpClientService.findTaskList(taskInfo);
        if (!CollectionUtils.isEmpty(taskBean.getTaskList())) {
            Set set = new HashSet<String>();
            List<Map> taskList = Lists.newArrayList();
            for (Map task : taskBean.getTaskList()) {
//                JSONArray currentUserIds = (JSONArray) task.get("currentUserIds");
//                if(!ObjectUtils.isEmpty(currentUserIds) && currentUserIds.toJavaList(String.class).stream()
//                        .anyMatch(userId -> userId.equals(taskInfo.getUserId()))){
//                    continue;
//                }
                Object processId = task.get("processInstanceId");
                if (set.add(processId)) {
                    PpmProjectFlow projectFlow = ppmProjectFlowService.queryDetail(String.valueOf(processId));
                    if (!ObjectUtils.isEmpty(projectFlow) && "进行中".equals(projectFlow.getFlowstatus())) {
                        task.put("creators", projectFlow.getCreators());
                        task.put("flowTitle", projectFlow.getFlowtitle());
                        task.put("creators", task.get("processDefineName"));
                        taskList.add(task);
                    }
                }
            }
            taskBean.setTaskList(taskList);
        }
        return taskBean;
    }

    /**
     * 查询可退回的节点
     *
     * @param instanceId
     * @param activityId
     * @param taskId
     * @return
     */
    public static TaskBean findHiTask(String instanceId, String activityId, String taskId) throws Exception {
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setProcessInstanceId(instanceId);
        processInfo.setTaskId(taskId);
        TaskBean taskBean = workFlowHttpClientService.findHiTaskByProcessInstanceId(processInfo);
        if (!CollectionUtils.isEmpty(taskBean.getCreditHiTaskList())) {
            // 过滤当前节点
            List<Map<String, Object>> taskList = taskBean.getCreditHiTaskList().stream().filter(
                    item -> !activityId.equals(item.get("activitiId"))).collect(Collectors.toList());
            taskBean.setCreditHiTaskList(taskList);
        }
        return taskBean;
    }

    /**
     * 执行授权
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public static String doAuth(BpsWorkFlowDto dto) {
        try {
            // 发起转办业务
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskInfo.setTaskId(dto.getTaskId());
            taskInfo.setUserId(dto.getUserId());// 用户id/工号
            PostInfo postInfo = new PostInfo();
            postInfo.setType("user");
            postInfo.setUserId(dto.getPersonId());
            taskInfo.setTransferUser(postInfo);
            taskInfo.setComment(dto.getSuggest());
            TaskBean taskBean = workFlowHttpClientService.transferTask(taskInfo);
            if (!taskBean.getRetCode().equals("0")) {
                throw new BizException(ApiCode.FAIL, "任务转办失败！");
            }
            // 保存授权日志
            savePpmFlowShouquanLog(dto);

            //更新流程对象表
            PpmProjectFlow flow = ppmProjectFlowService.queryDetail(dto.getProcessInstId());
            updateProjectFlow(flow, dto);
            //取行员信息
            CommEmployee employee = UsersUtil.getCommEmployeeInfo(dto.getPersonId());
            //更新审批日志
            ppmFlowLogService.addFlowLog(dto);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,dto);
            return "流程ID:" + dto.getProcessInstId()
                    + "未结束,当前环节:" + dto.getActivityInstName()
                    + ",当前活动定义ID:" + dto.getActivityInstId()
                    + ",参与者: " + dto.getPersonId()
                    + "-" + employee.getEmpname();
        } catch (Exception e) {
            log.error("任务转办失败：{}", e);
            throw new BizException(ApiCode.FAIL, "任务转办失败！");
        }
    }

    /**
     * 撤回任务
     *
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public static TaskBean reBackTask(BpsWorkFlowDto dto) throws Exception {
            String processInstId = dto.getProcessInstId();
            PpmProjectFlow flow = ppmProjectFlowService.queryDetail(processInstId);
            if (ObjectUtils.isEmpty(flow) || "已结束".equals(flow.getFlowstatus())) {
                throw new BizException(ApiCode.FAIL, "已结束,无法撤回！");
            }
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processInstId);
            if (ObjectUtils.isEmpty(taskBean) || taskBean.getIsFinished().equals("Y")) {
                throw new BizException(ApiCode.FAIL, "无法撤回！");
            }
            String taskId = null;
            String actName = null;
            TaskBean taskInfo = CamundaUtils.findTaskList(processInstId, dto.getUserId());
            for (Map<String, Object> entity : taskInfo.getTaskList()) {
                taskId = entity.get("taskId").toString();
                actName = entity.get("activityName").toString();
                break;
            }
            if (ObjectUtils.isEmpty(taskId)) {
                throw new BizException(ApiCode.FAIL, "无法撤回！");
            }

            TaskBean params = new TaskBean();
            params.setTaskId(taskId);
            params.setUserId(dto.getUserId());
            params.setComment("撤回");
            TaskBean result = workFlowHttpClientService.rebackTask(params);
            if ("0".equals(result.getRetCode())) {
                //添加撤回日志
                dto.setConclusion(WorkflowConclusionEnums.WITHDRAW.getCode());
                dto.setApplydesc(actName);
                dto.setSuggest("撤回");
                ppmFlowLogService.addFlowLog(dto);
                CamundaExtUtils.deleteLog(processInstId);

                workflowAccessCardService.reBackTaskDelete(dto,taskBean);
            }
            return result;
    }

    /**
     * 添加流程日志
     *
     * @param bps
     */
    @Transactional(rollbackFor = Exception.class)
    public static void addFlowLog(BpsWorkFlowDto bps, TaskBean taskBean) {
        if (bps.getToDB()) {
            ppmFlowLogService.addFlowLog(bps);
        }
        PpmProjectFlow flow = ppmProjectFlowService.queryDetail(bps.getProcessInstId());
        if (null != taskBean && "Y".equals(taskBean.getIsFinished())) {
            CamundaUtils.endProjectFlow(flow, bps);
        } else {
            if (ObjectUtils.isEmpty(flow)) {
                CamundaUtils.saveProjectFlow(bps);
            } else {
                CamundaUtils.updateProjectFlow(flow, bps);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public static void addFlowLogCus(BpsWorkFlowDto bps, TaskBean taskBean) {
        if (bps.getToDB()) {
            ppmFlowLogService.addFlowLog(bps);
        }
        PpmProjectFlow flow = ppmProjectFlowService.queryDetail(bps.getProcessInstId());
        flow.setFlowtitle(bps.getProcessInstName());
        if (null != taskBean && "Y".equals(taskBean.getIsFinished())) {
            CamundaUtils.endProjectFlow(flow, bps);
        } else {
            if (ObjectUtils.isEmpty(flow)) {
                CamundaUtils.saveProjectFlow(bps);
            } else {
                CamundaUtils.updateProjectFlow(flow, bps);
            }
        }
    }
    /**
     * 添加流程数据
     */
    protected static void saveProjectFlow(BpsWorkFlowDto bps) {
        // 获取 bean 接口
        Date date = new Date();
        PpmProjectFlow flow = new PpmProjectFlow();
        flow.setProjectno(bps.getProjectNo());
        flow.setFlowtitle(bps.getProcessInstName());
        flow.setFlowname(bps.getProcessInstDesc());
        flow.setInstanceid(bps.getProcessInstId());
        flow.setFlowid(bps.getProcessDefName());
        flow.setFlowurl(bps.getFlowUrl());
        flow.setFlowcreateuserid(bps.getUserId());
        flow.setStartdate(date);
        flow.setFlowstatus("进行中");
        flow.setIsdel(0);
        flow.setLastmoduser(bps.getUserId());
        flow.setCreatetime(date);
        String userName = commOperatorService.getUserNameByUserId(bps.getUserId());
        flow.setCreators(userName + "(" + bps.getUserId() + ")");
        flow.setLastmodtime(date);
        int id = ppmProjectFlowService.addPpmProjectFlow(flow);
        if (id <= 0) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "创建流程日志失败");
        }
    }

    /**
     * 更新流程对象表
     *
     * @return int
     */
    public static int updateProjectFlow(PpmProjectFlow flow, BpsWorkFlowDto bps) {
        try {
            flow.setLastmodtime(new Date());
            flow.setLastmoduser(flow.getLastmoduser() + "," + bps.getUserId());
            ppmProjectFlowService.updatePpmProjectFlow(flow);
        } catch (Exception e) {
            log.error("BpsUtil-更新流程对象异常", e);
            throw new BizException(e);
        }
        return 1;
    }


    /**
     * 结束流程对象表
     *
     * @param bps 流程
     */
    protected static void endProjectFlow(PpmProjectFlow flow, BpsWorkFlowDto bps) {
        try {
            flow.setInstanceid(bps.getProcessInstId());
            flow.setFlowstatus("已结束");
            flow.setEnddate(new Date());
            flow.setLastmodtime(new Date());
            flow.setLastmoduser(flow.getLastmoduser() + "," + bps.getUserId());
            ppmProjectFlowService.updatePpmProjectFlow(flow);
        } catch (Exception e) {
            log.error("更新流程对象异常", e);
        }
    }

    /**
     * 删除流程对象表
     */
    @Transactional(rollbackFor = Exception.class)
    public static void delProjectFlow(String processInstId) {
        PpmProjectFlow flow = ppmProjectFlowService.queryDetail(processInstId);
        ppmProjectFlowService.deletePpmProjectFlow(flow);
        PpmFlowLog ppmFlowLog = new PpmFlowLog();
        ppmFlowLog.setFlowcaseno(processInstId);
        ppmFlowLogService.deletePpmFlowLog(ppmFlowLog);
    }

    /**
     * 添加授权日志
     *
     * @param bps 对象
     */
    protected static void savePpmFlowShouquanLog(BpsWorkFlowDto bps) {
        PpmFlowShouquanLog flowLog = new PpmFlowShouquanLog();
        flowLog.setProcessinstid(bps.getProcessInstId());
        flowLog.setWorkitemid(bps.getWorkItemId());
        flowLog.setShouquanren(bps.getUserId());
        flowLog.setBeishouquanren(bps.getPersonId());
        flowLog.setProcessinstname(bps.getProcessInstName());
        flowLog.setProcessdefname(bps.getProcessDefName());
        flowLog.setCreatetime(new Date());
        flowLog.setCreators(bps.getUserId());
        ppmFlowShouquanLogService.addPpmFlowShouquanLog(flowLog);
    }

}
