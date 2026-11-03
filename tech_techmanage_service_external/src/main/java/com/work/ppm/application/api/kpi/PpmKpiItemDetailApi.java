package com.work.ppm.application.api.kpi;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiItemDetailDto;
import com.work.ppm.application.services.kpi.IPpmKpiItemDetailService;
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
 * 考核指标库详细表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiItemDetailApi"},description="考核指标库详细表")
@RestController
@RequestMapping("ppmKpiItemDetail")
@Slf4j
public class PpmKpiItemDetailApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiItemDetailService ppmKpiItemDetailService;

    /**
     * 分页
     * @param ppmKpiItemDetailDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "rid", value = "关联主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "standard", value = "标准内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workcontent", value = "考核内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiItemDetailDto ppmKpiItemDetailDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiItemDetailService.queryPager(ppmKpiItemDetailDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiItemDetailDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "rid", value = "关联主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "standard", value = "标准内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workcontent", value = "考核内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<PpmKpiItemDetailDto> queryDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) {
        ApiResult<PpmKpiItemDetailDto> result =new ApiResult<>();
        PpmKpiItemDetailDto item = ppmKpiItemDetailService.queryDetail(ppmKpiItemDetailDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiItemDetailDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "rid", value = "关联主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "分值", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "finishdate", value = "完成时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "standard", value = "标准内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workcontent", value = "考核内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKpiItemDetail(PpmKpiItemDetailDto ppmKpiItemDetailDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemDetailService.updatePpmKpiItemDetail(ppmKpiItemDetailDto);
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
     * @param ppmKpiItemDetailDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiItemDetail (PpmKpiItemDetailDto ppmKpiItemDetailDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiItemDetailService.deletePpmKpiItemDetail(ppmKpiItemDetailDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


}
