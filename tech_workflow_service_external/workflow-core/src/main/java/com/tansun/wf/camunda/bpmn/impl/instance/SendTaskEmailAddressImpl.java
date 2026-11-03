package com.tansun.wf.camunda.bpmn.impl.instance;

import com.tansun.wf.camunda.bpmn.instance.SendTaskEmailAddress;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

public class SendTaskEmailAddressImpl extends BpmnModelElementInstanceImpl implements SendTaskEmailAddress {
    public SendTaskEmailAddressImpl(ModelTypeInstanceContext instanceContext) {
        super(instanceContext);
    }

    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(SendTaskEmailAddress.class, "sendTaskEmailAddress")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<SendTaskEmailAddress>() {
                    public SendTaskEmailAddress newInstance(ModelTypeInstanceContext instanceContext) {
                        return new SendTaskEmailAddressImpl(instanceContext);
                    }
                });
        typeBuilder.build();
    }
}
