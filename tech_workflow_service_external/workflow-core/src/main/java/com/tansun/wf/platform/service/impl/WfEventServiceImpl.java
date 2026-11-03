package com.tansun.wf.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tansun.wf.activiti.service.HttpWorkController;
import com.tansun.wf.camunda.bpmn.impl.instance.ExtendEvent;
import com.tansun.wf.platform.service.WfEventService;
import com.tansun.workflow.api.IWorkflowEventService;
import com.tansun.workflow.model.TransRequest;

@Service
public class WfEventServiceImpl implements WfEventService,ApplicationContextAware {
	

	private  ApplicationContext   applicationContext ;
	
	@Override
	public  void  trigger(DelegateExecution execution,String eventType) {
		 List<ExtendEvent>  eventList = execution.getBpmnModelElementInstance().getExtensionElements().getElementsQuery().filterByType(ExtendEvent.class).list();
	     if (eventList != null &&  eventList.size()>0) {
	    	   for (ExtendEvent extendEvent: eventList) {
	    		    if (!eventType.equals(extendEvent.getEvent())) {
	    		    	continue;
	    		    }
					this.trigger(execution, extendEvent);
	    	   }
	     }	
	}
	
	public void trigger(DelegateExecution execution, ExtendEvent extendEvent) {
		DelegateExecution execution1 = execution;
		TransRequest transRequest  = new  TransRequest();
		transRequest.setProcessDefinitionId( execution.getProcessDefinitionId());
		transRequest.setActivityId( execution.getCurrentActivityId());
		transRequest.setTaskId(execution.getId());
		transRequest.setProcessInstanceId( execution.getProcessInstanceId());
		transRequest.setTenantId(execution.getTenantId());
		Map<String, Object> variables = execution.getVariables();
		transRequest.setProcessVariables(variables);
		this.trigger(transRequest, extendEvent);
	}
	
	@Override
	public void trigger(DelegateTask delegateTask,String enventType) {
		 List<ExtendEvent>  eventList = delegateTask.getBpmnModelElementInstance().getExtensionElements().getElementsQuery().filterByType(ExtendEvent.class).list();
	     if (eventList != null &&  eventList.size()>0) {
	    	   for (ExtendEvent extendEvent: eventList) {
	    		    if (!enventType.equals(extendEvent.getEvent())) {
	    		    	continue;
	    		    }
					this.trigger(delegateTask, extendEvent);
	    	   }
	     }		
	}
	
	
	
	@Override
	public void trigger(DelegateTask delegateTask, ExtendEvent extendEvent) {
		TransRequest transRequest  = new  TransRequest();
		transRequest.setProcessDefinitionId( delegateTask.getProcessDefinitionId());
		transRequest.setActivityId( delegateTask.getTaskDefinitionKey());
		//设置任务id
		transRequest.setTaskId(delegateTask.getId());
		transRequest.setProcessInstanceId( delegateTask.getProcessInstanceId());
		transRequest.setTenantId(delegateTask.getTenantId());
		Map<String, Object> variables = delegateTask.getVariables();
		transRequest.setProcessVariables(variables);
		this.trigger(transRequest, extendEvent);
	}


	
	public void trigger(TransRequest transRequest, ExtendEvent extendEvent)  {
           try {
			   if ("local".equals(extendEvent.getCallType())) {
				   IWorkflowEventService workflowController =   applicationContext.getBean(extendEvent.getServiceName(),IWorkflowEventService.class);
			       if (workflowController !=null) {
			    	   workflowController.executeTrans(transRequest);
			       }
			   }else {
				   HttpWorkController  httpWorkController =   applicationContext.getBean(HttpWorkController.class);
				   httpWorkController.restPost(extendEvent.getServiceName(), transRequest, null);
			
			   }
		   }catch(Exception e) {
			   throw  new RuntimeException("流程定义ID：="+transRequest.getProcessDefinitionId()+";活动ID:="+transRequest.getActivityId()+";事件：="+extendEvent.getServiceName(),e);
		   }
	   
	}


	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	

}
