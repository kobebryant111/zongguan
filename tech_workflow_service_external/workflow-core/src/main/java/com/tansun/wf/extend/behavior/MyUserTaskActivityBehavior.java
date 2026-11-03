package com.tansun.wf.extend.behavior;

import com.tansun.wf.extend.behavior.exception.MyBehaviorException;
import com.tansun.wf.platform.service.WfTaskSelectUserService;
import com.tansun.workflow.model.PostInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.camunda.bpm.engine.impl.task.TaskDecorator;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.UserTaskImpl;
import org.camunda.bpm.model.bpmn.instance.MultiInstanceLoopCharacteristics;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.util.Collection;

public class MyUserTaskActivityBehavior extends UserTaskActivityBehavior {

    public MyUserTaskActivityBehavior(TaskDecorator taskDecorator) {
        super(taskDecorator);
    }

    @Override
    public void performExecution(ActivityExecution execution) throws Exception {
        TaskEntity task = new TaskEntity((ExecutionEntity) execution);
        BpmnModelInstance ins = execution.getBpmnModelInstance();
        String id = execution.getCurrentActivityId();
        if(StringUtils.isBlank(id) || ins==null){
            throw new MyBehaviorException("activityId为空，请检查流程定义");
        }
        ModelElementInstance mele = ins.getModelElementById(id.split("#")[0]);
        UserTaskImpl uk = (UserTaskImpl)mele;
        Collection<MultiInstanceLoopCharacteristics> mls = uk.getChildElementsByType(MultiInstanceLoopCharacteristics.class);
        if(CollectionUtils.isNotEmpty(mls)) {
            MultiInstanceLoopCharacteristics characteristics = mls.iterator().next();
            String camundaElementVariable = characteristics.getCamundaElementVariable();
            Object variable = execution.getVariable(camundaElementVariable);
            if(variable instanceof PostInfo){
                ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)execution.getProcessEngine().getProcessEngineConfiguration();

                WfTaskSelectUserService wfTaskSelectUserService = (WfTaskSelectUserService)processEngineConfiguration.getBeans().get("wfTaskSelectUserService");
//                wfTaskSelectUserService.saveUserTaskAssignment(task,(PostInfo)variable);

            }else{
                throw new RuntimeException(camundaElementVariable+"多工作项对象不是PostInfo");
            }
        }
        task.insert();
        // initialize task properties
        taskDecorator.decorate(task, execution);
        // fire lifecycle events after task is initialized
        task.transitionTo(TaskEntity.TaskState.STATE_CREATED);
    }
}
