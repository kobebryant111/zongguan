package com.work.ppm.application.api.integratedmanager;

import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmEarlyCourseApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmEarlyCourseApplyInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.integratedmanager.IPpmEarlyCourseApplyInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmEarlyCourseApplyService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.StringUtil;
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
import java.util.Date;
import java.util.List;

/**
 * 提前入场申请
 *
 */
@Slf4j
@RestController
@RequestMapping("ppmEarlyCourseApply")
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmEarlyCourseApplyApi"})
public class PpmEarlyCourseApplyApi extends BaseUploadApi {
    /**
     * 提前入场申请
     */
    @Autowired
    private IPpmEarlyCourseApplyService ppmEarlyCourseApplyService;
    /**
     * 提前入场申请详情
     */
    @Autowired
    private IPpmEarlyCourseApplyInfoService ppmEarlyCourseApplyInfoService;

    @Autowired
    private ICommDepartmentService commDepartmentService;

    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 分页
     *
     * @param courseApplyDto 对象
     * @param pager             分页对象
     * @param request   request
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptNo", value = "部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wbsId", value = "供应商ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isComplement", value = "是否补全入场材料", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "campaignTimeVal", value = "催办时间", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List<PpmEarlyCourseApplyInfoDto>> queryPager(PpmEarlyCourseApplyInfoDto courseApplyDto,
                                                                  Pager pager, HttpServletRequest request) {
        ApiResult<List<PpmEarlyCourseApplyInfoDto>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<PpmEarlyCourseApplyInfoDto> list = ppmEarlyCourseApplyInfoService.queryPager(courseApplyDto, pager, user);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 分页
     *
     * @param courseApplyDto 对象
     * @param pager             分页对象
     * @param request   request
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPmPager")
    @ApiOperation(value = "任务分页查询", notes = "任务分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wbsId", value = "供应商ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isComplement", value = "是否补全入场材料", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List<PpmEarlyCourseApplyInfoDto>> queryPmPager(PpmEarlyCourseApplyInfoDto courseApplyDto,
                                                                  Pager pager, HttpServletRequest request) {
        ApiResult<List<PpmEarlyCourseApplyInfoDto>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<PpmEarlyCourseApplyInfoDto> list =
                ppmEarlyCourseApplyInfoService.queryPmPager(courseApplyDto, pager, user);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询当前登录人和对应部门
     *
     * @param request request
     * @return ApiResult<List>
     */
    @GetMapping(value = "queryUserDept")
    @ApiOperation(value = "查询当前登录人和对应部门", notes = "查询当前登录人和对应部门")
    public ApiResult<Object> queryUserDept(HttpServletRequest request) {
        //查询
        ApiResult<Object> result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        JSONObject json = new JSONObject();
        json.put("userId",user.getUserid());
        json.put("userName",user.getOperatorname());
        String deptNo = commEmployeeService.getDeptNo(user.getUserid());
        json.put("deptNo",deptNo);
        String deptName = commDepartmentService.getDetpIdForDeptName(deptNo);
        if(StringUtil.isNotEmpty(deptName)){
           String [] depts =  deptName.split("-");
            if(depts.length > 1){
                json.put("deptName",depts[1]);
            }
        }else {
            json.put("deptName","");
        }

        if(StringUtils.isNotEmpty(deptNo)){
            CommEmployeeDto commEmployee = commEmployeeService.getTwoDeptWorkManager(deptNo);
            json.put("leaderPmId",commEmployee.getBadge());
            json.put("leaderPmName",commEmployee.getEmpname());
        }

        result.setStatus(ApiCode.SUCCESS);
        result.setResults(json);
        return result;
    }

    /**
     * 面试通过 准入未完成查询
     *
     * @param deptNo 部门编号
     * @return ApiResult<List>
     */
    @GetMapping(value = "interView")
    @ApiOperation(value = "面试通过 准入未完成查询", notes = "面试通过 准入未完成查询")
    public ApiResult<Object> interView(String deptNo) {
        //查询
        ApiResult<Object> result = new ApiResult();

        result.setStatus(ApiCode.SUCCESS);

        result.setResults(ppmEarlyCourseApplyInfoService.interViews(deptNo));
        return result;
    }

    /**
     * 发起流程
     *
     * @param courseApplyDto 对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("initFlow")
    @ApiOperation(value = "提前入场申请流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applyInfoDtos", value = "外包人员申请信息集合", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "applyer", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptNo", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
    })
    public ApiResult initFlow(@RequestBody PpmEarlyCourseApplyDto courseApplyDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        courseApplyDto.setApplyer(user.getUserid());
        courseApplyDto.setDeptNo(user.getDeptId());
        if (courseApplyDto.getBps() == null) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(courseApplyDto.getInstanceid());
            courseApplyDto.setBps(bps);
        } else {
            courseApplyDto.getBps().setUserId(user.getUserid());
        }
        ApiResult result = new ApiResult();
        result.success(ppmEarlyCourseApplyService.managerInit(courseApplyDto));
        return result;
    }

    /**
     * 处理任务时，查询详情的接口
     *
     * @param ppmEarlyCourseApplyDto 部门编号
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryDetail")
    @ApiOperation(value = "处理任务时，查询详情的接口", notes = "处理任务时，查询详情的接口")
    public ApiResult<Object> queryDetail(PpmEarlyCourseApplyDto ppmEarlyCourseApplyDto) {
        //查询
        ApiResult<Object> result = new ApiResult();

        PpmEarlyCourseApplyDto courseApplyDto = ppmEarlyCourseApplyService.queryDetail(ppmEarlyCourseApplyDto);

        if(courseApplyDto == null ){
            result.setStatus(ApiCode.FAIL);
            result.setResults(null);
            return result;
        }
        CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(courseApplyDto.getApplyer());
        courseApplyDto.setApplyerName(commEmployee.getEmpname());

        String deptName = commDepartmentService.getDetpIdForDeptName(courseApplyDto.getDeptNo());
        String [] depts =  deptName.split("-");
        if(depts.length > 1){
            courseApplyDto.setDeptName(depts[1]);
        }else {
            courseApplyDto.setDeptName("");
        }

        courseApplyDto.setApplyInfoDtos(
                ppmEarlyCourseApplyInfoService.queryListForInstanceId(ppmEarlyCourseApplyDto.getInstanceid()));
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(courseApplyDto);
        return result;
    }


    /**
     * 二级部经理审批
     *
     * @param ppmEarlyCourseApplyDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("dhApproval")
    @ApiOperation(value = "二级部经理审批")
    public ApiResult dhApproval(@RequestBody PpmEarlyCourseApplyDto ppmEarlyCourseApplyDto,
                                HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmEarlyCourseApplyDto.getBps()) {
            BpsWorkFlowDto bps = ppmEarlyCourseApplyDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            bps.setProcessInstId(ppmEarlyCourseApplyDto.getInstanceid());
            ppmEarlyCourseApplyDto.setBps(bps);
        }
        String msg = ppmEarlyCourseApplyService.departmentHeadApproval(ppmEarlyCourseApplyDto);
        result.success(msg);
        return result;
    }

    /**
     * 一级部总经理室审批
     *
     * @param ppmEarlyCourseApplyDto 对象
     * @param request            request
     * @return ApiResult
     */
    @PostMapping("gmApproval")
    @ApiOperation(value = "一级部总经理室审批")
    public ApiResult gmApproval(@RequestBody PpmEarlyCourseApplyDto ppmEarlyCourseApplyDto,
                                HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (null != ppmEarlyCourseApplyDto.getBps()) {
            BpsWorkFlowDto bps = ppmEarlyCourseApplyDto.getBps();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
        } else {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId(user.getUserid());
            bps.setUserName(user.getOperatorname());
            ppmEarlyCourseApplyDto.setBps(bps);
        }
        String msg = ppmEarlyCourseApplyService.generalManagerApproval(ppmEarlyCourseApplyDto);
        result.success(msg);
        return result;
    }

    /**
     * 删除
     *
     * @param ppmEarlyCourseApply 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deletePpmEarlyCourseApply(@RequestBody PpmEarlyCourseApplyDto ppmEarlyCourseApply) {
        ApiResult result = new ApiResult<>();
        int id = ppmEarlyCourseApplyService.deletePpmEarlyCourseApply(ppmEarlyCourseApply);
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
     * @param ppmEarlyCourseApplyInfoDto 对象
     * @param response             response
     * @param request              request
     */
    @PostMapping("exportExcel")
    @ApiOperation(value = "导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptNo", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wbsId", value = "外包商编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applyerName", value = "申请人名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query")
    })
    public void exportExcel(PpmEarlyCourseApplyInfoDto ppmEarlyCourseApplyInfoDto, HttpServletRequest request,
                            HttpServletResponse response) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        List<PpmEarlyCourseApplyInfoDto> list =
                ppmEarlyCourseApplyInfoService.exportExcel(ppmEarlyCourseApplyInfoDto, user);
        ExcelUtil.exportExcel(list, PpmEarlyCourseApplyInfoDto.class, "jtk.xls", response);
        result.success("数据导出成功");
    }

    /**
     * 修改是否补全材料
     *
     * @param list 对象
     * @param request              request
     * @return ApiResult
     */
    @PostMapping("updateInfo")
    @ApiOperation(value = "修改是否补全材料")
    public ApiResult updateInfo(@RequestBody List<PpmEarlyCourseApplyInfoDto> list, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if(user != null){
            list.forEach(obj ->{
                PpmEarlyCourseApplyInfoDto applyInfoDto = new PpmEarlyCourseApplyInfoDto();
                applyInfoDto.setModifiedBy(user.getUserid());
                applyInfoDto.setModifiedTime(new Date());
                applyInfoDto.setId(obj.getId());
                applyInfoDto.setIsComplement(obj.getIsComplement());
                ppmEarlyCourseApplyInfoService.updatePpmEarlyCourseApplyInfo(obj);
            });
        }

        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
