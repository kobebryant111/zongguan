package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.activiti.constants.LeaveTransferConstants;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.manager.WfLeaveTransferManager;
import com.tansun.wf.activiti.model.HiIdentityLinkEntity;
import com.tansun.wf.activiti.model.LeaveTransferEntity;
import com.tansun.wf.activiti.query.WfLeaveTransferQuery;
import com.tansun.wf.activiti.util.DateUtil;
import com.tansun.wf.platform.mapper.ActHiIdentityLinkMapper;
import com.tansun.workflow.model.ClassifyInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import com.tansun.workflow.model.WfLeaveTransfer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.camunda.bpm.engine.impl.ManagementServiceImpl;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 离岗移交服务类
 */
@Service("workflowLeaveTransferService")
@Transactional
public class WorkflowLeaveTransferService extends AbstractManager {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final String SUCCEED = WorkflowConstants.SUCCEED;

	public static final String FAILED = WorkflowConstants.FAILED;

	public static final int NO_PAGE = WorkflowConstants.NO_PAGE;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private WorkflowClassifyService workflowClassifyService;

	@Autowired
	private IdGenerator idGenerator;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private WorkflowTaskService workflowTaskService;

	@Autowired
	private ActHiIdentityLinkMapper actHiIdentityLinkMapper;

	@Autowired
	private ProcessEngine processEngine;

