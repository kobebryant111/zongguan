//package com.tansun.wf.activiti.converter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import org.camunda.bpm.bpmn.model.FlowElement;
//import org.camunda.bpm.editor.language.json.converter.ReceiveTaskJsonConverter;
//import org.camunda.bpm.editor.language.json.converter.util.JsonConverterUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//
// camunda-TODO
//public class ReceiveTaskExpandJsonConverter extends ReceiveTaskJsonConverter {
//
//    private static Logger logger = LoggerFactory.getLogger(ReceiveTaskExpandJsonConverter.class);
//
//    public static final String EXTENSION_DATA_SOURCE = "extensiondatasource";
//
//    @Override
//    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
//        FlowElement task = super.convertJsonToElement(elementNode, modelNode, shapeMap);
//
//        //添加扩展属性
//        try {
//            JsonNode property = JsonConverterUtil.getProperty(EXTENSION_DATA_SOURCE, elementNode);
//            WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(property,task,EXTENSION_DATA_SOURCE);
//        } catch (JsonProcessingException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//        return task;
//    }
//}
