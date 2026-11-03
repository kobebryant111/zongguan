package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarHistoryDto;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarHistoryService;
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
 *  考勤日历修改记录 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKqCalendarHistoryApi"})
@RestController
@RequestMapping("ppmKqCalendarHistory")
@Slf4j
public class PpmKqCalendarHistoryApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKqCalendarHistoryService ppmKqCalendarHistoryService;

    /**
     * 分页
     * @param ppmKqCalendarHistoryDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKqCalendarHistoryService.queryPager(ppmKqCalendarHistoryDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKqCalendarHistoryDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmKqCalendarHistoryDto> queryDetail(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) {
        ApiResult<PpmKqCalendarHistoryDto> result =new ApiResult<>();
        PpmKqCalendarHistoryDto item = ppmKqCalendarHistoryService.queryDetail(ppmKqCalendarHistoryDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 添加
     * @param ppmKqCalendarHistoryDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "add")
    @ApiOperation(value = "添加", notes = "添加")
    public ApiResult<Object> addPpmKqCalendarHistory(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKqCalendarHistoryService.addPpmKqCalendarHistory(ppmKqCalendarHistoryDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 更新
     * @param ppmKqCalendarHistoryDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmKqCalendarHistory(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKqCalendarHistoryService.updatePpmKqCalendarHistory(ppmKqCalendarHistoryDto);
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
     * @param ppmKqCalendarHistoryDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deletePpmKqCalendarHistory (PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKqCalendarHistoryService.deletePpmKqCalendarHistory(ppmKqCalendarHistoryDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
