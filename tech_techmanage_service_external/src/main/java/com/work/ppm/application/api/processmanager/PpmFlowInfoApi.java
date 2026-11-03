package com.work.ppm.application.api.processmanager;

import com.work.ppm.application.dto.processmanager.PpmFlowInfoDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.application.model.processmanager.PpmFlowInfo;
import com.work.ppm.application.services.processmanager.IPpmFlowInfoService;
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
 * 流程信息
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmFlowInfoApi"},description="流程信息")
@RestController
@RequestMapping("ppmFlowInfo")
@Slf4j
public class PpmFlowInfoApi {
    /**
     * 流程信息 接口
     */
    @Autowired
    private IPpmFlowInfoService ppmFlowInfoService;

    /**
     * 列表
     * @param ppmFlowInfo 对象
     * @return ApiResult<List<PpmFlowInfoDto>>
     */
    @PostMapping(value = "queryList")
    @ApiOperation(value ="查询", notes = "查询")

    public ApiResult<List<PpmFlowInfoDto>> queryPager(PpmFlowInfo ppmFlowInfo) {
        ApiResult<List<PpmFlowInfoDto>> result =new ApiResult<>();
        List<PpmFlowInfoDto> list = ppmFlowInfoService.queryList(ppmFlowInfo);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询详情
     * @param ppmFlowInfo 对象
     * @return ApiResult<PpmFlowInfo>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "编号", dataType = "long", paramType = "query", required = true)
    })
    public ApiResult<PpmFlowInfo> queryDetail(PpmFlowInfo ppmFlowInfo) {
        ApiResult<PpmFlowInfo> result =new ApiResult<>();
        PpmFlowInfo item = ppmFlowInfoService.queryDetail(ppmFlowInfo);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmFlowInfo 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新和添加", notes = "更新")

    public ApiResult<Object> updatePpmFlowInfo(PpmFlowInfo ppmFlowInfo) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmFlowInfoService.updatePpmFlowInfo(ppmFlowInfo);
        if(id == 1){
            result.success("添加成功");
        }else if(id == 2)  {
            result.success("更新成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmFlowInfo 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query", required = true),

    })
    public ApiResult deletePpmFlowInfo (PpmFlowInfo ppmFlowInfo) {
        ApiResult result =new ApiResult<>();
        int id = ppmFlowInfoService.deletePpmFlowInfo(ppmFlowInfo);
        if(id > 0){
            result.success("操作成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
