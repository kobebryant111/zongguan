package com.tansun.wf.activiti.cmd;

import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionManager;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.runtime.operation.PvmAtomicOperation;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 通用跳转类,支持五种节点间的跳转
 * 1.单节点
 * 2.并行任务中的单节点
 * 3.并行开始网关
 * 4.会签并行
 * 5.会签串行
 * 6.其他节点
 *
 * 跳转场景如下：
 * *********************************************************************************
 *  序号     源节点                目标节点                              是否需要支持
 * **********************************************************************************
 *  1        单节点                单节点                                  是
 *  2        单节点                并行任务中的单节点                      是
 *  3        单节点                并行开始网关                            是
 *  4        单节点                会签并行                                是
 *  5        单节点                会签串行                                是
 *  6        并行任务中的单节点	   单节点                                  是
 *  7        并行任务中的单节点	   并行任务中的单节点，在同一个并行内      是
 *                                 且在同一个条分支线上
 *  9        并行任务中的单节点	   并行任务中的单节点，在同一个并行内	   否
 *                                 且在不同分支线上
 *  8        并行任务中的单节点	   并行任务中的单节点，在不同并行内        否
 *  10        并行任务中的单节点	   并行开始网关	                       是
 *  11       并行任务中的单节点	   会签并行	                               是
 *  12       并行任务中的单节点	   会签串行	                               是
 *  13       会签并行              单节点                                  是
 *  14       会签并行              并行任务中的单节点                      是
 *  15       会签并行              并行开始网关	                           是
 *  16       会签并行              会签并行	                               是
 *  17       会签并行              会签串行	                               是
 *  18       会签串行              单节点	                               是
 *  19       会签串行              并行任务中的单节点	                   是
 *  20       会签串行              并行开始网关	                           是
 *  21       会签串行              会签并行	                               是
 *  22       会签串行              会签串行	                               是
 * ps: 不支持多次并行任务嵌套
 * 跳转方向 ： sourceTaskId  --> targetActivityId
 */
public class GenericJumpCmd implements Command<Void> {

    /**
     * 节点类型
     */
    private final String SINGLE_NODE = "singleNode";
    private final String PARALLEL_SINGLE_NODE = "parallelSingleNode";
    private final String PARALLEL_START_GATEWAY = "parallelStartGateway";
    private final String MULTI_PARALLEL = "multiParallel";
    private final String MULTI_SEQUENTIAL = "multiSequential";
    private final String OTHER = "other";

    /**
     * 源节点任务id
     */
    private String sourceTaskId;
    private TaskEntity sourceTask;
    private ActivityImpl sourceActivity;
    private String sourceNodeType;

    /**
     * 目标节点任务id
     */
    private String targetActivityId;
    private ActivityImpl targetActivity;
    private String targetNodeType;

    /**
     * 流程变量
     */
    private Map<String, Object> paramvar;

    private ProcessDefinitionEntity processDefinition;

    private CommandContext commandContext;

    /**
     * map<并行开始网关id,并行网关内的任务id>
     */
    private HashMap<String, Set> parallelGatewayMapTaskNodeIds;


    public GenericJumpCmd(String sourceTaskId, String targetActivityId, Map<String, Object> paramvar) {
        this.sourceTaskId = sourceTaskId;
        this.targetActivityId = targetActivityId;
        this.paramvar = paramvar;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        this.commandContext = commandContext;
        sourceTask = commandContext.getTaskManager().findTaskById(sourceTaskId);

        processDefinition = commandContext.getProcessEngineConfiguration()
                .getDeploymentCache()
                .findDeployedProcessDefinitionById(sourceTask.getProcessDefinitionId());
        sourceActivity = processDefinition.findActivity(sourceTask.getTaskDefinitionKey());
        targetActivity = processDefinition.findActivity(targetActivityId);

        this.getParallelGatewayMapTaskNodeIds();

        sourceNodeType = getNodeType(sourceActivity);
        targetNodeType = getNodeType(targetActivity);



        //1.相同节点内不能跳转
//        if(sourceActivity.getId().equals(targetActivityId)){
//            throw new RuntimeException("相同的节点不能操作！");
//        }

        //2.未识别的节点类型不能跳转
        if(OTHER.equals(sourceNodeType) || OTHER.equals(targetNodeType)){
            throw new RuntimeException("未识别的节点类型不能跳转！");
        }

        //3.源节点为并行开始网关,不能跳转
        if(PARALLEL_START_GATEWAY.equals(sourceNodeType)){
            throw new RuntimeException("源节点为并行开始网关,不能跳转！");
        }

        //4.不支持不同的并行任务间的跳转和不同分支内任务跳转
        if(isParallelGateWayJump()){
            boolean allInOneParallel = false;
            for (String key : this.parallelGatewayMapTaskNodeIds.keySet()) {
                Set set = parallelGatewayMapTaskNodeIds.get(key);
                if( set.contains(sourceActivity.getId()) && set.contains(targetActivity.getId())){
                    allInOneParallel = true;
                    break;
                }
            }
            if (allInOneParallel) {
                //TODO 校验同一个并行网关内的同一个分支才能跳转
            }else {
                throw new RuntimeException("不同并行网关间的任务，不能跳转！");
            }

        }

        //5.执行跳转逻辑
        doJump();

        return null;
    }

