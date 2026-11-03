package com.work.ppm.application.api.suppliermanager;

import com.work.ppm.application.dto.suppliermanager.PpmSuppliersSyncDto;
import com.work.ppm.application.services.suppliermanager.IPpmSuppliersSyncFileService;
import com.work.ppm.application.services.suppliermanager.IPpmSuppliersSyncService;
import com.work.ppm.application.services.systemmanager.IPpmProjectInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  同步采购项目信息表 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmSuppliersSyncApi"})
@RestController
@RequestMapping("suppliersSync")
@Slf4j
public class PpmSuppliersSyncApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmSuppliersSyncService ppmSuppliersSyncService;

    @Autowired
    private IPpmSuppliersSyncFileService ppmSuppliersSyncFileService;

    @Autowired
    private IPpmProjectInfoService ppmProjectInfoService;



    /**
     * 查询项目信息
     * @param ppmSuppliersSyncDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryProjectList")
    @ApiOperation(value = "查询项目信息", notes = "查询项目信息")
    public ApiResult<List<PpmSuppliersSyncDto>> queryProjectList(PpmSuppliersSyncDto ppmSuppliersSyncDto) {
        ApiResult<List<PpmSuppliersSyncDto>> result =new ApiResult<>();
        result.setResults(ppmSuppliersSyncService.queryProjectList(ppmSuppliersSyncDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询项目信息
     * @param ppmSuppliersSyncDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryProjectListUpdate")
    @ApiOperation(value = "查询项目信息", notes = "查询项目信息")
    public ApiResult<List<PpmSuppliersSyncDto>> queryProjectListUpdate(PpmSuppliersSyncDto ppmSuppliersSyncDto) {
        ApiResult<List<PpmSuppliersSyncDto>> result =new ApiResult<>();
        result.setResults(ppmSuppliersSyncService.queryProjectListUpdate(ppmSuppliersSyncDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
