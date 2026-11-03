package com.work.ppm.application.api.otherdeptmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.otherdeptmanager.PpmKqRecordOtherDto;
import com.work.ppm.application.services.otherdeptmanager.IPpmKqRecordOtherService;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  考勤信息表-其他部门 API接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKqRecordOtherApi"})
@RestController
@RequestMapping("kqRecordOther")
@Slf4j
public class PpmKqRecordOtherApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKqRecordOtherService ppmKqRecordOtherService;

    /**
     * 分页
     * @param ppmKqRecordOtherDto 对象
     * @param pager 分页对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKqRecordOtherDto ppmKqRecordOtherDto,Pager pager,HttpServletRequest request) {
        ApiResult<List> result =new ApiResult<>();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        ppmKqRecordOtherService.queryPager(ppmKqRecordOtherDto,pager,user);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 导出信息接口
     * @param otherDto 对象
     * @param request request
     * @param response response
     */
    @PostMapping("export")
    @ApiOperation(value = "考勤信息导出")
    public void exportExcel(PpmKqRecordOtherDto otherDto, HttpServletRequest request, HttpServletResponse response) {
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        List<PpmKqRecordOtherDto> list = ppmKqRecordOtherService.exportExcel(otherDto,user);
        if(!list.isEmpty()){
            ExcelUtil.exportExcel(list, PpmKqRecordOtherDto.class,"考勤信息导出数据.xls",response);
        }
        log.info("导出成功。");
    }
}
