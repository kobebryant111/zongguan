package com.work.ppm.application.api.testmanager;

import com.work.ppm.application.dto.testmanager.TestEmployeePoolDto;
import com.work.ppm.application.dto.testmanager.TestGroupDto;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.application.services.testmanager.ITestTeamManagementService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
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
 * packageName com.work.ppm.application.api.testmanager
 *
 * @version JDK 8
 * @className TestTeamManagementApi
 * @date 2024/8/29
 * 测试组管理
 */
@Api( produces = "application/json;charset=UTF-8")
@RestController
@RequestMapping("/testTeamManagement")
@Slf4j
public class TestTeamManagementApi {

    @Autowired
    private ITestTeamManagementService iTestTeamManagementService;


    /**
     * 创建测试小组
     *
     * @param testGroupDto 测试小组对象
     * @return ApiResult
     */
    @PostMapping(value = "/creatTestGroup")
    @ApiOperation(value ="创建测试小组", notes = "创建测试小组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "testGroupName", value = "测试小组名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testGroupComemployee", value = "测试小组负责人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ownedTestTeam", value = "测试小组所属团队", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testGroupLeader", value = "测试小组组长", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testGroupMember", value = "测试小组成员", dataType = "list", paramType = "query"),
    })
    public ApiResult creatTestGroup(TestGroupDto testGroupDto) {
        ApiResult result = iTestTeamManagementService.creatTestGroup(testGroupDto);
        return result;
    }

    /**
     * 查询测试人员
     *
     * @param teamId 团队ID
     * @return ApiResult<List<TestEmployeePoolDto>>
     */
    @PostMapping(value = "/selectTestGroupEmployee")
    @ApiOperation(value ="查询测试团队下所有的成员信息", notes = "查询测试人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamId", value = "团队Id", dataType = "string", paramType = "update"),
    })
    public ApiResult<List<TestEmployeePoolDto>> selectTestGroupEmployee(String teamId) {
        ApiResult<List<TestEmployeePoolDto>> result = new ApiResult<>();
        List<TestEmployeePoolDto> list = iTestTeamManagementService.selectTestGroupEmployee(teamId);
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 查询当前小组名是否重复
     *
     * @param testGroupName 小组名称
     * @return ApiResult
     */
    @PostMapping(value = "/selectGroupName")
    @ApiOperation(value ="查询当前小组名是否重复", notes = "查询当前小组名是否重复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "testGroupName", value = "小组名称", dataType = "string", paramType = "update"),
    })
    public ApiResult selectGroupName(String testGroupName) {
        ApiResult result = new ApiResult<>();
        List<TestGroup> list = iTestTeamManagementService.selectGroupName(testGroupName);
        if (list.size() > 0) {
            result.setStatus(ApiCode.FAIL);
        } else {
            result.setStatus(ApiCode.SUCCESS);
        }
        return result;
    }
}
