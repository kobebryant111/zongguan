package com.tansun.wf.extend.behavior.factory;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParse;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParser;
import org.camunda.bpm.engine.impl.cfg.BpmnParseFactory;

public class MyBpmnParseFactory implements BpmnParseFactory {
    @Override
    public BpmnParse createBpmnParse(BpmnParser bpmnParser) {
        return new MyBpmnParse(bpmnParser);
    }
}
