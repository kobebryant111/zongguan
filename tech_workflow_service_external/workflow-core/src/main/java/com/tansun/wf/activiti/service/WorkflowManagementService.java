package com.tansun.wf.activiti.service;

import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.workflow.model.TaskBean;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.JobEntity;
import org.camunda.bpm.engine.runtime.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 引擎服务类
 *
 */
@Service("workflowManagementService")
@Transactional
public class WorkflowManagementService {
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;
	
	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;
	
	
	@Autowired
	private ManagementService managementService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private ProcessEngine processEngine;
	
	/**
	 * 重试流程中出异常的serviceTask节点
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
	public TaskBean serviceTaskRetry(TaskBean taskBean){
		try {
			String processInstanceId = taskBean.getProcessInstanceId();
			String executionId = taskBean.getExecutionId();
			if(StringUtils.isBlank(processInstanceId) || StringUtils.isBlank(executionId)){
				return new TaskBean(FAILED,"重试失败,流程实例id,流程执行实例id为空!");
			}
			String activityId = taskBean.getActivityId();
			if(StringUtils.isBlank(activityId)){
				return new TaskBean(FAILED,"重试失败,当前节点id为空!");
			}

			Map variables = taskBean.getmPara();
			variables = variables == null ? new HashMap():variables;
			runtimeService.setVariables(executionId, variables);

			//查询 当前实例下type=messages,retries=0,异常失败的任务
			List<Job> jobs = managementService.createJobQuery().processInstanceId(processInstanceId).executionId(executionId).messages().withException().noRetriesLeft().list();
			if(jobs != null && !jobs.isEmpty()){
				for (Job job : jobs) {
					final String id = job.getId();
					// camunda-TODO
					ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
					CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
					executor.execute(new Command<Void>() {
						//				managementService.executeCommand(new Command<Void>() {
						@Override
						public Void execute(CommandContext commandContext) {
							JobEntity job = commandContext
									.getJobManager()
									.findJobById(id);
							job.setDuedate(new Date());
							if (job != null) {
								//重试加1
								job.setRetries(1);
								//超时十分钟后,认为该任务因为服务异常
								Date lockExpirationTime = job.getLockExpirationTime();
								if (lockExpirationTime != null) {
									long currentTime = System.currentTimeMillis();
									long lockTime = lockExpirationTime.getTime();
									long l = (currentTime - lockTime) / (60 * 1000); //分钟
									if (l >= 30) {
										job.setLockExpirationTime(null);
									}
								}
								if (job.getDuedate() == null) {
									job.setDuedate(new Date());
								}
								// camunda-TODO
//							if (commandContext.getEventDispatcher().isEnabled()) {
//								commandContext.getEventDispatcher().dispatchEvent(
//										ActivitiEventBuilder.createEntityEvent(ActivitiEventType.ENTITY_UPDATED, job));
//							}
							} else {
								// camunda-TODO
//							throw ActivitiObjectNotFoundException("No job found with id '" + id + "'.", Job.class);
							}
							return null;
						}
					});
				}
			}else{
				return new TaskBean(FAILED,"重试失败,{"+processInstanceId+"}实例没有可重试的任务!");
			}
			return new TaskBean(SUCCEED, "流程重试中，请稍后刷新查看重试结果");
		}catch (Exception e){
			return new TaskBean(FAILED, "重试失败!");
		}

	}



}
