package com.tansun.wf.activiti.service;

import java.util.List;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.ManagementServiceImpl;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.model.ClassifyEntity;
import com.tansun.wf.activiti.query.WfClassifyPendQuery;

/**
 * 流程分类服务类
 *
 */
@Service("WorkflowClassifyPendService")
@SuppressWarnings({"unused"})
public class WorkflowClassifyPendService extends AbstractManager {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;
	
	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;

	@Autowired
	private ManagementService managementService;
	

	/**
	 * 通过code获取流程分类
	 */
    public List<ClassifyEntity> getClassifyPendByUserId(String userId, String code,String taskPoolStatus,String createTimeAfter,String createTimeBefore) {
//    	if(classifyCode == null){ //如果为空默认查询不到
//    		classifyCode = "null";
//		}
    	WfClassifyPendQuery  classfyPendQuery = new WfClassifyPendQuery();
    	if (managementService instanceof  ManagementServiceImpl) {
    		classfyPendQuery.setCommandExecutor(((ManagementServiceImpl)managementService).getCommandExecutor());
    	}
		return classfyPendQuery.userId(userId).code(code).taskPoolStatus(taskPoolStatus).createTimeAfter(createTimeAfter).createTimeBefore(createTimeBefore).list();
    }
	
}
