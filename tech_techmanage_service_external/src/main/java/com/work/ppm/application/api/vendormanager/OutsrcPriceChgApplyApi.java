package com.work.ppm.application.api.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendormanager.OutsrcPriceChgApplyDto;
import com.work.ppm.application.model.vendormanager.OutsrcPriceChgApply;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IOutsrcPriceChgApplyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 人员单价调整 前端控制器
 * </p>
 *
 * @since 2022-12-18
 */

@Api(produces="application/json;charset=UTF-8", tags= {"OutsrcPriceChgApplyApi"})
@RestController
@RequestMapping("/outsrcPriceChgApply")
public class OutsrcPriceChgApplyApi extends BaseUploadApi {

    @Autowired
    private IOutsrcPriceChgApplyService outsrcPriceChgApplyService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    /**
     * 依照流程实例id查询
     * @desc 依照流程实例id查询
     * @param instanceId 流程实例id
     * @return ApiResult<List>
     */
    @PostMapping("/queryDetailsByInsId")
    @ApiOperation(value = "依照流程实例id查询记录", notes = "依照流程实例id查询记录")
    public ApiResult queryDetailsByInsId(@Param("instanceId") String instanceId) {
        ApiResult<OutsrcPriceChgApplyDto> result =new ApiResult<>();
        OutsrcPriceChgApplyDto entity = outsrcPriceChgApplyService.queryDetailsByInsId(instanceId);
        result.setResults(entity);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 流程删除
     * @param apply 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteFlow")
    @ApiOperation(value = "流程删除", notes = "流程删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程编号",
                              dataType = "String", paramType = "query")
    })
    public ApiResult deleteFlow(@RequestBody OutsrcPriceChgApply apply) {
        ApiResult result = new ApiResult<>();
        int id = outsrcPriceChgApplyService.removeFlow(apply);
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
    public ApiResult<String> approvalCirculate(HttpServletRequest request,
                                               @RequestBody OutsrcPriceChgApplyDto dto) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = dto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        bps.setDepId(user.getDeptId().replace("dep", ""));
        String msg = outsrcPriceChgApplyService.approvalCirculate(dto,bps);
        result.success(msg);
        return result;
    }

    /**
     * 二级部门经理审批
     *
     * @param dto /
     * @param request /
     * @return ApiResult<String>
     */
    @PostMapping("approvedByMgrL2")
    @ApiOperation("二级部门经理审批")
    public ApiResult<String> approvedByMgrL2(@RequestBody OutsrcPriceChgApplyDto dto,
                                             HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = dto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = outsrcPriceChgApplyService.approvedByMgrL2(dto, bps);
        result.success(msg);
        return result;
    }

    /**
     * 一级部门经理审批
     *
     * @param dto /
     * @param request /
     * @return ApiResult<String>
     */
    @PostMapping("approvedByMgrL1")
    @ApiOperation("一级部门经理审批")
    public ApiResult<String> approvedByMgrL1(@RequestBody OutsrcPriceChgApplyDto dto,
                                             HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = dto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = outsrcPriceChgApplyService.approvedByMgrL1(dto, bps);
        result.success(msg);
        return result;
    }

}