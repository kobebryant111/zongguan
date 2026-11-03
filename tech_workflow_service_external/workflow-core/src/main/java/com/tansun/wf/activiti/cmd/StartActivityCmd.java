package com.tansun.wf.activiti.cmd;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.runtime.AtomicOperation;
public class StartActivityCmd implements Command<java.lang.Void>
{
	private ActivityImpl _activity;

	private String _executionId;

	public StartActivityCmd(String executionId, ActivityImpl activity)
	{
		_activity = activity;
		_executionId = executionId;
	}

	@Override
	public Void execute(CommandContext commandContext)
	{
		//创建新任务

		ExecutionEntity execution = commandContext.getExecutionManager().findExecutionById(_executionId);
		execution.setActivity(_activity);

		execution.performOperation(AtomicOperation.ACTIVITY_START);
		return null;
	}
}