package com.work.ppm.application.api.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmPwdApplyDto;
import com.work.ppm.application.services.projectmanager.IPpmPwdApplyService;
import com.work.ppm.commons.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 密管密码
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmPwdApplyApi"},description="密管密码")
@RestController
@RequestMapping("ppmPwdApply")
@Slf4j
public class PpmPwdApplyApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmPwdApplyService ppmPwdApplyService;


    /**
     * 是否隐藏按钮 - 废弃
     * @param ppmPwdApplyDto 对象
     * @return ApiResult 对象
     */
    @Deprecated
    @GetMapping(value = "ifHiddenButton")
    @ApiOperation(value = "是否隐藏按钮", notes = "是否隐藏按钮")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "isgetbydep", value = "是否需获取本部门其他系统用户(01：是，02：否)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "getbydepreason", value = "原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isrelate", value = "是否有相关流程", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isnew", value = "1", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "bfreason", value = "详细原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relateactivity", value = "关联流程节点", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealcode", value = "关联流程编号（主要用于关联流程是多人同时处理时，区分发起通用密码获取时哪个处理的）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "affectqr", value = "本次更新影响情况确认", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "adminreason", value = "临时获取管理员用户原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ishavestep", value = "是否已在关联流程中写明要获取的管理员用户及步骤", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isinerimadmin", value = "是否临时获取管理员用户", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applycode", value = "密码获取编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applyer", value = "申请人所在部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applyerdeptid", value = "申请人所在部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applyertel", value = "联系电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applytime", value = "申请时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isurgent", value = "是否紧急流程", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usehour", value = "使用时长（小时）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usestarttime", value = "开始使用时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "reason", value = "申请原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relateflow", value = "关联流程编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relateflowstatus", value = "关联流程状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "reasonnote", value = "详细原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "spuserid", value = "审批人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "密码到期时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdelay", value = "是否延期", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "delayreason", value = "延期理由", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "res", value = "处理结果意见", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isenable", value = "0:执行，-1：删除", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "creator", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "creattime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "updater", value = "修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "updatetime", value = "修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "realstarttime", value = "实际开始使用时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "useendtime", value = "使用结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "spuserbid", value = "审批人B", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relateflowname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemid", value = "相关业务系统", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planstarttime", value = "计划开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "planusehour", value = "预计时长", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "planendtime", value = "计划结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "relateinstanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "note", value = "备注", dataType = "string", paramType = "query"),
    })
    public ApiResult<PpmPwdApplyDto> ifHiddenButton(PpmPwdApplyDto ppmPwdApplyDto) {
		ApiResult result = ppmPwdApplyService.ifHiddenButton(ppmPwdApplyDto);
        return result;
    }
}
