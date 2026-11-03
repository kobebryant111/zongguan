package com.tansun.wf.platform.service;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.cache.UserTaskCache;
import com.tansun.wf.cache.UserTaskCacheQueue;
import com.tansun.wf.platform.model.ActRuIdentitylinkEntity;
import com.tansun.wf.platform.query.ActRuIdentityLinkQuery;
import com.tansun.workflow.model.PostInfo;
import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheQueueService {

    public static final Logger logger = LoggerFactory.getLogger(CacheQueueService.class);

    @Value("${workflow.cacheQuque.enabled:false}")
    private Boolean cacheQueueEnable;

    @Value("${workflow.cacheQuque.appId:#{null}}")
    private String appId;

    @Value("${workflow.cacheQuque.datailUrl:#{null}}")
    private String datailUrl;

    @Autowired
    private TaskService taskService;

    @Autowired
    private WfActHiCommentService wfActHiCommentService;

    public void cacheToUserTaskQueue(String taskId){
        if(!cacheQueueEnable){
            return;
        }
        logger.info("用户人员发生变化进行队列缓存，taskId:{}",taskId);
        // 查询task
        List<Task> tasks = taskService.createTaskQuery().taskId(taskId).listPage(0, 1);
        if(CollectionUtils.isNotEmpty(tasks)){
            Task task = tasks.get(0);

            // 查询link表
            ActRuIdentityLinkQuery actRuIdentityLinkQuery = new ActRuIdentityLinkQuery();
            actRuIdentityLinkQuery.setTaskId(taskId);
            List<ActRuIdentitylinkEntity> candidateUserList = wfActHiCommentService.findCandidateUserList(actRuIdentityLinkQuery);

            List<PostInfo> postInfos = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(candidateUserList)){
                for (ActRuIdentitylinkEntity actRuIdentitylinkEntity : candidateUserList) {
                    PostInfo postInfo = new PostInfo();
                    postInfo.setType(actRuIdentitylinkEntity.getType());
                    postInfo.setUserId(actRuIdentitylinkEntity.getUserId());
                    postInfo.setRoleCode(actRuIdentitylinkEntity.getRoleCode());
                    postInfo.setOrgCode(actRuIdentitylinkEntity.getOrgCode());
                    postInfo.setDeptCode(actRuIdentitylinkEntity.getDeptCode());
                    postInfo.setGroupId(actRuIdentitylinkEntity.getGroupId());
                    postInfos.add(postInfo);
                }
            }

            // 创建queue
            UserTaskCache userTaskCache = new UserTaskCache();
            userTaskCache.setAppId(appId);
            userTaskCache.setDatailUrl(datailUrl);

            userTaskCache.setProcessInstanceId(task.getProcessInstanceId());
            userTaskCache.setTaskId(task.getId());
            userTaskCache.setTaskName(task.getName());
            userTaskCache.setTaskType("update");
            userTaskCache.setProcessDefinitionId(task.getProcessDefinitionId());
            userTaskCache.setActivityId(task.getTaskDefinitionKey());
            userTaskCache.setParticipants(postInfos);

            // 数据插入缓存队列
            UserTaskCacheQueue.getInstance().offer(userTaskCache);
            logger.info("用户人员发生变化进行队列缓存完成，userTaskCache:{}", JSON.toJSONString(userTaskCache));

        }
    }
}
