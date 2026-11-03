package com.tansun.wf.platform.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tansun.wf.platform.service.BpmnModelService;
import com.tansun.workflow.model.ProcessInfo;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.ByteArrayEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.Resource;
import org.camunda.bpm.model.bpmn.BpmnModelException;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Documentation;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class BpmnModelServiceImpl implements BpmnModelService {
    private Logger logger = LoggerFactory.getLogger(getClass());	
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    ProcessEngine processEngine;

	@Override
	public ObjectNode findDiagram(ProcessInfo processInfo) {

        String processDefineId = processInfo.getProcessDefineId();
        String processDefineKey = processInfo.getProcessDefineKey();
        String tenantId = processInfo.getTenantId();
        if(StringUtils.isBlank(processDefineId)){
            ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefineKey).tenantIdIn(tenantId).latestVersion().singleResult();
            processDefineId = definition.getId();
        }

        ObjectNode modelNode = null;

        BpmnModelInstance model = repositoryService.getBpmnModelInstance(processDefineId);
        Process process = model.getModelElementsByType(Process.class).iterator().next();
        if (model != null) {
            try {
                modelNode = objectMapper.createObjectNode();
                modelNode.put("name", process.getName());
                modelNode.put("modelId", processDefineId);
                modelNode.put("revision",1);
                Collection<Documentation> documentations = process.getDocumentations();
                String textContent="";
                for (Documentation documentation:documentations){
                    textContent = documentation.getTextContent();
                }
                List<Resource> source = repositoryService.getDeploymentResources(processDefineId);

                if(source.size()>0){
                    ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(new String(source.get(0).getBytes(),"UTF-8"));
                    ObjectNode properties = (ObjectNode)editorJsonNode.get("properties");
                    properties.put("name",process.getName());
                    properties.put("process_id",process.getId());
                    properties.put("description",textContent);
                    modelNode.set("model", editorJsonNode);
                }else{
                    ObjectNode emptyNode = objectMapper.createObjectNode();
                    emptyNode.put("id","canvas");
                    emptyNode.put("resourceId","canvas");

                    ObjectNode stencilset = objectMapper.createObjectNode();
                    stencilset.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
                    emptyNode.set("stencilset",stencilset);

                    ObjectNode properties = objectMapper.createObjectNode();
                    properties.put("process_id",process.getId());
                    properties.put("name",process.getName());
                    properties.put("description",textContent);
                    emptyNode.set("properties",properties);
                    modelNode.set("model", emptyNode);
                }
            } catch (Exception e) {
                logger.error("Error creating model JSON", e);
                throw new BpmnModelException("Error creating model JSON", e);
            }
        }
        return modelNode;
	}
	
	@Override
	public  void  saveModel(String  modelId,MultiValueMap<String,String>  values) throws Exception {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(modelId).singleResult();
		String definitionId = processDefinition.getId();
        String json_xml1 = values.getFirst("json_xml");
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity("source",values.getFirst("json_xml").getBytes("utf-8"));
		byteArrayEntity.setDeploymentId(definitionId);
        String json_xml = values.get("json_xml").get(0).toString();
        JSONObject jsonObject = JSONObject.parseObject(json_xml);
        Map<String, Object> properties = jsonObject.getJSONObject("properties").getInnerMap();
        String name = (String)properties.get("name");
        String description = (String) properties.get("description");
        System.out.println(description);
        if ((String)properties.get("description")==null || "".equals((String)properties.get("description"))){
            properties.put("description"," ");
        }
//        JSONObject name = properties.getJSONObject("name");
        byteArrayEntity.setName(name+"_source");
//        byteArrayEntity.setName(list1.get(0));
		List<Resource> list = repositoryService.getDeploymentResources(definitionId);
		String idTemp = null;
		for(Resource r : list){
			if(r.getName().contains("source")){
				idTemp = r.getId();
				break;
			}
		}

		final String deleteId = idTemp;
		// 存json格式的流程图
		ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
		CommandExecutor commandExecutor = processEngineConfiguration.getCommandExecutorTxRequired();
		commandExecutor.execute(commandContext -> {
			if(deleteId!=null) {
				commandContext.getByteArrayManager().deleteByteArrayById(deleteId);
			}
			commandContext.getByteArrayManager().insertByteArray(byteArrayEntity);
			return null;
		});
	}

}
