package com.tansun.wf.activiti.service;


import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.model.TaskExpandPlanEntity;
import com.tansun.wf.activiti.query.WfExpandPlanQuery;
import com.tansun.wf.platform.mapper.WfTaskExpandPlanMapper;
import com.tansun.wf.platform.model.WfTaskExpandPlanEntity;
import com.tansun.wf.platform.query.WfTaskExpandPlanQuery;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 流程待办扩展方案服务类
 *
 */
@Service("workflowTaskExpandPlanService")
@SuppressWarnings({"unused"})
public class WorkflowTaskExpandPlanService extends AbstractManager {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;
	
	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private WfTaskExpandPlanMapper wfTaskExpandPlanMapper;

	/**
	 * 根据流程定义Id获取扩展方案
	 */
	public List<WfTaskExpandPlanEntity> getExpandPlanByProcessDefinitionId(String processDefinitionId){
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String category = processDefinition.getCategory();
//		WfExpandPlanQuery wfExpandPlanQuery = new WfExpandPlanQuery().wfClassId(category);
//		if (this.managementService instanceof ManagementServiceImpl) {
//			wfExpandPlanQuery.setCommandExecutor(((ManagementServiceImpl)this.managementService).getCommandExecutor());
//		}
//		return wfExpandPlanQuery.list();
		WfTaskExpandPlanQuery wfTaskExpandPlanQuery = new WfTaskExpandPlanQuery();
		wfTaskExpandPlanQuery.setWfClassId(category);
		wfTaskExpandPlanQuery.setTenantId(processDefinition.getTenantId());
		return wfTaskExpandPlanMapper.findList(wfTaskExpandPlanQuery);
	}

	/**
	 * 根据流程信息获取扩展方案
	 * @return
	 */
	public ProcessBean getExpandPlanByProcessInfo(ProcessInfo info){
		String processDefineId = info.getProcessDefineId();
		String processInstanceId = info.getProcessInstanceId();
		if (StringUtils.isBlank(processDefineId)) {
			if (StringUtils.isBlank(processInstanceId)){
				return new ProcessBean(WorkflowConstants.FAILED, "查询失败，流程定义id或流程实例id为空");
			}
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if (processInstance != null){
				processDefineId = processInstance.getProcessDefinitionId();
			}else {
				return new ProcessBean(WorkflowConstants.FAILED, "查询失败，流程定义id或流程实例id为空");
			}
		}

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefineId).singleResult();
		if (processDefinition == null) {
			return new ProcessBean(WorkflowConstants.FAILED, "查询失败，流程定义id不存在");
		}
		String category = processDefinition.getCategory();

		// camunda-TODO
		List<TaskExpandPlanEntity> list = new WfExpandPlanQuery().wfClassId(category).list();

		List<Map<String, Object>> columnsList = new ArrayList<>();
		for (TaskExpandPlanEntity plan: list) {
			Map map = new HashMap();
			String fieldName = plan.getFieldName();
			String fieldKey = plan.getFieldKey();
			String fieldType = plan.getFieldType();
			String fieldUrl = plan.getFieldUrl();
			String fieldStatus = plan.getFieldStatus();
			map.put("field", fieldKey);
			map.put("name", fieldName);
			map.put("fieldType", fieldType);
			map.put("fieldUrl", fieldUrl);
			map.put("fieldStatus", fieldStatus);
			columnsList.add(map);
		}

		ProcessBean processBean = new ProcessBean(WorkflowConstants.SUCCEED, "查询成功");
		processBean.setResultList(columnsList);
		return processBean;
	}


	/**
	 * 根据流程分类code查询待办扩展方案
	 */
    public List<WfTaskExpandPlanEntity> getExpandPlanByClassifyCode(String classifyCode,String tenantId) {
		if(classifyCode == null){ //如果为空默认查询不到
    		return new ArrayList<>();
		}
		WfTaskExpandPlanQuery wfTaskExpandPlanQuery = new WfTaskExpandPlanQuery();
		wfTaskExpandPlanQuery.setWfClassCode(classifyCode);
		wfTaskExpandPlanQuery.setTenantId(tenantId);
		return wfTaskExpandPlanMapper.findList(wfTaskExpandPlanQuery);

//		return new WfExpandPlanQuery(this.managementService).wfClassCode(classifyCode).list();
    }
    
    /**
	 * 根据扩展字段fieldKey查询待办扩展方案
	 */
    public List<TaskExpandPlanEntity> getExpandPlanByFieldKey(String fieldKey) {
    	if(fieldKey == null){ //如果为空默认查询不到
    		fieldKey = "null";
		}
    
		return new WfExpandPlanQuery(this.managementService).fieldKey(fieldKey).list();
    }
    
    /**
	 * 根据扩展字段classify和codefieldKey查询待办扩展方案
	 */
    public List<TaskExpandPlanEntity> getExpandPlanByClassifyAndFieldKey(String classifyCode,String fieldKey) {
    	if(fieldKey == null){ //如果为空默认查询不到
    		fieldKey = "null";
		}
		return new WfExpandPlanQuery(this.managementService).wfClassCode(classifyCode).fieldKey(fieldKey).list();
    }

	/**
	 * 根据流程分类classifyCodes查询待办扩展方案
	 * @param classifyCodes 前台可以传多个分类或者不传
	 * @return
	 */
	public List<WfTaskExpandPlanEntity> getExpandPlanByClassifyCodes(final List<String> classifyCodes) {
		// camunda-TODO
		return wfTaskExpandPlanMapper.getExpandPlanByClassifyCodes(classifyCodes);
//		return managementService.executeCustomSql(new AbstractCustomSqlExecution<WfTaskExpandPlanMapper, List<TaskExpandPlanEntity>>(WfTaskExpandPlanMapper.class) {
//			@Override
//			public List<TaskExpandPlanEntity> execute(WfTaskExpandPlanMapper wfTaskExpandPlanMapper) {
//				return wfTaskExpandPlanMapper.getExpandPlanByClassifyCodes(classifyCodes);
//			}
//		});
	}

	/**
	 * 获取所有分类的共同待办扩展字段
	 * @return
	 */
	public List<WfTaskExpandPlanEntity> getExpandPlanByClassifyCodes() {
		// camunda-TODO
//		return wfTaskExpandPlanMapper.getAllExpandPlanClassifyCount();
//		long wfClassSize = managementService.executeCustomSql(new AbstractCustomSqlExecution<WfTaskExpandPlanMapper, Long>(WfTaskExpandPlanMapper.class) {
//			@Override
//			public Long execute(WfTaskExpandPlanMapper wfTaskExpandPlanMapper) {
//				return wfTaskExpandPlanMapper.getAllExpandPlanClassifyCount();
//			}
//		});
		return getExpandPlanByClassifyCodes(Collections.EMPTY_LIST);
	}
}
