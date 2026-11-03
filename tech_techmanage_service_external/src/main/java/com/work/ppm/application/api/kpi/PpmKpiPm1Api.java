package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.CheckEvalItemsSummary;
import com.work.ppm.application.dto.kpi.KpiLevelPersons;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmBalanceSummaryDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiRoleDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.kpi.IPpmKpiPm1Service;
import com.work.ppm.application.services.kpi.IPpmKpiPm2Service;
import com.work.ppm.application.services.kpi.IPpmKpiPmService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiPmApi"},description="考核数据主表")
@RestController
@RequestMapping("ppmKpiPm1")
@Slf4j
public class PpmKpiPm1Api extends BaseApi {
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
	 * 提交经理半年度考核自评
	 * @param ppmKpiPmDto 对象
	 * @param ppmKpiRoleDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "empUpdateRole")
	@ApiOperation(value = "员工自己更新角色时需要更新自己相对应的考核数据", notes = "员工自己更新角色时需要更新自己相对应的考核数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query",required = true),

			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),

	})
	public ApiResult<Object> empUpdateRole(PpmKpiPmDto ppmKpiPmDto, PpmKpiRoleDto ppmKpiRoleDto,
										   HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPm1Service.empUpdateRole(ppmKpiPmDto, ppmKpiRoleDto, user.getUserid());
		if(id == 1){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("更新成功");
		}else{
			result.setStatus(ApiCode.FAIL);
			result.setMessage("更新成功");
		}
		return result;
	}


	/**
	 * 提交kpi评价
	 * @param ppmKpiPmDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitKpiEval")
	@ApiOperation(value = "提交kpi评价", notes = "提交kpi评价")
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
	public ApiResult<Object> submitKpiEval(PpmKpiPmDto ppmKpiPmDto,HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPm1Service.submitKpiEval(ppmKpiPmDto,user.getUserid());
		if(id == 0){
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("评价关系不对！");
		} else if(id==1) {
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("提交成功");
		}else if(id==2) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("没有可提交的数据！");
		}else if(id==3) {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("评价未完成！");
		}else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("状态不对！");
		}
		return result;
	}

    /**
     * 获得考核评价TAB页的统计项
     * @param checkEvalItemsSummary 对象
     * @return ApiResult
     */
    @PostMapping(value = "getCheckEvalItemsSummary")
    @ApiOperation(value = "获得考核评价TAB页的统计项", notes = "获得考核评价TAB页的统计项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itemstatus", value = "状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "check", value = "审批评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "kpitype", value = "类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "kpi_items", value = "kpi项目数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pid", value = "主表主键", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "comprehensiveAbility_items", value = "综合能力评估项目数",
					dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "postEvaluation_items", value = "履职评估项目数", dataType = "date", paramType = "query"),
    })
    public ApiResult<CheckEvalItemsSummary> getCheckEvalItemsSummary(CheckEvalItemsSummary checkEvalItemsSummary) {
        ApiResult<CheckEvalItemsSummary> result =new ApiResult<CheckEvalItemsSummary>();
        CheckEvalItemsSummary dto = ppmKpiPm1Service.getCheckEvalItemsSummary(checkEvalItemsSummary);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


	/**
	 * 获得平衡计分评价汇总数据
	 * @param ppmKpiPmBalanceSummaryDto 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getBalanceSummaryInfoPage")
	@ApiOperation(value ="获得平衡计分评价汇总数据", notes = "获得平衡计分评价汇总数据")
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
			@ApiImplicitParam(name = "decitems", value = "扣分项", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "decValues", value = "总扣分", dataType = "double", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
	})
	public ApiResult getBalanceSummaryInfoPage(PpmKpiPmBalanceSummaryDto ppmKpiPmBalanceSummaryDto,Pager pager) {
		ApiResult<List> result =new ApiResult<>();
		ppmKpiPm1Service.getBalanceSummaryInfoPage(ppmKpiPmBalanceSummaryDto,pager);
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}

	/**
	 * 平衡计分汇总扣分数据导出
	 * @param ppmKpiPmBalanceSummaryDto  对象
	 * @param response  对象
	 */
	@PostMapping("exportBalanceSummary")
	@ApiOperation(value = "平衡计分汇总扣分数据导出")
	public void exportBalanceSummary(PpmKpiPmBalanceSummaryDto ppmKpiPmBalanceSummaryDto, HttpServletResponse response) {
		ApiResult result = new ApiResult();
		List<PpmKpiPmBalanceSummaryDto> list = ppmKpiPm1Service.getBalanceSummaryInfoPage(ppmKpiPmBalanceSummaryDto);
		ExcelUtil.exportExcel(list,PpmKpiPmBalanceSummaryDto.class,"平衡计分汇总扣分数据.xls",response);
		result.success("平衡计分汇总扣分数据成功");
	}



	/**
	 * 新员工考核数据导出
	 * @param ppmKpiPmInfoDto  对象
	 * @param request  对象
	 * @param response  对象
	 */
	@PostMapping("exportEmpKpiDatas")
	@ApiOperation(value = "新员工考核数据导出")
	public void exportEmpKpiDatas(PpmKpiPmInfoDto ppmKpiPmInfoDto,HttpServletRequest request ,
								  HttpServletResponse response) {
		ApiResult result = new ApiResult();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		List<PpmKpiPmInfoDto> list = ppmKpiPm2Service.getEmpKpiDataList(ppmKpiPmInfoDto,user.getUserid());
		ExcelUtil.exportExcel(list,PpmKpiPmInfoDto.class,"员工考核数据.xls",response);
		result.success("员工考核数据成功");
	}


	/**
	 * 新考核查询页面汇总数据查询
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getEmpKpiDataPager")
	@ApiOperation(value = "新考核查询页面汇总数据查询", notes = "新考核查询页面汇总数据查询")
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
	public ApiResult getEmpKpiDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto,Pager pager, HttpServletRequest request) {
		ApiResult<List> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ppmKpiPm2Service.getEmpKpiDataPager(ppmKpiPmInfoDto,pager,user.getUserid());
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}


	/**
	 * 员工修改状态查询分页
	 * @param ppmKpiPmInfoDto 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "queryKpiPmByConditionPager")
	@ApiOperation(value ="员工修改状态查询分页", notes = "员工修改状态查询分页")
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
	public ApiResult queryKpiPmByConditionPager(PpmKpiPmInfoDto ppmKpiPmInfoDto,Pager pager) {
		ApiResult<List> result =new ApiResult<>();
		ppmKpiPm2Service.queryKpiPmByConditionPager(ppmKpiPmInfoDto,pager);
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}


	/**
	 * 新员工考核数据导出
	 * @param ppmKpiPmInfoDto  对象
	 * @param response  对象
	 */
	@PostMapping("exportKpiPmByCondition")
	@ApiOperation(value = "员工修改状态查询导出")
	public void exportKpiPmByCondition(PpmKpiPmInfoDto ppmKpiPmInfoDto,HttpServletResponse response) {
		ApiResult result = new ApiResult();
		List<PpmKpiPmInfoDto> list = ppmKpiPm2Service.queryKpiPmByConditionList(ppmKpiPmInfoDto);
		ExcelUtil.exportExcel(list,PpmKpiPmInfoDto.class,"员工考核数据.xls",response);
		result.success("员工考核数据成功");
	}


	/**
	 * 查询考核履职评估分页信息
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPostEvalInfoSummaryPager")
	@ApiOperation(value = "查询考核履职评估分页信息（下级评级上级）", notes = "查询考核履职评估分页信息（下级评级上级）")
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
	public ApiResult getPostEvalInfoSummaryPager(PpmKpiPmInfoDto ppmKpiPmInfoDto,Pager pager, HttpServletRequest request) {
		ApiResult<List> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ppmKpiPm2Service.getPostEvalInfoSummaryPager(ppmKpiPmInfoDto,pager,user.getUserid());
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}


	/**
	 * 发起履职评估
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "launchPostEvaluationEval")
	@ApiOperation(value = "发起履职评估", notes = "发起履职评估")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "String", paramType = "query"),

	})
	public ApiResult launchPostEvaluationEval ( PpmKpiPmDto ppmKpiPmDto) {
		ApiResult result =new ApiResult<>();
		int id = ppmKpiPm2Service.launchPostEvaluationEval(ppmKpiPmDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("发起履职评估成功");
		} else {
			result.setStatus(ApiCode.DELETE_FAIL);
			result.setMessage("发起履职评估失败");
		}
		return result;
	}


	/**
	 * 生成年度数据
	 * @param ppmKpiPmDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "launchYearData")
	@ApiOperation(value = "生成年度数据", notes = "生成年度数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
	})
	public ApiResult launchYearData ( PpmKpiPmDto ppmKpiPmDto, HttpServletRequest request) {
		ApiResult result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPm2Service.launchYearData(ppmKpiPmDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("生成年度数据成功");
		} else {
			result.setStatus(ApiCode.DELETE_FAIL);
			result.setMessage("生成年度数据失败");
		}
		return result;
	}




	/**
	 * 获得部门年度确认分页数据
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getDepYearDataPager")
	@ApiOperation(value = "获得部门年度确认分页数据", notes = "获得部门年度确认分页数据")
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
			@ApiImplicitParam(name = "deptscore", value = "二级部总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "deptlevel", value = "二级部等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deporder", value = "二级部排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "depremark", value = "部门备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centertlevel", value = "中心等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerorder", value = "中心排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "centerremark", value = "中心备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initialtscore", value = "初始分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "initialtlevel", value = "初始等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initialorder", value = "初始排名", dataType = "long", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),

	})
	public ApiResult getDepYearDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto,Pager pager, HttpServletRequest request) {
		ApiResult<List> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ppmKpiPm2Service.getDepYearDataPager(ppmKpiPmInfoDto,pager,user.getUserid());
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}
	/**
	 * 提交部门年度评价数据
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitDepYearConfirm")
	@ApiOperation(value = "提交部门年度评价数据", notes = "提交部门年度评价数据")
	public ApiResult<Object> submitDepYearConfirm(HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPm2Service.submitDepYearConfirm(user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("提交成功");
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败！");
		}
		return result;
	}

	/**
	 * 部门添加分数
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "addScore")
	@ApiOperation(value = "部门添加分数", notes = "部门添加分数")
	public ApiResult<Object> addScore(PpmKpiPmInfoDto ppmKpiPmInfoDto,HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPm2Service.addScore(ppmKpiPmInfoDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("添加成功");
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("添加失败！");
		}
		return result;
	}

	/**
	 * 获得中心年度初评分页数据
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getCenterYearDataPager")
	@ApiOperation(value = "获得中心年度初评分页数据", notes = "获得中心年度初评分页数据")
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
			@ApiImplicitParam(name = "deptscore", value = "二级部总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "deptlevel", value = "二级部等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deporder", value = "二级部排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "depremark", value = "部门备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centertlevel", value = "中心等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerorder", value = "中心排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "centerremark", value = "中心备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initialtscore", value = "初始分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "initialtlevel", value = "初始等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initialorder", value = "初始排名", dataType = "long", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),

	})
	public ApiResult getCenterYearDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto,Pager pager, HttpServletRequest request) {
		ApiResult<List> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ppmKpiPm2Service.getCenterYearDataPager(ppmKpiPmInfoDto,pager,user.getUserid());
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}

	/**
	 * 中心更新等级
	 * @param ppmKpiPmInfoDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "updateCenterTlevel")
	@ApiOperation(value = "中心更新等级", notes = "中心更新等级")
	public ApiResult<Object> updateCenterTlevel(PpmKpiPmInfoDto ppmKpiPmInfoDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPm2Service.updateCenterTlevel(ppmKpiPmInfoDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("更新成功");
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败！");
		}
		return result;
	}

	/**
	 * 提交中心年度评价数据
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitCenterYearEval")
	@ApiOperation(value = "提交中心年度评价数据", notes = "提交中心年度评价数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "role", value = "年份", dataType = "String", paramType = "query"),
	})
	public ApiResult<Object> submitCenterYearEval(PpmKpiPmInfoDto ppmKpiPmInfoDto,HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPm2Service.submitCenterYearEval(ppmKpiPmInfoDto,user.getUserid());
		if(id ==2){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("提交成功");
		} else if(id ==1){

			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("比例人数校验失败！");
		}else if(id ==0){

			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("未评价员工等级！");
		}else {

			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败！");
		}
		return result;
	}


	/**
	 * 获得科技部年度初评分页数据
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 *  @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getTecYearDataPager")
	@ApiOperation(value = "获得科技部年度初评分页数据", notes = "获得科技部年度初评分页数据")
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
			@ApiImplicitParam(name = "deptscore", value = "二级部总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "deptlevel", value = "二级部等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deporder", value = "二级部排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "depremark", value = "部门备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centertlevel", value = "中心等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerorder", value = "中心排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "centerremark", value = "中心备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initialtscore", value = "初始分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "initialtlevel", value = "初始等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initialorder", value = "初始排名", dataType = "long", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),

	})
	public ApiResult getTecYearDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto,Pager pager, HttpServletRequest request) {
		ApiResult<List> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		ppmKpiPm2Service.getTecYearDataPager(ppmKpiPmInfoDto,pager,user.getUserid());
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}


	/**
	 * 提交中心年度评价数据
	 * @param ppmKpiPmInfoDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitTecYearEval")
	@ApiOperation(value = "提交科技部年度评价数据", notes = "提交科技部年度评价数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "role", value = "年份", dataType = "String", paramType = "query"),
	})
	public ApiResult<Object> submitTecYearEval(PpmKpiPmInfoDto ppmKpiPmInfoDto,HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPm2Service.submitTecYearEval(ppmKpiPmInfoDto,user.getUserid());
		if(id ==2){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("提交成功");
		} else if(id ==1){

			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("比例人数校验失败！");
		}else if(id ==0){

			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("未评价员工等级！");
		}else {

			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败！");
		}
		return result;
	}

	/**
	 * 科技部更新等级
	 * @param ppmKpiPmInfoDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "updateTecTlevel")
	@ApiOperation(value = "科技部更新等级", notes = "科技部更新等级")
	public ApiResult<Object> updateTecTlevel(PpmKpiPmInfoDto ppmKpiPmInfoDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPm2Service.updateTecTlevel(ppmKpiPmInfoDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("更新成功");
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败！");
		}
		return result;
	}

	/**
	 * 获取当前比例人数提示
	 * @param ppmKpiLevelPersonInfoDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getLevelPersonTip")
	@ApiOperation(value = "获取当前比例人数提示", notes = "获取当前比例人数提示")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "otype", value = "类型（1：二级部，2：中心，3：一级部）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "role", value = "应用角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tlevel", value = "等级A。。。", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "nums", value = "人数", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
	})
	public ApiResult<List<KpiLevelPersons>> getLevelPersonTip(PpmKpiLevelPersonInfoDto
																		  ppmKpiLevelPersonInfoDto,
															  HttpServletRequest request) {
		ApiResult<List<KpiLevelPersons>> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		List<KpiLevelPersons> dtos = ppmKpiPm2Service.getLevelPersonTip(ppmKpiLevelPersonInfoDto,user.getUserid());
		result.setResults(dtos);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}
	/**
	 * 获取中心当前比例人数提示
	 * @param ppmKpiLevelPersonInfoDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getCenterLevelPersonTip")
	@ApiOperation(value = "获取中心当前比例人数提示", notes = "获取中心当前比例人数提示")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "otype", value = "类型（1：二级部，2：中心，3：一级部）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "role", value = "应用角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tlevel", value = "等级A。。。", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "nums", value = "人数", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
	})
	public ApiResult<List<KpiLevelPersons>> getCenterLevelPersonTip(PpmKpiLevelPersonInfoDto
																				ppmKpiLevelPersonInfoDto,
																	HttpServletRequest request) {
		ApiResult<List<KpiLevelPersons>> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		List<KpiLevelPersons> dtos = ppmKpiPm2Service.getCenterLevelPersonTip(ppmKpiLevelPersonInfoDto,user.getUserid());
		result.setResults(dtos);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}


	/**
	 * 部门考核状态显示
	 * @param ppmKpiPmDto 对象
	 *  @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getDepStatusPager")
	@ApiOperation(value = "部门考核状态显示", notes = "部门考核状态显示")
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
			@ApiImplicitParam(name = "deptscore", value = "二级部总分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "deptlevel", value = "二级部等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deporder", value = "二级部排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "depremark", value = "部门备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centertlevel", value = "中心等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerorder", value = "中心排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "centerremark", value = "中心备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initialtscore", value = "初始分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "initialtlevel", value = "初始等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initialorder", value = "初始排名", dataType = "long", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),

	})
	public ApiResult getDepStatusPager(PpmKpiPmDto ppmKpiPmDto,Pager pager) {
		ApiResult<List> result =new ApiResult<>();
		ppmKpiPm2Service.getDepStatusPager(ppmKpiPmDto,pager);
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}

	/**
	 * 更新部门状态数据
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "updateDepStatus")
	@ApiOperation(value = "更新部门状态数据", notes = "更新部门状态数据")
	public ApiResult<Object> updateDepStatus(PpmKpiPmDto ppmKpiPmDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPm2Service.updateDepStatus(ppmKpiPmDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("更新成功");
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败！");
		}
		return result;
	}
}
