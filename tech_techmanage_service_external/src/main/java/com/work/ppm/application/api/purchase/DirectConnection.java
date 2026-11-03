package com.work.ppm.application.api.purchase;

import com.work.ppm.application.dto.rdmanager.RdProjectContractQueryDto;
import com.work.ppm.application.dto.rdmanager.RdPurchaseContract;
import com.work.ppm.application.model.rdmanager.RdProjectContract;
import com.work.ppm.application.services.purchase.impl.DirectConnectionService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(produces = "application/json;charset=UTF-8", tags = {"DirectConnection"}, description = "采购直连")
@RestController
@RequestMapping("purchase")
@Slf4j
@Validated
public class DirectConnection {


    @Autowired
    private DirectConnectionService directConnectionService;

    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contractCode", value = "合同编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractName", value = "合同名称", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })

    public ApiResult<List> queryPager(RdProjectContractQueryDto req, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        directConnectionService.queryPager(req, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    @PostMapping(value = "imports")
    @ApiOperation(value = "确认导入", notes = "确认导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contractCode", value = "合同编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractName", value = "合同名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "budgetCode", value = "预算编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contractStartDate", value = "合同开始时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contractEndDate", value = "合同结束时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "maintenanceStartDate", value = "维保开始时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "maintenanceEndDate", value = "维保结束时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contractAmount", value = "合同金额", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contractTaxPoints", value = "合同税点", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "invoiceType", value = "发票类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supplierName", value = "供应商全称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contractPayRecords", value = "付款信息", dataType = "ArrayList<RdContractPayRecord>", paramType = "query"),
    })
    public ApiResult<List> imports(@RequestBody RdPurchaseContract req) {
        directConnectionService.imports(req);
        return ApiResult.newSuccess();
    }

}
