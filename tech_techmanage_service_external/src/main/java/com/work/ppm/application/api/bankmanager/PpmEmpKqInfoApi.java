package com.work.ppm.application.api.bankmanager;

import com.work.ppm.application.dto.bankmamager.PpmEmpKqInfoDto;
import com.work.ppm.application.dto.bankmamager.PpmEmpKqInfoExportDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.quartz.bankmanager.IPpmEmpKqInfoCalculateJobService;
import com.work.ppm.application.quartz.bankmanager.IPpmEmpKqRecordCalculateJobService;
import com.work.ppm.application.services.bankmanager.IPpmEmpKqInfoService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * 行员考勤信息API接口
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmEmpKqInfoApi"})
@RestController
@RequestMapping("/ppmEmpKqInfo")
@Slf4j
public class PpmEmpKqInfoApi extends BaseApi {

    /**
     * 考勤信息服务接口
     */
    @Autowired
    private IPpmEmpKqInfoService ppmEmpKqInfoService;

    /**
     * 定时考勤计算次数服务接口
     */
    @Autowired
    private IPpmEmpKqInfoCalculateJobService ppmEmpKqInfoCalculateJobService;

    /**
     * 定时考勤计算异常服务接口
     */
    @Autowired
    private IPpmEmpKqRecordCalculateJobService ppmEmpKqRecordCalculateJobService;

    /**
     * 分页
     *
     * @param ppmEmpKqInfoDto 对象
     * @param pager           分页对象
     * @param request         request
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "attendanceMonth", value = "考勤年月", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "所属部门", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<Object> queryPager(PpmEmpKqInfoDto ppmEmpKqInfoDto, Pager pager, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmEmpKqInfoService.queryPager(ppmEmpKqInfoDto, pager, user);

        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 按考勤年月区间下载。列表仍按单月查询。
     *
     * @param ppmEmpKqInfoDto 姓名、部门以及开始、结束年月
     * @param request         request
     * @param response        response
     */
    @PostMapping("exportInfo")
    @ApiOperation(value = "员工考勤按时间区间下载")
    public void exportInfo(PpmEmpKqInfoDto ppmEmpKqInfoDto, HttpServletRequest request, HttpServletResponse response) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        List<PpmEmpKqInfoExportDto> list = ppmEmpKqInfoService.exportExcel(ppmEmpKqInfoDto, user);
        ExcelUtil.exportExcel(list, PpmEmpKqInfoExportDto.class, "员工考勤.xls", response);
    }

    /**
     * 详情分页
     *
     * @param id 对象
     * @return ApiResult
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "详情查询", notes = "详情查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "姓名", dataType = "long", paramType = "query"),
    })
    public ApiResult<Object> queryDetail(@NotNull String id) {
        PpmEmpKqInfoDto ppmEmpKqInfoDto = new PpmEmpKqInfoDto();
        ppmEmpKqInfoDto.setId(Long.valueOf(id));
        ApiResult<Object> result = new ApiResult<>();
        result.setResults(ppmEmpKqInfoService.queryDetail(ppmEmpKqInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 数据跑批接口
     */
    @PostMapping("infoDataCalculateRunBatch")
    @ApiOperation(value = "infoDataCalculateRunBatch")
    public void infoDataCalculateRunBatch() {
        log.info("------------------------手动跑批计算加班时长 开始------------------------");
        ppmEmpKqRecordCalculateJobService.overtimeCalaulate();
        log.info("------------------------手动跑批计算加班时长 完成------------------------");

        log.info("------------------------手动跑批计算异常考勤 开始------------------------");
        ppmEmpKqRecordCalculateJobService.recordCalaulate();
        log.info("------------------------手动跑批计算异常考勤 完成------------------------");

        log.info("------------------------手动跑批计算考勤次数 完成------------------------");
        ppmEmpKqInfoCalculateJobService.infoCalaulate();
        log.info("------------------------手动跑批计算考勤次数 完成------------------------");

    }
}
