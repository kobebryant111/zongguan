package com.work.ppm.application.api.rdmanager;

import com.work.ppm.application.dto.integratedmanager.PpmAttendanceInfoExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmRdHrProjectInfoExportDto;
import com.work.ppm.application.model.commonmanager.CommRdDepartment;
import com.work.ppm.application.model.rdmanager.*;
import com.work.ppm.application.services.rdmanager.IHrNewRequiresService;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.application.dto.bankmamager.PpmEmpKqInfoDto;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmRdEmployeeInfoExportDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.services.rdmanager.IHrResourcesService;
import com.work.ppm.application.services.rdmanager.IRdProjectService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 研发项目人员
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"HrResources"}, description = "研发人力资源管理模块")
@RestController
@RequestMapping("hrResources")
@Slf4j
@Validated
public class HrResources extends BaseApi{

    @Autowired
    private IHrResourcesService hrResourcesService;

    @Autowired
    private IRdProjectService rdProjectService;

    @Autowired
    private IHrNewRequiresService hrNewRequiresService;

    /**
     * 分页
     *
     * @param commRdEmployeeDto 对象
     * @param pager             分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "department", value = "研发部门", dataType = "integer", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })

    public ApiResult<List> queryPager(@RequestBody CommRdEmployeeDto commRdEmployeeDto, @RequestBody Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        hrResourcesService.queryPager(commRdEmployeeDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询
     *
     * @param rdQueryEmployee 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryList")
    @ApiOperation(value = "查询", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "department", value = "研发部门", dataType = "integer", paramType = "query"),
    })

    public ApiResult<List> queryList(@RequestBody RdQueryEmployee rdQueryEmployee, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<CommRdEmployeeDto> commRdEmployeeDtos = hrResourcesService.queryList(rdQueryEmployee,user);
        result.setResults(commRdEmployeeDtos);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询
     *
     * @param commRdEmployeeDto 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryAdministrationList")
    @ApiOperation(value = "查询", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "department", value = "研发部门", dataType = "integer", paramType = "query"),
    })

    public ApiResult<List> queryAdministrationList(@RequestBody CommRdEmployeeDto commRdEmployeeDto) {
        ApiResult<List> result = new ApiResult<>();
        List<CommRdEmployeeDto> commRdEmployeeDtos = hrResourcesService.queryAdministrationList(commRdEmployeeDto);
        result.setResults(commRdEmployeeDtos);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "queryPagerInfo")
    @ApiOperation(value = "研发人员表导出", notes = "研发人员表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "department", value = "研发部门", dataType = "integer", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })

    public void queryPagerInfo(@RequestBody CommRdEmployeeDto commRdEmployeeDto, HttpServletResponse response) {
        ApiResult<List> result = new ApiResult<>();
        List<PpmRdEmployeeInfoExportDto> CommRdEmployees = hrResourcesService.queryPagerInfo(commRdEmployeeDto);
        if (!CommRdEmployees.isEmpty()) {
            ExcelUtil.exportExcel(CommRdEmployees, PpmRdEmployeeInfoExportDto.class, "研发人员表导出.xls", response);
        }
        log.info("导出成功。");
    }


    @PostMapping(value = "emqueryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depgrade", value = "研发级别", dataType = "integer", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> emqueryPager(@RequestBody CommEmployeeDto commEmployeeDto, @RequestBody Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        hrResourcesService.emqueryPager(commEmployeeDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    @PostMapping(value = "add")
    @ApiOperation(value = "添加", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "reportto", value = "上级领导", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "删除标识", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "empauth", value = "员工编制", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "authproperty", value = "编制属性", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fullname", value = "字段名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "jobid", value = "所属岗位代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "eid", value = "员工代码HR", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "所属机构代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "empstatus", value = "员工状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "boardline", value = "所属条线", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "spellname", value = "字段名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "chargeName", value = "所属部门", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "certificateType", value = "证件类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "certificateNumber", value = "证件号码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adminid", value = "部门父编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "depgrade", value = "部门级别", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "arrivalTime", value = "到岗日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "leaveTime", value = "离岗日期", dataType = "String", paramType = "query"),

    })
    public ApiResult<List> add(@RequestBody List<CommRdEmployeeDto> selectedData) {
        ApiResult<List> result = new ApiResult<>();
        hrResourcesService.add(selectedData);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depgrade", value = "研发级别", dataType = "integer", paramType = "query"),

    })
    public ApiResult<List> delete(@RequestBody CommRdEmployeeDto commRdEmployeeDto) {
        ApiResult<List> result = new ApiResult<>();
        Integer delete = hrResourcesService.delete(commRdEmployeeDto);
        if (delete == null || delete == 0) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "该员工已有考勤记录或不存在");
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "edit")
    @ApiOperation(value = "编辑", notes = "编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "empstatus", value = "人员状态", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "chargeName", value = "部门", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nature", value = "性质", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "arrivalTime", value = "到岗日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "leaveTime", value = "离岗日期", dataType = "String", paramType = "query"),
    })
    public ApiResult<List> edit(@RequestBody CommRdEmployeeDto commRdEmployeeDto) {
        ApiResult result = new ApiResult<>();
        hrResourcesService.edit(commRdEmployeeDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "queryDeptTree")
    @ApiOperation(value = "查询部门组织树", notes = "查询部门组织树")
    @ApiImplicitParams({
    })
    public ApiResult<CommTreeDto> queryDeptTree(@RequestBody CommEmployeeDto commEmployeeDto) {
        ApiResult result = new ApiResult<>();
        List<CommTreeDto> list = hrResourcesService.queryDeptTree(commEmployeeDto);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }

    @PostMapping(value = "queryDepartment")
    @ApiOperation(value = "查询部门组织树", notes = "查询部门组织树")
    @ApiImplicitParams({
    })
    public ApiResult<CommTreeDto> queryDepartment(@RequestBody CommEmployeeDto commEmployeeDto) {
        ApiResult result = new ApiResult<>();
        List<CommRdDepartment> list = hrResourcesService.queryDepartment(commEmployeeDto);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }

    @PostMapping(value = "rdProject")
    @ApiOperation(value = "查询项目", notes = "查询项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query")
    })

    public ApiResult<List> rdProject(@RequestBody CommRdEmployeeDto commRdEmployeeDto) {
        ApiResult result = new ApiResult<>();
        List<HrProject> list = hrResourcesService.rdProject(commRdEmployeeDto);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    @PostMapping(value = "addProject")
    @ApiOperation(value = "添加项目", notes = "添加项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectCode", value = "项目编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "eid", value = "员工代码", dataType = "Integer", paramType = "query"),
    })
    public ApiResult<List> addProject(@RequestBody CommRdEmployeeProjectDto commRdEmployeeProjectDto) {
        ApiResult result = new ApiResult<>();
        hrResourcesService.addProject(commRdEmployeeProjectDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "prDelete")
    @ApiOperation(value = "删除项目", notes = "删除项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectCode", value = "项目编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "eid", value = "员工代码", dataType = "Integer", paramType = "query")
    })

    public ApiResult<List> prDelete(@RequestBody HrProject hrProject, @RequestBody CommRdEmployeeDto commRdEmployeeDto) {
        ApiResult result = new ApiResult<>();
        hrResourcesService.prDelete(hrProject, commRdEmployeeDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "rdQueryDeptTree")
    @ApiOperation(value = "查询研发人员项目", notes = "查询研发人员项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "", value = "", dataType = "", paramType = ""),
    })
    public ApiResult<Object> rdQueryDeptTree(@RequestBody RdQueryDeptType rdQueryDeptType,HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        List<CommRdEmployeeDto> queryPagerList = hrResourcesService.rdQueryDeptTree(rdQueryDeptType,user);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(queryPagerList);
        return result;
    }

    @PostMapping(value = "rdQueryProject")
    @ApiOperation(value = "查询研发人员项目", notes = "查询研发人员项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eid", value = "员工代码", dataType = "Integer", paramType = "query"),
    })
    public ApiResult<Object> rdQueryProject(@RequestBody CommRdEmployeeDto commRdEmployeeDto) {

        ApiResult result = new ApiResult<>();
        List<HrProject> list = hrResourcesService.rdQueryProject(commRdEmployeeDto);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }
        @PostMapping(value = "rdQueryProjectInfo")
    @ApiOperation(value = "全员项目分配表", notes = "全员项目分配表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eid", value = "员工代码", dataType = "Integer", paramType = "query"),
    })
    public void rdQueryProjectInfo(@RequestBody CommRdEmployeeDto commRdEmployeeDto,HttpServletResponse response) {
        Pager pager = new Pager();
        pager.setIndex(1);
        pager.setSize(5000);
        ApiResult result = new ApiResult<>();
        List<PpmAttendanceInfoExportDto> list = hrResourcesService.rdQueryProjectInfo(commRdEmployeeDto,pager);
        if (!list.isEmpty()) {
            ExcelUtil.exportExcel(list, PpmAttendanceInfoExportDto.class, "全员项目分配表.xls", response);
        }
        log.info("导出成功。");

    }

    @PostMapping(value = "Management")
    @ApiOperation(value = "考勤期间管理", notes = "考勤期间管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entryTime", value = "日期", dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "", value = "", dataType = "", paramType = "query"),
            @ApiImplicitParam(name = "", value = "", dataType = "", paramType = "query"),
    })
    public ApiResult<Object> atManagement(PpmKqCalendarDto ppmKqCalendarDto) {

        ApiResult result = new ApiResult<>();
        hrResourcesService.atManagement(ppmKqCalendarDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "atManagement")
    @ApiOperation(value = "考勤期间管理", notes = "考勤期间管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attendanceDate", value = "日期", dataType = "String", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<Object> atDateManagement(@RequestBody PpmKqRdCalendar ppmKqRdCalendar ,@RequestBody Pager pager) {

        ApiResult result = new ApiResult<>();
        hrResourcesService.atDateManagement(ppmKqRdCalendar,pager);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(pager.getList());
        result.setTotal(pager.getTotal());
        return result;
    }

    @PostMapping(value = "atEditManagement")
    @ApiOperation(value = "考勤期间管理", notes = "考勤期间管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attendanceDate", value = "日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceStatus", value = "考勤状态", dataType = "String", paramType = "query"),
    })
    public ApiResult<Object> atEditManagement(@RequestBody RdProjectAttendance rdProjectAttendance) {

        ApiResult result = new ApiResult<>();
        Integer integer = hrResourcesService.atEditManagement(rdProjectAttendance);
        if (integer == 0) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("参数异常");
            return result;
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

//    @PostMapping(value = "rdAttendanceTree")
//    @ApiOperation(value = "查询考勤填报部门下研发人员", notes = "查询考勤填报部门下研发人员")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
//    })
//
//    public ApiResult<List> rdAttendanceTree(@RequestBody PpmKqRdCalendar ppmKqRdCalendar) {
//        ApiResult result = new ApiResult<>();
//        List<CommRdEmployeeDto> dataList = hrResourcesService.rdAttendanceTree(ppmKqRdCalendar);
//        result.setResults(dataList);
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

    @PostMapping(value = "rdManagementProject")
    @ApiOperation(value = "查询考勤填报考勤信息", notes = "查询考勤填报考勤信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commRdEmployeeDtoList", value = "员工代号", dataType = "List<CommRdEmployeeDto>", paramType = "query"),
            @ApiImplicitParam(name = "ppmKqCalendarDto", value = "", dataType = "PpmKqCalendarDto", paramType = "query"),
    })
    public ApiResult<List> rdManagementProject(@RequestBody RdManagementProject rdManagementProject) {

        ApiResult result = new ApiResult<>();
        List<HrProject> dataList = new ArrayList<>();
        List<HrProject> dataList1 = hrResourcesService.rdManagementProject(rdManagementProject);
        if (!ObjectUtils.isEmpty(dataList1)) {
            result.setResults(dataList1);
        } else {
            result.setResults(dataList);
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "queryAttendanceTabulation")
    @ApiOperation(value = "考勤查询", notes = "考勤查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commRdEmployeeDtoList", value = "员工代号", dataType = "List<CommRdEmployeeDto>", paramType = "query"),
            @ApiImplicitParam(name = "ppmKqCalendarDto", value = "", dataType = "PpmKqCalendarDto", paramType = "query"),
    })
    public ApiResult<List> queryAttendanceTabulation(@RequestBody RdManagementProject rdManagementProject) {

        ApiResult result = new ApiResult<>();
        List<HrProject> dataList = new ArrayList<>();
        List<HrProject> dataList1 = hrResourcesService.queryAttendanceTabulation(rdManagementProject);
        if (!ObjectUtils.isEmpty(dataList1)) {
            result.setResults(dataList1);
        } else {
            result.setResults(dataList);
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "outputCurrent")
    @ApiOperation(value = "查询考勤导出", notes = "查询考勤导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commRdEmployeeDtoList", value = "员工代号", dataType = "List<CommRdEmployeeDto>", paramType = "query"),
            @ApiImplicitParam(name = "ppmKqCalendarDto", value = "", dataType = "PpmKqCalendarDto", paramType = "query"),
    })
    public void outputCurrent(@RequestBody RdManagementProject rdManagementProject, HttpServletResponse response) {
        List<List<HrProject>> dataList = hrResourcesService.attendanceAuditInfo(rdManagementProject);
        if (!dataList.isEmpty()){
            ExcelUtil.exportToExcel(dataList, PpmRdHrProjectInfoExportDto.class, "考勤信息.xls", response);
        }
    }

    @PostMapping(value = "attendanceRecord")
    @ApiOperation(value = "查询考勤请假信息", notes = "查询考勤请假信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commRdEmployeeDtoList", value = "员工代号", dataType = "List<CommRdEmployeeDto>", paramType = "query"),
            @ApiImplicitParam(name = "ppmKqCalendarDto", value = "", dataType = "PpmKqCalendarDto", paramType = "query"),
    })
    public ApiResult<List> attendanceRecord(@RequestBody RdManagementProject rdManagementProject) {

        ApiResult result = new ApiResult<>();
        List<HrProject> dataList = new ArrayList<>();
        List<HrProject> dataList1 = hrResourcesService.attendanceRecord(rdManagementProject);
        if (!ObjectUtils.isEmpty(dataList1)) {
            result.setResults(dataList1);
        } else {
            result.setResults(dataList);
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "qyeryEmployeeAttendance")
    @ApiOperation(value = "查询考勤请假信息", notes = "查询考勤请假信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commRdEmployeeDtoList", value = "员工代号", dataType = "List<CommRdEmployeeDto>", paramType = "query"),
            @ApiImplicitParam(name = "ppmKqCalendarDto", value = "", dataType = "PpmKqCalendarDto", paramType = "query"),
    })
    public ApiResult<List> qyeryEmployeeAttendance(@RequestBody RdManagementProject rdManagementProject) {

        ApiResult result = new ApiResult<>();
        List<HrProject> dataList = new ArrayList<>();
        List<HrProject> dataList1 = hrResourcesService.qyeryEmployeeAttendance(rdManagementProject);
        if (!ObjectUtils.isEmpty(dataList1)) {
            result.setResults(dataList1);
        } else {
            result.setResults(dataList);
        }
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "keep")
    @ApiOperation(value = "填报考勤信息", notes = "填报考勤信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ppmKqRdCalendars", value = "list", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "eidCode", value = "员工代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时长", dataType = "Double", paramType = "query"),
    })
    public ApiResult<List> keep(@RequestBody List<HrProject> hrProjects) {
        ApiResult result = new ApiResult<>();
        Integer i = hrNewRequiresService.keep(hrProjects);
        if (i != 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("考勤工时数值格式不正确或此日期为节假日");
        }
        return result;
    }

    @PostMapping(value = "submit")
    @ApiOperation(value = "提交考勤填报考勤信息", notes = "提交考勤填报考勤信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eidCode", value = "员工代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时长", dataType = "Double", paramType = "query"),
    })
    public ApiResult<List> submit(@RequestBody List<HrProject> hrProjects, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        ApiResult result = new ApiResult<>();
        hrResourcesService.submit(hrProjects);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "withdraw")
    @ApiOperation(value = "驳回提交考勤填报考勤信息", notes = "驳回提交考勤填报考勤信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eidCode", value = "员工代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时长", dataType = "Double", paramType = "query"),
    })
    public ApiResult<List> withdraw(@RequestBody RdManagementProject rdManagementProject) {
        ApiResult result = new ApiResult<>();
        hrResourcesService.withdraw(rdManagementProject);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "attendanceAudit")
    @ApiOperation(value = "审核考勤填报考勤信息", notes = "审核考勤填报考勤信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eidCode", value = "员工代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时长", dataType = "Double", paramType = "query"),
    })
    public ApiResult<List> attendanceAudit(@RequestBody RdManagementProject rdManagementProject) {
        ApiResult result = new ApiResult<>();
        List<HrProject> dataList = hrResourcesService.attendanceAudit(rdManagementProject);
        result.setResults(dataList);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "attendanceAuditInfo")
    @ApiOperation(value = "导出当前考勤信息", notes = "导出当前考勤信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eidCode", value = "员工代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时长", dataType = "Double", paramType = "query"),
    })
    public void attendanceAuditInfo(@RequestBody RdManagementProject rdManagementProject, HttpServletResponse response) {
        List<List<HrProject>> dataList = hrResourcesService.attendanceAuditInfo(rdManagementProject);
        if (!dataList.isEmpty()){
            ExcelUtil.exportToExcel(dataList, PpmRdHrProjectInfoExportDto.class, "考勤信息.xls", response);
        }

    }


    //    submitAudit
    @PostMapping(value = "submitAudit")
    @ApiOperation(value = "审核通过", notes = "审核通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eidCode", value = "员工代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时长", dataType = "Double", paramType = "query"),
    })
    public ApiResult<List> submitAudit(@RequestBody RdManagementHrProject rdManagementProject, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        ApiResult result = new ApiResult<>();
        hrResourcesService.submitAudit(rdManagementProject,userid);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "submitTabulation")
    @ApiOperation(value = "勤填报提交造册", notes = "勤填报提交造册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eidCode", value = "员工代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时长", dataType = "Double", paramType = "query"),
    })
    public ApiResult<List> submitTabulation(@RequestBody RdProjectAttendance rdProjectAttendance) {
        ApiResult result = new ApiResult<>();
        hrResourcesService.submitTabulation(rdProjectAttendance);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

//    @PostMapping(value = "reject")
//    @ApiOperation(value = "驳回提交考勤填报考勤信息", notes = "驳回提交考勤填报考勤信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "eidCode", value = "员工代号", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "attendanceTime", value = "考勤时长", dataType = "Double", paramType = "query"),
//    })
//    public ApiResult<List> reject(@RequestBody RdManagementProject rdManagementProject) {
//        ApiResult result = new ApiResult<>();
//        hrResourcesService.reject(rdManagementProject);
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

}
