package com.tansun.wf.camunda.bpmn.impl.instance;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

public class Votingstrategy extends  BpmnModelElementInstanceImpl {

	public Votingstrategy(ModelTypeInstanceContext instanceContext) {
		super(instanceContext);
		// TODO Auto-generated constructor stub
	}
	
    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Votingstrategy.class, "votingstrategy")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Votingstrategy>() {
                    public Votingstrategy newInstance(ModelTypeInstanceContext instanceContext) {
                        return new Votingstrategy(instanceContext);
                    }
                });
        typeBuilder.build();
    } 

}
