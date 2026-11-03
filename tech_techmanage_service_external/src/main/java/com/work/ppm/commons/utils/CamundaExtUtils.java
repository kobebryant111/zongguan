package com.work.ppm.commons.utils;

import com.alibaba.fastjson.JSONArray;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.flowmanager.PpmNewflowHistorylog;
import com.work.ppm.application.services.flowmanager.IPpmNewflowHistorylogService;
import com.work.ppm.commons.constant.BusinessConstant;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;


/**
 *
 */
@Slf4j
public class CamundaExtUtils extends CamundaUtils{

    /**
     * 工作流服务
     */
    private static WorkFlowHttpClientService workFlowHttpClientService =
            SpringUtil.getBean(WorkFlowHttpClientService.class);

    /**
     * 历史流程日志
     */
    private static IPpmNewflowHistorylogService ppmNewflowHistorylogService =
            SpringUtil.getBean(IPpmNewflowHistorylogService.class);


    /**
     * 取下一步任务描述
     *
     * @param taskBean 任务bean
     */
    @Transactional(rollbackFor = Exception.class)
    public static void saveNextTaskInfo(TaskBean taskBean, String[] msgIds,BpsWorkFlowDto bps) {

        try {
            // 如果流程已完成成返回空
            if (ObjectUtils.isEmpty(taskBean) || "Y".equals(taskBean.getIsFinished())) {
                //删除已办结的空白流程日志
                deleteHistoryLog(bps.getProcessInstId());
                return ;
            }
            //防止taskList没有的问题
            if(taskBean.getTaskList() == null){
                taskBean = getTasksByProcessInstanceId(bps.getProcessInstId());
            }

            String taskId = (String) taskBean.getTaskList().get(0).get("taskId");
            // 取任务信息
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);
            taskInfo.setTaskId(taskId);
            taskBean = workFlowHttpClientService.findTaskInfo(taskInfo);
            if (ObjectUtils.isEmpty(taskBean)){
                return;
            }
            String nextActName = null;
            JSONArray nextUsers = null;
            //遍历任务
            List<Map> mapList = taskBean.getTaskList();
            for (Map task : mapList) {
                nextActName = (String) task.get("activityName");
                nextUsers = (JSONArray) task.get("powerUserNames");
            }

            //先删除处理中的空白流程日志
            deleteLog(bps.getProcessInstId());

            //获取下一步处理人
            if (null != msgIds && msgIds.length > 0) {
                for (String msgId : msgIds) {
                    setFlowLogValue(bps,msgId,nextActName);
                }
            } else {
                if (!CollectionUtils.isEmpty(nextUsers)) {
                    for (Object user : nextUsers) {
                        setFlowLogValue(bps,user.toString(),nextActName);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            log.error("取任务描述失败：{}", e.getCause().toString());
        }

    }

    /**
     * 存储下一步处理人信息
     * @param bps 对象
     * @param userId 下一步处理人
     * @param nextActName 名称
     */
    public static void setFlowLogValue(BpsWorkFlowDto bps,String userId,String nextActName){
        PpmFlowLog flowLog = new PpmFlowLog();
        flowLog.setFlowdraftno(bps.getProcessDefName());
        flowLog.setFlowcaseno(bps.getProcessInstId());
        flowLog.setUserid(userId);
        flowLog.setIsdel(0);
        flowLog.setApplydesc(nextActName);
        flowLog.setDeptno(bps.getDepId());
        ppmFlowLogService.addPpmFlowLog(flowLog);
    }

    /**
     * 删除处理中的空白日志
     * @param instanceId 流程编号
     */
    public static void deleteLog(String instanceId){
        PpmFlowLog delNullLog = new PpmFlowLog();
        delNullLog.setFlowcaseno(instanceId);
        ppmFlowLogService.deleteByNullLog(delNullLog);
    }

    /**
     * 删除已办结的空白日志
     * @param instanceId 流程编号
     */
    public static void deleteHistoryLog(String instanceId){
        PpmNewflowHistorylog delNullLog = new PpmNewflowHistorylog();
        delNullLog.setFlowcaseno(instanceId);
        ppmNewflowHistorylogService.deleteByHisNullLog(delNullLog);
    }
}
