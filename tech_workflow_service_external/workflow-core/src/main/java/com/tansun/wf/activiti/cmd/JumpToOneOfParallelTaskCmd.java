package com.tansun.wf.activiti.cmd;

import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionManager;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 回退至并行分支内某个节点（只支持一层）
 */
public class JumpToOneOfParallelTaskCmd implements Command<Void> {
    protected String executionId;//执行实例id
    protected String parentId;//流程实例id
    protected ActivityImpl desActivity;//目标节点
    protected Map<String, Object> paramvar;//变量
    protected ActivityImpl currentActivity;//当前的节点

    @Override
    public Void execute(CommandContext commandContext) {
        // 获取当前流程的executionId，因为常规的流程执行id与流程实例id是相等的。
        String id = parentId;
        if (!isSingleProcess()) {
            id = executionId;
        }


        ExecutionManager executionEntityManager = Context
                .getCommandContext().getExecutionManager();
        ExecutionEntity executionEntity = executionEntityManager
                .findExecutionById(id);
        executionEntity.setVariables(paramvar);
        executionEntity.setExecutions(null);
        executionEntity.setEventSource(this.currentActivity);
        executionEntity.setActivity(this.currentActivity);
        // 根据executionId 获取Task
        Iterator<TaskEntity> localIterator = null;
        if (isSingleProcess()) {
            localIterator = Context.getCommandContext()
                    .getTaskManager().findTasksByProcessInstanceId(id)
                    .iterator();
        } else {
            localIterator = Context.getCommandContext()
                    .getTaskManager().findTasksByExecutionId(id)
                    .iterator();
        }


        while (localIterator.hasNext()) {
            TaskEntity taskEntity = (TaskEntity) localIterator.next();
            // 触发任务监听器
            taskEntity.fireEvent("complete");
            //触发任务结束事件
            // camunda-TODO
//            ActivitiEntityEventImpl taskCompleted = new ActivitiEntityEventImpl(taskEntity, ActivitiEventType.TASK_COMPLETED);
//            commandContext.getEventDispatcher().dispatchEvent(taskCompleted);
            // 删除任务的原因
            Context.getCommandContext().getTaskManager()
                    .deleteTask(taskEntity, "TansunBackCompleted", false,false);
            //记录节点结束时间
            // camunda-TODO
//            Context.getCommandContext().getHistoryManager().recordActivityEnd(executionEntity);
        }

        //创建实例
        String startParallelId = this.findStartParallelId(executionEntity,this.desActivity, null);
        String endParallelId = findEndParallelId(executionEntity,this.desActivity,null);
        String incomingId = this.findIncomingId(executionEntity,this.desActivity, null);
        List<PvmTransition> outgoingTransitions = executionEntity.getProcessDefinition().findActivity(startParallelId).getOutgoingTransitions();
        ExecutionEntity targetChildExecution = null;
        for (PvmTransition outgoingTransition : outgoingTransitions) {
            ExecutionEntity childExecution = executionEntity.createExecution();
            if (outgoingTransition.getId().equals(incomingId)) {
                childExecution.setActive(true);
                targetChildExecution = childExecution;

            } else {
                childExecution.setActive(false);
                childExecution.setCachedEntityState(1);
                ActivityImpl activity = executionEntity.getProcessDefinition().findActivity(endParallelId);
                childExecution.setActivity(activity);
            }
            childExecution.setScope(false);
            childExecution.setConcurrent(true);

        }
        targetChildExecution.setActivity(this.desActivity);
        // camunda-TODO
//        targetChildExecution.take(this.desActivity.getIncomingTransitions().get(0));
        targetChildExecution.setTransitionsToTake(this.desActivity.getIncomingTransitions());

        return null;
    }

    private String findIncomingId(ExecutionEntity executionEntity, ActivityImpl desActivity, String incomingId){

        List<PvmTransition> incomingTransitions = desActivity.getIncomingTransitions();
        for (PvmTransition incomingTransition : incomingTransitions) {
            PvmActivity source = incomingTransition.getSource();
            if (source.getProperty("type").equals("parallelGateway") && source.getOutgoingTransitions().size() >= source.getIncomingTransitions().size()){
                incomingId = incomingTransition.getId();
                break;
            }else{
                ActivityImpl nextActivity = executionEntity.getProcessDefinition().findActivity(source.getId());
                return findIncomingId(executionEntity,nextActivity, incomingId);
            }
        }
        return incomingId;
    }

    private String findEndParallelId(ExecutionEntity executionEntity,ActivityImpl desActivity,String endParallelId){

        List<PvmTransition> outgoingTransitions = desActivity.getOutgoingTransitions();
        for (PvmTransition outgoingTransition : outgoingTransitions) {
            PvmActivity destination = outgoingTransition.getDestination();
            if (destination.getProperty("type").equals("parallelGateway") && destination.getOutgoingTransitions().size() <= destination.getIncomingTransitions().size()){
                endParallelId = destination.getId();
                break;
            }else{
                ActivityImpl nextActivity = executionEntity.getProcessDefinition().findActivity(destination.getId());
                return findEndParallelId(executionEntity,nextActivity, endParallelId);
            }
        }
        return endParallelId;
    }

    private String findStartParallelId(ExecutionEntity executionEntity, ActivityImpl desActivity, String startParallelId){

        List<PvmTransition> incomingTransitions = desActivity.getIncomingTransitions();
        for (PvmTransition incomingTransition : incomingTransitions) {
            PvmActivity source = incomingTransition.getSource();
            if (source.getProperty("type").equals("parallelGateway") && source.getOutgoingTransitions().size() >= source.getIncomingTransitions().size()){
                startParallelId = source.getId();
                break;
            }else{
                ActivityImpl nextActivity = executionEntity.getProcessDefinition().findActivity(source.getId());
                return findStartParallelId(executionEntity,nextActivity, startParallelId);
            }
        }
        return startParallelId;
    }

    public JumpToOneOfParallelTaskCmd(String executionId, String parentId,
                             ActivityImpl desActivity, Map<String, Object> paramvar,
                             ActivityImpl currentActivity) {
        this.executionId = executionId;
        this.desActivity = desActivity;
        this.paramvar = paramvar;
        this.currentActivity = currentActivity;
        this.parentId = parentId;
    }

    private boolean isSingleProcess() {
        return parentId.equals(executionId);
    }
}
