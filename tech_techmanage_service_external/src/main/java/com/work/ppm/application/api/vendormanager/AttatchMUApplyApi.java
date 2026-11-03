package com.work.ppm.application.api.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendorcontract.AttatchMUApplyDto;
import com.work.ppm.application.model.vendormanager.AttatchMUApply;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.vendormanager.IAttatchMUApplyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
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
 * 附件管理更新申请 前端控制器
 * </p>
 *
 * @since 2022-11-02
 */

@Api(produces="application/json;charset=UTF-8", tags= {"AttatchMUApplyApi"})
@RestController
@RequestMapping("/attatchMUApply")
public class AttatchMUApplyApi extends BaseUploadApi {

    @Autowired
    private IAttatchMUApplyService attatchMUApplyService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    /**
     * 项目经理发起申请
     *
     * @param dto 对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("approvalCirculate")
    @ApiOperation("发起申请")
    public ApiResult<String> approvalCirculate(@RequestBody AttatchMUApplyDto dto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = dto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        dto.setRecordId(dto.getId());
        String msg = attatchMUApplyService.approvalCirculate(dto,bps);
        result.success(msg);
        return result;
    }

    /**
     * 二级部门经理审批
     *
     * @param dto 对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("confirm")
    @ApiOperation("二级部门经理审批")
    public ApiResult<String> confirm(@RequestBody AttatchMUApplyDto dto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = dto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = attatchMUApplyService.confirm(dto, bps);
        result.success(msg);
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
        List<AttatchMUApply> items= attatchMUApplyService.selectByMapBasic(map);
        result.setResults(items);
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
    public ApiResult deleteFlow(@RequestBody AttatchMUApply apply) {
        ApiResult result = new ApiResult<>();
        int id = attatchMUApplyService.deleteFlowAttachApply(apply);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

}