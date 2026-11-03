//package com.tansun.wf.mocktest.service;
//
//import com.tansun.wf.activiti.cmd.GenericJumpCmd;
//import com.tansun.wf.extend.db.WfBaseManager;
//import com.tansun.wf.mocktest.mapper.WfSkipTaskConfigMapper;
//import com.tansun.wf.mocktest.model.WfSkipTaskConfigEntity;
//import com.tansun.workflow.model.DebugInfo;
//import com.tansun.workflow.model.ProcessBean;
//import com.tansun.workflow.model.ProcessInfo;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.camunda.bpm.engine.ManagementService;
//import org.camunda.bpm.engine.ProcessEngine;
//import org.camunda.bpm.engine.RepositoryService;
//import org.camunda.bpm.engine.TaskService;
//import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
//import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
//import org.camunda.bpm.engine.task.Task;
//import org.camunda.bpm.model.bpmn.BpmnModelInstance;
//import org.camunda.bpm.model.bpmn.instance.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.tansun.wf.activiti.constants.WorkflowConstants.FAILED;
//
//// camunda-TODO
//@Aspect
//@Component
//public class WfSkipTaskAspectService extends WfBaseManager<WfSkipTaskConfigMapper, WfSkipTaskConfigEntity> implements ApplicationContextAware {
//
//    private static Logger logger = LoggerFactory.getLogger(WfSkipTaskAspectService.class);
//
//    private static ApplicationContext act;
//
//    @Autowired
//    private ProcessEngine processEngine;
//
//    @Pointcut("execution(* com.tansun.wf.activiti.service.WorkflowService.startProcess(..)) || execution(* com.tansun.wf.activiti.service.WorkflowService.startAndSubmitProcess(..))")
//    private void operatePointCut() {
//    }
//
//
//    @Around(value = "operatePointCut()")
//    private Object setOperateInThreadLocal(ProceedingJoinPoint pjp) throws Throwable {
//        Object result = null;
//        try {
//            result = pjp.proceed();
//
//            try {
//                skipTask((ProcessInfo) pjp.getArgs()[0], (ProcessBean) result);
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//                throw new RuntimeException(e);
//            }
//        } catch (Throwable throwable) {
//            throw throwable;
//        }
//
//        return result;
//    }
//
//
//    /**
//     * 忽略中间任务，直接跳转至最后一步
//     *
//     * @param processInfo
//     * @param result
//     */
//    private void skipTask(ProcessInfo processInfo, ProcessBean result) {
//
//        WfSkipTaskConfigService wfSkipTaskConfigService = act.getBean(WfSkipTaskConfigService.class);
//        wfSkipTaskConfigService.fillWorkDebugConfig(processInfo);
//
//        TaskService taskService = act.getBean(TaskService.class);
//        RepositoryService repositoryService = act.getBean(RepositoryService.class);
//        ManagementService managementService = act.getBean(ManagementService.class);
//        DebugInfo debugInfo = processInfo.getDebugInfo();
//        if (debugInfo == null) {
//            return;
//        }
//        if (FAILED.equals(result.getRetMsg()) || !"Y".equals(debugInfo.getIsOpen())) {
//            return;
//        }
//
//        String processInstanceId = result.getProcessInstanceId();
//        List<Task> first = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
//        Task task = null;
//        if (CollectionUtils.isNotEmpty(first)) {
//            task = first.get(0);
//        }
//
//        String targetTaskNodeId = debugInfo.getTargetTaskNodeId();
//        String targetTaskAssignee = debugInfo.getTargetTaskAssignee();
//
//        // camunda-TODO
//        String processDefinitionId = task.getProcessDefinitionId();
//        BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);
//
//        UserTask endTask = null;
//        if (StringUtils.isNotBlank(targetTaskNodeId)) {
//            FlowElement flowElement = bpmnModel.getModelElementById(targetTaskNodeId);
//            if (flowElement instanceof UserTask) {
//                logger.warn("流程实例【" + processInstanceId + "】跨步调试指定的目标节点id【" + targetTaskNodeId + "】不存在，将跳转至最后一个审批任务");
//                endTask = (UserTask) flowElement;
//            }
//        }
//
//        // camunda-TODO
//        if (endTask == null) {
//            Collection<EndEvent> endEvents = bpmnModel.getModelElementsByType(EndEvent.class);
//            for (EndEvent endEvent : endEvents) {
//                UserTask userTask = findEndTask(bpmnModel, endEvent, endEvent.getIncoming());
//                if (userTask != null) {
//                    endTask = userTask;
//                    break;
//                }
//            }
//        }
//
//        if (endTask.getId().equals(task.getTaskDefinitionKey())) {
//            if (StringUtils.isNotBlank(targetTaskAssignee)) {
//                taskService.setAssignee(task.getId(), targetTaskAssignee);
//                logger.warn("-------- mocktest- -------- 流程实例【" + processInstanceId + "】跨步调试跳转至目标节点【" + endTask.getId() + " - " + endTask.getName() + "】,并由 【" + targetTaskAssignee + "】审批");
//            }else {
//                logger.warn("-------- mocktest- -------- 流程实例【" + processInstanceId + "】跨步调试跳转至目标节点【" + endTask.getId() + " - " + endTask.getName() + "】");
//            }
//            return;
//        }
//
//        Map<String, Object> params = new HashMap<>();
//        // camunda-TODO
//        String assignee = endTask.getCamundaAssignee();
//        List<String> candidateUsers = endTask.getCamundaCandidateUsersList();
//
//
//        String left = "${";
//        String right = "}";
//        if (StringUtils.isNotBlank(assignee) && assignee.indexOf(left) > -1 && assignee.indexOf(right) > -1) {
//            params.put(assignee.substring(2, assignee.length() - 1), "superadmin");
//        }
//
//        if (CollectionUtils.isNotEmpty(candidateUsers)) {
//            for (String candidateUser : candidateUsers) {
//                if (StringUtils.isNotBlank(candidateUser) && candidateUser.indexOf(left) > -1 && candidateUser.indexOf(right) > -1) {
//                    params.put(candidateUser.substring(2, candidateUser.length() - 1), "superadmin");
//                }
//            }
//        }
//
//        GenericJumpCmd genericJumpCmd = new GenericJumpCmd(task.getId(), endTask.getId(), params);
//        // camunda-TODO
//        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
//        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
//        executor.execute(genericJumpCmd);
////        managementService.executeCommand(genericJumpCmd);
//
//
//        if (StringUtils.isNotBlank(targetTaskAssignee)) {
//            List<Task> list = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
//            if (CollectionUtils.isNotEmpty(list)) {
//                for (Task task1 : list) {
//                    taskService.setAssignee(task1.getId(), targetTaskAssignee);
//                }
//            }
//            logger.warn("-------- mocktest- -------- 流程实例【" + processInstanceId + "】跨步调试跳转至目标节点【" + endTask.getId() + " - " + endTask.getName() + "】,并由 【" + targetTaskAssignee + "】审批");
//        } else {
//            logger.warn("-------- mocktest- -------- 流程实例【" + processInstanceId + "】跨步调试跳转至目标节点【" + endTask.getId() + " - " + endTask.getName() + "】");
//        }
//
//    }
//
//    // camunda-TODO
//    private UserTask findEndTask(BpmnModelInstance bpmnModel, FlowElement flowElement, Collection<SequenceFlow> incomingFlows) {
//        if (flowElement instanceof UserTask) {
//            UserTask userTask = (UserTask) flowElement;
//            return userTask;
//        }
//
//        for (SequenceFlow incomingFlow : incomingFlows) {
//            // camunda-TODO
//            String sourceRef = incomingFlow.getSource().getId();
//            FlowElement next = bpmnModel.getModelElementById(sourceRef);
//            return findEndTask(bpmnModel, next, ((FlowNode) next).getIncoming());
//        }
//        return null;
//    }
//
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        act = applicationContext;
//    }
//}
