package com.tansun.wf.camunda.bpmn.instance;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;

/**
 * 条件优先级
 */
public interface Priority extends BpmnModelElementInstance {

    Integer getOrder();

    void setOrder(Integer order);
}