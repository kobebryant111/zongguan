package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityApplyDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.integratedmanager.IPpmZjAuthorityApplyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 闸机权限申请表 API接口
 *
 */
@Slf4j
@RestController
@RequestMapping("ppmZjAuthorityApply")
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmZjAuthorityApplyApi"})
public class PpmZjAuthorityApplyApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmZjAuthorityApplyService ppmZjAuthorityApplyService;

    /**
     * 分页
     *
     * @param ppmZjAuthorityApplyDto 对象
     * @param pager                  分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmZjAuthorityApplyDto ppmZjAuthorityApplyDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmZjAuthorityApplyService.queryPager(ppmZjAuthorityApplyDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmZjAuthorityApplyDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmZjAuthorityApplyDto> queryDetail(PpmZjAuthorityApplyDto ppmZjAuthorityApplyDto) {
        ApiResult<PpmZjAuthorityApplyDto> result = new ApiResult<>();
        PpmZjAuthorityApplyDto item = ppmZjAuthorityApplyService.queryDetail(ppmZjAuthorityApplyDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 删除
     *
     * @param ppmZjAuthorityApplyDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deletePpmZjAuthorityApply(PpmZjAuthorityApplyDto ppmZjAuthorityApplyDto) {
        ApiResult result = new ApiResult<>();
        int id = ppmZjAuthorityApplyService.deletePpmZjAuthorityApply(ppmZjAuthorityApplyDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 草稿
     *
     * @param applyDto 对象
     * @param request  request
     * @return ApiResult
     */
    @PostMapping("draft")
    @ApiOperation(value = "草稿保存")
    public ApiResult draft(@RequestBody PpmZjAuthorityApplyDto applyDto, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = applyDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        PpmZjAuthorityApplyDto dto = ppmZjAuthorityApplyService.daftPpmZjAuthorityApply(applyDto, bps);
        if(null == dto) {
            result.failure("保存失败");
        }else {
            result.success("保存成功");
            result.setResults(dto);
        }
        return result;
    }

    /**
     * 发起流程
     *
     * @param applyDto 对象
     * @param request  request
     * @return ApiResult
     */
    @PostMapping("apply")
    @ApiOperation(value = "发起流程")
    public ApiResult apply(@RequestBody PpmZjAuthorityApplyDto applyDto, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = applyDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setDepId(user.getDeptId());
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        result.success(ppmZjAuthorityApplyService.apply(applyDto, bps));
        return result;
    }

    /**
     * 二级部经理审批
     *
     * @param applyDto 对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("twoManagerAudit")
    @ApiOperation("二级部负责人审批")
    public ApiResult<String> twoManagerAudit(HttpServletRequest request,
                                             @RequestBody PpmZjAuthorityApplyDto applyDto) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = applyDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setDepId(user.getDeptId());
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        result.success(ppmZjAuthorityApplyService.twoManagerAudit(applyDto, bps));
        return result;
    }


    /**
     * 一级部负责人审批
     *
     * @param applyDto 对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("topManagerAudit")
    @ApiOperation("一级部负责人审批")
    public ApiResult<String> topManagerAudit(@RequestBody PpmZjAuthorityApplyDto applyDto,
                                             HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = applyDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setDepId(user.getDeptId());
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        result.success(ppmZjAuthorityApplyService.topManagerAudit(applyDto, bps));
        return result;
    }

}
