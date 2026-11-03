package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmStaffTransferDto;
import com.work.ppm.application.dto.integratedmanager.PpmStaffTransferInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.PpmProjectInfoDto;
import com.work.ppm.application.dto.systemmanager.PpmSystemInfoDto;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.PpmSystemInfo;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmStaffTransferInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmStaffTransferService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IPpmProjectInfoService;
import com.work.ppm.application.services.systemmanager.IPpmSystemInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.utils.BusinessUtil;
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
 *  人员移交信息 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmStaffTransferApi"})
@RestController
@RequestMapping("staffTransfer")
@Slf4j
public class PpmStaffTransferApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmStaffTransferService ppmStaffTransferService;

    @Autowired
    private IPpmStaffTransferInfoService ppmStaffTransferInfoService;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    @Autowired
    private ICommDepartmentService commDepartmentService;

    @Autowired
    private ICommEmployeeService commEmployeeService;

    @Autowired
    private IPpmSystemInfoService ppmSystemInfoService;

    @Autowired
    private IPpmProjectInfoService ppmProjectInfoService;

    /*
     * 分页
     * @param ppmStaffTransferDto 对象
     * @param pager 分页对象
     * @return ApiResult

    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmStaffTransferDto ppmStaffTransferDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmStaffTransferService.queryPager(ppmStaffTransferDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }*/

    /**
     * 查询可选人员 查询项目经理名下的人员
     * @param ppmOutsourcingBasicInfo 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "initStaff")
    @ApiOperation(value = "查询可选人员", notes = "查询可选人员")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "personInCharge", value = "项目经理编号", dataType = "string", paramType = "query")
    })
    public ApiResult<List<PpmOutsourcingBasicInfo>> initStaff(PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo) {
        ApiResult<List<PpmOutsourcingBasicInfo>> result =new ApiResult<>();
        List<PpmOutsourcingBasicInfo> items = ppmOutsourcingBasicService.queryHumanList(ppmOutsourcingBasicInfo);
        items.forEach(item -> item.setDepname(BusinessUtil.getDeptNameValue(item.getDepname(),1)));
        result.setResults(items);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询可选人员 查询项目经理名下的人员
     * @param ppmSystemInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "initSystemInfo")
    @ApiOperation(value = "查询系统信息", notes = "查询系统信息")
    public ApiResult<List<PpmSystemInfo>> initSystemInfo(PpmSystemInfoDto ppmSystemInfoDto) {
        ApiResult<List<PpmSystemInfo>> result =new ApiResult<>();
        List<PpmSystemInfo> items = ppmSystemInfoService.queryTransferList(ppmSystemInfoDto);
        result.setResults(items);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询可选人员 查询项目经理名下的人员
     * @param ppmProjectInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "initProjectInfo")
    @ApiOperation(value = "查询项目信息", notes = "查询项目信息")
    public ApiResult<List<PpmProjectInfoDto>> initProjectInfo(PpmProjectInfoDto ppmProjectInfoDto) {
        ApiResult<List<PpmProjectInfoDto>> result =new ApiResult<>();
        result.setResults(ppmProjectInfoService.queryTransferList(ppmProjectInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /*
     * 创建流程 草稿保存
     *
     * @param staffTransferDto 对象
     * @param request            request
     * @return ApiResult

    @PostMapping("createFlow")
    @ApiOperation(value = "草稿保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applyInfoList", value = "外包人员申请信息集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyerDeptId", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyerId", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
    })
    public ApiResult createFlow(@RequestBody PpmStaffTransferDto staffTransferDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        if (null == staffTransferDto.getBps()) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setProcessInstId(staffTransferDto.getInstanceid());
            staffTransferDto.setBps(bps);
        } else {
            staffTransferDto.getBps().setUserId(user.getUserid());
        }
        ApiResult result = new ApiResult();
        result.success(ppmStaffTransferService.managerCreated(staffTransferDto));
        return result;
    }*/

    /**
     * 发起流程
     *
     * @param staffTransferDto 对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("initFlow")
    @ApiOperation(value = "人员移交申请流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applyInfoDtos", value = "外包人员申请信息集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "applyer", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptNo", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
    })
    public ApiResult initFlow(@RequestBody PpmStaffTransferDto staffTransferDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        staffTransferDto.setApplyerId(user.getUserid());
        staffTransferDto.setApplyerDeptId(user.getDeptId());
        if (staffTransferDto.getBps() == null) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(staffTransferDto.getInstanceid());
            staffTransferDto.setBps(bps);
        } else {
            staffTransferDto.getBps().setUserId(user.getUserid());
        }
        ApiResult result = new ApiResult();
        result.success(ppmStaffTransferService.managerInit(staffTransferDto));
        return result;
    }

    /**
     * 处理任务时，查询详情的接口
     *
     * @param ppmStaffTransferDto 部门编号
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryDetail")
    @ApiOperation(value = "处理任务时，查询详情的接口", notes = "处理任务时，查询详情的接口")
    public ApiResult<Object> queryDetail(PpmStaffTransferDto ppmStaffTransferDto) {
        //查询
        ApiResult<Object> result = new ApiResult();

        PpmStaffTransferDto staffTransferDto = ppmStaffTransferService.queryDetail(ppmStaffTransferDto);

        if(staffTransferDto == null ){
            result.setStatus(ApiCode.FAIL);
            result.setResults(null);
            return result;
        }
        CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(staffTransferDto.getApplyerId());
        staffTransferDto.setApplyerName(commEmployee.getEmpname());

        String deptName = commDepartmentService.getDetpIdForDeptName(staffTransferDto.getApplyerDeptId());
        staffTransferDto.setApplyerDeptName(BusinessUtil.getDeptNameValue(deptName,1));

        List<PpmStaffTransferInfoDto> transferInfoDtos =
                ppmStaffTransferInfoService.queryListForInstanceId(staffTransferDto.getInstanceid());

        transferInfoDtos.forEach(infoDto ->{
            infoDto.setApplyerDeptName(BusinessUtil.getDeptNameValue(infoDto.getApplyerDeptName(),1));
            infoDto.setDeptIdBeforeName(BusinessUtil.getDeptNameValue(infoDto.getDeptIdBeforeName(),1));
            infoDto.setDeptIdAfterName(BusinessUtil.getDeptNameValue(infoDto.getDeptIdAfterName(),1));
        });
        staffTransferDto.setTransferInfoDtos(transferInfoDtos);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(staffTransferDto);
        return result;
    }


    /**
     * 二级部经理审批
     *
     * @param staffTransferDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("dhApproval")
    @ApiOperation(value = "二级部经理审批")
    public ApiResult dhApproval(@RequestBody PpmStaffTransferDto staffTransferDto,
                                HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != staffTransferDto.getBps()) {
            BpsWorkFlowDto bps = staffTransferDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(staffTransferDto.getInstanceid());
            staffTransferDto.setBps(bps);
        }
        String msg = ppmStaffTransferService.deptManagerApproval(staffTransferDto);
        result.success(msg);
        return result;
    }

    /**
     * 移交后二级部经理审批
     *
     * @param staffTransferDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("dhApprovalTransfer")
    @ApiOperation(value = "移交后二级部经理审批")
    public ApiResult dhApprovalTransfer(@RequestBody PpmStaffTransferDto staffTransferDto,HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != staffTransferDto.getBps()) {
            BpsWorkFlowDto bps = staffTransferDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(staffTransferDto.getInstanceid());
            staffTransferDto.setBps(bps);
        }
        String msg = ppmStaffTransferService.deptManagerApprovalTransfer(staffTransferDto);
        result.success(msg);
        return result;
    }

    /**
     * 移交后项目经理审批
     *
     * @param staffTransferDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("pmApprovalTransfer")
    @ApiOperation(value = "移交后项目经理审批")
    public ApiResult pmApprovalTransfer(@RequestBody PpmStaffTransferDto staffTransferDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null == staffTransferDto.getBps()) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(staffTransferDto.getInstanceid());
            staffTransferDto.setBps(bps);
        } else {
            BpsWorkFlowDto bps = staffTransferDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            staffTransferDto.setBps(bps);
        }
        ApiResult result = new ApiResult();
        result.success(ppmStaffTransferService.managerApprovalTransfer(staffTransferDto));
        return result;
    }

    /**
     * 删除
     * @param ppmStaffTransferDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deletePpmStaffTransfer (@RequestBody PpmStaffTransferDto ppmStaffTransferDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmStaffTransferService.deletePpmStaffTransfer(ppmStaffTransferDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
