package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmDeptdistributionReportDto;
import com.work.ppm.application.services.integratedmanager.IPpmDeptdistributionReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 入职部门分配报表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDeptdistributionReportApi"},description="入职部门分配报表")
@RestController
@RequestMapping("ppmDeptdistributionReport")
@Slf4j
public class PpmDeptdistributionReportApi {
    /**
     * 入职部门分配报表
     */
    @Autowired
    private IPpmDeptdistributionReportService ppmDeptdistributionReportService;

    /**
     * 分页
     * @param ppmDeptdistributionReportDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "depId", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depNum", value = "各部门总编制数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "researchNum", value = "研发", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "productNum", value = "产品经理", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "appSuportNum", value = "应用支持", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "systemNum", value = "系统运维", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "testNum", value = "测试", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "functionsifNum", value = "职能管理", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "batch", value = "批次", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmDeptdistributionReportDto ppmDeptdistributionReportDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmDeptdistributionReportService.queryPager(ppmDeptdistributionReportDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmDeptdistributionReportDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "depId", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depNum", value = "各部门总编制数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "researchNum", value = "研发", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "productNum", value = "产品经理", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "appSuportNum", value = "应用支持", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "systemNum", value = "系统运维", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "testNum", value = "测试", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "functionsifNum", value = "职能管理", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "batch", value = "批次", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmDeptdistributionReportDto> queryDetail(PpmDeptdistributionReportDto ppmDeptdistributionReportDto) {
        ApiResult<PpmDeptdistributionReportDto> result =new ApiResult<>();
        PpmDeptdistributionReportDto item = ppmDeptdistributionReportService.queryDetail(ppmDeptdistributionReportDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 导入
     * @param batchNo 批次号
     * @param file 文件
     * @param request   request
     * @return   ApiResult
     */
    @PostMapping("importExcel")
    @ApiOperation(value = "导入", notes = "根据批次号导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "batchNo", value = "批次号", paramType = "query"),
    })
    public ApiResult importExcel(String batchNo, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();

        boolean falg = ppmDeptdistributionReportService.importExcel(batchNo, file);
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }

}
