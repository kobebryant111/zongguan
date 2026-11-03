package com.work.ppm.application.api.workflow;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.flowmanager.ICommonFlowService;
import com.work.ppm.application.services.workflowmanager.impl.WorkflowOutputService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.StringUtil;
import com.tansun.workflow.model.ClassifyInfo;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/workflow")
@Api(value = "workflow", tags = "工作流管理接口")
public class MyTaskController extends BaseUploadApi {
    //公共流程接口
    private final ICommonFlowService commonFlowService;
    //工作流远程调用服务
    private final WorkFlowHttpClientService workFlowHttpClientService;

    private final WorkflowOutputService workflowOutputService;

    /**
     * 查询流程分类列表
     *
     * @return
     */
    @GetMapping("/findClassifyList")
    @ApiOperation(value = "查询流程分类列表", notes = "查询流程分类列表")
    public ApiResult<List<ClassifyInfo>> findClassifyList() {
        ApiResult<List<ClassifyInfo>> result = new ApiResult<>();
        try {
            ClassifyInfo classifyInfo = new ClassifyInfo();
            classifyInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowHttpClientService.findClassifyList(classifyInfo));
        } catch (Exception e) {
            log.error("查询流程分类列表失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询流程分类列表!");
        }
        return result;
    }

    /**
     * 代办任务
     *
     * @param request          HttpServletRequest
     * @param processDefineKey 流程KEY
     * @param classifyCode     分类编码
     * @return
     */
    @GetMapping("/getDb")
    @ApiOperation(value = "查询代办任务", notes = "查询代办任务")
    public ApiResult<TaskBean> getDb(HttpServletRequest request, String processDefineKey, String classifyCode) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskInfo.setProcessDefineKey(processDefineKey);
            taskInfo.setWfClassifyCode(classifyCode); //分类编码
            taskInfo.setUserId(super.getCurrentUser(request).getUserid());// 用户id/工号
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(CamundaUtils.findTodoList(taskInfo));
        } catch (Exception e) {
            log.error("查询代办任务失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询代办任务失败!");
        }
        return result;
    }

    /**
     * 依据processDefineKey查询代办任务
     *
     * @param request          HttpServletRequest
     * @param processDefineKey 流程KEY
     * @param classifyCode     分类编码
     * @return ApiResult<TaskBean>
     */
    @PostMapping("/getDbDetails")
    @ApiOperation(value = "查询代办任务", notes = "查询代办任务")
    public ApiResult<TaskBean> getDbDetails(HttpServletRequest request, String processDefineKey, String classifyCode) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskInfo.setProcessDefineKey(processDefineKey);
            taskInfo.setWfClassifyCode(classifyCode); //分类编码
            taskInfo.setUserId(super.getCurrentUser(request).getUserid());// 用户id/工号
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(CamundaUtils.findTodoList(taskInfo));
        } catch (Exception e) {
            log.error("查询代办任务失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询代办任务失败!");
        }
        return result;
    }

    /**
     * 我的已办
     *
     * @param request          HttpServletRequest
     * @param processDefineKey 流程KEY
     * @param classifyCode     分类编码
     */
    @GetMapping("getYb")
    @ApiOperation("我的已办")
    public ApiResult<TaskBean> getYb(HttpServletRequest request, String processDefineKey, String classifyCode) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskInfo.setProcessDefineKey(processDefineKey);
            taskInfo.setWfClassifyCode(classifyCode); //分类编码
            taskInfo.setUserId(super.getCurrentUser(request).getUserid());// 用户id/工号
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(CamundaUtils.findTaskList(taskInfo));
        } catch (Exception e) {
            log.error("查询已办任务失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询已办任务失败!");
        }
        return result;
    }

    /**
     * 我的归档
     *
     * @param dto     对象
     * @param pager   分页对象
     * @param request request
     * @return ApiResult<List < PpmProjectFlowDto>>
     */
    @GetMapping("getGd")
    @ApiOperation("我的归档")
    public ApiResult<List<PpmProjectFlowDto>> getGd(PpmProjectFlowDto dto, Pager pager, HttpServletRequest request) {
        ApiResult<List<PpmProjectFlowDto>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        dto.setLastmoduser(user.getUserid());
        commonFlowService.getGdWorkItem(dto, pager);
        result.setResults((List<PpmProjectFlowDto>) pager.getList());
        result.setTotal(pager.getTotal());
        result.success("获取成功");
        return result;
    }

    /**
     * 我的归档
     *
     * @param dto     对象
     * @param pager   分页对象
     * @param request request
     * @return ApiResult<List < PpmProjectFlowDto>>
     */
    @PostMapping("getGdNew")
    @ApiOperation("我的归档")
    public ApiResult<List<PpmProjectFlowDto>> getGdNew(PpmProjectFlowDto dto, Pager pager, HttpServletRequest request) {
        ApiResult<List<PpmProjectFlowDto>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        dto.setLastmoduser(user.getUserid());
        commonFlowService.getGdWorkItem(dto, pager);
        result.setResults((List<PpmProjectFlowDto>) pager.getList());
        result.setTotal(pager.getTotal());
        result.success("获取成功");
        return result;
    }

    /**
     * 执行授权
     *
     * @param dto     对象
     * @param request request
     * @return ApiResult<String>
     */
    @PostMapping("doAuth")
    @ApiOperation(value = "流程授权")
    public ApiResult<String> doAuth(BpsWorkFlowDto dto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        dto.setUserId(user.getUserid());
        workflowOutputService.accreditTask(dto,"delete");
        result.setResults(CamundaUtils.doAuth(dto));
        workflowOutputService.accreditTask(dto,"push");
        result.success("授权成功");
        return result;
    }

    /**
     * 回收任务
     *
     * @param request
     * @param dto
     * @return
     */
    @PostMapping("/reBackTask")
    @ApiOperation(value = "回收任务", notes = "回收任务")
    public ApiResult<String> reBackTask(HttpServletRequest request, BpsWorkFlowDto dto) throws Exception {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        dto.setUserId(user.getUserid());
        TaskBean taskBean = CamundaUtils.reBackTask(dto);
        //推送待办
        result.failure("回收任务失败");
        if ("0".equals(taskBean.getRetCode())) {
            result.success("回收任务成功");
            workflowOutputService.reBackTaskPush(dto);
        }
        return result;
    }

    /**
     * 终止流程
     *
     * @param request
     * @param dto
     * @return
     */
    @PostMapping("/terminateProcess")
    @ApiOperation(value = "终止流程", notes = "终止流程")
    public ApiResult<ProcessBean> terminateProcess(HttpServletRequest request, BpsWorkFlowDto dto) {
        ApiResult<ProcessBean> result = new ApiResult<>();
        try {
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessInstanceId(dto.getProcessInstId());
            processInfo.setUserId(super.getCurrentUser(request).getUserid());// 操作人
            processInfo.setComment("取消申请");
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowHttpClientService.terminateProcess(processInfo));
            CamundaUtils.delProjectFlow(dto.getProcessInstId());
        } catch (Exception e) {
            log.error("终止流程失败：{}", e);
            throw new BizException(ApiCode.FAIL, "终止流程失败!");
        }
        return result;
    }

    /**
     * 查询已办任务
     *
     * @param request          HttpServletRequest
     * @param processDefineKey 流程KEY
     * @param classifyCode     分类编码
     * @return
     */
    @GetMapping("/findTaskList")
    @ApiOperation(value = "查询已办任务", notes = "查询已办任务")
    public ApiResult<TaskBean> findTaskList(HttpServletRequest request, String processDefineKey, String classifyCode) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskInfo.setProcessDefineKey(processDefineKey);
            taskInfo.setWfClassifyCode(classifyCode); //分类编码
            taskInfo.setUserId(super.getCurrentUser(request).getUserid());// 用户id/工号
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowHttpClientService.findTaskList(taskInfo));
        } catch (Exception e) {
            log.error("查询已办任务失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询已办任务失败!");
        }
        return result;
    }

    /**
     * 查询任务详情
     *
     * @param taskId 任务ID
     * @return
     */
    @PostMapping("/findNextTaskInfo")
    @ApiOperation(value = "查询任务详情", notes = "查询任务详情")
    public ApiResult<TaskBean> findTaskInfo(String taskId, String state) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);
            taskInfo.setTaskId(taskId);
            taskInfo.setState(state);
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowHttpClientService.findTaskInfo(taskInfo));
        } catch (Exception e) {
            log.error("查询下一节点清单失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询下一节点清单失败!");
        }
        return result;
    }

    /**
     * 获取流程当前节点信息
     *
     * @param dto 实例
     * @return
     */
    @GetMapping("/getTasksByInstanceId")
    @ApiOperation(value = "获取流程当前节点信息", notes = "获取流程当前节点信息")
    public ApiResult<BpsWorkFlowDto> getTasksByProcessInstanceId(BpsWorkFlowDto dto) {
        ApiResult<BpsWorkFlowDto> result = new ApiResult<>();
        try {
            BpsWorkFlowDto workFlowDto = new BpsWorkFlowDto();
            TaskBean taskBean = workFlowHttpClientService.getTasksByProcessInstanceId(dto.getProcessInstId());
            if (null != taskBean && taskBean.getTaskList().size() > 0) {
                for (Map map : taskBean.getTaskList()) {
                    workFlowDto.setProcessInstId(taskBean.getProcessInstanceId());
                    workFlowDto.setTaskId((String) map.getOrDefault("taskId", null));
                    break;
                }
            }
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowDto);
        } catch (Exception e) {
            log.error("获取当前节点信息失败：{}", e);
            throw new BizException(ApiCode.FAIL, "获取当前节点信息失败!");
        }
        return result;
    }


    /**
     * 获取按钮信息
     *
     * @param processDefineId 流程定义ID
     * @param activityId      任务key
     * @return
     */
    @PostMapping("/findButtonPower")
    @ApiOperation(value = "获取按钮信息", notes = "获取按钮信息")
    public ApiResult<TaskBean> findButtonPower(String processDefineId, String activityId) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setProcessDefinitionId(processDefineId);
            taskInfo.setActivityId(activityId);
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowHttpClientService.findButtonPower(taskInfo));
        } catch (Exception e) {
            log.error("获取按钮信息失败：{}", e);
            throw new BizException(ApiCode.FAIL, "获取按钮信息失败!");
        }
        return result;
    }


    /**
     * 查询审批记录
     *
     * @param instanceId 实例ID
     * @return
     */
    @PostMapping("/queryProcessTrack")
    @ApiOperation(value = "查询审批记录", notes = "查询审批记录")
    public ApiResult<TaskBean> queryProcessTrack(String instanceId) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            processInfo.setProcessInstanceId(instanceId);// 流程实例id
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowHttpClientService.queryProcessTrack(processInfo));
        } catch (Exception e) {
            log.error("查询审批记录失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询审批记录失败!");
        }
        return result;
    }

    /**
     * 获取可回退节点列表
     *
     * @param instanceId
     * @param taskId
     * @return
     */
    @PostMapping("/findHiTask")
    public ApiResult<TaskBean> findHiTask(String instanceId, String activityId, String taskId) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(CamundaUtils.findHiTask(instanceId, activityId, taskId));
        } catch (Exception e) {
            log.error("获取可回退节点列表失败：{}", e);
            throw new BizException(ApiCode.FAIL, "获取可回退节点列表失败!");
        }
        return result;
    }

    /**
     * 退回到指定节点
     *
     * @param request
     * @param dto
     * @return
     */
    @PostMapping("/backTask")
    @ApiOperation(value = "退回到指定节点", notes = "退回到指定节点")
    public ApiResult<TaskBean> backTask(HttpServletRequest request, BpsWorkFlowDto dto) {
        TaskBean taskBean = new TaskBean();
        taskBean.setTenantId(BusinessConstant.TENANT_ID);// 租户id
        taskBean.setProcessInstanceId(dto.getProcessInstId());
        taskBean.setTaskId(dto.getTaskId());// 任务id
        taskBean.setUserId(super.getCurrentUser(request).getUserid());// 用户id/工号
        taskBean.setComment(dto.getSuggest());// 审批意见
        taskBean.setNextActivityIds(Lists.newArrayList(dto.getActivityInstId()));
        //退回的参数
        if (StringUtil.isNotBlank(dto.getConclusion())) {
            Map mPara = new HashMap();
            mPara.put("condition", dto.getConclusion());// 审批状态
            taskBean.setmPara(mPara);// 流程变量
        }
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowHttpClientService.backTask(taskBean));
            return result;
        } catch (Exception e) {
            log.error("外包人员准入退回到指定节点失败：{}", e);
            throw new BizException(ApiCode.FAIL, "退回到指定节点失败!");
        }
    }

    /**
     * 退回任务到前一节点
     *
     * @param request   HttpServletRequest
     * @param processId 实例ID
     * @param taskId    任务ID
     * @param comment   评论
     * @return
     */
    @PostMapping("/backPreTask")
    @ApiOperation(value = "退回任务到前一节点", notes = "退回任务到前一节点")
    public ApiResult<TaskBean> backPreTask(HttpServletRequest request, String processId, String taskId, String comment) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskBean taskBean = new TaskBean();
            taskBean.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskBean.setProcessInstanceId(processId);
            taskBean.setTaskId(taskId);
            taskBean.setComment(comment);
            taskBean.setUserId(super.getCurrentUser(request).getUserid());// 用户id/工号
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowHttpClientService.backPreTask(taskBean));
        } catch (Exception e) {
            log.error("退回任务到前一节点失败：{}", e);
            throw new BizException(ApiCode.FAIL, "退回任务到前一节点失败!");
        }
        return result;
    }

    /**
     * 退回到第一个用户审批节点
     *
     * @param request   HttpServletRequest
     * @param processId 实例ID
     * @param taskId    任务ID
     * @param comment   评论
     * @return
     */
    @PostMapping("/backToFirstUserTask")
    @ApiOperation(value = "退回到第一个用户审批节点", notes = "退回到第一个用户审批节点")
    public ApiResult<TaskBean> backToFirstUserTask(HttpServletRequest request, String processId, String taskId, String comment) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskBean taskBean = new TaskBean();
            taskBean.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskBean.setProcessInstanceId(processId);
            taskBean.setTaskId(taskId);
            taskBean.setComment(comment);
            taskBean.setUserId(super.getCurrentUser(request).getUserid());// 用户id/工号
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(workFlowHttpClientService.backToFirstUserTask(taskBean));
        } catch (Exception e) {
            log.error("退回到第一个用户审批节点失败：{}", e);
            throw new BizException(ApiCode.FAIL, "退回到第一个用户审批节点失败!");
        }
        return result;
    }


}
