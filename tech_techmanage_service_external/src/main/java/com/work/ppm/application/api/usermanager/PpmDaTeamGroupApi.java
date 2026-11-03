package com.work.ppm.application.api.usermanager;

import com.work.ppm.application.dto.usermanager.PpmDaTeamGroupDto;
import com.work.ppm.application.services.usermanager.IPpmDaTeamGroupService;
import com.work.ppm.commons.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *团队分组管理
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDaTeamGroupApi"}, description = "团队分组管理")
@RestController
@RequestMapping("ppmDaTeamGroup")
@Slf4j
public class PpmDaTeamGroupApi {
    /**
     * 团队分组管理
     */
    @Autowired
    private IPpmDaTeamGroupService ppmDaTeamGroupService;

    /**
     * 根据部门编号查询小组
     * @param ppmDaTeamGroupDto 对象
     * @return  ApiResult<List<PpmDaTeamGroupDto>>
     */
    @GetMapping("queryGroup")
    @ApiOperation(value = "根据部门编号查询小组")
    @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query")
    public ApiResult<List<PpmDaTeamGroupDto>> queryGroup(PpmDaTeamGroupDto ppmDaTeamGroupDto) {
        ApiResult<List<PpmDaTeamGroupDto>> result = new ApiResult<>();
        List<PpmDaTeamGroupDto> list = ppmDaTeamGroupService.queryGroup(ppmDaTeamGroupDto.getDepid());
        result.success("获取成功");
        result.setResults(list);
        return result;
    }
}
