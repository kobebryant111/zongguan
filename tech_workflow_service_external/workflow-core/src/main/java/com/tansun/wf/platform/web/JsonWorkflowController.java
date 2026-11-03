package com.tansun.wf.platform.web;

import com.tansun.wf.activiti.util.ResultUtil;
import com.tansun.workflow.model.BatchExecuteParam;
import com.tansun.workflow.model.BatchExecuteVo;
import com.tansun.workflow.model.ClassifyInfo;
import com.tansun.workflow.model.DeployInfo;
import com.tansun.workflow.model.ModelInfo;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.QueryBean;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import com.tansun.workflow.service.WorkFlowClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tansun.wf.activiti.constants.WorkflowConstants.FAILED;


@RestController
@RequestMapping
public class JsonWorkflowController {

    @Autowired
    private WorkFlowClientService workFlowClientService;

    /********************************ProcessInstance***********************************/

    /**
     * 启动流程
     *
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/startProcess")
    public ProcessBean startProcess(@RequestBody ProcessInfo info) {
        ProcessBean processBean = new ProcessBean();
        try {
            processBean = workFlowClientService.startProcess(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "流程启动失败：" + e.getMessage());
        }
        return processBean;
    }

    /**
     * 启动流程并且提交
     *
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/startAndSubmitProcess")
    public ProcessBean startAndSubmitProcess(@RequestBody ProcessInfo info) {
        ProcessBean processBean = new ProcessBean();
        try {
            processBean = workFlowClientService.startAndSubmitProcess(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "流程启动并提交失败：" + e.getMessage());
        }
        return processBean;
    }

    /**
     * 根据流程实例ID终止流程
     *
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/terminateProcess")
    public ProcessBean terminateProcess(@RequestBody ProcessInfo info) {
        ProcessBean processBean = new ProcessBean();
        try {
            processBean = workFlowClientService.terminateProcess(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "流程终止失败：" + e.getMessage());
        }
        return processBean;
    }


    /**
     * 挂起流程实列
     *
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/suspendProcess")
    public ProcessBean suspendProcess(@RequestBody ProcessInfo info) {
        ProcessBean processBean = new ProcessBean();
        try {
            processBean = workFlowClientService.suspendProcess(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "流程挂起失败：" + e.getMessage());
        }
        return processBean;
    }

    /**
     * 激活流程实列
     *
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/activateProcess")
    public ProcessBean activateProcess(@RequestBody ProcessInfo info) {
        ProcessBean processBean = new ProcessBean();
        try {
            processBean = workFlowClientService.activateProcess(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "流程挂起失败：" + e.getMessage());
        }
        return processBean;
    }

    /**
     * 查询业务流程轨迹
     *
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/queryProcessTrack")
    public TaskBean queryProcessTrack(@RequestBody ProcessInfo info) {
        TaskBean taskBean = new TaskBean();
        try {
            taskBean = workFlowClientService.queryProcessTrack(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(taskBean, FAILED, "流程轨迹查询失败：" + e.getMessage());
        }
        return taskBean;
    }

    /**
     * 获取运行中的流程
     */
    @ResponseBody
    @RequestMapping(value = "/json/processInstance/processInstanceList")
    public ProcessBean processInstanceList(@RequestBody ProcessInfo processInfo) {
        ProcessBean processBean = new ProcessBean();
        try {
            processBean = workFlowClientService.processInstanceList(processInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "运行流程查询失败：" + e.getMessage());
        }
        return processBean;
    }

    /**
     * 流程定义列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/json/processDefinition/definitionList")
    public ProcessBean processList(@RequestBody ProcessInfo processInfo) {
        ProcessBean processBean = new ProcessBean();
        try {
            processBean = workFlowClientService.processDefinitionList(processInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "流程定义列表查询失败：" + e.getMessage());
        }
        return processBean;
    }
    /************************************task****************************/

