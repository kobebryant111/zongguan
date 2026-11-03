package com.work.ppm.application.api.othermanager;

import com.work.ppm.application.dto.othermanager.PpmWorkKjlCghtDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.othermanager.PpmWorkKjlCght;
import com.work.ppm.application.services.othermanager.IPpmWorkKjlCghtService;
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
 * 外包商合同
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"ppmWorkKjlCghtApi"},description = "外包商合同")
@RestController
@RequestMapping("ppmWorkKjlCght")
@Slf4j
public class PpmWorkKjlCghtApi {
	/**
	 * 包商合同接口
	 */
    @Autowired
    private IPpmWorkKjlCghtService ppmWorkKjlCghtService;

    /**
     * 分页
     * @param ppmWorkKjlCght 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "htbh", value = "合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htmc", value = "合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htje", value = "合同金额", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "wbq", value = "维保期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "wbfy", value = "维保费用", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wbksrq", value = "维保开始日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "wbjsrq", value = "维保结束日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "ksrq", value = "开始日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "jsrq", value = "结束日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "cgnr", value = "采购内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gysmc", value = "供应商名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htwb", value = "合同文本附件", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzylxbh", value = "人力资源立项编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "time", value = "同步日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "dealdept", value = "处理二级部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "creator", value = "创建者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "数据来源流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealdeptid", value = "处理二级部ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issendemail", value = "0为未发送，1为已发送", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "0为未删除，1为已删除", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "htlb", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isrlht", value = "是否为人力框架合同", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(PpmWorkKjlCght ppmWorkKjlCght,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmWorkKjlCghtService.queryPager(ppmWorkKjlCght,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmWorkKjlCght 对象
     * @return ApiResult<PpmWorkKjlCght>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "htbh", value = "合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htmc", value = "合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htje", value = "合同金额", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "wbq", value = "维保期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "wbfy", value = "维保费用", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wbksrq", value = "维保开始日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "wbjsrq", value = "维保结束日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "ksrq", value = "开始日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "jsrq", value = "结束日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "cgnr", value = "采购内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gysmc", value = "供应商名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htwb", value = "合同文本附件", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzylxbh", value = "人力资源立项编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "time", value = "同步日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "dealdept", value = "处理二级部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "creator", value = "创建者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "数据来源流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealdeptid", value = "处理二级部ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issendemail", value = "0为未发送，1为已发送", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "0为未删除，1为已删除", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "htlb", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isrlht", value = "是否为人力框架合同", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmWorkKjlCght> queryDetail(PpmWorkKjlCght ppmWorkKjlCght) {
        ApiResult<PpmWorkKjlCght> result =new ApiResult<>();
        PpmWorkKjlCght item = ppmWorkKjlCghtService.queryDetail(ppmWorkKjlCght);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmWorkKjlCght 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "updatePojo", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "htbh", value = "合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htmc", value = "合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htje", value = "合同金额", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "wbq", value = "维保期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "wbfy", value = "维保费用", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wbksrq", value = "维保开始日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "wbjsrq", value = "维保结束日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "ksrq", value = "开始日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "jsrq", value = "结束日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "cgnr", value = "采购内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gysmc", value = "供应商名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htwb", value = "合同文本附件", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzylxbh", value = "人力资源立项编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "time", value = "同步日期", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "dealdept", value = "处理二级部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "creator", value = "创建者", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "数据来源流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealdeptid", value = "处理二级部ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "issendemail", value = "0为未发送，1为已发送", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "0为未删除，1为已删除", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "htlb", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isrlht", value = "是否为人力框架合同", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmWorkKjlCght(PpmWorkKjlCght ppmWorkKjlCght) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmWorkKjlCghtService.updatePpmWorkKjlCght(ppmWorkKjlCght);
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
     * @param ppmWorkKjlCght 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "deletePojo", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmWorkKjlCght (PpmWorkKjlCght ppmWorkKjlCght) {
        ApiResult result =new ApiResult<>();
        int id = ppmWorkKjlCghtService.deletePpmWorkKjlCght(ppmWorkKjlCght);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


	/**
	 * 查询合同下拉框
	 * @param info 对象
	 * @return ApiResult<List<PpmWorkKjlCghtDto>>
	 */
	@GetMapping("queryContracts")
	@ApiOperation("查询合同下拉框")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dealdeptid", value = "部门ID", dataType = "string", paramType = "query"),
	})
	public ApiResult<List<PpmWorkKjlCghtDto>> queryContracts(PpmWorkKjlCghtDto info) {
		ApiResult<List<PpmWorkKjlCghtDto>> result =new ApiResult<>();
		List<PpmWorkKjlCghtDto> dtoList = ppmWorkKjlCghtService.queryContracts(info);
		PpmWorkKjlCghtDto dto=new PpmWorkKjlCghtDto();
		result.setResults(dtoList);
		result.success("查询成功");
		return result;
	}
}
