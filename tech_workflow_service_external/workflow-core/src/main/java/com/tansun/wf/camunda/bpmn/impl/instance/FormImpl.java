package com.tansun.wf.camunda.bpmn.impl.instance;

import com.tansun.wf.camunda.bpmn.instance.Form;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.CAMUNDA_NS;

public class FormImpl extends BpmnModelElementInstanceImpl implements Form {

    protected static Attribute<String> busimeansAttribute;
    protected static Attribute<String> formUrlAttribute;

    public FormImpl(ModelTypeInstanceContext instanceContext) {
        super(instanceContext);
    }

    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Form.class, "form")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Form>() {
                    public Form newInstance(ModelTypeInstanceContext instanceContext) {
                        return new FormImpl(instanceContext);
                    }
                });

        busimeansAttribute = typeBuilder.stringAttribute("busimeans").namespace(CAMUNDA_NS).build();
        formUrlAttribute = typeBuilder.stringAttribute("formUrl").namespace(CAMUNDA_NS).build();

        typeBuilder.build();
    }

    @Override
    public String getBusimeans() {
        return busimeansAttribute.getValue(this);
    }

    @Override
    public void setBusimeans(String busimeans) {
        busimeansAttribute.setValue(this,busimeans);
    }

    @Override
    public String getFormUrl() {
        return formUrlAttribute.getValue(this);
    }

    @Override
    public void setFormUrl(String formUrl) {
        formUrlAttribute.setValue(this,formUrl);
    }
}
