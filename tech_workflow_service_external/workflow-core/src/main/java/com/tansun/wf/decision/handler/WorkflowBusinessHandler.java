package com.tansun.wf.decision.handler;


import com.tansun.workflow.model.ActExpandTracelog;
import org.camunda.bpm.engine.delegate.DelegateExecution;


/**
 * 工作流自动审批业务逻辑处理类
 *
 */
public interface WorkflowBusinessHandler {
	/**
	 * 处理业务逻辑代码
	 * @param execution 流程实例对象
	 * @param invokeType 调用类型
	 * @param log 需要记录日志入参对象
	 * @return 需要记录日志的出参值
	 * @throws Exception 
	 */
	public Object dealBusiness(DelegateExecution execution, String invokeType, ActExpandTracelog log) throws Exception;
}
