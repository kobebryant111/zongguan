package com.work.ppm.application.api.projectmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.projectmanager.AapSystemDto;
import com.work.ppm.application.model.projectmanager.AapSystem;
import com.work.ppm.application.services.projectmanager.IAapSystemService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统管理
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"AapSystemApi"},description="系统管理")
@RestController
@RequestMapping("aapSystem")
@Slf4j
public class AapSystemApi {
	/**
	 * 系统管理 接口
	 */
    @Autowired
    private IAapSystemService aapSystemService;

	/**
	 * 部门系统关联树
	 * @param code  编号
	 * @return ApiResult<List<CommTreeDto>>
	 */
	@GetMapping(value = "/querySystemTree/{code}")
	@ApiOperation(value = "部门系统树", notes = "部门系统关联树")
	@ApiImplicitParam(name = "code", value = "编号", dataType = "string", paramType = "path", required = true)
    public ApiResult<List<CommTreeDto>> querySystemTree(@PathVariable String code) {
    	ApiResult<List<CommTreeDto>> result = new ApiResult();
		List<CommTreeDto> treeList = aapSystemService.querySystemTree(code);
		result.success("查询成功");
		result.setResults(treeList);
    	return result;
	}
    /**
     * 分页
     * @param aapSystem 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysNameShort", value = "系统简称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "intelnet", value = "互联网连接情况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "resId", value = "系统关联ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemId", value = "系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysName", value = "系统名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysBusName", value = "系统业务名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "alias", value = "别名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "devStrategy", value = "系统开发策略", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "procStatus", value = "处理状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysStatus", value = "系统状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysFun", value = "系统功能", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "arcRegion", value = "系统架构域别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "arcLayer", value = "系统架构层别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "funSort", value = "系统功能类别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "omLevel", value = "系统运维等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "serviceTime", value = "系统服务时间补充说名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rto", value = "RTO", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rpo", value = "RPO", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dataIntegrity", value = "数据完整性", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "modifyTime", value = "修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createPerson", value = "创建人员", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "modifyPerson", value = "修改人员", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysBackground", value = "背景", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysMaintain", value = "运维情况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysSuggest", value = "建议优化", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "transport", value = "迁移字段", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "f5", value = "F5", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "soundcode", value = "源码是否开放", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "backupDisaster", value = "灾备是否建设", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sensitive", value = "是否含敏感信息", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changePlan", value = "每月计划变更频率", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xuqiuquanxian", value = "是否开发需求权限", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pResId", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "yewushengpi", value = "子项目立项是否需要业务审批", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "whitetype", value = "是否白名单", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secCondept", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ischild", value = "是否子系统", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systype", value = "系统类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysgroup", value = "系统群", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "refmoney", value = "是否涉及资金", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "serviceTimeType", value = "系统服务时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usedMonitorBuild", value = "应用监控建设", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "importantInfoSystem", value = "重要信息系统", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(AapSystem aapSystem,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        aapSystemService.queryPager(aapSystem,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param aapSystem 对象
     * @return ApiResult<AapSystem>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "sysNameShort", value = "系统简称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "intelnet", value = "互联网连接情况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "resId", value = "系统关联ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemId", value = "系统编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysName", value = "系统名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysBusName", value = "系统业务名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "alias", value = "别名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "devStrategy", value = "系统开发策略", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "procStatus", value = "处理状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysStatus", value = "系统状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysFun", value = "系统功能", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "arcRegion", value = "系统架构域别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "arcLayer", value = "系统架构层别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "funSort", value = "系统功能类别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "omLevel", value = "系统运维等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "serviceTime", value = "系统服务时间补充说名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rto", value = "RTO", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rpo", value = "RPO", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dataIntegrity", value = "数据完整性", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "modifyTime", value = "修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createPerson", value = "创建人员", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "modifyPerson", value = "修改人员", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysBackground", value = "背景", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysMaintain", value = "运维情况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysSuggest", value = "建议优化", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "transport", value = "迁移字段", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "f5", value = "F5", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "soundcode", value = "源码是否开放", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "backupDisaster", value = "灾备是否建设", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sensitive", value = "是否含敏感信息", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changePlan", value = "每月计划变更频率", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xuqiuquanxian", value = "是否开发需求权限", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pResId", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "yewushengpi", value = "子项目立项是否需要业务审批", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "whitetype", value = "是否白名单", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secCondept", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ischild", value = "是否子系统", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systype", value = "系统类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sysgroup", value = "系统群", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "refmoney", value = "是否涉及资金", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "serviceTimeType", value = "系统服务时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usedMonitorBuild", value = "应用监控建设", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "importantInfoSystem", value = "重要信息系统", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<AapSystemDto> queryDetail(AapSystemDto aapSystem) {
        ApiResult<AapSystemDto> result =new ApiResult<>();
        AapSystemDto item = aapSystemService.queryDetail(aapSystem);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
