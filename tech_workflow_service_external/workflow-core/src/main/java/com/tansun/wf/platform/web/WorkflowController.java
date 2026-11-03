package com.tansun.wf.platform.web;

import com.alibaba.fastjson.JSON;
import com.tansun.workflow.model.*;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Controller
public class WorkflowController {
    @Autowired
    private WorkFlowClientService workFlowClientService;
    /********************************Model************************************/
    /**
     * 模型列表
     */
    @ResponseBody
    @RequestMapping(value = "/model/modelList")
    public PageConverter modelList(String page, String limit) {
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setCurrentPage(page);
        processInfo.setPageSize(limit);
        ProcessBean bean = workFlowClientService.modelList(processInfo);
        return new PageConverter(bean);
    }

    /**
     * 创建模型
     *
     * @param tenantId
     */
    @ResponseBody
    @RequestMapping(value = "/model/createModel")
    public ProcessBean createModel(String key, String name, String description, String tenantId) {
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setKey(key);
        modelInfo.setName(name);
        modelInfo.setTenantId(tenantId);
        ProcessBean processBean = workFlowClientService.createModel(modelInfo);
        return processBean;
    }

    /**
     * 删除模型
     */
    @ResponseBody
    @RequestMapping(value = "/model/deleteModel")
    public ProcessBean deleteModel(String modelId, String key) {
        ProcessBean processBean = workFlowClientService.deleteModel(modelId, key);
        return processBean;
    }

    /**
     * 根据模型部署流程
     */
    @ResponseBody
    @RequestMapping(value = "/model/deployByDefinitionId")
    public ProcessBean deployByDefinitionId(String processDefinitionId) throws Exception {
        ProcessBean processBean = workFlowClientService.deployByDefinitionId(processDefinitionId);
        return processBean;
    }

    /**
     * 导出model对象为指定类型
     *
     * @param modelId 模型ID
     * @param type    导出文件类型(bpmn\json)
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/model/export/{modelId}/{type}")
    public void export(@PathVariable("modelId") String modelId, @PathVariable("type") String type, HttpServletResponse response) throws IOException {
        ProcessBean processBean = workFlowClientService.exportModel(modelId, type);
        if ("1".equals(processBean.getRetCode())) {
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.getOutputStream().println(processBean.getRetMsg());
        } else {
            Map<String, Object> map = (Map<String, Object>) processBean.getResultObject();
            String fileName = (String) map.get("fileName");
            byte[] bytes = (byte[]) map.get("bytes");
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            IOUtils.copy(in, response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        }
        response.flushBuffer();
        return;
    }

    /********************************ProcessDefinition************************************/

