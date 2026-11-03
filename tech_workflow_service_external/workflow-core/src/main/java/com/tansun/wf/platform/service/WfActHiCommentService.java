package com.tansun.wf.platform.service;

import com.tansun.wf.extend.db.WfIdGeneratorService;
import com.tansun.wf.platform.mapper.WfActHiCommentMapper;
import com.tansun.wf.platform.model.ActHiCommentEntity;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.CommentEntity;
import org.camunda.bpm.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre>保存任务历史内容</pre>
 * @创建时间 2020/6/3
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfActHiCommentService {

    @Autowired
    public ManagementService managementService;

    @Autowired
    private IdGenerator dGenerator;

    @Autowired
    private TaskService taskService;

    @Autowired
    private WfActHiCommentMapper actHiCommentMapper;

    @Autowired
    WfIdGeneratorService wfIdGeneratorService;

    @Autowired
    private ProcessEngine processEngine;


    /**
     * 保存审批意见
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveApproveSuggestionInfo(String taskId,String processInstanceId,String comment){

        List<Comment> comlist= taskService.getTaskComments(taskId);
        ActHiCommentEntity actHiComment = new ActHiCommentEntity();
        actHiComment.setType("comment");
        actHiComment.setTime(new Date());
        actHiComment.setUserId("");
        actHiComment.setTaskId(taskId);
        actHiComment.setProcInstId(processInstanceId);
        actHiComment.setAction("AddComment");
        actHiComment.setMessage(comment);
        actHiComment.setFullMsg(comment);
        if(null == comlist || comlist.size() < 1){
            actHiComment.setId(dGenerator.getNextId());
            this.addApproveSuggestion(actHiComment);
        }else{
            actHiComment.setId(comlist.get(0).getId());
            this.updateApproveSuggestion(actHiComment);
        }

    }



    /**
     * 保存审批意见
     */
    @Transactional(rollbackFor = Exception.class)
    public void addApproveSuggestion(final ActHiCommentEntity actHiComment) {
        // camunda-TODO
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
        executor.execute(new Command<Void>() {
//        managementService.executeCommand(new Command<Void>() {
            @Override
            public Void execute(CommandContext commandContext) {
                //新增
                CommentEntity commentEntity = new CommentEntity();
                commentEntity.setType(actHiComment.getType());
                commentEntity.setTime(actHiComment.getTime());
                commentEntity.setUserId(actHiComment.getUserId());
                commentEntity.setTaskId(actHiComment.getTaskId());
                commentEntity.setProcessInstanceId(actHiComment.getProcInstId());
                commentEntity.setAction(actHiComment.getAction());
                commentEntity.setMessage(actHiComment.getMessage());
                commentEntity.setFullMessage(actHiComment.getFullMsg());
                commentEntity.setId(actHiComment.getId());
                // camunda-TODO
                commandContext.getCommentManager().insert(commentEntity);
                return null;
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateApproveSuggestion(final ActHiCommentEntity actHiComment) {
        // camunda-TODO
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor executor = processEngineConfiguration.getCommandExecutorTxRequired();
        executor.execute(new Command<Void>() {
//        managementService.executeCommand(new Command<Void>() {
            @Override
            public Void execute(CommandContext commandContext) {
                //修改
                CommentEntity commentEntity = new CommentEntity();
                commentEntity.setId(actHiComment.getId());
                commandContext.getCommentManager().delete(commentEntity);
                commentEntity = new CommentEntity();
                commentEntity.setType(actHiComment.getType());
                commentEntity.setTime(actHiComment.getTime());
                commentEntity.setUserId(actHiComment.getUserId());
                commentEntity.setTaskId(actHiComment.getTaskId());
                commentEntity.setProcessInstanceId(actHiComment.getProcInstId());
                commentEntity.setAction(actHiComment.getAction());
                commentEntity.setMessage(actHiComment.getMessage());
                commentEntity.setFullMessage(actHiComment.getFullMsg());
                //新增 重新生成id
                commentEntity.setId(dGenerator.getNextId());
                commandContext.getCommentManager().insert(commentEntity);
                return null;
            }
        });

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCandidateUser(String taskId, String processInstanceId, String[] users ) {
        actHiCommentMapper.deleteByTaskId(taskId);
        List<ActRuIdentitylinkEntity> list = new ArrayList<>();
        for(int i = 0;i<users.length ;i++){
            ActRuIdentitylinkEntity actRuIdentitylinkEntity = new ActRuIdentitylinkEntity();
            actRuIdentitylinkEntity.setId(wfIdGeneratorService.getIdGenerator().getNextId());
            actRuIdentitylinkEntity.setProcInstId(processInstanceId);
            actRuIdentitylinkEntity.setRev(1);
            actRuIdentitylinkEntity.setTaskId(taskId);
            actRuIdentitylinkEntity.setUserId(users[i]);
            actRuIdentitylinkEntity.setType("candidate");
            list.add(actRuIdentitylinkEntity);
        }
        actHiCommentMapper.insertBatch(list);
    }

    public List<ActRuIdentitylinkEntity> findCandidateUserList(ActRuIdentityLinkQuery actRuIdentitylinkQuery) {
        return  actHiCommentMapper.findList(actRuIdentitylinkQuery);
    }

    @Transactional(rollbackFor = Exception.class)
    public  void  insertRuIdentityLink(List<ActRuIdentitylinkEntity> identitylinkEntityList) {
    	for (ActRuIdentitylinkEntity ruIdentitylinkEntity:identitylinkEntityList) {
    	  ruIdentitylinkEntity.setId(wfIdGeneratorService.getIdGenerator().getNextId());
    	}      
    	actHiCommentMapper.insertBatch(identitylinkEntityList);
    	
    }
    
    public  void  insert(ActRuIdentitylinkEntity actRuIdentitylinkEntity) {
    	if (actRuIdentitylinkEntity != null) {
    		actRuIdentitylinkEntity.setId(wfIdGeneratorService.getIdGenerator().getNextId());
    	}
    	actHiCommentMapper.insert(actRuIdentitylinkEntity);
    	
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRuIdentityLink(String taskId) {
        actHiCommentMapper.deleteByTaskId(taskId);
    }
}
