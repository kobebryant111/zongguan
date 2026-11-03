package com.work.ppm.application.api.testmanager;

import com.work.ppm.application.dto.testmanagement.PpmTestTeamDto;
import com.work.ppm.application.dto.testmanager.TestEmployeePoolDto;
import com.work.ppm.application.dto.testmanager.TestEmployeePoolExportDto;
import com.work.ppm.application.dto.testmanager.TestGroupDto;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.application.services.testmanager.ITestEmployeePoolService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * packageName com.work.ppm.application.api.testmanager
 *
 * @version JDK 8
 * @className TestEmployeePoolApi
 * @date 2024/8/15
 *  测试人员池
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"TestApectaculars"},description="测试人员池")
@RestController
@RequestMapping("/testEmployeePool")
@Slf4j
public class TestEmployeePoolApi {

    @Autowired
    private ITestEmployeePoolService iTestEmployeePoolService;

    /**
     * 测试人员池分页查询
     *
     * @param testEmployeePoolDto 测试人员池对象
     * @param pager 分页参数
     * @return ApiResult
     */
    @PostMapping(value = "/queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "testEmpName", value = "测试人员", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testEmpBadge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "affilCompany", value = "所属公司", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "employeeStatus", value = "员工状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "headOfCompany", value = "公司内负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ownedTestTeam", value = "所属测试团队", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ownedTestGroup", value = "所属测试小组", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "empAttribute", value = "属性", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testGroupLeader", value = "测试组长", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "groupMember", value = "小组成员", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(TestEmployeePoolDto testEmployeePoolDto, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        iTestEmployeePoolService.queryPager(testEmployeePoolDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 获取所有公司内负责人
     *
     * @return ApiResult<List<CommEmployee>>
     */
    @GetMapping("getHeadOfCompanyList")
    @ApiOperation(value = "获取所有公司内负责人")
    public ApiResult<List<CommEmployee>> getHeadOfCompanyList() {
        ApiResult<List<CommEmployee>> result = new ApiResult<>();
        List<CommEmployee> list = iTestEmployeePoolService.getHeadOfCompanyList();
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 测试人员池分页查询
     *
     * @return ApiResult<List<PpmOutsourcingInfo>>
     */
    @GetMapping("getAffilCompanyList")
    @ApiOperation(value = "获取所有所属公司信息")
    public ApiResult<List<PpmOutsourcingInfo>> getAffilCompanyList() {
        ApiResult<List<PpmOutsourcingInfo>> result = new ApiResult<>();
        List<PpmOutsourcingInfo> list = iTestEmployeePoolService.getAffilCompanyList();
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 获取所有所属测试团队
     *
     * @return ApiResult<List<PpmTestTeamDto>>
     */
    @GetMapping("getOwnedTestTeamList")
    @ApiOperation(value = "获取所有所属测试团队信息")
    public ApiResult<List<PpmTestTeamDto>> getOwnedTestTeamList() {
        ApiResult<List<PpmTestTeamDto>> result = new ApiResult<>();
        List<PpmTestTeamDto> list = iTestEmployeePoolService.getOwnedTestTeamList();
        result.setResults(list);
        result.success("获取成功");
        return result;
    }


    /**
     * 获取所有所属测试小组
     *
     * @return ApiResult<List<TestGroupDto>>
     */
    @GetMapping("getOwnedTestGroupList")
    @ApiOperation(value = "获取所有所属测试小组信息")
    public ApiResult<List<TestGroupDto>> getOwnedTestGroupList() {
        ApiResult<List<TestGroupDto>> result = new ApiResult<>();
        List<TestGroupDto> list = iTestEmployeePoolService.getOwnedTestGroupList();
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 获取所有所属测试小组
     *
     * @return ApiResult<List<TestEmployeePoolDto>>
     */
    @GetMapping("getTestGroupEmpList")
    @ApiOperation(value = "获取所有测试小组人员信息")
    public ApiResult<List<TestEmployeePoolDto>> getTestGroupEmpList() {
        ApiResult<List<TestEmployeePoolDto>> result = new ApiResult<>();
        List<TestEmployeePoolDto> list = iTestEmployeePoolService.getTestGroupEmpList();
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 测试人员池分页查询
     *
     * @param testEmployeePoolDto 人员池对象
     * @param response http响应参数
     * @return void
     */
    @PostMapping(value = "/exportTestEmpPoolExcel")
    @ApiOperation(value ="测试人员池信息导出", notes = "测试人员池信息导出")
    public void exportTestEmpPoolExcel(@RequestBody TestEmployeePoolDto testEmployeePoolDto, HttpServletResponse response) {
        List<TestEmployeePoolExportDto> reqs = this.iTestEmployeePoolService.exportTestEmpPoolExcel(testEmployeePoolDto);
        if (!reqs.isEmpty()){
            ExcelUtil.exportExcel(reqs, TestEmployeePoolExportDto.class, "测试人员池信息表.xls", response);
        }
        log.info("测试人员池信息导出成功。");
    }

    /**
     * 测试人员出池功能
     *
     * @param testEmployeePoolDto 人员池对象
     * @return ApiResult
     */
    @PostMapping(value = "/dialogAddSubmit")
    @ApiOperation(value ="测试人员信息修改", notes = "测试人员信息修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "affilCompany", value = "所属公司", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "employeeStatus", value = "员工状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ownedTestTeam", value = "所属测试团队", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ownedTestGroup", value = "所属测试小组", dataType = "string", paramType = "query"),
    })
    public ApiResult dialogAddSubmit(@RequestBody TestEmployeePoolDto testEmployeePoolDto) {
            ApiResult result = iTestEmployeePoolService.dialogAddSubmit(testEmployeePoolDto);
        return result;
    }


    /**
     * 查询测试团队下所有的小组信息
     *
     * @param teamId 团队ID
     * @return ApiResult<List<TestGroup>>
     */
    @PostMapping(value = "/selectTestGroupInfoMsg")
    @ApiOperation(value ="查询测试团队下所有的小组信息", notes = "创建小组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamId", value = "团队Id", dataType = "string", paramType = "update"),
    })
    public ApiResult<List<TestGroup>> selectTestGroupInfoMsg(String teamId) {
        ApiResult<List<TestGroup>> result = new ApiResult<>();
        List<TestGroup> list = iTestEmployeePoolService.selectTestGroupInfoMsg(teamId);
        result.setResults(list);
        if (list.size() == 0) {
            result.setStatus(ApiCode.ISNULL);
            result.setMessage("未查询到该团队下的小组信息!");
        }else {
            result.setStatus(ApiCode.SUCCESS);
        }
        return result;
    }

}
