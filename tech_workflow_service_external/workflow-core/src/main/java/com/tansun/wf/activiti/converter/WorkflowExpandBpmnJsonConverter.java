//package com.tansun.wf.activiti.converter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import org.apache.commons.lang3.StringUtils;
//import org.camunda.bpm.model.bpmn.instance.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
///**
// * 工作流节点扩展配置服务类
// *
// */
//// camunda-TODO
//@Service("workflowExpandBpmnJsonConverter")
//public class WorkflowExpandBpmnJsonConverter extends BpmnJsonConverter{
//
//	private static Logger logger = LoggerFactory.getLogger(WorkflowExpandBpmnJsonConverter.class);
//
//	public static final String EXPAND_NAMESPACE_PREFIX = "tansun";//扩展命名空间前缀
//
//
//	public WorkflowExpandBpmnJsonConverter(){
//		//替换源码中的bpmnJsonConverter
//		super.convertersToBpmnMap.put(STENCIL_TASK_RECEIVE, ReceiveTaskExpandJsonConverter.class);
//		super.convertersToBpmnMap.put(STENCIL_TASK_SERVICE,ServiceTaskExpandJsonConverter.class);
//		super.convertersToBpmnMap.put(STENCIL_TASK_USER,UserTaskExpandJsonConverter.class);
//        super.convertersToBpmnMap.put(STENCIL_SEQUENCE_FLOW,SequenceFlowExpandJsonConverter.class);
//
//
//		//替换源码中
//        super.convertersToJsonMap.put(ReceiveTask.class,ReceiveTaskExpandJsonConverter.class);
//        super.convertersToJsonMap.put(ServiceTask.class,ServiceTaskExpandJsonConverter.class);
//        super.convertersToJsonMap.put(UserTask.class,UserTaskExpandJsonConverter.class);
//        super.convertersToJsonMap.put(SequenceFlow.class,SequenceFlowExpandJsonConverter.class);
//    }
//
//    /** ************************************ json to xml *************************************/
//    /**
//     * json 转 xml 扩展
//     * @param modelNode
//     * @return
//     */
//    @Override
//    public BpmnModel convertToBpmnModel(JsonNode modelNode) {
//        BpmnModel bpmnModel = super.convertToBpmnModel(modelNode);
//        new ProcessPropertyExpandJsonConverter().convertJsonToElement(bpmnModel,modelNode);
//        return bpmnModel;
//    }
//
//    /**
//     * 将流程的扩展属性存储到Process
//     * @param bpmnModel
//     * @param propertyName
//     * @param elementNode
//     */
//    public static void convertJsonToProcess(BpmnModel bpmnModel, String propertyName, JsonNode elementNode){
//        if(elementNode != null){
//            ObjectMapper objectMapper = new ObjectMapper();
//            String propertyJson = null;
//            try {
//                propertyJson = objectMapper.writeValueAsString(elementNode);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            if(StringUtils.isBlank(propertyJson)){
//                return ;
//            }
//            ExtensionElement element = new ExtensionElement();
//            element.setElementText(propertyJson);
//            element.setNamespacePrefix(EXPAND_NAMESPACE_PREFIX);
//            element.setNamespace(EXPAND_NAMESPACE_PREFIX);
//            element.setName(propertyName);
//            bpmnModel.getMainProcess().addExtensionElement(element);
//        }
//    }
//
//    /**
//     * 将json配置存储到扩展属性中
//     * @param elementNode
//     * @param task
//     */
//    public static void convertJsonToExpandProperties(JsonNode elementNode, BaseElement task, String propertyName) throws JsonProcessingException {
//        if(elementNode != null){
//            ObjectMapper objectMapper = new ObjectMapper();
//            String propertyJson = objectMapper.writeValueAsString(elementNode);
//            if(StringUtils.isBlank(propertyJson)){
//                return ;
//            }
//            ExtensionElement extensionElement = new ExtensionElement();
//            extensionElement.setElementText(propertyJson);
//            extensionElement.setNamespacePrefix(EXPAND_NAMESPACE_PREFIX);
//            extensionElement.setNamespace(EXPAND_NAMESPACE_PREFIX);
//            extensionElement.setName(propertyName);
//            task.addExtensionElement(extensionElement);
//        }
//    }
//
//    /** ************************************ xml to json *************************************/
//
//    /**
//     * xml 转 json 扩展
//     * @param model
//     * @return
//     */
//    @Override
//    public ObjectNode convertToJson(BpmnModel model) {
//        ObjectNode jsonNodes = super.convertToJson(model);
////        properties.set("", properties);
//        jsonNodes = new ProcessPropertyExpandJsonConverter().convertElementToJson(jsonNodes,model);
//        return jsonNodes;
//    }
//
//}
