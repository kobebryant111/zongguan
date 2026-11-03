package com.tansun.wf.activiti.cmd;

import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricTaskInstanceEntity;

public class UpdateHiTaskReasonCmd implements Command<Void> {

    private String taskId;
    private String deleteReason;

	public UpdateHiTaskReasonCmd(String taskId, String deleteReason) {
		this.taskId = taskId;
		this.deleteReason = deleteReason;
	}
	@Override
	public Void execute(CommandContext commandContext) {
		HistoricTaskInstanceEntity historicTaskInstance = commandContext
				.getDbSqlSession().selectById(HistoricTaskInstanceEntity.class,taskId);
		if (historicTaskInstance != null) {
			historicTaskInstance.setDeleteReason(deleteReason);
		}
		return null;
	}
    
}