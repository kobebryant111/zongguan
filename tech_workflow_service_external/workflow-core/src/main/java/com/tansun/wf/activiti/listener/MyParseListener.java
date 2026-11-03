package com.tansun.wf.activiti.listener;

import org.camunda.bpm.application.impl.event.ProcessApplicationEventParseListener;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.springframework.beans.factory.annotation.Autowired;

public class MyParseListener extends ProcessApplicationEventParseListener {
    @Autowired
    private GlobalTaskEventListener globalTaskEventListener;

    @Autowired
    private GlobalTaskCompletedListener globalTaskCompletedListener;

	@Autowired
	private GlobalTaskDeleteListener globalTaskDeleteListener;

	@Autowired
    private ProcessEndListener  processEndListener;
	
	@Autowired
	private  ExcutionEndListener  excutionEndListener;
	
	@Autowired
	private  ExcutionStartListener  excutionStartListener;

    @Override
    protected void addTaskCreateListeners(TaskDefinition taskDefinition) {
        taskDefinition.addTaskListener(TaskListener.EVENTNAME_CREATE,globalTaskEventListener);
    }

	@Override
	protected void addTaskCompleteListeners(TaskDefinition taskDefinition) {
		 taskDefinition.addTaskListener(TaskListener.EVENTNAME_COMPLETE,globalTaskCompletedListener);
	}

	@Override
	protected void addEndEventListener(ScopeImpl activity) {
	  	if (activity instanceof ProcessDefinitionEntity) {
			activity.addListener(ExecutionListener.EVENTNAME_END, processEndListener);
	   	}else{
	   		if ("userTask".equals(activity.getProperty("type"))) {
	   			activity.addListener(ExecutionListener.EVENTNAME_END, excutionEndListener);
	   		}else    		
			   super.addEndEventListener(activity);
	   	}
	}

	@Override
	protected void addTaskDeleteListeners(TaskDefinition taskDefinition) {
		taskDefinition.addTaskListener(TaskListener.EVENTNAME_DELETE,globalTaskDeleteListener);
	}

	@Override
	protected void addStartEventListener(ScopeImpl activity) {		
		if (activity instanceof ActivityImpl) {
			if ("userTask".equals(activity.getProperty("type"))) {
				activity.addListener(ExecutionListener.EVENTNAME_START, excutionStartListener);
			}else {
				super.addStartEventListener(activity);
			}
		}else {
			super.addStartEventListener(activity);
		}

	}
	
	
}
