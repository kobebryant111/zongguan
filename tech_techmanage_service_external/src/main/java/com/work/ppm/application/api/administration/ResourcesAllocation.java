package com.work.ppm.application.api.administration;

import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.CommRdEmployeeDto;
import com.work.ppm.application.services.administration.ResourcesAllocationServiceImpl;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 研发项目人员
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"ResourcesAllocation"}, description = "研发人力资源管理模块")
@RestController
@RequestMapping("hrResourcesAllocation")
@Slf4j
@Validated
public class ResourcesAllocation extends BaseApi {
    @Autowired
    private ResourcesAllocationServiceImpl resourcesAllocationService;

    @PostMapping(value = "editAttenStatus")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> queryPager(@RequestBody PpmKqRdCalendar ppmKqRdCalendar ) {
        ApiResult<List> result = new ApiResult<>();
        resourcesAllocationService.editAttenStatus(ppmKqRdCalendar);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