    /**
     * 执行跳转逻辑
     * 1.处理执行实例
     * 2.删除当前相关的任务
     * 3.删除当前相关的执行实例
     * 4.处理目标节点
     * 5.推动流程实例继续向下运转
     */
    private void doJump() {
        //1.处理执行实例
        ExecutionEntity processInstanceEntity = commandContext.getExecutionManager().findExecutionById(sourceTask.getProcessInstanceId());
        processInstanceEntity.setVariables(paramvar);
        processInstanceEntity.setExecutions(null);
        processInstanceEntity.setEventSource(sourceActivity);
        processInstanceEntity.setActivity(sourceActivity);

        //2.删除当前相关的任务
        List<TaskEntity> tobeDeleteTask = getTobeDeleteTask();
        if(CollectionUtils.isEmpty(tobeDeleteTask)){
            throw new RuntimeException("跳转失败，流程没有可执行的任务！");
        }
        deleteCurrentTask(processInstanceEntity, tobeDeleteTask);

        //3.删除相关流程实例
        deleteCurrentExecution(processInstanceEntity);

        //4.获取目标节点执行实例
        ExecutionEntity targetNodeExecution = createTargetNodeExecution(processInstanceEntity);

        //5.推动流程实例继续向下运转
        if (targetNodeExecution != null){
            targetNodeExecution.executeActivity(targetActivity);
        }

    }

    private void deleteCurrentExecution(ExecutionEntity processInstanceEntity) {
        if (sourceNodeType.equals(PARALLEL_SINGLE_NODE) && targetNodeType.equals(PARALLEL_SINGLE_NODE)){
            return;
        }

        ExecutionManager executionManager = commandContext.getExecutionManager();
        List<ExecutionEntity> list = executionManager.findChildExecutionsByParentExecutionId(processInstanceEntity.getId());
        for(ExecutionEntity executionEntity2 : list){
        	List<ExecutionEntity> parent  = executionManager.findChildExecutionsByParentExecutionId(executionEntity2.getId());
        	for(ExecutionEntity executionEntity3 : parent){
        		executionEntity3.remove();
        		// camunda-TODO
        		//Context.getCommandContext().getHistoricActivityInstanceManager()..recordActivityEnd(executionEntity3);
        		Context.getCommandInvocationContext().performOperation(PvmAtomicOperation.ACTIVITY_END, executionEntity3);
        	}
        	executionEntity2.remove();
        	// camunda-TODO
//        	Context.getCommandContext().getHistoryManager().recordActivityEnd(executionEntity2);
        	Context.getCommandInvocationContext().performOperation(PvmAtomicOperation.ACTIVITY_END, executionEntity2);
        }

        // 退回到多个人有问题，注释掉
        // commandContext.getIdentityLinkEntityManager().deleteIdentityLinksByProcInstance(processInstanceEntity.getId());
    }

