package com.work.ppm.application.api.integratedmanager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.work.ppm.application.dto.integratedmanager.PpmKqAnnualVacationDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqOvertimeConfigDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportAllDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportNoVerifyDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordImportDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.ext.PpmWorkloadKqInfoView;
import com.work.ppm.application.quartz.interfaces.IPpmKqInfoCalculateJobService;
import com.work.ppm.application.quartz.interfaces.IPpmKqRecordCalculateJobService;
import com.work.ppm.application.services.integratedmanager.IPpmKqAnnualVacationService;
import com.work.ppm.application.services.integratedmanager.IPpmKqInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqOvertimeConfigService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordImportService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.integratedmanager.impl.PpmSupplierContractService;
import com.work.ppm.application.services.otherdeptmanager.IPpmKqRecordOtherService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 考勤信息API接口
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmKqInfoApi"})
@RestController
@RequestMapping("ppmKqInfo")
@Slf4j
public class PpmKqInfoApi extends BaseApi {


    /**
     * 考勤信息服务接口
     */
    @Autowired
    IPpmKqInfoService ppmKqInfoService;

    /**
     * 考勤记录服务接口
     */
    @Autowired
    IPpmKqRecordService ppmKqRecordService;

    /**
     * 考勤记录服务接口
     */
    @Autowired
    IPpmKqRecordOtherService ppmKqRecordOtherService;

    /**
     * 考勤记录导入服务接口
     */
    @Autowired
    IPpmKqRecordImportService ppmKqRecordImportService;

    /**
     * 定时考勤计算次数服务接口
     */
    @Autowired
    IPpmKqInfoCalculateJobService ppmKqInfoCalculateJobService;

    /**
     * 定时考勤计算异常服务接口
     */
    @Autowired
    IPpmKqRecordCalculateJobService ppmKqRecordCalculateJobService;

    /**
     * 加班开始时间配置服务接口
     */
    @Autowired
    IPpmKqOvertimeConfigService ppmKqOvertimeConfigService;

    @Autowired
    IPpmKqAnnualVacationService ppmKqAnnualVacationService;

    /**
     * 供应商合同管理服务
     */
    @Autowired
    PpmSupplierContractService supplierContractService;


    /**
     * 分页
     *
     * @param ppmKqInfoDto 对象
     * @param pager            分页对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "attendanceMonth", value = "考勤年月", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "所属部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplierId", value = "供应商名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pmValue", value = "项目经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "outsourceType", value = "外包方式", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<Object> queryPager(PpmKqInfoDto ppmKqInfoDto, Pager pager, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmKqInfoService.queryPager(ppmKqInfoDto, pager,user);

        result.setResults(pager.getList() );
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 分页
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
        PpmKqInfoDto ppmKqInfoDto = new PpmKqInfoDto();
        ppmKqInfoDto.setId(Long.valueOf(id));
        ApiResult<Object> result = new ApiResult<>();
        result.setResults(ppmKqInfoService.queryDetail(ppmKqInfoDto));
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

//    /**
//     * 更新
//     *
//     * @param ppmKqRecordDto 对象
//     * @param request          对象
//     * @return ApiResult
//     */
//    @PostMapping(value = "update")
//    @ApiOperation(value = "更新", notes = "更新")
//    public ApiResult<Object> updatePpmKqRecord(PpmKqRecordDto ppmKqRecordDto, HttpServletRequest request) {
//        ApiResult<Object> result = new ApiResult<>();
//        CommOperatorDoLoginDto user = super.getCurrentUser(request);
//        if(user != null){
//            ppmKqRecordDto.setModifiedBy(user.getUserid());
//        }
//
//        ppmKqRecordDto.setModifiedTime(new Date());
////        String[] amAbnormalReasonList = ppmKqRecordDto.getAmAbnormalReasonList();
////        if(amAbnormalReasonList != null){
////            StringBuilder stringBuilder = new StringBuilder();
////            for(String reason : amAbnormalReasonList){
////                stringBuilder.append(reason).append("/");
////            }
////            ppmKqRecordDto.setAmAbnormalReason(stringBuilder.toString());
////        }
////
////        String[] pmAbnormalReasonList = ppmKqRecordDto.getPmAbnormalReasonList();
////        if(pmAbnormalReasonList != null){
////            StringBuilder stringBuilder = new StringBuilder();
////            for(String reason : pmAbnormalReasonList){
////                stringBuilder.append(reason).append("/");
////            }
////            ppmKqRecordDto.setPmAbnormalReason(stringBuilder.toString());
////        }
//        ppmKqRecordService.updatePpmKqRecordAndInfo(ppmKqRecordDto);
//
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

