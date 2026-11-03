package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingRiskResultDto;
import com.work.ppm.application.model.integratedmanager.OutRiskResultData;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingRiskResultService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
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
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingRiskResultApi"},description="外包排查结果表")
@RestController
@RequestMapping("ppmOutsourcingRiskResult")
@Slf4j
public class PpmOutsourcingRiskResultApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOutsourcingRiskResultService ppmOutsourcingRiskResultService;

    /**
     * 分页
     * @param ppmOutsourcingRiskResultDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certno", value = "身份证号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgname", value = "公司名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "公司代码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "explainstate", value = "说明情况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depfeedback", value = "二级部反馈", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealstatus", value = "处理状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "manageuserid", value = "总经理工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "risksuggest", value = "排查意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "riskdate", value = "排查日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "risktype", value = "排查类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "regid", value = "录入修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "managesuggest", value = "总经理意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tmonth", value = "月", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmOutsourcingRiskResultService.queryPager(ppmOutsourcingRiskResultDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmOutsourcingRiskResultDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certno", value = "身份证号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgname", value = "公司名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "公司代码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "explainstate", value = "说明情况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depfeedback", value = "二级部反馈", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealstatus", value = "处理状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "manageuserid", value = "总经理工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "risksuggest", value = "排查意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "riskdate", value = "排查日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "risktype", value = "排查类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "regid", value = "录入修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "managesuggest", value = "总经理意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tmonth", value = "月", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmOutsourcingRiskResultDto> queryDetail(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) {
        ApiResult<PpmOutsourcingRiskResultDto> result =new ApiResult<>();
        PpmOutsourcingRiskResultDto item = ppmOutsourcingRiskResultService.queryDetail(ppmOutsourcingRiskResultDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcingRiskResultDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certno", value = "身份证号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgname", value = "公司名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "公司代码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "explainstate", value = "说明情况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depfeedback", value = "二级部反馈", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealstatus", value = "处理状态", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "manageuserid", value = "总经理工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "risksuggest", value = "排查意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "riskdate", value = "排查日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "risktype", value = "排查类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "regid", value = "录入修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "managesuggest", value = "总经理意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tmonth", value = "月", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOutsourcingRiskResult(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcingRiskResultService.updatePpmOutsourcingRiskResult(ppmOutsourcingRiskResultDto);
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
     * @param ppmOutsourcingRiskResultDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOutsourcingRiskResult (PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcingRiskResultService.deletePpmOutsourcingRiskResult(ppmOutsourcingRiskResultDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 查询外包排查数据
     * @param outDataDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "getOutRiskResults")
    @ApiOperation(value = "查询外包排查数据", notes = "查询外包排查数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
    })
    public ApiResult<List<OutRiskResultData>> getOutRiskResults(OutDataDto outDataDto) {
        ApiResult<List<OutRiskResultData>> result =new ApiResult<>();
        List<OutRiskResultData> items = ppmOutsourcingRiskResultService.getOutRiskResults(outDataDto);
        result.setResults(items);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
