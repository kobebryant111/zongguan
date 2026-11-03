package com.tansun.wf.activiti.service;


import com.alibaba.fastjson.JSON;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.manager.WfHiTaskExpandDataManager;
import com.tansun.wf.activiti.manager.WfTaskExpandDataManager;
import com.tansun.wf.activiti.model.HiTaskExpandDataEntity;
import com.tansun.wf.activiti.model.TaskExpandDataEntity;
import com.tansun.wf.platform.model.WfTaskExpandPlanEntity;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.db.entitymanager.cache.CachedDbEntity;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("workflowHiTaskExpandDataService")
@SuppressWarnings({"rawtypes"})
public class WorkflowHiTaskExpandDataService  extends AbstractManager {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String SUCCEED = WorkflowConstants.SUCCEED;

    public static final String FAILED = WorkflowConstants.FAILED;

    @Autowired
    private WorkflowTaskExpandDataService workflowTaskExpandDataService;

    @Autowired
    private WorkflowTaskExpandPlanService workflowTaskExpandPlanService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private ProcessEngine processEngine;


    /**
     * 将待办扩展数据从运行表转移到历史表
     * @param hiTask
     */
    @Transactional
    public void taskToHistory(HistoricTaskInstanceEntity hiTask) {

        TaskExpandDataEntity taskExpandDataEntity = workflowTaskExpandDataService.selectByTaskId(hiTask.getId());
        if(taskExpandDataEntity == null){
            return ;
        }
        workflowTaskExpandDataService.delete(taskExpandDataEntity);
        final HiTaskExpandDataEntity hiEntity = new HiTaskExpandDataEntity();
        BeanUtils.copyProperties(taskExpandDataEntity,hiEntity);
        
        redundantData(hiEntity, hiTask);

        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
        executor.execute(new Command<Void>() {
            @Override
            public Void execute(CommandContext commandContext) {
                CachedDbEntity cachedEntity = commandContext.getDbEntityManager().getDbEntityCache().getCachedEntity(hiEntity);
                if (cachedEntity==null){
                    WfHiTaskExpandDataManager wfHiTaskExpandDataManager = commandContext.getSession(WfHiTaskExpandDataManager.class);
                    wfHiTaskExpandDataManager.insert(hiEntity);
                }
                return null;
            }
        });

    }

    private void redundantData(HiTaskExpandDataEntity model, HistoricTaskInstanceEntity task) {
        List<VariableInstance> instances = processEngine.getRuntimeService().createVariableInstanceQuery().processInstanceIdIn(task.getProcessInstanceId()).list();
        Map<String, Object> variables = new HashMap<>();
        for (VariableInstance instance : instances) {
            variables.put(instance.getName(), instance.getValue());
        }

		model.setAssigneeRoleCode(MapUtils.getString(variables,"roleCode"));//处理人角色编码
		model.setAssigneeRoleName(MapUtils.getString(variables,"roleName"));//处理人角色名称
		model.setAssigneeDeptCode(MapUtils.getString(variables,"deptCode"));//处理人部门编码
		model.setAssigneeDeptName(MapUtils.getString(variables,"deptName"));//处理人部门名称
		model.setAssigneeOrgCode(MapUtils.getString(variables,"orgCode"));//处理人机构编码
		model.setAssigneeOrgName(MapUtils.getString(variables,"orgName"));//处理人机构名称

		model.setOpionKey(WorkflowServiceAspectService.getExecuteMethodName());//流程操作键值，例如Submit
		model.setOpion(MapUtils.getString(variables,"option"));//流程操作名称，例如提交
		model.setOpinion(MapUtils.getString(variables,"opinion"));//流程操作业务名称,例如同意
        model.setTaskEndTime(task.getEndTime());

        List<Comment> comments = processEngine.getTaskService().getTaskComments(task.getId());
        if (CollectionUtils.isNotEmpty(comments)) {
            model.setCommentMsg(comments.get(0).getFullMessage());//审批意见详情
        }
    }

