package com.tansun.wf.extend.behavior;

import com.tansun.wf.activiti.service.EditorServiceProxyService;
import com.tansun.wf.activiti.service.HttpCustomUserService;
import com.tansun.wf.activiti.service.WorkflowProcessDefineService;
import com.tansun.wf.activiti.service.WorkflowTaskService;
import com.tansun.wf.activiti.util.ParticipantUtil;
import com.tansun.wf.camunda.bpmn.impl.instance.MultiWork;
import com.tansun.wf.camunda.bpmn.instance.Participants;
import com.tansun.wf.camunda.bpmn.instance.RuleLogicParam;
import com.tansun.wf.camunda.bpmn.instance.UserTaskParticipant;
import com.tansun.wf.extend.config.MySpringProcessEngineConfiguration;
import com.tansun.workflow.api.IWorkflowCustomUserService;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import com.tansun.workflow.model.PostInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.impl.bpmn.behavior.ParallelMultiInstanceActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.context.ApplicationContext;

import java.util.*;

import static com.tansun.wf.camunda.bpmn.instance.UserTaskParticipant.*;

public class MyParallelMultiInstanceActivityBehavior extends ParallelMultiInstanceActivityBehavior {

    @Override
    public void execute(ActivityExecution execution) throws Exception {
        MySpringProcessEngineConfiguration processEngineConfiguration = (MySpringProcessEngineConfiguration)execution.getProcessEngine().getProcessEngineConfiguration();
        EditorServiceProxyService editorServiceProxyService = processEngineConfiguration.getApplicationContext().getBean(EditorServiceProxyService.class);
        ModelElementInstance modelElementInstance = execution.getBpmnModelInstance().getModelElementById(execution.getCurrentActivityId().split("#")[0]);
        if(!(modelElementInstance instanceof UserTask)){
            return;
        }
        // 用户节点
        UserTask userTask = (UserTask)modelElementInstance;
        // 参与者
        UserTaskParticipant userTaskParticipant = userTask.getExtensionElements().getElementsQuery().filterByType(UserTaskParticipant.class).singleResult();

        String parRadioConfig = userTaskParticipant.getParRadioConfig();
        // 多工作项
        MultiWork multiWork = userTask.getExtensionElements().getElementsQuery().filterByType(MultiWork.class).singleResult();

        if(CONFIG_TYPE_1.equals(parRadioConfig)){
            // 多工作项分配策略
            String disStrategy = multiWork.getDisStrategy();

            Collection<Participants> participants = userTaskParticipant.getParticipants();
            Iterator<Participants> iterator = participants.iterator();
            List<PostInfo> postInfos = new ArrayList<>();
            while(iterator.hasNext()){
                PostInfo postInfo = new PostInfo();
                Participants participant = iterator.next();
                String type = participant.getType();
                postInfo.setType(type);
                switch (type){
                    case Participants.TYPE_ORG:{
                        // 机构
                        postInfo.setOrgCode(ParticipantUtil.getVariableValue(execution.getVariables(),participant.getId()));
                        break;
                    }
                    case Participants.TYPE_ROLE:{
                        // 角色
                        postInfo.setRoleCode(ParticipantUtil.getVariableValue(execution.getVariables(),participant.getId()));
                        break;
                    }
                    case Participants.TYPE_USER:{
                        // 个人
                        postInfo.setUserId(ParticipantUtil.getVariableValue(execution.getVariables(),participant.getId()));
                        break;
                    }
                    case Participants.TYPE_DEPT:{
                        // 部门
                        postInfo.setDeptCode(ParticipantUtil.getVariableValue(execution.getVariables(),participant.getId()));
                        break;
                    }
                    case Participants.TYPE_POST:{
                        // 岗位（角色+机构+部门）
                        String expression = participant.getId();
                        String roleCode = expression.substring(expression.indexOf("@{")+2,expression.indexOf("}{"));
                        String orgCode = expression.substring(expression.indexOf("}{")+2,expression.lastIndexOf("}{"));
                        String deptCode = expression.substring(expression.lastIndexOf("}{")+2,expression.length()-1);
                        postInfo.setRoleCode(ParticipantUtil.getVariableValue(execution.getVariables(),roleCode));
                        postInfo.setOrgCode(ParticipantUtil.getVariableValue(execution.getVariables(),orgCode));
                        postInfo.setDeptCode(ParticipantUtil.getVariableValue(execution.getVariables(),deptCode));
                        break;
                    }
                    default:{
                        // 其他组
                        postInfo.setGroupId(ParticipantUtil.getVariableValue(execution.getVariables(),participant.getId()));
                        break;
                    }
                }
                postInfos.add(postInfo);
            }
            if("1".equals(disStrategy)){
                // 按参与者设置个数领取工作项
                if(CollectionUtils.isEmpty(postInfos)){
                    throw new RuntimeException("多工作项参与者个数为0");
                }
                execution.setVariable("postInfos",postInfos);

            }else if("2".equals(disStrategy)){
                // 按操作员个数领取工作项
                Set<String> users = new HashSet<>();
                for (PostInfo postInfo : postInfos) {
                    EditorInfo editorInfo = new EditorInfo();
                    String type = postInfo.getType();
                    editorInfo.setTenantId(execution.getTenantId());
                    editorInfo.setType(type);
                    switch (type){
                        case Participants.TYPE_ORG:{
                            // 机构
                            List<String> orgCodes = new ArrayList<>();
                            orgCodes.add(postInfo.getOrgCode());
                            editorInfo.setOrgCodes(orgCodes);
                            break;
                        }
                        case Participants.TYPE_ROLE:{
                            // 角色
                            List<String> roleCode = new ArrayList<>();
                            roleCode.add(postInfo.getRoleCode());
                            editorInfo.setRoleCodes(roleCode);
                            break;
                        }
                        case Participants.TYPE_USER:{
                            // 个人 不需要再调用查询
                            users.add(postInfo.getUserId());
                            continue;
                        }
                        case Participants.TYPE_POST:{
                            // 岗位（角色+机构）
                            List<String> roleCode = new ArrayList<>();
                            roleCode.add(postInfo.getRoleCode());
                            editorInfo.setRoleCodes(roleCode);

                            List<String> orgCodes = new ArrayList<>();
                            orgCodes.add(postInfo.getOrgCode());
                            editorInfo.setOrgCodes(orgCodes);
                            break;
                        }
                        default:{
                            // 其他组
                            List<String> roleCode = new ArrayList<>();
                            roleCode.add(postInfo.getRoleCode());
                            editorInfo.setRoleCodes(roleCode);

                            List<String> orgCodes = new ArrayList<>();
                            orgCodes.add(postInfo.getOrgCode());
                            editorInfo.setOrgCodes(orgCodes);

                            List<String> deptCodes = new ArrayList<>();
                            deptCodes.add(postInfo.getDeptCode());
                            editorInfo.setDeptCodes(deptCodes);

                            editorInfo.setGroupId(postInfo.getGroupId());
                            break;
                        }
                    }

                    EditorBean editorBean = editorServiceProxyService.findUsersList(editorInfo);
                    List<Map<String, Object>> userList = editorBean.getUserList();
                    for (Map<String, Object> userMap : userList) {
                        users.add(MapUtils.getString(userMap, "userLoginName"));
                    }
                }
                postInfos = new ArrayList<>();
                for (String user : users) {
                    PostInfo postInfo = new PostInfo();
                    postInfo.setUserId(user);
                    postInfo.setType("user");
                    postInfos.add(postInfo);
                }
                if(CollectionUtils.isEmpty(postInfos)){
                    throw new RuntimeException("多工作项操作员个数为0");
                }
                execution.setVariable("postInfos",postInfos);

            }else if("3".equals(disStrategy)){
                // 按变量迭代创建工作项(迭代变量可在工作项创建后事件中使用)
            }
        }else if(CONFIG_TYPE_3.equals(parRadioConfig)){
            // 流程执行者（其他节点的）
            String activityId = userTaskParticipant.getExecutor();
            // 历史节点
            HistoryService historyService = execution.getProcessEngineServices().getHistoryService();
            HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
            List<HistoricActivityInstance> historicActivityInstances = historicActivityInstanceQuery.processInstanceId(execution.getProcessInstanceId()).activityId(activityId).orderByHistoricActivityInstanceEndTime().desc().list();
            // TODO 如果选择的历史节点是会签节点，那么需要考虑回退的问题，只能去最新一次的历史人员
            if(CollectionUtils.isEmpty(historicActivityInstances)){
                throw new RuntimeException("流程执行者"+activityId+"为空");
            }
            // 时间最近的一个任务
            HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(0);
            // 历史节点是一个多工作项，去多个任务的历史处理人
            if(((UserTask)execution.getBpmnModelInstance().getModelElementById(historicActivityInstance.getActivityId())).getLoopCharacteristics()!=null){
                String parentActivityInstanceId = historicActivityInstance.getParentActivityInstanceId();
                Set<String> userIds = new HashSet<>();
                for (HistoricActivityInstance activityInstance : historicActivityInstances) {
                    if(StringUtils.equals(parentActivityInstanceId,activityInstance.getParentActivityInstanceId())) {
                        userIds.add(activityInstance.getAssignee());
                    }
                }
                List<PostInfo> postInfos = new ArrayList<>();
                for (String userId : userIds) {
                    PostInfo postInfo = new PostInfo();
                    postInfo.setType("user");
                    postInfo.setUserId(userId);
                    postInfos.add(postInfo);
                }
                execution.setVariable("postInfos",postInfos);
            }else{
                // 不是多工作项，就取一个最新任务的历史处理人
                String userId = historicActivityInstance.getAssignee();
                List<PostInfo> postInfos = new ArrayList<>();
                PostInfo postInfo = new PostInfo();
                postInfo.setType("user");
                postInfo.setUserId(userId);
                postInfos.add(postInfo);
                execution.setVariable("postInfos",postInfos);
            }
        }else if(CONFIG_TYPE_6.equals(parRadioConfig)) {

            // 规则逻辑
            String serviceType = userTaskParticipant.getServiceType();
            String serviceValue = userTaskParticipant.getServiceValue();
            Collection<RuleLogicParam> ruleLogicParams = userTaskParticipant.getRuleLogicParams();
            // 参数
            Map<String, Object> param = new HashMap<>();
            for (RuleLogicParam ruleLogicParam : ruleLogicParams) {
                String name = ruleLogicParam.getName();
                String value = ruleLogicParam.getValue();
                String valueType = ruleLogicParam.getValueType();
                if (RuleLogicParam.VALUE_TYPE_CONSTANT.equals(valueType)) {
                    param.put(name, value);
                }
                if (RuleLogicParam.VALUE_TYPE_VARIABLE.equals(valueType)) {
                    param.put(name, execution.getVariable(value));
                }
            }
            // 执行
            List<PostInfo> postInfos = new ArrayList<>();
            ApplicationContext applicationContext = processEngineConfiguration.getApplicationContext();
            if (SERVICE_TYPE_LOCAL.equals(serviceType)) {
                // 本地服务
                IWorkflowCustomUserService bean = applicationContext.getBean(serviceValue, IWorkflowCustomUserService.class);
                postInfos = bean.execute(param);
            } else if (SERVICE_TYPE_ONLINE.equals(serviceType)) {
                // 远程服务
                HttpCustomUserService bean = applicationContext.getBean(HttpCustomUserService.class);
                postInfos = bean.execute(serviceValue, param);
            }
            if(CollectionUtils.isEmpty(postInfos)){
                throw new RuntimeException("规则逻辑服务返回参与者为空");
            }
            execution.setVariable("postInfos",postInfos);
        }
        super.execute(execution);
    }

}
