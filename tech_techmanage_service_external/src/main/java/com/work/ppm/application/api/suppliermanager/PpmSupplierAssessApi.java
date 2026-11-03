package com.work.ppm.application.api.suppliermanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierAssessDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierAssessService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRelationService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  供应商考核信息表 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmSupplierAssessApi"})
@RestController
@RequestMapping("supplierAssess")
@Slf4j
public class PpmSupplierAssessApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmSupplierAssessService ppmSupplierAssessService;
    @Autowired
    private ICommEmployeeService commEmployeeService;
    @Autowired
    private ICommDepartmentService commDepartmentService;
    @Autowired
    private IPpmSupplierRelationService ppmSupplierRelationService;

    /**
     * 分页
     * @param ppmSupplierAssessDto 对象
     * @param pager 分页对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmValue", value = "项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessYear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplierId", value = "供应商", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessLevel", value = "考核等级", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "流程状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectNumber", value = "项目编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessType", value = "考核类型", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmSupplierAssessDto ppmSupplierAssessDto,Pager pager,HttpServletRequest request) {
        ApiResult<List> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmSupplierAssessDto.setState("-1");
        ppmSupplierAssessService.queryPager(ppmSupplierAssessDto,pager,user);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 删除
     * @param ppmSupplierAssessDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deletePpmSupplierAssess (@RequestBody PpmSupplierAssessDto ppmSupplierAssessDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmSupplierAssessService.deletePpmSupplierAssess(ppmSupplierAssessDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmSupplierAssessDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteByVal")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deleteByVal (PpmSupplierAssessDto ppmSupplierAssessDto) {
        ApiResult result =new ApiResult<>();

        List<PpmSupplierAssessDto> list = ppmSupplierAssessService.queryDetailList(ppmSupplierAssessDto);
        list.forEach(obj ->{
            ppmSupplierAssessService.deletePpmSupplierAssess(obj);
        });

        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list.size());
        return result;
    }

    /**
     * 发起流程
     *
     * @param ppmSupplierAssessDto 对象
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
    public ApiResult initFlow(@RequestBody PpmSupplierAssessDto ppmSupplierAssessDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if(StringUtils.isEmpty(ppmSupplierAssessDto.getPmId())){
            ppmSupplierAssessDto.setPmId(user.getUserid());
        }

        if(StringUtils.isEmpty(ppmSupplierAssessDto.getDeptId())){
            ppmSupplierAssessDto.setDeptId(user.getDeptId());
        }

        if (ppmSupplierAssessDto.getBps() == null) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(ppmSupplierAssessDto.getInstanceid());
            ppmSupplierAssessDto.setBps(bps);
        } else {
            ppmSupplierAssessDto.getBps().setUserId(user.getUserid());
        }
        ApiResult result = new ApiResult();
        result.success(ppmSupplierAssessService.managerInit(ppmSupplierAssessDto));
        return result;
    }

    /**
     * 处理任务时，查询详情的接口
     *
     * @param ppmSupplierAssessDto 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryDetail")
    @ApiOperation(value = "处理任务时，查询详情的接口", notes = "处理任务时，查询详情的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query")
    })
    public ApiResult<Object> queryDetail(PpmSupplierAssessDto ppmSupplierAssessDto) {
        //查询
        ApiResult<Object> result = new ApiResult();

        PpmSupplierAssessDto supplierAssessDto =
                ppmSupplierAssessService.queryDetail(ppmSupplierAssessDto,"queryBaseInfo");

        if(supplierAssessDto == null ){
            result.setStatus(ApiCode.FAIL);
            result.setResults(null);
            return result;
        }

        if(StringUtils.isNotEmpty(supplierAssessDto.getDeptName())){
           String [] depts =  supplierAssessDto.getDeptName().split("-");
           if(depts.length > 1){
               supplierAssessDto.setDeptName(depts[1]);
           }
        }

        supplierAssessDto.setAssessScoreMap(BusinessUtil.stringRegexMap(
                supplierAssessDto.getAssessScores(),new String[]{BusinessUtil.REGEX_LINE_UN,BusinessUtil.REGEX_COLON}));

        result.setStatus(ApiCode.SUCCESS);
        result.setResults(supplierAssessDto);
        return result;
    }

    /**
     * 二级部经理审批
     *
     * @param ppmSupplierAssessDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("dhApproval")
    @ApiOperation(value = "二级部经理审批")
    public ApiResult dhApproval(@RequestBody PpmSupplierAssessDto ppmSupplierAssessDto,HttpServletRequest request) {

        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmSupplierAssessDto.getBps()) {
            BpsWorkFlowDto bps = ppmSupplierAssessDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(ppmSupplierAssessDto.getInstanceid());
            ppmSupplierAssessDto.setBps(bps);
        }
        String msg = ppmSupplierAssessService.departmentHeadApproval(ppmSupplierAssessDto);
        result.success(msg);
        return result;
    }

    /**
     * 一级部总经理室审批
     *
     * @param ppmSupplierAssessDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("gmApproval")
    @ApiOperation(value = "一级部总经理室审批")
    public ApiResult gmApproval(@RequestBody PpmSupplierAssessDto ppmSupplierAssessDto,HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmSupplierAssessDto.getBps()) {
            BpsWorkFlowDto bps = ppmSupplierAssessDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            ppmSupplierAssessDto.setBps(bps);
        }
        String msg = ppmSupplierAssessService.generalManagerApproval(ppmSupplierAssessDto);
        result.success(msg);
        return result;
    }

    /**
     * 导出供应商考核
     * @param ppmSupplierAssessDto 对象
     * @param response response
     * @throws Exception 异常
     */
    @GetMapping(value = "/download",name = "导出供应商考核")
    @ApiOperation(value = "导出供应商考核", notes = "导出供应商考核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "string", paramType = "query")
    })
    public void downloadSupplierAssess(PpmSupplierAssessDto ppmSupplierAssessDto,
                                       HttpServletResponse response) throws Exception {
        ppmSupplierAssessService.exportPdf(ppmSupplierAssessDto,response);
    }

}
