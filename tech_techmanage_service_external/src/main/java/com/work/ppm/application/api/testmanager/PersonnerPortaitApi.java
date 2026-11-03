package com.work.ppm.application.api.testmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.testmanagement.PersonnerPortaitsDto;
import com.work.ppm.application.dto.testmanagement.PpmTeamGroup;
import com.work.ppm.application.dto.testmanagement.PpmTestGroupDto;
import com.work.ppm.application.dto.testmanagement.PpmTestGroupInfo;
import com.work.ppm.application.dto.testmanagement.PpmTestTeamDto;
import com.work.ppm.application.model.rdmanager.RdProjectExcel;
import com.work.ppm.application.model.systemmanager.ext.CommEmployeeInfo;
import com.work.ppm.application.dto.testmanagement.PpmTestTeamInfo;
import com.work.ppm.application.model.testmanager.TestEmployeePool;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.application.model.testmanager.TestGroupExcel;
import com.work.ppm.application.model.testmanager.TestGroupInfo;
import com.work.ppm.application.model.testmanager.TestTeatDtoExcel;
import com.work.ppm.application.services.testmanager.IPersonnerPortaitService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * packageName com.work.ppm.application.api.testmanager
 *
 * @version JDK 8
 * @className PersonnerPortaitApi
 * @date 2024/8/2
 * 人员画像
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"TestApectaculars"}, description = "人员画像")
@RestController
@RequestMapping("/testManagement")
@Slf4j
public class PersonnerPortaitApi extends BaseApi {
    @Autowired
    private IPersonnerPortaitService personnerPortaitService;

