package com.tansun.wf.platform.web;

import java.util.ArrayList;
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
import com.tansun.wf.platform.query.WfAgentQuery;
import com.tansun.wf.platform.query.WfDelegateQuery;
import com.tansun.wf.platform.service.WfAgentService;
import com.tansun.wf.platform.service.WfDelegateService;

/**
 * 代办、协办用列
 * @author admin
 *
 */
@RestController
@RequestMapping("/wf/wfAgency")
public class WfAgencyController {
	
	@Autowired
	private  WfAgentService wfAgentService;
	
	@Autowired
	private  WfDelegateService  wfDelegateService;

	@RequestMapping(value ="/list")
	public   WfResponseVO  list(@RequestBody WfAgentQuery query,PageInfo page) {
		Map<String,Object> result = new  HashMap<>();
		List<String>  list = new  ArrayList<>();
		list.add(TrustType.REPLACE.val);
		list.add(TrustType.AGREE.val);
		query.setTrustTypes(list);
		result.put("grid",wfAgentService.findPage(query, page));
		return WfResponseVO.successResponse(result);
	}
	
	@RequestMapping(value ="/save")
	public  WfResponseVO   save(@RequestBody  WfAgentEntity agentEntity) {
	    if (findDelegateExist(agentEntity)) {
	    	return  WfResponseVO.errorResponse("委托人已经存在代办配置");
	    }
		agentEntity.setCreateDate(new Date());
		this.wfDelegateService.saveDelegate(agentEntity);
		return  WfResponseVO.successResponse("保存成功!");
	}
	
	@RequestMapping(value ="/update")
	public  WfResponseVO   update(@RequestBody  WfAgentEntity agentEntity) {
	    if (findDelegateExist(agentEntity)) {
	    	return  WfResponseVO.errorResponse("委托人已经存在代办配置");
	    }	    
		this.wfAgentService.update(agentEntity);
		return  WfResponseVO.successResponse("保存成功!");
	}
	
	@RequestMapping(value ="/delete")
	public  WfResponseVO   delete(@RequestBody  WfAgentEntity agentEntity) {
		this.wfAgentService.delete(agentEntity);
		return  WfResponseVO.successResponse("删除成功!");
	}
	
	@RequestMapping(value ="/findDelegateList")
	public  WfResponseVO   findDelegateList( @RequestBody WfDelegateQuery query,PageInfo page) {
		Map<String,Object> result = new  HashMap<>();
		result.put("grid",this.wfDelegateService.findDelegateInfo(query, page));
		return WfResponseVO.successResponse(result);
	}
	

	private   boolean findDelegateExist(WfAgentEntity agentEntity) {
		   WfAgentQuery  query  = new  WfAgentQuery();
		   String  id = agentEntity.getId();
		   query.setTrustUser(agentEntity.getTrustUser());
		   boolean  flag =false;
		   List<WfAgentEntity>  list =  this.wfAgentService.findList(query);
		   if (list != null && list.size() >0) {
			   int  len  = list.size();
			   if (len == 1) {
				   WfAgentEntity  agent =  list.get(0); 
				   if (id != null && !id.equals(agent.getId())) {
					   flag =true;
				   }
			   }else {
				   flag =true;
			   }
		   }
		   return  flag;		
	}
	
	@RequestMapping(value ="/takeBack")
	public  WfResponseVO  takeBack(@RequestBody  List<String> ids ) {
		this.wfDelegateService.taskBack(ids);
		return  WfResponseVO.successResponse("成功收回!");
		
	}
	
}
