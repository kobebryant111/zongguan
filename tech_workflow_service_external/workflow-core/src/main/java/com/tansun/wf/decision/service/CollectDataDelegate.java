package com.tansun.wf.decision.service;


import com.tansun.wf.decision.handler.InvokeType;
import com.tansun.wf.decision.handler.WorkflowBusinessHandler;
import com.tansun.workflow.model.ActExpandTracelog;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("collect")
public class CollectDataDelegate implements ExecutionListener,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4183593157946532898L;

	@Autowired
    @Qualifier("collectServiceHandler")
	private WorkflowBusinessHandler workflowBusinessHandler;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		if(execution.getEventName().equals(EVENTNAME_START)){
            workflowBusinessHandler.dealBusiness(execution, InvokeType.COLLEC_DATA, new ActExpandTracelog());
		}
	}
	

}