    /**
     * 分页查询
     *
     * @param personnerPortaitsDto 查询条件实体类
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "/personnerPortaitQueryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "textTeam", value = "所属团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "textGroup", value = "所属小组", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "entryTime", value = "开始/结束日期", dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "outsourcname", value = "供应商名称", dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "whetherIncludeSelfProject", value = "是否包含自建项目",
                    dataType = "boolean", paramType = "query"),
    })
    public ApiResult queryPager(PersonnerPortaitsDto personnerPortaitsDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        personnerPortaitService.queryPager(personnerPortaitsDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 分页查询
     *
     * @param personnerPortaitsDto 包含查询条件实体类
     * @param pager                分页条件
     * @return ApiResult
     */
    @PostMapping(value = "/personnerPortaitQueryPagerInfo")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "textTeam", value = "所属团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "textGroup", value = "所属小组", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "entryTime", value = "开始/结束日期", dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "outsourcname", value = "供应商名称", dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "whetherIncludeSelfProject", value = "是否包含自建项目",
                    dataType = "boolean", paramType = "query"),
    })
    public ApiResult queryPagerInfo(PersonnerPortaitsDto personnerPortaitsDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        personnerPortaitService.queryPagerInfo(personnerPortaitsDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询项目经理列表
     *
     * @return ApiResult
     */
    @PostMapping(value = "/selectTeamMansgerList")
    @ApiOperation(value = "查询项目经理列表", notes = "查询项目经理列表")
    @ApiImplicitParams({})
    public ApiResult selectTeamMansgerList() {
        ApiResult<List> result = new ApiResult<>();
        List<CommEmployeeInfo> commEmployees = personnerPortaitService.selectTeamMansgerList();
        result.setResults(commEmployees);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询团队成员列表
     *
     * @return ApiResult
     */
    @PostMapping(value = "/selectTeamMemberList")
    @ApiOperation(value = "查询团队成员列表", notes = "查询团队成员列表")
    @ApiImplicitParams({})
    public ApiResult selectTeamMemberList() {
        ApiResult<List> result = new ApiResult<>();
        List<TestEmployeePool> testEmployeePools = personnerPortaitService.selectTeamMemberList();
        result.setResults(testEmployeePools);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询团队成员列表
     *
     * @param ppmTestTeamDto 查询团队条件实体类
     * @param request        HTTP请求
     * @return ApiResult
     */
    @PostMapping(value = "/creatOfTeam")
    @ApiOperation(value = "查询团队成员列表", notes = "查询团队成员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamName", value = "所属团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamMansger", value = "团队项目经理", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamMember", value = "团队成员", dataType = "String", paramType = "query"),
    })
    public ApiResult creatOfTeam(@RequestBody PpmTestTeamDto ppmTestTeamDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        ApiResult<List> result = personnerPortaitService.creatOfTeam(ppmTestTeamDto, userid);

        return result;
    }

    /**
     * 查询测试团队列表
     *
     * @param ppmTestTeamInfo 查询条件实体类
     * @param pager           分页
     * @return ApiResult
     */
    @PostMapping(value = "/selectTeam")
    @ApiOperation(value = "查询测试团队列表", notes = "查询测试团队列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamId", value = "所属团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "团队项目经理", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "testEmpBadge", value = "团队成员", dataType = "String", paramType = "query"),
    })
    public ApiResult selectTeam(@RequestBody PpmTestTeamInfo ppmTestTeamInfo, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        personnerPortaitService.teamNameListInfo(ppmTestTeamInfo, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询团队成员做下拉框
     *
     * @return ApiResult
     */
    @PostMapping(value = "/selectTeamMemberInfo")
    @ApiOperation(value = "查询团队成员做下拉框", notes = "查询团队成员做下拉框")
    @ApiImplicitParams({})
    public ApiResult teamMemberListinfo() {
        ApiResult<List> result = new ApiResult<>();
        List<TestEmployeePool> testEmployeePools = personnerPortaitService.teamMemberListinfo();
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(testEmployeePools);
        return result;
    }

    /**
     * 编辑测试团队
     *
     * @param ppmTestTeamDto 编辑条件
     * @param request        对象
     * @return ApiResult
     */
    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑测试团队", notes = "编辑测试团队")
    @ApiImplicitParams({})
    public ApiResult edit(@RequestBody PpmTestTeamDto ppmTestTeamDto, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        personnerPortaitService.edit(ppmTestTeamDto, userid);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 修改团队成员
     *
     * @param ppmTestTeamDto d对象
     * @param request        对象
     * @return ApiResult
     */
    @PostMapping(value = "/editTemMeb")
    @ApiOperation(value = "修改团队成员", notes = "修改团队成员")
    @ApiImplicitParams({})
    public ApiResult editTemMeb(@RequestBody PpmTestTeamDto ppmTestTeamDto, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        List<TestEmployeePool> testEmployeePools = personnerPortaitService.editTemMeb(ppmTestTeamDto, userid);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(testEmployeePools);
        return result;
    }

    /**
     * 查询无团队的成员和当前团队所属成员
     *
     * @param ppmTestTeamDto d对象
     * @param request        对象
     * @return ApiResult
     */
    @PostMapping(value = "/queryTemMeb")
    @ApiOperation(value = "查询无团队的成员和当前团队所属成员", notes = "查询无团队的成员和当前团队所属成员")
    @ApiImplicitParams({})
    public ApiResult queryTemMeb(@RequestBody PpmTestTeamDto ppmTestTeamDto, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        List<TestEmployeePool> testEmployeePools = personnerPortaitService.queryTemMeb(ppmTestTeamDto);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(testEmployeePools);
        return result;
    }

    /**
     * 修改团队成员
     *
     * @param ppmTestTeamDto 修改对象
     * @return ApiResult
     */
    @PostMapping(value = "/updateTemMeb")
    @ApiOperation(value = "修改团队成员", notes = "修改团队成员")
    @ApiImplicitParams({})
    public ApiResult updateTemMeb(@RequestBody PpmTestTeamDto ppmTestTeamDto) {
        ApiResult<List> result = new ApiResult<>();
        personnerPortaitService.updateTemMeb(ppmTestTeamDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 删除测试团队
     *
     * @param ppmTestTeamDto 删除条件对象实体类
     * @return ApiResult
     */
    @PostMapping(value = "/deleteTeam")
    @ApiOperation(value = "删除测试团队", notes = "删除测试团队")
    @ApiImplicitParams({})
    public ApiResult deleteTeam(@RequestBody PpmTestTeamDto ppmTestTeamDto) {
        ApiResult<List> result = new ApiResult<>();
        personnerPortaitService.deleteTeam(ppmTestTeamDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询测试团队详情
     *
     * @param ppmTestTeamDto 查询对象
     * @return ApiResult
     */
    @PostMapping(value = "/temDetail")
    @ApiOperation(value = "查询测试团队详情", notes = "查询测试团队详情")
    @ApiImplicitParams({})
    public ApiResult temDetail(@RequestBody PpmTestTeamDto ppmTestTeamDto) {
        ApiResult<List> result = new ApiResult<>();
        List<TestGroup> list = personnerPortaitService.temDetail(ppmTestTeamDto);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 下载测试团队列表
     *
     * @param ppmTestTeamInfo 查询条件实体类
     * @param pager           分页
     */
    @PostMapping(value = "/queryPagerInfo")
    @ApiOperation(value = "下载测试团队列表", notes = "下载测试团队列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamId", value = "所属团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "团队项目经理", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "testEmpBadge", value = "团队成员", dataType = "String", paramType = "query"),
    })
    public void excelInfo(@RequestBody PpmTestTeamInfo ppmTestTeamInfo, Pager pager,
                          HttpServletResponse response) {
        List<TestTeatDtoExcel> testTeatDtoExcels = personnerPortaitService.excelInfo(ppmTestTeamInfo, pager);
        if (!testTeatDtoExcels.isEmpty()) {
            ExcelUtil.exportExcel(testTeatDtoExcels, TestTeatDtoExcel.class, "项目表.xls", response);
        }
        log.info("导出成功。");
    }

    /**
     * 查询测试小组列表
     *
     * @param ppmTeamGroup 查询条件实体类
     * @param pager        分页
     * @return ApiResult
     */
    @PostMapping(value = "/queryPagerGrop")
    @ApiOperation(value = "查询测试小组列表", notes = "查询测试小组列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "测试小组", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ownedTestTeam", value = "测试团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamMansger", value = "项目经理", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "testGroupLeader", value = "测试组长", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "testGroupMember", value = "小组成员", dataType = "String", paramType = "query"),
    })
    public ApiResult queryPagerGrop(@RequestBody PpmTeamGroup ppmTeamGroup, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        personnerPortaitService.queryPagerGrop(ppmTeamGroup, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 测试小组下拉框查询
     *
     * @return ApiResult
     */
    @PostMapping(value = "/secltGroup")
    @ApiOperation(value = "测试小组下拉框查询", notes = "测试小组下拉框查询")
    @ApiImplicitParams({})
    public ApiResult secltGroup() {
        ApiResult<List> result = new ApiResult<>();
        List<TestGroup> testGroups = personnerPortaitService.secltGroup();
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(testGroups);
        return result;
    }

    /**
     * 下载测试小组列表
     *
     * @param ppmTeamGroup 查询条件实体类
     * @param pager        分页
     * @return ApiResult
     */
    @PostMapping(value = "/secltGroupExcel")
    @ApiOperation(value = "下载测试小组列表", notes = "下载测试小组列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "测试小组", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ownedTestTeam", value = "测试团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamMansger", value = "项目经理", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "testGroupLeader", value = "测试组长", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "testGroupMember", value = "小组成员", dataType = "String", paramType = "query"),
    })
    public void secltGroupExcel(@RequestBody PpmTeamGroup ppmTeamGroup, Pager pager, HttpServletResponse response) {
        List<TestGroupExcel> repsJson = personnerPortaitService.secltGroupExcel(ppmTeamGroup, pager);
        if (!repsJson.isEmpty()) {
            ExcelUtil.exportExcel(repsJson, TestGroupExcel.class, "项目表.xls", response);
        }
        log.info("导出成功。");
    }

    /**
     * 删除测试小组列表
     * @param ppmTestGroupDto 删除对象
     * @return ApiResult
     */
    @PostMapping(value = "/deleteGroupInfo")
    @ApiOperation(value = "删除测试小组列表", notes = "删除测试小组列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "测试小组", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "testGroupName", value = "测试小组名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamName", value = "所属测试团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamMansger", value = "项目经理", dataType = "List", paramType = "query"),
            @ApiImplicitParam(name = "testEmpName", value = "小组组长", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupMemberNo", value = "小组成员数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupMember", value = "小组成员", dataType = "String", paramType = "query"),
    })
    public ApiResult deleteGroupInfo(@RequestBody PpmTestGroupDto ppmTestGroupDto ) {
        ApiResult<List> result = new ApiResult<>();
        personnerPortaitService.deleteGroupInfo(ppmTestGroupDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
    /**
     * 测试小组成员下拉框
     * @param ppmTestGroupDto 测试小组成员下拉框对象
     * @return ApiResult
     */
    @PostMapping(value = "/groupDateInfo")
    @ApiOperation(value = "测试小组成员下拉框", notes = "测试小组成员下拉框")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "测试小组", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "testGroupName", value = "测试小组名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamName", value = "所属测试团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamMansger", value = "项目经理", dataType = "List", paramType = "query"),
            @ApiImplicitParam(name = "testEmpName", value = "小组组长", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupMemberNo", value = "小组成员数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupMember", value = "小组成员", dataType = "String", paramType = "query"),
    })
    public ApiResult groupDateInfo(@RequestBody PpmTestGroupDto ppmTestGroupDto ) {
        ApiResult<List> result = new ApiResult<>();
        List<TestEmployeePool> testEmployeePools = personnerPortaitService.groupDateInfo(ppmTestGroupDto);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(testEmployeePools);
        return result;
    }

    /**
     * 编辑测试小组确认
     * @param ppmTestGroupInfo 编辑对象
     * @return ApiResult
     */
    @PostMapping(value = "/updateTestGroup")
    @ApiOperation(value = "编辑测试小组确认", notes = "编辑测试小组确认")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "测试小组", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "testGroupName", value = "测试小组名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamName", value = "所属测试团队", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "teamMansger", value = "项目经理", dataType = "List", paramType = "query"),
            @ApiImplicitParam(name = "testEmpName", value = "小组组长", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupMemberNo", value = "小组成员数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupMember", value = "小组成员", dataType = "String", paramType = "query"),
    })
    public ApiResult updateTestGroup(@RequestBody PpmTestGroupInfo ppmTestGroupInfo ) {
        ApiResult<List> result = new ApiResult<>();
        personnerPortaitService.updateTestGroup(ppmTestGroupInfo);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
