package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmInterviewApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmInterviewerInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmInterviewApply;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;
import com.work.ppm.application.services.integratedmanager.IPpmInterviewApplyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 外包面试申请
 *
 */
@Slf4j
@RestController
@RequestMapping("ppmInterviewApply")
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmInterviewApplyApi"}, description = "外包面试申请")
public class PpmInterviewApplyApi extends BaseUploadApi {
    /**
     * 面试接口
     */
    @Autowired
    private IPpmInterviewApplyService ppmInterviewApplyService;

    /**
     * 草稿
     *
     * @param ppmInterviewerInfo 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("draft")
    @ApiOperation(value = "草稿保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "veList", value = "面试官集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "eeList", value = "外包面试员工集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "processInstDesc", value = "流程描述,例如：发起申请 节点", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "申请人名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processDefName", value = "流程定义名称 (com.work.ppm.bps.glzxzdgz)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processInstName", value = "流程实例名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgid", value = "面试官工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgname", value = "面试官姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgdeptname", value = "面试官部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgdeptno", value = "面试官部门ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msname", value = "面试人姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "面试人性别 0:女1:男", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "idcard", value = "身份证", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workyears", value = "工作年限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestlevel", value = "建议等级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wbsid", value = "外包商ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jl", value = "简历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mspy", value = "面试评语", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "score", value = "成绩", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "interviewresult", value = "面试结果 0：通过1：不通过", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestprice", value = "建议单价", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "reason", value = "修改理由", dataType = "string", paramType = "query"),
    })
    public ApiResult save(@RequestBody PpmInterviewerInfoDto ppmInterviewerInfo, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        PpmInterviewApply apply = new PpmInterviewApply();
        apply.setApplyer(user.getUserid());
        apply.setDeptno(user.getDeptId());
        if (null == ppmInterviewerInfo.getBps()) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setProcessInstId(ppmInterviewerInfo.getInstanceid());
            ppmInterviewerInfo.setBps(bps);
        } else {
            ppmInterviewerInfo.getBps().setUserId(user.getUserid());
        }
        ApiResult result = new ApiResult();
        result.success(ppmInterviewApplyService.daftPpmInterviewApply(apply, ppmInterviewerInfo));
        return result;
    }

    /**
     * 发起流程
     *
     * @param interviewerInfoDto 对象
     * @param request            request
     * @param bindResult         spring 调试类
     * @return ApiResult
     */
    @PostMapping("apply")
    @ApiOperation(value = "面试流程发起")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "veList", value = "面试官集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "eeList", value = "外包面试员工集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "processInstDesc", value = "流程描述,例如：发起申请 节点", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "申请人名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processDefName", value = "流程定义名称 (com.work.ppm.bps.glzxzdgz)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processInstName", value = "流程实例名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgid", value = "面试官工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgname", value = "面试官姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgdeptname", value = "面试官部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgdeptno", value = "面试官部门ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msname", value = "面试人姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "面试人性别 0:女1:男", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "idcard", value = "身份证", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workyears", value = "工作年限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestlevel", value = "建议等级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wbsid", value = "外包商ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jl", value = "简历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mspy", value = "面试评语", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "score", value = "成绩", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "interviewresult", value = "面试结果 0：通过1：不通过", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestprice", value = "建议单价", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "reason", value = "修改理由", dataType = "string", paramType = "query"),
    })
    public ApiResult apply(@RequestBody PpmInterviewerInfoDto interviewerInfoDto, HttpServletRequest request, BindingResult bindResult) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        PpmInterviewApply apply = new PpmInterviewApply();
        apply.setApplyer(user.getUserid());
        apply.setDeptno(user.getDeptId());
        if (null == interviewerInfoDto.getBps()) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(interviewerInfoDto.getInstanceid());
            interviewerInfoDto.setBps(bps);
        } else {
            interviewerInfoDto.getBps().setUserId(user.getUserid());
        }
        ApiResult result = new ApiResult();
        result.success(ppmInterviewApplyService.addPpmInterviewApply(apply, interviewerInfoDto));
        return result;
    }

    /**
     * 面试打分
     *
     * @param ppmInterviewerInfo 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("interviewScore")
    @ApiOperation(value = "面试打分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程实例编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workItemId", value = "流程工作编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applydesc", value = "审批意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "conclusion", value = "审批类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "ID,当更新时必须带上", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "idcard", value = "身份证ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgid", value = "面试官工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgname", value = "面试官姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "education", value = "学历评分", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "experience", value = "经验评分", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "technology", value = "技术评分", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "comprehensive", value = "综合评分", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "评语", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pid", value = "对应的PPM_INTERVIEWEE_INFO(申请人员信息表)的ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ppmInterviewerInfo", value = "dto", dataType = "string", paramType = "body",
                    required = true, defaultValue = "{\"bps\":{\"processInstId\":\"1238186\", " +
                    "\"activityInstId\":6890765, \"conclusion\":\"01\", \"applydesc\":\"结果录入\"}}"),
    })
    public ApiResult interviewScore(@RequestBody PpmInterviewerInfoDto ppmInterviewerInfo, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmInterviewerInfo.getBps()) {
            BpsWorkFlowDto bps = ppmInterviewerInfo.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            ppmInterviewerInfo.setBps(bps);
        }
        List<PpmIntervieweeScoreinfo> ivsList = ppmInterviewerInfo.getIvsList();
        ivsList.forEach(ppmIntervieweeScoreinfo -> ppmIntervieweeScoreinfo.setId(null));
        String msg = ppmInterviewApplyService.interviewScore(ppmInterviewerInfo.getBps(), ivsList);
        result.success(msg);
        return result;
    }

    /**
     * 结果录入
     *
     * @param ppmInterviewerInfo 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("resultEntry")
    @ApiOperation(value = "结果录入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程实例编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workItemId", value = "流程工作编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applydesc", value = "审批意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "conclusion", value = "审批类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msname", value = "面试人姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "面试人性别 0:女1:男", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "idcard", value = "身份证", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workyears", value = "工作年限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestlevel", value = "建议等级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wbsid", value = "外包商ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jl", value = "简历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mspy", value = "面试评语", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "score", value = "成绩", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "interviewresult", value = "面试结果 0：通过1：不通过", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestprice", value = "建议单价", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "reason", value = "修改理由", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ppmInterviewerInfo", value = "dto", dataType = "string", paramType = "body",
                    required = true, defaultValue = "{\"bps\":{\"processInstId\":\"1238186\", " +
                    "\"activityInstId\":6890765, \"conclusion\":\"01\", \"applydesc\":\"结果录入\"}}"),
    })
    public ApiResult resultEntry(@RequestBody PpmInterviewerInfoDto ppmInterviewerInfo, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmInterviewerInfo.getBps()) {
            BpsWorkFlowDto bps = ppmInterviewerInfo.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            ppmInterviewerInfo.setBps(bps);
        }
        String msg = ppmInterviewApplyService.resultEntry(ppmInterviewerInfo);
        result.success(msg);
        return result;
    }

    /**
     * 单价确认
     *
     * @param ppmInterviewerInfo 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("priceConfirm")
    @ApiOperation(value = "单价确认")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程实例编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workItemId", value = "流程工作编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applydesc", value = "审批意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "conclusion", value = "审批类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msname", value = "面试人姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "面试人性别 0:女1:男", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "idcard", value = "身份证", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workyears", value = "工作年限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestlevel", value = "建议等级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wbsid", value = "外包商ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jl", value = "简历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mspy", value = "面试评语", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "score", value = "成绩", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "interviewresult", value = "面试结果 0：通过1：不通过", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestprice", value = "建议单价", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "reason", value = "修改理由", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ppmInterviewerInfo", value = "dto", dataType = "string", paramType = "body",
                    defaultValue = "{\"bps\":{\"processInstId\":\"1238186\", \"activityInstId\":6890765, " +
                            "\"conclusion\":\"01\", \"applydesc\":\"结果录入\"}}"),
    })
    public ApiResult priceConfirm(@RequestBody PpmInterviewerInfoDto ppmInterviewerInfo, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmInterviewerInfo.getBps()) {
            BpsWorkFlowDto bps = ppmInterviewerInfo.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            ppmInterviewerInfo.setBps(bps);
        }
        String msg = ppmInterviewApplyService.priceConfirm(ppmInterviewerInfo);
        result.success(msg);
        return result;
    }

    /**
     * 一级部总经理室审批
     *
     * @param ppmInterviewerInfo 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("zhbConfirm")
    @ApiOperation(value = "研发管理部确认")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstId", value = "流程实例编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workItemId", value = "流程工作编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applydesc", value = "审批意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "conclusion", value = "审批类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msname", value = "面试人姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "面试人性别 0:女1:男", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "idcard", value = "身份证", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "workyears", value = "工作年限", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestlevel", value = "建议等级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wbsid", value = "外包商ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jl", value = "简历", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mspy", value = "面试评语", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "score", value = "成绩", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "interviewresult", value = "面试结果 0：通过1：不通过", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggestprice", value = "建议单价", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "reason", value = "修改理由", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ppmInterviewerInfo", value = "dto", dataType = "string", paramType = "body",
                    required = true, defaultValue = "{\"bps\":{\"processInstId\":\"1238186\", " +
                    "\"activityInstId\":6890765, \"conclusion\":\"01\", \"applydesc\":\"结果录入\"}}"),
    })
    public ApiResult zhbConfirm(@RequestBody PpmInterviewerInfoDto ppmInterviewerInfo, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        AssertUtil.notNull(ppmInterviewerInfo.getMeet(), "是否符合要求不能为空");
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmInterviewerInfo.getBps()) {
            BpsWorkFlowDto bps = ppmInterviewerInfo.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            ppmInterviewerInfo.setBps(bps);
        }
        String msg = ppmInterviewApplyService.zhbConfirm(ppmInterviewerInfo);
        result.success(msg);
        return result;
    }

    /**
     * excel 上传
     *
     * @param request request
     * @return ApiResult
     */
    @PostMapping("uploadExcel")
    @ApiOperation(value = "excel 上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectNo", value = "唯一编号(这里一般指流程id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "string", paramType = "query", defaultValue = "RLWBXQ"),
            @ApiImplicitParam(name = "catalogNo", value = "文档表编号", dataType = "string", paramType = "query",
                    defaultValue = "CATALOG2019043000001"),
    })
    public ApiResult uploadExcel(String objectNo, String type, String catalogNo,
                                 @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        ApiResult result = new ApiResult();
        super.setFile(file);
        super.setIp(IpUtil.getIpAddress(request));
        String imageId = super.uploadImage(objectNo, type, catalogNo, request);
        result.setResults(imageId);
        result.success("上传成功");
        return result;
    }

    /**
     * 下载文件
     *
     * @param docmentno 文档编号
     * @param request   request
     * @param response  response
     * @return ApiResult
     */
    @GetMapping("download/{docmentno}")
    @ApiOperation(value = "文档下载", notes = "面试文档下载")
    @ApiImplicitParam(name = "docmentno", value = "文档编号", dataType = "string", paramType = "path", defaultValue =
            "DOCUMENT202009236012")
    public ApiResult download(@PathVariable String docmentno, HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        ApiResult result = new ApiResult();
        this.setIp(IpUtil.getIpAddress(request));
        super.download(docmentno, response);
        result.success("下载成功");
        return null;
    }

    /**
     * 分页
     *
     * @param ppmInterviewApply 对象
     * @param pager             分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyer", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(PpmInterviewApplyDto ppmInterviewApply, Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<PpmInterviewApplyDto> list = ppmInterviewApplyService.queryPager(ppmInterviewApply, pager, user);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 面试详情
     *
     * @param ppmInterviewApply 对象
     * @return ApiResult<PpmInterviewApplyDto>
     */
    @GetMapping(value = "interviewScoreDateil")
    @ApiOperation(value = "面试详情", notes = "面试详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "applyer", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "string", paramType = "query"),

    })
    public ApiResult<PpmInterviewApplyDto> interviewScoreDateil(HttpServletRequest request, PpmInterviewApplyDto ppmInterviewApply) {
        ApiResult<PpmInterviewApplyDto> result = new ApiResult<>();
        AssertUtil.notNull(ppmInterviewApply.getInstanceid(), "[instanceid]不能为空");
        PpmInterviewApplyDto item = ppmInterviewApplyService.interviewScore(ppmInterviewApply);
        if (!ObjectUtils.isEmpty(item)) {
            CommOperatorDoLoginDto user = super.getCurrentUser(request);
            item.setCurrentUserId(user.getUserid());
        }
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     *
     * @param ppmInterviewApply 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyer", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> updatePpmInterviewApply(PpmInterviewApply ppmInterviewApply) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmInterviewApplyService.updatePpmInterviewApply(ppmInterviewApply);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     *
     * @param ppmInterviewApply 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deletePpmInterviewApply(PpmInterviewApply ppmInterviewApply) {
        ApiResult result = new ApiResult<>();
        int id = ppmInterviewApplyService.deletePpmInterviewApply(ppmInterviewApply);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 导出
     *
     * @param ppmInterviewApplyDto 对象
     * @param response             response
     * @param request              request
     */
    @PostMapping("exportExcel")
    @ApiOperation(value = "导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptno", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msname", value = "面试人名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "string", paramType = "query"),
    })
    public void exportExcel(PpmInterviewApplyDto ppmInterviewApplyDto, HttpServletRequest request,
                            HttpServletResponse response) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<PpmInterviewApplyDto> list = ppmInterviewApplyService.exportExcel(ppmInterviewApplyDto, user);
        ExcelUtil.exportExcel(list, PpmInterviewApplyDto.class, "jtk.xls", response);
        result.success("数据导出成功");
    }
}