    /**
     * 导入考勤信息
     *
     * @param file    对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("importAttendanceInfo")
    @ApiOperation(value = "导入考勤记录")
    public ApiResult<Object> importAttendanceInfo(
            @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        try {
            log.info("{}开始上传考勤信息！", "测试开始");
            List<PpmKqRecordImportDto> list = ExcelUtil.importExcel(file,1,1,PpmKqRecordImportDto.class);

            if(list == null){
                result.setStatus(ApiCode.FAIL);
                result.setMessage("导入无数据或模板有误");
                return result;
            }
            long listSize = list.stream().filter(x -> x.getAttendanceDate() == null && x.getJobId() == null &&
                    x.getUserName() == null && x.getOutsourceType() == null).count();
            if(listSize == list.size()){
                result.setStatus(ApiCode.FAIL);
                result.setMessage("导入无数据或模板有误");
                return result;
            }

            List<PpmKqRecordImportDto> exList = ppmKqRecordImportService.importExcel(list,user);
            if(!exList.isEmpty()){
                result.success("部分导入成功");
                result.setResults(exList);
                result.setMessage("false");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(ApiCode.FAIL);
            result.setMessage("导入失败");
            return result;
        }
        result.setMessage("true");
        result.setStatus(ApiCode.SUCCESS);
        log.info("{}上传考勤信息结束！", "测试结束");
        return result;
    }

    /**
     * 导入成绩失败导出接口
     * @param ppmKqRecordImportDtos ppmKqRecordImportDtos
     * @param response response
     */
    @PostMapping("exportErrorRecordExport")
    @ApiOperation(value = "导入成绩失败导出")
    public void exportErrorRecordExport(
            @RequestBody List<PpmKqRecordImportDto> ppmKqRecordImportDtos,HttpServletResponse response) {
        List<PpmKqRecordExportDto> list = ppmKqRecordImportService.exportExcel(ppmKqRecordImportDtos);
        if(!list.isEmpty()){
            ExcelUtil.exportExcel(list,"考勤记录信息表","sheet1",PpmKqRecordExportDto.class,"考勤信息导入失败数据.xls",true,response);
        }
        log.info("导出成功。");
    }

