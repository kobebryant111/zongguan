package com.work.ppm.application.api.testmanager;

import com.work.ppm.application.dto.testmanager.TestEmployeePoolDto;
import com.work.ppm.application.dto.testmanager.TestEmployeePoolExportDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.testmanager.IUndistributionPoolService;
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
 * @className UndistributionPoolApi
 * @date 2024/8/30
 * 未分配测试人员池
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"TestApectaculars"},description="未分配测试人员池")
@RestController
@RequestMapping("/undistributionPool")
@Slf4j
public class UndistributionPoolApi {
    @Autowired
    private IUndistributionPoolService iUndistributionPoolService;


    /**
     * 未分配人员池分页查询
     *
     * @param testEmployeePoolDto 对象
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
        iUndistributionPoolService.queryPager(testEmployeePoolDto, pager);
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
        List<CommEmployee> list = iUndistributionPoolService.getHeadOfCompanyList();
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 未分配测试人员池信息导出
     *
     * @param testEmployeePoolDto 对象
     * @param response http响应对象
     * @return void
     */
    @PostMapping(value = "/exportUndistriPoolExcel")
    @ApiOperation(value ="未分配测试人员池信息导出", notes = "未分配测试人员池信息导出")
    public void exportUndistriPoolExcel(@RequestBody TestEmployeePoolDto testEmployeePoolDto, HttpServletResponse response) {
        List<TestEmployeePoolExportDto> reqs = this.iUndistributionPoolService.exportUndistriPoolExcel(testEmployeePoolDto);
        if (!reqs.isEmpty()){
            ExcelUtil.exportExcel(reqs, TestEmployeePoolExportDto.class, "未分配测试人员池信息表.xls", response);
        }
        log.info("未分配测试人员池信息导出成功。");
    }

    /**
     * 测试人员出池
     *
     * @param testEmployeePoolDto 人员池对象
     * @return ApiResult
     */
    @PostMapping(value = "/dialogAddSubmitUndis")
    @ApiOperation(value ="测试人员出池", notes = "测试人员出池")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "ownedTestTeam", value = "所属测试团队", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ownedTestGroup", value = "所属测试小组", dataType = "string", paramType = "query"),
    })
    public ApiResult dialogAddSubmit(@RequestBody TestEmployeePoolDto testEmployeePoolDto) {
        ApiResult result = iUndistributionPoolService.dialogAddSubmit(testEmployeePoolDto);
        return result;
    }
}
