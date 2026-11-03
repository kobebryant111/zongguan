package com.tansun.wf.camunda.bpmn.impl.instance;

import com.tansun.wf.camunda.bpmn.instance.Priority;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.CAMUNDA_NS;

/**
 * 条件优先级
 */
public class PriorityImpl extends BpmnModelElementInstanceImpl implements Priority {

    protected static Attribute<Integer> orderAttribute;

    public PriorityImpl(ModelTypeInstanceContext instanceContext) {
        super(instanceContext);
    }

    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Priority.class, "priority")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Priority>() {
                    public Priority newInstance(ModelTypeInstanceContext instanceContext) {
                        return new PriorityImpl(instanceContext);
                    }
                });

        orderAttribute = typeBuilder.integerAttribute("order").namespace(CAMUNDA_NS).build();
        typeBuilder.build();
    }

    @Override
    public Integer getOrder() {
        return orderAttribute.getValue(this);
    }

    @Override
    public void setOrder(Integer order) {
        orderAttribute.setValue(this,order);
    }
}