    /**
     * 提交任务
     */
    @ResponseBody
    @RequestMapping("/json/task/submitTask")
    public TaskBean submitTask(@RequestBody TaskBean taskBean) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.submitTask(taskBean);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "任务提交失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 退回到任务前一节点
     *
     * @param taskBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/backPreTask")
    public TaskBean backPreTask(@RequestBody TaskBean taskBean) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.backPreTask(taskBean);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "退回上一步失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 退回到指定节点
     *
     * @param taskBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/backTask")
    public TaskBean backTask(@RequestBody TaskBean taskBean) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.backTask(taskBean);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "退回失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 退回到第一个用户审批节点
     *
     * @param taskBean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/json/task/backToFirstUserTask")
    public TaskBean backToFirstUserTask(@RequestBody TaskBean taskBean) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.backToFirstUserTask(taskBean);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "退回首节点失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 释放任务
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/releaseTask")
    public TaskBean releaseTask(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.releaseTask(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "释放任务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据当前任务ID查找下一人工任务
     *
     * @param taskInfo
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/json/task/queryNextTaskList")
    public TaskBean queryNextTaskList(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.queryNextTaskList(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查找下一人工任务失败：" + e.getMessage());
        }
        return result;
    }


    /**
     * 查询任务列表
     */
    @ResponseBody
    @RequestMapping("/json/task/taskList")
    public TaskBean findTaskList(@RequestBody TaskInfo info) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.findTaskList(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询已办任务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据taskId查询任务详情(任务信息按钮信息等)
     */
    @ResponseBody
    @RequestMapping("/json/task/findTaskInfo")
    public TaskBean findTaskInfo(@RequestBody TaskInfo info) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.findTaskInfo(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询任务详情失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取流程变量
     *
     * @param processInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/findProcessVariable")
    public TaskBean findProcessVariable(@RequestBody ProcessInfo processInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.findProcessVariable(processInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询流程变量失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取按钮信息
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/findButtonPower")
    public TaskBean findButtonPower(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.findButtonPower(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询按钮信息失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 取回任务
     *
     * @param taskBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/rebackTask")
    public TaskBean rebackTask(@RequestBody TaskBean taskBean) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.rebackTask(taskBean);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "撤回任务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据流程实例id获取该流程实例已办任务列表
     *
     * @param processInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/findHiTaskByProcessInstanceId")
    public TaskBean findHiTaskByProcessInstanceId(@RequestBody ProcessInfo processInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.findHiTaskByProcessInstanceId(processInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询已办任务列表失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据实例id获取当前节点信息
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/getTasksByProcessInstanceId")
    public TaskBean getTasksByProcessInstanceId(@RequestBody String processInstanceId) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.getTasksByProcessInstanceId(processInstanceId);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "获取当前节点信息失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 返回会签任务的审批状态
     *
     * @param taskInfo
     */
    @ResponseBody
    @RequestMapping("/json/task/getMultiStatus")
    public TaskBean getMultiStatus(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.getMultiStatus(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询多工作项任务失败：" + e.getMessage());
        }
        return result;
    }


    /********************************Model************************************/

    /**
     * 获取模型列表
     */
    @ResponseBody
    @RequestMapping(value = "/json/model/modelList")
    public ProcessBean modelList(@RequestBody ProcessInfo processInfo) {
        ProcessBean processBean = new ProcessBean();
        try {
            processBean = workFlowClientService.modelList(processInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "查询流程图列表失败：" + e.getMessage());
        }
        return processBean;
    }

    /**
     * 获取下一步所有任务
     */
    @ResponseBody
    @RequestMapping(value = "/json/task/getAllNextTaskInfo")
    public TaskBean getAllNextTaskInfo(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.getAllNextTaskInfo(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询下一步所有任务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除整个流程实例，包括历史数据
     *
     * @param info
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/deleteProcessInstance")
    public ProcessBean deleteProcessInstance(@RequestBody ProcessInfo info) {
        ProcessBean processBean = new ProcessBean();
        try {
            processBean = workFlowClientService.deleteProcessInstance(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "删除流程实例失败：" + e.getMessage());
        }
        return processBean;
    }

    /**
     * 根据流程定义id获取任务节点信息
     *
     * @param processDefinitionId
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/getTaskDefinitions")
    public TaskBean getTaskDefinitions(@RequestBody String processDefinitionId) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.getTaskDefinitions(processDefinitionId);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "根据流程定义id查询任务节点信息失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据流程定义key获取任务节点信息
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/getTaskDefinitionsByKey")
    public TaskBean getTaskDefinitionsByKey(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.getTaskDefinitionsByKey(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "根据流程定义key查询任务节点信息失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 先查分类树,并获取每个分类下的已办扩展表头
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/getAllClassifyCompletedTasks")
    public List<TaskBean> getAllClassifyCompletedTasks(@RequestBody TaskInfo info) {
        List<TaskBean> result = new ArrayList<>();
        TaskBean taskBean = new TaskBean();
        try {
            result = workFlowClientService.getAllClassifyCompletedTasks(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(taskBean, FAILED, "查询已办扩展表头失败：" + e.getMessage());
            result.add(taskBean);
        }
        return result;
    }

    /**
     * 先查分类树,并获取每个分类下的待办扩展表头
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/getAllClassifyPendTasks")
    public List<TaskBean> getAllClassifyPendTasks(@RequestBody TaskInfo info) {
        List<TaskBean> result = new ArrayList<>();
        TaskBean taskBean = new TaskBean();
        try {
            result = workFlowClientService.getAllClassifyPendTasks(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(taskBean, FAILED, "查询待办扩展表头失败：" + e.getMessage());
            result.add(taskBean);
        }
        return result;
    }

    /**
     * 批量发起流程，支持批量回滚
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/batchStartProcess")
    public List<ProcessBean> batchStartProcess(@RequestBody List<ProcessInfo> processInfoList) {
        List<ProcessBean> result = new ArrayList<>();
        ProcessBean processBean = new ProcessBean();
        try {
            result = workFlowClientService.batchStartProcess(processInfoList);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "批量发起流程失败：" + e.getMessage());
            result.add(processBean);
        }
        return result;
    }

    /**
     * 批量发起流程并且提交任务，支持批量回滚
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/batchStartAndSubmitProcess")
    public List<ProcessBean> batchStartAndSubmitProcess(@RequestBody List<ProcessInfo> processInfoList) {
        List<ProcessBean> result = new ArrayList<>();
        ProcessBean processBean = new ProcessBean();
        try {
            result = workFlowClientService.batchStartAndSubmitProcess(processInfoList);
        } catch (Exception e) {
            ResultUtil.setReturnCode(processBean, FAILED, "批量发起并提交流程失败：" + e.getMessage());
            result.add(processBean);
        }
        return result;
    }

    /**
     * 批量退回流程
     */
    @ResponseBody
    @RequestMapping("/json/task/batchBackTask")
    public List<TaskBean> batchBackTask(@RequestBody List<TaskBean> taskBeanList) {
        List<TaskBean> result = new ArrayList<>();
        TaskBean taskBean = new TaskBean();
        try {
            result = workFlowClientService.batchBackTask(taskBeanList);
        } catch (Exception e) {
            ResultUtil.setReturnCode(taskBean, FAILED, "批量退回任务失败：" + e.getMessage());
            result.add(taskBean);
        }
        return result;
    }


    public void setWorkFlowClientService(WorkFlowClientService workFlowClientService) {
        this.workFlowClientService = workFlowClientService;
    }

    /**
     * 根据流程定义key和第一个节点的id获取属性信息
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/getFirstTaskDefine")
    public TaskBean getFirstTaskDefine(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.getFirstTaskDefine(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询第一个节点信息失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 转办任务
     *
     * @param taskInfo
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/json/task/transferTask")
    public TaskBean transferTask(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.transferTask(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "转办任务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 批量提交任务
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/batchSubmitTask")
    public List<TaskBean> batchSubmitTask(@RequestBody List<TaskBean> taskBeans) {
        List<TaskBean> result = new ArrayList<>();
        TaskBean taskBean = new TaskBean();
        try {
            result = workFlowClientService.batchSubmitTask(taskBeans);
        } catch (Exception e) {
            ResultUtil.setReturnCode(taskBean, FAILED, "批量提交任务失败：" + e.getMessage());
            result.add(taskBean);
        }
        return result;
    }


    /**
     * 批量操作任务
     *
     * @param batchExecuteParam 批量任务对象
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/batchExecuteTask")
    public BatchExecuteVo batchExecuteTask(@RequestBody BatchExecuteParam batchExecuteParam) {
        BatchExecuteVo result = new BatchExecuteVo();
        try {
            result = workFlowClientService.batchExecuteTask(batchExecuteParam);
        } catch (Exception e) {
            result.setRetCode(FAILED);
            result.setRetMsg("批量操作任务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 标记任务，已阅、未阅
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/signTaskStatus")
    public TaskBean signTaskStatus(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.signTaskStatus(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "标记任务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 标记任务，已阅、未阅
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/removeTaskStatus")
    public TaskBean removeTaskStatus(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.removeTaskStatus(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "删除任务标记失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取任务，已阅、未阅状态
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/getSignTaskStatus")
    public TaskBean getSignTaskStatus(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.getSignTaskStatus(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询任务标记状态失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 流程结束后获取流程变量信息
     *
     * @param processInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/findFinishedVariable")
    public TaskBean findFinishedVariable(@RequestBody ProcessInfo processInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.findFinishedVariable(processInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询流程变量信息失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 流程结束后获取流程变量信息
     *
     * @param classifyInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/findClassifyList")
    public List<ClassifyInfo> findClassifyList(@RequestBody ClassifyInfo classifyInfo) {
        return workFlowClientService.findClassifyList(classifyInfo);
    }


    /**
     * 根据模型部署流程
     *
     * @param processDefinitionId
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/deployByDefinitionId")
    public ProcessBean deployByDefinitionId(@RequestBody String processDefinitionId) {
        ProcessBean result = new ProcessBean();
        try {
            result = workFlowClientService.deployByDefinitionId(processDefinitionId);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "发布流程图失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 部署流程
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/json/task/deploy")
    public ProcessBean deploy(DeployInfo deployInfo, @RequestParam(value = "file", required = true) MultipartFile file) {
        ProcessBean result = new ProcessBean();
        try {
            String fileName = file.getOriginalFilename();
            InputStream fileInputStream = null;
            try {
                fileInputStream = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            deployInfo.setInputStream(fileInputStream);
            deployInfo.setFileName(fileName);
            result = workFlowClientService.deploy(deployInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "发布流程图失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 复制流程模型
     *
     * @param modelInfo
     */
    @ResponseBody
    @RequestMapping("/json/model/copyModel")
    public ProcessBean copyModel(@RequestBody ModelInfo modelInfo) {
        ProcessBean result = new ProcessBean();
        try {
            result = workFlowClientService.copyModel(modelInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "复制流程图失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 岗位待办
     */
    @ResponseBody
    @RequestMapping("/json/task/findPendOrgTaskList")
    public TaskBean findPendOrgTaskList(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.findPendOrgTaskList(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询岗位待办任务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 发送信息给接收任务
     */
    @ResponseBody
    @RequestMapping("/json/process/sendMessageToReceiveTask")
    public ProcessBean sendMessageToReceiveTask(@RequestBody ProcessInfo processInfo) {
        ProcessBean result = new ProcessBean();
        try {
            result = workFlowClientService.messageConfirmProcessInstanceId(processInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "发送信息到接收任务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询机构任务数
     */
    @ResponseBody
    @RequestMapping("/json/task/findOrgTaskCount")
    public QueryBean findOrgTaskCount(@RequestBody TaskInfo taskInfo) {
        QueryBean result = new QueryBean();
        try {
            result = workFlowClientService.findOrgTaskCount(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询机构任务数失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询异常服务
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/findErrorServiceTask")
    public Map<String, Object> findErrorServiceTask(@RequestParam Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        try {
            result = workFlowClientService.findErrorServiceTask(map);
        } catch (Exception e) {
            result.put("retCode", FAILED);
            result.put("retMsg", "查询异常服务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 重试异常服务
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/retryServiceTask")
    public TaskBean retryServiceTask(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.retryServiceTask(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "重试异常服务失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 改派任务
     */
    @ResponseBody
    @RequestMapping("/json/task/reAssignmentTask")
    public TaskBean reAssignmentTask(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.reAssignmentTask(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "改派失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 领取任务
     */
    @ResponseBody
    @RequestMapping("/json/task/claimWorkInfo")
    public TaskBean claimWorkInfo(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.claimWorkInfo(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "任务领取失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 创建流程图
     */
    @ResponseBody
    @RequestMapping("/json/task/createModel")
    public ProcessBean createModel(@RequestBody ModelInfo modelInfo) {
        ProcessBean result = new ProcessBean();
        try {
            result = workFlowClientService.createModel(modelInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "创建流程图失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除流程图
     */
    @ResponseBody
    @RequestMapping("/json/task/deleteModel")
    public ProcessBean deleteModel(@RequestBody ModelInfo modelInfo) {
        ProcessBean result = new ProcessBean();
        try {
            result = workFlowClientService.deleteModel(modelInfo.getKey(), modelInfo.getTenantId());
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "删除流程图失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取已完成的流程
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/getCompletedProcess")
    public ProcessBean getCompletedProcess(@RequestBody ProcessInfo processInfo) {
        ProcessBean result = new ProcessBean();
        try {
            result = workFlowClientService.getCompletedProcess(processInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询已完成的流程失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据流程实例ID批量终止流程
     *
     * @param info
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/json/processInstance/batchTerminateProcess", method = RequestMethod.POST)
    public ProcessBean batchterminateProcess(@RequestBody List<ProcessInfo> info) {
        ProcessBean result = new ProcessBean();
        try {
            result = workFlowClientService.batchTerminateProcess(info);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "批量终止流程失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据userId获取所有的待办任务
     *
     * @param taskInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/task/findTodoList")
    public TaskBean findTodoList(@RequestBody TaskInfo taskInfo) {
        TaskBean result = new TaskBean();
        try {
            result = workFlowClientService.findTodoList(taskInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "查询个人待办失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 设置流程变量
     *
     * @param processInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/json/processInstance/setProcessVariable")
    public ProcessBean setProcessVariable(@RequestBody ProcessInfo processInfo) {
        ProcessBean result = new ProcessBean();
        try {
            result = workFlowClientService.setProcessVariable(processInfo);
        } catch (Exception e) {
            ResultUtil.setReturnCode(result, FAILED, "设置流程变量失败：" + e.getMessage());
        }
        return result;
    }
}