package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;

import com.tansun.wf.activiti.exception.WorkflowException;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfTenantEntity;
import com.tansun.wf.platform.query.WfTenantQuery;
import com.tansun.wf.platform.service.WfTenantService;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zhuofushen
 * 租户管理
 *
 */
@RestController
@RequestMapping("/wf/tenant")
public class WfTenantController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private WfTenantService wfTenantService;


	/**
	 * 租户列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public WfResponseVO wfTenantList(@RequestParam Map<String,Object> reqMap, PageInfo pageInfo){
		try {
			WfTenantQuery wfTenantQuery = new WfTenantQuery();
			if(reqMap.containsKey("name")){
				wfTenantQuery.setName(reqMap.get("name").toString());
			}
			if(reqMap.containsKey("tenantId")){
				wfTenantQuery.setTenantId(reqMap.get("tenantId").toString());
			}
			PageInfo<WfTenantEntity> page = wfTenantService.getPageList(wfTenantQuery, pageInfo);
			return WfResponseVO.successResponse(page);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return WfResponseVO.successResponse(e.getMessage());
		}
	}


	/**
	 * 租户保存
	 */
	@RequestMapping(value = "/save")
	public WfResponseVO saveWfTenant(@RequestParam Map<String,Object> reqMap) throws WorkflowException {
		if(StringUtils.isBlank(MapUtils.getString(reqMap,"tenantId"))){
			return WfResponseVO.errorResponse("请填写租户编号");
		}
		if(StringUtils.isBlank(MapUtils.getString(reqMap,"name"))){
			return WfResponseVO.errorResponse("请填写租户名称");
		}
		reqMap.put("userId", "superadmin");
		return wfTenantService.saveWfTenant(reqMap);
	}

	/**
	 * 删除租户
	 */
	@RequestMapping(value = "/delete")
	public WfResponseVO deleteWfTenant(@RequestParam Map<String,Object> reqMap){
		if(!reqMap.containsKey("id") || reqMap.get("id").toString() == null){
			return WfResponseVO.errorResponse("删除异常，租户ID为空");
		}
		//判断该租户是否被使用
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 获取service类
		List<ProcessDefinition> pdList = processEngine.getRepositoryService().createProcessDefinitionQuery()
				// 通过租户查询
				.tenantIdIn(MapUtils.getString(reqMap, "tenantId"))
				.list();
		if(pdList.size()>0){
			return WfResponseVO.errorResponse("该租户已经有相应的流程，不允许删除");
		}
		return wfTenantService.deleteWfTenant(reqMap);
	}


	/**
	 * 获取单个租户
	 */
	@RequestMapping(value = "/get")
	public WfResponseVO get(@RequestParam Map<String,Object> reqMap) throws Exception {
		String id = MapUtils.getString(reqMap, "id");
		if (!StringUtils.isEmpty(id)) {
			return WfResponseVO.successResponse(wfTenantService.getById(id));
		}else{
			return WfResponseVO.successResponse(null);
		}
	}



}
