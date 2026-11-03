package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.manager.WfHiCarbonCopyManager;
import com.tansun.wf.activiti.model.CarbonCopyConfig;
import com.tansun.wf.activiti.model.HiCarbonCopyEntity;
import com.tansun.wf.activiti.query.WfHiCarbonCopyQuery;
import com.tansun.wf.camunda.bpmn.impl.instance.Carboncopy;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 抄送任务服务类
 *
 */
@Service("workflowCarbonCopyService")
public class WorkflowCarbonCopyService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;

	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;
	
	@Autowired
	private WorkflowProcessDefineService workflowProcessDefineService;

	@Autowired
    private WorkflowUserService workflowUserService;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private WorkflowTaskService workflowTaskService;

    /**
     * 抄送
     * @param entity
     */
	public void doCopy(TaskEntity entity){
        List<String> carbonCopyUsers = new ArrayList<>();

        //根据节点配置，抄送任务
        CarbonCopyConfig nodeConfig = workflowProcessDefineService.getNodeExtensionProperty(entity.getProcessDefinitionId(), entity.getTaskDefinitionKey(), Carboncopy.class, CarbonCopyConfig.class);
        if(nodeConfig != null){
            carbonCopyUsers.addAll(getUsersByConfig(entity, nodeConfig));
        }


        //根据任务提交参数，抄送任务
        String ccVar = (String) entity.getVariable("_CARBON_COPY");
        if(StringUtils.isNotBlank(ccVar)){
            CarbonCopyConfig varConfig = JSON.parseObject(ccVar,CarbonCopyConfig.class);
            carbonCopyUsers.addAll(getUsersByConfig(entity,varConfig));
        }

        //保存
        this.saveCarbonCopy(entity,carbonCopyUsers);
    }

    private void saveCarbonCopy(TaskEntity task, List<String> carbonCopyUsers) {
	    final List<HiCarbonCopyEntity> entities = new ArrayList<>();
        for (String userId : carbonCopyUsers) {
            HiCarbonCopyEntity e = new HiCarbonCopyEntity();
            e.setIsRead("N");
            e.setProcDefId(task.getProcessDefinitionId());
            e.setType("user");
            e.setProcInstId(task.getProcessInstanceId());
            e.setUserId(userId);
            e.setTaskId(task.getId());
            e.setCreateTime(new Date());
            entities.add(e);
        }

        // camunda-TODO 暂时的写法，应该还有其他更加方便的写法，或者把Manager去掉，直接使用mapper
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
        executor.execute(new Command<Void>() {
            @SuppressWarnings("unchecked")
            @Override
            public Void execute(CommandContext commandContext) {
                WfHiCarbonCopyManager wfHiCarbonCopyManager = commandContext.getSession(WfHiCarbonCopyManager.class);
                for (HiCarbonCopyEntity entity : entities) {
                    wfHiCarbonCopyManager.insert(entity);
                }
                return null;

            }
        });
    }

    @Autowired
    private ProcessEngine processEngine;



    /**
     * 获取抄送用户
     * @param entity
     * @param carbonCopyConfig
     * @return
     */
    private List<String> getUsersByConfig(TaskEntity entity, CarbonCopyConfig carbonCopyConfig) {
        List<String> carbonCopyUsers = new ArrayList<>();
        carbonCopyUsers.addAll(carbonCopyConfig.getUserLoginNames());
        if(CollectionUtils.isNotEmpty(carbonCopyConfig.getRoleCodes())){
            Set<String> users = workflowUserService.findUserByRoleList(entity.getTenantId(), carbonCopyConfig.getRoleCodes());
            if(CollectionUtils.isNotEmpty(users)){
                carbonCopyUsers.addAll(users);
            }
        }
        return carbonCopyUsers;
    }

    /**
     *
     * @param taskBean
     */
    public void saveCarbonCopyInfo(TaskBean taskBean) {
        Task task = taskService.createTaskQuery().taskId(taskBean.getTaskId()).singleResult();
        CarbonCopyConfig config = new CarbonCopyConfig();
        if (CollectionUtils.isNotEmpty(taskBean.getCcUsers())){
            config.setUserLoginNames(taskBean.getCcUsers());
        }
        if(CollectionUtils.isNotEmpty(taskBean.getCcRoles())){
            config.setRoleCodes(taskBean.getCcRoles());
        }

        if(CollectionUtils.isNotEmpty(taskBean.getCcUsers()) || CollectionUtils.isNotEmpty(taskBean.getCcRoles())){
            runtimeService.setVariable(task.getExecutionId(),"_CARBON_COPY",JSON.toJSONString(config));
        }else{
            runtimeService.removeVariable(task.getExecutionId(),"_CARBON_COPY");
        }
    }

    /**
     * 查询抄送任务
     * @param
     * @return
     */
