package com.tansun.wf.extend.behavior.factory;

import com.tansun.wf.extend.behavior.MyExclusiveGatewayActivityBehavior;
import com.tansun.wf.extend.behavior.MyParallelMultiInstanceActivityBehavior;
import com.tansun.wf.extend.behavior.MySequentialMultiInstanceActivityBehavior;
import org.camunda.bpm.engine.ActivityTypes;
import org.camunda.bpm.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParse;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParser;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

public class MyBpmnParse extends BpmnParse {
    /**
     * Constructor to be called by the {@link BpmnParser}.
     *
     * @param parser
     */
    public MyBpmnParse(BpmnParser parser) {
        super(parser);
    }

    @Override
    public ScopeImpl parseMultiInstanceLoopCharacteristics(Element activityElement, ScopeImpl scope) {
        Element miLoopCharacteristics = activityElement.element("multiInstanceLoopCharacteristics");
        if (miLoopCharacteristics == null) {
            return null;
        } else {
            String id = activityElement.attribute("id");

            LOG.parsingElement("mi body for activity", id);

            id = getIdForMiBody(id);
            ActivityImpl miBodyScope = scope.createActivity(id);
            setActivityAsyncDelegates(miBodyScope);
            miBodyScope.setProperty(PROPERTYNAME_TYPE, ActivityTypes.MULTI_INSTANCE_BODY);
            miBodyScope.setScope(true);

            boolean isSequential = parseBooleanAttribute(miLoopCharacteristics.attribute("isSequential"), false);

            MultiInstanceActivityBehavior behavior = null;
            if (isSequential) {
                behavior = new MySequentialMultiInstanceActivityBehavior();
            } else {
                behavior = new MyParallelMultiInstanceActivityBehavior();
            }
            miBodyScope.setActivityBehavior(behavior);

            // loopCardinality
            Element loopCardinality = miLoopCharacteristics.element("loopCardinality");
            if (loopCardinality != null) {
                String loopCardinalityText = loopCardinality.getText();
                if (loopCardinalityText == null || "".equals(loopCardinalityText)) {
                    addError("loopCardinality must be defined for a multiInstanceLoopCharacteristics definition ", miLoopCharacteristics, id);
                }
                behavior.setLoopCardinalityExpression(expressionManager.createExpression(loopCardinalityText));
            }

                        Element completionCondition = miLoopCharacteristics.element("completionCondition");
            if (completionCondition != null) {
                String completionConditionText = completionCondition.getText();
                behavior.setCompletionConditionExpression(expressionManager.createExpression(completionConditionText));
            }

            // activiti:collection
            String collection = miLoopCharacteristics.attributeNS(CAMUNDA_BPMN_EXTENSIONS_NS, "collection");
            if (collection != null) {
                if (collection.contains("{")) {
                    behavior.setCollectionExpression(expressionManager.createExpression(collection));
                } else {
                    behavior.setCollectionVariable(collection);
                }
            }

                        Element loopDataInputRef = miLoopCharacteristics.element("loopDataInputRef");
            if (loopDataInputRef != null) {
                String loopDataInputRefText = loopDataInputRef.getText();
                if (loopDataInputRefText != null) {
                    if (loopDataInputRefText.contains("{")) {
                        behavior.setCollectionExpression(expressionManager.createExpression(loopDataInputRefText));
                    } else {
                        behavior.setCollectionVariable(loopDataInputRefText);
                    }
                }
            }

            // activiti:elementVariable
            String elementVariable = miLoopCharacteristics.attributeNS(CAMUNDA_BPMN_EXTENSIONS_NS, "elementVariable");
            if (elementVariable != null) {
                behavior.setCollectionElementVariable(elementVariable);
            }

            // dataInputItem
            Element inputDataItem = miLoopCharacteristics.element("inputDataItem");
            if (inputDataItem != null) {
                String inputDataItemName = inputDataItem.attribute("name");
                behavior.setCollectionElementVariable(inputDataItemName);
            }

            // Validation
            if (behavior.getLoopCardinalityExpression() == null && behavior.getCollectionExpression() == null && behavior.getCollectionVariable() == null) {
                addError("Either loopCardinality or loopDataInputRef/activiti:collection must been set", miLoopCharacteristics, id);
            }

            // Validation
            if (behavior.getCollectionExpression() == null && behavior.getCollectionVariable() == null && behavior.getCollectionElementVariable() != null) {
                addError("LoopDataInputRef/activiti:collection must be set when using inputDataItem or activiti:elementVariable", miLoopCharacteristics, id);
            }

            for (BpmnParseListener parseListener : parseListeners) {
                parseListener.parseMultiInstanceLoopCharacteristics(activityElement, miLoopCharacteristics, miBodyScope);
            }

            return miBodyScope;
        }
    }

    @Override
    public ActivityImpl parseExclusiveGateway(Element exclusiveGwElement, ScopeImpl scope) {
        ActivityImpl activity = createActivityOnScope(exclusiveGwElement, scope);
        activity.setActivityBehavior(new MyExclusiveGatewayActivityBehavior());

        parseAsynchronousContinuationForActivity(exclusiveGwElement, activity);

        parseExecutionListenersOnScope(exclusiveGwElement, activity);

        for (BpmnParseListener parseListener : parseListeners) {
            parseListener.parseExclusiveGateway(exclusiveGwElement, scope, activity);
        }
        return activity;
    }
//
//    /**
//     * Parses a userTask declaration.
//     */
//    public ActivityImpl parseUserTask(Element userTaskElement, ScopeImpl scope) {
//        ActivityImpl activity = createActivityOnScope(userTaskElement, scope);
//
//        parseAsynchronousContinuationForActivity(userTaskElement, activity);
//
//        TaskDefinition taskDefinition = parseTaskDefinition(userTaskElement, activity.getId(), activity, (ProcessDefinitionEntity) scope.getProcessDefinition());
//        TaskDecorator taskDecorator = new TaskDecorator(taskDefinition, expressionManager);
//
//        MyUserTaskActivityBehavior userTaskActivity = new MyUserTaskActivityBehavior(taskDecorator);
//        activity.setActivityBehavior(userTaskActivity);
//
//        parseProperties(userTaskElement, activity);
//        parseExecutionListenersOnScope(userTaskElement, activity);
//
//        for (BpmnParseListener parseListener : parseListeners) {
//            parseListener.parseUserTask(userTaskElement, scope, activity);
//        }
//        return activity;
//    }
}
