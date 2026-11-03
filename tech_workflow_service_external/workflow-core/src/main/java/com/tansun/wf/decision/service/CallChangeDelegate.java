package com.tansun.wf.decision.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 用于服务节点测试，将N1值和N2值对调，以此演示服务节点起作用
 */
@Service("change")
public class CallChangeDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        // 获取所有流程
    	System.out.println("-----");
        Set<String> variableNames = execution.getVariableNames();
        System.out.println("aa"+variableNames);
    }
}
