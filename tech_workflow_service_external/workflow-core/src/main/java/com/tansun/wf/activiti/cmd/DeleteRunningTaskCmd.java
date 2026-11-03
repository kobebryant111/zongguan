package com.tansun.wf.activiti.cmd;

import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;

public class DeleteRunningTaskCmd implements Command<java.lang.Void> {
    private TaskEntity _currentTaskEntity;

    public DeleteRunningTaskCmd(TaskEntity currentTaskEntity) {
        _currentTaskEntity = currentTaskEntity;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        //删除当前的任务
        //不能删除当前正在执行的任务，所以要先清除掉关联
        if (_currentTaskEntity != null) {
            Context.getCommandContext().getTaskManager()
                    .deleteTask(_currentTaskEntity, TaskEntity.DELETE_REASON_DELETED, false,false);
        }

        return null;
    }
}