//package com.tansun.wf.activiti.converter;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import org.camunda.bpm.bpmn.model.BaseElement;
//import org.camunda.bpm.bpmn.model.ExtensionElement;
//import org.camunda.bpm.bpmn.model.FlowElement;
//import org.camunda.bpm.editor.language.json.converter.UserTaskJsonConverter;
//import org.camunda.bpm.editor.language.json.converter.util.JsonConverterUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//// camunda-TODO
//public class UserTaskExpandJsonConverter extends UserTaskJsonConverter {
//
//    private static Logger logger = LoggerFactory.getLogger(UserTaskExpandJsonConverter.class);
//
//    private static final String ASSIGNMENT = "assignment";
//
//    private static final String BACK_TYPE = "backtype";
//
//    private static final String USERTASKFUNCTION = "usertaskfunction";
//
//    private static final String RECEIVE_CLASS = "receive_class";
//
//    private static final String DESGIN_FORM = "designform";
//
//    private static final String CARBON_COPY = "carboncopy";
//    private static final String TIME_SET = "timeset";
//
//    private static final String FORM_SCENE = "formscene";
//
//    /**
//     * 投票策略
//     */
//    private static final String VOTING_STRATEGY = "votingstrategy";
//
//    @Override
//    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
//        FlowElement element = super.convertJsonToElement(elementNode, modelNode, shapeMap);
//        try {
//            JsonNode usertaskassignment = JsonConverterUtil.getProperty(PROPERTY_USERTASK_ASSIGNMENT, elementNode);
//            if(usertaskassignment != null){
//                JsonNode assignment = usertaskassignment.get("assignment");
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(assignment,element,ASSIGNMENT);
//            }
//
//            //回退属性
//            JsonNode backType = JsonConverterUtil.getProperty(BACK_TYPE,elementNode);
//            if(backType != null){
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(backType,element,BACK_TYPE);
//            }
//
//            //表单视图
//            JsonNode designform = JsonConverterUtil.getProperty(DESGIN_FORM,elementNode);
//            if(designform != null){
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(designform,element,DESGIN_FORM);
//            }
//
//
//            //节点功能设置
//            JsonNode userTaskFunction = JsonConverterUtil.getProperty(USERTASKFUNCTION,elementNode);
//            if(userTaskFunction != null){
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(userTaskFunction,element,USERTASKFUNCTION);
//            }
//
//            //业务处理类
//            JsonNode receiveClass = JsonConverterUtil.getProperty(RECEIVE_CLASS,elementNode);
//            if(receiveClass != null){
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(receiveClass,element,RECEIVE_CLASS);
//            }
//
//          //时间设置（催办）
//            JsonNode timeset = JsonConverterUtil.getProperty(TIME_SET,elementNode);
//            if(timeset != null){
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(timeset,element,TIME_SET);
//            }
//
//            //抄送
//            JsonNode carbonCopy = JsonConverterUtil.getProperty(CARBON_COPY,elementNode);
//            if(carbonCopy != null){
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(carbonCopy,element,CARBON_COPY);
//            }
//
//            //表单应用场景
//            JsonNode formScene = JsonConverterUtil.getProperty(FORM_SCENE,elementNode);
//            if(formScene != null){
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(formScene,element,FORM_SCENE);
//            }
//
//            //投票策略
//            JsonNode votingStrategy = JsonConverterUtil.getProperty(VOTING_STRATEGY,elementNode);
//            if(votingStrategy != null){
//                WorkflowExpandBpmnJsonConverter.convertJsonToExpandProperties(votingStrategy,element,VOTING_STRATEGY);
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//        }
//        return element;
//    }
//
//    @Override
//    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
//        super.convertElementToJson(propertiesNode, baseElement);
//        //处理人
//        String assignment = getExtensionValue(baseElement, ASSIGNMENT);
//        if(!StringUtils.isEmpty(assignment)){
//            String jsonStr = "{ \"assignment\":" + assignment + "}";
//            propertiesNode.put(PROPERTY_USERTASK_ASSIGNMENT, toJsonNode(jsonStr));
//        }
//        //抄送
//        String carbonCopy = getExtensionValue(baseElement, CARBON_COPY);
//        if(!StringUtils.isEmpty(carbonCopy)){
//            propertiesNode.put(CARBON_COPY,toJsonNode(carbonCopy));
//        }
//
//        //回退
//        String backType = getExtensionValue(baseElement, BACK_TYPE);
//        if(!StringUtils.isEmpty(backType)){
//            propertiesNode.put(BACK_TYPE,backType);
//        }
//
//        //节点功能设置
//        String userTaskFunction = getExtensionValue(baseElement,USERTASKFUNCTION);
//        if(!StringUtils.isEmpty(userTaskFunction)){
//            propertiesNode.put(USERTASKFUNCTION,toJsonNode(userTaskFunction));
//        }
//
//        //表单视图
//        String designForm = getExtensionValue(baseElement,DESGIN_FORM);
//        if(!StringUtils.isEmpty(designForm)){
//            propertiesNode.put(DESGIN_FORM,toJsonNode(designForm));
//        }
//
//
//        //业务处理类
//        String receiveClass = getExtensionValue(baseElement,RECEIVE_CLASS);
//        if(!StringUtils.isEmpty(receiveClass)){
//            propertiesNode.put(RECEIVE_CLASS,receiveClass);
//        }
//
//        //时间设置（催办）
//        String timesetValue = getExtensionValue(baseElement, TIME_SET);
//        if(null!=timesetValue&&!"".equals(timesetValue)&&!"\"\"".equals(timesetValue)){
//          propertiesNode.put(TIME_SET, toJsonNode(timesetValue));
//        }
//
//        //投票策略
//        String votingStrategy = getExtensionValue(baseElement, VOTING_STRATEGY);
//        if(!StringUtils.isEmpty(votingStrategy)){
//            propertiesNode.put(VOTING_STRATEGY,votingStrategy);
//        }
//        //表单应用场景
//        String formScene = getExtensionValue(baseElement,FORM_SCENE);
//        if(!StringUtils.isEmpty(formScene)){
//            propertiesNode.put(FORM_SCENE,formScene);
//        }
//
//    }
//
//    public static JsonNode toJsonNode(String jsonStr) {
//        JsonNode jsonNode = null;
//        try {
//            jsonNode = new ObjectMapper().readTree(jsonStr);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return jsonNode;
//    }
//
//
//    /**
//     * 获取扩展属性的值
//     *
//     * @param baseElement
//     * @param propertyName
//     * @return
//     */
//    public static String getExtensionValue(BaseElement baseElement, String propertyName) {
//        Map<String, List<ExtensionElement>> extensionElements = baseElement.getExtensionElements();
//        if (extensionElements == null) {
//            return "";
//        }
//        List<ExtensionElement> extensionElementList = extensionElements.get(propertyName);
//        if (CollectionUtils.isEmpty(extensionElementList)) {
//            return "";
//        }
//        ExtensionElement extensionElement = extensionElementList.get(0);
//        return trimQuotes(extensionElement.getElementText());
//    }
//
//    /**
//     * 去除字符串前后双引号
//     * @return
//     */
//    public static String trimQuotes(String str){
//        if(!StringUtils.isEmpty(str) && str.length() >1){
//            if("\"".equals(str.charAt(0)+"") && "\"".equals(str.charAt(str.length()-1)+"")){
//                return str.substring(1,str.length()-1);
//            }
//        }
//        return str;
//    }
//}
