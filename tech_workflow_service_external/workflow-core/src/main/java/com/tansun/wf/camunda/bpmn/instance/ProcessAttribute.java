package com.tansun.wf.camunda.bpmn.instance;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;

/**
 * @author lijiang
 * @create 2021-04-07 14:49
 */
public interface ProcessAttribute extends BpmnModelElementInstance {

    /**
     *
     * @return
     */
    void setProcessStartClass(String procesStartClass);
    String getProcessStartClass();

    void setProcessCompleteClass(String processComplieClass);
    String getProcessCompleteClass();

   
    String getClassName();

    void setClassName(String className);

   
}
