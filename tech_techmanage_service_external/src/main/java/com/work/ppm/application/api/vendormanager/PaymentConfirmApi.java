package com.work.ppm.application.api.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendorcontract.AttendanceInfoDto;
import com.work.ppm.application.dto.vendorcontract.PaymentConfirmDto;

import com.work.ppm.application.dto.vendormanager.PaymentConfirmPersonDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.vendormanager.PaymentConfirm;
import com.work.ppm.application.model.vendormanager.PaymentConfirmOther;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IPaymentConfirmService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 费用支付确认 前端控制器
 * </p>
 *
 * @since 2022-11-06
 */

@Slf4j
@Api(produces="application/json;charset=UTF-8", tags= {"PaymentConfirmApi"})
@RestController
@RequestMapping("/paymentConfirm")
public class PaymentConfirmApi extends BaseUploadApi {

    @Autowired
    private IPaymentConfirmService paymentConfirmService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 分页
     * @param  paymentConfirm 对象
     * @param pager 分页对象
     *  @param request 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "contractNo", value = "合同编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contractName", value = "合同名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "vendor", value = "供应商", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "paymentSubmitDate", value = "支付提交日期：项目经理提交支付流程日期",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "feeStartDate", value = "费用开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "feeEndDate", value = "费用结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "approvalStatus", value = "审批状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isDel", value = "逻辑删除", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "proManager", value = "项目经理(姓名不带工号)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "amountRaw", value = "应付金额（初始阶段）", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "amountSalt", value = "调整金额", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "amount", value = "应付总金额", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "amountTax", value = "应付总金额(含税)", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "taxRate", value = "税率", dataType = "big_decimal", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "创建时间", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "updateDate", value = "更新时间", dataType = "local_date_time", paramType = "query"),
            @ApiImplicitParam(name = "updateUser", value = "更新人", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(@RequestBody PaymentConfirmDto paymentConfirm,
                                      @RequestBody Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String perm = ppmOutsourcingBasicService.getPermissionByUserid(user);
        paymentConfirm.setPerm(perm);
        paymentConfirm.setProManager(user.getUserid());
        paymentConfirm.setIsDel(LogicEnums.VALID.getCode());
        paymentConfirmService.queryPager(paymentConfirm,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 不分页查询
     * @param  paymentConfirm 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "onePaymentConfirm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contractNo", value = "合同编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "feeStartDate", value = "费用开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "feeEndDate", value = "费用结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "proManager", value = "项目经理(姓名不带工号)", dataType = "string", paramType = "query"),
    })
    public ApiResult<PaymentConfirmDto> onePaymentConfirm(@RequestBody PaymentConfirm paymentConfirm) {
        ApiResult<PaymentConfirmDto> result = new ApiResult<>();
        PaymentConfirmDto item = paymentConfirmService.selectOne(paymentConfirm);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 人员信息
     * @param  paymentConfirm 对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPersons")
    @ApiOperation(value ="List", notes = "List")
    public ApiResult<List<PaymentConfirmPersonDto>> queryPersons(@RequestBody PaymentConfirmDto paymentConfirm) {
        ApiResult<List<PaymentConfirmPersonDto>> result = new ApiResult<>();
        List<PaymentConfirmPersonDto> item = paymentConfirmService.queryPersonList(paymentConfirm);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 费用结算点击新增时的列表查询
     * @param  paymentConfirm  /
     * @param  pager  /
     * @param  request 请求
     * @return  ApiResult<PaymentConfirm>
     */
    @PostMapping("/queryForSettle")
    public ApiResult<List> queryForSettle(@RequestBody PaymentConfirmDto paymentConfirm,
                                          @RequestBody Pager pager, HttpServletRequest request) {
        ApiResult<List> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String perm = ppmOutsourcingBasicService.getPermissionByUserid(user);
        paymentConfirm.setPerm(perm);
        paymentConfirm.setProManager(user.getUserid());
        paymentConfirmService.queryForSettle(paymentConfirm,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 依据id查询详情
     * @param  id  Long
     * @return  ApiResult<PaymentConfirm>
     */
    @ApiOperation(value = "依据id查询详情", notes = "依据id查询详情")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query", required = true),
    })
    @GetMapping("/queryDetails/{id}")
    public ApiResult queryDetailsById(@PathVariable("id") Long id) {
        ApiResult<PaymentConfirmDto> result =new ApiResult<>();
        PaymentConfirmDto dto = paymentConfirmService.queryDetailsById(id);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 依据id查询详情
     * @param  instanceId /
     * @return  ApiResult<PaymentConfirmDto>
     */
    @ApiOperation(value = "依据id查询详情", notes = "依据id查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceId", value = "", dataType = "String",
                    paramType = "query", required = true),
    })
    @GetMapping("/queryDetailsByInstanceId/{id}")
    public ApiResult queryDetailsByInstanceId(@PathVariable("id") String instanceId) {

        ApiResult<PaymentConfirmDto> result =new ApiResult<>();
        PaymentConfirm item = paymentConfirmService.queryDetailsByInstanceId(instanceId);
        PaymentConfirmDto dto = JsonUtil.jsonToClass(item, PaymentConfirmDto.class);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
    /**
     * 依据ids查询详情列表
     * @param  ids  String
     * @return ApiResult<List<PaymentConfirm>>
     */
    @ApiOperation(value = "依据id查询详情", notes = "依据id查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/queryListByIds/{ids}")
    public ApiResult<List<PaymentConfirm>> queryDetailsById(@PathVariable("ids") String ids) {
        ApiResult<List<PaymentConfirm>> result =new ApiResult<>();
        if (!StringUtil.isStringNotEmpty(ids)) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "参数不为空");
        }
        try {
            List list = Arrays.stream(ids.split(",")).map(Long::parseLong)
                    .collect(Collectors.toList());
            List<PaymentConfirm> item = paymentConfirmService.queryListByIds(list);
            result.setResults(item);
            result.setStatus(ApiCode.SUCCESS);
        } catch (NumberFormatException e) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage("For input string < abnormal digit type > ");
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
        }
        return result;
    }

    /**
     * 流程删除
     * @param apply 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteFlow")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deleteFlow(@RequestBody PaymentConfirm apply) {
        ApiResult result = new ApiResult<>();
        int id = paymentConfirmService.deleteFlowPayConfirm(apply);
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
     * @return ApiResult
     */
    @PostMapping("approvalCirculate")
    @ApiOperation(value = "发起申请")
    public ApiResult<String> approvalCirculate(HttpServletRequest request, @RequestBody PaymentConfirmDto dto) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = dto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        bps.setDepId(user.getDeptId().replace("dep", ""));
        String msg = paymentConfirmService.approvalCirculate(dto,bps);
        result.success(msg);
        return result;
    }

    /**
     * 草稿
     * @param dto /
     * @param request   request
     * @return ApiResult
     */
    @PostMapping("draft")
    @ApiOperation(value = "草稿")
    public ApiResult<PaymentConfirmDto> draft(@RequestBody PaymentConfirmDto dto, HttpServletRequest request) {
        ApiResult<PaymentConfirmDto> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = dto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        bps.setDepId(user.getDeptId().replace("dep", ""));
        PaymentConfirmDto apply = paymentConfirmService.draftPayment(dto,bps);
        result.success("msg ... ");
        result.setResults(apply);
        return result;
    }

    /**
     * 二级部门经理审批
     *
     * @param dto /
     * @param request /
     * @return ApiResult<String>
     */
    @PostMapping("confirm")
    @ApiOperation("二级部门经理审批")
    public ApiResult<String> confirm(@RequestBody PaymentConfirmDto dto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = dto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = paymentConfirmService.confirm(dto, bps);
        result.success(msg);
        return result;
    }

    /**
     * 依据流程实例id更新记录审批状态
     * @param paymentConfirm /
     * @return ApiResult<String>
     */
    @PutMapping("/updateByInstanceId")
    @ApiOperation(value = "依据流程实例id更新记录审批状态", notes = "依据流程实例id更新记录审批状态")
    public ApiResult<String> updateByInstanceId(@RequestBody PaymentConfirm paymentConfirm) {
        ApiResult<String> result = new ApiResult<>();
        int id = paymentConfirmService.updateByInstanceId(paymentConfirm);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }
    /**
     * 支付确认-人力结算费用导出
     *
     * @param paymentConfirm /
     * @param request        /
     * @param response       /
     */
    @PostMapping("exportExcelPersons")
    @ApiOperation(value = "支付确认-人力结算费用导出")
    public void exportPersonFee(@RequestBody PaymentConfirmDto paymentConfirm,
                                   HttpServletRequest request, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        List<String> tmp = new ArrayList<>();
        tmp.add("tmp");
        // jobIdList为空时给默认值
        if ((Objects.isNull(paymentConfirm.getJobIdList()) || paymentConfirm.getJobIdList().size() == 0)){
            paymentConfirm.setJobIdList(tmp);
        }
        List<PaymentConfirmOther> list = paymentConfirmService.exportPersonFee(paymentConfirm);
        ExcelUtil.exportExcel(list, "人力结算费用", "name1", PaymentConfirmOther.class,
                "人力结算费用.xls", true, response);
        result.success("数据导出成功");
    }

    /**
     * 支付确认-考勤明细导出
     *
     * @param paymentConfirm /
     * @param request        /
     * @param response       /
     */
    @PostMapping("exportAttendanceDetails")
    @ApiOperation(value = "支付确认-考勤明细导出")
    public void exportAttendanceDetails(@RequestBody PaymentConfirmDto paymentConfirm,
                                   HttpServletRequest request, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        List<String> tmp = new ArrayList<>();
        tmp.add("tmp");
        // jobIdList为空时给默认值
        if ((Objects.isNull(paymentConfirm.getJobIdList()) || paymentConfirm.getJobIdList().size() == 0)){
            paymentConfirm.setJobIdList(tmp);
        }
        List<AttendanceInfoDto> list = paymentConfirmService.queryAttendanceDetails(paymentConfirm);
        ExcelUtil.exportExcel(list, AttendanceInfoDto.class,"人员考勤详情.xls", response);
        result.success("数据导出成功");
    }

}