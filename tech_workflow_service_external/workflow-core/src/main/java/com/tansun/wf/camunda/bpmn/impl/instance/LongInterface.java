package com.tansun.wf.camunda.bpmn.impl.instance;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

public class LongInterface extends  BpmnModelElementInstanceImpl {

	public LongInterface(ModelTypeInstanceContext instanceContext) {
		super(instanceContext);
	}
	
    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(LongInterface.class, "longInterface")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<LongInterface>() {
                    public LongInterface newInstance(ModelTypeInstanceContext instanceContext) {
                        return new LongInterface(instanceContext);
                    }
                });
        typeBuilder.build();
    } 

}
