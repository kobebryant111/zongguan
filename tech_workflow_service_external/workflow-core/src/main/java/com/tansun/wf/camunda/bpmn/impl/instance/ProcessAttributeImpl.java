package com.tansun.wf.camunda.bpmn.impl.instance;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.CAMUNDA_NS;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;

import com.tansun.wf.camunda.bpmn.instance.ProcessAttribute;

/**
 * @author lijiang
 * @create 2021-04-07 14:49
 */
public class ProcessAttributeImpl extends BpmnModelElementInstanceImpl implements ProcessAttribute {


    protected static Attribute<String> classNameAttribute;

    protected static Attribute<String> processStartedClassAttribute;
    protected static Attribute<String> processCompleteClassAttribute;

    public ProcessAttributeImpl(ModelTypeInstanceContext instanceContext) {
        super(instanceContext);
    }

    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(ProcessAttribute.class, "processAttribute")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<ProcessAttribute>() {
                    public ProcessAttribute newInstance(ModelTypeInstanceContext instanceContext) {
                        return new ProcessAttributeImpl(instanceContext);
                    }
                });

        SequenceBuilder sequenceBuilder = typeBuilder.sequence();

        processStartedClassAttribute=typeBuilder.stringAttribute("processStartedClass").namespace(CAMUNDA_NS).build();
        processCompleteClassAttribute=typeBuilder.stringAttribute("processCompleteClass").namespace(CAMUNDA_NS).build();
        classNameAttribute=typeBuilder.stringAttribute("className").namespace(CAMUNDA_NS).build();  
        typeBuilder.build();
    }

    @Override
    public void setProcessStartClass(String procesStartClass) {
        processStartedClassAttribute.setValue(this,procesStartClass);
    }

    @Override
    public String getProcessStartClass() {
        return processStartedClassAttribute.getValue(this);
    }

    @Override
    public void setProcessCompleteClass(String processComplieClass) {
        processCompleteClassAttribute.setValue(this,processComplieClass);
    }

    @Override
    public String getProcessCompleteClass() {
        return processCompleteClassAttribute.getValue(this);
    }

    @Override
    public String getClassName() {
        return classNameAttribute.getValue(this);
    }

    @Override
    public void setClassName(String className) {
        classNameAttribute.setValue(this,className);
    }
}
