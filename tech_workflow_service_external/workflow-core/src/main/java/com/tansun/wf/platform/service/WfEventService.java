package com.tansun.wf.platform.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;

import com.tansun.wf.camunda.bpmn.impl.instance.ExtendEvent;
import com.tansun.workflow.model.TransRequest;

public interface WfEventService {
	
	
	public void trigger(TransRequest transRequest,ExtendEvent extendEvent);

	void trigger(DelegateTask delegateTask, ExtendEvent extendEvent);

	void trigger(DelegateTask delegateTask, String enventType);

	void trigger(DelegateExecution execution, String eventType);

}