//    public TaskBean findCarbonCopyTasks(TaskInfo taskInfo){
//        String userId = taskInfo.getUserId();
//        if(StringUtils.isBlank(userId)){
//            return new TaskBean(FAILED,"查询失败，用户为空!");
//        }
//
//        // camunda-TODO
//        WfHiCarbonCopyQuery query = new WfHiCarbonCopyQuery();
//        query.userId(taskInfo.getUserId());
//        //获取分页
//        int currentPage = StringUtils.isNumericSpace(taskInfo.getCurrentPage()) ? Integer.parseInt(taskInfo.getCurrentPage()):1;
//        int pageSize = StringUtils.isNumericSpace(taskInfo.getPageSize()) ? Integer.parseInt(taskInfo.getPageSize()):10;
//        int firstResult = pageSize * (currentPage - 1);
//        List<Map> list = query.listPage(firstResult, pageSize);
//
//        Collection<String> taskIds = Collections2.transform(list, new Function<Map, String>() {
//            @Override
//            public String apply(Map entity) {
//                return (String) entity.get("taskId");
//            }
//        });
//
//        List<Map> tasks = null;
//        if(CollectionUtils.isNotEmpty(taskIds)){
//            //关联任务表
//            TaskInfo condition = new TaskInfo();
//            condition.setTaskIds(new ArrayList<>(taskIds));
//            condition.setPageSize(Integer.MAX_VALUE +"");
//            final TaskBean pendTaskList = workflowTaskService.findPendTaskList(condition);
//            final TaskBean completedTaskList = workflowTaskService.findCompletedTaskList(condition);
//            Collection<Map> tmp = Collections2.transform(list, new Function<Map, Map>() {
//                @Override
//                public Map apply(Map entity) {
//                    HashMap<Object, Object> ccTask = new HashMap<>();
//                    String taskId = (String) entity.get("taskId");
//                    Map pendTask = getFromTaskBean(pendTaskList, taskId);
//                    Map completedTask = getFromTaskBean(completedTaskList, taskId);
//                    if (pendTask != null) {
//                        ccTask.putAll(pendTask);
//                    } else if (completedTask != null) {
//                        ccTask.putAll(completedTask);
//                    }
//                    //是否抄送
//                    ccTask.put("isCarbonCopy", "Y");
//                    //抄送时间
//                    ccTask.put("carbonCopyTime", entity.get("createTime"));
//                    return ccTask;
//                }
//            });
//            tasks = new ArrayList<>(tmp);
//        }
//        TaskBean taskBean = new TaskBean();
//        taskBean.setTaskList(tasks);
//        taskBean.setTotal(query.count()+"");
//        taskBean.setCurrentPageIndex(currentPage+"");
//        return taskBean;
//    }

    private Map getFromTaskBean(TaskBean taskBean, String taskId) {
        List<Map> taskList = taskBean.getTaskList();
        if (CollectionUtils.isNotEmpty(taskList)) {
            for (Map task : taskList) {
                if(taskId.equals(task.get("taskId"))){
                    return  task;
                }
            }
        }
        return null;
    }
}
