package com.work.ppm.application.api.otherdeptmanager;

import com.work.ppm.application.dto.otherdeptmanager.PpmDevelopGroupInfoDto;
import com.work.ppm.application.services.otherdeptmanager.IPpmDevelopGroupInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  对接手机平台信息 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDevelopGroupInfoApi"})
@RestController
@RequestMapping("developGroupInfo")
@Slf4j
public class PpmDevelopGroupInfoApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmDevelopGroupInfoService ppmDevelopGroupInfoService;

    /**
     * 分页
     * @param developGroupInfoDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryList")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    public ApiResult queryList(PpmDevelopGroupInfoDto developGroupInfoDto) {
        ApiResult<List> result =new ApiResult<>();
        result.setResults(ppmDevelopGroupInfoService.queryDetailList(developGroupInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 分页
     * @param developGroupInfoDto 对象
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
    public ApiResult queryPager(PpmDevelopGroupInfoDto developGroupInfoDto, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmDevelopGroupInfoService.queryPager(developGroupInfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

//    /**
//     * 查询详情
//     * @param ppmDevelopGroupInfoDto 对象
//     * @return ApiResult 对象
//     */
//    @GetMapping(value = "queryDetail")
//    @ApiOperation(value = "查询详情", notes = "查询详情")
//    public ApiResult<PpmDevelopGroupInfoDto> queryDetail(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
//        ApiResult<PpmDevelopGroupInfoDto> result =new ApiResult<>();
//        PpmDevelopGroupInfoDto item = ppmDevelopGroupInfoService.queryDetail(ppmDevelopGroupInfoDto);
//        result.setResults(item);
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

    /**
     * 添加
     * @param ppmDevelopGroupInfoDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "add")
    @ApiOperation(value = "添加", notes = "添加")
    public ApiResult<Object> addPpmDevelopGroupInfo(@RequestBody PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmDevelopGroupInfoService.addPpmDevelopGroupInfo(ppmDevelopGroupInfoDto);
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
     * @param ppmDevelopGroupInfoDto 对象
     * @return ApiResult
     */
    @PutMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmDevelopGroupInfo(@RequestBody PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmDevelopGroupInfoService.updatePpmDevelopGroupInfo(ppmDevelopGroupInfoDto);
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
     * @param ppmDevelopGroupInfoDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deletePpmDevelopGroupInfo (@RequestBody PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmDevelopGroupInfoService.deletePpmDevelopGroupInfo(ppmDevelopGroupInfoDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 导出
     * @param ppmDevelopGroupInfoDto 对象
     * @param response 对象
     */
    @PostMapping(value = "export")
    @ApiOperation(value = "导出", notes = "导出")
    public void exportPpmDevelopGroupInfo (PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto,HttpServletResponse response) {
        List<PpmDevelopGroupInfoDto> list = ppmDevelopGroupInfoService.exportPpmDevelopGroupInfo(ppmDevelopGroupInfoDto);
        ExcelUtil.exportExcel(list, PpmDevelopGroupInfoDto.class,"xxx.xls", response);
    }
}
