package com.tansun.wf.activiti.service;

import com.tansun.wf.activiti.manager.WfTimerConfigManager;
import com.tansun.wf.activiti.model.TimerConfigEntity;
import com.tansun.wf.platform.mapper.WfTimerConfigMapper;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 定时配置信息服务类
 *
 */
// camunda-TODO
@Service("timerConfigService")
public class TimerConfigService {
	
	//定时配置信息状态，0正常，1失效
    private static String TIMERT_CONFIG_STATUS="0";
    //定时发送类型 邮件：email邮件
    private static String TIMERT_CONFIG_SEND_TYPE="email";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private WfTimerConfigMapper wfTimerConfigMapper;

	
    /**
	 * 定时配置信息新增
     * @param
     */
	public void addTimerConfig(final TimerConfigEntity timerConfig) {
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
//		managementService.executeCommand(new Command<Void>() {
			@SuppressWarnings("unchecked")
			@Override
			public Void execute(CommandContext commandContext) {
				WfTimerConfigManager timerConfigManager = commandContext.getSession(WfTimerConfigManager.class);
				timerConfigManager.insert(timerConfig);
				return null;
			}
		});
	}

    /**
	 * 定时配置信息删除
     * @param id
     */
	public void deleteTimerConfig(final String id) {
		final TimerConfigEntity timerConfig = new TimerConfigEntity();
		timerConfig.setId(id);
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
//		managementService.executeCommand(new Command<Void>() {
			@SuppressWarnings("unchecked")
			@Override
			public Void execute(CommandContext commandContext) {
				WfTimerConfigManager timerConfigManager = commandContext.getSession(WfTimerConfigManager.class);
				timerConfigManager.delete(timerConfig);
				return null;
			}
		});
	}
	/**
	 * 定时配置信息删除
     * @param
     */
	public void deleteTimerConfigByCondition(String procDefId ,String procInstId,String taskDefKey,String executionId) {
		final TimerConfigEntity timerConfig = new TimerConfigEntity();
		timerConfig.setProcDefId(procDefId);
		timerConfig.setProcInstId(procInstId);
		timerConfig.setTaskDefKey(taskDefKey);
		timerConfig.setExecutionId(executionId);
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
//		managementService.executeCommand(new Command<Void>() {
			@SuppressWarnings("unchecked")
			@Override
			public Void execute(CommandContext commandContext) {
				WfTimerConfigManager timerConfigManager = commandContext.getSession(WfTimerConfigManager.class);
				timerConfigManager.deleteTimerConfigByCondition(timerConfig);
				return null;
			}
		});
	}
	/**
	 * 定时配置信息查询
     * @param
     */
	public List<HashMap> findTimerConfig(final String status) {
		// camunda-TODO
//		List<HashMap> timerConfigList =  managementService.executeCustomSql(new AbstractCustomSqlExecution<WfTimerConfigMapper, List<HashMap>>(WfTimerConfigMapper.class) {
//			@Override
//			public List<HashMap> execute(WfTimerConfigMapper wfTimerConfigMapper) {
//				return wfTimerConfigMapper.findTimerConfig(status);
//			}
//		});
		List<HashMap> timerConfigList = wfTimerConfigMapper.findTimerConfig(status);
		return timerConfigList;
	}
	
	/**
	 *创建任务时保存定时配置信息
	 **/
	public void addTimerConfigByCreateTask(TaskEntity entity) {		
	 Boolean flag = false;
     TimerConfigEntity tc = new TimerConfigEntity();
     BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(entity.getProcessDefinitionId());
     // camunda-TODO
     //FlowElement flowElement = bpmnModel.getMainProcess().getFlowElement(entity.getTaskDefinitionKey());
     FlowElement flowElement = bpmnModel.getModelElementById(entity.getTaskDefinitionKey());
		List<ModelElementInstance> elements = (ArrayList)flowElement.getExtensionElements().getElements();
		if(null!=elements) {
//			List<ExtensionElement> extensionElements = elements.get("timeset");
//			if(null!=extensionElements && extensionElements.size() >0){
//				String text =  extensionElements.get(0).getElementText();
//				if(StringUtils.isNotBlank(text)&&!"\"\"".equals(text) && !"null".equals(text)){
//					JSONObject jsonObj = JSON.parseObject(text);
//					JSONObject timeset = (JSONObject) jsonObj.get("timeset");
//					String timeType = (String) timeset.get("timetype");
//					String calculation = (String) timeset.get("calculation");
//					String beginTime = (String) timeset.get("begintime");
//					String endTime = (String) timeset.get("endtime");
//					String fashion = (String) timeset.get("fashion");
//					String frequency = (String) timeset.get("frequency");
//					String timeFrequency = (String) timeset.get("timefrequency");
//					String remindNum = (String) timeset.get("remindnum");
//					String remindType = (String) timeset.get("remindtype");
//					String templateContent = (String) timeset.get("templatecontent");
//					try {
//					tc.setRemindNum(Long.valueOf(remindNum));
//				    tc.setTimeType(timeType);
//				    tc.setTemplate(templateContent);
//				    tc.setSendType(remindType);
//					if(StringUtils.isNotBlank(beginTime)&&StringUtils.isNotBlank(endTime)
//							&&StringUtils.isNotBlank(frequency)&&StringUtils.isNotBlank(timeFrequency)
//							&&StringUtils.isNotBlank(remindType)){
//			            	set时间表达式规则
//			            	if("hour".equals(timeFrequency)){
//			            		tc.setCronExpression("0 0 0/"+frequency+" * * ?");
//			            		tc.setCfrequency(Long.valueOf(frequency));
//			            	}else if("day".equals(timeFrequency)){
//			            		tc.setCronExpression("0 0 0 0/"+frequency+" * ?");
//			            		tc.setCfrequency(Long.valueOf(frequency)*24);
//			            	}
//			            	Long bgms=0L;
//			            	Long edms=0L;
//			            	if(!"systemDefine".equals(calculation)){//自定义
//			            		set时间	   /***自定义暂时保存系统定义，开发自定义时在此修改***/
//				            		 bgms = new Date().getTime()+Long.valueOf(beginTime)*60*60*1000;
//					            	 edms = new Date().getTime()+Long.valueOf(endTime)*60*60*1000;
//			            	}else{//系统定义
//			            		bgms = new Date().getTime()+Long.valueOf(beginTime)*60*60*1000;
//				            	edms = new Date().getTime()+Long.valueOf(endTime)*60*60*1000;
//			            	}
//			            	Date bg=new Date();
//			            	bg.setTime(bgms);
//			            	tc.setBeginTime(bg);
//			            	Date ed=new Date();
//			            	ed.setTime(edms);
//							tc.setEndTime(ed);
//				            flag=true;
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
		}
     if(flag){
     	 tc.setId(UUID.randomUUID().toString().replaceAll("-", ""));// 主键id
          tc.setProcDefId(entity.getProcessDefinitionId());
          tc.setTaskDefKey(entity.getTaskDefinitionKey());
          tc.setProcInstId(entity.getProcessInstanceId());
          tc.setExecutionId(entity.getExecutionId());
          tc.setCreateTime(new Date());
          tc.setNote("");
          tc.setStatus(TIMERT_CONFIG_STATUS);
          tc.setTenantId(entity.getTenantId());
          this.addTimerConfig(tc);
     }
	}
	/**
	 * 修改定时配置提醒次数
	 * @param id
	 */
	public void updateTimerConfigNumber(final String id,final Long remindNum){
		final TimerConfigEntity timerConfig = new TimerConfigEntity();
		timerConfig.setId(id);
		timerConfig.setRemindNum(remindNum);
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
//		managementService.executeCommand(new Command<Void>() {
			@SuppressWarnings("unchecked")
			@Override
			public Void execute(CommandContext commandContext) {
				WfTimerConfigManager timerConfigManager = commandContext.getSession(WfTimerConfigManager.class);
				timerConfigManager.update(timerConfig);
				return null;
			}
		});
	}
	
}
