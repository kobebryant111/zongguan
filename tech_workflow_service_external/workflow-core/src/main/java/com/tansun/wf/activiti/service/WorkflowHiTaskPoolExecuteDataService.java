package com.tansun.wf.activiti.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.manager.WfHiTaskPoolExecuteDataManager;
import com.tansun.wf.activiti.model.HiTaskPoolExecuteDataEntity;
import com.tansun.wf.activiti.query.WfHiTaskPoolExecuteDataQuery;
import com.tansun.wf.activiti.util.DateUtil;

/**
 * 任务池操作历史记录服务类
 *
 */
@Service("workflowHiTaskPoolExecuteDataService")
@SuppressWarnings({"unused"})
@Transactional
public class WorkflowHiTaskPoolExecuteDataService extends AbstractManager {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;
	
	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private ProcessEngine processEngine;
	
	/**
	 * 根据条件获取任务池任务分配及释放历史记录
	 * @param taskId
	 * @param sysId
	 * @param userId
	 * @param isAllot
	 * @param tenantId
	 * @return
	 */
    public List<HiTaskPoolExecuteDataEntity> getHiTaskPoolExecuteData(String taskId, String sysId, 
    			String userId, String isAllot, String tenantId) {
    	WfHiTaskPoolExecuteDataQuery  poolExecuteQuery =	new WfHiTaskPoolExecuteDataQuery();
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
    	poolExecuteQuery.setCommandExecutor(executor);
    	List<Map<String, Object>> list = poolExecuteQuery.taskId(taskId)
    			.sysId(sysId).userId(userId).isAllot(isAllot).tenantId(tenantId).list();
    	List<HiTaskPoolExecuteDataEntity>  resList  = new  ArrayList<>();
    	HiTaskPoolExecuteDataEntity  poolExecuteEntity = null;
    	for (Map<String,Object> map :list) {
    		poolExecuteEntity  = new  HiTaskPoolExecuteDataEntity();
    		poolExecuteEntity.setId((String) map.get("ID"));
    		poolExecuteEntity.setIsAllot((String) map.get("isAllot"));
    		poolExecuteEntity.setSysId((String) map.get("sysId"));
    		poolExecuteEntity.setTaskId((String) map.get("taskId"));
    		poolExecuteEntity.setTenantId((String) map.get("tenantId"));
    		poolExecuteEntity.setUserId((String) map.get("userId"));
    		poolExecuteEntity.setCreateDate(DateUtil.toDate( map.get("createTime")));
    		resList.add(poolExecuteEntity);    		
    	}
    	return  resList;
    }
    
    public void insertHiTaskPoolExecute(final HiTaskPoolExecuteDataEntity hiTaskPoolExecuteDataEntity){
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				WfHiTaskPoolExecuteDataManager wfHiTaskPoolExecuteDataManager = commandContext.getSession(WfHiTaskPoolExecuteDataManager.class);
				wfHiTaskPoolExecuteDataManager.insertHiTaskPoolExecute(hiTaskPoolExecuteDataEntity);
				return null;
			}
		});
    }
}
