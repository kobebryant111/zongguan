package com.work.ppm.application.api.flowmanager;

import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.flowmanager.ICommonFlowService;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.workflow.client.soentity.BPSWorkItem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * bps流程公共类
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"commonFlowApi"}, description = "bps流程公共类")
@RestController
@RequestMapping("commonFlowApi")
@Slf4j
public class CommonFlowApi extends BaseApi {
    /**
     * 公共流程接口
     */
    @Autowired
    private ICommonFlowService commonFlowService;

    /**
     * 我的待办
     *
     * @param dto     对象
     * @param request request
     * @return ApiResult<List   <   BPSWorkItem>>
     */
    @GetMapping("getdb")
    @ApiOperation(value = "我的待办", notes = "我的待办")
    public ApiResult<List<BPSWorkItem>> getdbWorkItem(BpsWorkFlowDto dto, HttpServletRequest request) {
        ApiResult<List<BPSWorkItem>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        dto.setUserId(user.getUserid());
        List<BPSWorkItem> list = commonFlowService.getdbWorkItem(dto);
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 我的已办
     *
     * @param dto     对象
     * @param request request
     * @return ApiResult<List   <   BPSWorkItem>>
     */
    @GetMapping("getyb")
    @ApiOperation("我的已办")
    public ApiResult<List<BPSWorkItem>> getybWorkItem(BpsWorkFlowDto dto, HttpServletRequest request) {
        ApiResult<List<BPSWorkItem>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        dto.setUserId(user.getUserid());
        List<BPSWorkItem> list = commonFlowService.getybWorkItem(dto);
        result.setResults(list);
        result.success("获取成功");
        return result;
    }

    /**
     * 我的归档
     *
     * @param dto     对象
     * @param pager   分页对象
     * @param request request
     * @return ApiResult<List   <   PpmProjectFlowDto>>
     */
    @GetMapping("getgd")
    @ApiOperation("我的归档")
    public ApiResult<List<PpmProjectFlowDto>> getgdWorkItem(PpmProjectFlowDto dto, Pager pager, HttpServletRequest request) {
        ApiResult<List<PpmProjectFlowDto>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        dto.setLastmoduser(user.getUserid());
        commonFlowService.getGdWorkItem(dto, pager);
        result.setResults((List<PpmProjectFlowDto>) pager.getList());
        result.setTotal(pager.getTotal());
        result.success("获取成功");
        return result;
    }

    /**
     * 获取授权列表
     *
     * @param dto 对象
     * @param request request
     * @return ApiResult<List < CommTreeDto>>
     */
    @GetMapping("getAuthList")
    @ApiOperation(value = "获取授权列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptNo", value = "部门编号", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "userData", value = "用户数据", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "users", value = "用户id，多个, 分开", dataType = "string", paramType = "query", required = true),
    })
    public ApiResult<List<CommTreeDto>> getAuthList(PpmProjectFlowDto dto, HttpServletRequest request) {
        ApiResult<List<CommTreeDto>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        if(!StringUtil.isNotEmpty(dto.getDeptNo())) {
            dto.setDeptNo(user.getDeptId());
        }
        List<CommTreeDto> treeList = commonFlowService.getAuthList(dto);
        result.setResults(treeList);
        result.success("获取成功");
        return result;
    }

    /**
     * 执行授权
     *
     * @param dto     对象
     * @param request request
     * @return ApiResult<String>
     */
    @PostMapping("doAuth")
    @ApiOperation(value = "流程授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personId", value = "指派的人员id（多个以“@“分割）", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "personName", value = "指派的人员name（多个以“@“分割）", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "processInstID", value = "流程id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "workItemId", value = "工作项id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "processInstName", value = "流程实例名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "processDefName", value = "流程定义名称 (com.work.ppm.bps.glzxzdgz)", dataType = "string", paramType = "query", required = true)
    })
    public ApiResult<String> doAuth(BpsWorkFlowDto dto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        dto.setUserId(user.getUserid());
        String msg = commonFlowService.doAuth(dto);
        result.setResults(msg);
        result.success("授权成功");
        return result;
    }

    /**
     * 删除流程
     *
     * @param bps     对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("doDel")
    @ApiOperation(value = "删除流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstID", value = "流程id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "workItemId", value = "工作项id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "processDefName", value = "流程定义名称", dataType = "string", paramType = "query", required = true),
    })
    public ApiResult doDel(BpsWorkFlowDto bps, HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = commonFlowService.doDel(bps);
        result.success("删除流程成功:" + msg);
        return result;
    }

    /**
     * 撤回流程
     *
     * @param bps     对象
     * @param request request
     * @return ApiResult<String>
     */
    @PostMapping("drawback")
    @ApiOperation(value = "撤回流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstID", value = "流程id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "workItemId", value = "工作项id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "activityInstName", value = "审批节点描述",
                    dataType = "string", paramType = "query", required = true)
    })
    public ApiResult<String> drawbackWorkItem(BpsWorkFlowDto bps, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = commonFlowService.drawbackWorkItem(bps);
        result.success(msg);
        return result;
    }

    /**
     * 判断是否可以撤回工作项
     *
     * @param bps     对象
     * @param request request
     * @return ApiResult<BpsWorkFlowDto>
     */
    @GetMapping("doIsDrawbackEnable")
    @ApiOperation(value = "判断是否可以撤回工作项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstID", value = "流程id", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "workItemId", value = "工作项id", dataType = "string", paramType = "query", required = true)
    })
    public ApiResult<BpsWorkFlowDto> doIsDrawbackEnable(BpsWorkFlowDto bps, HttpServletRequest request) {
        ApiResult<BpsWorkFlowDto> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        bps.setUserId(user.getUserid());
        BpsWorkFlowDto msg = commonFlowService.doIsDrawbackEnable(bps);
        result.setResults(msg);
        result.success("获取成功");
        return result;
    }

    /**
     * 查询已完成但未结束的流程信息
     *
     * @param bps     对象
     * @param request request
     * @return ApiResult<BPSWorkItem>
     */
    @GetMapping("doFinishedLastWorkitem")
    @ApiOperation(value = "查询已完成但未结束的流程信息")
    @ApiImplicitParam(name = "processInstID", value = "流程id", dataType = "string", paramType = "query", required = true)
    public ApiResult<BPSWorkItem> doFinishedLastWorkitem(BpsWorkFlowDto bps, HttpServletRequest request) {
        ApiResult<BPSWorkItem> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        bps.setUserId(user.getUserid());
        BPSWorkItem item = commonFlowService.doFinishedLastWorkitem(bps);
        result.setResults(item);
        result.success("获取成功");
        return result;
    }


    /**
     * 获取待办工作项详情-单条
     *
     * @param bps 对象
     * @return ApiResult<BPSWorkItem>
     */
    @GetMapping("queryWorkItem")
    @ApiOperation(value = "获取待办工作项详情-单条", notes = "这里指单条数据详情")
    @ApiImplicitParam(name = "processInstID", value = "流程id", dataType = "string", paramType = "query", required = true)
    public ApiResult<BPSWorkItem> queryWorkItem(BpsWorkFlowDto bps) {
        ApiResult<BPSWorkItem> result = new ApiResult();
        BPSWorkItem item = commonFlowService.queryWorkItem(bps);
        result.success("获取成功");
        result.setResults(item);
        return result;
    }

    /**
     * 查询归档详情
     *
     * @param bps 对象
     * @return ApiResult
     */
    @GetMapping("queryGdDetail")
    @ApiOperation(value = "查询归档详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceid", value = "流程实例id")
    })
    public ApiResult<PpmProjectFlowDto> queryGdDetail(BpsWorkFlowDto bps) {
        ApiResult<PpmProjectFlowDto> result = new ApiResult<>();
        PpmProjectFlowDto item = commonFlowService.queryGdDetail(bps.getProcessInstId());
        result.setResults(item);
        result.success("查询成功");
        return result;
    }

    /**
     * 查询流程状态
     *
     * @param bps 对象
     * @return ApiResult
     */
    @GetMapping("getProcessInstState")
    @ApiOperation(value = "查询流程状态")
    public ApiResult<Boolean> getProcessInstState(BpsWorkFlowDto bps) {
        ApiResult<Boolean> result = new ApiResult<>();
        boolean falg = commonFlowService.getProcessInstState(bps);
        result.setResults(falg);
        result.success("查询成功");
        return result;
    }

    /**
     * 查询流程info
     *
     * @param bps 对象
     * @return ApiResult
     */
    @GetMapping("getProcessInstInfo")
    @ApiOperation(value = "查询流程info")
    public ApiResult<String> getProcessInstInfo(BpsWorkFlowDto bps) {
        ApiResult<String> result = new ApiResult<>();
        String res = BpsUtil.queryProcessInfo(bps);
        result.setResults(res);
        result.success("查询成功");
        return result;
    }

    /**
     * 删除oa通知待办
     * @param bps 对象
     * @return ApiResult
     */
    @PostMapping("delOaNotice")
    @ApiOperation(value = "删除oa通知待办")
    public ApiResult delOaNotice(BpsWorkFlowDto bps) {
        ApiResult result = new ApiResult<>();
        commonFlowService.delOaNotice(bps);
        result.success("操作成功");
        return result;
    }
}
