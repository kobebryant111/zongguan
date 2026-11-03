package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmWorkKjlCghtDetailDto;
import com.work.ppm.application.services.integratedmanager.IPpmWorkKjlCghtDetailService;
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
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmWorkKjlCghtDetailApi"},description="合同相关表")
@RestController
@RequestMapping("ppmWorkKjlCghtDetail")
@Slf4j
public class PpmWorkKjlCghtDetailApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmWorkKjlCghtDetailService ppmWorkKjlCghtDetailService;

    /**
     * 分页
     * @param ppmWorkKjlCghtDetailDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "htbh", value = "合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htmc", value = "合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htje", value = "合同金额", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "wbq", value = "维保期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "wbfy", value = "维保费用", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wbksrq", value = "维保开始日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "wbjsrq", value = "维保结束日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "ksrq", value = "开始日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jsrq", value = "结束日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "cgnr", value = "采购内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gysmc", value = "供应商名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htwb", value = "合同文本附件", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzylxbh", value = "人力资源立项编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "dealstatus", value = "处理结果，0未处理，1已删除，2已处理", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "dealdept", value = "处理二级部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealdeptid", value = "处理二级部ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htlb", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isrlht", value = "是否为人力框架合同", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xmyjrs", value = "项目预计人数", dataType = "long", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmWorkKjlCghtDetailService.queryPager(ppmWorkKjlCghtDetailDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmWorkKjlCghtDetailDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "htbh", value = "合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htmc", value = "合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htje", value = "合同金额", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "wbq", value = "维保期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "wbfy", value = "维保费用", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wbksrq", value = "维保开始日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "wbjsrq", value = "维保结束日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "ksrq", value = "开始日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jsrq", value = "结束日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "cgnr", value = "采购内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gysmc", value = "供应商名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htwb", value = "合同文本附件", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzylxbh", value = "人力资源立项编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "dealstatus", value = "处理结果，0未处理，1已删除，2已处理", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "dealdept", value = "处理二级部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealdeptid", value = "处理二级部ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htlb", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isrlht", value = "是否为人力框架合同", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xmyjrs", value = "项目预计人数", dataType = "long", paramType = "query"),
			
    })
    public ApiResult<PpmWorkKjlCghtDetailDto> queryDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) {
        ApiResult<PpmWorkKjlCghtDetailDto> result =new ApiResult<>();
        PpmWorkKjlCghtDetailDto item = ppmWorkKjlCghtDetailService.queryDetail(ppmWorkKjlCghtDetailDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmWorkKjlCghtDetailDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "htbh", value = "合同编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htmc", value = "合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htje", value = "合同金额", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "wbq", value = "维保期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "wbfy", value = "维保费用", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wbksrq", value = "维保开始日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "wbjsrq", value = "维保结束日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "ksrq", value = "开始日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jsrq", value = "结束日期", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "cgnr", value = "采购内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gysmc", value = "供应商名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htwb", value = "合同文本附件", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rlzylxbh", value = "人力资源立项编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "dealstatus", value = "处理结果，0未处理，1已删除，2已处理", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "dealdept", value = "处理二级部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealdeptid", value = "处理二级部ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htlb", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isrlht", value = "是否为人力框架合同", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xmyjrs", value = "项目预计人数", dataType = "long", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmWorkKjlCghtDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmWorkKjlCghtDetailService.updatePpmWorkKjlCghtDetail(ppmWorkKjlCghtDetailDto);
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
     * @param ppmWorkKjlCghtDetailDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmWorkKjlCghtDetail (PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmWorkKjlCghtDetailService.deletePpmWorkKjlCghtDetail(ppmWorkKjlCghtDetailDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
