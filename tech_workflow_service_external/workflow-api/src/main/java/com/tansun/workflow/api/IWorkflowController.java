package com.tansun.workflow.api;

import com.tansun.workflow.model.TaskInfo;

import java.util.Map;

/**
 * 工作流模板接口类
 */
public interface IWorkflowController {
	
	/**
	 * 启动流程前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeStartProcessTrans(Map<String, Object> map);
	
	/**
	 * 启动流程后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterStartProcessTrans(Map<String, Object> map);
	
	/**
	 * 提交任务前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeSubmitTaskTrans(Map<String, Object> map);
	
	/**
	 * 提交任务后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterSubmitTaskTrans(Map<String, Object> map);
	
	/**
	 * 回退上一节点前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeBackPreTaskTrans(Map<String, Object> map);
	
	/**
	 * 回退上一节点后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterBackPreTaskTrans(Map<String, Object> map);
	
	/**
	 * 取回前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeRebackTaskTrans(Map<String, Object> map);
	
	/**
	 * 取回后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterRebackTaskTrans(Map<String, Object> map);
	
	/**
	 * 流程挂起前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeSuspendProcessTrans(Map<String, Object> map);
	
	/**
	 * 流程挂起后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterSuspendProcessTrans(Map<String, Object> map);
	
	/**
	 * 流程激活前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeActivateProcessTrans(Map<String, Object> map);
	
	/**
	 * 流程激活后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterActivateProcessTrans(Map<String, Object> map);
	
	/**
	 * 转办前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeTransferTaskTrans(Map<String, Object> map);
	
	/**
	 * 转办后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterTransferTaskTrans(Map<String, Object> map);
	
	/**
	 * 回退第一节点前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeBackFirstTaskTrans(Map<String, Object> map);
	
	/**
	 * 回退第一节点后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterBackFirstTaskTrans(Map<String, Object> map);
	
	/**
	 * 回退至指定节点前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeBackTaskTrans(Map<String, Object> map);
	
	/**
	 * 回退至指定节点后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterBackTaskTrans(Map<String, Object> map);
	
	/**
	 * 废弃流程前
	 * @param map
	 * @return
	 */
	public Map<String, Object> beforeTerminateProcessTrans(Map<String, Object> map);
	
	/**
	 * 废弃流程后
	 * @param map
	 * @return
	 */
	public Map<String, Object> afterTerminateProcessTrans(Map<String, Object> map);

	/**
	 * 流程结束时
	 * @param map
	 * @return
	 */
	public void processCompletedTrans(Map<String, Object> map,String beanName);
	
	/**
	 * 推送app
	 * @param taskInfo
	 * @return
	 */
	public void createTaskEvent(TaskInfo taskInfo);

	/**
	 * 会签完成后调用
	 * @param map
	 */
	public void afterVotingCompleted(Map<String,Object> map);
}
