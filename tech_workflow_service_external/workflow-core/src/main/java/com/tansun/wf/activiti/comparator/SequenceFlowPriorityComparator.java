package com.tansun.wf.activiti.comparator;

import com.tansun.wf.camunda.bpmn.instance.Priority;
import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SequenceFlowPriorityComparator implements Comparator<PvmTransition> {

    private BpmnModelInstance bpmnModelInstance;

    @Override
    public int compare(PvmTransition pvmTransition1, PvmTransition pvmTransition2) {
        SequenceFlow sequenceFlow1 = bpmnModelInstance.getModelElementById(pvmTransition1.getId());
        SequenceFlow sequenceFlow2 = bpmnModelInstance.getModelElementById(pvmTransition2.getId());

        Collection<ExtensionElements> extensionElements1 = sequenceFlow1.getChildElementsByType(ExtensionElements.class);
        Collection<ExtensionElements> extensionElements2 = sequenceFlow2.getChildElementsByType(ExtensionElements.class);
        List<Priority> priorities1 = extensionElements1.iterator().next().getElementsQuery().filterByType(Priority.class).list();
        List<Priority> priorities2 = extensionElements2.iterator().next().getElementsQuery().filterByType(Priority.class).list();

        Integer order1 = 0;
        if (CollectionUtils.isNotEmpty(priorities1)){
            order1 = priorities1.iterator().next().getOrder();
        }
        Integer order2 = 0;
        if (CollectionUtils.isNotEmpty(priorities2)){
            order2 = priorities2.iterator().next().getOrder();

        }
        return order2.compareTo(order1);
    }

    public void setBpmnModelInstance(BpmnModelInstance bpmnModelInstance) {
        this.bpmnModelInstance = bpmnModelInstance;
    }
}
