package com.tansun.wf.decision.service;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.camunda.bpmn.impl.instance.LongInterface;
import com.tansun.workflow.utils.RestTemplateUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务任务默认远程调用接口
 * @author lijiang
 * @create 2019-11-26 14:42
 */
public class HttpServiceTaskDelegate implements JavaDelegate {

    // 远程调用地址，用户启动之前在properties中配置java.service.address
    private String address;

    private static Logger logger = LoggerFactory.getLogger(HttpServiceTaskDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String longInterface = execution.getBpmnModelElementInstance().getExtensionElements().getElementsQuery().filterByType(LongInterface.class).singleResult().getTextContent();
        if(StringUtils.isBlank(longInterface)){
            throw new RuntimeException("服务节点需要配置远程接口");
        }else{
            address = longInterface;
        }

        // 声明参数
        Map<String,Object> paramMap = new HashMap<>();

        // 添加常规属性
        addFields(execution,paramMap);

        // 调用远程服务
        Map result;
        try {
            logger.info("before call serviceTask params is: " + paramMap);
            result = doPost(address, paramMap);
            logger.info("after call serviceTask result is: " + result);
            Integer status = MapUtils.getInteger(result, "status");
            if(status!=null && status==100){
                throw new RuntimeException("远程服务报错" + result.get("description"));
            }
            if(result==null){
                throw new RuntimeException("远程服务报错");
            }
        }catch (Exception e){
            throw new RuntimeException("远程服务调用失败："+e.getMessage());
        }
        // 更新返回的流程变量
        if(result!=null && MapUtils.getMap(result,"variables")!=null){
            execution.setVariables(MapUtils.getMap(result,"variables"));
        }
    }

    /**
     * 添加属性值
     * @param execution
     * @param paramMap
     */
    private void addFields(DelegateExecution execution,Map<String,Object> paramMap){
        paramMap.put("id",execution.getId());
        paramMap.put("tenantId",execution.getTenantId());
        paramMap.put("processInstanceId",execution.getProcessInstanceId());
        paramMap.put("activityId",((ExecutionEntity) execution).getActivityId());
        paramMap.put("businessKey",execution.getProcessBusinessKey());
        paramMap.put("processDefinitionId",execution.getProcessDefinitionId());
        // camunda-TODO
        paramMap.put("processDefinitionKey",((ExecutionEntity) execution).getProcessDefinition().getKey());
        paramMap.put("activityId",execution.getCurrentActivityId());
        paramMap.put("activityName",execution.getCurrentActivityName());
        paramMap.put("parentId",execution.getParentId());

        // camunda-TODO
        paramMap.put("name",((ExecutionEntity) execution).getProcessDefinition().getName());
        paramMap.put("deleteReason",((ExecutionEntity) execution).getDeleteReason());
        paramMap.put("deploymentId",((ExecutionEntity) execution).getProcessDefinition().getDeploymentId());
        paramMap.put("description",((ExecutionEntity) execution).getProcessDefinition().getDescription());
        paramMap.put("eventName",execution.getEventName());
        paramMap.put("processDefinitionVersion",((ExecutionEntity) execution).getProcessDefinition().getVersion());
        paramMap.put("revision",((ExecutionEntity) execution).getRevision());
        paramMap.put("revisionNext",((ExecutionEntity) execution).getRevisionNext());
        paramMap.put("superExecutionId",((ExecutionEntity) execution).getSuperExecutionId());
        paramMap.put("cachedEntityState",((ExecutionEntity) execution).getCachedEntityState());
        paramMap.put("isActive",((ExecutionEntity) execution).isActive());
        paramMap.put("isScope",((ExecutionEntity) execution).isScope());
        paramMap.put("isConcurrent",((ExecutionEntity) execution).isConcurrent());
        paramMap.put("isEnded",((ExecutionEntity) execution).isEnded());
        paramMap.put("isEventScope",((ExecutionEntity) execution).isEventScope());
        //流程变量
        paramMap.put("variables",execution.getVariables());
    }

    /**
     * 通用远程调用请求
     * @return
     */
    private  Map doPost(String url, Map params) throws Exception {
        String paramsJson = JSON.toJSONString(params);
        logger.info("remote interface url is :" + url);
        logger.info("mehtod: {}, invoke params is :" + paramsJson);

        String result = RestTemplateUtils.post(url, paramsJson);
        logger.info("result content is :" + result);
        return JSON.parseObject(result, Map.class);

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
