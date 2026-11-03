package com.work.ppm.application.api.integratedmanager;

import cn.hutool.json.JSONUtil;
import com.work.ppm.application.model.integratedmanager.PpmSupplierContractRequest;
import com.work.ppm.application.model.integratedmanager.PpmSupplierContractResponse;
import com.work.ppm.application.model.integratedmanager.PpmSupplierOutsourcingRequest;
import com.work.ppm.application.services.integratedmanager.impl.PpmSupplierContractService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 供应商合同管理关联外包人员
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmSupplierContractManageApi"}, description = "供应商合同管理")
@RestController
@RequestMapping("contractManage")
@Slf4j
public class PpmSupplierContractManageApi extends BaseApi {

    /**
     * 供应商合同管理服务
     */
    @Autowired
    private PpmSupplierContractService supplierContractService;

    /**
     * 分页
     *
     * @param request 对象
     * @param pager   分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public ApiResult queryPagerList(PpmSupplierContractRequest request, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        log.info("供应商合同分页查询请求参数{}", JSONUtil.toJsonStr(request));
        supplierContractService.queryPagerList(request, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     *
     * @param request 对象
     * @return ApiResult<PpmOutsourcingBasic>
     */
    @PostMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmSupplierContractResponse> queryDetail(@RequestBody PpmSupplierContractRequest request) {
        ApiResult<PpmSupplierContractResponse> result = new ApiResult<>();
        log.info("供应商合同查询详情请求参数{}", JSONUtil.toJsonStr(request));
        PpmSupplierContractResponse item = supplierContractService.queryDetail(request);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 合同新增/修改
     *
     * @param request 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "add")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<PpmSupplierContractRequest> updateSupplierContract(@RequestBody PpmSupplierContractRequest request) {
        ApiResult<PpmSupplierContractRequest> result = new ApiResult<>();
        log.info("供应商合同修改请求参数{}", JSONUtil.toJsonStr(request));
        boolean row = supplierContractService.updateSupplierContract(request);
        if (row) {
            result.setResults(request);
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 合同删除
     *
     * @param request 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deletePpmSupplierContract(@RequestBody PpmSupplierContractRequest request) {
        ApiResult result = new ApiResult<>();
        boolean row = supplierContractService.deletePpmSupplierContract(request);
        if (row) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 查询合同关联外包人员
     *
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryOutsourceList")
    public ApiResult<PpmSupplierContractResponse> queryOutsourceList(@RequestBody PpmSupplierContractRequest request) {
        ApiResult<PpmSupplierContractResponse> result = new ApiResult();
        log.info("查询合同关联外包人员请求参数{}", JSONUtil.toJsonStr(request));
        PpmSupplierContractResponse item = supplierContractService.queryOutsourceList(request);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(item);
        return result;
    }

    /**
     * 新增合同与外包人员关联
     *
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "addOutsource")
    public ApiResult addOutsource(@RequestBody PpmSupplierOutsourcingRequest request) {
        log.info("更新合同关联外包人员请求参数{}", JSONUtil.toJsonStr(request));
        boolean item = supplierContractService.addOutsource(request);
        ApiResult result = item ? ApiResult.newSuccess() : ApiResult.newFailure("更新失败");
        return result;
    }


    /**
     * 解除外包人员关联
     *
     * @param request 对象
     * @return PpmOutsourcingBasicInfo
     */
    @PostMapping(value = "deleteOutsource")
    public ApiResult deleteOutsource(@RequestBody PpmSupplierOutsourcingRequest request) {
        log.info("解除外包人员关联请求参数{}", JSONUtil.toJsonStr(request));
        boolean item = supplierContractService.deleteOutsource(request);
        ApiResult result = item ? ApiResult.newSuccess() : ApiResult.newFailure("更新失败");
        return result;
    }

    /**
     * 删除外包人员关联
     *
     * @param request 对象
     * @return PpmOutsourcingBasicInfo
     */
    @PostMapping(value = "removeOutsource")
    public ApiResult removeOutsource(@RequestBody PpmSupplierOutsourcingRequest request) {
        log.info("删除外包人员关联{}", JSONUtil.toJsonStr(request));
        boolean item = supplierContractService.removeOutsource(request);
        ApiResult result = item ? ApiResult.newSuccess() : ApiResult.newFailure("更新失败");
        return result;
    }

    /**
     * 供应商名单导出
     *
     * @param request request
     */
    @PostMapping("exportInfo")
    @ApiOperation(value = "供应商名单导出")
    public void exportSupplier(@RequestBody PpmSupplierContractRequest contractDTO,
                               HttpServletRequest request, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        List<PpmSupplierContractResponse> list =
                supplierContractService.exportSupplier(contractDTO);
        ExcelUtil.exportExcel(list, PpmSupplierContractResponse.class, "supplierInfo.xlsx", response);
        result.success("数据导出成功");
    }

}
