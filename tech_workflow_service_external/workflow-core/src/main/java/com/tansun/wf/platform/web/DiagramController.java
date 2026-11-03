package com.tansun.wf.platform.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *流程渲染器
 */
@Controller
@RequestMapping("/process/diagram")
public class DiagramController {
		//extends BaseController {
		private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private WorkFlowClientService workFlowClientService;

	// camunda-TODO
//	@Autowired
//	private WfTestExecutionPathService wfTestExecutionPathService;

	@ResponseBody
	@RequestMapping(value = "getDiagram")
	public List<Map> getDiagram(String processDefineId, String processInstanceId,HttpServletRequest request,
								HttpServletResponse response,Model model) {
		ProcessInfo processInfo = new ProcessInfo();
		if(null!=processDefineId&&!"".equals(processDefineId))
			processInfo.setProcessDefineId(processDefineId);
		if(null!=processInstanceId&&!"".equals(processInstanceId))
			processInfo.setProcessInstanceId(processInstanceId);
//		if(StringUtils.isNoneBlank(UserUtils.getTenantCache())){
////			processInfo.setTenantId(UserUtils.getTenantCache());
////		}
		//查询流程轨迹
		List<Map> mapList= new ArrayList<Map>();
		try {
			TaskBean taskBean = workFlowClientService.queryProcessTrack(processInfo);
			if("0".equals(taskBean.getRetCode())){
				if(null!=taskBean.getTaskList()){
					mapList = taskBean.getTaskList();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return mapList;
	}




	/**
	 * 获取某个执行路径的信息
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pathPreview", method = RequestMethod.GET, produces = "application/json")
	public ObjectNode previewIndex(@RequestParam Map<String,Object> reqMap) {
		int previewIndex = MapUtils.getInteger(reqMap, "previewIndex");
		String processDefinitionId = MapUtils.getString(reqMap, "processDefinitionId");
//		return wfTestExecutionPathService.getExecutionPathHighLight(processDefinitionId,previewIndex);
		return null;
	}
}
