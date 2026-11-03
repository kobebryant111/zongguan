package com.work.ppm.application.api.rdmanager;

import com.work.ppm.application.dto.integratedmanager.PpmRdHrProjectInfoExportDto;
import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.HrProject;
import com.work.ppm.application.model.rdmanager.RdEmployeeDto;
import com.work.ppm.application.model.rdmanager.RdManagementProject;
import com.work.ppm.application.services.rdmanager.IHumanResourceService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 研发项目人员
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"HumanResources"}, description = "研发人力资源管理模块")
@RestController
@RequestMapping("humanResources")
@Slf4j
@Validated
public class HumanResources extends BaseApi {

    @Autowired
    private IHumanResourceService humanResourceService;

    @PostMapping(value = "jobTransfer")
    @ApiOperation(value = "部门调整", notes = "部门调整")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "empstatus", value = "人员状态", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "chargeName", value = "部门", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nature", value = "性质", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "arrivalTime", value = "到岗日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "leaveTime", value = "离岗日期", dataType = "String", paramType = "query"),
    })
    public ApiResult<Object> jobTransfer(@RequestBody RdEmployeeDto rdEmployeeDto) {
        ApiResult<Object> result = new ApiResult<>();
        Integer integer = humanResourceService.jobTransfer(rdEmployeeDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "currentAllYear")
    @ApiOperation(value = "获取年份", notes = "获取年份")
    @ApiImplicitParams({
    })
    public ApiResult<List<String>> currentAllYear() {
        ApiResult<List<String>> result = new ApiResult<>();
        List<String> list =humanResourceService.currentAllYear();
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }

    @PostMapping(value = "importYearAll")
    @ApiOperation(value = "导出当前考勤信息", notes = "导出当前考勤信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eidCode", value = "员工代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceDate", value = "考勤日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "attendanceTime", value = "考勤时长", dataType = "Double", paramType = "query"),
    })
    public void importYearAll(@RequestBody PpmKqRdCalendar ppmKqRdCalendar, HttpServletResponse response) {
        List<Map<Object, Object>> dataList = humanResourceService.importYearAll(ppmKqRdCalendar);
        if (!dataList.isEmpty()){
            ExcelUtil.mapToExcel(dataList, Map.class, ppmKqRdCalendar.getYearOfCalendar()+"年度考勤信息.xls", response);
        }

    }
}
