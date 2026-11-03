package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcUpdateDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcUpdateInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcUpdateInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcUpdateService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  信息变更申请信息表 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcUpdateApi"})
@RestController
@RequestMapping("ppmOutsourcUpdate")
@Slf4j
public class PpmOutsourcUpdateApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOutsourcUpdateService ppmOutsourcUpdateService;

    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOutsourcUpdateInfoService ppmOutsourcUpdateInfoService;
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 类接口描述
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 类接口描述
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    /**
     * 类接口描述
     */
    @Autowired
    private IAttatchManagementService attatchManagementService;

    /**
     * 查询详情 在岗人员 / 部门id
     * @param infoDto 对象
     * @return ApiResult<List<PpmOutsourcUpdateInfoDto> 对象
     */
    @GetMapping(value = "presenceInfo")
    @ApiOperation(value = "查询在岗人员", notes = "查询在岗人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "presenceState", value = "在岗状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "personInCharge", value = "项目经理", dataType = "string", paramType = "query")
    })
    public ApiResult<List<PpmOutsourcUpdateInfoDto>> presenceInfo(PpmOutsourcUpdateInfoDto infoDto) {
        ApiResult<List<PpmOutsourcUpdateInfoDto>> result =new ApiResult<>();
        if("sysadmin".equals(infoDto.getPersonInCharge())){
            infoDto.setPersonInCharge("");
        }
        List<PpmOutsourcUpdateInfoDto> items =
                ppmOutsourcUpdateInfoService.queryDetailExtList(infoDto, "getPersenceList");

        items.forEach(item ->{
            item.setCreditReports(BusinessUtil.getArrayString(item.getCreditReportAf(),BusinessUtil.REGEX_COMMA));
            item.setSsPaymentProves(BusinessUtil.getArrayString(item.getSsPaymentProveAf(),BusinessUtil.REGEX_COMMA));
            item.setConfidentCommitments(
                    BusinessUtil.getArrayString(item.getConfidentCommitmentAf(),BusinessUtil.REGEX_COMMA));
            item.setOtherFiles(BusinessUtil.getArrayString(item.getOtherFileAf(),BusinessUtil.REGEX_COMMA));
            item.setCeneralOfficeMotionAfLabel(BusinessUtil.getComboBoxLabel(
                    item.getCeneralOfficeMotionAfName(),item.getCeneralOfficeMotionAfNo()));
            item.setCeneralOfficeMotionBeLabel(BusinessUtil.getComboBoxLabel(
                    item.getCeneralOfficeMotionBeName(),item.getCeneralOfficeMotionBeNo()));
            item.setNewContractIdBeLabel(BusinessUtil.getComboBoxLabel(
                    item.getNewContractIdBeName(),item.getNewContractIdBeNo()));
            item.setNewContractIdAfLabel(BusinessUtil.getComboBoxLabel(
                    item.getNewContractIdAfName(),item.getNewContractIdAfNo()));
            item.setEvaluationDate(item.getEvaluationTime() != null ?
                    DateUtil.dateToIsoDateFormat(item.getEvaluationTime()) : "");
        });
        result.setResults(items);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 创建流程 草稿保存
     *
     * @param ppmOutsourcUpdateDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("createFlow")
    @ApiOperation(value = "草稿保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateInfoDtos", value = "外包人员申请信息集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptNo", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyer", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
    })
    public ApiResult createFlow(@RequestBody PpmOutsourcUpdateDto ppmOutsourcUpdateDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmOutsourcUpdateDto.setApplyer(user.getUserid());
        ppmOutsourcUpdateDto.setApplyerDept(user.getDeptId());
        if (null == ppmOutsourcUpdateDto.getBps()) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setProcessInstId(ppmOutsourcUpdateDto.getInstanceid());
            ppmOutsourcUpdateDto.setBps(bps);
        } else {
            ppmOutsourcUpdateDto.getBps().setUserId(user.getUserid());
        }

        ApiResult result = new ApiResult();
        result.success(ppmOutsourcUpdateService.managerCreated(ppmOutsourcUpdateDto));

        /*List<PpmOutsourcUpdateInfoDto> infoDtos = ppmOutsourcUpdateDto.getUpdateInfoDtos();
        if(infoDtos != null){
            infoDtos.forEach(infoDto -> {
                //总办议案修改时需要增减人数
                if(infoDto.getCeneralOfficeMotionBe() == null && infoDto.getCeneralOfficeMotionAf() != null){
                    attatchManagementService.calMotionNumberPlus(infoDto.getCeneralOfficeMotionAf());
                }else if(!infoDto.getCeneralOfficeMotionBe().equals(infoDto.getCeneralOfficeMotionAf())){
                    attatchManagementService.calMotionNumber(
                            infoDto.getCeneralOfficeMotionBe(),infoDto.getCeneralOfficeMotionAf());
                }
            });
        }*/
        return result;
    }

    /**
     * 发起流程
     *
     * @param ppmOutsourcUpdateDto 对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("initFlow")
    @ApiOperation(value = "信息变更申请流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateInfoDtos", value = "外包人员申请信息集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "applyer", value = "申请人编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyerName", value = "申请人名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyerDept", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyerDeptName", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
    })
    public ApiResult initFlow(@RequestBody PpmOutsourcUpdateDto ppmOutsourcUpdateDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmOutsourcUpdateDto.setApplyer(user.getUserid());
        ppmOutsourcUpdateDto.setApplyerDept(user.getDeptId());
        if (ppmOutsourcUpdateDto.getBps() == null) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(ppmOutsourcUpdateDto.getInstanceid());
            ppmOutsourcUpdateDto.setBps(bps);
        } else {
            ppmOutsourcUpdateDto.getBps().setUserId(user.getUserid());
        }
        ApiResult result = new ApiResult();
        result.success(ppmOutsourcUpdateService.managerInit(ppmOutsourcUpdateDto));

        return result;
    }

    /**
     * 处理任务时，查询详情的接口
     *
     * @param outsourcUpdateDto 部门编号
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryDetail")
    @ApiOperation(value = "处理任务时，查询详情的接口", notes = "处理任务时，查询详情的接口")
    public ApiResult<Object> queryDetail(PpmOutsourcUpdateDto outsourcUpdateDto) {
        //查询
        ApiResult<Object> result = new ApiResult();

        PpmOutsourcUpdateDto updateDto = ppmOutsourcUpdateService.queryDetail(outsourcUpdateDto);
        if(updateDto == null){
            result.setStatus(ApiCode.SUCCESS);
            return result;
        }
        CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(updateDto.getApplyer());
        updateDto.setApplyerName(commEmployee.getEmpname());

        String deptName = commDepartmentService.getDetpIdForDeptName(updateDto.getApplyerDept());
        String [] depts =  deptName.split("-");
        if(depts.length > 1){
            updateDto.setApplyerDeptName(depts[1]);
        }else {
            updateDto.setApplyerDeptName("");
        }

        List<PpmOutsourcUpdateInfoDto> infoDtos =
                ppmOutsourcUpdateInfoService.queryListForInstanceId(updateDto.getInstanceid());
        infoDtos.forEach(infoDto -> {
            infoDto.setCreditReports(infoDto.getCreditReportAf() == null? new String[]{} :
                    infoDto.getCreditReportAf().split(","));
            infoDto.setSsPaymentProves(infoDto.getSsPaymentProveAf() == null? new String[]{} :
                    infoDto.getSsPaymentProveAf().split(","));
            infoDto.setConfidentCommitments(infoDto.getConfidentCommitmentAf() == null? new String[]{} :
                    infoDto.getConfidentCommitmentAf().split(","));
            infoDto.setOtherFiles(infoDto.getOtherFileAf() == null? new String[]{} :
                    infoDto.getOtherFileAf().split(","));
            infoDto.setEvaluationDate(infoDto.getEvaluationTimeBe());
        });
        updateDto.setUpdateInfoDtos(infoDtos);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(updateDto);
        return result;
    }


    /**
     * 二级部经理审批 20260312 需求变更为总经理室审批
     *
     * @param ppmOutsourcUpdateDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("dhApproval")
    @ApiOperation(value = "二级部经理审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateInfoDtos", value = "外包人员申请信息集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "bps", value = "流程相关", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyerDept", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyerDeptName", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
    })
    public ApiResult dhApproval(@RequestBody PpmOutsourcUpdateDto ppmOutsourcUpdateDto, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmOutsourcUpdateDto.getBps()) {
            BpsWorkFlowDto bps = ppmOutsourcUpdateDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(ppmOutsourcUpdateDto.getInstanceid());
            ppmOutsourcUpdateDto.setBps(bps);
        }
        String msg = ppmOutsourcUpdateService.departmentHeadApproval(ppmOutsourcUpdateDto);
        result.success(msg);
        return result;
    }

    /**
     * 一级部总经理室审批
     *
     * @param ppmOutsourcUpdateDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("gmApproval")
    @ApiOperation(value = "一级部总经理室审批")
    public ApiResult gmApproval(@RequestBody PpmOutsourcUpdateDto ppmOutsourcUpdateDto,
                                HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmOutsourcUpdateDto.getBps()) {
            BpsWorkFlowDto bps = ppmOutsourcUpdateDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            ppmOutsourcUpdateDto.setBps(bps);
        }
        String msg = ppmOutsourcUpdateService.generalManagerApproval(ppmOutsourcUpdateDto);
        result.success(msg);
        return result;
    }

    /**
     * 删除
     * @param ppmOutsourcUpdateDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceid", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deletePpmOutsourcUpdate (@RequestBody PpmOutsourcUpdateDto ppmOutsourcUpdateDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcUpdateService.deletePpmOutsourcUpdate(ppmOutsourcUpdateDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
