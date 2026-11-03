package com.tansun.wf.camunda.bpmn.impl.instance;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

public class Designform  extends BpmnModelElementInstanceImpl{

	public Designform(ModelTypeInstanceContext instanceContext) {
		super(instanceContext);
		// TODO Auto-generated constructor stub
	}
	
    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Designform.class, "designform")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Designform>() {
                    public Designform newInstance(ModelTypeInstanceContext instanceContext) {
                        return new Designform(instanceContext);
                    }
                });
        typeBuilder.build();
    } 


}
