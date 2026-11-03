package com.tansun.wf.activiti.cmd;

import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionManager;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 多实例节点自由跳转
 */
public class MultiInstanceJumpTaskCmd implements Command<Void> {
    protected String executionId;//执行实例id
    protected String parentId;//流程实例id
    protected ActivityImpl desActivity;//目标节点
    protected Map<String, Object> paramvar;//变量
    protected ActivityImpl currentActivity;//当前的节点
    protected String taskId;//当前任务ID

    public Void execute(CommandContext commandContext) {
        // 获取当前流程的executionId，因为常规的流程执行id与流程实例id是相等的。
        ExecutionManager executionEntityManager = Context
                .getCommandContext().getExecutionManager();
        ExecutionEntity executionEntity = executionEntityManager
                .findExecutionById(executionId);
        String id = executionId;
        if(executionEntity.getParent()!=null){
        	executionEntity = executionEntity.getParent();
        	if(executionEntity.getParent()!=null){
        		executionEntity = executionEntity.getParent();
		    }
		    id = executionEntity.getId();
        }
        
        executionEntity.setVariables(paramvar);
        executionEntity.setExecutions(null);
        executionEntity.setEventSource(this.currentActivity);
        executionEntity.setActivity(this.currentActivity);
        // 根据executionId 获取Task
        Iterator<TaskEntity> localIterator = Context.getCommandContext()
                .getTaskManager().findTasksByProcessInstanceId(id)
                .iterator();

        while (localIterator.hasNext()) {
            TaskEntity taskEntity = (TaskEntity) localIterator.next();
            // 触发任务监听器
            taskEntity.fireEvent("complete");
            //触发任务结束事件
            // camunda-TODO
//            ActivitiEntityEventImpl taskCompleted = new ActivitiEntityEventImpl(taskEntity,ActivitiEventType.TASK_COMPLETED);
//            commandContext.getEventDispatcher().dispatchEvent(taskCompleted);
            // 删除任务的原因
            if(taskId.equals(taskEntity.getId())){
            	Context.getCommandContext().getTaskManager()
                	.deleteTask(taskEntity, "TansunBackCompleted", false,false);
            }else{
            	Context.getCommandContext().getTaskManager()
                	.deleteTask(taskEntity, "TansunBackDelete", false,false);
            }
            //记录节点结束时间
            // camunda-TODO
//            Context.getCommandContext().getHistoryManager().recordActivityEnd(executionEntity);

        }
        //删除分支执行实例数据
        List<ExecutionEntity> list = executionEntityManager.findChildExecutionsByParentExecutionId(parentId);
        for(ExecutionEntity executionEntity2 : list){
//        	ExecutionEntity findExecutionById = executionEntityManager.findExecutionById(executionEntity2.getId());
        	List<ExecutionEntity> parent  = executionEntityManager.findChildExecutionsByParentExecutionId(executionEntity2.getId());
        	for(ExecutionEntity executionEntity3 : parent){
        		executionEntity3.remove();
        		// camunda-TODO
//        		Context.getCommandContext().getHistoryManager().recordActivityEnd(executionEntity3);
        	}
        	executionEntity2.remove();
        	// camunda-TODO
//        	Context.getCommandContext().getHistoryManager().recordActivityEnd(executionEntity2);
        }
        // camunda-TODO
        commandContext.getIdentityLinkManager().delete(executionEntity);
        // 推动流程实例继续向下运转
        executionEntity.executeActivity(this.desActivity);

        return null;
    }

    public MultiInstanceJumpTaskCmd(String executionId, String parentId,
                             ActivityImpl desActivity, Map<String, Object> paramvar,
                             ActivityImpl currentActivity,String taskId) {
        this.executionId = executionId;
        this.desActivity = desActivity;
        this.paramvar = paramvar;
        this.currentActivity = currentActivity;
        this.parentId = parentId;
        this.taskId = taskId;
    }
}
