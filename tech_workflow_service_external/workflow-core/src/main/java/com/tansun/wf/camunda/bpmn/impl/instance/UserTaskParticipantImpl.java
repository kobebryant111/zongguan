package com.tansun.wf.camunda.bpmn.impl.instance;

import com.tansun.wf.camunda.bpmn.instance.Participants;
import com.tansun.wf.camunda.bpmn.instance.RuleLogicParam;
import com.tansun.wf.camunda.bpmn.instance.UserTaskParticipant;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.child.ChildElementCollection;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;

import java.util.Collection;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.CAMUNDA_NS;

public class UserTaskParticipantImpl extends BpmnModelElementInstanceImpl implements UserTaskParticipant {

    protected static Attribute<String> parRadioConfigAttribute;
    protected static ChildElementCollection<Participants> participantsAttribute;
    protected static Attribute<Boolean> allowPreActAttribute;
    protected static Attribute<String> particRuleAttribute;
    protected static Attribute<String> aboutDataAttribute;
    protected static Attribute<String> executorAttribute;
    protected static Attribute<String> serviceTypeAttribute;
    protected static Attribute<String> serviceValueAttribute;
    protected static ChildElementCollection<RuleLogicParam> ruleLogicParamsAttribute;

    public UserTaskParticipantImpl(ModelTypeInstanceContext instanceContext) {
        super(instanceContext);
    }

    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(UserTaskParticipant.class, "userTaskParticipant")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<UserTaskParticipant>() {
                    public UserTaskParticipant newInstance(ModelTypeInstanceContext instanceContext) {
                        return new UserTaskParticipantImpl(instanceContext);
                    }
                });

        SequenceBuilder sequenceBuilder = typeBuilder.sequence();

        parRadioConfigAttribute = typeBuilder.stringAttribute("parRadioConfig").namespace(CAMUNDA_NS).build();
        participantsAttribute=sequenceBuilder.elementCollection(Participants.class).build();
        allowPreActAttribute = typeBuilder.booleanAttribute("allowPreAct").namespace(CAMUNDA_NS).build();
        particRuleAttribute=typeBuilder.stringAttribute("particRule").namespace(CAMUNDA_NS).build();
        aboutDataAttribute=typeBuilder.stringAttribute("aboutData").namespace(CAMUNDA_NS).build();
        executorAttribute = typeBuilder.stringAttribute("executor").namespace(CAMUNDA_NS).build();
        serviceTypeAttribute = typeBuilder.stringAttribute("serviceType").namespace(CAMUNDA_NS).build();
        serviceValueAttribute = typeBuilder.stringAttribute("serviceValue").namespace(CAMUNDA_NS).build();
        ruleLogicParamsAttribute=sequenceBuilder.elementCollection(RuleLogicParam.class).build();
        typeBuilder.build();
    }

    @Override
    public String getParRadioConfig() {
        return parRadioConfigAttribute.getValue(this);
    }

    @Override
    public void setParRadioConfig(String parRadioConfig) {
        parRadioConfigAttribute.setValue(this,parRadioConfig);
    }

    @Override
    public Collection<Participants> getParticipants() {
        return participantsAttribute.get(this);
    }

    @Override
    public boolean getAllowPreAct() {
        return allowPreActAttribute.getValue(this);
    }

    @Override
    public void setAllowPreAct(boolean allowPreAct) {
        allowPreActAttribute.setValue(this,allowPreAct);
    }

    @Override
    public String getExecutor() {
        return executorAttribute.getValue(this);
    }

    @Override
    public void setExecutor(String activityId) {
        executorAttribute.setValue(this,activityId);
    }

    @Override
    public String getParticRule() {
        return particRuleAttribute.getValue(this);
    }

    @Override
    public void setParticRule(String particRule) {
        particRuleAttribute.setValue(this,particRule);
    }

    @Override
    public String getAboutData() {
        return aboutDataAttribute.getValue(this);
    }

    @Override
    public void setAboutData(String aboutData) {
        aboutDataAttribute.setValue(this,aboutData);
    }

    @Override
    public String getServiceType() {
        return serviceTypeAttribute.getValue(this);
    }

    @Override
    public void setServiceType(String serviceType) {
        serviceTypeAttribute.setValue(this,serviceType);
    }

    @Override
    public String getServiceValue() {
        return serviceValueAttribute.getValue(this);
    }

    @Override
    public void setServiceValue(String serviceValue) {
        serviceValueAttribute.setValue(this,serviceValue);
    }

    @Override
    public Collection<RuleLogicParam> getRuleLogicParams() {
        return ruleLogicParamsAttribute.get(this);
    }

    @Override
    public void addChildElement(ModelElementInstance newChild) {
        getDomElement().appendChild(newChild.getDomElement());
    }
}
