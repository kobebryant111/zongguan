package com.tansun.wf.activiti.listener;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import com.tansun.wf.activiti.service.WorkflowProcessDefineService;
import com.tansun.wf.activiti.service.WorkflowTaskService;

/**
 * 岗位模式会签流转自动设置用户监听
 * @author ldh
 */
public class HqFlowListener implements ExecutionListener {

    private static RuntimeService runtimeService;

    private static WorkflowProcessDefineService workflowProcessDefineService;

    private static WorkflowTaskService workflowTaskService;

    public static void initHqFlowListener(RuntimeService runtimeService,
                                          WorkflowProcessDefineService workflowProcessDefineService,
                                          WorkflowTaskService workflowTaskService){
        HqFlowListener.runtimeService = runtimeService;
        HqFlowListener.workflowProcessDefineService = workflowProcessDefineService;
        HqFlowListener.workflowTaskService = workflowTaskService;
    }

    @Override
    public void notify(DelegateExecution execution) {
//        FlowElement flow = execution.getBpmnModelElementInstance();
//        if(!(flow instanceof SequenceFlowImpl)){
//            return;
//        }
//        SequenceFlowImpl sf = (SequenceFlowImpl) flow;
//        FlowNode target = sf.getTarget();
//        if(!(target instanceof UserTaskImpl)){
//            return;
//        }
//        UserTaskImpl uk = (UserTaskImpl) target;
//        Map<String, Object> map = new HashMap<>();
//        UserTaskAssignment assignmentModel = workflowProcessDefineService
//                .getNodeExtensionProperty(execution.getProcessDefinitionId(), uk.getId(), UserTaskAssignment.class,UserTaskAssignment.class);
//        Set<String> users = workflowTaskService.getCandidateUsers(execution.getTenantId(), execution.getId(),
//                execution.getProcessInstanceId(), execution.getVariables(), execution.getProcessDefinitionId(),
//                uk.getId(), execution, assignmentModel);
//        // 如果配置了候选用户，做并集处理
//        if(!uk.getCamundaCandidateUsersList().isEmpty()){
//            users.addAll(uk.getCamundaCandidateUsersList());
//        } else if(uk.getExtensionElements() != null){
//            Collection<UserTaskAssignment> els = uk.getExtensionElements().getChildElementsByType(UserTaskAssignment.class);
//            UserTaskAssignment el = (UserTaskAssignment) els.toArray()[0];
//            users.addAll(el.getCandidateUsersList());
//        }
//        map.put("users", users);
//        runtimeService.setVariables(execution.getId(), map);
    }

}
