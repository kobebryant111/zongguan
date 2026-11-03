package com.tansun.wf.activiti.cmd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceManager;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.instance.InputDataItem;
import org.camunda.bpm.model.bpmn.instance.MultiInstanceLoopCharacteristics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.activiti.model.SequentialAddedTaskState;
import com.tansun.wf.activiti.service.WorkflowProcessDefineService;
import com.tansun.workflow.model.TaskBean;

public class AddTaskCmd implements Command<Void> {


    private Logger logger = LoggerFactory.getLogger(AddTaskCmd.class);

    private MultiInstanceLoopCharacteristics _loopCharacteristics;

    private List<String> _userIds;

    //加签部门
    private  List<String> _deptCodes;

    //加签角色
    private  List<String> _roleCodes;

	private String _executionId;

	private String addTaskPosition;

	private Map localVariables;

	private String comment;


    public AddTaskCmd(TaskBean taskBean, MultiInstanceLoopCharacteristics loopCharacteristics) {
        _userIds = taskBean.getAddTaskUsers();
        _roleCodes = taskBean.getAddRoleCodes();
        _deptCodes = taskBean.getAddDeptCodes();
        _executionId = taskBean.getExecutionId();
        addTaskPosition = taskBean.getAddTaskPosition();
        _loopCharacteristics = loopCharacteristics;
        localVariables = taskBean.getLocalVariables();
        comment = taskBean.getComment();
    }


    @Override
	public Void execute(CommandContext commandContext) {
	    if(_loopCharacteristics.isSequential()){
	        //串行加签
	        return sequentialAddTask(commandContext);
        }else{
	        //并行加签
            return parallelAddTask(commandContext);
        }

	}

    /**
     * 串行加签
     * 1.往前加签：
     *    a.添加加签任务
     *    b.复制当前任务添加至后面
     * 2.往后加签
     *    a.添加加签任务
     * 3.更新会签总数（nrOfInstances）变量
     * 4.更新集合
     * 5.是前加签,结束当前任务
     * @param commandContext
     * @return
     */
    private Void sequentialAddTask(CommandContext commandContext) {
        ExecutionEntity execution = commandContext.getExecutionManager().findExecutionById(_executionId);

        List<TaskEntity> tasks = execution.getTasks();
        TaskEntity taskEntity = tasks.get(0);

        if (SequentialAddedTaskState.FRONT.equals(this.addTaskPosition)) {
            _userIds.add(taskEntity.getAssignee());
        }

        updateSequentialAddTaskInfo(execution,taskEntity);
        //更新nrOfInstances
        Integer nrOfInstances = this.getLoopVariable(execution, "nrOfInstances");
        nrOfInstances = nrOfInstances + _userIds.size();
        execution.setVariable("nrOfInstances",nrOfInstances);

        //更新集合
        // camunda-TODO
        InputDataItem inputDataItem = _loopCharacteristics.getInputDataItem();
        if (inputDataItem != null) {
            // camunda-TODO
            ArrayList<String> inputDataItemContent= (ArrayList<String>) execution.getVariableNames();
            int currentIndex = this.getLoopVariable(execution, "loopCounter");
            inputDataItemContent.addAll(currentIndex+1, _userIds);
//            execution.setVariable(inputDataItem,inputDataItemContent);
        }

        if (SequentialAddedTaskState.FRONT.equals(this.addTaskPosition)) {
            //结束当前任务
            TaskService taskService = commandContext.getProcessEngineConfiguration().getTaskService();
            Map variables = localVariables == null ? new HashMap<>() : localVariables;
            variables.put(SequentialAddedTaskState.TASK_TYPE_KEY, SequentialAddedTaskState.IGNORE_TASK);
            taskService.setVariablesLocal(taskEntity.getId(), variables);
            if (StringUtils.isNotBlank(comment)) {
                taskService.createComment(taskEntity.getId(),taskEntity.getProcessInstanceId(), comment);
            }
            taskService.complete(taskEntity.getId());
        }
        return null;
    }