    private void deleteCurrentTask(ExecutionEntity executionEntity, List<TaskEntity> tobeDeleteTask) {
        for (TaskEntity taskEntity : tobeDeleteTask) {
            // 触发任务监听器
            taskEntity.fireEvent("complete");
            //触发任务结束事件
            // camunda-TODO
//            Activiti taskCompleted = new ActivitiEntityEventImpl(taskEntity, ActivitiEventType.TASK_COMPLETED);
//            commandContext.getEventDispatcher().dispatchEvent(taskCompleted);
            // 删除任务的原因
            if (sourceTaskId.equals(taskEntity.getId())) {
                Context.getCommandContext().getTaskManager()
                        .deleteTask(taskEntity, "TansunBackCompleted", false,false);
            } else {
                Context.getCommandContext().getTaskManager()
                        .deleteTask(taskEntity, "TansunBackDelete", false,false);
            }
       
            //记录节点结束时间
            // camunda-TODO
//            Context.getCommandContext().getHistoryManager().recordActivityEnd(executionEntity);
            Context.getCommandInvocationContext().performOperation(PvmAtomicOperation.ACTIVITY_END, executionEntity);
        }
    }

    private ExecutionEntity createTargetNodeExecution(ExecutionEntity processInstanceEntity) {
        ExecutionEntity targetChildExecution = null;
        if (!sourceNodeType.equals(PARALLEL_SINGLE_NODE) && targetNodeType.equals(PARALLEL_SINGLE_NODE)) {
            //创建实例
            String startParallelId = this.findStartParallelId(this.targetActivity, null);
            String endParallelId = findEndParallelId(this.targetActivity, null);
            String incomingId = this.findIncomingId(this.targetActivity, null);
            List<PvmTransition> outgoingTransitions = processDefinition.findActivity(startParallelId).getOutgoingTransitions();

            for (PvmTransition outgoingTransition : outgoingTransitions) {
                ExecutionEntity childExecution = processInstanceEntity.createExecution();
                if (outgoingTransition.getId().equals(incomingId)) {
                    childExecution.setActive(true);
                    targetChildExecution = childExecution;

                } else {
                    childExecution.setActive(false);
                    childExecution.setCachedEntityState(1);
                    ActivityImpl activity = processInstanceEntity.getProcessDefinition().findActivity(endParallelId);
                    childExecution.setActivity(activity);
                }
                childExecution.setScope(false);
                childExecution.setConcurrent(true);

            }
            targetChildExecution.setActivity(this.targetActivity);
            // camunda-TODO 该方法什么意思
            targetChildExecution.setTransitionsToTake(this.targetActivity.getIncomingTransitions());
            targetChildExecution = null;
        } else if (sourceNodeType.equals(PARALLEL_SINGLE_NODE) && targetNodeType.equals(PARALLEL_SINGLE_NODE)){
            ExecutionEntity childrenExecution = commandContext.getExecutionManager().findExecutionById(sourceTask.getExecutionId());
            childrenExecution.setActivity(this.targetActivity);
            targetChildExecution =  childrenExecution;
        } else if (targetNodeType.equals(MULTI_SEQUENTIAL) || targetNodeType.equals(MULTI_PARALLEL)) {
            ExecutionEntity childExecution = processInstanceEntity.createExecution();
            childExecution.setActive(true);
            targetChildExecution = childExecution;
        } else {
            targetChildExecution = processInstanceEntity;
        }
        return targetChildExecution;

    }

    /**
     * 获取可删除的任务
     */
    private List<TaskEntity> getTobeDeleteTask() {
        if(isParallelGateWayJump()){
            return Context.getCommandContext()
                    .getTaskManager().findTasksByExecutionId(sourceTask.getExecutionId());
        } else {
            return Context.getCommandContext()
                    .getTaskManager().findTasksByProcessInstanceId(sourceTask.getProcessInstanceId());
        }
    }

    private boolean isParallelGateWayJump() {
        return PARALLEL_SINGLE_NODE.equals(sourceNodeType) && PARALLEL_SINGLE_NODE.equals(targetNodeType);
    }

    private String findIncomingId(ActivityImpl desActivity, String incomingId){
        List<PvmTransition> incomingTransitions = desActivity.getIncomingTransitions();
        for (PvmTransition incomingTransition : incomingTransitions) {
            PvmActivity source = incomingTransition.getSource();
            if (source.getProperty("type").equals("parallelGateway") && source.getOutgoingTransitions().size() >= source.getIncomingTransitions().size()){
                incomingId = incomingTransition.getId();
                break;
            }else{
                ActivityImpl nextActivity = processDefinition.findActivity(source.getId());
                return findIncomingId(nextActivity, incomingId);
            }
        }
        return incomingId;
    }

