package com.work.ppm.application.api.kpi;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiParamsDto;
import com.work.ppm.application.services.kpi.IPpmKpiParamsService;
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
 * 考勤参数表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiParamsApi"},description="考核参数表")
@RestController
@RequestMapping("ppmKpiParams")
@Slf4j
public class PpmKpiParamsApi {
    /**
     * 考勤参数表接口描述
     */
    @Autowired
    private IPpmKpiParamsService ppmKpiParamsService;

    /**
     * 分页
     * @param ppmKpiParamsDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "ptype", value = "参数大类", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dicttype", value = "参数类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictid", value = "参数ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictname", value = "参数名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = " ", value = "参数规则", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注2", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiParamsDto ppmKpiParamsDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiParamsService.queryPager(ppmKpiParamsDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiParamsDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "ptype", value = "参数大类", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dicttype", value = "参数类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictid", value = "参数ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictname", value = "参数名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "prule", value = "参数规则", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注2", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmKpiParamsDto> queryDetail(PpmKpiParamsDto ppmKpiParamsDto) {
        ApiResult<PpmKpiParamsDto> result =new ApiResult<>();
        PpmKpiParamsDto item = ppmKpiParamsService.queryDetail(ppmKpiParamsDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiParamsDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "ptype", value = "参数大类", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dicttype", value = "参数类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictid", value = "参数ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dictname", value = "参数名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "prule", value = "参数规则", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark1", value = "备注1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark2", value = "备注2", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKpiParams(PpmKpiParamsDto ppmKpiParamsDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiParamsService.updatePpmKpiParams(ppmKpiParamsDto);
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
     * @param ppmKpiParamsDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiParams (PpmKpiParamsDto ppmKpiParamsDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiParamsService.deletePpmKpiParams(ppmKpiParamsDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }



    /**
     * 获取参数数据
     * @param ppmKpiParamsDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryLists")
    @ApiOperation(value = "获取参数数据", notes = "获取参数数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "ptype", value = "参数大类", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dicttype", value = "参数类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictid", value = "参数ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictname", value = "参数名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "prule", value = "参数规则", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark1", value = "备注1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark2", value = "备注2", dataType = "string", paramType = "query"),
    })
    public ApiResult<List<PpmKpiParamsDto>> queryLists(PpmKpiParamsDto ppmKpiParamsDto) {
        ApiResult<List<PpmKpiParamsDto>> result =new ApiResult<>();
        List<PpmKpiParamsDto> dtos = ppmKpiParamsService.queryLists(ppmKpiParamsDto);
        result.setResults(dtos);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 添加参数数据
     * @param ppmKpiParamsDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "addPpmKpiParams")
    @ApiOperation(value = "添加参数数据", notes = "添加参数数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "ptype", value = "参数大类", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dicttype", value = "参数类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictid", value = "参数ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictname", value = "参数名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "prule", value = "参数规则", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark1", value = "备注1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark2", value = "备注2", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> addPpmKpiParams(PpmKpiParamsDto ppmKpiParamsDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiParamsService.addPpmKpiParams(ppmKpiParamsDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }
}
