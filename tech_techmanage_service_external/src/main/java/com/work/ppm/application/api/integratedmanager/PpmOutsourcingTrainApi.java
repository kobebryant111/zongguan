package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingTrainDto;
import com.work.ppm.application.model.integratedmanager.OutTrainData;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingTrainService;
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
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingTrainApi"},description="外包培训")
@RestController
@RequestMapping("ppmOutsourcingTrain")
@Slf4j
public class PpmOutsourcingTrainApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOutsourcingTrainService ppmOutsourcingTrainService;

    /**
     * 分页
     * @param ppmOutsourcingTrainDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "traintime", value = "培训时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "project", value = "培训项目(如果项目独立，存项目编号)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "score", value = "分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "traintec", value = "培训导师", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOutsourcingTrainDto ppmOutsourcingTrainDto, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmOutsourcingTrainService.queryPager(ppmOutsourcingTrainDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmOutsourcingTrainDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "traintime", value = "培训时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "project", value = "培训项目(如果项目独立，存项目编号)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "score", value = "分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "traintec", value = "培训导师", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmOutsourcingTrainDto> queryDetail(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) {
        ApiResult<PpmOutsourcingTrainDto> result =new ApiResult<>();
        PpmOutsourcingTrainDto item = ppmOutsourcingTrainService.queryDetail(ppmOutsourcingTrainDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcingTrainDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "traintime", value = "培训时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "project", value = "培训项目(如果项目独立，存项目编号)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "score", value = "分数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "traintec", value = "培训导师", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOutsourcingTrain(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcingTrainService.updatePpmOutsourcingTrain(ppmOutsourcingTrainDto);
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
     * @param ppmOutsourcingTrainDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOutsourcingTrain (PpmOutsourcingTrainDto ppmOutsourcingTrainDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcingTrainService.deletePpmOutsourcingTrain(ppmOutsourcingTrainDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 查询外包培训数据
     * @return ApiResult<List<CommTreeDto>>
     */
    @GetMapping("getOutTrainList")
    @ApiOperation("查询外包培训数据")
    public ApiResult<List<OutTrainData>> getOutTrainList(OutDataDto outDataDto) {
        ApiResult<List<OutTrainData>> result =new ApiResult<>();
        List<OutTrainData> dto = ppmOutsourcingTrainService.getOutTrainList(outDataDto);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
