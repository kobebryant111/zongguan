package com.work.ppm.application.api.inforTechOut;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.esb.req.PpmKqFaceDataEsbReqDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.inforTechOut.PhoneApproveDto;
import com.work.ppm.application.dto.inforTechOut.PpmOutsourcingXklx;
import com.work.ppm.application.dto.inforTechOut.PpmOutsourcingXklxDto;
import com.work.ppm.application.dto.inforTechOut.XJMultiSequenceRequestDTO;
import com.work.ppm.application.dto.inforTechOut.XKFileUploadDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.inforTechOut.InforOutsourcFile;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingAccessapply;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.inforTechOut.InforTechOutService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 信息科技外包立项审批模块
 * </p>
 *
 * @since 20260625
 */

@Slf4j
@Api(produces = "application/json;charset=UTF-8", tags = {"inforTechOutApi"})
@RestController
@RequestMapping("/inforTechOut")
public class InforTechOutApi extends BaseUploadApi {

    @Autowired
    private InforTechOutService inforTechOutService;

    /**
     * 上传附件
     *
     * @param file
     * @return Result
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public ApiResult upload(@Validated XKFileUploadDto req,
                            @NotNull(message = "文件不能为空") MultipartFile file, HttpServletRequest request) {
        try {
            CommOperatorDoLoginDto user = super.getCurrentUser(request);
            req.setBadge(user.getUserid());
            req.setEmpname(user.getOperatorname());
            this.inforTechOutService.upload(req, file);
            return ApiResult.newSuccess("上传文件成功");
        } catch (Exception e) {
            log.error("上传失败",e);
            throw new BizException(e);
        }
    }


    /**
     * 获取编号自动增1
     *
     * @param xJMultiSequenceRequestDTO 查询条件
     * @return MercuryResponseDTO对象
     */
    @GetMapping("/getNextSeqNo")
    @ApiOperation(value = "获取编号自动增1", notes = "获取编号自动增1")
    public ApiResult getNextSeqNo(XJMultiSequenceRequestDTO xJMultiSequenceRequestDTO) {
        return ApiResult.newSuccessResults(this.inforTechOutService.getNextSeqNo(xJMultiSequenceRequestDTO));
    }

