package com.tansun.wf.camunda.bpmn.impl.instance;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

public class Carboncopy extends BpmnModelElementInstanceImpl {

	public Carboncopy(ModelTypeInstanceContext instanceContext) {
		super(instanceContext);
	
	}
	
    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Carboncopy.class, "carboncopy")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Carboncopy>() {
                    public Carboncopy newInstance(ModelTypeInstanceContext instanceContext) {
                        return new Carboncopy(instanceContext);
                    }
                });
        typeBuilder.build();
    } 

}
