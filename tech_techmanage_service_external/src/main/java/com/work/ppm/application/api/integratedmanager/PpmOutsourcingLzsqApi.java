package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingLzsqInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingLzsqService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.exception.BizException;
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
 * 外包离职流程接口
 *
 */
@Slf4j
@RestController
@RequestMapping("ppmOutsourcingLzsq")
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingLzsqApi"}, description = "外包离职流程接口")
public class PpmOutsourcingLzsqApi extends BaseApi {
    /**
     * 外包离职流程接口
     */
    @Autowired
    private IPpmOutsourcingLzsqService ppmOutsourcingLzsqService;
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    /**
     * 分页
     *
     * @param ppmOutsourcingLzsq 对象
     * @param pager              对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOutsourcingLzsq ppmOutsourcingLzsq, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmOutsourcingLzsqService.queryPager(ppmOutsourcingLzsq, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmOutsourcingLzsq 对象
     * @return ApiResult<PpmOutsourcingLzsq>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmOutsourcingLzsq> queryDetail(PpmOutsourcingLzsq ppmOutsourcingLzsq) {
        ApiResult<PpmOutsourcingLzsq> result = new ApiResult<>();
        PpmOutsourcingLzsq item = ppmOutsourcingLzsqService.queryDetail(ppmOutsourcingLzsq);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     *
     * @param ppmOutsourcingLzsq 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmOutsourcingLzsq(PpmOutsourcingLzsq ppmOutsourcingLzsq) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmOutsourcingLzsqService.updatePpmOutsourcingLzsq(ppmOutsourcingLzsq);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     *
     * @param ppmOutsourcingLzsq 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),
    })
    public ApiResult deletePpmOutsourcingLzsq(PpmOutsourcingLzsq ppmOutsourcingLzsq) {
        ApiResult result = new ApiResult<>();
        int id = ppmOutsourcingLzsqService.deletePpmOutsourcingLzsq(ppmOutsourcingLzsq);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 保存流程数据
     *
     * @param info 对象
     * @return ApiResult<PpmOutsourcingLzsq>
     */
    @PostMapping("save")
    @ApiOperation("保存流程数据")
    public ApiResult<PpmOutsourcingLzsq> save(PpmOutsourcingLzsq info, HttpServletRequest request) {
        ApiResult<PpmOutsourcingLzsq> result = new ApiResult<>();
        BpsWorkFlowDto bps = new BpsWorkFlowDto();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        PpmOutsourcingBasicInfo basicInfo = ppmOutsourcingBasicService.getBaseInfo(info.getJobid());
        if (!user.getUserid().equals("sysadmin")) {
            throw new BizException(ApiCode.FAIL, "只允许文员发起离场申请！");
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        PpmOutsourcingLzsq dto = ppmOutsourcingLzsqService.save(info, bps);
        if (null == dto) {
            result.failure("保存失败");
        } else {
            result.success("保存成功");
            result.setResults(dto);
        }
        return result;
    }

    /**
     * 发起申请 20260312 需求变更总经理室审批
     *
     * @param ppmOutsourcingLzsqInfoDto 对象
     * @param request                   request
     * @return ApiResult<String>
     */
    @PostMapping("outLeaveStart")
    @ApiOperation("发起申请")
    public ApiResult<String> outLeaveStart(@RequestBody PpmOutsourcingLzsqInfoDto ppmOutsourcingLzsqInfoDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = ppmOutsourcingLzsqInfoDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        PpmOutsourcingLzsq lzsq = ppmOutsourcingLzsqInfoDto.getLzsq();
        PpmOutsourcingBasicInfo basicInfo = ppmOutsourcingBasicService.getBaseInfo(lzsq.getJobid());
        if (!user.getUserid().equals("sysadmin")) {
            throw new BizException(ApiCode.FAIL, "只允许文员发起离场申请！");
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = ppmOutsourcingLzsqService.outLeaveStart(lzsq, bps);
        result.success(msg);
        return result;
    }

    /**
     * 二级部经理审批
     *
     * @param ppmOutsourcingLzsqInfoDto 对象
     * @param request                   request
     * @return ApiResult<String>
     */
    @PostMapping("managerApprove")
    @ApiOperation("二级部经理审批")
    public ApiResult<String> managerApprove(@RequestBody PpmOutsourcingLzsqInfoDto ppmOutsourcingLzsqInfoDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = ppmOutsourcingLzsqInfoDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = ppmOutsourcingLzsqService.managerApprove(ppmOutsourcingLzsqInfoDto.getLzsq(), bps);
        result.success(msg);
        return result;
    }

    /**
     * 根据流程instanceid查询数据
     *
     * @param info 对象
     * @return ApiResult<PpmOutsourcingLzsqInfoDto>
     */
    @PostMapping("getBaseInfo")
    @ApiOperation("根据流程instanceid查询数据")
    public ApiResult<PpmOutsourcingLzsqInfoDto> getBaseInfo(PpmOutsourcingLzsq info) {
        ApiResult<PpmOutsourcingLzsqInfoDto> result = new ApiResult<>();
        PpmOutsourcingLzsqInfoDto dto = ppmOutsourcingLzsqService.getBaseInfo(info);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * isExist
     *
     * @param ppmOutsourcingLzsq 对象
     * @return ApiResult<String>
     */
    @PostMapping("isExist")
    @ApiOperation("判断流程是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
    })
    public ApiResult<String> isExist(PpmOutsourcingLzsq ppmOutsourcingLzsq) {
        ApiResult<String> result = new ApiResult<>();
        boolean flag = ppmOutsourcingLzsqService.isExist(ppmOutsourcingLzsq.getJobid());
        result.success(flag + "");
        return result;
    }
}
