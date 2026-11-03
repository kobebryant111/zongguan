package com.tansun.wf.platform.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.service.WorkflowTaskExpandPlanService;
import com.tansun.wf.activiti.util.PageUtils;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.mapper.ActProcessMapper;
import com.tansun.wf.platform.model.WfClassifyEntity;
import com.tansun.wf.platform.model.WfTaskExpandPlanEntity;
import com.tansun.wf.platform.service.WfClassifyService;
import com.tansun.wf.platform.service.WfProcessCreateService;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.Resource;
import org.camunda.bpm.model.xml.ModelInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


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
@RestController
@RequestMapping(value = "/wf/wfProcessCreate")
public class WfProcessCreateController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WfProcessCreateService wfProcessCreateService;
    @Autowired
    private WorkFlowClientService workFlowClientService;
    @Autowired
    WfClassifyService wfClassifyService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    private ActProcessMapper actProcessMapper;
    @Autowired
    private WorkflowTaskExpandPlanService workflowTaskExpandPlanService;


    @ResponseBody
    @RequestMapping(value = "/classifyTree")
    public WfResponseVO classifyTree(@RequestParam Map<String,Object> reqMap){
        List<Map> classifyList = wfProcessCreateService.findClassifyList(reqMap);
        return WfResponseVO.successResponse(classifyList);
    }

    /**
     * 流程分类的拖拽
     */
    @RequestMapping("dropProcess")
    @ResponseBody
    public WfResponseVO dropProcess(@RequestParam Map<String,Object> reqMap){
        ProcessBean processBean= wfProcessCreateService.dropProcess(reqMap);
        if(processBean.getRetCode() == WorkflowConstants.SUCCEED){
            return WfResponseVO.successResponse(processBean.getRetMsg());
        }else{
            return WfResponseVO.errorResponse(processBean.getRetMsg());
        }
    }

    /**
     * 流程列表
     * @return
     */
    @RequestMapping("/list")
    public WfResponseVO query(@RequestBody ProcessInfo info,  PageInfo pageInfo) throws Exception {
        info.setCurrentPage(String.valueOf(pageInfo.getPageNum()));
        info.setPageSize(String.valueOf(pageInfo.getPageSize()));
        ProcessBean queryResult = workFlowClientService.modelList(info);
        Map result = PageUtils.convertPageResult(queryResult,pageInfo);
        return WfResponseVO.successResponse(result);
    }

    /**
     * 保存模型
     */
    @RequestMapping("/saveModel")
    public WfResponseVO saveModel(@RequestParam Map<String,Object> reqMap)  {
        String classifyId = MapUtils.getString(reqMap, "classifyId");
        String key = MapUtils.getString(reqMap, "key");
        String name = MapUtils.getString(reqMap, "name");
        String description = MapUtils.getString(reqMap, "description");
        String tenantId = MapUtils.getString(reqMap, "tenantId");
        String templateModelId = MapUtils.getString(reqMap, "templateModelId");
        try{
            ProcessBean result = null;
            if(StringUtils.isNotBlank(templateModelId)){
                result = wfProcessCreateService.copyModel(classifyId,templateModelId,key,name,description,tenantId);
            }else{
                result = wfProcessCreateService.createModel(classifyId,key,name,description,tenantId);
            }
            if(result.getRetCode() == WorkflowConstants.SUCCEED){
                return WfResponseVO.successResponse(result.getRetMsg());
            }else{
                return WfResponseVO.errorResponse(result.getRetMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }

    }

    @RequestMapping("/copyModel")
    public WfResponseVO copyModel(@RequestParam Map<String,Object> reqMap) throws Exception {
        String classifyId = MapUtils.getString(reqMap, "classifyId");
        String tenantId = MapUtils.getString(reqMap, "tenantId");
        String modelId = MapUtils.getString(reqMap, "id");
        String key = MapUtils.getString(reqMap, "key");
        String name = MapUtils.getString(reqMap, "name");
        String description = MapUtils.getString(reqMap, "description");

        try{
            ProcessBean result = wfProcessCreateService.copyModel(classifyId,modelId,key,name,description,tenantId);
            if(result.getRetCode() == WorkflowConstants.SUCCEED){
                return WfResponseVO.successResponse(result.getRetMsg());
            }else{
                return WfResponseVO.errorResponse(result.getRetMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }

    }

    /**
     * 发布流程
     */
    @RequestMapping("/publish")
    public WfResponseVO publish(@RequestParam Map<String,Object> reqMap)  {
        String modelId = MapUtils.getString(reqMap, "id");
        try{
            ProcessBean result = wfProcessCreateService.deployeBymodel(modelId);
            if(result.getRetCode() == WorkflowConstants.SUCCEED){
                return WfResponseVO.successResponse(result.getRetMsg());
            }else{
                return WfResponseVO.errorResponse(result.getRetMsg());
            }
        } catch (Exception e) {
            if(StringUtils.contains(e.getMessage(),"without condition")){
                return WfResponseVO.errorResponse("网关条件配置有误");
            }
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    @RequestMapping("/updateDeploy")
    public WfResponseVO updateDeploy(@RequestParam Map<String,Object> reqMap)  {
        String modelId = MapUtils.getString(reqMap, "id");
        String deployId = MapUtils.getString(reqMap, "deploymentId");
        try{
            ProcessBean result = wfProcessCreateService.deployeBymodel(modelId,deployId);
            if(result.getRetCode() == WorkflowConstants.SUCCEED){
                return WfResponseVO.successResponse(result.getRetMsg());
            }else{
                return WfResponseVO.errorResponse(result.getRetMsg());
            }
        } catch (Exception e) {
            if(StringUtils.contains(e.getMessage(),"without condition")){
                return WfResponseVO.errorResponse("网关条件配置有误");
            }
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }



    /**
     * 删除
     */
    @RequestMapping("/delete")
    public WfResponseVO delete(@RequestParam Map<String,Object> reqMap) {
        try{
            String key = MapUtils.getString(reqMap, "key");
            String tenantId = MapUtils.getString(reqMap, "tenantId");
            ProcessBean result = workFlowClientService.deleteModel(key,tenantId);
            if(result.getRetCode() == WorkflowConstants.SUCCEED){
                return WfResponseVO.successResponse(result.getRetMsg());
            }else{
                return WfResponseVO.errorResponse(result.getRetMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 导入
     */
    @RequestMapping("/upload")
    @CrossOrigin
    public WfResponseVO deploy(@RequestParam(value = "file", required = true)
                                       MultipartFile file, String name,
                               HttpServletRequest request) {
        try{
            String batch = getParameter("batch",request);
            ProcessBean result = null;
            String classify = getParameter("classify",request);
            String key = getParameter("key",request);
            String tenantId = getParameter("tenantId",request);
            InputStream fileInputStream = file.getInputStream();
            if("0".equals(batch)){
                // 单个文件导入
                String fileName = file.getOriginalFilename();
                result = wfProcessCreateService.upLoadModel(classify, key, name, fileInputStream, tenantId,fileName);
            }else if("1".equals(batch)){
                // 批量导入
                result = wfProcessCreateService.batchUpLoadModel(fileInputStream);
            }
            if(result.getRetCode() == WorkflowConstants.SUCCEED){
                return WfResponseVO.successResponse(result.getRetMsg());
            }else{
                return WfResponseVO.errorResponse(result.getRetMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 回退
     */
    @RequestMapping("/restoreVersion")
    public WfResponseVO restoreVersion(@RequestParam Map<String,Object> reqMap) {
        try{
            String modelId = MapUtils.getString(reqMap, "modelId");
            String version = MapUtils.getString(reqMap, "version");
            ProcessBean result = wfProcessCreateService.restoreVersion(modelId, version);
            if(result.getRetCode() == WorkflowConstants.SUCCEED){
                return WfResponseVO.successResponse(result.getRetMsg());
            }else{
                return WfResponseVO.errorResponse(result.getRetMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 挂起或激活流程
     */
    @RequestMapping("/suspendOrActivate")
    public WfResponseVO suspendOrActivate(@RequestParam Map<String,Object> reqMap) throws Exception {
        String procDefId = MapUtils.getString(reqMap, "procDefId");
        String operate = MapUtils.getString(reqMap, "operate");
        if(StringUtils.isBlank(procDefId)){
            return WfResponseVO.errorResponse("流程定义id为空");
        }
        if(StringUtils.isBlank(operate)){
            return WfResponseVO.errorResponse("操作类型为空");
        }
        try{
            String newProcDefId = repositoryService.createProcessDefinitionQuery().processDefinitionKey(procDefId).latestVersion().singleResult().getId();
            ProcessBean result = wfProcessCreateService.suspendOrActivate(newProcDefId,operate);
            if(result.getRetCode() == WorkflowConstants.SUCCEED){
                return WfResponseVO.successResponse(result.getRetMsg());
            }else{
                return WfResponseVO.errorResponse(result.getRetMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 流程列表
     * @return
     */
    @RequestMapping("/versionList")
    @ResponseBody
    public WfResponseVO versionList(@RequestParam Map<String,Object> reqMap, PageInfo page) throws Exception {
        try{
            ProcessInfo info = PageUtils.newProcessInfo(page);
            String key = MapUtils.getString(reqMap, "key");
            info.setProcessDefineKey(key);
            info.setTenantId(MapUtils.getString(reqMap, "tenantId"));
            ProcessBean queryResult = workFlowClientService.processDefinitionList(info);
            Map result = PageUtils.convertPageResult(queryResult, page);
            return WfResponseVO.successResponse(result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    @RequestMapping("/deleteVersion")
    @ResponseBody
    public  WfResponseVO  deleteVersion(@RequestBody List<String>  deploys) {
        this.workFlowClientService.deleteModel(deploys);
        return WfResponseVO.successResponse("删除成功!");
    }

    /**
     * 流程列表
     * @return
     */
    @RequestMapping("/versionComparison")
    @ResponseBody
    public WfResponseVO versionComparison(String processDefineId1,String processDefineId2) throws Exception {
        if(StringUtils.isBlank(processDefineId1) || StringUtils.isBlank(processDefineId2)){
            return WfResponseVO.errorResponse("比对的流程定义id为空");
        } else {
            InputStream resourceAsStream1 = workFlowClientService.getProcessXml(processDefineId1);
            InputStream resourceAsStream2 = workFlowClientService.getProcessXml(processDefineId2);
            String Bpmn1 = IOUtils.toString(resourceAsStream1, StandardCharsets.UTF_8);
            String Bpmn2 = IOUtils.toString(resourceAsStream2, StandardCharsets.UTF_8);
            Map<String,String> result = new HashMap<>(2);
            result.put("processDefineId1Bpmn",Bpmn1);
            result.put("processDefineId2Bpmn",Bpmn2);
            return WfResponseVO.successResponse(result);
        }
    }

    /**
     * 启动或者
     */
    @RequestMapping(value = "/startProcess")
    public WfResponseVO  startProcess(@RequestBody ProcessInfo info, boolean andSubmit){
        ProcessBean processBean = null;
        try {
            if(andSubmit){
                processBean = workFlowClientService.startAndSubmitProcess(info);
            }else{
                processBean = workFlowClientService.startProcess(info);
            }
            if("0".equals(processBean.getRetCode())){
                return WfResponseVO.successResponse(processBean.getRetMsg()+"流程实例id:" + processBean.getProcessInstanceId());
            }else{
                return WfResponseVO.errorResponse(processBean.getRetMsg());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse("流程启动失败:" + e.getMessage());

        }
    }

    @RequestMapping("/modelBpmn")
    public WfResponseVO modelBpmn(@RequestParam String modelId,@RequestParam(required = false) boolean isBeforeDownload,HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            if(isBeforeDownload){
                ProcessBean processBean = workFlowClientService.exportModel(modelId, "bpmn");
                if(processBean.getRetCode().equals("1")){
                    if ("模型不存在".equals(processBean.getRetMsg())) {
                        return WfResponseVO.errorResponse("该流程未编辑");
                    }
                }
            }else{
                String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                String fileName = URLEncoder.encode(modelId,"UTF-8");
                ServletOutputStream out = response.getOutputStream();
                // zip输出流
                ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));
                response.setCharacterEncoding("UTF-8");
                // zip文件名
                response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(),"iso-8859-1") + ".zip");
                // 导出bpmn格式的数据
                InputStream resourceAsStream = workFlowClientService.getProcessXml(modelId);
                // 拼接文件名

                zos.putNextEntry(new ZipEntry(modelId+".bpmn"));
                int len;
                // 读入需要下载的文件的内容，打包到zip文件里
                byte[] buffer = new byte[1024];
                while ((len = resourceAsStream.read(buffer)) > 0){
                    zos.write(buffer,0,len);
                }
                zos.closeEntry();

                ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(modelId).singleResult();
                List<Resource> source = repositoryService.getDeploymentResources(pd.getId());
                zos.putNextEntry(new ZipEntry(modelId+".json"));
                byte[] bytes = source.get(0).getBytes();
                zos.write(bytes);
                zos.closeEntry();

                // 关闭zip流
                zos.close();
                // 关闭输出流
                out.close();
            }
            return WfResponseVO.successResponse("导出成功");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "batchModelBpmn")
    public WfResponseVO batchModelBpmn(@RequestParam(value = "keys",required = false) String[] keys,
                                       @RequestParam(value = "classifyIds",required = false) String[] classifyIds,
                                       @RequestParam(required = false) boolean isBeforeDownload, HttpServletResponse response) throws IOException {
        // 根据key导出
        List<ProcessDefinition> list = new ArrayList<>();
        if(keys !=null && keys.length != 0){
            String[] keyArrays = keys;
            for (Object keyArray : keyArrays) {
                ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(keyArray.toString()).latestVersion().singleResult();
                if(definition!=null){
                    list.add(definition);
                }
            }
        }
        // 根据分类id导出
        if(classifyIds !=null && classifyIds.length != 0){
            for (String classifyId : classifyIds) {
                WfClassifyEntity classify = wfClassifyService.findClassifyById(classifyId);
                if(classify != null){
                    List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().processDefinitionCategory(classifyId).latestVersion().list();
                    if(CollectionUtils.isNotEmpty(definitions)){
                        list.addAll(definitions);
                    }
                }
            }
        }
        if(keys == null && classifyIds == null){
            list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        }
        if(CollectionUtils.isEmpty(list)){
            return WfResponseVO.errorResponse("没有可导出的文件");
        }
        if(isBeforeDownload){
            // 去重
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (i != j && StringUtils.equals(list.get(i).getKey(), list.get(j).getKey())) {
                        list.remove(list.get(j));
                    }
                }
            }
            String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String fineName = URLEncoder.encode("批量流程导出文件（" + date + "）","UTF-8");
            ServletOutputStream out = response.getOutputStream();
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fineName.getBytes(),"iso-8859-1") + ".zip");

            for (ProcessDefinition processDefinition : list) {
                // 先把zip放到项目的随机文件夹下，UUID
                FileOutputStream fileOutputStream = new FileOutputStream("D:\\a.zip");
                ZipOutputStream zos1 = new ZipOutputStream(fileOutputStream);
                String tenantId = processDefinition.getTenantId();
                String category = processDefinition.getCategory();
                String processDefineId = processDefinition.getId();
                String key = processDefinition.getKey();
                String name = processDefinition.getName();
                InputStream resourceAsStream = workFlowClientService.getProcessXml(processDefineId);
                // 拼接文件名
                String fileName = StringUtils.join(new String[]{tenantId,category,key,name},"~");
                zos1.putNextEntry(new ZipEntry(processDefineId+".bpmn"));
                int len;
                // 读入需要下载的文件的内容，打包到zip文件里
                byte[] buffer = new byte[1024];
                while ((len = resourceAsStream.read(buffer)) > 0){
                    zos1.write(buffer,0,len);
                }
                zos1.closeEntry();
                //导出json
                ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefineId).singleResult();
                List<Resource> source = repositoryService.getDeploymentResources(pd.getId());
                zos1.putNextEntry(new ZipEntry(processDefineId+".json"));
                logger.info("当前问题:[{}]",processDefineId);
                byte[] bytes = source.get(0).getBytes();
                zos1.write(bytes);
                zos1.closeEntry();
                zos1.finish();
                fileOutputStream.close();
                File zipFile = new File("D:\\a.zip");
                FileInputStream fileInputStream = new FileInputStream(zipFile);
                zos.putNextEntry(new ZipEntry(fileName+".zip"));
                byte[] in = new byte[1024];
                while((len = fileInputStream.read(in)) > 0){
                    zos.write(in,0,len);
                }
                zipFile.delete();

            }
            zos.closeEntry();
            zos.close();
            out.close();
        }
        return WfResponseVO.successResponse("导出成功");
    }

    /**
     * 流程部署图下载
     */
    @RequestMapping(value = "processBpmn")
    public String processBpmn(@RequestParam String processDefineId, HttpServletRequest request, HttpServletResponse response) throws IOException {

//        InputStream resourceAsStream = workFlowClientService.getProcessXml(processDefineId);
//        ServletOutputStream outputStream = response.getOutputStream();
//        response.setHeader("Content-Disposition", "attachment; filename=" + processDefineId+".bpmn");
//        byte[] b = new byte[2048];
//        int length;
//        while ((length = resourceAsStream.read(b)) > 0) {
//            outputStream.write(b, 0, length);
//        }
//        outputStream.flush();
//        return "";


        String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = URLEncoder.encode(processDefineId,"UTF-8");
        ServletOutputStream out = response.getOutputStream();
        // zip输出流
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));
        response.setCharacterEncoding("UTF-8");
        // zip文件名
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(),"iso-8859-1") + ".zip");
        // 导出bpmn格式的数据
        InputStream resourceAsStream = workFlowClientService.getProcessXml(processDefineId);
        // 拼接文件名

        zos.putNextEntry(new ZipEntry(processDefineId+".bpmn"));
        int len;
        // 读入需要下载的文件的内容，打包到zip文件里
        byte[] buffer = new byte[1024];
        while ((len = resourceAsStream.read(buffer)) > 0){
            zos.write(buffer,0,len);
        }
        zos.closeEntry();

        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefineId).singleResult();
        List<Resource> source = repositoryService.getDeploymentResources(pd.getId());
        zos.putNextEntry(new ZipEntry(processDefineId+".json"));
        byte[] bytes = source.get(0).getBytes();
        zos.write(bytes);
        zos.closeEntry();

        // 关闭zip流
        zos.close();
        // 关闭输出流
        out.close();
        return "";
    }

    /**
     * 获取流程运行的实时图片
     */
    @RequestMapping(value = "processImage")
    public void processImage(@RequestParam String processDefineId,HttpServletResponse response) throws IOException{
        InputStream resourceAsStream = workFlowClientService.getProcessImage(processDefineId);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    @RequestMapping(value = "taskExpandPlan")
    public WfResponseVO  taskExpandPlan(String classifyCode,String classifyId){
        WfClassifyEntity classifyEntity=new WfClassifyEntity();
        if (classifyCode!=null&&!"".equals(classifyCode)) {
            classifyEntity = wfClassifyService.findClassifyByCode(classifyCode);
        }
        if (classifyId!=null&&!"".equals(classifyId)) {
            classifyEntity = wfClassifyService.findClassifyById(classifyId);
        }
        List<WfTaskExpandPlanEntity> plans = new ArrayList<>();
        if (classifyEntity == null) {
            return WfResponseVO.errorResponse(plans,"查询失败!");
        }
        plans = workflowTaskExpandPlanService.getExpandPlanByClassifyCode(classifyEntity.getCode(),"");
        return WfResponseVO.successResponse(plans,"查询成功!");
    }

    private String getParameter(String key,HttpServletRequest request) throws UnsupportedEncodingException {
        String parameter = request.getParameter(key);
        if (parameter == null) {
            return null;
        }
        return new String(parameter.getBytes("iso-8859-1"), "utf-8");
    }

    @RequestMapping(value = "updateIsTemplate")
    public WfResponseVO  updateIsTemplate(@RequestParam(value = "modelId",required = true) String modelId,@RequestParam(value = "isTemplate",required = true) Integer isTemplate){
        // camunda-TODO
//        ModelInstance model = repositoryService.getBpmnModelInstance(modelId);
        actProcessMapper.updateTemplateStatus(modelId,isTemplate);
//        String metaInfo = model.getMetaInfo();
//        JSONObject modelObjectNode = JSONObject.parseObject(metaInfo);
//        modelObjectNode.put("isTemplate",isTemplate);
//        model.setMetaInfo(modelObjectNode.toJSONString());
//        repositoryService.saveModel(model);
        return WfResponseVO.successResponse("更新成功!");
    }

    /**
     * 查询设为模板的流程
     * @return
     */
    @RequestMapping("/templateList")
    public WfResponseVO templateList(ProcessInfo info,  PageInfo pageInfo) throws Exception {
        info.setPageSize("0");
        info.setCurrentPage("0");
        ProcessBean queryResult = workFlowClientService.modelList(info);
        List<Map<String, Object>> newProcessList = new ArrayList<>();
        List<Map<String, Object>> resultList = queryResult.getResultList();
        if(CollectionUtils.isNotEmpty(resultList)){
            for (Map<String, Object> map : resultList) {
//                String metaInfo = MapUtils.getString(map, "meteInfo");
//                JSONObject modelObjectNode = JSONObject.parseObject(metaInfo);
//                if(modelObjectNode.getBoolean("isTemplate") != null && modelObjectNode.getBoolean("isTemplate")){
                String isTemplate = String.valueOf(map.get("isTemplate"));
                if("1".equals(isTemplate)){
                    newProcessList.add(map);
                }
            }
        }
        ProcessBean processBean = new ProcessBean();
        processBean.setResultList(newProcessList);
        processBean.setTotal(String.valueOf(newProcessList.size()));
        Map result = PageUtils.convertPageResult(processBean,pageInfo);
        return WfResponseVO.successResponse(result);
    }

}
