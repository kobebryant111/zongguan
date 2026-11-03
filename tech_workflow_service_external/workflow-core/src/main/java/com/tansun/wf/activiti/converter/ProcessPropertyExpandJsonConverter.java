//package com.tansun.wf.activiti.converter;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//import org.camunda.bpm.bpmn.model.BpmnModel;
//import org.camunda.bpm.bpmn.model.ExtensionElement;
//import org.camunda.bpm.bpmn.model.Process;
//import org.camunda.bpm.editor.language.json.converter.BpmnJsonConverterUtil;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
///**
// * 流程基本属性转化类
// */
//// camunda-TODO
//public class ProcessPropertyExpandJsonConverter {
//
//    /**
//     * 流程启动回调类
//     */
//    public static final String PROCESS_STARTED_CLASS = "process_started_class";
//
//    /**
//     * 流程回调类
//     */
//    public static final String PROCESS_COMPLETED_CLASS = "process_completed_class";
//
//
//    private static final String PROCESS_DESIGN = "processdesign";
//
//	/**
//     * 按钮扩展
//     */
//    public static final String USERTASKFUNCTION = "usertaskfunction";
//
//    /**
//     * xml to json
//     *
//     * @param
//     * @param bpmnModel
//     */
//    public ObjectNode convertElementToJson(ObjectNode jsonBpmn, BpmnModel bpmnModel) {
//
//        ObjectNode properties = jsonBpmn.get("properties").deepCopy();
//        //流程启动回调类
//        String process_started_class = getExtensionElement(bpmnModel, PROCESS_STARTED_CLASS);
//        properties.put(PROCESS_STARTED_CLASS, process_started_class);
//
//        //流程回调类
//        String process_completed_class = getExtensionElement(bpmnModel, PROCESS_COMPLETED_CLASS);
//        properties.put(PROCESS_COMPLETED_CLASS, process_completed_class);
//
//        //流程设计方案
//        String processDesign = getExtensionElement(bpmnModel,PROCESS_DESIGN);
//        properties.put(PROCESS_DESIGN,processDesign);
//
//        jsonBpmn.put("properties", properties);
//        return jsonBpmn;
//    }
//
//    private String getExtensionElement(BpmnModel bpmnModel, String propertyName) {
//        String elementText = "";
//        Process mainProcess = bpmnModel.getMainProcess();
//        if (mainProcess == null) {
//            return elementText;
//        }
//        Map<String, List<ExtensionElement>> extensionElements = mainProcess.getExtensionElements();
//        if (extensionElements == null) {
//            return elementText;
//        }
//        List<ExtensionElement> extensionElement = extensionElements.get(propertyName);
//        if (CollectionUtils.isNotEmpty(extensionElement)) {
//            ExtensionElement first = extensionElement.get(0);
//            String elementTextStr = first.getElementText();
//            if (!"".equals(elementTextStr) && elementTextStr != null) {
//                elementText = trimQuotes(elementTextStr);
//            }
//            System.out.println(first.getElementText());
//        }
//        return elementText;
//    }
//
//    /**
//	 * 对扩展usertask类型扩展字段转换为xml
//	 * @param processDefId
//	 * @param taskDefId
//	 * @param propertyName
//	 * @return
//	 */
//	/*public List<Map<String, String>> getNodeExtensionProperty(BpmnModel bpmnModel, String propertyName) {
//		Map<String, String> map = new HashMap<String, String>();
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
//		for (FlowElement e: flowElements) {
//            if(e instanceof UserTask){
//            	List<ExtensionElement>  extensionElements = e.getExtensionElements().get(propertyName);
//            	if (extensionElements.size() != 0) {
//            		String id = e.getId();
//            		String usertaskfunction = extensionElements.get(0).getElementText();
//            		usertaskfunction = trimQuotes(usertaskfunction);
//            		usertaskfunction = usertaskfunction.replaceAll("\\\\", "");
//            		map.put("id", id);
//            		map.put(propertyName, usertaskfunction);
//            		list.add(map);
//				}
//            }
//		}
//		return list;
//	}*/
//
//    /**
//     * json to xml
//     *
//     * @param bpmnModel
//     * @param modelNode
//     */
//    public void convertJsonToElement(BpmnModel bpmnModel, JsonNode modelNode) {
//
//        //流程启动回调类
//        JsonNode startedClass = BpmnJsonConverterUtil.getProperty(PROCESS_STARTED_CLASS, modelNode);
//        WorkflowExpandBpmnJsonConverter.convertJsonToProcess(bpmnModel, PROCESS_STARTED_CLASS, startedClass);
//
//        //流程回调类
//        JsonNode completedClass = BpmnJsonConverterUtil.getProperty(PROCESS_COMPLETED_CLASS, modelNode);
//        WorkflowExpandBpmnJsonConverter.convertJsonToProcess(bpmnModel, PROCESS_COMPLETED_CLASS, completedClass);
//
//        //流程设计方案
//        JsonNode processDesign = BpmnJsonConverterUtil.getProperty(PROCESS_DESIGN, modelNode);
//        WorkflowExpandBpmnJsonConverter.convertJsonToProcess(bpmnModel, PROCESS_DESIGN, processDesign);
//    }
//
//    /**
//     * 去除字符串前后双引号
//     *
//     * @return
//     */
//    private String trimQuotes(String str) {
//        if (!StringUtils.isEmpty(str) && str.length() > 1) {
//            if ("\"".equals(str.charAt(0) + "") && "\"".equals(str.charAt(str.length() - 1) + "")) {
//                return str.substring(1, str.length() - 1);
//            }
//        }
//        return str;
//    }
//
//
//}
