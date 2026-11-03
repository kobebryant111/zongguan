package com.tansun.wf.activiti.cmd;

import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionManager;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;

import java.util.Iterator;
import java.util.Map;

/**
 * 常规节点自由跳转
 */
public class CommonJumpTaskCmd implements Command<Void> {
    protected String executionId;//执行实例id
    protected String parentId;//流程实例id
    protected ActivityImpl desActivity;//目标节点
    protected Map<String, Object> paramvar;//变量
    protected ActivityImpl currentActivity;//当前的节点

    public Void execute(CommandContext commandContext) {
        // 获取当前流程的executionId，因为常规的流程执行id与流程实例id是相等的。
        String id = parentId;
        if(!isSingleProcess()){
            id = executionId;
        }


        ExecutionManager executionManager = Context.getCommandContext().getExecutionManager();
        ExecutionEntity executionEntity = executionManager
                .findExecutionById(id);
        executionEntity.setVariables(paramvar);
        executionEntity.setExecutions(null);
        executionEntity.setEventSource(this.currentActivity);
        executionEntity.setActivity(this.currentActivity);
        // 根据executionId 获取Task
        Iterator<TaskEntity> localIterator = null;
        if(isSingleProcess()){
            localIterator = Context.getCommandContext().getTaskManager().findTasksByProcessInstanceId(id)
                    .iterator();
        }else {
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
//            ActivitiEntityEventImpl taskCompleted = new ActivitiEntityEventImpl(taskEntity,ActivitiEventType.TASK_COMPLETED);
//            commandContext.getEventDispatcher().dispatchEvent(taskCompleted);
            // 删除任务的原因
            Context.getCommandContext().getTaskManager()
                    .deleteTask(taskEntity, "TansunBackCompleted", false,false);
            //记录节点结束时间
            // camunda-TODO
//            Context.getCommandContext().getHistoricTaskInstanceManager().recordActivityEnd(executionEntity);
        }
        // 推动流程实例继续向下运转
        executionEntity.executeActivity(this.desActivity);

        return null;
    }

    public CommonJumpTaskCmd(String executionId, String parentId,
                             ActivityImpl desActivity, Map<String, Object> paramvar,
                             ActivityImpl currentActivity) {
        this.executionId = executionId;
        this.desActivity = desActivity;
        this.paramvar = paramvar;
        this.currentActivity = currentActivity;
        this.parentId = parentId;
    }

    private boolean isSingleProcess(){
        return parentId.equals(executionId);
    }
}
