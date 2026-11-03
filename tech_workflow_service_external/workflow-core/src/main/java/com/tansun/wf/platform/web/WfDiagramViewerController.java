package com.tansun.wf.platform.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 工作流流程设计器
 */
@RestController
public class WfDiagramViewerController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    protected ObjectMapper objectMapper = new ObjectMapper();


    @RequestMapping(value = "/diagram-viewer/{processInstanceId}/highlights", method = RequestMethod.GET, produces = "application/json")
    public ObjectNode getHighlighted(@PathVariable String processInstanceId) {

        ObjectNode responseJSON = objectMapper.createObjectNode();
        responseJSON.put("processInstanceId", processInstanceId);

        ArrayNode activitiesArray = objectMapper.createArrayNode();
        ArrayNode flowsArray = objectMapper.createArrayNode();

        ArrayNode historyActivities = objectMapper.createArrayNode();
        try {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String processDefinitionId = null;
            if(processInstance != null){
                processDefinitionId = processInstance.getProcessDefinitionId();
            }else{
                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                if(historicProcessInstance == null){
                    return responseJSON;
                }else{
                    processDefinitionId = historicProcessInstance.getProcessDefinitionId();
                }
            }

            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);

            responseJSON.put("processDefinitionId", processDefinitionId);
            List<String> highLightedActivities = new ArrayList<>();
            if(processInstance != null){
                highLightedActivities = runtimeService.getActiveActivityIds(processInstanceId);
                if(CollectionUtils.isEmpty(highLightedActivities)){
                    List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).unfinished().list();
                    if(CollectionUtils.isNotEmpty(list)){
                        for (HistoricActivityInstance historicActivityInstance : list) {
                            if(!"userTask".equals(historicActivityInstance.getActivityType())){
                                highLightedActivities.add(historicActivityInstance.getActivityId());
                            }
                        }
                    }

                    List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
                    if(CollectionUtils.isNotEmpty(tasks)){
                        for (Task task : tasks) {
                            highLightedActivities.add(task.getTaskDefinitionKey());
                        }
                    }

                }
            }
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().finished().processInstanceId(processInstanceId).list();
            if(CollectionUtils.isNotEmpty(list)){
                for (HistoricActivityInstance historicActivityInstance : list) {
                    if ("multiInstanceBody".equals(historicActivityInstance.getActivityType())) {
                        //会签节点camunda会自己生成一个节点id+#multiInstanceBody的虚拟节点，导致前端设计器报错，需要过滤掉
                        continue;
                    }
                    historyActivities.add(historicActivityInstance.getActivityId());
                }
            }

            // 获取需要高亮的线条id
            List<String> highLightedFlows = getHighLightedFlows(processDefinition, processInstanceId);

            for (String activityId : highLightedActivities) {
                activitiesArray.add(activityId);
            }

            for (String flow : highLightedFlows) {
                flowsArray.add(flow);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        responseJSON.set("activities", activitiesArray);
        responseJSON.set("historyActivities", historyActivities);
        responseJSON.set("flows", flowsArray);

        return responseJSON;
    }


    /**
     * 获取需要高亮的线条id
     *
     * @param processDefinition
     * @param processInstanceId
     * @return
     */
    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinition, String processInstanceId) {

        // 线条id容器
        List<String> highLightedFlows = new ArrayList<String>();
        // 获取各种历史节点 activity instance
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                // 默认按时节点开始时间排序，如果开始时间相同，然后在按照结束时间排序
                .orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc()
                .list();

        LinkedList<HistoricActivityInstance> hisActInstList = new LinkedList<HistoricActivityInstance>();
        hisActInstList.addAll(historicActivityInstances);

        getHighlightedFlows(processDefinition.getActivities(), hisActInstList, highLightedFlows);

        return highLightedFlows;
    }

    /**
     * getHighlightedFlows
     * <p>
     * code logic:
     * 1. Loop all activities by id asc order;
     * 2. Check each activity's outgoing transitions and eventBoundery outgoing transitions, if outgoing transitions's destination.id is in other executed activityIds, add this transition to highLightedFlows List;
     * 3. But if activity is not a parallelGateway or inclusiveGateway, only choose the earliest flow.
     *
     * @param activityList
     * @param hisActInstList
     * @param highLightedFlows
     */
    private void getHighlightedFlows(List<ActivityImpl> activityList, LinkedList<HistoricActivityInstance> hisActInstList, List<String> highLightedFlows) {

        //check out startEvents in activityList
        List<ActivityImpl> startEventActList = new ArrayList<ActivityImpl>();
        Map<String, ActivityImpl> activityMap = new HashMap<String, ActivityImpl>(activityList.size());
        // 循环流程定义中所有的节点，如果是开始节点则加入startEventActList中
        for (ActivityImpl activity : activityList) {

            activityMap.put(activity.getId(), activity);

            String actType = (String) activity.getProperty("type");
            if (actType != null && actType.toLowerCase().indexOf("startevent") >= 0) {
                startEventActList.add(activity);
            }
        }

        //These codes is used to avoid a bug:
        //ACT-1728 If the process instance was started by a callActivity, it will be not have the startEvent activity in ACT_HI_ACTINST table
        //Code logic:
        //Check the first activity if it is a startEvent, if not check out the startEvent's highlight outgoing flow.
        HistoricActivityInstance firstHistActInst = hisActInstList.getFirst();
        String firstActType = (String) firstHistActInst.getActivityType();
        if (firstActType != null && firstActType.toLowerCase().indexOf("startevent") < 0) {
            PvmTransition startTrans = getStartTransaction(startEventActList, firstHistActInst);
            if (startTrans != null) {
                highLightedFlows.add(startTrans.getId());
            }
        }
        while (!hisActInstList.isEmpty()) {
            HistoricActivityInstance histActInst = hisActInstList.removeFirst();
            ActivityImpl activity = activityMap.get(histActInst.getActivityId());
            if (activity != null) {
                boolean isParallel = false;
                String type = histActInst.getActivityType();
                if ("parallelGateway".equals(type) || "inclusiveGateway".equals(type)) {// 判断释放是并行网关或者包含网关
                    isParallel = true;
                } else if ("subProcess".equals(histActInst.getActivityType())) {// 判断是否是子流程
                    getHighlightedFlows(activity.getActivities(), hisActInstList, highLightedFlows);
                }

                List<PvmTransition> allOutgoingTrans = new ArrayList<PvmTransition>();// 节点的所有输出箭头
                allOutgoingTrans.addAll(activity.getOutgoingTransitions());
                allOutgoingTrans.addAll(getBoundaryEventOutgoingTransitions(activity));
                List<String> activityHighLightedFlowIds = getHighlightedFlows(allOutgoingTrans, hisActInstList, isParallel);
                highLightedFlows.addAll(activityHighLightedFlowIds);
            }
        }
    }

    /**
     * Check out the outgoing transition connected to firstActInst from startEventActList
     *
     * @param startEventActList
     * @param firstActInst
     * @return
     */
    private PvmTransition getStartTransaction(List<ActivityImpl> startEventActList, HistoricActivityInstance firstActInst) {
        for (ActivityImpl startEventAct : startEventActList) {
            for (PvmTransition trans : startEventAct.getOutgoingTransitions()) {
                if (trans.getDestination().getId().equals(firstActInst.getActivityId())) {
                    return trans;
                }
            }
        }
        return null;
    }

    /**
     * getBoundaryEventOutgoingTransitions
     *
     * @param activity
     * @return
     */
    private List<PvmTransition> getBoundaryEventOutgoingTransitions(ActivityImpl activity) {
        List<PvmTransition> boundaryTrans = new ArrayList<PvmTransition>();
        for (ActivityImpl subActivity : activity.getActivities()) {
            String type = (String) subActivity.getProperty("type");
            if (type != null && type.toLowerCase().indexOf("boundary") >= 0) {
                boundaryTrans.addAll(subActivity.getOutgoingTransitions());
            }
        }
        return boundaryTrans;
    }

    /**
     * find out single activity's highlighted flowIds
     *

     * @param hisActInstList

     * @return
     */
    private List<String> getHighlightedFlows(List<PvmTransition> pvmTransitionList, LinkedList<HistoricActivityInstance> hisActInstList, boolean isParallel) {

        List<String> highLightedFlowIds = new ArrayList<String>();

        PvmTransition earliestTrans = null;
        HistoricActivityInstance earliestHisActInst = null;

        for (PvmTransition pvmTransition : pvmTransitionList) {// 循环输出箭头

            String destActId = pvmTransition.getDestination().getId();// 目标节点id
            HistoricActivityInstance destHisActInst = null;// 目标节点实例
            if(pvmTransition.getSource().getActivityBehavior() instanceof ExclusiveGatewayActivityBehavior){// 如果当前节点是互斥网关
                if(CollectionUtils.isNotEmpty(hisActInstList)){
                    if (hisActInstList.get(0).getActivityId().equals(destActId)) {
                        destHisActInst = hisActInstList.get(0);
                    }
                }
            }else{
                destHisActInst = findHisActInst(hisActInstList, destActId);// 找到历史记录中的流程实例
            }
            if (destHisActInst != null) {// 历史记录不为空
                if (isParallel) {//如果是并行或者包含，则都加进去
                    highLightedFlowIds.add(pvmTransition.getId());
                } else if (earliestHisActInst == null || (earliestHisActInst.getId().compareTo(destHisActInst.getId()) > 0)) {
                    earliestTrans = pvmTransition;//最早的箭头
                    earliestHisActInst = destHisActInst;// 最早的节点实例
                }
            }
        }

        if ((!isParallel) && earliestTrans != null) {// 不是并行，且earliestTrans不等于null
            highLightedFlowIds.add(earliestTrans.getId());
        }

        return highLightedFlowIds;
    }

    private HistoricActivityInstance findHisActInst(LinkedList<HistoricActivityInstance> hisActInstList, String actId) {
        for (HistoricActivityInstance hisActInst : hisActInstList) {
            if (hisActInst.getActivityId().equals(actId)) {
                return hisActInst;
            }
        }
        return null;
    }



}
