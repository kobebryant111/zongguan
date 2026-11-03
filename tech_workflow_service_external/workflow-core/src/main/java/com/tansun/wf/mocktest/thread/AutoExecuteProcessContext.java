package com.tansun.wf.mocktest.thread;

import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowClientService;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class AutoExecuteProcessContext {

    private ApplicationContext applicationContext;

    private String processInstanceId;

    private String processDefKey;

    private String states;

    private Map nextTask;

    private TaskBean nextTaskParams;

    public TaskBean getNextTaskParams() {
        return nextTaskParams;
    }

    public void setNextTaskParams(TaskBean nextTaskParams) {
        this.nextTaskParams = nextTaskParams;
    }



    private WorkFlowClientService workFlowClientService;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public Map getNextTask() {
        return nextTask;
    }

    public void setNextTask(Map nextTask) {
        this.nextTask = nextTask;
    }

    public WorkFlowClientService getWorkFlowClientService() {
        return workFlowClientService;
    }

    public void setWorkFlowClientService(WorkFlowClientService workFlowClientService) {
        this.workFlowClientService = workFlowClientService;
    }

}
