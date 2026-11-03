package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.ManpowerdemandsInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyInfoInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyOrgDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApply;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfoExport;
import com.work.ppm.application.services.integratedmanager.IPpmManpowerdemandApplyInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmManpowerdemandApplyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 人力需求流程
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmManpowerdemandApplyApi"}, description = "人力需求流程")
@RestController
@RequestMapping("ppmManpowerdemandApply")
@Slf4j
public class PpmManpowerdemandApplyApi extends BaseApi {
    /**
     * 人力需求流程
     */
    @Autowired
    private IPpmManpowerdemandApplyService ppmManpowerdemandApplyService;
    /**
     * 人力需求详情
     */
    @Autowired
    private IPpmManpowerdemandApplyInfoService ppmManpowerdemandApplyInfoService;

    /**
     * 分页
     *
     * @param apply 对象
     * @param pager 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dept", value = "部门名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "部门ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplytime", value = "提至供应商时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "manageruserid", value = "审批的主持工作经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "confirmuserid", value = "综合部人员确认", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmManpowerdemandApply apply, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmManpowerdemandApplyService.queryPager(apply, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     *
     * @param apply 对象
     * @return ApiResult<PpmManpowerdemandApply>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dept", value = "部门名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "部门ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplytime", value = "提至供应商时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "manageruserid", value = "审批的主持工作经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "confirmuserid", value = "综合部人员确认", dataType = "string", paramType = "query"),

    })
    public ApiResult<PpmManpowerdemandApply> queryDetail(PpmManpowerdemandApply apply) {
        ApiResult<PpmManpowerdemandApply> result = new ApiResult<>();
        PpmManpowerdemandApply item = ppmManpowerdemandApplyService.queryDetail(apply);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     *
     * @param apply 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dept", value = "部门名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "部门ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplytime", value = "提至供应商时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "manageruserid", value = "审批的主持工作经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "confirmuserid", value = "综合部人员确认", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updatePpmManpowerdemandApply(PpmManpowerdemandApply apply) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmManpowerdemandApplyService.updatePpmManpowerdemandApply(apply);
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
     * @param apply 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程编号", dataType = "String", paramType = "query")
    })
    public ApiResult deletePpmManpowerdemandApply(PpmManpowerdemandApply apply) {
        ApiResult result = new ApiResult<>();
        int id = ppmManpowerdemandApplyService.deletePpmManpowerdemandApply(apply);
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
     * @param applyDto 对象
     * @return ApiResult<PpmManpowerdemandApplyDto>
     */
    @PostMapping("save")
    @ApiOperation("保存流程数据")
    public ApiResult<PpmManpowerdemandApplyDto> save(HttpServletRequest request,
                                                     @RequestBody PpmManpowerdemandApplyDto applyDto) {
        ApiResult<PpmManpowerdemandApplyDto> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        PpmManpowerdemandApplyDto dto = ppmManpowerdemandApplyService.save(applyDto, applyDto.getInfos(), user);
        if (null == dto) {
            result.failure("保存失败");
        } else {
            result.success("保存成功");
            result.setResults(dto);
        }
        return result;
    }

