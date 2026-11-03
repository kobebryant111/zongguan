package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmApplyvirtualInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.integratedmanager.IPpmApplyvirtualInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingAccessapplyService;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.utils.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 虚拟桌面
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"VirtualDesktopApi"},description="虚拟桌面")
@RestController
@RequestMapping("virtualDesktop")
@Slf4j
public class VirtualDesktopApi extends BaseApi {
    /**
     * 外包准入接口
     */
    @Autowired
    private IPpmOutsourcingAccessapplyService ppmOutsourcingAccessapplyService;
    /**
     * 虚拟桌面接口
     */
    @Autowired
    private IPpmApplyvirtualInfoService ppmApplyvirtualInfoService;

    /**
     * 宁波地区管理审批
     * @param ppmApplyvirtualInfoDto 对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("nbAreaManagerApprove")
    @ApiOperation(value = "宁波地区管理审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userType", value = "人员类型", dataType = "string", paramType = "query", required =
                    true),
            @ApiImplicitParam(name = "username", value = "人员名称", dataType = "string", paramType = "query",
                    required = true),
            @ApiImplicitParam(name = "usercode", value = "人员工号", dataType = "string", paramType = "query",
                    required = true),
            @ApiImplicitParam(name = "virtualNo", value = "虚拟桌面号", dataType = "string", paramType = "query",
                    required = true),
            @ApiImplicitParam(name = "xmjl", value = "项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "xmjlcode", value = "项目经理号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ssxm", value = "所属项目", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pzx", value = "配置项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "others", value = "其他", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query", defaultValue =
                    "1", required = true),
    })
    public ApiResult nbAreaManagerApprove(@RequestBody PpmApplyvirtualInfoDto ppmApplyvirtualInfoDto, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        AssertUtil.notNull(ppmApplyvirtualInfoDto.getVirtualNo(),"【虚拟桌面号】不能为空" );
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmApplyvirtualInfoDto.getBps().setUserId(user.getUserid());
        String messge = ppmApplyvirtualInfoService.nbAreaManagerApprove(ppmApplyvirtualInfoDto);
        result.success(messge);
        return result;
    }

    /**
     * 获取申请虚拟桌面详情
     * @param bps 流程对象
     * @return ApiResult
     */
    @GetMapping("getDateil")
    @ApiOperation(value = "获取申请虚拟桌面详情", notes = "根据processInstId 查询详情")
    @ApiImplicitParam(name = "processInstId", value = "流程实例id", required = true, paramType = "query")
    public ApiResult<PpmOutsourcingAccessapplyDto> getDateil(BpsWorkFlowDto bps) {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult();
        PpmOutsourcingAccessapplyDto dto = new PpmOutsourcingAccessapplyDto();
        dto.setVirtualDesktopFlowId(bps.getProcessInstId());
        PpmOutsourcingAccessapplyDto item = ppmOutsourcingAccessapplyService.queryDetail(dto);
        result.success("获取成功");
        result.setResults(item);
        return result;
    }

    /**
     * 获取SVN申請详情
     * @param bps 流程对象
     * @return ApiResult
     */
    @GetMapping("getSvnDateil")
    @ApiOperation(value = " 获取SVN申請详情", notes = "根据processInstId 查询详情")
    @ApiImplicitParam(name = "processInstId", value = "流程实例id", required = true, paramType = "query")
    public ApiResult<PpmOutsourcingAccessapplyDto> getSvnDateil(BpsWorkFlowDto bps) {
        ApiResult<PpmOutsourcingAccessapplyDto> result = new ApiResult();
        PpmOutsourcingAccessapplyDto dto = new PpmOutsourcingAccessapplyDto();
        dto.setApplysvnno(bps.getProcessInstId());
        PpmOutsourcingAccessapplyDto item = ppmOutsourcingAccessapplyService.queryDetail(dto);
        result.success("获取成功");
        result.setResults(item);
        return result;
    }
    /**
     * svn申请审批
     * @param bps 对象
     * @return APIResult
     */
    @PostMapping("svnApprove")
    @ApiOperation(value = "svn申请审批", notes = "svn申请审批只能通过")
    public ApiResult svnApprove(@RequestBody BpsWorkFlowDto bps, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String messge = ppmOutsourcingAccessapplyService.svnApprove(bps);
        result.success(messge);
        return result;
    }

    /**
     * 检查虚拟桌面号 是否重复
     * @param virtualNo 虚拟桌面号
     * @return ApiResult
     */
    @GetMapping("checkedVirtualNo")
    @ApiOperation(value = "检查虚拟桌面号 是否重复")
    @ApiImplicitParam(name = "virtualNo", value = "虚拟桌面号", required = true, paramType = "query")
    public ApiResult checkedVirtualNo(String virtualNo) {
        ApiResult result = new ApiResult();
        boolean falg = ppmApplyvirtualInfoService.checkedVirtualNo(virtualNo);
        result.setResults(falg);
        result.success("获取成功");
        return result;
    }
}
