package com.tansun.wf.extend.manager;

import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskManager;

/**
 * @author jali
 * @create 2019-12-06 9:49
 */
public class MyTaskManager extends TaskManager {

    @Override
    public void deleteTask(TaskEntity task, String deleteReason, boolean cascade, boolean skipCustomListeners) {
        if (!task.isDeleted()) {
            task.setDeleted(true);

            CommandContext commandContext = Context.getCommandContext();
            String taskId = task.getId();

//            List<Task> subTasks = findTasksByParentTaskId(taskId);
//            for (Task subTask: subTasks) {
//                ((TaskEntity) subTask).delete(deleteReason, cascade, skipCustomListeners);
//            }

            task.deleteIdentityLinks();

            commandContext
                    .getVariableInstanceManager()
                    .deleteVariableInstanceByTask(task);

            if (cascade) {
                commandContext
                        .getHistoricTaskInstanceManager()
                        .deleteHistoricTaskInstanceById(taskId);
            } else {
                commandContext
                        .getHistoricTaskInstanceManager()
                        .markTaskInstanceEnded(taskId, deleteReason);
            }

            deleteAuthorizations(Resources.TASK, taskId);
            getDbEntityManager().delete(task);
        }
    }
}
