package com.work.ppm.application.api.onlinemanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.onlinemanager.OnlineInfoDto;
import com.work.ppm.application.dto.onlinemanager.PpmProjectNewonlineDto;
import com.work.ppm.application.dto.onlinemanager.PpmProjectOnlinefjhDto;
import com.work.ppm.application.dto.projectmanager.DemandBackDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftTreeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.onlinemanager.IPpmProjectNewonlineService;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmProjectNewonlineApi"},description="描述该类叫什么")
@RestController
@RequestMapping("ppmProjectNewonline")
@Slf4j
public class PpmProjectNewonlineApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmProjectNewonlineService ppmProjectNewonlineService;

    /**
     * 分页
     * @param ppmProjectNewonlineDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "onlineno", value = "上线编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "项目编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userid", value = "申请人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "apply", value = "申请时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "申请部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projecttype", value = "项目类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectscale", value = "项目规模", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysno", value = "系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "busideptno", value = "牵头业务部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "operatetype", value = "投产类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planonline", value = "申请上线日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "testconclusion", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgarea", value = "上线范围", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "删除标志1：删除 0：未删除", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "最后操作人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywuserid", value = "业务二级部经理（版本申请阶段）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ywfgzjluserid", value = "业务分管总经理（版本申请阶段）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ysuserid", value = "业务二级部经理(上线阶段)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ysfgzjluserid", value = "业务分管总经理(上线阶段)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "demandno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "reviewid", value = "评审ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "reviewstatus", value = "评审状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fjhreason", value = "非计划原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "上线简述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fgnxqpg", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isothersys", value = "是否特殊系统上线 01：是  00：否", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "teststatus", value = "测试结论状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "testresult", value = "测试结论", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wyType", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdfxfjh", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isproduct", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "bfsxreason", value = "部分上线原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "testcontent", value = "测试内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "noallreason", value = "功能需求非全部上线原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "iszjlsp", value = "是否总经理审批", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "notcz", value = "非投产周周四", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "notczreason", value = "未选择非投产周周四原因", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryOnlinePager(PpmProjectNewonlineDto ppmProjectNewonlineDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmProjectNewonlineService.queryOnlinePager(ppmProjectNewonlineDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }
    /**
     * 查询详情
     * @param ppmProjectNewonlineDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "onlineno", value = "上线编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectno", value = "项目编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "apply", value = "申请时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "申请部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projecttype", value = "项目类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectscale", value = "项目规模", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sysno", value = "系统编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "busideptno", value = "牵头业务部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "operatetype", value = "投产类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "planonline", value = "申请上线日期", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "testconclusion", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "orgarea", value = "上线范围", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "删除标志1：删除 0：未删除", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "creators", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduser", value = "最后操作人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ywuserid", value = "业务二级部经理（版本申请阶段）",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ywfgzjluserid", value = "业务分管总经理（版本申请阶段）",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ysuserid", value = "业务二级部经理(上线阶段)",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ysfgzjluserid", value = "业务分管总经理(上线阶段)",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandno", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "reviewid", value = "评审ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "reviewstatus", value = "评审状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fjhreason", value = "非计划原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "上线简述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fgnxqpg", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isothersys", value = "是否特殊系统上线 01：是  00：否",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "teststatus", value = "测试结论状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testresult", value = "测试结论", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wyType", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdfxfjh", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isproduct", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bfsxreason", value = "部分上线原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testcontent", value = "测试内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "noallreason", value = "功能需求非全部上线原因",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iszjlsp", value = "是否总经理审批", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "notcz", value = "非投产周周四", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "notczreason", value = "未选择非投产周周四原因",
                    dataType = "string", paramType = "query"),

    })
    public ApiResult<PpmProjectNewonlineDto> queryDetail(PpmProjectNewonlineDto ppmProjectNewonlineDto) {
        ApiResult<PpmProjectNewonlineDto> result =new ApiResult<>();
        PpmProjectNewonlineDto item = ppmProjectNewonlineService.queryDetail(ppmProjectNewonlineDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmProjectNewonlineDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "onlineno", value = "上线编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectno", value = "项目编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "apply", value = "申请时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "申请部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projecttype", value = "项目类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectscale", value = "项目规模", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sysno", value = "系统编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "busideptno", value = "牵头业务部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "operatetype", value = "投产类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "planonline", value = "申请上线日期", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "testconclusion", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "orgarea", value = "上线范围", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "删除标志1：删除 0：未删除", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "creators", value = "创建人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduser", value = "最后操作人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ywuserid", value = "业务二级部经理（版本申请阶段）",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ywfgzjluserid", value = "业务分管总经理（版本申请阶段）",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ysuserid", value = "业务二级部经理(上线阶段)",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ysfgzjluserid", value = "业务分管总经理(上线阶段)",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "demandno", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "reviewid", value = "评审ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "reviewstatus", value = "评审状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fjhreason", value = "非计划原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "上线简述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fgnxqpg", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isothersys", value = "是否特殊系统上线 01：是  00：否",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "teststatus", value = "测试结论状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testresult", value = "测试结论", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "wyType", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdfxfjh", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isproduct", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bfsxreason", value = "部分上线原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testcontent", value = "测试内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "noallreason", value = "功能需求非全部上线原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "iszjlsp", value = "是否总经理审批", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "notcz", value = "非投产周周四", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "notczreason", value = "未选择非投产周周四原因", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updatePpmProjectNewonline(PpmProjectNewonlineDto ppmProjectNewonlineDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmProjectNewonlineService.updatePpmProjectNewonline(ppmProjectNewonlineDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmProjectNewonlineDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "getPpmProjectBuildSoft")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "员工编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult<List<PpmProjectBuildSoftTreeDto>> getPpmProjectBuildSoft (
            PpmProjectNewonlineDto ppmProjectNewonlineDto) {
        //删除
        ApiResult result =new ApiResult<>();
        List<PpmProjectBuildSoftTreeDto> list = ppmProjectNewonlineService.
                getPpmProjectBuildSoft(ppmProjectNewonlineDto);
        result.success("查询成功");
        result.setResults(list);
        return result;
    }

    /**
     * 发起上线流程
     * @param onlineInfo 对象
     * @return ApiResult
     */
    @PostMapping(value = "addPpmProjectNewonlineFlow")
    @ApiOperation(value = "发起上线流程", notes = "发起上线流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "onlineSubProjectInfo", value = "上线子项目列表", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "demandFlowLogList", value = "审批意见列表", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "demandFileList", value = "附件信息", dataType = "list", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "apply", value = "申请日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectno", value = "相关项目", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "onlineType", value = "分行上线", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "teststatus", value = "测试结论", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "testcontent", value = "测试内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fjhreason", value = "非计划窗口上线原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "上线内容概述(两句话简述)", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "bfsxreason", value = "部分上线原因", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "operatetype", value = "上线类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "orgarea", value = "上线范围", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "planonline", value = "申请上线日期", dataType = "string", paramType = "query"),

    })
    public ApiResult<Map<String, Object>> addPpmProjectNewonlineFlow(@RequestBody OnlineInfoDto onlineInfo) {
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        Map<String, Object> flowReuslt = ppmProjectNewonlineService.addPpmProjectNewonlineFlow(onlineInfo);
        String status = StringUtils.isEmpty(flowReuslt.get("status"))
                ? "0000" : String.valueOf(flowReuslt.get("status"));
        if("1111".equals(status)) {
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("分行意向上线发起成功！");
            Map<String, Object> res = new HashMap<>();
            String onlinenum = StringUtils.isEmpty(flowReuslt.get("onlineNum")) ?
                    "0000" : String.valueOf(flowReuslt.get("onlineNum"));
            res.put("onlinenum", onlinenum);
            result.setResults(res);
        } else {
            result.setStatus(ApiCode.FAIL);
            String msg = StringUtils.isEmpty(flowReuslt.get("msg"))
                    ? "分行意向上线发起失败！" : String.valueOf(flowReuslt.get("msg"));
            result.setMessage(msg);
        }

        return result;
    }


    /**
     * 撤回上线流程
     * @param demandBackDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "recallPpmProjectNewonlineFlow")
    @ApiOperation(value = "撤回上线流程", notes = "撤回上线流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "上线项目编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "delType", value = "撤回类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "撤回操作人员", dataType = "String", paramType = "query"),

    })
    public ApiResult<String> recallPpmProjectNewonlineFlow(@RequestBody DemandBackDto demandBackDto) {
        ApiResult<String> result = new ApiResult<>();
        String msg = ppmProjectNewonlineService.recallPpmProjectNewonlineFlow(demandBackDto);
        if (msg != null) {
            if ("1111".equals(msg)) {
                result.setStatus(ApiCode.SUCCESS);
                result.setMessage("分行意向上线撤回成功！");
            } else if ("2222".equals(msg)) {
                result.setStatus(ApiCode.FAIL);
                result.setMessage("分行意向上线已审批无法撤回！");
            } else if ("3333".equals(msg)) {
                result.setStatus(ApiCode.FAIL);
                result.setMessage("未找到对应已发起的分行意向上线！");
            } else {
                result.setStatus(ApiCode.FAIL);
                result.setMessage("分行意向上线撤回失败！");
            }
        }

        return result;
    }

    /**
     * 分行科技二级部经理审批
     * @param onlinefjh 业务流程对象
     * @param request 请求
     * @return ApiResult
     */
    @PostMapping(value = "approveFlowByEJBManagers")
    @ApiOperation(value = "分行科技二级部经理审批", notes = "分行科技二级部经理审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "上线项目编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "delType", value = "撤回类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "撤回操作人员", dataType = "String", paramType = "query"),

    })
    public ApiResult<String> approveFlowByEJBManagers(@RequestBody PpmProjectOnlinefjhDto onlinefjh
            , HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = onlinefjh.getBps();
        if(null==bps){
            bps=new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmProjectNewonlineService.approveFlowByEJBManagers(onlinefjh, bps);
        result.success(msg);

        return result;
    }

    /**
     * 分行业务总经理审批
     * @param onlinefjh 业务流程对象
     * @param request 请求
     * @return ApiResult
     */
    @PostMapping(value = "approveFlowByBusZJL")
    @ApiOperation(value = "分行业务总经理审批", notes = "分行业务总经理审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "上线项目编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "delType", value = "撤回类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "撤回操作人员", dataType = "String", paramType = "query"),

    })
    public ApiResult<String> approveFlowByBusZJL(@RequestBody PpmProjectOnlinefjhDto onlinefjh,
                                                 HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = onlinefjh.getBps();
        if(null==bps){
            bps=new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmProjectNewonlineService.approveFlowByBusZJL(onlinefjh, bps);
        result.success(msg);

        return result;
    }

    /**
     * 分行业务科技总经理审批
     * @param onlinefjh 业务流程对象
     * @param request 请求
     * @return ApiResult
     */
    @PostMapping(value = "approveFlowByTechZJL")
    @ApiOperation(value = "分行业务科技总经理审批", notes = "分行业务科技总经理审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "上线项目编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "delType", value = "撤回类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "撤回操作人员", dataType = "String", paramType = "query"),

    })
    public ApiResult<String> approveFlowByTechZJL(@RequestBody PpmProjectOnlinefjhDto onlinefjh,
                                                  HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        BpsWorkFlowDto bps = onlinefjh.getBps();
        if(null==bps){
            bps=new BpsWorkFlowDto();
        }
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        bps.setUserId(user.getUserid());
        String msg = ppmProjectNewonlineService.approveFlowByTechZJL(onlinefjh, bps);
        result.success(msg);

        return result;
    }



}
