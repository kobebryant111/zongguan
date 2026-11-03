package com.work.ppm.application.api.otherdeptmanager;

import com.work.ppm.application.dto.otherdeptmanager.PpmDevelopGroupLedgerDto;
import com.work.ppm.application.services.otherdeptmanager.IPpmDevelopGroupLedgerService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  研发小组台账 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDevelopGroupLedgerApi"})
@RestController
@RequestMapping("developGroupLedger")
@Slf4j
public class PpmDevelopGroupLedgerApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmDevelopGroupLedgerService ppmDevelopGroupLedgerService;

    /**
     * 分页
     * @param ppmDevelopGroupLedgerDto 对象
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
    public ApiResult queryPager(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmDevelopGroupLedgerService.queryPager(ppmDevelopGroupLedgerDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

//    /**
//     * 查询详情
//     * @param ppmDevelopGroupLedgerDto 对象
//     * @return ApiResult 对象
//     */
//    @GetMapping(value = "queryDetail")
//    @ApiOperation(value = "查询详情", notes = "查询详情")
//    public ApiResult<PpmDevelopGroupLedgerDto> queryDetail(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) {
//        ApiResult<PpmDevelopGroupLedgerDto> result =new ApiResult<>();
//        PpmDevelopGroupLedgerDto item = ppmDevelopGroupLedgerService.queryDetail(ppmDevelopGroupLedgerDto);
//        result.setResults(item);
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

//    /**
//     * 添加
//     * @param ppmDevelopGroupLedgerDto 对象
//     * @return ApiResult
//     */
//    @PostMapping(value = "add")
//    @ApiOperation(value = "添加", notes = "添加")
//    public ApiResult<Object> addPpmDevelopGroupLedger(PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) {
//        ApiResult<Object> result =new ApiResult<>();
//        int id = ppmDevelopGroupLedgerService.addPpmDevelopGroupLedger(ppmDevelopGroupLedgerDto);
//        if(id > 0){
//            result.setStatus(ApiCode.SUCCESS);
//        } else {
//            result.setStatus(ApiCode.UPATE_FAIL);
//            result.setMessage("添加失败");
//        }
//        return result;
//    }

    /**
     * 更新
     * @param developGroupLedgerDto 对象
     * @return ApiResult
     */
    @PutMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmDevelopGroupLedger(@RequestBody PpmDevelopGroupLedgerDto developGroupLedgerDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmDevelopGroupLedgerService.updatePpmDevelopGroupLedger(developGroupLedgerDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

//    /**
//     * 删除
//     * @param ppmDevelopGroupLedgerDto 对象
//     * @return ApiResult
//     */
//    @DeleteMapping(value = "delete")
//    @ApiOperation(value = "删除", notes = "删除")
//    public ApiResult deletePpmDevelopGroupLedger (PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto) {
//        ApiResult result =new ApiResult<>();
//        int id = ppmDevelopGroupLedgerService.deletePpmDevelopGroupLedger(ppmDevelopGroupLedgerDto);
//        if(id > 0){
//            result.setStatus(ApiCode.SUCCESS);
//        } else {
//            result.setStatus(ApiCode.DELETE_FAIL);
//            result.setMessage("删除失败");
//        }
//        return result;
//    }

    /**
     * 导出
     * @param ppmDevelopGroupLedgerDto 对象
     * @param response 对象
     */
    @PostMapping(value = "export")
    @ApiOperation(value = "导出", notes = "导出")
    public void exportPpmDevelopGroupLedger (PpmDevelopGroupLedgerDto ppmDevelopGroupLedgerDto,HttpServletResponse response) {
        List<PpmDevelopGroupLedgerDto> list = ppmDevelopGroupLedgerService.exportPpmDevelopGroupLedger(ppmDevelopGroupLedgerDto);
        ExcelUtil.exportExcel(list, PpmDevelopGroupLedgerDto.class,"xxx.xls", response);
    }

}
