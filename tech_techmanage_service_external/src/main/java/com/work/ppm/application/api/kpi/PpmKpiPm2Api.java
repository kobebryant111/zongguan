package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.KpiLevelPersons;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.kpi.IPpmKpiPm1Service;
import com.work.ppm.application.services.kpi.IPpmKpiPm2Service;
import com.work.ppm.application.services.kpi.IPpmKpiPmService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiPmApi"},description="考核数据主表")
@RestController
@RequestMapping("ppmKpiPm2")
@Slf4j
public class PpmKpiPm2Api extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiPmService ppmKpiPmService;

	/**
	 * 类接口描述
	 */
	@Autowired
	private IPpmKpiPm1Service ppmKpiPm1Service;

	/**
	 * 类接口描述
	 */
	@Autowired
	private IPpmKpiPm2Service ppmKpiPm2Service;

	/**
	 * 发起综合能力评价成功
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "launchComprehensive")
	@ApiOperation(value = "发起综合能力评价成功", notes = "发起综合能力评价成功")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "String", paramType = "query"),

	})
	public ApiResult launchComprehensive ( PpmKpiPmDto ppmKpiPmDto) {
		ApiResult result =new ApiResult<>();
		int id = ppmKpiPm2Service.launchComprehensive(ppmKpiPmDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("发起综合能力评价成功");
		} else {
			result.setStatus(ApiCode.DELETE_FAIL);
			result.setMessage("发起综合能力评价失败");
		}
		return result;
	}

	/**
	 * 发起总体评语成功
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "launchComment")
	@ApiOperation(value = "发起总体评语", notes = "发起总体评语")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "String", paramType = "query"),

	})
	public ApiResult launchComment ( PpmKpiPmDto ppmKpiPmDto) {
		ApiResult result =new ApiResult<>();
		int id = ppmKpiPm2Service.launchComment(ppmKpiPmDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("发起总体评语成功");
		} else {
			result.setStatus(ApiCode.DELETE_FAIL);
			result.setMessage("发起总体评语失败");
		}
		return result;
	}

	/**
	 * 发起经理Kpi自评
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "launchDepManagersSelfEval")
	@ApiOperation(value = "发起经理Kpi自评", notes = "发起经理Kpi自评")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "String", paramType = "query"),

	})
	public ApiResult launchDepManagersSelfEval ( PpmKpiPmDto ppmKpiPmDto) {
		ApiResult result =new ApiResult<>();
		int id = ppmKpiPm2Service.launchDepManagersSelfEval(ppmKpiPmDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("发起经理Kpi自评成功");
		} else {
			result.setStatus(ApiCode.DELETE_FAIL);
			result.setMessage("发起经理Kpi自评失败");
		}
		return result;
	}


	/**
	 * 获取未提交的中心数据
	 * @param ppmKpiLevelPersonInfoDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getNoSubmitCenterTip")
	@ApiOperation(value = "获取未提交的中心数据", notes = "获取未提交的中心数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentCens", value = "当前中心", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "setCens", value = "设置的中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "noSubmitCenterName", value = "未提交的中心名称", dataType = "string", paramType = "query"),

	})
	public ApiResult<KpiLevelPersons> getNoSubmitCenterTip(PpmKpiLevelPersonInfoDto
																			ppmKpiLevelPersonInfoDto,
															  HttpServletRequest request) {
		ApiResult<KpiLevelPersons> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		KpiLevelPersons dtos = ppmKpiPm2Service.getNoSubmitCenterTip(ppmKpiLevelPersonInfoDto,user.getUserid());
		result.setResults(dtos);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}
	/**
	 * 获取未提交的部门数据
	 * @param ppmKpiLevelPersonInfoDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getNoSubmitDepTip")
	@ApiOperation(value = "获取未提交的部门数据", notes = "获取未提交的部门数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentDeps", value = "当前部门", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "setDeps", value = "设置的部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "noSubmitDepName", value = "未提交的部门名称", dataType = "string", paramType = "query"),

	})
	public ApiResult<KpiLevelPersons> getNoSubmitDepTip(PpmKpiLevelPersonInfoDto
																   ppmKpiLevelPersonInfoDto,
														   HttpServletRequest request) {
		ApiResult<KpiLevelPersons> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		KpiLevelPersons dtos = ppmKpiPm2Service.getNoSubmitDepTip(ppmKpiLevelPersonInfoDto,user.getUserid());
		result.setResults(dtos);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

}
