package com.tansun.wf.activiti.listener;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.activiti.service.WorkflowTaskExpandDataService;
import com.tansun.wf.activiti.service.WorkflowTaskService;
import com.tansun.wf.activiti.service.WorkflowThreadLocalUtils;
import com.tansun.wf.cache.UserTaskCache;
import com.tansun.wf.cache.UserTaskCacheQueue;
import com.tansun.wf.camunda.bpmn.instance.DefineEvent;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import com.tansun.wf.platform.service.WfActHiCommentService;
import com.tansun.wf.platform.service.WfEventService;
import com.tansun.wf.platform.service.WfTaskSelectUserService;
import com.tansun.workflow.model.PostInfo;
import com.tansun.workflow.model.TaskInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.management.JobDefinition;
import org.camunda.bpm.model.bpmn.instance.MultiInstanceLoopCharacteristics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author lijiang
 * @create 2021-04-01 17:19
 */
public class GlobalTaskEventListener implements TaskListener, ApplicationContextAware {


    private static Logger logger = LoggerFactory.getLogger(GlobalTaskEventListener.class);

    private  ApplicationContext applicationContext;

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("进入全局任务创建监听器");
        WorkflowTaskExpandDataService workflowTaskExpandDataService = this.applicationContext.getBean(WorkflowTaskExpandDataService.class);

        DelegateExecution  execution =  delegateTask.getExecution();

        TaskEntity entity =  null;
        if (delegateTask instanceof  TaskEntity) {
            entity  = (TaskEntity) delegateTask;
        }else {
            entity = this.executionTask(execution, delegateTask);
        }
        TaskInfo taskInfo = new TaskInfo();
        Collection<MultiInstanceLoopCharacteristics> mls = delegateTask.getBpmnModelElementInstance().getChildElementsByType(MultiInstanceLoopCharacteristics.class);
        if(CollectionUtils.isNotEmpty(mls)) {
            MultiInstanceLoopCharacteristics characteristics = mls.iterator().next();
            String camundaElementVariable = characteristics.getCamundaElementVariable();
            Object variable = execution.getVariable(camundaElementVariable);
            try {
                PostInfo postInfo = JSON.parseObject(JSON.toJSONString(variable), PostInfo.class);
                WfTaskSelectUserService wfTaskSelectUserService = (WfTaskSelectUserService)applicationContext.getBean("wfTaskSelectUserService");
                wfTaskSelectUserService.saveUserTaskAssignment(delegateTask,postInfo);
            }catch (Exception e){
                throw new RuntimeException(camundaElementVariable+"多工作项对象不是PostInfo");
            }
        }else{
            String requestMethod = WorkflowThreadLocalUtils.getString("request-method");
            if(!StringUtils.equals("jumpFlowTask",requestMethod)){
                //为任务动态添加候选人
                WorkflowTaskService workflowTaskService = this.applicationContext.getBean(WorkflowTaskService.class);
                workflowTaskService.addCandidateUser(delegateTask);
            }
        }

        //保存扩展表数据
        workflowTaskExpandDataService.saveTaskExpandData(entity,taskInfo.getOrgCode());

		WfEventService wfEventService = applicationContext.getBean(WfEventService.class);
		wfEventService.trigger(delegateTask, DefineEvent.TASK_EVENT_CREATE);

		// 设置超时时间字段
        List<JobDefinition> jobDefinitions = delegateTask.getProcessEngineServices().getManagementService().createJobDefinitionQuery().processDefinitionId(delegateTask.getProcessDefinitionId()).activityIdIn(delegateTask.getTaskDefinitionKey()).list();
        if(CollectionUtils.isNotEmpty(jobDefinitions)){
            String jobConfiguration = jobDefinitions.get(0).getJobConfiguration();
            if(StringUtils.isNotBlank(jobConfiguration) && jobConfiguration.startsWith("DURATION: ")){
                Duration parse = Duration.parse(jobConfiguration.substring(10));
                Date date = DateUtils.addSeconds(new Date(), (int) parse.getSeconds());
                delegateTask.setDueDate(date);
            }

        }
        // 新增任务时，添加缓存队列
        Boolean cacheQueueEnable = applicationContext.getEnvironment().getProperty("workflow.cacheQuque.enabled", Boolean.class,false);
        if(cacheQueueEnable){
            logger.info("用户人员发生变化进行队列缓存，taskId:{}",entity.getId());
            UserTaskCache userTaskCache = new UserTaskCache();
            userTaskCache.setTaskId(entity.getId());
            userTaskCache.setActivityId(entity.getTaskDefinitionKey());
            userTaskCache.setProcessDefinitionId(entity.getProcessDefinitionId());
            userTaskCache.setTaskName(entity.getName());
            userTaskCache.setTaskType("new");
            userTaskCache.setProcessInstanceId(entity.getProcessInstanceId());
            userTaskCache.setAppId(applicationContext.getEnvironment().getProperty("workflow.cacheQuque.appId"));
            userTaskCache.setDatailUrl(applicationContext.getEnvironment().getProperty("workflow.cacheQuque.datailUrl"));
            WfActHiCommentService wfActHiCommentService = applicationContext.getBean(WfActHiCommentService.class);
            ActRuIdentityLinkQuery actRuIdentityLinkQuery = new ActRuIdentityLinkQuery();
            actRuIdentityLinkQuery.setTaskId(entity.getId());
            List<ActRuIdentitylinkEntity> candidateUserList = wfActHiCommentService.findCandidateUserList(actRuIdentityLinkQuery);
            List<PostInfo> postInfos = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(candidateUserList)){
                for (ActRuIdentitylinkEntity actRuIdentitylinkEntity : candidateUserList) {
                    PostInfo postInfo = new PostInfo();
                    postInfo.setType(actRuIdentitylinkEntity.getType());
                    postInfo.setUserId(actRuIdentitylinkEntity.getUserId());
                    postInfo.setDeptCode(actRuIdentitylinkEntity.getDeptCode());
                    postInfo.setOrgCode(actRuIdentitylinkEntity.getOrgCode());
                    postInfo.setRoleCode(actRuIdentitylinkEntity.getRoleCode());
                    postInfo.setGroupId(actRuIdentitylinkEntity.getGroupId());
                    postInfos.add(postInfo);
                }
            }
            userTaskCache.setParticipants(postInfos);
            UserTaskCacheQueue.getInstance().offer(userTaskCache);
            logger.info("用户人员发生变化进行队列缓存完成，userTaskCache:{}", JSON.toJSONString(userTaskCache));
        }
        logger.info("全局任务创建监听器执行完成");
    }
    
    

    private  TaskEntity  executionTask(DelegateExecution execution,DelegateTask  task) {
    	TaskEntity  taskEntity  = new  TaskEntity();
    	taskEntity.setProcessDefinitionId(execution.getProcessDefinitionId());
    	taskEntity.setId(task.getId());
    	taskEntity.setProcessInstanceId(execution.getProcessInstanceId());
    	taskEntity.setVariable("_TASK_EXPAND_DATA", task.getVariable("_TASK_EXPAND_DATA"));
    	taskEntity.setAssignee(task.getAssignee());
    	taskEntity.setTaskDefinitionKey(task.getTaskDefinitionKey());
    	taskEntity.setVariables(task.getVariables());
    	taskEntity.setExecutionId(task.getExecutionId());
    	return  taskEntity;

    	
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public  ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    

}
