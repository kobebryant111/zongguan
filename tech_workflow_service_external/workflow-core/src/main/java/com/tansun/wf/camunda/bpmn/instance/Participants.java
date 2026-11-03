package com.tansun.wf.camunda.bpmn.instance;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;

/**
 * 自定义标签：人工节点 - 参与者 - 从参与者列表获得
 *
 */
public interface Participants extends BpmnModelElementInstance {

    String TYPE_ROLE = "role";// 角色
    String TYPE_ORG = "org";// 机构
    String TYPE_USER = "user";// 个人
    String TYPE_POST = "post";// 岗位
    String TYPE_DEPT = "dept";// 岗位

    String MODEL_SIMPLE = "simple";// 简单参与者
    String MODEL_COMPLEX = "complex";// 复杂参与者

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getType();

    void setType(String type);

    String getModel();

    void setModel(String model);
}