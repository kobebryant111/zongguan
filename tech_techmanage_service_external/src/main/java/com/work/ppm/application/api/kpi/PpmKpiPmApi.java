package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.MyKpiDataDto;
import com.work.ppm.application.dto.kpi.MyKpiDbDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoTotalDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.kpi.IPpmKpiPm1Service;
import com.work.ppm.application.services.kpi.IPpmKpiPm2Service;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.services.kpi.IPpmKpiPmService;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiPmApi"},description="考核数据主表")
@RestController
@RequestMapping("ppmKpiPm")
@Slf4j
public class PpmKpiPmApi extends BaseApi {
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
     * 分页
     * @param ppmKpiPmDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiPmDto ppmKpiPmDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiPmService.queryPager(ppmKpiPmDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiPmDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmKpiPmDto> queryDetail(PpmKpiPmDto ppmKpiPmDto) {
        ApiResult<PpmKpiPmDto> result =new ApiResult<>();
        PpmKpiPmDto item = ppmKpiPmService.queryDetail(ppmKpiPmDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiPmDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKpiPm(PpmKpiPmDto ppmKpiPmDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiPmService.updatePpmKpiPm(ppmKpiPmDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmKpiPmDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiPm (PpmKpiPmDto ppmKpiPmDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiPmService.deletePpmKpiPm(ppmKpiPmDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
	/**
	 * 发起员工考核数据
	 * @param ppmKpiPmDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "launchEmployee")
	@ApiOperation(value = "发起员工考核数据", notes = "发起员工考核数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "String", paramType = "query"),

	})
	public ApiResult launchEmployee (PpmKpiPmDto ppmKpiPmDto, HttpServletRequest request) {
		ApiResult result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmService.launchEmployee(ppmKpiPmDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("员工考核成功");
		} else {
			result.setStatus(ApiCode.DELETE_FAIL);
			result.setMessage("员工考核失败");
		}
		return result;
	}

	/**
	 * 发起经理层考核数据
	 * @param ppmKpiPmDto 对象
	 * @param request  对象
	 * @return ApiResult
	 */
	@PostMapping(value = "launchManagers")
	@ApiOperation(value = "发起经理层考核数据", notes = "发起经理层考核数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "String", paramType = "query"),

	})
	public ApiResult launchManagers (PpmKpiPmDto ppmKpiPmDto, HttpServletRequest request) {
		ApiResult result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmService.launchManagers(ppmKpiPmDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("发起经理层考核成功");
		} else {
			result.setStatus(ApiCode.DELETE_FAIL);
			result.setMessage("发起经理层考核失败");
		}
		return result;
	}


	/**
	 * 发起主持经理考核数据
	 * @param ppmKpiPmDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "launchWorkManager")
	@ApiOperation(value = "发起主持经理考核数据", notes = "发起主持经理考核数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "String", paramType = "query"),

	})
	public ApiResult launchWorkManager (PpmKpiPmDto ppmKpiPmDto, HttpServletRequest request) {
		ApiResult result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmService.launchWorkManager(ppmKpiPmDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("发起主持经理考核成功");
		} else {
			result.setStatus(ApiCode.DELETE_FAIL);
			result.setMessage("发起主持经理考核失败");
		}
		return result;
	}


	/**
	 * 获取我的代办
	 * @param myKpiDbDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getMyDb")
	@ApiOperation(value = "获取我的代办", notes = "获取我的代办")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),

			@ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "emp_formulate_kpis_first_half", value = "员工上半年录入的KPI条数",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "emp_formulate_kpis_last_half", value = "员工下半年录入的KPI条数",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "managers_formulate_kpis", value = "经理层全年kpi的制定",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "emp_self_eval_kpis_first_half", value = "员工kpi自评上半年",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "emp_self_eval_kpis_last_half", value = "员工kpi自评下半年",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "managers_self_eval_kpis", value = "经理kpi自评",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "managers_self_eval_kpis_half_year", value = "经理kpi半年度自评",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "emp_kpis_approve_first_half", value = "员工上半年kpi审批",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "emp_kpis_approve_last_half", value = "员工下半年kpi审批",
					dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "managers_kpis_approve", value = "经理层kpi审批",
					dataType = "string", paramType = "query"),
	})
	public ApiResult<MyKpiDbDto> getMyDb(MyKpiDbDto myKpiDbDto, HttpServletRequest request) {
		ApiResult<MyKpiDbDto> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		MyKpiDbDto dto = ppmKpiPmService.getMyDb(myKpiDbDto,user.getUserid());
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}


	/**
	 * 获取考核主表信息/获取考核审批流程信息
	 * @param ppmKpiPmInfoDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPpmKpiPmInfo")
	@ApiOperation(value = "获取考核主表信息/获取考核审批流程信息", notes = "获取考核主表信息/获取考核审批流程信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),

			@ApiImplicitParam(name = "firstcheckname", value = "审批人1姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheckname", value = "审批人2姓名", dataType = "string", paramType = "query"),

	})
	public ApiResult<PpmKpiPmInfoDto> getPpmKpiPmInfo(PpmKpiPmInfoDto ppmKpiPmInfoDto) {
		ApiResult<PpmKpiPmInfoDto> result =new ApiResult<>();
		PpmKpiPmInfoDto dto = ppmKpiPmService.queryKpiPmByCondition(ppmKpiPmInfoDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}



	/**
	 * 提交考核指标项
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitKpiDetail")
	@ApiOperation(value = "提交考核指标项", notes = "提交考核指标项")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),

	})
	public ApiResult<Object> submitKpiDetail(PpmKpiPmDto ppmKpiPmDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmService.submitKpiDetail(ppmKpiPmDto);
		if(id == 1){
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("请先录入指标再提交！");
		} else if(id==2) {
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("提交成功");
		}else if(id==3) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("不是草稿状态，无法提交！");
		}else if(id==4) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("KPI分数不等于100，无法提交！");
		}else if(id==5) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("KPI条数不对，无法提交！");
		}else if(id==6) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("知识能力提升条数不对，无法提交！");
		}else{
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败，请联系管理员！");
		}
		return result;
	}





	/**
	 * 考核指标项审批
	 * @param ppmKpiPmDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "approveKpiDetail")
	@ApiOperation(value = "考核指标项审批", notes = "考核指标项审批")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query",required=true),
	})
	public ApiResult<Object> approveKpiDetail(PpmKpiPmDto ppmKpiPmDto, HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPm1Service.approveKpiDetail(ppmKpiPmDto,user.getUserid());
		if(id == 1){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("审批成功");
		} else if(id==2) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("考核状态有误，无法审批！");
		} else if(id==4) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("KPI分数不等于100，无法审批！");
		}else if(id==5) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("KPI条数不对，无法审批！");
		}else if(id==6) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("知识能力提升条数不对，无法审批！");
		}else{
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败，请联系管理员！");
		}
		return result;
	}


	/**
	 * 考核指标项回退
	 * @param ppmKpiPmDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "rollbackKpiDetail")
	@ApiOperation(value = "考核指标项回退", notes = "考核指标项回退")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query",required=true),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query",required=true),
	})
	public ApiResult<Object> rollbackKpiDetail(PpmKpiPmDto ppmKpiPmDto, HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPm1Service.rollbackKpiDetail(ppmKpiPmDto,user.getUserid());
		if(id == 1){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("回退成功");
		} else if(id==2) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("考核状态有误，无法回退！");
		}else{
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败，请联系管理员！");
		}
		return result;
	}


	/**
	 * 考核审批查询分页
	 * @param ppmKpiPmInfoDto 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "queryApprovePager")
	@ApiOperation(value ="考核审批查询分页", notes = "考核审批查询分页")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query",required=true),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "check", value = "审批人", dataType = "string", paramType = "query"),
			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
	})
	public ApiResult queryApprovePager(PpmKpiPmInfoDto ppmKpiPmInfoDto,Pager pager) {
		ApiResult<List> result =new ApiResult<>();
		ppmKpiPmService.queryApprovePager(ppmKpiPmInfoDto,pager);
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}



	/**
	 * 提交考核指标项自评
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitSelfEval")
	@ApiOperation(value = "提交考核指标项自评", notes = "提交考核指标项自评")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),

	})
	public ApiResult<Object> submitSelfEval(PpmKpiPmDto ppmKpiPmDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPm1Service.submitSelfEval(ppmKpiPmDto);
		if(id == 0){
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("你还有KPI的自评未填写！");
		} else if(id==-1) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("你还有知识技能提升的自评未填写");
		}else if(id==1) {
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("提交成功");
		}else{
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败，请联系管理员！");
		}
		return result;
	}


	/**
	 * 考核查询页面汇总数据查询
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 * @param pager 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPpmKpiPmInfoSummary")
	@ApiOperation(value = "考核查询页面汇总数据查询", notes = "考核查询页面汇总数据查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),

			@ApiImplicitParam(name = "firstcheckname", value = "审批人1姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheckname", value = "审批人2姓名", dataType = "string", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),

	})
	public ApiResult getPpmKpiPmInfoSummary(PpmKpiPmInfoDto ppmKpiPmInfoDto,Pager pager, HttpServletRequest request) {
		ApiResult<List> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ppmKpiPm1Service.getPpmKpiPmInfoSummaryPager(ppmKpiPmInfoDto,pager,user.getUserid());
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}


    /**
     * 发起经理半年度KPI考核自评
     * @param ppmKpiPmDetailDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "launchSelfEval")
    @ApiOperation(value = "发起经理半年度KPI考核自评", notes = "发起经理半年度KPI考核自评")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query",required = true),

    })
    public ApiResult launchSelfEval (PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiPm1Service.launchSelfEval(ppmKpiPmDetailDto.getTyear());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("发起经理半年度KPI考核自评成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("发起经理半年度KPI考核自评失败");
        }
        return result;
    }


	/**
	 * 提交经理半年度考核自评
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitSelfEvalHalfSelf")
	@ApiOperation(value = "提交经理半年度考核自评", notes = "提交经理半年度考核自评")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),

	})
	public ApiResult<Object> submitSelfEvalHalfSelf(PpmKpiPmDto ppmKpiPmDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPm1Service.submitSelfEvalHalfSelf(ppmKpiPmDto);
		if(id == 0){
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("自评未完成！");
		} else if(id==1) {
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("提交成功");
		}else{
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败，请联系管理员！");
		}
		return result;
	}


	/**
	 * 获取我的考核数据
	 * @param myKpiDataDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getMyKpi")
	@ApiOperation(value = "获取我的考核数据", notes = "获取我的考核数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),

			@ApiImplicitParam(name = "ppmKpiRoleInfoDto", value = "我的考核角色数据角色",
					dataType = "PpmKpiRoleInfoDto", paramType = "query"),
			@ApiImplicitParam(name = "emp_kpis_first_half", value = "我的考核员工上半年数据",
					dataType = "PpmKpiPmInfoDto", paramType = "query"),
			@ApiImplicitParam(name = "emp_kpis_last_half", value = "我的考核员工下半年数据",
					dataType = "PpmKpiPmInfoDto", paramType = "query"),
			@ApiImplicitParam(name = "manager_kpis", value = "我的考核经理层数据",
					dataType = "PpmKpiPmInfoDto", paramType = "query"),
			@ApiImplicitParam(name = "my_kpis_year_eval", value = "我的考核年度评价数据",
					dataType = "PpmKpiPmInfoDto", paramType = "query"),

	})
	public ApiResult<MyKpiDataDto> getMyKpi(MyKpiDataDto myKpiDataDto, HttpServletRequest request) {
		ApiResult<MyKpiDataDto> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		String secertKey= CryptoUtil.encoderByMd5("getMyKpi"+myKpiDataDto.getEid());
		MyKpiDataDto dto =null;
		if(secertKey.equals(myKpiDataDto.getSecertkey())){
			dto=ppmKpiPm1Service.getMyKpi(myKpiDataDto);
		}
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}



	/**
	 * 获得考核主项的统计项
	 * @param ppmKpiPmInfoTotalDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPpmKpiPmInfoTotalDto")
	@ApiOperation(value = "获得考核主项的统计项", notes = "获得考核主项的统计项")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),

			@ApiImplicitParam(name = "firstcheckname", value = "审批人1姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheckname", value = "审批人2姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheckname", value = "审批人2姓名", dataType = "string", paramType = "query"),


			@ApiImplicitParam(name = "kpsItems", value = "kpi考核项", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "finishItems", value = "已完成考核项", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalItems", value = "进行中考核项", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "abilityItems", value = "能力项提升考核项", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "configureItems", value = "配置中考核项", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "approveItems", value = "审批中考核项", dataType = "long", paramType = "query"),

	})
	public ApiResult<PpmKpiPmInfoTotalDto> getPpmKpiPmInfoTotalDto(PpmKpiPmInfoTotalDto ppmKpiPmInfoTotalDto) {
		ApiResult<PpmKpiPmInfoTotalDto> result =new ApiResult<>();
		PpmKpiPmInfoTotalDto dto = ppmKpiPm1Service.getPpmKpiPmInfoTotalDto(ppmKpiPmInfoTotalDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}


	/**
	 * 员工考核数据导出
	 * @param ppmKpiPmInfoDto  对象
	 * @param request  对象
	 * @param response  对象
	 */
	@PostMapping("exportKpiDatas")
	@ApiOperation(value = "员工考核数据导出")
	public void exportKpiDatas(PpmKpiPmInfoDto ppmKpiPmInfoDto,HttpServletRequest request ,HttpServletResponse response) {
		ApiResult result = new ApiResult();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		List<PpmKpiPmInfoDto> list = ppmKpiPm1Service.getPpmKpiPmInfoSummaryList(ppmKpiPmInfoDto,user.getUserid());
		ExcelUtil.exportExcel(list,PpmKpiPmInfoDto.class,"员工考核数据.xls",response);
		result.success("员工考核数据成功");
	}

	/**
	 * 获取考核年份下拉框
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPpmKpiPmTyears")
	@ApiOperation(value = "获取考核年份下拉框", notes = "获取考核年份下拉框")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query",required = true),

	})
	public ApiResult<List<PpmKpiPmDto>> getPpmKpiPmTyears(PpmKpiPmDto ppmKpiPmDto) {
		ApiResult<List<PpmKpiPmDto>> result =new ApiResult<>();
		List<PpmKpiPmDto> dto = ppmKpiPm1Service.getPpmKpiPmTyears(ppmKpiPmDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}




	/**
	 * 查询考核评价分页信息
	 * @param ppmKpiPmInfoDto 对象
	 * @param pager 对象
	 * @param request  对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPpmKpiPmEvalInfoPage")
	@ApiOperation(value = "查询考核评价分页信息", notes = "查询考核评价分页信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "审批人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "审批人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstchecktime", value = "审批时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondchecktime", value = "审批时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "评价人1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "评价人2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "评价时间1", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "评价时间2", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "rollbacksuggest", value = "回退意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "firstcomment", value = "初评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcomment", value = "终评人评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcommenttime", value = "初评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondcommenttime", value = "终评人评语时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tgrade", value = "总等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalby1score", value = "初评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "evalby2score", value = "终评人评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "职位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstorder", value = "初次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "secondorder", value = "终次排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "kpiscore", value = "KPI分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "comprehensivescore", value = "综合分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "performanceevaluationscore", value = "履职评估分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "balancescore", value = "平衡计分分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "abilityimporvescore", value = "能力提升分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "adjustlevel", value = "调整等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adjustreason", value = "调整原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teamorder", value = "团队排名", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "teampercentage", value = "团队占比", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcheckname", value = "审批人1姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheckname", value = "审批人2姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "evalitems", value = "待评价项", dataType = "string", paramType = "query"),
			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),

	})
	public ApiResult getPpmKpiPmEvalInfoPage(PpmKpiPmInfoDto ppmKpiPmInfoDto,Pager pager, HttpServletRequest request) {
		ApiResult<List> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ppmKpiPm1Service.getPpmKpiPmEvalInfoPage(ppmKpiPmInfoDto,pager,user.getUserid());
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}


	/**
	 * 老应用获取我的代办
	 * @param myKpiDbDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getMyDbOld")
	@ApiOperation(value = "老应用获取我的代办", notes = "老应用获取我的代办")
	public ApiResult<MyKpiDbDto> getMyDbOld(@RequestBody MyKpiDbDto myKpiDbDto ) {
		ApiResult<MyKpiDbDto> result =new ApiResult<>();
		MyKpiDbDto dto = ppmKpiPmService.getMyDb(myKpiDbDto,myKpiDbDto.getEid());
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}
	/**
	 * 考核整体提交
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "totalSubmit")
	@ApiOperation(value = "考核整体提交", notes = "考核整体提交")
	public ApiResult<Object> totalSubmit(PpmKpiPmInfoDto ppmKpiPmInfoDto,HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmService.totalSubmit(ppmKpiPmInfoDto,user.getUserid());
		if(id == 1){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("提交成功");
		}
		return result;
	}
}
