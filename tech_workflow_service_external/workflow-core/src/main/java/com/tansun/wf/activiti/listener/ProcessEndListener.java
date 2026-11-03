package com.tansun.wf.activiti.listener;

import com.alibaba.fastjson.JSONObject;
import com.tansun.wf.activiti.service.HttpWorkController;
import com.tansun.wf.activiti.service.WorkflowHiTaskExpandDataService;
import com.tansun.wf.activiti.service.WorkflowTaskService;
import com.tansun.workflow.api.IWorkflowController;
import com.tansun.workflow.model.ProcessBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;


public class ProcessEndListener   implements ExecutionListener,ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(ProcessEndListener.class);
	
	private  ApplicationContext applicationContext;



	public void notify(DelegateExecution execution) throws Exception {
		WorkflowHiTaskExpandDataService  workflowHiTaskExpandPlanService = this.applicationContext.getBean(WorkflowHiTaskExpandDataService.class);

		HistoryService historyService = applicationContext.getBean(HistoryService.class);
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(execution.getId()).list();
		List<String> taskIds = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(historicTaskInstances)){
			for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
				if (historicTaskInstance instanceof HistoricTaskInstanceEntity) {
					HistoricTaskInstanceEntity hiTask = (HistoricTaskInstanceEntity) historicTaskInstance;
					workflowHiTaskExpandPlanService.taskToHistory(hiTask);
					taskIds.add(hiTask.getId());
				}
			}
		}

		// 流程结束后更新流程结束时间
		workflowHiTaskExpandPlanService.updateHiExpandProcessEndTime(taskIds,new Date());

        ExecutionEntity executionEntity = (ExecutionEntity)execution;
        workflowHiTaskExpandPlanService.updateLastExpandData(executionEntity);
    	String processDefinitionId = execution.getProcessDefinitionId();
    	ProcessBean processBean = new ProcessBean();
    	processBean.setProcessDefineId(processDefinitionId);
//    	taskInfo.setProcessStatus(IS_PROCESS);
    	WorkflowTaskService workflowTaskService = this.applicationContext.getBean(WorkflowTaskService.class);
		processBean = workflowTaskService.findProcessHandleClass(processBean);
//		if (!org.springframework.util.StringUtils.isEmpty(processBean.getExtendedData())){
		if (processBean.getExtendedData() != null) {
			//本地接口
			String process_completed_class=processBean.getExtendedData().get("process_completed_class").replaceAll("\"", "");
			IWorkflowController iWorkflowController = null;
			if(StringUtils.isNotEmpty(process_completed_class) && this.applicationContext.containsBean(process_completed_class)){
				iWorkflowController = (IWorkflowController) this.applicationContext.getBean(process_completed_class);
			}
			//远程接口
			HttpWorkController httpWorkflowController = this.applicationContext.getBean(HttpWorkController.class);

			if (iWorkflowController != null || (
					httpWorkflowController.getMethods()!= null && httpWorkflowController.getMethods().contains(HttpWorkController.PROCESS_COMPLETED_TRANS))) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("businessKey", executionEntity.getProcessBusinessKey());
				map.put("processDefinitionId", execution.getProcessDefinitionId());
				map.put("activityId", executionEntity.getActivityId());
				map.put("processInstanceId", execution.getProcessInstanceId());
				map.put("tenantId",executionEntity.getTenantId());
				Map<String, Object> variables = executionEntity.getVariables();

				//流程变量，包含最后一个调用规则的结果
				map.put("processVariables", variables);
				Object jet = variables.get("_TASK_EXPAND_DATA");
				if (jet != null) {
					Map<String, String> taskmap = (Map<String, String>) JSONObject.parse(jet.toString());
					//待办扩展数据
					map.put("taskExpandData", taskmap);
				}
				map.put("comment", variables.get("comment"));
//				String domainStr = MapUtils.getString(variables,ExecutionDB._RULE_DOMAIN_KEY);
//				if(StringUtils.isNotBlank(domainStr)){
//					ExecutionDB executionDB = new ExecutionDB(domainStr);
//					Map<String, Object> blockData = executionDB.getBlockData(ExecutionDB._APPLY_DATA_BLOCK);
//					if (blockData != null){
//						// 启动的formData
//						map.put("formData", blockData);
//					}
//					Map<String, Object> collectData = executionDB.getBlockData(ExecutionDB._COLLECT_DATA_BLOCK);
//					if (collectData != null){
//						// 数据源采集的所有数据
//						map.put("allCollectData", collectData);
//					}
//					Map<String, Object> ruleData = executionDB.getBlockData(ExecutionDB._RULE_DATA_BLOCK);
//					if (ruleData != null){
//						// 调用规则的所有结果
//						map.put("allRuleResultData", ruleData);
//					}
//				}
				// 获取最后一个节点提交的用户id
				// 最后一个节点是普通用户节点
				List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(execution.getProcessInstanceId()).unfinished().listPage(0, 1);
				if (CollectionUtils.isNotEmpty(list)) {
					HistoricTaskInstance instance = list.get(0);
					map.put("userId", instance.getAssignee());
				}else{
				    // 如果没有普通用户节点，可能最后一个节点是子流程
                    List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(execution.getProcessInstanceId()).unfinished().listPage(0, 1);
                    if(CollectionUtils.isNotEmpty(historicActivityInstances)){
                        HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(0);
                        if("callActivity".equals(historicActivityInstance.getActivityType())){
                            List<HistoricTaskInstance> subHistoricTaskInstance = historyService.createHistoricTaskInstanceQuery().processInstanceId(historicActivityInstance.getCalledProcessInstanceId()).unfinished().listPage(0, 1);
                            map.put("userId", subHistoricTaskInstance.get(0).getAssignee());
                        }
                    }else{
                        logger.info("userId is not found, processInstanceId: " + execution.getProcessInstanceId());
                    }
                }
				//TODO  先注释规则调用
//				try {
//					if(iWorkflowController!=null){
//						logger.info(" iWorkflowController.processCompletedTrans executed , processInstanceId : " + execution.getProcessInstanceId() );
//						iWorkflowController.processCompletedTrans(map,process_completed_class);
//					}else{
//						logger.info("iWorkflowController is not find,processInstanceId="+execution.getProcessInstanceId() );
//					}
//
//					if(httpWorkflowController.getMethods().contains(HttpWorkController.PROCESS_COMPLETED_TRANS)){
//						map.put("process_completed_class", process_completed_class);
//						logger.info(" httpWorkflowController.processCompletedTrans executed , processInstanceId : " + execution.getProcessInstanceId() );
//						httpWorkflowController.processCompletedTrans(map,null);
//					}else{
//						logger.info("httpWorkflowController workflow.controller.methods not contain processCompletedTrans" + execution.getProcessInstanceId());
//					}
//				}catch (Exception e){
//					logger.error(" httpWorkflowController.processCompletedTrans executed"+e.getMessage());
//					throw new RuntimeException(e);
//				}
				return;

			}else {
				logger.info("iWorkflowController == null && workflow.controller.methods not contain processCompletedTrans" + execution.getProcessInstanceId());
			}
		}
	
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext  = applicationContext;
	}

}
