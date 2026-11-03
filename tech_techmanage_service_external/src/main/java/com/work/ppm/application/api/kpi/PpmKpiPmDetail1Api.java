package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.PostEvaluationEvalDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.kpi.IPpmKpiPmDetailService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiPmDetailApi"},description="考核数据指标项表")
@RestController
@RequestMapping("ppmKpiPmDetail1")
@Slf4j
public class PpmKpiPmDetail1Api extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiPmDetailService ppmKpiPmDetailService;

	/**
	 * 综合能力，履职评估，平衡计分统计评价数据
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "queryPpmKpiPmDetailEvalGroup")
	@ApiOperation(value = "综合能力，履职评估，平衡计分统计评价数据", notes = "综合能力，履职评估，平衡计分统计评价数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatusname", value = "详细项状态名称", dataType = "string", paramType = "query"),
	})
	public ApiResult<List<PpmKpiPmDetailDto>> queryPpmKpiPmDetailEvalGroup(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
		ApiResult<List<PpmKpiPmDetailDto>> result =new ApiResult<>();
		List<PpmKpiPmDetailDto> dto = ppmKpiPmDetailService.queryPpmKpiPmDetailEvalGroup(ppmKpiPmDetailDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}


	/**
	 * 综合能力整体提交
	 * @param ppmKpiPmDetailDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitTotalComprehensiveAbilityEval")
	@ApiOperation(value = "综合能力整体提交", notes = "综合能力整体提交")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatusname", value = "详细项状态名称", dataType = "string", paramType = "query"),
	})
	public ApiResult<Object> submitTotalComprehensiveAbilityEval(
			PpmKpiPmDetailDto ppmKpiPmDetailDto, HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.submitTotalComprehensiveAbilityEval(
				ppmKpiPmDetailDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败");
		}
		return result;
	}


	/**
	 * 保存评语
	 * @param ppmKpiPmDetailDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "saveWholeComment")
	@ApiOperation(value = "保存评语", notes = "保存评语")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),

	})
	public ApiResult<Object> saveWholeComment(PpmKpiPmDetailDto ppmKpiPmDetailDto, HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.saveWholeComment(ppmKpiPmDetailDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("保存失败");
		}
		return result;
	}

	/**
	 * 员工考核_状态修改_综合能力，履职评估，平衡计分统计数据
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "queryPpmKpiPmDetailInfoGroup")
	@ApiOperation(value = "员工考核_状态修改_综合能力，履职评估，平衡计分统计数据", notes = "员工考核_状态修改_综合能力，履职评估，平衡计分统计数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatusname", value = "详细项状态名称", dataType = "string", paramType = "query"),
	})
	public ApiResult<List<PpmKpiPmDetailInfoDto>> queryPpmKpiPmDetailInfoGroup(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
		ApiResult<List<PpmKpiPmDetailInfoDto>> result =new ApiResult<>();
		List<PpmKpiPmDetailInfoDto> dto = ppmKpiPmDetailService.queryPpmKpiPmDetailInfoGroup(ppmKpiPmDetailDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

	/**
	 * 员工考核_状态修改_获取考核考核项数据
	 * @param ppmKpiPmDetailInfoDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getDetailItemList")
	@ApiOperation(value = "员工考核_状态修改_获取考核考核项数据", notes = "员工考核_状态修改_获取考核考核项数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态名称", dataType = "string", paramType = "query"),
	})
	public ApiResult<List<PpmKpiPmDetailInfoDto>> getDetailItemList(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
		ApiResult<List<PpmKpiPmDetailInfoDto>> result =new ApiResult<>();
		List<PpmKpiPmDetailInfoDto> dto = ppmKpiPmDetailService.getDetailItemList(ppmKpiPmDetailInfoDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}






	/**
	 * 后台状态修改多条
	 * @param ppmKpiPmDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "backStageUpdateListStatus")
	@ApiOperation(value = "后台状态修改多条", notes = "后台状态修改多条")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),

	})
	public ApiResult<Object> backStageUpdateListStatus(@RequestBody PpmKpiPmDto ppmKpiPmDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.backStageUpdateListStatus(ppmKpiPmDto.getPpmKpiPmDetailDtos());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("更新成功");
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败");
		}
		return result;
	}




	/**
	 * 后台状态修改单条
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "backStageUpdateStatus")
	@ApiOperation(value = "后台状态修改单条", notes = "后台状态修改单条")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),

	})
	public ApiResult<Object> backStageUpdateStatus(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.singleBackStageUpdateStatus(ppmKpiPmDetailDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
			result.setMessage("更新成功");
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败");
		}
		return result;
	}


	/**
	 * 查询履职评估信息
	 * @param ppmKpiPmDetailInfoDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPpmKpiPmPostEvaluationEvalInfo")
	@ApiOperation(value = "查询履职评估信息", notes = "查询履职评估信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalavgscore", value = "初评平均分", dataType = "Double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalavgscore", value = "终评平均分", dataType = "Double", paramType = "query"),
	})
	public ApiResult<List<PpmKpiPmDetailInfoDto>> getPpmKpiPmPostEvaluationEvalInfo(
			PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
		ApiResult<List<PpmKpiPmDetailInfoDto>> result =new ApiResult<>();
		List<PpmKpiPmDetailInfoDto> dto = ppmKpiPmDetailService.
				getPpmKpiPmPostEvaluationEvalInfo(ppmKpiPmDetailInfoDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}


	/**
	 * 查询履职评估评价数据
	 * @param postEvaluationEvalDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "queryPostEvaluationEvalEvalInfo")
	@ApiOperation(value = "查询履职评估评价数据", notes = "查询履职评估评价数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query" ,required = true),
	})
	public ApiResult<PostEvaluationEvalDto> queryPostEvaluationEvalEvalInfo(PostEvaluationEvalDto postEvaluationEvalDto) {
		ApiResult<PostEvaluationEvalDto> result =new ApiResult<>();
		PostEvaluationEvalDto item = ppmKpiPmDetailService.queryPostEvaluationEvalEvalInfo(postEvaluationEvalDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}



	/**
	 * 履职评估科技部评价后台数据查询
	 * @param ppmKpiPmDetailInfoDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPostEvaluationList")
	@ApiOperation(value = "履职评估科技部评价后台数据查询", notes = "履职评估科技部评价后台数据查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态名称", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "centername", value = "中心名称", dataType = "string", paramType = "query"),
	})
	public ApiResult<List<PpmKpiPmDetailInfoDto>> getPostEvaluationList(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
		ApiResult<List<PpmKpiPmDetailInfoDto>> result =new ApiResult<>();
		List<PpmKpiPmDetailInfoDto> dto = ppmKpiPmDetailService.getPostEvaluationList(ppmKpiPmDetailInfoDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

	/**
	 * 履职评估科技部评价后台分页
	 * @param ppmKpiPmDetailInfoDto 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPostEvaluationPager")
	@ApiOperation(value ="履职评估科技部评价后台 分页查询", notes = " 履职评估科技部评价后台 分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态名称", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "centername", value = "中心名称", dataType = "string", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
	})
	public ApiResult getPostEvaluationPager(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto,Pager pager) {
		ApiResult<List> result =new ApiResult<>();
		ppmKpiPmDetailService.getPostEvaluationPager(ppmKpiPmDetailInfoDto,pager);
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}

	/**
	 * 履职评估科技部评价数据导出
	 * @param ppmKpiPmDetailInfoDto  对象
	 * @param response  对象
	 */
	@PostMapping("exportPostEvaluationDatas")
	@ApiOperation(value = "履职评估科技部评价数据导出")
	public void exportPostEvaluationDatas(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto , HttpServletResponse response) {
		ApiResult result = new ApiResult();
		List<PpmKpiPmDetailInfoDto> list = ppmKpiPmDetailService.getPostEvaluationList(ppmKpiPmDetailInfoDto);
		ExcelUtil.exportExcel(list,PpmKpiPmDetailInfoDto.class,"履职评估科技部评价数据导出.xls",response);
		result.success("履职评估科技部评价数据导出");
	}


	/**
	 * 导入
	 * @param file 文件
	 * @return   ApiResult
	 */
	@PostMapping("importExcel")
	@ApiOperation(value = "导入", notes = "导出的数据导入")
	public ApiResult importExcel(@RequestParam("file") MultipartFile file) {
		ApiResult result = new ApiResult();
		boolean falg = ppmKpiPmDetailService.importExcel(file);
		if(falg) {
			result.success("导入成功");
		} else {
			result.failure("导入失败");
		}
		return result;
	}


	/**
	 * 科技部履职评估数据提交
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitTechPostEvaluation")
	@ApiOperation(value = "科技部履职评估数据提交", notes = "科技部履职评估数据提交")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatusname", value = "详细项状态名称", dataType = "string", paramType = "query"),
	})
	public ApiResult<Object> submitTechPostEvaluation(PpmKpiPmDetailDto ppmKpiPmDetailDto ) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.submitTechPostEvaluation(ppmKpiPmDetailDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败");
		}
		return result;
	}
	/**
	 * 履职评估整体提交
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitTotalPostEvaluationEval")
	@ApiOperation(value = "履职评估整体提交", notes = "履职评估整体提交")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatusname", value = "详细项状态名称", dataType = "string", paramType = "query"),
	})
	public ApiResult<Object> submitTotalPostEvaluationEval(PpmKpiPmDetailDto ppmKpiPmDetailDto ) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.submitTotalPostEvaluationEval(ppmKpiPmDetailDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else if(id==0){
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("评价分数未填写！");
		}else{
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("提交失败");
        }
		return result;
	}



	/**
	 * 保存履职评估评价
	 * @param postEvaluationEvalDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "savePostEvaluationEval")
	@ApiOperation(value = "保存履职评估评价", notes = "保存履职评估评价")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mainpartname", value = "主体名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "list", value = "综合评价的子项", dataType = "List", paramType = "query"),
	})
	public ApiResult<Object> savePostEvaluationEval(@RequestBody PostEvaluationEvalDto postEvaluationEvalDto ) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.savePostEvaluationEval(postEvaluationEvalDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("保存失败");
		}
		return result;
	}


	/**
	 * 履职评估科技部评价后台分页
	 * @param ppmKpiPmDetailInfoDto 对象
	 * @param pager 分页对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPostEvaluationGroupPager")
	@ApiOperation(value ="履职评估科技部评价后台 分页查询", notes = " 履职评估科技部评价后台 分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "halfyearevalself", value = "半年度自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evaluserid", value = "上下级或者科技部评价人工号（履职评估用）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "主表主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "kpitype", value = "指标类型", dataType = "long", paramType = "query",required = true),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isedit", value = "是否可编辑", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "evalself", value = "完成情况自评", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalcomment", value = "初评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalcomment", value = "终评评语", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注内容1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注内容2", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态名称", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "centername", value = "中心名称", dataType = "string", paramType = "query"),

			// 分页公共参数
			@ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
	})
	public ApiResult getPostEvaluationGroupPager(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto,Pager pager) {
		ApiResult<List> result =new ApiResult<>();
		ppmKpiPmDetailService.getPostEvaluationGroupPager(ppmKpiPmDetailInfoDto,pager);
		result.setResults(pager.getList());
		result.setStatus(ApiCode.SUCCESS);
		result.setTotal(pager.getTotal());
		return result;
	}

}