    private String findEndParallelId(ActivityImpl desActivity,String endParallelId){
        List<PvmTransition> outgoingTransitions = desActivity.getOutgoingTransitions();
        for (PvmTransition outgoingTransition : outgoingTransitions) {
            PvmActivity destination = outgoingTransition.getDestination();
            if (destination.getProperty("type").equals("parallelGateway") && destination.getOutgoingTransitions().size() <= destination.getIncomingTransitions().size()){
                endParallelId = destination.getId();
                break;
            }else{
                ActivityImpl nextActivity = processDefinition.findActivity(destination.getId());
                return findEndParallelId(nextActivity, endParallelId);
            }
        }
        return endParallelId;
    }

    private String findStartParallelId(ActivityImpl desActivity, String startParallelId){
        List<PvmTransition> incomingTransitions = desActivity.getIncomingTransitions();
        for (PvmTransition incomingTransition : incomingTransitions) {
            PvmActivity source = incomingTransition.getSource();
            if (source.getProperty("type").equals("parallelGateway") && source.getOutgoingTransitions().size() >= source.getIncomingTransitions().size()){
                startParallelId = source.getId();
                break;
            }else{
                ActivityImpl nextActivity = processDefinition.findActivity(source.getId());
                return findStartParallelId(nextActivity, startParallelId);
            }
        }
        return startParallelId;
    }

    /**
     * 获取节点类型
     * @param activity
     * @return
     */
    private String getNodeType(ActivityImpl activity) {
        Object type = activity.getProperty("type");

        if(!type.equals("userTask") && !type.equals("parallelGateway")){
            return OTHER;
        }

        if (type.equals("parallelGateway") && activity.getIncomingTransitions().size() <= activity.getOutgoingTransitions().size()){
            return PARALLEL_START_GATEWAY;
        }

        Set<String> taskIds = new HashSet<>();
        for (String key : this.parallelGatewayMapTaskNodeIds.keySet()) {
            taskIds.addAll(parallelGatewayMapTaskNodeIds.get(key));
        }


        Object multiInstance = activity.getProperty("multiInstance");
        if (multiInstance != null) {
            if(taskIds.contains(activity)){
                return OTHER;
            }else{
                return "sequential".equals(multiInstance) ? MULTI_SEQUENTIAL : MULTI_PARALLEL;
            }
        } else {
            if(taskIds.contains(activity.getId())){
                return PARALLEL_SINGLE_NODE;
            }else{
                return SINGLE_NODE;
            }
        }
    }

    /**
     * 获取并行任务相关信息
     */
    private void getParallelGatewayMapTaskNodeIds(){
        List<ActivityImpl> activities = processDefinition.getActivities();
        //map<并行开始网关id,并行网关内的任务id>
        this.parallelGatewayMapTaskNodeIds = new HashMap<>();
        for (ActivityImpl activity : activities) {
            if (activity.getProperty("type").equals("parallelGateway") && activity.getOutgoingTransitions().size() >= activity.getIncomingTransitions().size()){
                HashSet taskNodeIds = new HashSet();
                getAllTaskNodeIdInParallelGateway(activity.getId(), taskNodeIds);
                parallelGatewayMapTaskNodeIds.put(activity.getId(),taskNodeIds);
            }
        }

    }

    private void getAllTaskNodeIdInParallelGateway(String activityId, Set taskNodeIds) {
        ActivityImpl activity = processDefinition.findActivity(activityId);
        List<PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
        for (PvmTransition outgoingTransition : outgoingTransitions) {
            PvmActivity destination = outgoingTransition.getDestination();
            if (taskNodeIds.contains(destination.getId())) {
                continue;
            } else if (destination.getProperty("type").equals("parallelGateway")) {
                if (destination.getOutgoingTransitions().size() <= destination.getIncomingTransitions().size()){
                    continue;
                }else{
                    //TODO 并行嵌套并行处理
                }
            } else if (destination.getProperty("type").equals("userTask")) {
                taskNodeIds.add(destination.getId());
            }
                getAllTaskNodeIdInParallelGateway(destination.getId(), taskNodeIds);

        }
    }

}
