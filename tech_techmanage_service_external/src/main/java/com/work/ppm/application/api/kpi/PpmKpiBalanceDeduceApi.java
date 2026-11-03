package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiBalanceDeduceDto;
import com.work.ppm.application.services.kpi.IPpmKpiBalanceDeduceService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 平衡计分扣分表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiBalanceDeduceApi"},description="平衡计分扣分表")
@RestController
@RequestMapping("ppmKpiBalanceDeduce")
@Slf4j
public class PpmKpiBalanceDeduceApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiBalanceDeduceService ppmKpiBalanceDeduceService;

    /**
     * 分页
     * @param ppmKpiBalanceDeduceDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deducescorereason", value = "扣分原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "workremark", value = "录入标准", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiBalanceDeduceService.queryPager(ppmKpiBalanceDeduceDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiBalanceDeduceDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deducescorereason", value = "扣分原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "workremark", value = "录入标准", dataType = "string", paramType = "query")
			
    })
    public ApiResult<PpmKpiBalanceDeduceDto> queryDetail(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) {
        ApiResult<PpmKpiBalanceDeduceDto> result =new ApiResult<>();
        PpmKpiBalanceDeduceDto item = ppmKpiBalanceDeduceService.queryDetail(ppmKpiBalanceDeduceDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiBalanceDeduceDto 对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deducescorereason", value = "扣分原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "workremark", value = "录入标准", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKpiBalanceDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto,
                                                       HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        ppmKpiBalanceDeduceDto.setRegby(user.getUserid());
        int id = ppmKpiBalanceDeduceService.updateDeduce(ppmKpiBalanceDeduceDto);
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
     * @param ppmKpiBalanceDeduceDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiBalanceDeduce (PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiBalanceDeduceService.deleteDeduce(ppmKpiBalanceDeduceDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 添加数据
     * @param ppmKpiBalanceDeduceDto 对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "addPpmKpiParams")
    @ApiOperation(value = "添加数据", notes = "添加数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "plan", value = "考核标准", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "plandescribe", value = "考核标准描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deducescorereason", value = "扣分原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "regby", value = "录入人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "regtime", value = "录入时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "workremark", value = "录入标准", dataType = "string", paramType = "query")
    })
    public ApiResult<Object> addPpmKpiBalanceDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto,
                                                    HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        ppmKpiBalanceDeduceDto.setRegby(user.getUserid());
        int id = ppmKpiBalanceDeduceService.addDeduce(ppmKpiBalanceDeduceDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }


    /**
     * 获取扣分详细项
     * @param ppmKpiBalanceDeduceDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "getBalanceDeduceItem")
    @ApiOperation(value = "获取扣分详细项", notes = "获取扣分详细项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query" ,required = true),
            @ApiImplicitParam(name = "eid", value = "工号", dataType = "string", paramType = "query",required = true),

            @ApiImplicitParam(name = "item", value = "考核项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itemdescribe", value = "考核项描述", dataType = "string", paramType = "query"),

    })
    public ApiResult<List<PpmKpiBalanceDeduceDto>> getBalanceDeduceItem(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) {
        ApiResult<List<PpmKpiBalanceDeduceDto>> result =new ApiResult<>();
        List<PpmKpiBalanceDeduceDto> dto = ppmKpiBalanceDeduceService.getBalanceDeduceItem(ppmKpiBalanceDeduceDto);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 平衡计分详细扣分数据
     * @param ppmKpiBalanceDeduceDto  对象
     * @param response  对象
     */
    @PostMapping("exportBalanceSummary")
    @ApiOperation(value = "平衡计分详细扣分数据导出")
    public void exportBalanceDecduceItems(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        ApiResult result = new ApiResult();
        List<PpmKpiBalanceDeduceDto> list = ppmKpiBalanceDeduceService.getBalanceDeduceItem(ppmKpiBalanceDeduceDto);
        for(int i=0;i<list.size();i++){
            PpmKpiBalanceDeduceDto dto=list.get(i);
            dto.setRegtimeStr(sdf.format(dto.getRegtime()));
        }
        ExcelUtil.exportExcel(list,PpmKpiBalanceDeduceDto.class,"平衡计分详细扣分数据.xls",response);
        result.success("平衡计分详细扣分数据成功");
    }
}
