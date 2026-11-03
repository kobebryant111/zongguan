package com.tansun.wf.activiti.listener.helper;

import com.tansun.wf.activiti.listener.HqFlowListener;
import com.tansun.wf.activiti.service.WorkflowProcessDefineService;
import com.tansun.wf.activiti.service.WorkflowTaskService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListenerHelper {

    @Autowired
    public ListenerHelper(RuntimeService runtimeService,
                          WorkflowProcessDefineService workflowProcessDefineService,
                          WorkflowTaskService workflowTaskService){
        HqFlowListener.initHqFlowListener(runtimeService, workflowProcessDefineService, workflowTaskService);
    }
}
