package com.tansun.wf.camunda.bpmn.instance;

import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;

import java.util.Collection;

/**
 * 自定义标签：人工节点
 *
 * 人工节点自定义的元素在这个接口需要都能获取得到
 */
public interface UserTaskParticipant extends BpmnModelElementInstance {

    //============人工节点 - 基本信息================

    //============人工节点 - 参与者================

    /**从参与者列表获得*/
    String CONFIG_TYPE_1 = "1";
    /**流程启动者*/
    String CONFIG_TYPE_2 = "2";
    /**流程执行者*/
    String CONFIG_TYPE_3 = "3";
    /**参与者规则*/
    String CONFIG_TYPE_4 = "4";
    /**相关数据*/
    String CONFIG_TYPE_5 = "5";
    /**规则逻辑*/
    String CONFIG_TYPE_6 = "6";

    /**服务类型：本地服务*/
    String SERVICE_TYPE_LOCAL = "local";
    /**服务类型：远程服务*/
    String SERVICE_TYPE_ONLINE = "online";

    /**参与者配置类型*/
    String getParRadioConfig();

    /**赋值参与者配置类型 CONFIG_TYPE*/
    void setParRadioConfig(String parRadioConfig);

    /**参与者列表，不需要set方法，通过addChildElement*/
    Collection<Participants> getParticipants();

    /**是否允许前驱活动根据如上列表指派活动参与者*/
    boolean getAllowPreAct();

    /**
     * 允许前驱活动根据如上列表指派活动参与者
     */
    void setAllowPreAct(boolean allowPreAct);

    /**
     * 流程执行者
     * @return activityId
     */
    String getExecutor();

    /**
     * 流程执行者
     * @param activityId
     */
    void setExecutor(String activityId);

    /**
     * TODO 参与者规则 待开发
     * @return
     */
    String getParticRule();

    /**
     * TODO 参与者规则 待开发
     * @param particRule
     */
    void setParticRule(String particRule);

    /**
     * 相关数据
     * @return ${xxx}
     */
    String getAboutData();

    /**
     * 相关数据
     */
    void setAboutData(String aboutData);

    /**
     * 服务类型
     * @return
     */
    String getServiceType();

    void setServiceType(String serviceType);

    /**
     * 服务类
     * @return
     */
    String getServiceValue();

    void setServiceValue(String serviceValue);

    /**
     * 参数配置
     * @return
     */
    Collection<RuleLogicParam> getRuleLogicParams();

}
