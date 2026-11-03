package com.tansun.wf.activiti.listener;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tansun.wf.activiti.service.WorkflowHiTaskExpandDataService;
import com.tansun.wf.activiti.service.WorkflowTaskService;
import com.tansun.wf.camunda.bpmn.instance.DefineEvent;
import com.tansun.wf.platform.service.WfEventService;


public class GlobalTaskCompletedListener implements TaskListener,ApplicationContextAware {
	
    private    ApplicationContext applicationContext;
	

	@Override
	public void notify(DelegateTask delegateTask) {        
		WorkflowHiTaskExpandDataService workflowHiTaskExpandPlanService= applicationContext.getBean(WorkflowHiTaskExpandDataService.class);
		HistoryService historyService = applicationContext.getBean(HistoryService.class);
		HistoricTaskInstance  historicTaskInstance =historyService.createHistoricTaskInstanceQuery().taskId(delegateTask.getId()).singleResult();
		if (historicTaskInstance instanceof HistoricTaskInstanceEntity ) {
			HistoricTaskInstanceEntity hiTask = (HistoricTaskInstanceEntity) historicTaskInstance;
			workflowHiTaskExpandPlanService.taskToHistory(hiTask);
		}
//      //处理会签审批意见
      WorkflowTaskService workflowTaskService = this.applicationContext.getBean(WorkflowTaskService.class);
      workflowTaskService.dealVotingResult((TaskEntity)delegateTask);
      
	  WfEventService wfEventService = applicationContext.getBean(WfEventService.class);
	  wfEventService.trigger(delegateTask, DefineEvent.TASK_EVENT_COMPLETE);


	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}

	

}
