package com.tansun.wf.platform.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.enums.TrustType;
import com.tansun.wf.platform.model.WfAgentEntity;
import com.tansun.wf.platform.model.WfTreeNode;
import com.tansun.wf.platform.query.WfAgentQuery;
import com.tansun.wf.platform.service.WfAgentService;
import com.tansun.workflow.model.ProcessInfo;


@RestController
@RequestMapping("/wf/wfAgent")
public class WfAgentController {
	@Autowired
	private  WfAgentService wfAgentService;
	
	@RequestMapping(value ="/list")
	public   WfResponseVO  list(@RequestBody WfAgentQuery query,PageInfo page) {
		Map<String,Object> result = new  HashMap<>();
		query.setTrustType(TrustType.AGENT.val);
		result.put("grid",wfAgentService.findPage(query, page));
		return WfResponseVO.successResponse(result);
	}
	
	@RequestMapping(value ="/findProcessTaskInfo")
	public  WfResponseVO  findProcessTaskInfo(@RequestBody WfAgentQuery query) {
		ProcessInfo  processInfo  = new ProcessInfo();
		processInfo.setTenantId(query.getTenantId());
		List<WfTreeNode> list =this.wfAgentService.findProcessTaskInfo(processInfo);
		return   WfResponseVO.successResponse(list);	
	}
	
	@RequestMapping(value ="/save")
	public  WfResponseVO   save(@RequestBody  WfAgentEntity agentEntity) {
		agentEntity.setCreateDate(new Date());
		agentEntity.setTrustType(TrustType.AGENT.val);
		this.wfAgentService.save(agentEntity);
		return  WfResponseVO.successResponse("保存成功!");
	}
	
	@RequestMapping(value ="/update")
	public  WfResponseVO   update(@RequestBody  WfAgentEntity agentEntity) {
		this.wfAgentService.update(agentEntity);
		return  WfResponseVO.successResponse("保存成功!");
	}
	
	@RequestMapping(value ="/delete")
	public  WfResponseVO   delete(@RequestBody  WfAgentEntity agentEntity) {
		this.wfAgentService.delete(agentEntity);
		return  WfResponseVO.successResponse("删除成功!");
	}

}
