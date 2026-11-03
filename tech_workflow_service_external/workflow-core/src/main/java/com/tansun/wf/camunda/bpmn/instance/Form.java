package com.tansun.wf.camunda.bpmn.instance;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;

public interface Form extends BpmnModelElementInstance {

    /**
     * 默认URL
     */
    String BUSIMEANS_DEFAULT = "default";
    /**
     * 人工任务
     */
    String BUSIMEANS_ARTIFICIAL = "artificial";
    /**
     * 自定义URL
     */
    String BUSIMEANS_CUSTOM = "custom";

    public String getBusimeans();

    public void setBusimeans(String busimeans);

    public String getFormUrl();

    public void setFormUrl(String formUrl);
}
