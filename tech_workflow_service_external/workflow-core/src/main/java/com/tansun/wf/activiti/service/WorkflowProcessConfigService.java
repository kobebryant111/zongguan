package com.tansun.wf.activiti.service;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.model.ProcessConfig;
import com.tansun.wf.activiti.query.WfProcessConfigQuery;

/**
 * 流程分类服务类
 *
 */
@Service("workflowProcessConfigService")
@SuppressWarnings({"unused"})
public class WorkflowProcessConfigService extends AbstractManager {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;
	
	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;

	@Autowired
	private ManagementService managementService;


	/**
	 * 通过code获取流程分类
	 */
    public ProcessConfig getProcessConfigByProcDefIdAndActId(String procDefId, String actId, String processStatus) {
//    	if(classifyCode == null){ //如果为空默认查询不到
//    		classifyCode = "null";
//		}
		return new WfProcessConfigQuery().procDefId(procDefId).actId(actId).processStatus(processStatus).singleResult();
    }
    
    /**
     * 通过流程定义id 流程配置状态    获取流程处理类
     */
    public ProcessConfig getProcessClassByProcDefId(String procDefId, String processStatus) {
		return new WfProcessConfigQuery().procDefId(procDefId).processStatus(processStatus).singleResult();
    }
}