	/**
	 * 离岗移交启动授权
	 */
	public TaskBean startTransfer(final LeaveTransferEntity leaveTransfer) {
	    logger.info("离岗移交自动授权 startTransfer，配置信息：" + JSON.toJSONString(leaveTransfer));
		// 离岗移交配置校验
		if (StringUtils.isBlank(leaveTransfer.getClassifyId())  && StringUtils.isBlank(leaveTransfer.getProcInstId())) {
			logger.info("leaveTransferTimer classifyId &&  procInstId is null , ignore ....." );
			return new TaskBean(FAILED,"离岗移交授权失败，分类或流程为空!");
		}
		if(StringUtils.isBlank(leaveTransfer.getUserId()) || StringUtils.isBlank(leaveTransfer.getTransferId())){
			return new TaskBean(FAILED,"离岗移交授权失败，离岗移交用户集或被交接人用户集为空!");
		}

		logger.info("离岗移交定时器移交任务 startTransfer："
				+ " fromUser：" + leaveTransfer.getUserId()
				+ " toUser" + leaveTransfer.getTransferId()
				+ " workflowConfig : " + JSON.toJSONStringWithDateFormat(leaveTransfer,"yyyy-MM-dd HH:mm:ss")
		);

		// 开始移交待办任务
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override
            public Void execute(CommandContext commandContext) {
                // 获取移交人的待办任务
                final List<Task> tasks = getTransferPendTasks(leaveTransfer.getClassifyId(),leaveTransfer.getProcInstId(),leaveTransfer.getUserId());
                if(CollectionUtils.isNotEmpty(tasks)) {
                    for (Task task : tasks) {
                        TaskEntity taskEntity = (TaskEntity) task;
                        logger.info("startTransfer 开始移交待办任务：" + taskEntity.getId());
                        // start
                        Set<IdentityLink> candidates = taskEntity.getCandidates();
                        // 把 act_ru_identitylink 表中的 userId 抽取出来供后面判断用
                        Set<String> candidateUsers = new HashSet<>();
                        for (IdentityLink candidate : candidates) {
                            candidateUsers.add(candidate.getUserId());
                        }

                        // 参数
                        String userId = leaveTransfer.getUserId();// 移交人
                        String transferId = leaveTransfer.getTransferId();// 交接人
                        String processInstanceId = taskEntity.getProcessInstanceId();
                        String taskId = taskEntity.getId();

                        if (StringUtils.isNotBlank(taskEntity.getAssignee())) {
                            if (LeaveTransferConstants.IS_POWER_YES.equals(leaveTransfer.getIsPower())) {
                                // 有权限的话，处理人从 assignee 转成2条link记录
                                taskService.setAssignee(taskId, null);
                                taskEntity.addCandidateUser(userId);
                                taskEntity.addCandidateUser(transferId);
                            } else {
                                taskService.setAssignee(taskId, transferId);
                                addHistoricIdentityLink(leaveTransfer.getId(), processInstanceId, taskId, userId, LeaveTransferConstants.CANDIDATE_USER);
                            }
                            // 保存 移交人和交接人到 act_hi_identitylink
                            addHistoricIdentityLink(leaveTransfer.getId(), processInstanceId, taskId, transferId, LeaveTransferConstants.CANDIDATE_TRANSFER);
                        } else {
                            // 交接人在link表中是否存在（存在：不添加act_ru_identitylink，不存在：添加记录）
                            if (!candidateUsers.contains(transferId)) {
                                taskEntity.addIdentityLink(transferId, null, IdentityLinkType.CANDIDATE);
                                addHistoricIdentityLink(leaveTransfer.getId(), processInstanceId, taskId, transferId, LeaveTransferConstants.CANDIDATE_TRANSFER);
                            }
                            // 移交人没有权限
                            if (!LeaveTransferConstants.IS_POWER_YES.equals(leaveTransfer.getIsPower())) {
                                // 删除移交人 act_ru_identitylink
                                Iterator<IdentityLink> iterator = candidates.iterator();
                                IdentityLink next = null;
                                while (iterator.hasNext()) {
                                    next = iterator.next();
                                    if (userId.equals(next.getUserId())) {
                                        taskEntity.deleteCandidateUser(userId);
                                        candidateUsers.remove(userId);
                                        break;
                                    }
                                }
                                // 添加移交人到 act_hi_identitylink
                                addHistoricIdentityLink(leaveTransfer.getId(), processInstanceId, taskId, userId, LeaveTransferConstants.CANDIDATE_USER);
                            }
                            candidateUsers.add(transferId);
                            // 若link只剩一个，则要setAssignee中去
                            if (candidateUsers.size() == 1) {
                                taskEntity.deleteCandidateUser(transferId);
                                taskService.setAssignee(taskId, transferId);
                            }
                        }
                        // end
                    }
                }else{
                    logger.info("当前离岗移交配置没有待授权的任务，离岗移交id：" + leaveTransfer.getId());
                }
                return null;
            }
        });
		// 永久移交：可以选择是否移交历史任务
		if(LeaveTransferConstants.IS_TRANSFER_YES.equals(leaveTransfer.getIsTransfer())){
			// 移交历史任务
			if(LeaveTransferConstants.IS_TRANSFER_HISTORICAL_TASKS_YES.equals(leaveTransfer.getIsTransferHistoricalTasks())){
				transferHiTask(leaveTransfer);
			}
		}
		// 离岗移交状态更新为“已执行”
		updateLeaveTransferStartStatus(leaveTransfer.getId(),LeaveTransferConstants.START_STATUS_IS_BEGIN);
		return new TaskBean(SUCCEED,"自动离岗移交成功!");
	}

    /**
     * 离岗移交终止
     */
	public TaskBean stopTransfer(final LeaveTransferEntity leaveTransfer) {
        logger.info("离岗移交终止授权 stopTransfer，配置信息：" + JSON.toJSONString(leaveTransfer));
		// 离岗移交配置校验
		if (StringUtils.isBlank(leaveTransfer.getClassifyId())  && StringUtils.isBlank(leaveTransfer.getProcInstId())) {
			logger.info("leaveTransferTimer classifyId &&  procInstId is null , ignore ....." );
			return new TaskBean(FAILED,"终止授权失败，分类或流程为空!");
		}
		if(StringUtils.isBlank(leaveTransfer.getUserId()) || StringUtils.isBlank(leaveTransfer.getTransferId())){
			return new TaskBean(FAILED,"终止授权失败，离岗移交用户集或被交接人用户集为空!");
		}
		if(LeaveTransferConstants.IS_TRANSFER_YES.equals(leaveTransfer.getIsTransfer())){
			return new TaskBean(FAILED, "永久离岗移交，无法终止");
		}
		if(LeaveTransferConstants.STATUS_INVALID.equals(leaveTransfer.getStatus())){
			return new TaskBean(FAILED, "强制终止失败，该条离岗移交记录已失效");
		}

		logger.info("收回临时转授权任务 "
				+ " fromUsers：" + leaveTransfer.getUserId()
				+ " toUsers" + leaveTransfer.getTransferId()
				+ " workflowConfig : " + JSON.toJSONStringWithDateFormat(leaveTransfer,"yyyy-MM-dd HH:mm:ss")
		);
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
                List<Task> tasks = getTransferPendTasks(leaveTransfer.getClassifyId(),leaveTransfer.getProcInstId(),leaveTransfer.getTransferId());
                // 获取交接人的待办任务
				if (CollectionUtils.isNotEmpty(tasks)) {
					for (Task task: tasks) {
						TaskEntity taskEntity = (TaskEntity)task;
                        logger.info("startTransfer 开始终止授权待办任务：" + taskEntity.getId());
						Set<IdentityLink> candidates = taskEntity.getCandidates();
						// 把 act_ru_identitylink 表中的 userId 抽取出来供后面判断用
						Set<String> candidateUsers = new HashSet<>();
						if(StringUtils.isNotBlank(taskEntity.getAssignee())){
							candidateUsers.add(taskEntity.getAssignee());
						}else{
							for (IdentityLink candidate : candidates) {
								candidateUsers.add(candidate.getUserId());
							}
						}
						// 获取 act_hi_identitylink
						List<HiIdentityLinkEntity> identityLinks = selectHiIdentityLinks(taskEntity.getId());
						for (HiIdentityLinkEntity identityLink : identityLinks) {
							// 使用 groupId 匹配离岗移交 id，保证准确性
							String userId = identityLink.getUserId();
							if(leaveTransfer.getId().equals(identityLink.getGroupId())){
								if(LeaveTransferConstants.CANDIDATE_USER.equals(identityLink.getType())){
									candidateUsers.add(userId);
								}
								if(LeaveTransferConstants.CANDIDATE_TRANSFER.equals(identityLink.getType())){
									taskEntity.deleteCandidateUser(userId);
									candidateUsers.remove(userId);
								}
							}
						}
						if(candidateUsers.size()==1){
							taskEntity.deleteCandidateUser(leaveTransfer.getTransferId());
							taskService.setAssignee(taskEntity.getId(),leaveTransfer.getUserId());
						}else{
							for (String candidateUser : candidateUsers) {
								taskEntity.addCandidateUser(candidateUser);
							}
							taskService.setAssignee(taskEntity.getId(),null);
						}
					}
				}else{
				    logger.info("当前离岗移交配置没有待终止的任务，离岗移交id：" + leaveTransfer.getId());
                }
				return null;
			}
		});
		// 终止授权完成，非永久移交的，离岗移交状态改为失效
		if(LeaveTransferConstants.IS_TRANSFER_NO.equals(leaveTransfer.getIsTransfer())){
			updateLeaveTransferStatus(leaveTransfer.getId(),LeaveTransferConstants.STATUS_INVALID);
		}
		return new TaskBean(SUCCEED, "终止离岗移交成功!");
	}

	/**
	 * 获取移交人目前的所有待办任务
	 * @param
	 * @return
	 */
	public List<Task> getTransferPendTasks(String classifyId, String procInstId, String userId) {
		String conditionClassifySql = "";
		if (StringUtils.isNotBlank(classifyId)) {
			conditionClassifySql = " INNER JOIN ACT_RE_PROCDEF PRO ON RES.PROC_DEF_ID_ = PRO.ID_ AND PRO.CATEGORY_ = '"+classifyId+"' ";
		}
		String conditionProcInstIdSql = "";
		if (StringUtils.isNotBlank(procInstId)) {
			conditionProcInstIdSql = " AND RES.PROC_INST_ID_ = '"+procInstId+"' " ;
		}
		String customSql = "SELECT DISTINCT RES.* FROM ACT_RU_TASK RES " + conditionClassifySql +
				"LEFT JOIN ACT_RU_IDENTITYLINK I ON I.TASK_ID_ = RES.ID_ " + conditionProcInstIdSql +
				"WHERE " +
				"  (" +
				"    RES.ASSIGNEE_ = '"+userId+"' " +
				"    OR ( RES.ASSIGNEE_ IS NULL AND (I.USER_ID_ = '"+userId+"'))" +
				"  )";
		return taskService.createNativeTaskQuery().sql(customSql).list();
	}

	/**
	 * 对已执行定时启动授权的数据添加状态锁
	 * @param id
	 * @param startStatus
	 */
	public void updateLeaveTransferStartStatus(final String id, final String startStatus){
		final LeaveTransferEntity leaveTransferEntity = new LeaveTransferEntity();
		leaveTransferEntity.setId(id);
		leaveTransferEntity.setStartStatus(startStatus);
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				WfLeaveTransferManager wfLeaveTransferManager = commandContext.getSession(WfLeaveTransferManager.class);
				wfLeaveTransferManager.updateLeaveTransferStartStatus(leaveTransferEntity);
				return null;
			}
		});
	}


	/**
	 * 修改定时状态失效或者生效
	 * @param id
	 * @param status
	 */
	public void updateLeaveTransferStatus(final String id,final String status){
		final LeaveTransferEntity leaveTransferEntity = new LeaveTransferEntity();
		leaveTransferEntity.setId(id);
		leaveTransferEntity.setStatus(status);
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				WfLeaveTransferManager wfLeaveTransferManager = commandContext.getSession(WfLeaveTransferManager.class);
				wfLeaveTransferManager.updateLeaveTransferStatus(leaveTransferEntity);
				return null;
			}
		});
	}


	/**
	 * 移交历史任务
	 */
	public void transferHiTask(LeaveTransferEntity leaveTransfer) {
		final String userId = leaveTransfer.getUserId();
		final String transferId = leaveTransfer.getTransferId();
		String classifyId = leaveTransfer.getClassifyId();
		String procInstId = leaveTransfer.getProcInstId();

		HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId);
		if (StringUtils.isNotBlank(procInstId)){
			historicTaskInstanceQuery.processInstanceId(procInstId);
		}
		if (StringUtils.isNotBlank(classifyId)){
			// camunda-TODO camunda没有分类的概念
//			historicTaskInstanceQuery.processCategoryIn(Arrays.asList(classifyId));
		}
		List<HistoricTaskInstance> list = historicTaskInstanceQuery.list();
		final List<String> ids = new ArrayList<>();
		if (CollectionUtils.isEmpty(list)) {
			return;
		} else{
			for (HistoricTaskInstance historicTaskInstance : list) {
				ids.add(historicTaskInstance.getId());
			}
		}
		logger.info("离岗移交定时器移交历史任务 transferHiTask："
				+ " workConfig ： " + JSON.toJSONStringWithDateFormat(leaveTransfer,"yyyy-MM-dd HH:mm:ss")
				+ " fromUsers ：" + userId
				+ " toUsers ：" + transferId
				+ " change task ids ：" + ids
		);
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		executor.execute(new Command<Void>() {
			@Override
			public Void execute(CommandContext commandContext) {
				Map params = new HashMap<>();
				params.put("userId",userId);
				params.put("transferId",transferId);
				params.put("taskIds",ids);
				commandContext.getDbSqlSession().getSqlSession().update("transferHiTask",params);
				return null;
			}
		});

	}

	/**
	 * 通过userId获取离岗移交数据
	 */
	public List<LeaveTransferEntity> getLeaveTransfer(String userId, Date time, String classifyId, String procInstId, String startStatus) {
		return new WfLeaveTransferQuery().userId(userId)
				.time(time).classifyId(classifyId).procInstId(procInstId).startStatus(startStatus).status("0").list();
	}

	/**
	 * 添加历史用户记录
	 * @param leaveTransferId 使用 act_hi_identitylink 表的 groupId 存放离岗移交配置的 id
	 * @param processInstanceId
	 * @param taskId
	 * @param userId
	 * @param type 主要使用 candidate_user 和 candidate_transfer
	 */
	public void addHistoricIdentityLink(String leaveTransferId,String processInstanceId,String taskId,String userId,String type){
		HiIdentityLinkEntity identityLinkEntity = new HiIdentityLinkEntity();
		identityLinkEntity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		identityLinkEntity.setGroupId(leaveTransferId);
		identityLinkEntity.setProcessInstanceId(processInstanceId);
		identityLinkEntity.setTaskId(taskId);
		identityLinkEntity.setType(type);
		identityLinkEntity.setUserId(userId);
		actHiIdentityLinkMapper.insertHiIdentityLink(identityLinkEntity);
	}

	/**
	 * 根据 taskId 查询 act_hi_identitylink 表的数据
	 * @param taskId
	 * @return
	 */
	public List<HiIdentityLinkEntity> selectHiIdentityLinks(final String taskId){
		if(StringUtils.isBlank(taskId)){
			return new ArrayList<>();
		}
		// camunda-TODO 重新写一个条件vo，放到mapper文件里
		return new ArrayList<>();
//		ListQueryParameterObject param = new ListQueryParameterObject();
//		return actHiIdentityLinkMapper.selectHiIdentityLinks();
//		return managementService.executeCommand(new Command<List<HiIdentityLinkEntity>>() {
//			@Override
//			public List<HiIdentityLinkEntity> execute(CommandContext commandContext) {
//				HiIdentityLinkManager hiIdentityLinkManager = commandContext.getSession(HiIdentityLinkManager.class);
//				return hiIdentityLinkManager.selectHiIdentityLinks(taskId,null,null,null);
//			}
//		});
	}

	/**
	 * 查询 act_hi_identitylink 表的数据
	 * @return
	 */
	public List<HiIdentityLinkEntity> selectHiIdentityLinks(final String taskId,final String userId,final String groupId,final String type){
		if(StringUtils.isBlank(taskId)){
			return new ArrayList<>();
		}
		return new ArrayList<>();
//		actHiIdentityLinkMapper.selectHiIdentityLinks()
		// camunda-TODO 去掉上条注释
//		return managementService.executeCommand(new Command<List<HiIdentityLinkEntity>>() {
//			@Override
//			public List<HiIdentityLinkEntity> execute(CommandContext commandContext) {
//				HiIdentityLinkManager hiIdentityLinkManager = commandContext.getSession(HiIdentityLinkManager.class);
//				return hiIdentityLinkManager.selectHiIdentityLinks(taskId,userId,groupId,type);
//			}
//		});
	}

	/**
	 * 校验是否存在离岗移交,如果存在则进行离岗操作
	 * @param entity
	 * @param users
	 */
	public Set getUsersWhenCreateTaskAndLeave(TaskEntity entity, Set<String> users){
		String processDefinitionId = entity.getProcessDefinitionId();
		ProcessDefinition processDefinition =
				repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String classifyId = processDefinition.getCategory();
		String procInstId = entity.getProcessInstanceId();
		//存在候选用户场景下
		List<LeaveTransferEntity> allList = this.getAllLeaveTransfer();

		List<LeaveTransferEntity> workConfig = new ArrayList<>();
		HashSet newUsers = new HashSet();
		for(String user : users){
			if(StringUtils.isBlank(user)){
				continue;
			}
			//分类模式
			List<LeaveTransferEntity> leaveTransferEntities = getLeaveTransferFromList(user, new Date(), classifyId, null, LeaveTransferConstants.STATUS_VALID,null, allList);
			if (CollectionUtils.isEmpty(leaveTransferEntities)) {
				//流程实例模式
				leaveTransferEntities = getLeaveTransferFromList(user, new Date(), null, procInstId, LeaveTransferConstants.STATUS_VALID,null, allList);
			}
			if (CollectionUtils.isEmpty(leaveTransferEntities)) {
				// 吃瓜群众
				newUsers.add(user);
			}else{
				workConfig.addAll(leaveTransferEntities);
				for(LeaveTransferEntity leaveTransfer : leaveTransferEntities){
					// 失效的配置不执行
					if(!LeaveTransferConstants.STATUS_VALID.equals(leaveTransfer.getStatus())){
						continue;
					}
					String transferId = leaveTransfer.getTransferId();
					String userId = leaveTransfer.getUserId();
					String processInstanceId = entity.getProcessInstanceId();
					String taskId = entity.getId();

					if (!users.contains(transferId)) {
						newUsers.add(transferId);
						addHistoricIdentityLink(leaveTransfer.getId(),processInstanceId, taskId,transferId, LeaveTransferConstants.CANDIDATE_TRANSFER);
					}
					if(LeaveTransferConstants.IS_POWER_NO.equals(leaveTransfer.getIsPower())){
						// 移交人没有权限，插入 hiLink 表
						addHistoricIdentityLink(leaveTransfer.getId(),processInstanceId, taskId,userId, LeaveTransferConstants.CANDIDATE_USER);
					}else{
						// 移交人有权限
						newUsers.add(user);
					}
				}
			}
		}

		logger.info("=============addCandidateWhenCreateTask方法，进行离岗移交配置分析："
				+ " 当前节点id：" + entity.getId()
				+ " 当前节点处理人：" + entity.getAssignee()
				+ " 待设置候选处理人：" + users
				+ " 当前实例id：" + entity.getProcessInstanceId()
				+ " 生效的离岗移交配置：" + JSON.toJSONStringWithDateFormat(workConfig,"yyyy-MM-dd HH:mm:ss")
		);
		return newUsers;
	}

	/**
	 * 获取数据库中所有的离岗移交配置信息
	 * @return
	 */
	public List<LeaveTransferEntity> getAllLeaveTransfer() {
		List<LeaveTransferEntity> list = new WfLeaveTransferQuery().list();
		return list == null ? new ArrayList<LeaveTransferEntity>() : list;
	}

	/**
	 * 终止离岗移交时根据 ids 获取离岗移交配置
	 * @return
	 */
	public List<LeaveTransferEntity> getLeaveTransfersByIds(String[] ids) {
		List<LeaveTransferEntity> list = new WfLeaveTransferQuery().ids(ids).list();
		return list == null ? new ArrayList<LeaveTransferEntity>() : list;
	}

	/**
	 * 通过userId获取离岗移交数据
	 */
	public List<LeaveTransferEntity> getLeaveTransferFromList(String userId, Date time, String classifyId, String procInstId, String status, String startStatus,List<LeaveTransferEntity> list) {
		List<LeaveTransferEntity> result = new ArrayList<>();
		if (list == null) {
			return result;
		}

		for (LeaveTransferEntity item : list) {
			if(StringUtils.isNotBlank(userId) && !userId.equals(item.getUserId())){
				continue;
			}
			if(StringUtils.isNotBlank(classifyId) && !classifyId.equals(item.getClassifyId())){
				continue;
			}
			if(StringUtils.isNotBlank(procInstId) && !procInstId.equals(item.getProcInstId())){
				continue;
			}
			if(StringUtils.isNotBlank(status) && !status.equals(item.getStatus())){
				continue;
			}
			if(StringUtils.isNotBlank(startStatus) && !startStatus.equals(item.getStartStatus())){
				continue;
			}
			if(time != null && !(time.after(item.getStartTime()) && time.before(item.getEndTime()))){
				continue;
			}
			result.add(item);
		}
		return result;
	}

	/**
	 * 非backToGroup 即不是在回退至岗位池的场景下
	 * 1、历史处理人为代理人
	 * 2、历史处理人移交给其他交接人
	 * 3、无移交
	 * @param entity
	 */
	public Set createTaskWhenBackToUser(TaskEntity entity,HistoricTaskInstance hiTask){
		// 1.原先 a -> b，b代理审批了，此时离岗移交移交失效了，应回退至 a

		// a,b  -> c，c代理审批了，此时a失效了，b没有失效，退回a，c
		// a,b  -> c，c代理审批了，此时a和b失效了，退回给a，b
		// a,b  -> c，c代理审批了，此时a和b都没有失效，退回给c

		Set<String> newUsers = new HashSet<>();
		String processInstanceId = entity.getProcessInstanceId();
		String taskId = entity.getId();
		String assignee = hiTask.getAssignee();
		// 判断是不是失效的离岗移交
		List<HiIdentityLinkEntity> identityLinks = selectHiIdentityLinks(hiTask.getId(), assignee, null, LeaveTransferConstants.CANDIDATE_TRANSFER);
		if(CollectionUtils.isNotEmpty(identityLinks)){
			List<LeaveTransferEntity> allLeaveTransfer = getAllLeaveTransfer();
			for (HiIdentityLinkEntity identityLink : identityLinks) {
				for (LeaveTransferEntity leaveTransfer : allLeaveTransfer) {
					// 匹配离岗移交配置id
					if(leaveTransfer.getId().equals(identityLink.getGroupId())){
						// 配置失效，退回到移交人
						if(LeaveTransferConstants.STATUS_INVALID.equals(leaveTransfer.getStatus())){
							newUsers.add(leaveTransfer.getUserId());
						}else{
							// 没有失效，退回给交接人
							newUsers.add(leaveTransfer.getTransferId());
							// 保存 移交人和交接人到 act_hi_identitylink
							addHistoricIdentityLink(leaveTransfer.getId(),processInstanceId, taskId,leaveTransfer.getUserId(), LeaveTransferConstants.CANDIDATE_USER);
							addHistoricIdentityLink(leaveTransfer.getId(),processInstanceId, taskId,leaveTransfer.getTransferId(), LeaveTransferConstants.CANDIDATE_TRANSFER);
						}
					}
				}
			}
		}else{
			// 2.没有移交过，历史处理人是 a，此时 a 移交给了 b，则退回给 b
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(entity.getProcessDefinitionId()).singleResult();
			String classifyId = processDefinition.getCategory();
			// 找出历史处理人
			// 分类模式（有生效的离岗移交配置）
			List<LeaveTransferEntity> leaveTransferEntities = getLeaveTransfer(assignee, new Date(), classifyId, null,null);
			if (CollectionUtils.isEmpty(leaveTransferEntities)) {
				// 流程实例模式
				leaveTransferEntities = getLeaveTransfer(assignee, new Date(), null, entity.getProcessInstanceId(),null);
			}
			if (CollectionUtils.isNotEmpty(leaveTransferEntities)) {
				LeaveTransferEntity leaveTransfer = leaveTransferEntities.get(0);
				String userId = leaveTransfer.getUserId();
				String transferId = leaveTransfer.getTransferId();
				// 添加交接人
				newUsers.add(transferId);
				addHistoricIdentityLink(leaveTransfer.getId(),processInstanceId, taskId,transferId, LeaveTransferConstants.CANDIDATE_TRANSFER);
				if(LeaveTransferConstants.IS_POWER_YES.equals(leaveTransfer.getIsPower())){
					// 移交人有有权限
					newUsers.add(userId);
				}else{
					addHistoricIdentityLink(leaveTransfer.getId(),processInstanceId, taskId,userId, LeaveTransferConstants.CANDIDATE_USER);
				}
			}
		}
		// 以上都不满足，默认退回给历史处理人
		if(CollectionUtils.isEmpty(newUsers)){
			newUsers.add(hiTask.getAssignee());
		}
		return newUsers;
	}

	/**
	 * 新增和更新离岗移交设置
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public TaskBean saveLeaveTransfer(WfLeaveTransfer leaveTransfer) {
		// 字段校验
		TaskBean taskBean = checkField(leaveTransfer);
		if(FAILED.equals(taskBean.getRetCode())){
			return taskBean;
		}
		// 时间校验
		taskBean = checkData(leaveTransfer);
		if (FAILED.equals(taskBean.getRetCode())) {
			return taskBean;
		}
		if ("1".equals(leaveTransfer.getSetStatus()) || "2".equals(leaveTransfer.getSetStatus())){
			String nextId = this.save(leaveTransfer);
			TaskBean result = new TaskBean(SUCCEED, "保存离岗移交成功");
			result.setTransNo(nextId);
			return result;
		}else{
			//获取所有分类
			ClassifyInfo condition = new ClassifyInfo();
			condition.setCurrentPage("1");
			condition.setPageSize(String.valueOf(Integer.MAX_VALUE));
			List<ClassifyInfo> classifyList = workflowClassifyService.findClassifyList(condition);
			if(!org.springframework.util.CollectionUtils.isEmpty(classifyList)){
				List<WfLeaveTransfer> leaveTransfers = new ArrayList<>();
				for (ClassifyInfo classifyInfo : classifyList) {
					//过滤被删除的分类或不可用分类
					if("1".equals(classifyInfo.getDel_flag()) || "0".equals(classifyInfo.getUSEABLE())){
						continue;
					}
					WfLeaveTransfer item = new WfLeaveTransfer();
					BeanUtils.copyProperties(leaveTransfer, item);
					item.setClassifyId(classifyInfo.getId());
					item.setClassifyName(classifyInfo.getName());
					leaveTransfers.add(item);
				}
				List<String> ids = new ArrayList<>();
				for (WfLeaveTransfer model : leaveTransfers) {
					String id = this.save(model);
					ids.add(id);
				}
				String join = org.apache.commons.lang3.StringUtils.join(ids, ",");
				TaskBean result = new TaskBean(SUCCEED, "保存离岗移交成功");
				result.setTransNo(join);
				return result;
			}else{
				return new TaskBean(SUCCEED, "保存离岗移交成功,暂时未找到流程分类");
			}
		}
	}

	/**
	 * 字段设置校验
	 * @param leaveTransfer
	 * @return
	 */
	private TaskBean checkField(WfLeaveTransfer leaveTransfer) {
		if ("1".equals(leaveTransfer.getSetStatus()) && StringUtils.isBlank(leaveTransfer.getClassifyId())) {
			return new TaskBean(FAILED,"保存失败，流程分类未设置");
		}
		if ("2".equals(leaveTransfer.getSetStatus()) && StringUtils.isBlank(leaveTransfer.getProcInstId())) {
			return new TaskBean(FAILED,"保存失败，流程实例ID未设置");
		}
		if(StringUtils.isBlank(leaveTransfer.getClassifyId()) && StringUtils.isBlank(leaveTransfer.getProcInstId())){
			return new TaskBean(FAILED,"保存失败，未选择流程分类设置或流程实例设置");
		}
		if (StringUtils.isBlank(leaveTransfer.getUserId())) {
			return new TaskBean(FAILED,"保存失败，移交人未设置");
		}
		if (StringUtils.isBlank(leaveTransfer.getTransferId())) {
			return new TaskBean(FAILED,"保存失败，交接人未设置");
		}
		if (leaveTransfer.getStartTime() == null
				||"".equals(leaveTransfer.getStartTime().toString())) {
			return new TaskBean(FAILED,"保存失败，起始时间未设置");
		}
		if (leaveTransfer.getEndTime() == null
				||"".equals(leaveTransfer.getEndTime().toString())) {
			return new TaskBean(FAILED,"保存失败，结束时间未设置");
		}
		if (StringUtils.isBlank(leaveTransfer.getStatus())) {
			return new TaskBean(FAILED,"保存失败，是否启用状态未设置");
		}
		if(leaveTransfer.getUserId().equals(leaveTransfer.getTransferId())){
			return new TaskBean(FAILED,"保存失败，处理人与交接人一致");
		}
		return new TaskBean(SUCCEED,"");
	}

	/**
	 * 与历史记录校验
	 */
	public TaskBean checkHiDate(WfLeaveTransfer wfLeaveTransfer, WfLeaveTransfer hiWfLeaveTransfer) {
		Date startTime = wfLeaveTransfer.getStartTime();
		Date endTime = wfLeaveTransfer.getEndTime();
		Date hiStartTime = hiWfLeaveTransfer.getStartTime();
		Date hiEndTime = hiWfLeaveTransfer.getEndTime();
		if (startTime.getTime() == hiStartTime.getTime()){
			return new TaskBean(FAILED,"保存失败，起始时间与已保存记录的起始时间一致");
		}
		if (startTime.getTime() == hiEndTime.getTime()){
			return new TaskBean(FAILED,"保存失败，起始时间与已保存记录的结束时间一致");
		}
		if (endTime.getTime() == hiStartTime.getTime()){
			return new TaskBean(FAILED,"保存失败，结束时间与已保存记录的起始时间一致");
		}
		if (endTime.getTime() == hiEndTime.getTime()) {
			return new TaskBean(FAILED,"保存失败，结束时间与已保存记录的结束时间一致");
		}
		if (startTime.after(hiEndTime)) {
			// 开始时间在历史结束时间之后
			return new TaskBean(SUCCEED,"校验成功");
		} else if (endTime.before(hiStartTime)){
			// 结束时间在历史开始时间之前
			return new TaskBean(SUCCEED,"校验成功");
		} else {
			return new TaskBean(FAILED,"保存失败,临时转授权时间段出现重叠，重叠时间为 : " + DateUtil.formatDate(hiStartTime) + " 至 " + DateUtil.formatDate(hiEndTime));
		}
	}

	/**
	 * 新数据基本自检
	 * @param newStartTime
	 * @param newEndTime
	 * @return
	 */
	public TaskBean checkDate(Date newStartTime, Date newEndTime){
		if (newStartTime.equals(newEndTime) || newStartTime.after(newEndTime)) {
			return new TaskBean(FAILED,"保存失败，起始时间必须在结束时间之前");
		}
		Date nowDate = new Date();
		if (newStartTime.before(nowDate)) {
			return new TaskBean(FAILED,"保存失败，起始时间小于当前时间");
		}
		return new TaskBean(SUCCEED,"新数据基本自检");
	}

	/**
	 * 时间校验
	 */
	public TaskBean checkData(WfLeaveTransfer wfLeaveTransfer) {
		Date newStartTime = wfLeaveTransfer.getStartTime();
		Date newEndTime = wfLeaveTransfer.getEndTime();
		String newStatus = wfLeaveTransfer.getStatus();
		//已执行的离岗移交操作无法修改
		TaskBean taskBean = checkWorkedData(wfLeaveTransfer);
		if (FAILED.equals(taskBean.getRetCode())) {
			return taskBean;
		};
		//正在执行离岗操作不得修改
		taskBean = checkWorkingData(wfLeaveTransfer);
		if (FAILED.equals(taskBean.getRetCode())) {
			return taskBean;
		};
		if ("1".equals(newStatus)) {
			return new TaskBean(SUCCEED,"时间校验成功");
		}
		taskBean = checkDate(newStartTime, newEndTime);
		if (FAILED.equals(taskBean.getRetCode())) {
			return taskBean;
		}
		taskBean = checkUpdateData(wfLeaveTransfer);
		if (FAILED.equals(taskBean.getRetCode())) {
			return taskBean;
		}
		//验证流程设置和分类设置交集
		taskBean = checkProcessAndClassifyData(wfLeaveTransfer);
		if (FAILED.equals(taskBean.getRetCode())) {
			return taskBean;
		}
		return new TaskBean(SUCCEED,"");
	}

	/**
	 * 保存或更新到数据库
	 * @param leaveTransfer
	 * @return
	 */
	private String save(final WfLeaveTransfer leaveTransfer) {
		final LeaveTransferEntity leaveTransferEntity = new LeaveTransferEntity();
		BeanUtils.copyProperties(leaveTransfer, leaveTransferEntity);
		// camunda-TODO
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
		return executor.execute(new Command<String>() {
			@Override
			public String execute(CommandContext commandContext) {
				String nextId = null;
				if (StringUtils.isBlank(leaveTransfer.getId())) {
					nextId = idGenerator.getNextId();
					leaveTransferEntity.setId(nextId);
					// 添加新配置时，执行状态初始化为0：未执行
					leaveTransferEntity.setStartStatus(LeaveTransferConstants.START_STATUS_UN_BEGIN);
					commandContext.getDbEntityManager().insert(leaveTransferEntity);
				} else {
					commandContext.getDbEntityManager().forceUpdate(leaveTransferEntity);
				}
				return nextId;
			}
		});
	}

	/**
	 * 校验流程设置和流程分类设置交集
	 */
	public TaskBean checkProcessAndClassifyData(WfLeaveTransfer wfLeaveTransfer) {
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setUserId(wfLeaveTransfer.getUserId());
		taskInfo.setTaskPoolStatus("0");
		taskInfo.setState("1");
		if ("1".equals(wfLeaveTransfer.getSetStatus()) && "0".equals(wfLeaveTransfer.getStatus())) {
			TaskBean taskBean = workflowTaskService.findPendTaskList(taskInfo);
			List<Map> tasks = taskBean.getTaskList();
			for(Map m:tasks){
				String processInstanceId = m.get("processInstanceId") == null?"":m.get("processInstanceId").toString();
				WfLeaveTransfer wf = new WfLeaveTransfer();
				wf.setProcInstId(processInstanceId);
				wf.setUserId(wfLeaveTransfer.getUserId());
				wf.setStatus("0");
				List<WfLeaveTransfer> hiWfLeaveTransfers = findList(wf);
				for(WfLeaveTransfer hiWfLeaveTransfer : hiWfLeaveTransfers){
					TaskBean checkBean = checkHiDate(wfLeaveTransfer, hiWfLeaveTransfer);
					if (FAILED.equals(checkBean.getRetCode())) {
						return checkBean;
					};
				}
			}
		}

		if ("2".equals(wfLeaveTransfer.getSetStatus()) && "0".equals(wfLeaveTransfer.getStatus())) {
			ProcessInstance processInstance =
					runtimeService.createProcessInstanceQuery().processInstanceId(wfLeaveTransfer.getProcInstId()).singleResult();
			String processDefinitionId = null;
			if (processInstance == null || StringUtils.isBlank(processDefinitionId)) {
				HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(wfLeaveTransfer.getProcInstId()).singleResult();
				if (historicProcessInstance == null ){
					return new TaskBean(FAILED,"运行中流程和已完成的流程都找不到该流程实例ID");
				}else{
					processDefinitionId = historicProcessInstance.getProcessDefinitionId();
				}
			}else{
				processDefinitionId = processInstance.getProcessDefinitionId();
			}
			ProcessDefinition processDefinition =
					repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
			String classifyId = processDefinition.getCategory();
			WfLeaveTransfer wf = new WfLeaveTransfer();
			wf.setUserId(wfLeaveTransfer.getUserId());
			wf.setClassifyId(classifyId);
			wf.setStatus("0");
			List<WfLeaveTransfer> hiWfs = findList(wf);
			for(WfLeaveTransfer hiWf:hiWfs){
				TaskBean taskBean = checkHiDate(wfLeaveTransfer, hiWf);
				if (FAILED.equals(taskBean.getRetCode())) {
					return taskBean;
				};
			}
		}
		return new TaskBean(SUCCEED,"校验流程设置和流程分类设置交集");
	}

	/**
	 * 已执行的离岗移交操作无法修改
	 */
	public TaskBean checkWorkedData(WfLeaveTransfer wfLeaveTransfer){
		String id = wfLeaveTransfer.getId();
		if (StringUtils.isNotBlank(id)) {
			WfLeaveTransfer leaveTransfer = new WfLeaveTransfer();
			leaveTransfer.setId(wfLeaveTransfer.getId());
			List<WfLeaveTransfer> hiWfLeaveTransfers = findList(leaveTransfer);
			for(WfLeaveTransfer wf : hiWfLeaveTransfers){
				if (!"1".equals(wf.getStartStatus())) {
					return new TaskBean(FAILED,"保存失败，不能修改已执行过的离岗移交设置");
				}
			}
		}
		return new TaskBean(SUCCEED,"校验成功");
	}

	public List<WfLeaveTransfer> findList(WfLeaveTransfer wf) {
		WfLeaveTransferQuery wfLeaveTransferQuery = new WfLeaveTransferQuery();
        if (this.managementService instanceof ManagementServiceImpl) {
        	ManagementServiceImpl  manageServiceImpl = (ManagementServiceImpl) this.managementService;
        	 wfLeaveTransferQuery.setCommandExecutor(manageServiceImpl.getCommandExecutor());
        }
       
		wfLeaveTransferQuery.classifyId(wf.getClassifyId());
		wfLeaveTransferQuery.userId(wf.getUserId());
		wfLeaveTransferQuery.transferId(wf.getTransferId());
		wfLeaveTransferQuery.status(wf.getStatus());
		wfLeaveTransferQuery.startStatus(wf.getStartStatus());
		wfLeaveTransferQuery.setStatus(wf.getSetStatus());
		wfLeaveTransferQuery.procInstId(wf.getProcInstId());
		wfLeaveTransferQuery.id(wf.getId());
		List<LeaveTransferEntity> list = wfLeaveTransferQuery.list();
		List<WfLeaveTransfer> list1 = new ArrayList<>();
		if (!CollectionUtils.isEmpty(list)){
			for (LeaveTransferEntity leaveTransferEntity : list) {
				WfLeaveTransfer record = new WfLeaveTransfer();
				BeanUtils.copyProperties(leaveTransferEntity,record);
				list1.add(record);
			}
		}
		return list1;
	}

	public TaskBean checkWorkingData(WfLeaveTransfer wfLeaveTransfer){
		if (StringUtils.isNotBlank(wfLeaveTransfer.getId())) {
			WfLeaveTransfer Wf = new WfLeaveTransfer();
			Wf.setId(wfLeaveTransfer.getId());
			List<WfLeaveTransfer> hiWfs = findList(Wf);
			if (hiWfs.size() == 0) {
				return new TaskBean(FAILED,"保存失败，请清除缓存，重新加载");
			}
			for(WfLeaveTransfer hiWf:hiWfs){
				TaskBean taskBean = checkWorkingDataTime(hiWf);
				if (FAILED.equals(taskBean.getRetCode())) {
					return taskBean;
				};
			}
		}
		return new TaskBean(SUCCEED,"");
	}

	public TaskBean checkWorkingDataTime(WfLeaveTransfer hiWf){
		Date startTime = hiWf.getStartTime();
		Date endTime = hiWf.getEndTime();
		Date nowDate = new Date();
		//校验当前时间是否正在离岗执行时间
		if ((nowDate.after(startTime) && nowDate.before(endTime))
				|| (nowDate.equals(startTime) || nowDate.equals(endTime))) {
			if ("1".equals(hiWf.getStatus())) {
				return new TaskBean(FAILED,"修改失败，当前时间不得在起始时间与结束时间之间");
			}
			return new TaskBean(FAILED,"修改失败，该条数据正在执行中或者当前");
		}
		return new TaskBean(SUCCEED,"");
	}

	/**
	 * 修改
	 */
	public TaskBean checkUpdateData(WfLeaveTransfer wfLeaveTransfer){
		WfLeaveTransfer hiWfLeaveTransfer = new WfLeaveTransfer();
		if ("1".equals(wfLeaveTransfer.getSetStatus())) {
			hiWfLeaveTransfer.setClassifyId(wfLeaveTransfer.getClassifyId());
		}else{
			hiWfLeaveTransfer.setProcInstId(wfLeaveTransfer.getProcInstId());
		}
		hiWfLeaveTransfer.setUserId(wfLeaveTransfer.getUserId());
		hiWfLeaveTransfer.setTransferId(wfLeaveTransfer.getTransferId());
		hiWfLeaveTransfer.setSetStatus(wfLeaveTransfer.getSetStatus());
		hiWfLeaveTransfer.setStatus(wfLeaveTransfer.getStatus());
		List<WfLeaveTransfer> hiWfs = findList(hiWfLeaveTransfer);
		//修改的本条数据classifyId变与不变逻辑一致
		for(WfLeaveTransfer wf:hiWfs){
			if (!wf.getId().equals(wfLeaveTransfer.getId())) {
				TaskBean taskBean = checkHiDate(wfLeaveTransfer,wf);
				if (FAILED.equals(taskBean.getRetCode())) {
					return taskBean;
				};
			}
		}
		return new TaskBean(SUCCEED,"修改成功");
	}
}