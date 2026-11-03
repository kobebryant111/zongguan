package com.work.ppm.application.api.otherdeptmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyInfoDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyInfoExportDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardApplyInfoService;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardApplyService;
import com.work.ppm.application.services.othermanager.IPpmDocumentCatalogService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.ExcelUtil;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  监保部台账领用申请表 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmAccessCardApplyApi"})
@RestController
@RequestMapping("accessCardApply")
@Slf4j
public class PpmAccessCardApplyApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmAccessCardApplyService ppmAccessCardApplyService;

    @Autowired
    private IPpmDocumentCatalogService ppmDocumentCatalogService;
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmAccessCardApplyInfoService accessCardApplyInfoService;

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
     * 查询详情
     * @param ppmAccessCardApplyDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmAccessCardApplyDto> queryDetail(PpmAccessCardApplyDto ppmAccessCardApplyDto) {
        ApiResult<PpmAccessCardApplyDto> result =new ApiResult<>();
        PpmAccessCardApplyDto item = ppmAccessCardApplyService.queryDetail(ppmAccessCardApplyDto);
        if(item == null){
            result.setStatus(ApiCode.SUCCESS);
            return result;
        }

        CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(item.getApplyerId());
        item.setApplyerName(commEmployee.getEmpname());

        String deptName = commDepartmentService.getDetpIdForDeptName(item.getApplyerDeptId());
        String [] depts =  deptName.split("-");
        if(depts.length > 1){
            item.setApplyerDeptName(BusinessUtil.getDeptNameValue(depts[1],1));
        }else {
            item.setApplyerDeptName("");
        }

        List<PpmAccessCardApplyInfoDto> infoDtos =
                accessCardApplyInfoService.queryDetailList(PpmAccessCardApplyInfoDto.builder()
                        .instanceId(item.getInstanceId()).build(), "getListForInstanceId");
        infoDtos.forEach(infoDto ->{
            infoDto.setDeptName(BusinessUtil.getDeptNameValue(infoDto.getDeptName(),1));
        });

        item.setAccessCardApplyInfoList(infoDtos);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 发起流程
     *
     * @param accessCardApplyDto 对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("initFlow")
    @ApiOperation(value = "门禁卡申请流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applyInfoDtos", value = "外包人员申请信息集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "applyer", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptNo", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
    })
    public ApiResult initFlow(@RequestBody PpmAccessCardApplyDto accessCardApplyDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        accessCardApplyDto.setApplyerId(user.getUserid());
        accessCardApplyDto.setApplyerName(user.getOperatorname());
        accessCardApplyDto.setApplyerDeptId(user.getDeptId());
        if (accessCardApplyDto.getBps() == null) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(accessCardApplyDto.getInstanceId());
            accessCardApplyDto.setBps(bps);
        } else {
            accessCardApplyDto.getBps().setUserId(user.getUserid());
        }
        ApiResult result = new ApiResult();

        String type = accessCardApplyDto.getType();
        String logStr = "流程类型异常";
        if ("Receive".equals(type)){
            logStr = ppmAccessCardApplyService.flowInit(accessCardApplyDto);
        }else if ("Return".equals(type)){
            logStr = ppmAccessCardApplyService.returnFlowInit(accessCardApplyDto);
        }
        result.success(logStr);
        return result;
    }


    /**
     * 金融科技部一级部总经理室审批
     *
     * @param accessCardApplyDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("gmApproval")
    @ApiOperation(value = "金融科技部一级部总经理室审批")
    public ApiResult gmApproval(@RequestBody PpmAccessCardApplyDto accessCardApplyDto,
                                HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != accessCardApplyDto.getBps()) {
            BpsWorkFlowDto bps = accessCardApplyDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(accessCardApplyDto.getInstanceId());
            accessCardApplyDto.setBps(bps);
        }
        String msg = ppmAccessCardApplyService.flowGmApproval(accessCardApplyDto);
        result.success(msg);
        return result;
    }

    /**
     * 监保部单人审批
     *
     * @param accessCardApplyDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("monitorApproval")
    @ApiOperation(value = "监保部单人审批")
    public ApiResult monitorApproval(@RequestBody PpmAccessCardApplyDto accessCardApplyDto,HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != accessCardApplyDto.getBps()) {
            BpsWorkFlowDto bps = accessCardApplyDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(accessCardApplyDto.getInstanceId());
            accessCardApplyDto.setBps(bps);
        }
        String msg = ppmAccessCardApplyService.flowMonitorApproval(accessCardApplyDto);
        result.success(msg);
        return result;
    }

    /**
     * 监保部多人审批
     *
     * @param accessCardApplyDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("finishFlow")
    @ApiOperation(value = "监保部多人审批")
    public ApiResult finishFlow(@RequestBody PpmAccessCardApplyDto accessCardApplyDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps;
        if(null == accessCardApplyDto.getBps()) {
            bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(accessCardApplyDto.getInstanceId());
        } else {
            bps = accessCardApplyDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        }
        accessCardApplyDto.setBps(bps);
        ApiResult result = new ApiResult();
        result.success(ppmAccessCardApplyService.flowFinish(accessCardApplyDto));
        return result;
    }

    /**
     * 删除
     * @param accessCardApplyDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceId", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deletePpmAccessCardApply (PpmAccessCardApplyDto accessCardApplyDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmAccessCardApplyService.deletePpmAccessCardApply(accessCardApplyDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 导出信息接口
     * @param accessCardApplyDto 对象
     * @param request request
     * @param response response
     */
    @PostMapping("exportInfo")
    @ApiOperation(value = "监保部导出")
    public void exportExcel(@RequestBody PpmAccessCardApplyDto accessCardApplyDto, HttpServletRequest request, HttpServletResponse response) {
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        List<PpmAccessCardApplyInfoExportDto> list =
                ppmAccessCardApplyService.exportExcel(accessCardApplyDto,user);
        if(!list.isEmpty()){
            ExcelUtil.exportExcel(list, PpmAccessCardApplyInfoExportDto.class,"监保部台账导出.xls",response);
        }
        log.info("导出成功。");
    }

}