    /**
     * 发起申请
     *
     * @param applyDto 对象
     * @return ApiResult<String>
     */
    @PostMapping("liuzhuan")
    @ApiOperation("发起申请")
    @ApiImplicitParam(value = "依照页面元素进行填充")
    public ApiResult<String> liuzhuan(HttpServletRequest request,
                                      @RequestBody PpmManpowerdemandApplyDto applyDto) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        BpsWorkFlowDto bps = applyDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        String msg = ppmManpowerdemandApplyService.liuzhuan(applyDto, bps);
        result.success(msg);
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
    @ApiOperation("二级部经理审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ppmOutsourcingApply", value = "", paramType = "body", dataType = "String")
    })
    public ApiResult<String> twoManagerAudit(HttpServletRequest request,
                                             @RequestBody PpmManpowerdemandApplyDto applyDto) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = applyDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmManpowerdemandApplyService.twoManagerAudit(applyDto, bps);
        result.success(msg);
        return result;
    }


    /**
     * 一级部总经理室审批
     *
     * @param applyDto 对象
     * @param request  request
     * @return ApiResult<String>
     */
    @PostMapping("confirm")
    @ApiOperation("一级部总经理室审批")
    public ApiResult<String> confirm(@RequestBody PpmManpowerdemandApplyDto applyDto, HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = applyDto.getBps();
        if (null == bps) {
            bps = new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmManpowerdemandApplyService.confirm(applyDto, bps);
        result.success(msg);
        return result;
    }


    /**
     * 在职分页列表
     *
     * @param infoDto 对象
     * @param pager   分页对象
     * @return ApiResult<List < PpmManpowerdemandApplyInfoInfoDto>>
     */
    @GetMapping("queryPagerList")
    @ApiOperation("根据当前用户权限查询人力需求流程")
    public ApiResult<List<PpmManpowerdemandApplyInfoInfoDto>> queryPagerList(ManpowerdemandsInfoDto infoDto,
                                                                             Pager pager, HttpServletRequest request) {
        ApiResult<List<PpmManpowerdemandApplyInfoInfoDto>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<PpmManpowerdemandApplyInfoInfoDto> dtoList = ppmManpowerdemandApplyService.queryPagerList(infoDto, pager, user);
        result.setTotal(pager.getTotal());
        result.setResults(dtoList);
        result.success("查询成功");
        return result;
    }


    /**
     * 根据流程instanceid查询数据
     *
     * @param ppmManpowerdemandApply 对象
     * @return ApiResult<PpmManpowerdemandApplyOrgDto>
     */
    @GetMapping("queryDetails")
    @ApiOperation("根据流程instanceid查询数据")
    @ApiImplicitParam(name = "instanceid", value = "流程编号", required = true, dataType = "string", paramType = "query")
    public ApiResult<PpmManpowerdemandApplyOrgDto> queryDetails(PpmManpowerdemandApply ppmManpowerdemandApply) {
        ApiResult<PpmManpowerdemandApplyOrgDto> result = new ApiResult<>();
        PpmManpowerdemandApplyOrgDto dto = ppmManpowerdemandApplyService.queryDetails(ppmManpowerdemandApply);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 面试查询人力需求
     *
     * @param infoDto 对象
     * @return ApiResult<List < ManpowerdemandsInfoDto>>
     */
    @GetMapping("queryManpowerdemands")
    @ApiOperation("面试查询人力需求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "usertype", value = "人员类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "usernum", value = "人员数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplytime", value = "提至供应商时间", dataType = "date", paramType = "query"),
    })
    public ApiResult<List<ManpowerdemandsInfoDto>> queryManpowerdemands(ManpowerdemandsInfoDto infoDto) {
        ApiResult<List<ManpowerdemandsInfoDto>> result = new ApiResult<>();
        List<ManpowerdemandsInfoDto> dtoList = ppmManpowerdemandApplyInfoService.queryManpowerdemands(infoDto);
        result.setResults(dtoList);
        result.success("查询成功");
        return result;
    }

    /**
     * 更新人力需求主表和详细表的状态
     *
     * @param applyInfo 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updateStatus")
    @ApiOperation(value = "更新人力需求主表和详细表的状态", notes = "更新人力需求主表和详细表的状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "instanceid", value = "流程号", dataType = "string", paramType = "query", required = true),
    })
    public ApiResult<Object> updateStatus(PpmManpowerdemandApplyInfo applyInfo) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmManpowerdemandApplyService.updateStatus(applyInfo);
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
     * @param applyInfo 对象
     * @return ApiResult deletePpmManpowerdemandApply
     */
    @DeleteMapping(value = "deletePpmManpowerdemandApply")
    @ApiOperation(value = "根据主键删除列表数据", notes = "根据主键删除列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "integer", paramType = "query", required = true),
    })
    public ApiResult deletePpmManpowerdemandApply(PpmManpowerdemandApplyInfo applyInfo) {
        ApiResult result = new ApiResult<>();
        int id = ppmManpowerdemandApplyInfoService.deletePpmManpowerdemandApplyInfo(applyInfo);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 导出
     *
     * @param response response
     * @param request  request
     * @param infoDto  对象
     */
    @PostMapping(value = "export")
    public void doExport(HttpServletResponse response, HttpServletRequest request, ManpowerdemandsInfoDto infoDto) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<PpmManpowerdemandApplyInfoExport> list = ppmManpowerdemandApplyInfoService.queryListExport(infoDto, user);
        ExcelUtil.exportExcel(list, PpmManpowerdemandApplyInfoExport.class, "人力需求.xls", response);
        result.success("数据导出成功");
//        return result;
    }
}