    /**
     * 查询加班开始时间配置
     * @param ppmKqOvertimeConfigDto ppmKqOvertimeConfigDto
     * @return ApiResult<Object>
     */
    @GetMapping("queryOvertimeConfig")
    @ApiOperation(value = "查询加班开始时间配置")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "status", value = "状态", dataType = "string", paramType = "query"),
    })
    public ApiResult<PpmKqOvertimeConfigDto> queryOvertimeConfig(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto) {
        ApiResult<PpmKqOvertimeConfigDto> result = new ApiResult<>();
        PpmKqOvertimeConfigDto item = ppmKqOvertimeConfigService.queryDetail(ppmKqOvertimeConfigDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 加班开始时间配置
     * @param ppmKqOvertimeConfigDto ppmKqOvertimeConfigDto
     * @return ApiResult<Object>
     */
    @PostMapping("addOvertimeConfig")
    @ApiOperation(value = "加班开始时间配置")
    public ApiResult<Object> addOvertimeConfig(PpmKqOvertimeConfigDto ppmKqOvertimeConfigDto) {
        ApiResult<Object> result = new ApiResult<>();
        ppmKqOvertimeConfigService.addPpmKqOvertimeConfigHandle(ppmKqOvertimeConfigDto);

        //修改后 将生效日期之后的考勤信息加班时间都改为 新的加班时间
        String attendanceDateValue = DateUtil.dateToStr(ppmKqOvertimeConfigDto.getEffectDate(),"yyyy/MM/dd");
        PpmKqRecordDto ppmKqRecordDto = new PpmKqRecordDto();
        ppmKqRecordDto.setAttendanceDateValue(attendanceDateValue);
        ppmKqRecordDto.setIsCalOvertime("0");
        ppmKqRecordDto.setOvertimeStartTime(ppmKqOvertimeConfigDto.getStartTime());
        ppmKqRecordService.updatePpmKqRecordOvertime(ppmKqRecordDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 数据跑批接口
     */
    @PostMapping("infoDataCalculateRunBatch")
    @ApiOperation(value = "infoDataCalculateRunBatch")
    public void infoDataCalculateRunBatch() {
        log.info("手动跑批计算加班时长 开始");
        ppmKqRecordCalculateJobService.overtimeCalaulate();
        log.info("手动跑批计算加班时长 完成");

        log.info("手动跑批计算异常考勤 开始");
        ppmKqRecordCalculateJobService.recordCalaulate();
        log.info("手动跑批计算异常考勤 完成");

        log.info("手动跑批计算考勤次数 完成");
        ppmKqInfoCalculateJobService.infoCalaulate();
        log.info("手动跑批计算考勤次数 完成");

    }

//    /**
//     * 数据跑批接口
//     */
//    @PostMapping("annualVacationBatch")
//    @ApiOperation(value = "annualVacation")
//    public void annualVacationBatch() {
//
//        log.info("手动跑批增加年假 开始");
//        ppmKqInfoService.testVacation();
//        log.info("手动跑批增加年假 完成");
//
//    }

    /**
     * 导出信息接口
     * @param ppmKqInfoDto ppmKqInfoView
     * @param request request
     * @param response response
     */
    @PostMapping("exportInfo")
    @ApiOperation(value = "考勤信息导出")
    public void exportInfo(PpmKqInfoDto ppmKqInfoDto, HttpServletRequest request,HttpServletResponse response) {
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        List<PpmKqInfoExportDto> list = ppmKqInfoService.exportExcel(ppmKqInfoDto,user);
        if(!list.isEmpty()){
            ExcelUtil.exportExcel(list, PpmKqInfoExportDto.class,"考勤信息导出数据.xls",response);
        }
        log.info("导出成功。");
    }

    /**
     * 导出信息接口
     * @param ppmKqInfoDto ppmKqInfoView
     * @param request request
     * @param response response
     */
    @PostMapping("exportRecord")
    @ApiOperation(value = "考勤信息导出")
    public void exportRecord(PpmKqInfoDto ppmKqInfoDto, HttpServletRequest request,HttpServletResponse response) {
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        List<PpmKqRecordExportAllDto> list = ppmKqInfoService.exportRecordExcel(ppmKqInfoDto,user);
        if(!list.isEmpty()){
            ExcelUtil.exportExcel(list, PpmKqRecordExportAllDto.class,"考勤信息导出数据.xls",response);
        }
        log.info("导出成功。");
    }

    /**
     * 导出未处理异常
     * @param ppmKqInfoDto ppmKqInfoView
     * @param request request
     * @param response response
     */
    @PostMapping("exportNoVerify")
    @ApiOperation(value = "考勤信息导出")
    public void exportNoVerify(PpmKqInfoDto ppmKqInfoDto, HttpServletRequest request,HttpServletResponse response) {
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        String otherExportTime = ppmKqInfoDto.getOtherExportTime();
        if (!otherExportTime.contains(BusinessUtil.REGEX_COMMA)){
            throw new BizException(ApiCode.FAIL, "时间需为周期时间！");
        }
        List<PpmKqRecordExportNoVerifyDto> list1 = ppmKqInfoService.exportNoVerify(ppmKqInfoDto,user);
        if(list1 != null){
            ExcelUtil.exportExcel(list1, PpmKqRecordExportNoVerifyDto.class,"考勤信息导出数据.xls",response);
        }
        log.info("导出成功。");
    }
    /**
     * 分页
     *
     * @param ppmKqRecordDto 对象
     * @param pager            分页对象
     * @param request  request
     * @return ApiResult
     */
    @PostMapping(value = "queryRecordPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobId", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "attendanceDateValue", value = "考勤日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "所属部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "whetherHandle", value = "是否处理", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<Object> queryRecordPager(PpmKqRecordDto ppmKqRecordDto, Pager pager, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmKqRecordService.queryPager(ppmKqRecordDto, pager,user);

        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }
    /**
     * 更新 多选
     *
     * @param ppmKqRecordDtos 对象
     * @param request          对象
     * @return ApiResult
     */
    @PostMapping(value = "cipherRecordList")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> cipherRecordList(
            @RequestBody List<PpmKqRecordDto> ppmKqRecordDtos, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        log.info("手动刷新计算异常考勤 开始");
        int num = ppmKqRecordCalculateJobService.cipherRecordList(ppmKqRecordDtos);
        log.info("手动刷新计算异常考勤 完成");

        log.info("手动刷新计算考勤次数 开始");
        ppmKqInfoCalculateJobService.infoCalaulate();
        log.info("手动刷新计算考勤次数 完成");
        result.setResults(num);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
    @PostMapping(value = "backRecordList")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> backRecordList(
            @RequestBody List<PpmKqRecordDto> ppmKqRecordDtos, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmKqRecordService.backRecordList(ppmKqRecordDtos, user);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
    /**
     * 更新 多选
     *
     * @param ppmKqRecordDtos 对象
     * @param request          对象
     * @return ApiResult
     */
    @PostMapping(value = "updateRecordList")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updateRecordList(
            @RequestBody List<PpmKqRecordDto> ppmKqRecordDtos, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        ppmKqRecordDtos.forEach(ppmKqRecordDto ->{
            //异常原因所选是年假的总个数
            long vacationDays = ppmKqRecordDtos.stream()
                    .filter(x -> "3".equals(x.getAbnormalReason()) && ppmKqRecordDto.getJobId().equals(x.getJobId()))
                    .count();
            //实际的剩余天数
            double validVacationDays = ppmKqRecordDto.getAnnualVacationDays()+ppmKqRecordDto.getAnnualVacationSurplus();
            if (vacationDays > validVacationDays * 2){
                throw new BizException(ApiCode.FAIL,
                        ppmKqRecordDto.getUserName()+"剩余年假"
                                +validVacationDays+"天，所选年假天数"+(double)(vacationDays)/2+"天");
            }
            PpmKqRecordDto recordDto = new PpmKqRecordDto();
            if(user != null){
                recordDto.setModifiedBy(user.getUserid());
            }

            if("AM".equals(ppmKqRecordDto.getDataType())){
                recordDto.setAmWhetherHandle("2");
                recordDto.setAmWhetherAbnormal(ppmKqRecordDto.getWhetherAbnormal());
                recordDto.setAmAbnormalReason(ppmKqRecordDto.getAbnormalReason());
                recordDto.setAmRemark(ppmKqRecordDto.getRemark());
            }else if("PM".equals(ppmKqRecordDto.getDataType())){
                recordDto.setPmWhetherHandle("2");
                recordDto.setPmWhetherAbnormal(ppmKqRecordDto.getWhetherAbnormal());
                recordDto.setPmAbnormalReason(ppmKqRecordDto.getAbnormalReason());
                recordDto.setPmRemark(ppmKqRecordDto.getRemark());
            }
            recordDto.setModifiedTime(new Date());
            recordDto.setId(ppmKqRecordDto.getId());
            recordDto.setInfoId(ppmKqRecordDto.getInfoId());
            ppmKqRecordService.updatePpmKqRecordAndInfoAdd(recordDto);

            if ("3".equals(ppmKqRecordDto.getAbnormalReason())){
                PpmKqAnnualVacationDto ppmKqAnnualVacationDto = new PpmKqAnnualVacationDto();
                ppmKqAnnualVacationDto.setJobId(ppmKqRecordDto.getJobId());
                ppmKqAnnualVacationService.validAnnualVacationMinus(ppmKqAnnualVacationDto,0.5);
            }
        });

        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 修改日历 需要改变考勤是否节假日 并且重新计算
     *
     * @param ppmKqCalendarDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "updateRecordHoliday")
    @ApiOperation(value = "更新日历附加", notes = "更新日历附加")
    public ApiResult<Object> updateRecordHoliday(@RequestBody  PpmKqCalendarDto ppmKqCalendarDto) {
        ApiResult<Object> result = new ApiResult<>();
        Date date = ppmKqCalendarDto.getCalendarDate();
        String isHoliday = ppmKqCalendarDto.getIsHoliday();
        if(date != null){
            ppmKqRecordService.updateRecordHoliday(date,isHoliday);

            PpmKqInfoDto ppmKqInfoDto = new PpmKqInfoDto();
            ppmKqInfoDto.setAttendanceMonth(DateUtil.dateToStr(date,"yyyyMM"));
            ppmKqInfoDto.setWhetherUpdate("0");
            ppmKqInfoDto.setFifthWorkingDate(ppmKqInfoService.compareFifthWorkingDate(date));
            ppmKqInfoService.updatePpmKqInfoWhetherUpdate(ppmKqInfoDto);

            // 修改非科技外包考勤是否节假日字段
            ppmKqRecordOtherService.updateRecordOtherHoliday(date,isHoliday);
        }

        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 导出工作量明细
     *
     * @param ppmKqInfoDto ppmKqInfoView
     * @param request      request
     * @param response     response
     */
    @PostMapping("exportWorkload")
    @ApiOperation(value = "导出工作量明细")
    public void exportWorkload(PpmKqInfoDto ppmKqInfoDto, HttpServletRequest request, HttpServletResponse response) {
        log.info("导出工作量明细请求{}", JSONUtil.toJsonStr(ppmKqInfoDto));
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        List<PpmWorkloadKqInfoView> list = supplierContractService.exportWorkload(ppmKqInfoDto, user);
        if (CollectionUtil.isNotEmpty(list)) {
            ExcelUtil.exportExcel(list, PpmWorkloadKqInfoView.class, "工作量明细考勤数据.xls", response);
        }
        log.info("导出成功。");
    }

}