    /**
     * 分页
     *
     * @param outReqid 文件id
     * @return ApiResult<List>
     */
    @GetMapping(value = "queryInforOutsourcFile")
    @ApiOperation(value = "查询项目文件", notes = "查询项目文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rdProjectId", value = "项目id", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> queryInforOutsourcFile(@NotBlank(message = "研发项目id不能为空")
                                                  @Param("rdProjectId") String outReqid) {
        List<InforOutsourcFile> list = this.inforTechOutService.queryInforOutsourcFile(outReqid);
        return ApiResult.newSuccessResults(list);
    }

    /**
     * 下载文件
     *
     * @param id
     */
    @PostMapping("/download")
    @ApiOperation(value = "下载文件", notes = "下载文件")
    public void download(@NotBlank(message = "研发项目id不能为空") @Param("id") String id, HttpServletResponse resp) throws IOException {
        try {
            this.inforTechOutService.download(id, resp);
        } catch (Exception e) {
            log.error("下载文件失败",e);
            throw new BizRuntimeException(e.getMessage());
        }
//        AssertUtil.notNull(file, "文件不存在");
//        resp.setHeader("Content-Disposition", "attachment;filename=" +
//                URLEncoder.encode("UTF-8"));
//        resp.setContentType("application/octet-stream");
//        IoUtil.copy(Files.newInputStream(new File(file.getFilePath()).toPath()),
//                resp.getOutputStream());
    }

    /**
     * 删除文件
     *
     * @param id
     * @return ApiResult
     */
    @GetMapping(value = "/delete")
    @ApiOperation(value = "删除文件", notes = "删除文件")
    public ApiResult delete(@NotBlank(message = "研发项目id不能为空") @Param("id") String id) {
        this.inforTechOutService.delete(id);
        return ApiResult.newSuccess();
    }


    /**
     * 获取申请部门负责人
     *
     * @param depid
     * @return ApiResult
     */
    @GetMapping(value = "/getAplyDirector")
    @ApiOperation(value = "获取申请部门负责人", notes = "获取申请部门负责人")
    public ApiResult<List> getAplyDirector(@NotBlank(message = "获取申请部门负责人部门id不能为空") @Param("depid") String depid) {
        List<CommEmployee> list = this.inforTechOutService.getAplyDirector(depid);
        return ApiResult.newSuccessResults(list);
    }

    /**
     * id
     * 获取用工部门负责人
     *
     * @param outDepid
     * @return ApiResult
     */
    @GetMapping(value = "/getOutDirector")
    @ApiOperation(value = "获取用工部门负责人", notes = "获取用工部门负责人")
    public ApiResult<List> getOutDirector(@NotBlank(message = "用工部门负责人部门id不能为空") @Param("outDepid") String outDepid) {
        List<CommEmployee> list = this.inforTechOutService.getOutDirector(outDepid);
        return ApiResult.newSuccessResults(list);
    }

    /**
     * 保存信息科技外包立项申请
     *
     * @param applyDto
     * @return
     */
    @PostMapping(value = "/saveInforTechApply")
    @ApiOperation(value = "保存信息科技外包立项申请", notes = "保存信息科技外包立项申请")
    public ApiResult<PpmOutsourcingXklx> saveInforTechApply(@RequestBody PpmOutsourcingXklx applyDto, HttpServletRequest request) {

        ApiResult<PpmOutsourcingXklx> result = new ApiResult<>();
        BpsWorkFlowDto bps = new BpsWorkFlowDto();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        PpmOutsourcingXklx dto = this.inforTechOutService.saveInforTechApply(applyDto, bps);

        if (null == dto) {
            result.failure("保存失败");
        } else {
            result.success("保存成功");
            result.setResults(dto);
        }
        return result;
    }
    /**
     * 删除
     *
     * @param accessapplyDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceId", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deletePpmInterviewApply(PpmOutsourcingXklx accessapply) {
        ApiResult result = new ApiResult<>();

        int id = inforTechOutService.deletePpmOutsourcingXklx(accessapply);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
    @PostMapping("outLeaveStart")
    @ApiOperation("发起申请")
    public ApiResult<String> outLeaveStart(@RequestBody PpmOutsourcingXklx applyDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = applyDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());

        String msg = this.inforTechOutService.outLeaveStart(applyDto, bps);
        result.success(msg);
        return result;
    }

    @GetMapping("/getDsp")
    @ApiOperation(value = "查询代审批", notes = "查询代审批")
    public ApiResult<TaskBean> getDb(HttpServletRequest request, String processDefineKey, String classifyCode) {
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskInfo.setProcessDefineKey(processDefineKey);
            taskInfo.setWfClassifyCode(classifyCode); //分类编码
            taskInfo.setUserId(super.getCurrentUser(request).getUserid());// 用户id/工号
            result.setStatus(ApiCode.SUCCESS);
//            List<PpmOutsourcingXklx> list = this.inforTechOutService.getDb(CamundaUtils.findXkTodoList(taskInfo));
            result.setResults(CamundaUtils.findXkTodoList(taskInfo));
        } catch (Exception e) {
            log.error("查询代审批任务失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询代审批任务失败!");
        }
        return result;
    }

    /**
     * 根据流程instanceid查询数据
     *
     * @param info 对象
     * @return ApiResult<PpmOutsourcingLzsqInfoDto>
     */
    @PostMapping("getArvlDetail")
    @ApiOperation("根据流程instanceid查询数据")
    public ApiResult<PpmOutsourcingXklx> getArvlDetail(PpmOutsourcingXklx info) {
        ApiResult<PpmOutsourcingXklx> result = new ApiResult<>();
        PpmOutsourcingXklx dto = inforTechOutService.getDb(info);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping("managerApprove")
    @ApiOperation("申请部门负责人审批")
    public ApiResult<String> managerApprove(@RequestBody PpmOutsourcingXklx ppmOutsourcingXklx, HttpServletRequest request) {

        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = ppmOutsourcingXklx.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = inforTechOutService.managerApprove(ppmOutsourcingXklx, bps);
        result.success(msg);
        return result;
    }


    @PostMapping("hiringDepApprove")
    @ApiOperation("用工部门负责人审批")
    public ApiResult<String> hiringDepApprove(@RequestBody PpmOutsourcingXklx ppmOutsourcingXklx, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = ppmOutsourcingXklx.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = inforTechOutService.hiringDepApprove(ppmOutsourcingXklx, bps);
        result.success(msg);
        return result;

    }

    @PostMapping("financeApprove")
    @ApiOperation("金融科技部经办人审核")
    public ApiResult<String> financeApprove(@RequestBody PpmOutsourcingXklx ppmOutsourcingXklx, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = ppmOutsourcingXklx.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = inforTechOutService.financeApprove(ppmOutsourcingXklx, bps);
        result.success(msg);
        return result;

    }

    @PostMapping("riskHandApprove")
    @ApiOperation("风险管理部经办人审核")
    public ApiResult<String> riskHandApprove(@RequestBody PpmOutsourcingXklx ppmOutsourcingXklx, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = ppmOutsourcingXklx.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = inforTechOutService.riskHandApprove(ppmOutsourcingXklx, bps);
        result.success(msg);
        return result;

    }

    @PostMapping("resPerApprove")
    @ApiOperation("人力资源部负责人审批")
    public ApiResult<String> resPerApprove(@RequestBody PpmOutsourcingXklx ppmOutsourcingXklx, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = ppmOutsourcingXklx.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = inforTechOutService.resPerApprove(ppmOutsourcingXklx, bps);
        result.success(msg);
        return result;
    }

    @PostMapping("queryList")
    @ApiOperation("信息科技外包立项查询")
    public ApiResult<List> queryList(PpmOutsourcingXklx ppmOutsourcingXklx, Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        inforTechOutService.queryList(ppmOutsourcingXklx, pager, user);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    @PostMapping("queryListInfo")
    @ApiOperation("信息科技外包立项查询")
    public void queryListInfo(@RequestBody PpmOutsourcingXklx ppmOutsourcingXklx, @RequestBody Pager pager, HttpServletRequest request,
                              HttpServletResponse response) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        inforTechOutService.queryList(ppmOutsourcingXklx, pager, user);
        List<PpmOutsourcingXklxDto> dtos = JsonUtil.jsonToList(pager.getList(), PpmOutsourcingXklxDto.class);

        if (!dtos.isEmpty()) {
            ExcelUtil.exportExcel(dtos, PpmOutsourcingXklxDto.class, "信息科技外包立项表导出.xls", response);
        }
        log.info("导出成功。");
    }

    @PostMapping("getSignDetail")
    @ApiOperation("根据流程instanceid查询审批信息")
    public ApiResult<List> getSignDetail(PpmFlowLog info) {
        ApiResult<List> result = new ApiResult<>();
        List<PpmFlowLogDto> dto = inforTechOutService.getSignDetail(info);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping("getDetail")
    @ApiOperation("根据流程instanceid查询审批信息")
    public ApiResult<PpmOutsourcingXklx> getDetail(@RequestBody JSONObject upBody ) {
        log.info("手机工作平台查询信息:{}",upBody);
//        String body = JsonUtil.toJSONString(upBody.get("BODY"));
        PpmOutsourcingXklx info = JsonUtil.jsonToClass(upBody, PpmOutsourcingXklx.class);
        ApiResult<PpmOutsourcingXklx> result = new ApiResult<>();
        PpmOutsourcingXklx xklx =inforTechOutService.getPhoneDb(info);
        result.setResults(xklx);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping("phoneApprove")
    @ApiOperation("根据流程instanceid查询审批信息")
    public ApiResult phoneApprove(@RequestBody JSONObject upBody) {
        log.info("手机工作平台审批信息:{}",upBody);
//        String body = JsonUtil.toJSONString(upBody.get("BODY"));
        PhoneApproveDto info = JsonUtil.jsonToClass(upBody, PhoneApproveDto.class);
        ApiResult<TaskBean> result = new ApiResult<>();
        try {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTenantId(BusinessConstant.TENANT_ID);// 租户id
            taskInfo.setProcessDefineKey(null);
            taskInfo.setWfClassifyCode(null); //分类编码
            taskInfo.setUserId(info.getUserId());// 用户id/工号
            TaskBean taskBean = CamundaUtils.findXkTodoList(taskInfo);
            Map bpsMap = taskBean.getTaskList().stream()
                    .filter(map -> Objects.equals(String.valueOf(map.get("processInstanceId")), info.getInstanceid())
                    ).findFirst().orElse(null);
            Objects.requireNonNull(bpsMap, "查询代审批不能为空");
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
//            assert bpsMap != null;
            bps.setProcessInstId((String) bpsMap.get("processInstanceId"));
            bps.setTaskID((String) bpsMap.get("taskId"));
            bps.setActivityName((String) bpsMap.get("activityName"));
            bps.setAssignee((String) bpsMap.get("assignee"));
            bps.setActivityInstId((String) bpsMap.get("activityId"));
            bps.setUserId(info.getUserId());
            bps.setUserName(info.getUserName());
            bps.setConclusion(info.getConclusion());
            bps.setSuggest(info.getSuggest());
            String msg = inforTechOutService.phoneApprove(info, bps);
            result.success(msg);
            return result;
        } catch (Exception e) {
            log.error("查询代审批任务失败：{}", e);
            throw new BizException(ApiCode.FAIL, "查询代审批任务失败!");
        }

    }
}