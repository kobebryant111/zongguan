package com.work.ppm.application.api.suppliermanager;

import com.work.ppm.application.dto.othermanager.PpmOutsourcingInfoDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRosterInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.othermanager.IPpmOutsourcingInfoService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRosterInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.StreamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  供应商花名册表 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmSupplierRosterApi"})
@RestController
@RequestMapping("supplierRoster")
@Slf4j
public class PpmSupplierRosterApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmSupplierRosterInfoService supplierRosterInfoService;

    /**
     *	外包商公司 接口
     */
    @Autowired
    private IPpmOutsourcingInfoService ppmOutsourcingInfoService;

    /**
     * 添加
     * @param ppmOutsourcingInfoDto 对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "addRoster")
    @ApiOperation(value = "添加", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outsourcname", value = "供应商名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplierRosterInfoDtos",
                    value = "扩展详情集合", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outsourcingInfoPriceDtos",
                    value = "价格详情集合", dataType = "string", paramType = "query"),
    })
    public ApiResult addRoster(@RequestBody PpmOutsourcingInfoDto ppmOutsourcingInfoDto,
                                          HttpServletRequest request) {
        ApiResult result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        ppmOutsourcingInfoService.addSupplierRoster(ppmOutsourcingInfoDto,user);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 添加
     * @param ppmOutsourcingInfoDto 对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "updateRoster")
    @ApiOperation(value = "添加", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assessYear", value = "供应商编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pmValue", value = "项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "所属部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplierId", value = "所属部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "assessType", value = "评估类型", dataType = "string", paramType = "query"),
    })
    public ApiResult updateRoster(@RequestBody PpmOutsourcingInfoDto ppmOutsourcingInfoDto,HttpServletRequest request) {
        ApiResult result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        ppmOutsourcingInfoService.updateSupplierRoster(ppmOutsourcingInfoDto,user);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询详情
     * @param ppmOutsourcingInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryRoster")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "string", paramType = "query"),
    })
    public ApiResult<PpmOutsourcingInfoDto> queryRoster(PpmOutsourcingInfoDto ppmOutsourcingInfoDto) {
        ApiResult<PpmOutsourcingInfoDto> result = new ApiResult<>();
        result.setResults(ppmOutsourcingInfoService.querySupplierRoster(ppmOutsourcingInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 删除
     * @param ppmOutsourcingInfoDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteRoster")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "string", paramType = "query"),
    })
    public ApiResult deleteRoster(@RequestBody PpmOutsourcingInfoDto ppmOutsourcingInfoDto) {
        ApiResult result =new ApiResult<>();

        ppmOutsourcingInfoService.deleteSupplierRoster(ppmOutsourcingInfoDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 导入考勤信息
     *
     * @param file    对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("importRoster")
    @ApiOperation(value = "导入供应商花名册")
    public ApiResult<Object> importRoster(
            @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        log.info("{}开始导入供应商花名册信息！", "测试开始");
        List<PpmSupplierRosterInfoDto> list = ExcelUtil.importExcel(file,0,1,PpmSupplierRosterInfoDto.class);


        if(list == null){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("导入无数据或模板有误");
            return result;
        }

        List<PpmSupplierRosterInfoDto> rosterInfoDtos =
                list.stream().filter(x -> StringUtils.isNotEmpty(x.getRid())).collect(Collectors.toList());

        long listSize = rosterInfoDtos.stream().filter(x -> x.getRid() == null).count();
        if(listSize == rosterInfoDtos.size()){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("导入无数据或模板有误");
            return result;
        }

        ppmOutsourcingInfoService.importSupplierRoster(rosterInfoDtos,user);

        result.setMessage("true");
        result.setStatus(ApiCode.SUCCESS);
        log.info("导入供应商信息成功");
        return result;
    }


    /**
     * 导出信息接口
     * @param ppmOutsourcingInfoDto 对象
     * @param request request
     * @param response response
     */
    @PostMapping("exportRoster")
    @ApiOperation(value = "供应商花名册导出")
    public void exportRoster(@RequestBody PpmOutsourcingInfoDto ppmOutsourcingInfoDto,
                             HttpServletRequest request, HttpServletResponse response) {
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        List<PpmSupplierRosterInfoDto> list =
                supplierRosterInfoService.exportSupplierRoster(ppmOutsourcingInfoDto,user);
        if(!list.isEmpty()){
            ExcelUtil.exportExcel(list, PpmSupplierRosterInfoDto.class,"供应商花名册.xls",response);
        }
        log.info("导出成功。");
    }

    /**
     * 查询项目经理
     *
     * @param ppmSupplierRosterInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryOneYears")
    @ApiOperation(value = "查询项目", notes = "查询项目")
    public ApiResult<List<PpmSupplierRosterInfoDto>> queryOneYears(PpmSupplierRosterInfoDto ppmSupplierRosterInfoDto) {
        ApiResult<List<PpmSupplierRosterInfoDto>> result = new ApiResult<>();
        List<PpmSupplierRosterInfoDto> list = supplierRosterInfoService.getFinishOneYears(ppmSupplierRosterInfoDto);
        list = list.stream().filter(StreamUtils.distinctByKey(PpmSupplierRosterInfoDto::getSupplierCode))
                .collect(Collectors.toList());
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }





}
