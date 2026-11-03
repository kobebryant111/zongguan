package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.ComprehensiveAbilityEvalDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailDto;
import com.work.ppm.application.services.kpi.IPpmKpiPmDetailService;
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
import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiPmDetailApi"},description="考核数据指标项表")
@RestController
@RequestMapping("ppmKpiPmDetail")
@Slf4j
public class PpmKpiPmDetailApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiPmDetailService ppmKpiPmDetailService;

    /**
     * 分页
     * @param ppmKpiPmDetailDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
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
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiPmDetailDto ppmKpiPmDetailDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiPmDetailService.queryPager(ppmKpiPmDetailDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiPmDetailDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
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
    public ApiResult<PpmKpiPmDetailDto> queryDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        ApiResult<PpmKpiPmDetailDto> result =new ApiResult<>();
        PpmKpiPmDetailDto item = ppmKpiPmDetailService.queryDetail(ppmKpiPmDetailDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiPmDetailDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
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
    public ApiResult<Object> updatePpmKpiPmDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiPmDetailService.updatePpmKpiPmDetail(ppmKpiPmDetailDto);
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
     * @param ppmKpiPmDetailDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiPmDetail (PpmKpiPmDetailDto ppmKpiPmDetailDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiPmDetailService.deletePpmKpiPmDetail(ppmKpiPmDetailDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


	/**
	 * 获取考核考核项数据
	 * @param ppmKpiPmDetailInfoDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPpmKpiPmDetails")
	@ApiOperation(value = "获取考核考核项数据", notes = "获取考核考核项数据")
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
	public ApiResult<List<PpmKpiPmDetailInfoDto>> getPpmKpiPmDetails(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
		ApiResult<List<PpmKpiPmDetailInfoDto>> result =new ApiResult<>();
		List<PpmKpiPmDetailInfoDto> dto = ppmKpiPmDetailService.getPpmKpiPmDetails(ppmKpiPmDetailInfoDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}


	/**
	 * 审核时添加KPI和能力提升
	 * @param ppmKpiPmDetailDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "addPpmKpiPmDetailItemApprove")
	@ApiOperation(value = "审核时添加KPI和能力提升", notes = "审核时添加KPI和能力提升")
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
	public ApiResult<Object> addPpmKpiPmDetailItemApprove(PpmKpiPmDetailDto ppmKpiPmDetailDto,
														  HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.addPpmKpiPmDetailItemApprove(ppmKpiPmDetailDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("添加失败");
		}
		return result;
	}

	/**
	 * 制定时添加KPI和能力提升
	 * @param ppmKpiPmDetailDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "addPpmKpiPmDetailItemFormulate")
	@ApiOperation(value = "制定时添加KPI和能力提升", notes = "制定时添加KPI和能力提升")
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
	public ApiResult<Object> addPpmKpiPmDetailItemFormulate(PpmKpiPmDetailDto ppmKpiPmDetailDto,
															HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.addPpmKpiPmDetailItemFormulate(ppmKpiPmDetailDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("添加失败");
		}
		return result;
	}



	/**
	 * 审核时更新指标
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "updatePpmKpiPmDetailApprove")
	@ApiOperation(value = "审核时更新指标", notes = "审核时更新指标")
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
	public ApiResult<Object> updatePpmKpiPmDetailApprove(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.updatePpmKpiPmDetailApprove(ppmKpiPmDetailDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败");
		}
		return result;
	}


	/**
	 * 制定时更新指标
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "updatePpmKpiPmDetailFormulate")
	@ApiOperation(value = "制定时更新指标", notes = "制定时更新指标")
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
	public ApiResult<Object> updatePpmKpiPmDetailFormulate(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.updatePpmKpiPmDetailFormulate(ppmKpiPmDetailDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败");
		}
		return result;
	}


	/**
	 * 根据ID查询详情数据
	 * @param ppmKpiPmDetailInfoDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "getPpmKpiPmDetailInfo")
	@ApiOperation(value = "根据ID查询详情数据", notes = "根据ID查询详情数据")
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
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query" ,required = true),
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
			@ApiImplicitParam(name = "firstevalname", value = "初评人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondevalname", value = "终评人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemstatusname", value = "详细项状态名称", dataType = "long", paramType = "query"),
	})
	public ApiResult<PpmKpiPmDetailInfoDto> getPpmKpiPmDetailInfo(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
		ApiResult<PpmKpiPmDetailInfoDto> result =new ApiResult<>();
		PpmKpiPmDetailInfoDto item = ppmKpiPmDetailService.getPpmKpiPmDetailInfo(ppmKpiPmDetailInfoDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}



	/**
	 * 自评之类从页面传参过来进行数据更新
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "updatePpmKpiPmDetailDto")
	@ApiOperation(value = "自评之类从页面传参过来进行数据更新", notes = "自评之类从页面传参过来进行数据更新")
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
	public ApiResult<Object> updatePpmKpiPmDetailDto(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.updatePpmKpiPmDetailDto(ppmKpiPmDetailDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败");
		}
		return result;
	}


	/**
	 * 评价修改考核信息
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "updateDetail")
	@ApiOperation(value = "评价修改考核信息", notes = "评价修改考核信息")
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
	public ApiResult<Object> updateDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.updateDetailEval(ppmKpiPmDetailDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败");
		}
		return result;
	}



	/**
	 * 更新多条_评价修改考核信息
	 * @param ppmKpiPmDetailDtos 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "updateDetails")
	@ApiOperation(value = "更新多条_评价修改考核信息", notes = "更新多条_评价修改考核信息")
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
	public ApiResult<Object> updateDetails(List<PpmKpiPmDetailDto> ppmKpiPmDetailDtos) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPmDetailService.updateDetails(ppmKpiPmDetailDtos);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败");
		}
		return result;
	}




	/**
	 * 评价回退初评
	 * @param ppmKpiPmDetailDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "backFirstEval")
	@ApiOperation(value = "评价回退初评", notes = "评价回退初评")
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
	public ApiResult<Object> backFirstEval(PpmKpiPmDetailDto ppmKpiPmDetailDto, HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.backFirstEval(ppmKpiPmDetailDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("回退失败");
		}
		return result;
	}

	/**
	 * 回退自评
	 * @param ppmKpiPmDetailDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "backEvalSelf")
	@ApiOperation(value = "回退自评", notes = "回退自评")
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
	public ApiResult<Object> backEvalSelf(PpmKpiPmDetailDto ppmKpiPmDetailDto, HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.backEvalSelf(ppmKpiPmDetailDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("回退失败");
		}
		return result;
	}


	/**
	 * 提交评价
	 * @param ppmKpiPmDetailDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitEval")
	@ApiOperation(value = "提交评价", notes = "提交评价")
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
	public ApiResult<Object> submitEval(PpmKpiPmDetailDto ppmKpiPmDetailDto, HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.submitEval(ppmKpiPmDetailDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败");
		}
		return result;
	}


	/**
	 * 查询综合能力评价数据
	 * @param comprehensiveAbilityEvalDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "queryComprehensiveAbilityEvalInfo")
	@ApiOperation(value = "查询综合能力评价数据", notes = "查询综合能力评价数据")
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
	public ApiResult<ComprehensiveAbilityEvalDto> queryComprehensiveAbilityEvalInfo(
			ComprehensiveAbilityEvalDto comprehensiveAbilityEvalDto) {
		ApiResult<ComprehensiveAbilityEvalDto> result =new ApiResult<>();
		ComprehensiveAbilityEvalDto item = ppmKpiPmDetailService.
				queryComprehensiveAbilityEvalInfo(comprehensiveAbilityEvalDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

	/**
	 * 获取综合能力评价汇总数据
	 * @param ppmKpiPmDetailInfoDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getPpmKpiPmComprehensiveAbilityEvalInfo")
	@ApiOperation(value = "获取综合能力评价汇总数据", notes = "获取综合能力评价汇总数据")
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
	public ApiResult<List<PpmKpiPmDetailInfoDto>> getPpmKpiPmComprehensiveAbilityEvalInfo(
			PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto) {
		ApiResult<List<PpmKpiPmDetailInfoDto>> result =new ApiResult<>();
		List<PpmKpiPmDetailInfoDto> dto = ppmKpiPmDetailService.
				getPpmKpiPmComprehensiveAbilityEvalInfo(ppmKpiPmDetailInfoDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

	/**
	 * 提交综合能力评价
	 * @param comprehensiveAbilityEvalDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitComprehensiveAbilityEval")
	@ApiOperation(value = "提交综合能力评价", notes = "提交综合能力评价")
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
			@ApiImplicitParam(name = "list", value = "综合评价的子项", dataType = "List", paramType = "query"),
	})
	public ApiResult<Object> submitComprehensiveAbilityEval(
			@RequestBody ComprehensiveAbilityEvalDto comprehensiveAbilityEvalDto,
			HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.submitComprehensiveAbilityEval(
				comprehensiveAbilityEvalDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败");
		}
		return result;
	}

	/**
	 * 保存综合能力评价
	 * @param comprehensiveAbilityEvalDto 对象
	 * @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "saveComprehensiveAbilityEval")
	@ApiOperation(value = "保存综合能力评价", notes = "保存综合能力评价")
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
			@ApiImplicitParam(name = "list", value = "综合评价的子项", dataType = "List", paramType = "query"),
	})
	public ApiResult<Object> saveComprehensiveAbilityEval(
			@RequestBody ComprehensiveAbilityEvalDto comprehensiveAbilityEvalDto,
			HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.saveComprehensiveAbilityEval(
				comprehensiveAbilityEvalDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("保存失败");
		}
		return result;
	}

	/**
	 * 获取综合能力评价汇总数据
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getBalanceItem")
	@ApiOperation(value = "查询考核项目表里面平衡计分项", notes = "查询考核项目表里面平衡计分项")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query" ,required = true),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query",required = true),

			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),

	})
	public ApiResult<List<PpmKpiPmDetailDto>> getBalanceItem(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
		ApiResult<List<PpmKpiPmDetailDto>> result =new ApiResult<>();
		List<PpmKpiPmDetailDto> dto = ppmKpiPmDetailService.getBalanceItem(ppmKpiPmDetailDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

	/**
	 * 查询考核项目表里面平衡计分项描述
	 * @param ppmKpiPmDetailDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "getBalancePlan")
	@ApiOperation(value = "查询考核项目表里面平衡计分项描述", notes = "查询考核项目表里面平衡计分项描述")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query" ,required = true),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query" ,required = true),

			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),

	})
	public ApiResult<List<PpmKpiPmDetailDto>> getBalancePlan(PpmKpiPmDetailDto ppmKpiPmDetailDto) {
		ApiResult<List<PpmKpiPmDetailDto>> result =new ApiResult<>();
		List<PpmKpiPmDetailDto> dto = ppmKpiPmDetailService.getBalancePlan(ppmKpiPmDetailDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}


	/**
	 * 提交整体评语
	 * @param ppmKpiPmDetailDto 对象
	 *  @param request 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitWholeComment")
	@ApiOperation(value = "提交整体评语", notes = "提交整体评语")
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
	public ApiResult<Object> submitWholeComment(PpmKpiPmDetailDto ppmKpiPmDetailDto,
												HttpServletRequest request) {
		ApiResult<Object> result =new ApiResult<>();
		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		int id = ppmKpiPmDetailService.submitWholeComment(ppmKpiPmDetailDto,user.getUserid());
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败");
		}
		return result;
	}


}
