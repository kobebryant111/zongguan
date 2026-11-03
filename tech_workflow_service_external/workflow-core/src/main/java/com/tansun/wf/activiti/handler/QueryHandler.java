package com.tansun.wf.activiti.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.query.Query;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;

/**
 * 查询处理类
 *
 */
@Component
public class QueryHandler {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 流程分页通用查询
	 * 
	 * @param processInfo
	 * @param query
	 * @param queryResultHandler
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ProcessBean commonPageQuery(ProcessInfo processInfo, Query query, QueryResultHandler queryResultHandler) {
		ProcessBean processBean = new ProcessBean();
		if (query == null) {
			return new ProcessBean(WorkflowConstants.FAILED, "activiti Query is null");
		}
		try {
			int pageSize = StringUtils.isNumericSpace(processInfo.getPageSize()) ? Integer.parseInt(processInfo.getPageSize()) : 10;
			int currentPage = StringUtils.isNumericSpace(processInfo.getCurrentPage()) ? Integer.parseInt(processInfo.getCurrentPage()) : 1;
			int firstResult = pageSize * (currentPage - 1);

			List<Object> list = null;
			if (WorkflowConstants.NO_PAGE == pageSize) {
				list = query.list();
			} else {
				list = query.listPage(firstResult, pageSize);
			}
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (Object model : list) {
				Map<String, Object> item = queryResultHandler.handlerRecord(model);
				resultList.add(item);
			}
			processBean.setResultList(resultList);
			processBean.setCurrentPageIndex(currentPage + "");
			processBean.setTotal(query.count() + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			processBean.setRetCode(WorkflowConstants.FAILED);
		}
		processBean.setRetCode(WorkflowConstants.SUCCEED);
		return processBean;
	}
	
	/**
	 * 任务通用分页查询
	 * @param taskInfo
	 * @param query
	 * @param queryResultHandler
	 * @return
	 */
	public TaskBean commonPageQuery(TaskInfo taskInfo,Query query, QueryResultHandler queryResultHandler){
		TaskBean bean = new TaskBean();
		if (query == null) {
			return new TaskBean(WorkflowConstants.FAILED, "activiti Query is null");
		}
		try {
			int pageSize = StringUtils.isNumericSpace(taskInfo.getPageSize()) ? Integer.parseInt(taskInfo.getPageSize()) : 10;
			int currentPage = StringUtils.isNumericSpace(taskInfo.getCurrentPage()) ? Integer.parseInt(taskInfo.getCurrentPage()) : 1;
			int firstResult = pageSize * (currentPage - 1);

			List<Object> list = null;
			if (WorkflowConstants.NO_PAGE == pageSize) {
				list = query.list();
			} else {
				list = query.listPage(firstResult, pageSize);
			}
			List<Map> resultList = new ArrayList<Map>();
			for (Object model : list) {
				Map<String, Object> item = queryResultHandler.handlerRecord(model);
				resultList.add(item);
			}
			bean.setTaskList(resultList);
			bean.setCurrentPageIndex(currentPage + "");
			bean.setTotal(query.count() + "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			bean.setRetCode(WorkflowConstants.FAILED);
		}
		bean.setRetCode(WorkflowConstants.SUCCEED);
		return bean;
	}

}
