//package com.tansun.wf.activiti.converter;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
//import org.camunda.bpm.bpmn.model.BaseElement;
//import org.camunda.bpm.bpmn.model.FlowElement;
//import org.camunda.bpm.editor.language.json.converter.ServiceTaskJsonConverter;
//import org.camunda.bpm.editor.language.json.converter.util.JsonConverterUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//
//import java.util.Map;
//
//
//public class ServiceTaskExpandJsonConverter extends ServiceTaskJsonConverter {
//
//    private static Logger logger = LoggerFactory.getLogger(ServiceTaskExpandJsonConverter.class);
//
//    public static final String RULE_CONFIG = "ruleconfig";//规则配置
//    public static final String RULE_IN_BANK = "ruleinbank";//外部规则
//    public static final String REMOTE_INTERFACE = "remoteinterface";//服务任务远程接口
//
//    @Override
//    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
//        FlowElement task = super.convertJsonToElement(elementNode, modelNode, shapeMap);
//
//        try {
//            JsonNode property = JsonConverterUtil.getProperty(RULE_CONFIG, elementNode);
//            WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(property,task,RULE_CONFIG);
//            property = JsonConverterUtil.getProperty(RULE_IN_BANK, elementNode);
//            WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(property,task,RULE_IN_BANK);
//            property = JsonConverterUtil.getProperty(REMOTE_INTERFACE, elementNode);
//            WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(property,task,REMOTE_INTERFACE);
//        } catch (JsonProcessingException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//
//        return task;
//    }
//
//    @Override
//    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
//
//    	String ruleconfig = UserTaskExpandJsonConverter.getExtensionValue(baseElement,RULE_CONFIG);
//        if(!StringUtils.isEmpty(ruleconfig)){
//            propertiesNode.put(RULE_CONFIG,UserTaskExpandJsonConverter.toJsonNode(ruleconfig));
//            String servicetaskdelegateexpression = "${rule}";
//            if(!StringUtils.isEmpty(servicetaskdelegateexpression)){
//            	propertiesNode.put("servicetaskdelegateexpression",servicetaskdelegateexpression);
//            }
//        }
//    	String ruleinbank = UserTaskExpandJsonConverter.getExtensionValue(baseElement,RULE_IN_BANK);
//        if(!StringUtils.isEmpty(ruleinbank)){
//            propertiesNode.put(RULE_IN_BANK,UserTaskExpandJsonConverter.toJsonNode(ruleinbank));
//            String servicetaskdelegateexpression = "${rule}";
//            if(!StringUtils.isEmpty(servicetaskdelegateexpression)){
//            	propertiesNode.put("servicetaskdelegateexpression",servicetaskdelegateexpression);
//            }
//        }
//    	String remoteinterface = UserTaskExpandJsonConverter.getExtensionValue(baseElement,REMOTE_INTERFACE);
//        if(!StringUtils.isEmpty(remoteinterface)){
//            propertiesNode.put(REMOTE_INTERFACE,UserTaskExpandJsonConverter.toJsonNode(remoteinterface));
//            //远程接口默认使用httpServiceTaskDelegate
//            String servicetaskdelegateexpression = "${httpServiceTaskDelegate}";
//            if(!StringUtils.isEmpty(servicetaskdelegateexpression)){
//                propertiesNode.put("servicetaskdelegateexpression",servicetaskdelegateexpression);
//            }
//        }
//    }
//}