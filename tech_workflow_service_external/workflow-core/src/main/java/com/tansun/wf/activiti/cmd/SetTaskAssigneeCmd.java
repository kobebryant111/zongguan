package com.tansun.wf.activiti.cmd;

import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SetTaskAssigneeCmd implements Command<java.lang.Void> {
    private String _userId;

    private String _executionId;

    public SetTaskAssigneeCmd(String executionId, String userId) {
        _userId = userId;
        _executionId = executionId;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        //创建新任务

        List<TaskEntity> task = commandContext.getTaskManager().findTasksByExecutionId(_executionId);
        if (StringUtils.isNotBlank(_userId)) {
            task.get(0).setAssignee(_userId);
        }
        return null;
    }
}