package com.tansun.wf.extend.behavior;

import com.tansun.wf.activiti.comparator.SequenceFlowPriorityComparator;
import org.apache.commons.collections.IteratorUtils;
import org.camunda.bpm.engine.impl.Condition;
import org.camunda.bpm.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParse;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MyExclusiveGatewayActivityBehavior extends ExclusiveGatewayActivityBehavior {

    @Override
    public void doLeave(ActivityExecution execution) {

        LOG.leavingActivity(execution.getActivity().getId());

        PvmTransition outgoingSeqFlow = null;
        //获取默认分支的箭头id
        String defaultSequenceFlow = (String) execution.getActivity().getProperty("default");

        //列出可以扭转的分支
        Iterator<PvmTransition> transitionIterator = execution.getActivity().getOutgoingTransitions().iterator();

        //循环分支进行排序
        List<PvmTransition> list = IteratorUtils.toList(transitionIterator);
        BpmnModelInstance bpmnModelInstance = execution.getProcessEngineServices().getRepositoryService().getBpmnModelInstance(execution.getProcessDefinitionId());

        // 排序器
        SequenceFlowPriorityComparator sequenceFlowPriorityComparator = new SequenceFlowPriorityComparator();
        sequenceFlowPriorityComparator.setBpmnModelInstance(bpmnModelInstance);
        Collections.sort(list,sequenceFlowPriorityComparator);
        Iterator<PvmTransition> iterator = list.iterator();

        //循环可以扭转的分支，判断条件是否满足，outgoingSeqFlow有值就走outgoingSeqFlow
        while (outgoingSeqFlow == null && iterator.hasNext()) {
            PvmTransition seqFlow = iterator.next();

            Condition condition = (Condition) seqFlow.getProperty(BpmnParse.PROPERTYNAME_CONDITION);
            // 进行条件判断，true就决定是这个分支了
            if ( (condition == null && (defaultSequenceFlow == null || !defaultSequenceFlow.equals(seqFlow.getId())) )
                    || (condition != null && condition.evaluate(execution)) ) {

                LOG.outgoingSequenceFlowSelected(seqFlow.getId());
                // 要走哪条分支，赋值
                outgoingSeqFlow = seqFlow;
            }
        }

        // 如果上面确定了要走哪条线
        if (outgoingSeqFlow != null) {
            // 那么就执行哪条
            execution.leaveActivityViaTransition(outgoingSeqFlow);
        } else {
            // 否则，如果有默认分支，就走默认分支
            if (defaultSequenceFlow != null) {
                PvmTransition defaultTransition = execution.getActivity().findOutgoingTransition(defaultSequenceFlow);
                if (defaultTransition != null) {
                    execution.leaveActivityViaTransition(defaultTransition);
                } else {
                    throw LOG.missingDefaultFlowException(execution.getActivity().getId(), defaultSequenceFlow);
                }
            } else {
                // 没有默认分支，报错
                //No sequence flow could be found, not even a default one
                throw LOG.stuckExecutionException(execution.getActivity().getId());
            }
        }
    }
}
