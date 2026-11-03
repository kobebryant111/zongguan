package com.tansun.wf.decision.service;

import com.tansun.wf.decision.handler.InvokeType;
import com.tansun.wf.decision.handler.WorkflowBusinessHandler;
import com.tansun.workflow.model.ActExpandTracelog;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("rule")
public class CallRuleDelegate implements JavaDelegate{

	@Autowired
    @Qualifier("callRuleServiceHandler")
	private WorkflowBusinessHandler workflowBusinessHandler;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
        workflowBusinessHandler.dealBusiness(execution, InvokeType.CALL_RULE, new ActExpandTracelog());
	}
	
}
