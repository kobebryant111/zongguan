package com.tansun.wf.platform.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.handler.QueryHandler;
import com.tansun.wf.activiti.handler.QueryResultHandler;
import com.tansun.wf.activiti.service.WorkflowModelService;
import com.tansun.wf.activiti.util.ResultUtil;
import com.tansun.wf.platform.mapper.ActProcessMapper;
import com.tansun.wf.platform.mapper.WfClassifyMapper;
import com.tansun.wf.platform.mapper.WfTenantMapper;
import com.tansun.wf.platform.model.WfClassifyEntity;
import com.tansun.wf.platform.query.WfClassifyQuery;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.ByteArrayEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.repository.Resource;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.Documentation;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.stream.XMLInputFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/4/29
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class  WfProcessCreateService  {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    WfTenantService wfTenantService;

    @Autowired
    WfClassifyService wfClassifyService;

    @Autowired
     ActProcessMapper actProcessMapper;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    WorkflowModelService  workflowModelService;


    private static final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();;

    static {
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, true);
    }

    // camunda-TODO
//    private static final BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();;

    // camunda-TODO
//    private static final WorkflowExpandBpmnJsonConverter bpmnJsonConverter = new WorkflowExpandBpmnJsonConverter() ;

    @Autowired
    WfClassifyMapper wfClassifyMapper;

    @Autowired
    private QueryHandler queryHandler;

    public static final String SUCCEED = WorkflowConstants.SUCCEED;
    public static final String FAILED = WorkflowConstants.FAILED;

    public List<Map> findClassifyList(Map<String,Object> reqMap) {
        String tenantId = (String) reqMap.get("tenantId");
        String type = (String) reqMap.get("type");
        List<Map> result = new ArrayList<>();
        WfClassifyQuery  classQuery =  new  WfClassifyQuery();
        classQuery.setTenantId(tenantId);
        List<WfClassifyEntity> classifyList = wfClassifyMapper.findList(classQuery);
        if (classifyList.size()!=0) {
            for (WfClassifyEntity wfClassifyEntity : classifyList) {
                Map<Object, Object> item = new HashMap<>();
                item.putAll(new BeanMap(wfClassifyEntity));
                item.put("pId", wfClassifyEntity.getParentId());
                item.put("isCategoryDir",1);
                if (!StringUtils.isEmpty(type)){
                    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
                    List<ProcessDefinition> list = processDefinitionQuery.tenantIdIn(tenantId).processDefinitionCategory(wfClassifyEntity.getId()).latestVersion().list();
                    for (ProcessDefinition processDefinition:list){
                        Map<Object, Object> processItem = new HashMap<>();
                        ProcessBean processBean=new ProcessBean();
                        processBean.setProcessName(processDefinition.getName());
                        processBean.setName(processDefinition.getName()+"("+processDefinition.getKey()+")");
                        processBean.setProcessDefineKey(processDefinition.getKey());
                        processItem.put("pId",wfClassifyEntity.getId());
                        processItem.putAll(new BeanMap(processBean));
                        processItem.put("isCategoryDir",0);
                        result.add(processItem);
                    }
                }
                result.add(item);
            }
        }else {
            WfClassifyEntity wfClassifyEntity = new WfClassifyEntity();
            wfClassifyEntity.setCode("defaultCode");
            wfClassifyEntity.setIsDefault(1);
            wfClassifyEntity.setCreateDate(new Date());
            wfClassifyEntity.setParentId("0");
            wfClassifyEntity.setName("默认分类");
            wfClassifyEntity.setDelFlag("0");
            wfClassifyEntity.setUseable("1");
            wfClassifyEntity.setTenantId(tenantId);
            wfClassifyEntity.setRemarks("系统默认创建");
            wfClassifyService.save(wfClassifyEntity);
            Map<Object, Object> item = new HashMap<>();
            item.putAll(new BeanMap(wfClassifyEntity));
            item.put("pId", wfClassifyEntity.getParentId());
            result.add(item);
        }

        return result;
    }

    @Transactional
    public ProcessBean createModel(String classifyId, String key, String name, String description, String tenantId) throws Exception {
        ProcessBean processBean = new ProcessBean();
        if(StringUtils.isBlank(classifyId)){
            ResultUtil.setReturnCode(processBean, FAILED, "流程分类不能为空");
            return processBean;
        }
        if(StringUtils.isBlank(name) || StringUtils.isBlank(key)){
            ResultUtil.setReturnCode(processBean, FAILED, "键或名称不能为空");
            return processBean;
        }
        // camunda-TODO
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().tenantIdIn(tenantId).processDefinitionKey(key).list();
        if (list.size() != 0) {
            ResultUtil.setReturnCode(processBean, FAILED, "流程键值已存在");
            return processBean;
        }

//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode editorNode = objectMapper.createObjectNode();
//        editorNode.put("id", "canvas");
//        editorNode.put("resourceId", "canvas");
//        ObjectNode stencilSetNode = objectMapper.createObjectNode();
//        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
//        editorNode.put("stencilset", stencilSetNode);
//        ObjectNode properties = objectMapper.createObjectNode();
//        properties.put("process_id", key);
//        properties.put("name", name);
//        editorNode.put("properties", properties);

        // camunda-TODO
        BpmnModelInstance emptyModel = Bpmn.createExecutableProcess(key).name(name).documentation(description).done();
        Definitions definitions = emptyModel.getDefinitions();
        // 分类
        definitions.setTargetNamespace(classifyId);
        Deployment deploy = repositoryService.createDeployment()
                .tenantId(tenantId)
                .addModelInstance(key+".bpmn", emptyModel).deploy();

//        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        // camunda-TODO
//        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
//        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
//        description = StringUtils.defaultString(description);
//        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
//        modelData.setMetaInfo(modelObjectNode.toString());
//        modelData.setName(name);
//        modelData.setTenantId(tenantId);
//        modelData.setKey(StringUtils.defaultString(key));
//        modelData.setCategory(classifyId);
//
//        repositoryService.saveModel(modelData);
//        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        processBean.setResultObject(deploy.getId());
        ResultUtil.setReturnCode(processBean, SUCCEED, "创建成功");

        return processBean;
    }

    public ProcessBean suspendOrActivate(String procDefId, String operate) {
        if("suspend".equals(operate)){
            repositoryService.suspendProcessDefinitionById(procDefId);
        }else if("activate".equals(operate)){
            repositoryService.activateProcessDefinitionById(procDefId);
        }
        return new ProcessBean(SUCCEED,"操作成功");
    }

    @Transactional
    public ProcessBean copyModel(String classifyId, String modelId, String newKey, String name, String description,
                                 String tenantId) throws IOException {
        ProcessBean processBean = new ProcessBean();
        if(StringUtils.isBlank(classifyId)){
            ResultUtil.setReturnCode(processBean, FAILED,"流程分类不能为空");
            return processBean;
        }
        if(StringUtils.isBlank(name) || StringUtils.isBlank(newKey)){
            ResultUtil.setReturnCode(processBean, FAILED, "键或名称不能为空");
            return processBean;
        }
        // 查询是否已经存在同key的流程
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().tenantIdIn(tenantId).processDefinitionKey(newKey).list();
        if (list.size() != 0) {
            ResultUtil.setReturnCode(processBean, FAILED, "流程键值已存在");
            return processBean;
        }
        // 流程定义id可以根据:进行拆分，例如：miaoshu:2:d991138a-79ce-11ec-a706-d81265c67a34
        String[] split = modelId.split(":");
        String sourceKey = split[0];
        // 查询源流程定义
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey(sourceKey).latestVersion().singleResult();
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(modelId);
        if (bpmnModelInstance==null) {
            ResultUtil.setReturnCode(processBean, FAILED, "模型不存在");
            return processBean;
        }
        // 替换分类
        bpmnModelInstance.getDefinitions().setTargetNamespace(classifyId);
        // 替换流程key和name
        Process process = bpmnModelInstance.getModelElementsByType(Process.class).iterator().next();
        process.setId(newKey);
        process.setName(name);

        // 替换描述
        Collection<Documentation> documentations = process.getChildElementsByType(Documentation.class);
        Documentation documentation = null;
        if(CollectionUtils.isNotEmpty(documentations)){
            documentation = documentations.iterator().next();
        }else{
            documentation = bpmnModelInstance.newInstance(Documentation.class);
            process.addChildElement(documentation);
        }

        if(StringUtils.isNotBlank(description)){
            // 描述不为空
            documentation.setTextContent(description);
        }else{
            // 描述设置为空，原来有值的，复制的时候去掉了，需要重新设置为空
            documentation.setTextContent("");
        }

        // 复制流程，创建newKey的流程
        repositoryService.createDeployment().tenantId(tenantId).name(name).source("copyModel")
                .addModelInstance(newKey + ".bpmn", bpmnModelInstance).deploy();
        ResultUtil.setReturnCode(processBean, SUCCEED, "复制成功");

        // 源source，复制json格式的数据
        List<Resource> source = repositoryService.getDeploymentResources(pd.getId());
        // 查询新的流程定义id
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(newKey).latestVersion().singleResult();
        String definitionId = processDefinition.getId();
        if(CollectionUtils.isEmpty(source)){
            return processBean;
        }
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity("source",source.get(0).getBytes());
        byteArrayEntity.setDeploymentId(definitionId);
        List<Resource> list1 = repositoryService.getDeploymentResources(definitionId);
        // 存json格式的流程图
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
        CommandExecutor commandExecutor = processEngineConfiguration.getCommandExecutorTxRequired();
        commandExecutor.execute(commandContext -> {
            commandContext.getByteArrayManager().insertByteArray(byteArrayEntity);
            return null;
        });
        return processBean;
    }
    @Transactional
    public ProcessBean deployeBymodel(String processDefinitionId) throws Exception {
        ProcessBean processBean = new ProcessBean();
        if(StringUtils.isBlank(processDefinitionId)){
            ResultUtil.setReturnCode(processBean, FAILED, "模型id为空");
            return processBean;
        }
        return workflowModelService.deployByDefinitionId(processDefinitionId);
    }

    public ProcessBean deployeBymodel(String modelId,String  deployId) throws Exception {
        ProcessBean processBean = new ProcessBean();
        if(StringUtils.isBlank(modelId)){
            ResultUtil.setReturnCode(processBean, FAILED, "模型id为空");
            return processBean;
        }
        return workflowModelService.deployeBymodel(modelId,deployId);
    }


    /**
     * 版本回退
     * @return
     */
    @Transactional
    public ProcessBean restoreVersion(String modelId, String processDefineVersion) throws Exception {
        // camunda-TODO
//        Model model = repositoryService.createModelQuery().modelId(modelId).singleResult();
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(model.getDeploymentId())
//                .singleResult();
//        int processVersion = Integer.parseInt(processDefineVersion);
//        processDefinition = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey(processDefinition.getKey()).processDefinitionVersion(processVersion).singleResult();
//
//        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition
//                .getDeploymentId(),processDefinition.getResourceName());
//        InputStreamReader in;
//
//        in = new InputStreamReader(bpmnStream, "UTF-8");
//        XMLStreamReader xtr = xmlInputFactory.createXMLStreamReader(in);
//        BpmnModel bpmnModel =  bpmnXMLConverter.convertToBpmnModel(xtr);
//        ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);
//        byte[] bpmnBytes = null;
//        bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel,"utf-8");
//        String processName = model.getName();
//        if(!model.getName().endsWith(".bpmn20.xml") && !model.getName().endsWith(".bpmn")){
//            processName = model.getName() + ".bpmn";
//        }
//        DeploymentBuilder builder = repositoryService.createDeployment().name(processName).addString(processName, new String(bpmnBytes,"utf-8"));
//        if(StringUtils.isNotBlank(model.getTenantId())){
//            builder.tenantId(model.getTenantId());
//        }
//        Deployment deployment = builder.deploy();
//        model.setDeploymentId(deployment.getId());
//
//        repositoryService.saveModel(model);
//        Model md = repositoryService.createModelQuery().deploymentId(deployment.getId()).singleResult();
//        ProcessDefinition newProcessDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
//        String processDefinitionId = newProcessDefinition.getId();
//        repositoryService.setProcessDefinitionCategory(processDefinitionId, md.getCategory());
//        repositoryService.addModelEditorSource(model.getId(), modelNode.toString().getBytes("utf-8"));
//
        return new ProcessBean(SUCCEED,"回退成功!");
    }

    /**
     * 批量上传模型
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessBean batchUpLoadModel(InputStream inputStream) {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ProcessBean processBean = new ProcessBean();
        // 适配旧需求，解压后一个一个部署
//        repositoryService.createDeployment().source("批量文件导入").tenantId(tenantId)
//                .addZipInputStream(zipInputStream).deploy();
        ZipEntry zipEntry;
        try {
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                // 不是文件夹
                if (zipEntry.isDirectory()) {
                    continue;
                }
                String fileName = zipEntry.getName();
                String[] split = fileName.split("~");
                String tenantId = split[0];
                String classify = split[1];
                String key = split[2];
                String name = split[3].substring(0,split[3].lastIndexOf('.'));
                if(StringUtils.isBlank(classify)){
                    ResultUtil.setReturnCode(processBean, FAILED, "流程分类不能为空");
                    return processBean;
                }
                if(StringUtils.isBlank(name) || StringUtils.isBlank(key)){
                    ResultUtil.setReturnCode(processBean, FAILED, "键或名称不能为空");
                    return processBean;
                }
                String extension = FilenameUtils.getExtension(fileName);
                if (!"zip".equals(extension)) {
                    ResultUtil.setReturnCode(processBean, FAILED, "无法识别的文件类型");
                    return processBean;
                }

                this.upLoadModel(classify,key,name,zipInputStream,tenantId,fileName);
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                int len;
//                byte[] buffer = new byte[1024];
//                while ((len = zipInputStream.read(buffer)) > 0){
//                    outputStream.write(buffer,0,len);
//                }
//                zipInputStream.closeEntry();
//                InputStream in = new ByteArrayInputStream(outputStream.toByteArray());
//                repositoryService.createDeployment().source("批量文件导入")
//                        .tenantId(tenantId)
//                        .addInputStream(name+".bpmn", in).deploy();
            }
        }catch (IOException e){
            logger.error(e.getMessage(),e);
            ResultUtil.setReturnCode(processBean, FAILED, "批量导入发布失败");
        }finally {
            try {
                zipInputStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }

        ResultUtil.setReturnCode(processBean, SUCCEED, "上传模型并发布成功");
        return processBean;
    }

    /**
     * 上传单个模型
     */
    @Transactional
    public ProcessBean upLoadModel(String classifyId, String key, String name, InputStream inputStream,
                                   String tenantId, String fileName) {
        ProcessBean processBean = new ProcessBean();
        if(StringUtils.isBlank(classifyId)){
            ResultUtil.setReturnCode(processBean, FAILED, "流程分类不能为空");
            return processBean;
        }
        if(StringUtils.isBlank(name) || StringUtils.isBlank(key)){
            ResultUtil.setReturnCode(processBean, FAILED, "键或名称不能为空");
            return processBean;
        }
        String extension = FilenameUtils.getExtension(fileName);
        if (!"zip".equals(extension)) {
            ResultUtil.setReturnCode(processBean, FAILED, "无法识别的文件类型");
            return processBean;
        }
        byte[] bpmnByte = new byte[0];
        byte[] jsonByte = new byte[0];

        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry zipEntry;
        try {
            while (true) {
                if (!((zipEntry = zipInputStream.getNextEntry()) != null)) break;

                // 不是文件夹
                if (zipEntry.isDirectory()) {
                    continue;
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int len;
                byte[] buffer = new byte[1024];
                while ((len = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                zipInputStream.closeEntry();
                if(zipEntry.getName().contains(".bpmn")){
                    bpmnByte = outputStream.toByteArray();
                }else if(zipEntry.getName().contains(".json")){
                    jsonByte = outputStream.toByteArray();
                }
            }
            // bpmn格式导入
            BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(new ByteArrayInputStream(bpmnByte));
            bpmnModelInstance.getDefinitions().setTargetNamespace(classifyId);
            Process process = bpmnModelInstance.getDefinitions().getChildElementsByType(Process.class).iterator().next();
            process.setId(key);
            process.setName(name);
            bpmnModelInstance.getDefinitions().getChildElementsByType(Process.class).iterator().next().setId(key);
            Deployment deployment = repositoryService.createDeployment().source("单文件导入")
                    .tenantId(tenantId)
                    .addModelInstance(name+".bpmn",bpmnModelInstance)
                    .deploy();
            List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionName(name).tenantIdIn(tenantId).processDefinitionKey(key).deploymentId(deployment.getId()).listPage(0, 1);

            // json格式导入
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity("source",jsonByte);
            byteArrayEntity.setDeploymentId(processDefinitions.get(0).getId());
            // 存json格式的流程图
            ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl)processEngine.getProcessEngineConfiguration();
            CommandExecutor commandExecutor = processEngineConfiguration.getCommandExecutorTxRequired();
            commandExecutor.execute(commandContext -> {
                commandContext.getByteArrayManager().insertByteArray(byteArrayEntity);
                return null;
            });
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new RuntimeException("导入流程图失败");
        }

        ResultUtil.setReturnCode(processBean, SUCCEED, "上传模型并发布成功");
        return processBean;

    }

    public ProcessBean dropProcess(Map<String, Object> reqMap) {
        String key = (String) reqMap.get("key");
        String name = (String) reqMap.get("name");
        String tenantId = (String) reqMap.get("tenantId");
        String classifyId = (String) reqMap.get("classifyId");
        ProcessBean processBean=new ProcessBean();
        try {
            List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionName(name).tenantIdIn(tenantId).processDefinitionKey(key).list();
            for (ProcessDefinition processDefinition:processDefinitions){
                //更新Byte表的数据
                actProcessMapper.updateProcessClasstify(classifyId,processDefinition.getId());
                String content = org.camunda.bpm.model.xml.impl.util.IoUtil.convertXmlDocumentToString(repositoryService.getBpmnModelInstance(processDefinition.getId()).getDocument());
                Pattern pattern = Pattern.compile("targetNamespace=\"([^\"]*)\"");
                Matcher matcher = pattern.matcher(content);
                if(!matcher.find()){
                    ResultUtil.setReturnCode(processBean, FAILED, "原流程定义格式错误,<definitions>必须有targetNamespace属性");
                    return processBean;
                }
                String temp = matcher.group(1);
                content = content.replaceAll(temp, classifyId);
                byte[] bytes = content.getBytes();
                //更新Byte表的数据
                actProcessMapper.updateProcessClasstifyXml(bytes,processDefinition.getDeploymentId());
            }
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionName(name).tenantIdIn(tenantId).processDefinitionKey(key).latestVersion().singleResult();
            String content = org.camunda.bpm.model.xml.impl.util.IoUtil.convertXmlDocumentToString(repositoryService.getBpmnModelInstance(processDefinition.getId()).getDocument());
            Pattern pattern = Pattern.compile("targetNamespace=\"([^\"]*)\"");
            Matcher matcher = pattern.matcher(content);
            if(!matcher.find()){
                ResultUtil.setReturnCode(processBean, FAILED, "原流程定义格式错误,<definitions>必须有targetNamespace属性");
                return processBean;
            }
            String temp = matcher.group(1);
            content = content.replaceAll(temp, classifyId);
            repositoryService.createDeployment().tenantId(tenantId).name(name)
                    .addInputStream(key + ".bpmn", org.camunda.commons.utils.IoUtil.stringAsInputStream(content)).deploy();
            processBean.setRetCode("0");
            processBean.setRetMsg("拖拽成功");
            return processBean;
        }catch (Exception e){
            processBean.setRetCode("1");
            processBean.setRetMsg(e.getMessage());
            return processBean;
        }

    }
}
