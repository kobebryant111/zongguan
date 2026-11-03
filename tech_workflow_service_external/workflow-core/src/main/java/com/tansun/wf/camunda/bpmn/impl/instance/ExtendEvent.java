package com.tansun.wf.camunda.bpmn.impl.instance;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.CAMUNDA_NS;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;

public class ExtendEvent extends BpmnModelElementInstanceImpl {
	
	private  static  Attribute<String>  eventAttr;	
	private  static  Attribute<String>  serviceNameAttr;
	private  static  Attribute<String>  callTypeAttr;
	
	

	public ExtendEvent(ModelTypeInstanceContext instanceContext) {
		super(instanceContext);		
	}

	 public static void registerType(ModelBuilder modelBuilder) {
	        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(ExtendEvent.class, "extendEvent")
	                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
	                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<ExtendEvent>() {
	                    public ExtendEvent newInstance(ModelTypeInstanceContext instanceContext) {
	                        return new ExtendEvent(instanceContext);
	                    }
	                });
	        eventAttr=  typeBuilder.stringAttribute("event")
	                .namespace(CAMUNDA_NS)
	                .build();
	        
	        serviceNameAttr=  typeBuilder.stringAttribute("serviceName")
	                .namespace(CAMUNDA_NS)
	                .build();
	        
	        callTypeAttr=  typeBuilder.stringAttribute("callType")
	                .namespace(CAMUNDA_NS)
	                .build();
	        typeBuilder.build();
	 }  


	public String getEvent() {
		return  eventAttr.getValue(this);
	}

	public void setEvent(String event) {
		eventAttr.setValue(this, event);
	}

	public String getServiceName() {
		return  serviceNameAttr.getValue(this);
	}

	public void setServiceName(String serviceName) {
		serviceNameAttr.setValue(this, serviceName);
	}

	public String getCallType() {
		return callTypeAttr.getValue(this);
	}

	public void setCallType(String callType) {
		callTypeAttr.setValue(this, callType);
	}
	
	

}
