package com.tansun.wf.camunda.bpmn.impl.instance;

import com.tansun.wf.camunda.bpmn.instance.SendTaskEmailContext;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

public class SendTaskEmailContextImpl extends BpmnModelElementInstanceImpl implements SendTaskEmailContext {
    public SendTaskEmailContextImpl(ModelTypeInstanceContext instanceContext) {
        super(instanceContext);
    }

    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(SendTaskEmailContext.class, "sendTaskEmailContext")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<SendTaskEmailContext>() {
                    public SendTaskEmailContext newInstance(ModelTypeInstanceContext instanceContext) {
                        return new SendTaskEmailContextImpl(instanceContext);
                    }
                });
        typeBuilder.build();
    }
}
