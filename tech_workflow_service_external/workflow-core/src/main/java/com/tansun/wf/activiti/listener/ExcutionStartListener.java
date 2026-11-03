package com.tansun.wf.activiti.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tansun.wf.camunda.bpmn.instance.DefineEvent;
import com.tansun.wf.platform.service.WfEventService;

public class ExcutionStartListener implements ExecutionListener, ApplicationContextAware {

	private  ApplicationContext applicationContext;

	public void notify(DelegateExecution execution) throws Exception {
		WfEventService wfEventService = applicationContext.getBean(WfEventService.class);
		wfEventService.trigger(execution, DefineEvent.EXECTION_EVENT_START);

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;		
	}

}
