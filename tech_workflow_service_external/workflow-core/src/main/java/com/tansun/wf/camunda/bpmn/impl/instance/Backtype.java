package com.tansun.wf.camunda.bpmn.impl.instance;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

public class Backtype extends BpmnModelElementInstanceImpl {

    public static final String BACK_TO_USER = "backToUser";
    public static final String BACK_TO_GROUP = "backToGroup";

	public Backtype(ModelTypeInstanceContext instanceContext) {
		super(instanceContext);
	
	}
	
    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Backtype.class, "backtype")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Backtype>() {
                    public Backtype newInstance(ModelTypeInstanceContext instanceContext) {
                        return new Backtype(instanceContext);
                    }
                });
        typeBuilder.build();
    } 

}