    /**
     * 更新串行加签状态
     * @param execution
     * @param taskEntity
     */
    private void updateSequentialAddTaskInfo(ExecutionEntity execution, TaskEntity taskEntity){
        String currentAssignee = taskEntity.getAssignee();
        //串行加签状态
        String sequentialAddedTaskStatesStr = (String) execution.getVariable(SequentialAddedTaskState.STATE_VARIABLE_KEY);
        List<SequentialAddedTaskState> sequentialAddedTaskStates = new ArrayList<>();
        if (StringUtils.isNotBlank(sequentialAddedTaskStatesStr)) {
            sequentialAddedTaskStates = JSON.parseArray(sequentialAddedTaskStatesStr, SequentialAddedTaskState.class);
        }

        //不存在，则初始化
        Integer nrOfInstances = this.getLoopVariable(execution, "nrOfInstances");
        if (sequentialAddedTaskStates.size() == 0){
            for (int i = 0; i <nrOfInstances; i++) {
                sequentialAddedTaskStates.add(new SequentialAddedTaskState(SequentialAddedTaskState.NORMAL_TASK));
            }
        }

        Integer currentIndex = this.getLoopVariable(execution, "loopCounter");
        if (SequentialAddedTaskState.FRONT.equals(this.addTaskPosition)) {
            for (int i = 0; i < _userIds.size(); i++) {

                if(i == _userIds.size()){
                    SequentialAddedTaskState state = new SequentialAddedTaskState(_userIds.get(i), SequentialAddedTaskState.MOVE_TASK);
                    sequentialAddedTaskStates.add(currentIndex +i+1, state);
                }else{
                    SequentialAddedTaskState state = new SequentialAddedTaskState( _userIds.get(i), SequentialAddedTaskState.ADD_TASK);
                    state.setOperator(currentAssignee);
                    sequentialAddedTaskStates.add(currentIndex +i+1, state);
                }
            }
            sequentialAddedTaskStates.get(currentIndex).setTaskType(SequentialAddedTaskState.IGNORE_TASK);
            sequentialAddedTaskStates.get(currentIndex).setUser(currentAssignee);
        }else {
            for (int i = 0; i < _userIds.size(); i++) {
                SequentialAddedTaskState state = new SequentialAddedTaskState(_userIds.get(i), SequentialAddedTaskState.ADD_TASK);
                state.setOperator(currentAssignee);
                sequentialAddedTaskStates.add(currentIndex +i +1, state);
            }


        }
        execution.setVariable(SequentialAddedTaskState.STATE_VARIABLE_KEY,JSON.toJSONString(sequentialAddedTaskStates));
        logger.info("sequential added task states {}",sequentialAddedTaskStates);

    }




    /**
     * 并行加签
     * @param commandContext
     * @return
     */
    private Void parallelAddTask(CommandContext commandContext) {

        ExecutionEntity executionEntity = commandContext.getExecutionManager().findExecutionById(_executionId);

        if (_deptCodes != null) {
            for (String deptCode : _deptCodes) {
                createParallelTask(commandContext, executionEntity, deptCode,false);
            }

        }

        if (_roleCodes != null) {
            for (String roleCode : _roleCodes) {
                createParallelTask(commandContext,  executionEntity, roleCode, false);
            }
        }

        if (_userIds != null) {

            for (String userId : _userIds) {
                createParallelTask(commandContext,  executionEntity, userId, true);
            }
        }
        return null;
    }


