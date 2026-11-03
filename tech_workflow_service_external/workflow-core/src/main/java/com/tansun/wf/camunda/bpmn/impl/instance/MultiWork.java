package com.tansun.wf.camunda.bpmn.impl.instance;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.CAMUNDA_NS;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.impl.instance.BpmnModelElementInstanceImpl;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;

public class MultiWork extends BpmnModelElementInstanceImpl {
	

    protected static Attribute<String>  completeNumAttr;
	

	protected static Attribute<String> autoStopAttr;
	

	protected static Attribute<String> completePercentAttr;

	protected static Attribute<String> enableMultiWorkAttr;
	

	protected static Attribute<String> disStrategyAttr;

	protected static Attribute<String> isInOrderAttr;

	protected static Attribute<String> finishRuleAttr;

	protected static Attribute<String> iterateVariableAttr;

	protected static Attribute<String> iterateElementAttr;

	public MultiWork(ModelTypeInstanceContext instanceContext) {
		super(instanceContext);
	
	}
	
	 public static void registerType(ModelBuilder modelBuilder) {
	        ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(MultiWork.class, "multiWork")
	                .namespaceUri(BpmnModelConstants.CAMUNDA_NS)
	                .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<MultiWork>() {
	                    public MultiWork newInstance(ModelTypeInstanceContext instanceContext) {
	                        return new MultiWork(instanceContext);
	                    }
	                });
	        completeNumAttr=  typeBuilder.stringAttribute("completeNum")
            .namespace(CAMUNDA_NS)
            .build();
	        
	        autoStopAttr= typeBuilder.stringAttribute("autoStop")
            .namespace(CAMUNDA_NS)
            .build();
	        
	        completePercentAttr=typeBuilder.stringAttribute("completePercent")
            .namespace(CAMUNDA_NS)
            .build();
	        
	        enableMultiWorkAttr= typeBuilder.stringAttribute("enableMultiWork")
            .namespace(CAMUNDA_NS)
            .build();
	        
	        disStrategyAttr=typeBuilder.stringAttribute("disStrategy")
            .namespace(CAMUNDA_NS)
            .build();
	        
	        isInOrderAttr= typeBuilder.stringAttribute("isInOrder")
            .namespace(CAMUNDA_NS)
            .build();
	        
	        finishRuleAttr= typeBuilder.stringAttribute("finishRule")
            .namespace(CAMUNDA_NS)
            .build();
	        
	        iterateVariableAttr= typeBuilder.stringAttribute("iterateVariable")
            .namespace(CAMUNDA_NS)
            .build();
	        
	        iterateElementAttr= typeBuilder.stringAttribute("iterateElement")
            .namespace(CAMUNDA_NS)
            .build();
	        typeBuilder.build();
	 }


	public String getCompleteNum() {
		return completeNumAttr.getValue(this);
	}


	public void setCompleteNum(String completeNum) {
		completeNumAttr.setValue(this, completeNum);
	}


	public String getAutoStop() {
		return autoStopAttr.getValue(this);
	}


	public void setAutoStop(String autoStop) {
		autoStopAttr.setValue(this, autoStop);
	}


	public String getCompletePercent() {
		return completePercentAttr.getValue(this);
	}


	public void setCompletePercent(String completePercent) {
		completePercentAttr.setValue(this, completePercent);
	}


	public boolean isEnableMultiWork() {
		String enbaleWork =enableMultiWorkAttr.getValue(this);
		if (enbaleWork != null) {
		    return	Boolean.valueOf(enbaleWork);
		}
		return  false;
	}


	public void setEnableMultiWork(boolean enableMultiWork) {
		enableMultiWorkAttr.setValue(this, String.valueOf(enableMultiWork));
	}


	public String getDisStrategy() {
		return disStrategyAttr.getValue(this);
	}


	public void setDisStrategy(String disStrategy) {
		disStrategyAttr.setValue(this, disStrategy);
	}


	public String getIsInOrder() {
	   return 	isInOrderAttr.getValue(this);
	}


	public void setIsInOrder(String isInOrder) {
		isInOrderAttr.setValue(this, isInOrder);
	}


	public String getFinishRule() {
		return finishRuleAttr.getValue(this);
	}


	public void setFinishRule(String finishRule) {
		finishRuleAttr.setValue(this, finishRule);
	}


	public String getIterateVariable() {
		return iterateVariableAttr.getValue(this);
	}


	public void setIterateVariable(String iterateVariable) {
		iterateVariableAttr.setValue(this, iterateVariable);
	}


	public String getIterateElement() {
		return iterateElementAttr.getValue(this);
	}


	public void setIterateElement(String iterateElement) {
		iterateElementAttr.setValue(this, iterateElement);
	}
	
	
	

}