    /**
     * 更新最后一个任务提交的 待办扩展数据
     * @param entity
     */
    public void updateLastExpandData(ExecutionEntity entity) {

        //从流程变量中获取待办暂存数据
        String taskExpandDataJson = (String) entity.getVariable("_TASK_EXPAND_DATA");
        if(StringUtils.isBlank(taskExpandDataJson)){
            return;
        }
        Map expandData = JSON.parseObject(taskExpandDataJson, Map.class);

        String processDefinitionId = entity.getProcessDefinitionId();
        List<WfTaskExpandPlanEntity> plans = workflowTaskExpandPlanService.getExpandPlanByProcessDefinitionId(processDefinitionId);
        if(plans == null || plans.size() == 0){
            logger.warn("expandPlan is not found !, processDefinitionId is {0}",processDefinitionId);
            return ;
        }
        //查询历史表根据时间降序排序，取最后审批的任务
        // camunda-TODO
        List<HistoricTaskInstance> hiTasks = entity.getProcessEngineServices().getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(entity.getProcessInstanceId()).orderByHistoricActivityInstanceStartTime().desc().listPage(0, 1);
        if(CollectionUtils.isEmpty(hiTasks)){
            logger.warn("updateLastExpandData() hiTasks is null");
            return ;
        }else{
            logger.warn("updateLastExpandData() hiTasks: " + JSON.toJSONString(hiTasks));
        }

        final HiTaskExpandDataEntity hiEntity = new HiTaskExpandDataEntity();
        hiEntity.setProcessInstanceId(entity.getProcessInstanceId());
        hiEntity.setTaskNodeId(hiTasks.get(0).getTaskDefinitionKey());
        for (WfTaskExpandPlanEntity plan : plans) {
            String fieldKey = plan.getFieldKey();
            String filedColumn = plan.getFieldValue();
            //获取需要存储的值
            String value = (String) expandData.get(fieldKey);
            if(StringUtils.isNotBlank(value)){
                try {
                    PropertyUtils.setProperty(hiEntity,filedColumn,value);
                } catch (Exception e) {
                    logger.warn("{0} set value failed !!!",filedColumn);
                    e.printStackTrace();
                }
            }
        }
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
        executor.execute(new Command<Void>() {
            @Override
            public Void execute(CommandContext commandContext) {
                List<HiTaskExpandDataEntity> updateObjects = commandContext.getDbEntityManager().getCachedEntitiesByType(HiTaskExpandDataEntity.class);
                for (HiTaskExpandDataEntity updateObject:updateObjects){
                        HiTaskExpandDataEntity updateHi =  updateObject;
                        logger.warn("updateLastExpandData() updateHi is " + JSON.toJSONString(updateHi));
                        logger.warn("updateLastExpandData() hiEntity is " + JSON.toJSONString(hiEntity));
                        if(updateHi.getProcessInstanceId().equals(hiEntity.getProcessInstanceId()) && updateHi.getTaskNodeId().equals(hiEntity.getTaskNodeId())){
                            //如此恶心
                            if (hiEntity.getField01() != null) {
                                updateHi.setField01(hiEntity.getField01());
                            }
                            if (hiEntity.getField02() != null) {
                                updateHi.setField02(hiEntity.getField02());
                            }
                            if (hiEntity.getField03() != null) {
                                updateHi.setField03(hiEntity.getField03());
                            }
                            if (hiEntity.getField04() != null) {
                                updateHi.setField04(hiEntity.getField04());
                            }
                            if (hiEntity.getField05() != null) {
                                updateHi.setField05(hiEntity.getField05());
                            }
                            if (hiEntity.getField06() != null) {
                                updateHi.setField06(hiEntity.getField06());
                            }
                            if (hiEntity.getField07() != null) {
                                updateHi.setField07(hiEntity.getField07());
                            }
                            if (hiEntity.getField08() != null) {
                                updateHi.setField08(hiEntity.getField08());
                            }
                            if (hiEntity.getField09() != null) {
                                updateHi.setField09(hiEntity.getField09());
                            }
                            if (hiEntity.getField10() != null) {
                                updateHi.setField10(hiEntity.getField10());
                            }
                            if (hiEntity.getField11() != null) {
                                updateHi.setField11(hiEntity.getField11());
                            }
                            if (hiEntity.getField12() != null) {
                                updateHi.setField12(hiEntity.getField12());
                            }
                            if (hiEntity.getField13() != null) {
                                updateHi.setField13(hiEntity.getField13());
                            }
                            if (hiEntity.getField14() != null) {
                                updateHi.setField14(hiEntity.getField14());
                            }
                            if (hiEntity.getField15() != null) {
                                updateHi.setField15(hiEntity.getField15());
                            }
                            if (hiEntity.getField16() != null) {
                                updateHi.setField16(hiEntity.getField16());
                            }
                            if (hiEntity.getField17() != null) {
                                updateHi.setField17(hiEntity.getField17());
                            }
                            if (hiEntity.getField18() != null) {
                                updateHi.setField18(hiEntity.getField18());
                            }
                            if (hiEntity.getField19() != null) {
                                updateHi.setField19(hiEntity.getField19());
                            }
                            if (hiEntity.getField20() != null) {
                                updateHi.setField20(hiEntity.getField20());
                            }

                        }
    
                }
                return null;
            }
        });

    }
    
    /**
     * 删除 历史已完成的拓展数据
     * @param
     */
    public void delete(final HiTaskExpandDataEntity hiTaskExpandDataEntity) {
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
        executor.execute(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				WfHiTaskExpandDataManager wfHiTaskExpandDataManager = commandContext.getSession(WfHiTaskExpandDataManager.class);
                WfTaskExpandDataManager wfTaskExpandDataManager = commandContext.getSession(WfTaskExpandDataManager.class);
                TaskExpandDataEntity taskExpandDataEntity=new TaskExpandDataEntity();
                taskExpandDataEntity.setProcessInstanceId(hiTaskExpandDataEntity.getProcessInstanceId());
                wfHiTaskExpandDataManager.delete(hiTaskExpandDataEntity);
                taskExpandDataEntity.setTaskId(hiTaskExpandDataEntity.getTaskId());
                wfTaskExpandDataManager.delete(taskExpandDataEntity);
				return null;
			}
		});
	}

    /**
     * 根据任务id更新待办扩展记录结束时间
     * @param taskIds
     * @param endTime
     */
    public void updateHiExpandProcessEndTime(final List<String> taskIds, final Date endTime) {
        if(CollectionUtils.isEmpty(taskIds)){
            return;
        }
        if (Context.getCommandContext() != null && Context.getCommandContext().getDbSqlSession() != null ){
            List<HiTaskExpandDataEntity> inCache = Context.getCommandContext().getDbEntityManager().getCachedEntitiesByType(HiTaskExpandDataEntity.class);
            for (HiTaskExpandDataEntity hiTaskExpandDataEntity : inCache) {
                hiTaskExpandDataEntity.setProcessEndTime(endTime);
            }
        }

        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();

        executor.execute((Command<Void>) commandContext -> {
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("taskIds", taskIds);
            params.put("endTime", endTime);
            commandContext.getDbSqlSession().update("updateHiExpandProcessEndTime",params);
            return null;
        });
    }
}