    /**
     * 创建并行任务
     * @param commandContext
     * @param executionEntity
     * @param addItem
     * @param isUser
     */
    private void createParallelTask(CommandContext commandContext, ExecutionEntity executionEntity, String addItem, boolean isUser) {
        TaskService taskService = commandContext .getProcessEngineConfiguration().getTaskService();
        RepositoryService repositoryService = commandContext.getProcessEngineConfiguration().getRepositoryService();
        String processDefinitionId = executionEntity.getProcessDefinitionId();
        String activityId = executionEntity.getActivityId();
        ExecutionEntity parent = executionEntity.getParent();
        ExecutionEntity createExecution = parent.createExecution();
        createExecution.setActive(true);
        createExecution.setConcurrent(true);
        createExecution.setScope(false);
        // TaskEntity taskEntity =
        // TaskEntity.createAndInsert(createExecution);
        Task singleResult = taskService.createTaskQuery()
                .executionId(_executionId).singleResult();
        TaskEntity t = (TaskEntity) singleResult;
        // camunda-TODO
        TaskEntity taskEntity = new TaskEntity();
//        TaskEntity taskEntity = TaskEntity.createAndInsert(createExecution);
//        TaskEntity task = new TaskEntity();
//        task.insert();

        taskEntity.setCreateTime(new Date());
        taskEntity.setTaskDefinition(t.getTaskDefinition());
        taskEntity.setProcessDefinitionId(t.getProcessDefinitionId());
        taskEntity.setTaskDefinitionKey(t.getTaskDefinitionKey());
        taskEntity.setProcessInstanceId(t.getProcessInstanceId());
        taskEntity.setExecutionId(createExecution.getId());
        taskEntity.setTenantId(t.getTenantId());
        taskEntity.setName(singleResult.getName());
        if (isUser) {
            taskEntity.setAssignee(addItem);
        }
        
      //  IdGenerator idGenerator = commandContext
        //        .getProcessEngineConfiguration().getIdGenerator();
        //String nextId = idGenerator.getNextId();
        //taskEntity.setId(nextId);
        taskEntity.setExecution(createExecution);
        // camunda-TODO
        taskEntity.insert();
        String elementKey = WorkflowProcessDefineService.getMultiElementKey(repositoryService, processDefinitionId, activityId);
        createExecution.setVariableLocal(elementKey,addItem);
        if (!isUser) {
            taskEntity.setVariableLocal(elementKey, addItem);
        }
        HistoricActivityInstanceManager historyManager = commandContext.getHistoricActivityInstanceManager();
      
        historyManager.insert(this.createHistoricActivityInstanceEntity(createExecution));
 
       
        // camunda-TODO
//        historyManager.recordActivityStart(createExecution);
//        historyManager.recordTaskId(taskEntity);
        int nrOfInstances = getLoopVariable(createExecution, "nrOfInstances");
        setLoopVariable(createExecution, "nrOfInstances", nrOfInstances + 1, true);

        int nrOfActiveInstances = getLoopVariable(createExecution, "nrOfActiveInstances");
        setLoopVariable(createExecution, "nrOfActiveInstances", nrOfActiveInstances + 1, true);

        setLoopVariable(createExecution, "loopCounter", nrOfInstances + 1, false);

        String multiInputDataItem = WorkflowProcessDefineService.getMultiInputDataItem(repositoryService, processDefinitionId, activityId);
        Collection variable = executionEntity.getVariablesInternal();
        variable.add(addItem);
        

        
         //camunda -TODO
         taskEntity.complete();
//        executionEntity.setVariable(multiInputDataItem,variable);
        // camunda-TODO
//        if (Context.getProcessEngineConfiguration().getEventDispatcher().isEnabled()) {
//            Context.getProcessEngineConfiguration().getEventDispatcher().dispatchEvent(ActivitiEventBuilder.createEntityEvent(ActivitiEventType.TASK_CREATED, taskEntity));
//            if (taskEntity.getAssignee() != null) {
//                Context.getProcessEngineConfiguration().getEventDispatcher().dispatchEvent(ActivitiEventBuilder.createEntityEvent(ActivitiEventType.TASK_ASSIGNED, taskEntity));
//            }
//        }
    }

    private HistoricActivityInstanceEntity  createHistoricActivityInstanceEntity(ExecutionEntity executionEntity) {
       // if(isHistoryLevelAtLeast(HistoryLevel.HISTORY_LEVEL_ACTIVITY)) {
        	if(executionEntity.getActivity() != null) {
        		IdGenerator idGenerator = Context.getProcessEngineConfiguration().getIdGenerator();
        		
        		String processDefinitionId = executionEntity.getProcessDefinitionId();
        		String processInstanceId = executionEntity.getProcessInstanceId();
        		String executionId = executionEntity.getId();
        		
        		HistoricActivityInstanceEntity historicActivityInstance = new HistoricActivityInstanceEntity();
        		historicActivityInstance.setId(idGenerator.getNextId());
        		historicActivityInstance.setProcessDefinitionId(processDefinitionId);
        		historicActivityInstance.setProcessInstanceId(processInstanceId);
        		historicActivityInstance.setExecutionId(executionId);
        		historicActivityInstance.setActivityId(executionEntity.getActivityId());
        		historicActivityInstance.setActivityName((String) executionEntity.getActivity().getProperty("name"));
        		historicActivityInstance.setActivityType((String) executionEntity.getActivity().getProperty("type"));
        		historicActivityInstance.setStartTime(new Date());
        		
        	  // Inherit tenant id (if applicable)
            if (executionEntity.getTenantId() != null) {
            	historicActivityInstance.setTenantId(executionEntity.getTenantId());
            }
            return historicActivityInstance;
        	//}   
    
        }
        return  null;
    }
    
    protected void setLoopVariable(ExecutionEntity execution,
			String variableName, Object value,boolean paretnt) {
        if(paretnt){
            ActivityExecution parent = execution.getParent();
            parent.setVariableLocal(variableName, value);
        }else{
            execution.setVariableLocal(variableName,value);
        }
	}

	protected Integer getLoopVariable(ExecutionEntity execution,
			String variableName) {
		Object value = execution.getVariableLocal(variableName);
		ActivityExecution parent = execution.getParent();
		while (value == null && parent != null) {
			value = parent.getVariableLocal(variableName);
			parent = parent.getParent();
		}
		return (Integer) (value != null ? value : 0);
	}

}
