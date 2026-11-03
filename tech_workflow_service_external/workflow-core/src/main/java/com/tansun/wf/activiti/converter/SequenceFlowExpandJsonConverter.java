//package com.tansun.wf.activiti.converter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import org.camunda.bpm.bpmn.model.BaseElement;
//import org.camunda.bpm.bpmn.model.BpmnModel;
//import org.camunda.bpm.bpmn.model.FlowElement;
//import org.camunda.bpm.bpmn.model.FlowElementsContainer;
//import org.camunda.bpm.editor.language.json.converter.ActivityProcessor;
//import org.camunda.bpm.editor.language.json.converter.SequenceFlowJsonConverter;
//import org.camunda.bpm.editor.language.json.converter.util.JsonConverterUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//
//import java.util.Map;
//
//import static com.tansun.wf.activiti.converter.UserTaskExpandJsonConverter.getExtensionValue;
//
///**
// * @author: 卓福深 zhuofushen@tansun.com.cn
// * @类描述 <pre></pre>
// * @创建时间 2021/1/1
// * @版本 v1.01
// * @修改记录 <pre>
// * 版本 修改人 修改时间 修改内容描述
// * ----------------------------------------------
// * ----------------------------------------------
// * </pre>
// */
//// camunda-TODO
//public class SequenceFlowExpandJsonConverter  extends SequenceFlowJsonConverter {
//    private static final String LEVEL_VALUE = "levelValue";
//    private static final String CONDITION_LABEL = "conditionsequenceflowlabel";
//
//    private static Logger logger = LoggerFactory.getLogger(SequenceFlowExpandJsonConverter.class);
//
//    public SequenceFlowExpandJsonConverter() {
//    }
//
//    @Override
//    public void convertToJson(BaseElement baseElement, ActivityProcessor processor, BpmnModel model, FlowElementsContainer container, ArrayNode shapesArrayNode, double subProcessX, double subProcessY) {
//        super.convertToJson(baseElement, processor, model, container, shapesArrayNode, subProcessX, subProcessY);
//        //流转条件优先级配置
//        String levelValue = getExtensionValue(baseElement,LEVEL_VALUE);
//        if(!org.springframework.util.StringUtils.isEmpty(levelValue)){
//            if (shapesArrayNode.size() > 0 ){
//                JsonNode jsonNode = shapesArrayNode.get(shapesArrayNode.size() - 1);
//                ((ObjectNode) jsonNode.get("properties")).put(LEVEL_VALUE, levelValue);
//            }
//        }
//
//        //流转条件lablel
//        String conditionLabel = getExtensionValue(baseElement,CONDITION_LABEL);
//        if(!org.springframework.util.StringUtils.isEmpty(conditionLabel)){
//            if (shapesArrayNode.size() > 0 ){
//                JsonNode jsonNode = shapesArrayNode.get(shapesArrayNode.size() - 1);
//                ((ObjectNode) jsonNode.get("properties")).put(CONDITION_LABEL, conditionLabel);
//            }
//        }
//    }
//
//
//
//    @Override
//    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
//        FlowElement element = super.convertJsonToElement(elementNode, modelNode, shapeMap);
//        //优先级配置
//        JsonNode levelValue = JsonConverterUtil.getProperty(LEVEL_VALUE,elementNode);
//        if(levelValue != null){
//            try {
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(levelValue,element,LEVEL_VALUE);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//                logger.error(e.getMessage());
//            }
//        }
//        //流转条件lablel
//        JsonNode conditionLabel = JsonConverterUtil.getProperty(CONDITION_LABEL,elementNode);
//        if(conditionLabel != null){
//            try {
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(conditionLabel,element,CONDITION_LABEL);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//                logger.error(e.getMessage());
//            }
//        }
//        return element;
//    }
//
//}