    /**
     * 流程定义列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/processDefinition/definitionList")
    public PageConverter processList(ProcessInfo processInfo) {
//	    	ProcessInfo processInfo = new ProcessInfo();
//	    	processInfo.setCurrentPage(page);
//	    	processInfo.setPageSize(limit);
        ProcessBean processBean = workFlowClientService.processDefinitionList(processInfo);
        return new PageConverter(processBean);
    }

    /**
     * 上传并且部署
     */
    @ResponseBody
    @RequestMapping(value = "/processDefinition/deploy")
    public ProcessBean deploy(@RequestParam(value = "file", required = false) MultipartFile file, DeployInfo deployInfo) {
        String fileName = file.getOriginalFilename();
        InputStream fileInputStream = null;
        try {
            fileInputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        deployInfo.setInputStream(fileInputStream);
        deployInfo.setFileName(fileName);
        return workFlowClientService.deploy(deployInfo);
    }


    /**
     * 获取流程运行的实时图片
     *
     * @param processDefineId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/processDefinition/getProcessImage")
    public void getProcessImage(@RequestParam("processDefineId") String processDefineId, HttpServletResponse response) throws IOException {
        InputStream resourceAsStream = workFlowClientService.getProcessImage(processDefineId);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 获取流程定义文件
     *
     * @param processDefineId
     * @return
     */
    @RequestMapping(value = "/processDefinition/getProcessXml")
    public void getProcessXml(@RequestParam("processDefineId") String processDefineId, HttpServletResponse response) throws IOException {
        InputStream resourceAsStream = workFlowClientService.getProcessXml(processDefineId);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }


    /**
     * 根据流程定义生成模型
     *
     * @param processDefinitionId
     * @return
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException
     */
    @ResponseBody
    @RequestMapping(value = "/processDefinition/convertToModel/{processDefinitionId}")
    public ProcessBean convertToModel(@PathVariable("processDefinitionId") String processDefinitionId) {
        return workFlowClientService.createModelByProcessDefinition(new String[]{processDefinitionId});
    }
    /********************************ProcessInstance***********************************/
    /**
     * 启动流程
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/processInstance/startProcess")
    public ProcessBean startProcess(ProcessInfo info) throws Exception {
        return workFlowClientService.startProcess(info);
    }

    /**
     * 启动流程并且提交
     *
     * @param info
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/processInstance/startAndSubmitProcess")
    public ProcessBean startAndSubmitProcess(ProcessInfo info) throws Exception {
        return workFlowClientService.startAndSubmitProcess(info);
    }

    /**
     * 根据流程实例ID终止流程
     *
     * @param info
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/processInstance/terminateProcess")
    public ProcessBean terminateProcess(ProcessInfo info) throws Exception {
        return workFlowClientService.terminateProcess(info);
    }
    /**
     * 删除整个流程实例，包括历史数据
     *
     * @param info
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/processInstance/deleteProcessInstance")
    public ProcessBean deleteProcessInstance(ProcessInfo info) throws Exception {
        return workFlowClientService.deleteProcessInstance(info);
    }


    /**
     * 挂起流程实列
     *
     * @param info
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/processInstance/suspendProcess")
    public ProcessBean suspendProcess(ProcessInfo info) throws Exception {
        return workFlowClientService.suspendProcess(info);
    }

    /**
     * 激活流程实列
     *
     * @param info
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/processInstance/activateProcess")
    public ProcessBean activateProcess(ProcessInfo info) throws Exception {
        return workFlowClientService.activateProcess(info);
    }

    /**
     * 查询业务流程轨迹
     *
     * @param info
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/processInstance/queryProcessTrack")
    public TaskBean queryProcessTrack(ProcessInfo info) throws Exception {
        return workFlowClientService.queryProcessTrack(info);
    }


    /**
     * 获取运行中的流程
     */
    @ResponseBody
    @RequestMapping(value = "/processInstance/processInstanceList")
    public ProcessBean processInstanceList(ProcessInfo processInfo) {
        return workFlowClientService.processInstanceList(processInfo);
    }


    /**
     * 获取已经结束的流程列表
     *
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/processInstance/getCompletedProcess")
    public PageConverter getCompleteProcess(String page, String limit) {
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setCurrentPage(page);
        processInfo.setPageSize(limit);
        ProcessBean bean = workFlowClientService.getCompletedProcess(processInfo);
        return new PageConverter(bean);
    }
    /************************************task****************************/

    /**
     * 提交任务
     * 可以使用固定变量nextUser
     * 也可以添加流程变量
     */
    @ResponseBody
    @RequestMapping("/task/submitTask")
    public TaskBean submitTask(@RequestBody TaskBean taskBean)
            throws Exception {
        Map mPara = taskBean.getmPara();
        if (mPara != null) {
            for (Object key : mPara.keySet()) {
                try {
                    //修复数组变成字符串问题
                    List<String> list = JSON.parseArray((String) mPara.get(key), String.class);
                    mPara.put(key, list);
                } catch (Exception e) {

                }
            }
        }
        return workFlowClientService.submitTask(taskBean);
    }


    /**
     * 退回到任务前一节点／收回
     *
     * @param taskBean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/task/backPreTask")
    public TaskBean backPreTask(TaskBean taskBean) throws Exception {
        return workFlowClientService.backPreTask(taskBean);
    }

    /**
     * 退回到指定节点
     *
     * @param taskBean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/task/backTask")
    public TaskBean backTask(TaskBean taskBean) throws Exception {
        return workFlowClientService.backTask(taskBean);
    }

    /**
     * 退回到第一个用户审批节点
     *
     * @param taskBean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/task/backToFirstUserTask")
    public TaskBean backToFirstUserTask(TaskBean taskBean) throws Exception {
        return workFlowClientService.backToFirstUserTask(taskBean);
    }

    /**
     * 释放任务
     *
     * @param taskInfo
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/task/releaseTask")
    public TaskBean releaseTask(TaskInfo taskInfo) throws Exception {
        return workFlowClientService.releaseTask(taskInfo);
    }

    /**
     * 转办任务
     *
     * @param taskInfo
     * @return
     * @throws Exception TODO 修改此api
     */
    @ResponseBody
    @RequestMapping("/task/transferTask")
    public TaskBean transferTask(TaskInfo taskInfo) throws Exception {
        return workFlowClientService.transferTask(taskInfo);
    }

    /**
     * 根据当前任务ID查找下一人工任务
     *
     * @param taskInfo
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/task/queryNextTaskList")
    public TaskBean queryNextTaskList(TaskInfo taskInfo) throws Exception {
        return workFlowClientService.queryNextTaskList(taskInfo);
    }



    /**
     * 查询任务列表
     * 3 = 已办
     * 非3 = 待办
     */
    @ResponseBody
    @RequestMapping("/task/taskList")
    public TaskBean tasks(TaskInfo info) throws Exception {
        return workFlowClientService.findTaskList(info);
    }

    /**
     * 根据taskId查询任务详情(任务信息按钮信息等)
     */
    @ResponseBody
    @RequestMapping("/task/findTaskInfo")
    public TaskBean findTaskInfo(TaskInfo info) throws Exception {
        return workFlowClientService.findTaskInfo(info);
    }

    @ResponseBody
    @RequestMapping("/task/getTasksByProcessInstanceId")
    public TaskBean getTasksByProcessInstanceId(String processInstanceId) throws Exception {
        return workFlowClientService.getTasksByProcessInstanceId(processInstanceId);
    }

    /**
     * 根据流程定义id获取任务节点信息
     *
     * @param processDefinitionId
     * @return
     */
    @ResponseBody
    @RequestMapping("/task/getTaskDefinitions")
    public TaskBean getTaskDefinitions(String processDefinitionId) {
        return workFlowClientService.getTaskDefinitions(processDefinitionId);
    }

    /**
     * 根据流程定义key获取任务节点信息
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/task/getTaskDefinitionsByKey")
    public TaskBean getTaskDefinitionsByKey(TaskInfo taskInfo) {
        return workFlowClientService.getTaskDefinitionsByKey(taskInfo);
    }

    //============================== service task 服务===========

    /**
     * 根据流程实例id获取所有的下一步审批任务
     */
    @ResponseBody
    @RequestMapping("/task/getAllNextTaskInfo")
    public TaskBean getAllNextTaskInfo(TaskInfo taskInfo) throws Exception {
        return workFlowClientService.getAllNextTaskInfo(taskInfo);
    }

    /**
     * 获取所有流程分类的待办数据
     */
    @ResponseBody
    @RequestMapping("/task/getAllClassifyPendTasks")
    public List<TaskBean> getAllClassifyPendTasks(TaskInfo taskInfo) {
        return workFlowClientService.getAllClassifyPendTasks(taskInfo);
    }

    /**
     * 获取所有流程分类的已办数据
     */
    @ResponseBody
    @RequestMapping("/task/getAllClassifyCompletedTasks")
    public List<TaskBean> getAllClassifyCompletedTasks(TaskInfo taskInfo) {
        return workFlowClientService.getAllClassifyCompletedTasks(taskInfo);
    }

    /**
     * 获取流程变量
     *
     * @param processInfo
     * @return b
     */
    @ResponseBody
    @RequestMapping("/task/findProcessVariable")
    public TaskBean findProcessVariable(ProcessInfo processInfo) {
        return workFlowClientService.findProcessVariable(processInfo);
    }

    /**
     * 获取按钮信息
     *
     * @param taskInfo
     * @return b
     */
    @ResponseBody
    @RequestMapping("/task/findButtonPower")
    public TaskBean findButtonPower(TaskInfo taskInfo) {
        return workFlowClientService.findButtonPower(taskInfo);
    }

    /**
     * 取回任务
     *
     * @param taskBean
     * @return b
     */
    @ResponseBody
    @RequestMapping("/task/rebackTask")
    public TaskBean rebackTask(TaskBean taskBean) throws Exception {
        return workFlowClientService.rebackTask(taskBean);
    }

    /**
     * 根据流程实例id获取该流程实例已办任务列表
     *
     * @param processInfo
     */
    @ResponseBody
    @RequestMapping("/task/findHiTaskByProcessInstanceId")
    public TaskBean findHiTaskByProcessInstanceId(ProcessInfo processInfo) {
        return workFlowClientService.findHiTaskByProcessInstanceId(processInfo);
    }

    /**
     * 根据流程定义key和第一个节点的id获取属性信息
     *
     * @param taskInfo
     * @return b
     */
    @ResponseBody
    @RequestMapping("/task/getFirstTaskDefine")
    public TaskBean getFirstTaskDefine(TaskInfo taskInfo) {
        return workFlowClientService.getFirstTaskDefine(taskInfo);
    }

    /**
     * 批量执行任务
     * @param batchExecuteParam 批量执行任务对象
     */
    @ResponseBody
    @RequestMapping("/processInstance/batchExecuteTask")
    public BatchExecuteVo batchExecuteTask(BatchExecuteParam batchExecuteParam) throws Exception {
        return workFlowClientService.batchExecuteTask(batchExecuteParam);
    }


    /**
     * 标记任务，已阅、未阅
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/task/signTaskStatus")
    public TaskBean signTaskStatus(TaskInfo taskInfo){
        return workFlowClientService.signTaskStatus(taskInfo);
    }

    /**
     * 获取任务，已阅、未阅状态
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/task/getSignTaskStatus")
    public TaskBean getSignTaskStatus(TaskInfo taskInfo){
        return workFlowClientService.getSignTaskStatus(taskInfo);
    }

    /**
     * 返回会签任务的审批状态
     * @param taskInfo
     */
    @ResponseBody
    @RequestMapping("/task/getMultiStatus")
    public TaskBean getMultiStatus(TaskInfo taskInfo)  {
        return this.workFlowClientService.getMultiStatus(taskInfo);
    }

    /**
     * 流程结束后获取流程变量信息
     * @param processInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/task/findFinishedVariable")
    public TaskBean findFinishedVariable(ProcessInfo processInfo){
        return workFlowClientService.findFinishedVariable(processInfo);
    }

    /**
     * 复制流程模型
     * @param modelInfo
     */
    @ResponseBody
    @RequestMapping("/model/copyModel")
    public ProcessBean copyModel(ModelInfo modelInfo) throws Exception {
        return workFlowClientService.copyModel(modelInfo);
    }

    /**
     * 岗位待办
     */
    @ResponseBody
    @RequestMapping("/task/findPendOrgTaskList")
    public TaskBean findPendOrgTaskList(TaskInfo taskInfo){
        return workFlowClientService.findPendOrgTaskList(taskInfo);
    }

    /**
     * 根据userId获取所有的待办任务
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/task/findTodoList")
    public TaskBean findTodoList( TaskInfo taskInfo) {
        return workFlowClientService.findTodoList(taskInfo);
    }

    @ResponseBody
    @RequestMapping("/processInstance/setProcessVariable")
    public ProcessBean setProcessVariable(ProcessInfo processInfo) {
        return workFlowClientService.setProcessVariable(processInfo);
    }

}
/**
 * 分页转换器
 *
 */
 class PageConverter {

    private Object totalCount;

    private Object items;

    public PageConverter(ProcessBean bean){
        this.items = bean.getResultList();
        this.totalCount = bean.getTotal();
    }

    public PageConverter(TaskBean bean){
        this.items = bean.getTaskList();
        this.totalCount = bean.getTotal();
    }

    public Object getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Object totalCount) {
        this.totalCount = totalCount;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }

}