package com.tansun.wf.activiti.service;

import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.model.ClassifyEntity;
import com.tansun.wf.activiti.query.WfClassifyCompletedQuery;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.ManagementServiceImpl;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程分类服务类
 *
 */
@Service("WorkflowClassifyCompletedService")
@SuppressWarnings({"unused"})
public class WorkflowClassifyCompletedService extends AbstractManager {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;
	
	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;

	@Autowired
	private ManagementService managementService;


	/**
	 * 通过code获取流程分类
	 */
    public List<ClassifyEntity> getClassifyCompletedByUserId(String userId, String isTerminateProcess, String code, 
    		String isEnd, String singleHiTaskStatus, String createTimeAfter,String createTimeBefore) {
//    	if(classifyCode == null){ //如果为空默认查询不到
//    		classifyCode = "null";
//		}
    	if (org.apache.commons.lang3.StringUtils.isNotBlank(isTerminateProcess)) {
			isTerminateProcess = "强制终止";
		}
    	// camunda-TODO 这里不清楚是否会有问题，把构造器里的去掉，如果CommandContext是null，则需要在构造器里加入
//		return new WfClassifyCompletedQuery(managementService).userId(userId).isTerminateProcess(isTerminateProcess)
//				.code(code).isEnd(isEnd).singleHiTaskStatus(singleHiTaskStatus).createTimeAfter(createTimeAfter).createTimeBefore(createTimeBefore).list();
		WfClassifyCompletedQuery wfClassifyCompletedQuery = new WfClassifyCompletedQuery();
		if (managementService instanceof ManagementServiceImpl) {
			wfClassifyCompletedQuery.setCommandExecutor(((ManagementServiceImpl)managementService).getCommandExecutor());
		}
		return wfClassifyCompletedQuery.userId(userId).isTerminateProcess(isTerminateProcess)
				.code(code).isEnd(isEnd).singleHiTaskStatus(singleHiTaskStatus).createTimeAfter(createTimeAfter).createTimeBefore(createTimeBefore).list();
    }
    
    public Long getClassifyCompletedCountByCode(String userId, String isTerminateProcess, String code, String isEnd,
    		String singleHiTaskStatus) {
    	if (org.apache.commons.lang3.StringUtils.isNotBlank(isTerminateProcess)) {
			isTerminateProcess = "强制终止";
		}
		WfClassifyCompletedQuery wfClassifyCompletedQuery = new WfClassifyCompletedQuery();
		if (managementService instanceof  ManagementServiceImpl) {
			wfClassifyCompletedQuery.setCommandExecutor(((ManagementServiceImpl)managementService).getCommandExecutor());
		}
    	return wfClassifyCompletedQuery.userId(userId).isTerminateProcess(isTerminateProcess)
				.code(code).isEnd(isEnd).singleHiTaskStatus(singleHiTaskStatus).count();
    }
}
