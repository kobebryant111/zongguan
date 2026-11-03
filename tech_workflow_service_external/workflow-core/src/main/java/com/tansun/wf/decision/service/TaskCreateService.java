package com.tansun.wf.decision.service;

import com.tansun.wf.decision.handler.InvokeType;
import com.tansun.wf.decision.handler.TaskCreateServiceHandler;
import com.tansun.workflow.model.ActExpandTracelog;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("taskCreate")
public class TaskCreateService implements TaskListener{

	private static final long serialVersionUID = -4998859917700338193L;

	private static Logger logger = LoggerFactory.getLogger(TaskCreateService.class);

	@Autowired
	private TaskCreateServiceHandler taskCreateServiceHandler;

	@Override
	public void notify(DelegateTask delegateTask) {
		try {
			taskCreateServiceHandler.createTask(delegateTask, InvokeType.CREATE_TASK, new ActExpandTracelog());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}

	
}
