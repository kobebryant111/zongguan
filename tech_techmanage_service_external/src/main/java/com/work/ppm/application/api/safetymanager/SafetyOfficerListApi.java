package com.work.ppm.application.api.safetymanager;

import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.safetymanager.BusinessUnit;
import com.work.ppm.application.model.safetymanager.EmployeeDepartMsg;
import com.work.ppm.application.model.safetymanager.SafetyOfficerList;
import com.work.ppm.application.model.safetymanager.UpdateEmployeeInfo;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.application.services.safetymanager.ISafetyOfficerListService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * packageName com.work.ppm.application.api.safetymanager
 *
 * @version JDK 8
 * @className safetyOfficerListApi
 * @date 2025/1/2
 * @description TODO
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"safetyOfficerList"},description="总部安全专员清单")
@RestController
@RequestMapping("/safetyOfficerList")
@Slf4j
public class SafetyOfficerListApi extends BaseApi {
    @Autowired
    private ISafetyOfficerListService safetyOfficerListService;
    /**
     * 员工
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;


    /**
     * 总部安全专员清单分页查询
     *
     * @param safetyOfficerList 对象
     * @param pager 分页对象
     * @return ApiResult
     * @date 2025/1/2
     * @version JDK 8
     */
    @PostMapping(value = "/queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "depcode", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "location", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depManager", value = "部门经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "safetySpecialist", value = "安全专员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phoneNum", value = "电话号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateTime", value = "修改时间", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(SafetyOfficerList safetyOfficerList, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        safetyOfficerListService.queryPager(safetyOfficerList, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 分页查询
     *
     * @param request 请求对象
     * @return ApiResult
     * @date 2025/1/15
     * @version JDK 8
     * @description TODO
     */
    @PostMapping(value = "/queryRole")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    public ApiResult queryRole(HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<ImUserRoleRel> imUserRoleRelList = safetyOfficerListService.queryRole(user.getUserid());
        result.setResults(imUserRoleRelList);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 修改内容
     *
     * @param safetyOfficerList 对象
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
            @ApiImplicitParam(name = "location", value = "部门名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depManager", value = "部门经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "safetySpecialist", value = "安全专员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phoneNum", value = "电话号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateTime", value = "修改时间", dataType = "string", paramType = "query"),
    })
    public ApiResult updateMsg(SafetyOfficerList safetyOfficerList) {
        ApiResult result = new ApiResult<>();
        result = safetyOfficerListService.updateMsg(safetyOfficerList);
        return result;
    }


    /**
     * 查询工号等基本信息
     *
     * @return ApiResult
     */
    @PostMapping(value = "/quertBadgeMsg")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    public ApiResult quertBadgeMsg() {
        ApiResult<List> result = new ApiResult<>();
        List<EmployeeDepartMsg> list = safetyOfficerListService.quertBadgeMsg();
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }

    /**
     * 修改人员信息
     *
     * @return ApiResult
     * @date 2025/5/30
     * @version JDK 8
     * @description TODO
     */
    @PostMapping(value = "/updateEmployeeMsg")
    @ApiOperation(value ="修改人员信息", notes = "修改人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depcode", value = "部门编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobiletelphone", value = "联系电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "safetyStatus", value = "员工状态", dataType = "string", paramType = "query"),
    })
    public ApiResult updateEmployeeMsg(UpdateEmployeeInfo updateEmployeeInfo) {
        ApiResult result = new ApiResult<>();
        result = safetyOfficerListService.updateEmployeeMsg(updateEmployeeInfo);
        return result;
    }

    /**
     * 分页
     * @param commEmployee 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryEmployeePager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mobiletelphone", value = "联系电话", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depcode", value = "部门编号", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryEmployeePager(CommEmployeeDto commEmployee, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        safetyOfficerListService.queryEmployeePager(commEmployee,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }


}
