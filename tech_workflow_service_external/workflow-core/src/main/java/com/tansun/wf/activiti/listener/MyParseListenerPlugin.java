package com.tansun.wf.activiti.listener;

import com.tansun.wf.camunda.bpmn.impl.instance.*;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyParseListenerPlugin extends AbstractProcessEnginePlugin implements InitializingBean  {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private MyParseListener myParseListener;

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        LOGGER.info("加载自定义插件：MyParseListenerPlugin...");
        List<BpmnParseListener> preParseListeners = processEngineConfiguration.getCustomPostBPMNParseListeners();
        if (preParseListeners == null) {
            preParseListeners = new ArrayList<>();
        }
        preParseListeners.add(myParseListener);
        processEngineConfiguration.setCustomPostBPMNParseListeners(preParseListeners);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ModelBuilder bpmnModelBuilder = Bpmn.INSTANCE.getBpmnModelBuilder();
        Designform.registerType(bpmnModelBuilder);
        UsertaskfunctionImpl.registerType(bpmnModelBuilder);
        Backtype.registerType(bpmnModelBuilder);
        Votingstrategy.registerType(bpmnModelBuilder);
        Carboncopy.registerType(bpmnModelBuilder);
        ProcessAttributeImpl.registerType(bpmnModelBuilder);
        UserTaskParticipantImpl.registerType(bpmnModelBuilder);
        RuleLogicParamImpl.registerType(bpmnModelBuilder);
        ParticipantsImpl.registerType(bpmnModelBuilder);
        FormImpl.registerType(bpmnModelBuilder);
        SendTaskEmailAddressImpl.registerType(bpmnModelBuilder);
        SendTaskEmailContextImpl.registerType(bpmnModelBuilder);
        MultiWork.registerType(bpmnModelBuilder);
        ExtendEvent.registerType(bpmnModelBuilder);
        LongInterface.registerType(bpmnModelBuilder);
        PriorityImpl.registerType(bpmnModelBuilder);
    }

}