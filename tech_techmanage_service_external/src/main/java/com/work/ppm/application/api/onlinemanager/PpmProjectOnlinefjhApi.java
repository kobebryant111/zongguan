package com.work.ppm.application.api.onlinemanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmProjectOnlinefjhDto;
import com.work.ppm.application.services.onlinemanager.IPpmProjectOnlinefjhService;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmProjectOnlinefjhApi"},description="描述该类叫什么")
@RestController
@RequestMapping("ppmProjectOnlinefjh")
@Slf4j
public class PpmProjectOnlinefjhApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmProjectOnlinefjhService ppmProjectOnlinefjhService;

    /**
     * 分页
     * @param ppmProjectOnlinefjhDto 对象
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
			@ApiImplicitParam(name = "issueid", value = "奋进号ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "iszjlsp", value = "是否总经理审批", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmProjectOnlinefjhService.queryPager(ppmProjectOnlinefjhDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmProjectOnlinefjhDto 对象
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
			@ApiImplicitParam(name = "issueid", value = "奋进号ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "iszjlsp", value = "是否总经理审批", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmProjectOnlinefjhDto> queryDetail(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        ApiResult<PpmProjectOnlinefjhDto> result =new ApiResult<>();
        PpmProjectOnlinefjhDto item = ppmProjectOnlinefjhService.queryDetail(ppmProjectOnlinefjhDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmProjectOnlinefjhDto 对象
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
			@ApiImplicitParam(name = "issueid", value = "奋进号ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "iszjlsp", value = "是否总经理审批", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmProjectOnlinefjh(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmProjectOnlinefjhService.updatePpmProjectOnlinefjh(ppmProjectOnlinefjhDto);
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
     * @param ppmProjectOnlinefjhDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmProjectOnlinefjh (PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmProjectOnlinefjhService.deletePpmProjectOnlinefjh(ppmProjectOnlinefjhDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

	/**
	 * 查询上线信息
	 * @param ppmProjectOnlinefjhDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "getOnlineInfo")
	@ApiOperation(value = "查询上线信息", notes = "查询上线信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "onlineno", value = "上线编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),

	})
	public ApiResult<PpmProjectOnlinefjhDto> getOnlineInfo(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
		ApiResult<PpmProjectOnlinefjhDto> result =new ApiResult<>();
		PpmProjectOnlinefjhDto item = ppmProjectOnlinefjhService.getOnlineInfo(ppmProjectOnlinefjhDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

	/**
	 * 根据上线编号查询流程ID
	 * @param onlinefjh 业务流程对象
	 * @return ApiResult
	 */
	@GetMapping(value = "queryInstanceidByOnlinenum")
	@ApiOperation(value = "根据上线编号查询流程ID", notes = "根据上线编号查询流程ID")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "onlinenum", value = "上线项目编号", dataType = "String", paramType = "query"),
	})
	public ApiResult<PpmProjectOnlinefjhDto> queryInstanceidByOnlinenum(PpmProjectOnlinefjhDto onlinefjh) {
		ApiResult<PpmProjectOnlinefjhDto> result = new ApiResult<>();
		String instanceid = ppmProjectOnlinefjhService.queryInstanceidByOnlinenum(onlinefjh);
		if (StringUtil.isNotEmpty(instanceid)) {
			result.setStatus(ApiCode.SUCCESS);
			PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto = new PpmProjectOnlinefjhDto();
			ppmProjectOnlinefjhDto.setInstanceid(instanceid);
			result.setResults(ppmProjectOnlinefjhDto);
		} else {
			result.setStatus(ApiCode.FAIL);
			result.setMessage("根据流程ID无流程！");
		}

		return result;
	}

	/**
	 * 查询上线信息(手机端)
	 * @param ppmProjectOnlinefjhDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "getMobileOnlineInfo")
	@ApiOperation(value = "查询上线信息(手机端)", notes = "查询上线信息(手机端)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "onlineno", value = "上线编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),

	})
	public ApiResult<PpmProjectOnlinefjhDto> getMobileOnlineInfo(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto) {
		ApiResult<PpmProjectOnlinefjhDto> result =new ApiResult<>();
		PpmProjectOnlinefjhDto item = ppmProjectOnlinefjhService.getMobileOnlineInfo(ppmProjectOnlinefjhDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}
}
