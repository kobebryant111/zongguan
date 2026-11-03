package com.tansun.wf.camunda.bpmn.impl.instance;

import com.tansun.wf.camunda.bpmn.instance.RuleLogicParam;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.CAMUNDA_NS;

/**
 *  参数配置表
 */
public class RuleLogicParamImpl extends BpmnModelElementInstanceImpl implements RuleLogicParam {

    protected static Attribute<String> nameAttribute;
    protected static Attribute<String> dataTypeAttribute;
    protected static Attribute<String> valueAttribute;
    protected static Attribute<String> valueTypeAttribute;

    public RuleLogicParamImpl(ModelTypeInstanceContext instanceContext) {
        super(instanceContext);
    }

    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(RuleLogicParam.class, "ruleLogicParam")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<RuleLogicParam>() {
                    public RuleLogicParam newInstance(ModelTypeInstanceContext instanceContext) {
                        return new RuleLogicParamImpl(instanceContext);
                    }
                });

        SequenceBuilder sequenceBuilder = typeBuilder.sequence();

        nameAttribute = typeBuilder.stringAttribute("name").namespace(CAMUNDA_NS).build();
        dataTypeAttribute=typeBuilder.stringAttribute("dataType").namespace(CAMUNDA_NS).build();
        valueAttribute = typeBuilder.stringAttribute("value").namespace(CAMUNDA_NS).build();
        valueTypeAttribute = typeBuilder.stringAttribute("valueType").namespace(CAMUNDA_NS).build();
        typeBuilder.build();
    }

    @Override
    public String getName() {
        return nameAttribute.getValue(this);
    }

    @Override
    public void setName(String name) {
        nameAttribute.setValue(this,name);
    }

    @Override
    public String getDataType() {
        return dataTypeAttribute.getValue(this);
    }

    @Override
    public void setDataType(String dataType) {
        dataTypeAttribute.setValue(this,dataType);
    }

    @Override
    public String getValue() {
        return valueAttribute.getValue(this);
    }

    @Override
    public void setValue(String value) {
        valueAttribute.setValue(this,value);
    }

    @Override
    public String getValueType() {
        return valueTypeAttribute.getValue(this);
    }

    @Override
    public void setValueType(String valueType) {
        valueTypeAttribute.setValue(this,valueType);
    }
}
