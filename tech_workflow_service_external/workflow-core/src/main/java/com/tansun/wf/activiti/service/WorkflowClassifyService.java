package com.tansun.wf.activiti.service;

import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.model.ClassifyEntity;
import com.tansun.wf.activiti.query.WfClassifyQuery;
import com.tansun.wf.platform.mapper.WfClassifyMapper;
import com.tansun.workflow.model.ClassifyInfo;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.ManagementServiceImpl;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程分类服务类
 *
 */
@Service("workflowClassifyService")
public class WorkflowClassifyService extends AbstractManager {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;

	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private WfClassifyMapper wfClassifyMapper;

	/**
	 * 通过code获取流程分类
	 */
    public List<ClassifyEntity> getClassifyByClassifyCode(String classifyCode) {
		if (StringUtils.isNotBlank(classifyCode)) {
    		return new WfClassifyQuery().code(classifyCode).list();
		}
    	return new WfClassifyQuery().list();
    }

	/**
	 * 获取分类编码和名称的映射关系 Map<code, name>
	 * @return
	 */
    public Map<String, String> getClassifyCodeMapName() {
		Map<String, String> map = new HashMap<>();
		ClassifyInfo params = new ClassifyInfo();
		params.setDel_flag("0");
		params.setPageSize(Integer.toString(Integer.MAX_VALUE));
		List<ClassifyInfo> classifyList = this.findClassifyList(params);
		if (CollectionUtils.isNotEmpty(classifyList)) {
			for (ClassifyInfo classifyInfo : classifyList) {
				map.put(classifyInfo.getCode(), classifyInfo.getName());
			}
		}else{
			return map;
		}
		return map;
	}

    public List<ClassifyEntity> getClassifyByClassifyId(String parentIds) {
    	return new WfClassifyQuery().parentIds(parentIds).list();
    }

    public List<ClassifyEntity> getClassifyById(String id) {
    	return new WfClassifyQuery().id(id).list();
    }

	/**
	 * 查询流程分类列表
	 * id
	 * @return
	 */
	public List<ClassifyInfo> findClassifyList(ClassifyInfo classifyInfo) {

		WfClassifyQuery query = new WfClassifyQuery();
		query.setId(classifyInfo.getId());
		query.setCode(classifyInfo.getCode());
		query.setName(classifyInfo.getName());
		query.setParentId(classifyInfo.getParent_id());
		query.setParentIds(classifyInfo.getParent_ids());
		query.setSort(classifyInfo.getSort());
		query.setType(classifyInfo.getType());
		query.setUSEABLE(classifyInfo.getUSEABLE());
		query.setRemarks(classifyInfo.getRemarks());
		query.setDelFlag(classifyInfo.getDel_flag());
		query.setTenantId(classifyInfo.getTenantId());
		
		if (this.managementService instanceof ManagementServiceImpl) {
			query.setCommandExecutor(((ManagementServiceImpl)this.managementService).getCommandExecutor());			
		}
		//获取分页
		int currentPage = StringUtils.isNumericSpace(classifyInfo.getCurrentPage()) ? Integer.parseInt(classifyInfo.getCurrentPage()):1;
		int pageSize = StringUtils.isNumericSpace(classifyInfo.getPageSize()) ? Integer.parseInt(classifyInfo.getPageSize()):10;
		int firstResult = pageSize * (currentPage - 1);
		//查询内容
		List<ClassifyEntity> list = query.listPage(firstResult, pageSize);
		List<ClassifyInfo> listClassifyInfo = new ArrayList<ClassifyInfo>();
//		long count = query.count();
		for (ClassifyEntity classifyEntity : list) {
			ClassifyInfo ClassifyInfo = new ClassifyInfo();
			ClassifyInfo.setId(classifyEntity.getId());
			ClassifyInfo.setParent_id(classifyEntity.getParent_id());
			ClassifyInfo.setParent_ids(classifyEntity.getParent_ids());
			ClassifyInfo.setCode(classifyEntity.getCode());
			ClassifyInfo.setName(classifyEntity.getName());
			ClassifyInfo.setSort(classifyEntity.getSort());
			ClassifyInfo.setRemarks(classifyEntity.getRemarks());
			ClassifyInfo.setCreate_by(classifyEntity.getCreate_by());
			ClassifyInfo.setCreate_date(classifyEntity.getCreate_date());
			ClassifyInfo.setUpdate_by(classifyEntity.getUpdate_by());
			ClassifyInfo.setUpdate_date(classifyEntity.getUpdate_date());
			ClassifyInfo.setTenantId(classifyEntity.getTenantId());
			ClassifyInfo.setType(classifyEntity.getType());
			ClassifyInfo.setTotal(list.size()+"");
			listClassifyInfo.add(ClassifyInfo);
		}
		return listClassifyInfo;
	}
	public Map<String, Object> getCodeById(final String id) {
		return  wfClassifyMapper.selectClassfyCodeById(id);
	}

}
