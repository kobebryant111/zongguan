package com.tansun.wf.camunda.bpmn.instance;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;

public interface RuleLogicParam extends BpmnModelElementInstance {

    String VALUE_TYPE_VARIABLE = "variable";// 变量
    String VALUE_TYPE_CONSTANT = "constant";// 常量

    String getName();

    void setName(String name);

    String getDataType();

    void setDataType(String dataType);

    String getValue();

    void setValue(String value);

    String getValueType();

    void setValueType(String valueType);
}
