package com.tansun.wf.camunda.bpmn.impl.instance;

import com.tansun.wf.camunda.bpmn.instance.Participants;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.CAMUNDA_NS;

public class ParticipantsImpl extends BpmnModelElementInstanceImpl implements Participants {

    protected static Attribute<String> idAttribute;
    protected static Attribute<String> nameAttribute;
    protected static Attribute<String> typeAttribute;
    protected static Attribute<String> modelAttribute;

    public ParticipantsImpl(ModelTypeInstanceContext instanceContext) {
        super(instanceContext);
    }

    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Participants.class, "participants")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Participants>() {
                    public Participants newInstance(ModelTypeInstanceContext instanceContext) {
                        return new ParticipantsImpl(instanceContext);
                    }
                });

        idAttribute = typeBuilder.stringAttribute("id").namespace(CAMUNDA_NS).build();
        nameAttribute = typeBuilder.stringAttribute("name").namespace(CAMUNDA_NS).build();
        typeAttribute = typeBuilder.stringAttribute("type").namespace(CAMUNDA_NS).build();
        modelAttribute = typeBuilder.stringAttribute("model").namespace(CAMUNDA_NS).build();

        typeBuilder.build();
    }

    @Override
    public String getId() {
        return idAttribute.getValue(this);
    }

    @Override
    public void setId(String id) {
        idAttribute.setValue(this,id);
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
    public String getType() {
        return typeAttribute.getValue(this);
    }

    @Override
    public void setType(String type) {
        typeAttribute.setValue(this,type);
    }

    @Override
    public String getModel() {
        return modelAttribute.getValue(this);
    }

    @Override
    public void setModel(String model) {
        modelAttribute.setValue(this,model);
    }
}
