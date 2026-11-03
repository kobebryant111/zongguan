package com.tansun.wf.decision.service;

import com.tansun.wf.activiti.service.SendMailService;
import com.tansun.wf.camunda.bpmn.instance.SendTaskEmailAddress;
import com.tansun.wf.camunda.bpmn.instance.SendTaskEmailContext;
import com.tansun.wf.decision.util.ElUtil;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.model.bpmn.impl.instance.SendTaskImpl;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 发送邮件
 * @author ldh
 */
@Service("SendTaskEmailDelegate")
public class SendTaskEmailDelegate implements JavaDelegate {

    @Autowired
    public SendMailService sendMailService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        SendTaskImpl flow = (SendTaskImpl)execution.getBpmnModelElementInstance();
        ExtensionElements extensionElements = flow.getExtensionElements();
        SendTaskEmailAddress address = (SendTaskEmailAddress)extensionElements.getChildElementsByType(SendTaskEmailAddress.class).toArray()[0];
        SendTaskEmailContext context = (SendTaskEmailContext)extensionElements.getChildElementsByType(SendTaskEmailContext.class).toArray()[0];
        Map<String, Object> var = execution.getVariables();
        try {
            String contextStr = ElUtil.readExpr(context.getTextContent(), var);
            sendMailService.sendEmail(Collections.singletonList(address.getTextContent()), contextStr);
        }catch(SpelEvaluationException e) {
            throw new RuntimeException("邮件内容解析参数缺失", e);
        }catch (Exception e){
            throw new RuntimeException("邮件发送失败", e);
        }
    }
}
