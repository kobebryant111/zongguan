package com.work.ppm.application.api.suppliermanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRiskEvalDto;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRiskEvalService;
import com.work.ppm.commons.utils.BusinessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  风险评估表 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmSupplierRiskEvalApi"})
@RestController
@RequestMapping("supplierRiskEval")
@Slf4j
public class PpmSupplierRiskEvalApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmSupplierRiskEvalService ppmSupplierRiskEvalService;

    /**
     * 分页
     * @param ppmSupplierRiskEvalDto 对象
     * @param pager 分页对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assessYear", value = "评估年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pmValue", value = "项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "所属部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplierId", value = "所属部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessType", value = "评估类型", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto,Pager pager,HttpServletRequest request) {
        ApiResult<List> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmSupplierRiskEvalService.queryPager(ppmSupplierRiskEvalDto,pager,user);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 删除
     * @param ppmSupplierRiskEvalDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deletePpmSupplierRiskEval (@RequestBody PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmSupplierRiskEvalService.deletePpmSupplierRiskEval(ppmSupplierRiskEvalDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 发起流程
     *
     * @param ppmSupplierRiskEvalDto 对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("initFlow")
    @ApiOperation(value = "供应商考核人力类申请流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assessYear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplierId", value = "供应商编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessType", value = "考核类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessScores", value = "考核分数集合", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessScoreSum", value = "考核总分", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessLevel", value = "考核等级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pmType", value = "项目经理类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pmId", value = "项目经理编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectNumber", value = "项目编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "existQuestion", value = "存在问题", dataType = "string", paramType = "query")
    })
    public ApiResult initFlow(@RequestBody PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if(StringUtils.isEmpty(ppmSupplierRiskEvalDto.getPmId())){
            ppmSupplierRiskEvalDto.setPmId(user.getUserid());
        }

        if(StringUtils.isEmpty(ppmSupplierRiskEvalDto.getDeptId())){
            ppmSupplierRiskEvalDto.setDeptId(user.getDeptId());
        }

        if (ppmSupplierRiskEvalDto.getBps() == null) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(ppmSupplierRiskEvalDto.getInstanceid());
            ppmSupplierRiskEvalDto.setBps(bps);
        } else {
            ppmSupplierRiskEvalDto.getBps().setUserId(user.getUserid());
        }
        ApiResult result = new ApiResult();
        result.success(ppmSupplierRiskEvalService.managerInit(ppmSupplierRiskEvalDto));
        return result;
    }

    /**
     * 处理任务时，查询详情的接口
     *
     * @param ppmSupplierRiskEvalDto 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryDetail")
    @ApiOperation(value = "处理任务时，查询详情的接口", notes = "处理任务时，查询详情的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query")
    })
    public ApiResult<Object> queryDetail(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        //查询
        ApiResult<Object> result = new ApiResult();

        PpmSupplierRiskEvalDto supplierRiskEvalDto =
                ppmSupplierRiskEvalService.queryDetail(ppmSupplierRiskEvalDto,"queryBaseInfo");

        if(supplierRiskEvalDto == null ){
            result.setStatus(ApiCode.FAIL);
            result.setResults(null);
            return result;
        }

        supplierRiskEvalDto.setAssessScoreMap(BusinessUtil.stringRegexMap(
                supplierRiskEvalDto.getAssessScores(),
                new String[]{BusinessUtil.REGEX_LINE_UN,BusinessUtil.REGEX_COLON}));
        supplierRiskEvalDto.setAffectBusiTypeArray(BusinessUtil.getArrayString(
                supplierRiskEvalDto.getAffectBusiType(),BusinessUtil.REGEX_LINE_UN));

        result.setStatus(ApiCode.SUCCESS);
        result.setResults(supplierRiskEvalDto);
        return result;
    }

    /**
     * 二级部经理审批
     *
     * @param ppmSupplierRiskEvalDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("dhApproval")
    @ApiOperation(value = "二级部经理审批")
    public ApiResult dhApproval(@RequestBody PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto,HttpServletRequest request) {

        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmSupplierRiskEvalDto.getBps()) {
            BpsWorkFlowDto bps = ppmSupplierRiskEvalDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(ppmSupplierRiskEvalDto.getInstanceid());
            ppmSupplierRiskEvalDto.setBps(bps);
        }
        String msg = ppmSupplierRiskEvalService.departmentHeadApproval(ppmSupplierRiskEvalDto);
        result.success(msg);
        return result;
    }

    /**
     * 一级部总经理室审批
     *
     * @param ppmSupplierRiskEvalDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("gmApproval")
    @ApiOperation(value = "一级部总经理室审批")
    public ApiResult gmApproval(@RequestBody PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto,HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmSupplierRiskEvalDto.getBps()) {
            BpsWorkFlowDto bps = ppmSupplierRiskEvalDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            ppmSupplierRiskEvalDto.setBps(bps);
        }
        String msg = ppmSupplierRiskEvalService.generalManagerApproval(ppmSupplierRiskEvalDto);
        result.success(msg);
        return result;
    }

    /**
     * 导出风险评估记录表
     * @param ppmSupplierRiskEvalDto 对象
     * @param response 问题
     * @throws Exception 异常
     */
    @GetMapping(value = "/download",name = "导出风险评估记录表")
    @ApiOperation(value = "导出风险评估记录表", notes = "导出风险评估记录表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "string", paramType = "query")
    })
    public void downloadSupplierRiskEval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto,
                                       HttpServletResponse response) throws Exception {
        ppmSupplierRiskEvalService.exportPdf(ppmSupplierRiskEvalDto,response);
        log.info("导出成功。");
    }

}
