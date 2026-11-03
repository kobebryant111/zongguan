package com.work.ppm.application.api.projectmanager;

import com.work.ppm.application.dto.projectmanager.AapSystemPropertyDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.projectmanager.IAapSystemPropertyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统属性
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"AapSystemPropertyApi"},description="系统属性")
@RestController
@RequestMapping("aapSystemProperty")
@Slf4j
public class AapSystemPropertyApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IAapSystemPropertyService aapSystemPropertyService;



    /**
     * 查询系统列表
     * @param request 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryList")
    @ApiOperation(value = "查询系统列表", notes = "查询系统列表")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemid", value = "系统", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tecFzzgUser", value = "科技负责主管A_20170120_000108",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tecdep", value = "科技责任部门A_20160426_000083",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "admina", value = "系统管理员A岗A_20160426_000086",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adminb", value = "系统管理员B岗A_20160426_000087",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "configuser", value = "系统级配置管理员A_20210128_000661",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jgywuser", value = "系统架构组运维人员A_20210119_000651",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "yfmanagera", value = "系统研发负责人A岗A_20200507_000391",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "yfmanagerb", value = "系统研发负责人B岗A_20200507_000392",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywywuser", value = "系统业务运维人员A_20210204_000671",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywmanagera", value = "系统运维负责人A岗A_20160331_000051",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywmanagerb", value = "系统运维负责人B岗A_20160426_000085",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "zmanager", value = "系统总负责人A_20210118_000641",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywlinkman", value = "业务联系人A_20160426_000088",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busdep", value = "业务主管部门A_20160426_000084",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywfzruser", value = "运维负责人（甲乙类系统）A_20210302_000711",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "collectdep", value = "被上收部门A_20210302_000721",
					dataType = "string", paramType = "query"),
			
    })
    public ApiResult<List<AapSystemPropertyDto>> queryList(HttpServletRequest request) {
        ApiResult<List<AapSystemPropertyDto>> result =new ApiResult<>();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        List<AapSystemPropertyDto> item = aapSystemPropertyService.queryList(user.getUserid());
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


}
