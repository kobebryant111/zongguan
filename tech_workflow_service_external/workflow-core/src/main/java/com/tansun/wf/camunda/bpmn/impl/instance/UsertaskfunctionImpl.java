package com.tansun.wf.camunda.bpmn.impl.instance;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

import com.tansun.wf.camunda.bpmn.instance.Usertaskfunction;

public class UsertaskfunctionImpl  extends BpmnModelElementInstanceImpl implements Usertaskfunction {

	public UsertaskfunctionImpl(ModelTypeInstanceContext instanceContext) {
		super(instanceContext);
	}
	
    public static void registerType(ModelBuilder modelBuilder) {
        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Usertaskfunction.class, "usertaskfunction")
                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Usertaskfunction>() {
                    public Usertaskfunction newInstance(ModelTypeInstanceContext instanceContext) {
                        return new UsertaskfunctionImpl(instanceContext);
                    }
                });
        typeBuilder.build();
    }    

}
