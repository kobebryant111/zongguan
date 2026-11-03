package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.PpmKpiPostevaluationInfoDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiPostevaluationDto;
import com.work.ppm.application.services.kpi.IPpmKpiPostevaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 履职评估表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiPostevaluationApi"},description="履职评估表")
@RestController
@RequestMapping("ppmKpiPostevaluation")
@Slf4j
public class PpmKpiPostevaluationApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiPostevaluationService ppmKpiPostevaluationService;

    /**
     * 分页
     * @param ppmKpiPostevaluationDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "履职评估主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiPostevaluationDto ppmKpiPostevaluationDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiPostevaluationService.queryPager(ppmKpiPostevaluationDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiPostevaluationDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "履职评估主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmKpiPostevaluationDto> queryDetail(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        ApiResult<PpmKpiPostevaluationDto> result =new ApiResult<>();
        PpmKpiPostevaluationDto item = ppmKpiPostevaluationService.queryDetail(ppmKpiPostevaluationDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiPostevaluationDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmKpiPostevaluation")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "履职评估主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiPostevaluationService.updatePpmKpiPostevaluation(ppmKpiPostevaluationDto);
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
     * @param ppmKpiPostevaluationDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePpmKpiPostevaluation")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiPostevaluation (PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiPostevaluationService.deletePpmKpiPostevaluation(ppmKpiPostevaluationDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }



	/**
	 * 获取需要下级评价的数据
	 * @param ppmKpiPostevaluationInfoDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "queryPpmKpiPostevaluationInfos")
	@ApiOperation(value = "获取需要下级评价的数据", notes = "获取需要下级评价的数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "履职评估主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),

			@ApiImplicitParam(name = "eidname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centername", value = "中心名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depname", value = "部门名称", dataType = "string", paramType = "query"),

	})
	public ApiResult<List<PpmKpiPostevaluationInfoDto>> queryPpmKpiPostevaluationInfos(
			PpmKpiPostevaluationInfoDto ppmKpiPostevaluationInfoDto) {
		ApiResult<List<PpmKpiPostevaluationInfoDto>> result =new ApiResult<>();
		List<PpmKpiPostevaluationInfoDto> dto = ppmKpiPostevaluationService.
				queryPpmKpiPostevaluationInfos(ppmKpiPostevaluationInfoDto);
		result.setResults(dto);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}



	/**
	 * 单条更新数据
	 * @param ppmKpiPostevaluationDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "updateSinglePpmKpiPostevaluation")
	@ApiOperation(value = "单条更新数据", notes = "单条更新数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "履职评估主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),

	})
	public ApiResult<Object> updateSinglePpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPostevaluationService.updateSinglePpmKpiPostevaluation(ppmKpiPostevaluationDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("更新失败");
		}
		return result;
	}


	/**
	 * 员工提交履职评估
	 * @param ppmKpiPostevaluationDto 对象
	 * @return ApiResult
	 */
	@PostMapping(value = "submitPpmKpiPostevaluation")
	@ApiOperation(value = "员工提交履职评估", notes = "员工提交履职评估")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "履职评估主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
	})
	public ApiResult<Object> submitPpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
		ApiResult<Object> result =new ApiResult<>();
		int id = ppmKpiPostevaluationService.submitPpmKpiPostevaluation(ppmKpiPostevaluationDto);
		if(id > 0){
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.setStatus(ApiCode.UPATE_FAIL);
			result.setMessage("提交失败");
		}
		return result;
	}

	/**
	 * 查询员工评价履职评估的分数
	 * @param ppmKpiPostevaluationDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "queryPostEvaluationSumScore")
	@ApiOperation(value = "查询员工评价履职评估的分数", notes = "查询员工评价履职评估的分数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "履职评估主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "workremark", value = "工作内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstevalscore", value = "初评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firstevalgoal", value = "初评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalscore", value = "终评分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "secondevalgoal", value = "终评比重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "tscore", value = "最终得分", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "firsteval", value = "初评人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondeval", value = "终评人", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "firstevaltime", value = "初评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "secondevaltime", value = "终评时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itemstatus", value = "详细项状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "考核主体", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query",required = true),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query",required = true),

	})
	public ApiResult<PpmKpiPostevaluationDto> queryPostEvaluationSumScore(
			PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
		ApiResult<PpmKpiPostevaluationDto> result =new ApiResult<>();
		PpmKpiPostevaluationDto item = ppmKpiPostevaluationService.
				queryPostEvaluationSumScore(ppmKpiPostevaluationDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

}
