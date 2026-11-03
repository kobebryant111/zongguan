package com.work.ppm.application.api.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendorcontract.ExpenseSettlementDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.vendormanager.ExpenseSettlement;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IExpenseSettlementService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 费用结算 前端控制器
 * </p>
 *
 * @since 2022-11-09
 */

@Api(produces="application/json;charset=UTF-8", tags= {"ExpenseSettlementApi"})
@RestController
@RequestMapping("/expenseSettlement")
public class ExpenseSettlementApi extends BaseUploadApi {

    @Autowired
    private IExpenseSettlementService expenseSettlementService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    /**
     * 分页
     * @param  expenseSettlement 对象
     * @param pager 分页对象
     * @param request 请求
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "vendorName", value = "供应商名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "vendorCode", value = "供应商编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "taxRate", value = "税率", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "amount", value = "应付金额", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "amountTax", value = "实付金额", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "isDel", value = "逻辑删除", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "proManager", value = "项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ex01", value = "追加字段", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ex02", value = "追加字段", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ex03", value = "追加字段", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateDate", value = "更新时间", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "updateUser", value = "更新人", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(@RequestBody ExpenseSettlementDto expenseSettlement,
                                      @RequestBody Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String perm = ppmOutsourcingBasicService.getPermissionByUserid(user);
        expenseSettlement.setPerm(perm);
        expenseSettlement.setProManager(user.getUserid());
        expenseSettlement.setIsDel(LogicEnums.VALID.getCode());
        expenseSettlementService.queryPager(expenseSettlement,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 不分页查询
     * @param  map 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryListByMapBasic")
    @ApiOperation(value ="List", notes = "不分页查询,基础字段")
    public ApiResult<List> queryListByMapBasic(@RequestBody HashMap map) {
        ApiResult<List> result = new ApiResult<>();
        List<ExpenseSettlement> items= expenseSettlementService.selectByMapBasic(map);
        result.setResults(items);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询详情
     * @param expenseSettlement 对象
     * @return  ApiResult<ExpenseSettlement>
     */
    @PostMapping(value = "queryDetails")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "主键", dataType = "big_decimal", paramType = "query"),
         @ApiImplicitParam(name = "vendorName", value = "供应商名称", dataType = "string", paramType = "query"),
         @ApiImplicitParam(name = "vendorCode", value = "供应商编号", dataType = "string", paramType = "query"),
         @ApiImplicitParam(name = "taxRate", value = "税率", dataType = "big_decimal", paramType = "query"),
         @ApiImplicitParam(name = "amount", value = "应付金额", dataType = "big_decimal", paramType = "query"),
         @ApiImplicitParam(name = "amountTax", value = "实付金额", dataType = "big_decimal", paramType = "query"),
         @ApiImplicitParam(name = "isDel", value = "逻辑删除", dataType = "string", paramType = "query"),
         @ApiImplicitParam(name = "proManager", value = "项目经理", dataType = "string", paramType = "query"),
         @ApiImplicitParam(name = "ex01", value = "追加字段", dataType = "string", paramType = "query"),
         @ApiImplicitParam(name = "ex02", value = "追加字段", dataType = "string", paramType = "query"),
         @ApiImplicitParam(name = "ex03", value = "追加字段", dataType = "string", paramType = "query"),
         @ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "local_date_time", paramType = "query"),
         @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
         @ApiImplicitParam(name = "updateDate", value = "更新时间", dataType = "local_date_time", paramType = "query"),
         @ApiImplicitParam(name = "updateUser", value = "更新人", dataType = "string", paramType = "query"),
    })
    public ApiResult<ExpenseSettlementDto> queryDetails(@RequestBody ExpenseSettlement expenseSettlement) {
        ApiResult<ExpenseSettlementDto> result =new ApiResult<>();
        ExpenseSettlementDto item = expenseSettlementService.queryDetails(expenseSettlement);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 流程删除
     *
     * @param apply 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteFlow")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deleteFlow(@RequestBody ExpenseSettlement apply) {
        ApiResult result = new ApiResult<>();
        int id = expenseSettlementService.deleteFlowExpenseSettle(apply);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 发起申请
     * @param dto /
     * @param request /
     * @return ApiResult<String>
     */
    @PostMapping("approvalCirculate")
    @ApiOperation(value = "发起申请")
    public ApiResult<String> approvalCirculate(@RequestBody ExpenseSettlementDto dto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = dto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        bps.setDepId(user.getDeptId().replace("dep", ""));
        String msg = expenseSettlementService.approvalCirculate(dto,bps);
        result.success(msg);
        return result;
    }

    /**
     * 一级部门经理审批
     * @param dto 对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("confirm")
    @ApiOperation("一级部门经理审批")
    public ApiResult<String> confirm(@RequestBody ExpenseSettlementDto dto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = dto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = expenseSettlementService.confirm(dto, bps);
        result.success(msg);
        return result;
    }
}