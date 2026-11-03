package com.tansun.workflow.api;

import com.tansun.workflow.model.TransBean;
import com.tansun.workflow.model.TransRequest;

public interface IWorkflowEventService {
	
	
	TransBean executeTrans(TransRequest  transRequest);

}
