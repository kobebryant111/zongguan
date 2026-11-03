package com.tansun.wf.activiti.listener;

import com.tansun.wf.activiti.service.WorkflowHiTaskExpandDataService;
import com.tansun.wf.activiti.service.WorkflowThreadLocalUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * 全局删除事件监听器
 */
public class GlobalTaskDeleteListener implements TaskListener,ApplicationContextAware {
	
    private ApplicationContext applicationContext;
	

	@Override
	public void notify(DelegateTask delegateTask) {
		WorkflowHiTaskExpandDataService workflowHiTaskExpandPlanService= applicationContext.getBean(WorkflowHiTaskExpandDataService.class);
		HistoryService historyService = applicationContext.getBean(HistoryService.class);
		HistoricTaskInstance  historicTaskInstance =historyService.createHistoricTaskInstanceQuery().taskId(delegateTask.getId()).singleResult();
		if (historicTaskInstance instanceof HistoricTaskInstanceEntity ) {
			HistoricTaskInstanceEntity hiTask = (HistoricTaskInstanceEntity) historicTaskInstance;
			workflowHiTaskExpandPlanService.taskToHistory(hiTask);
		}
		List<String> beReBackTaskIds = (List)WorkflowThreadLocalUtils.get("beReBack");
		if(CollectionUtils.isNotEmpty(beReBackTaskIds)){
			for (String beReBackTaskId : beReBackTaskIds) {
				if(StringUtils.equals(delegateTask.getId(),beReBackTaskId)){
					delegateTask.setAssignee(null);
					((TaskEntity) delegateTask).delete("beReBack",false);
					break;
				}
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
