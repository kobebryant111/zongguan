package com.work.ppm.application.api.safetymanager;

import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.safetymanager.BusinessUnit;
import com.work.ppm.application.model.safetymanager.EmployeeDepartMsg;
import com.work.ppm.application.model.safetymanager.UpdateEmployeeInfo;
import com.work.ppm.application.services.safetymanager.IBusinessUnitService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName com.work.ppm.application.api.safetymanager
 *
 * @version JDK 8
 * @className BusinessUnitApi
 * @date 2025/1/3
 * @description TODO
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"businessUnit"},description="业务部门安全专员清单")
@RestController
@RequestMapping("/businessUnit")
@Slf4j
public class BusinessUnitApi {
    @Autowired
    private IBusinessUnitService businessUnitService;

    /**
     * 总部安全专员清单分页查询
     *
     * @param businessUnit 对象
     * @param pager 分页对象
     * @return ApiResult
     * @date 2025/1/2
     * @version JDK 8
     */
    @PostMapping(value = "/queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "depcode", value = "一级部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "location", value = "一级部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "safetySpecialist", value = "安全专员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phoneNum", value = "电话号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "businessUnitDepcode", value = "二级部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "businessUnit", value = "二级部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaManager", value = "区域总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaSafetySpecialist", value = "区域安全专员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaBadge", value = "区域安全专员工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaPhoneNum", value = "区域安全专员联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateTime", value = "修改时间", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(BusinessUnit businessUnit, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        businessUnitService.queryPager(businessUnit, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }


    /**
     * 修改内容
     *
     * @param businessUnit 对象
     * @return ApiResult
     * @date 2025/1/15
     * @version JDK 8
     * @description TODO
     */
    @PostMapping(value = "/updateMsg")
    @ApiOperation(value ="修改内容", notes = "修改内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "depcode", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "location", value = "一级部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstLevelManager", value = "一级部总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "safetySpecialist", value = "安全专员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phoneNum", value = "电话号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "businessUnit", value = "业务部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaManager", value = "区域负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaSafetySpecialist", value = "区域安全专员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oldAreaBadge", value = "区域安全专员工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaBadge", value = "区域安全专员工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaPhoneNum", value = "区域安全专员联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateTime", value = "修改时间", dataType = "string", paramType = "query"),
    })
    public ApiResult updateMsg(BusinessUnit businessUnit) {
        ApiResult result = new ApiResult<>();
        result = businessUnitService.updateMsg(businessUnit);
        return result;
    }

    /**
     * 修改人员信息
     *
     * @return ApiResult
     * @date 2025/6/3
     * @version JDK 8
     * @description TODO
     */
    @PostMapping(value = "/updateEmployeeMsg")
    @ApiOperation(value ="修改人员信息", notes = "修改人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depcode", value = "一级部部门编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "oldBadge", value = "原来员工工号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phoneNum", value = "联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "safetySpecialist", value = "安全部门专员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaSafetySpecialist", value = "区域安全专员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "businessUnitDepcode", value = "二级部部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "oldAreaBadge", value = "原来二级部门安全专员工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaBadge", value = "区域安全专员工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaPhoneNum", value = "区域安全专员联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "safetyStatus", value = "一级部员工状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "areaSafetyStatus", value = "二级部员工状态", dataType = "string", paramType = "query"),
    })
    public ApiResult updateEmployeeMsg(BusinessUnit businessUnit) {
        ApiResult result = new ApiResult<>();
        result = businessUnitService.updateEmployeeMsg(businessUnit);
        return result;
    }


}
