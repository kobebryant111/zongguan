package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportAllDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportNoVerifyDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendar;
import com.work.ppm.application.model.integratedmanager.PpmKqInfo;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.ext.PpmKqInfoView;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.quartz.interfaces.IPpmKqInfoCalculateJobService;
import com.work.ppm.application.services.integratedmanager.IPpmKqAnnualVacationService;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarService;
import com.work.ppm.application.services.integratedmanager.IPpmKqInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqOvertimeConfigService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.joda.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤信息表 接口实现类
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
@Service("ppmKqInfoService")
public class PpmKqInfoService extends BaseDaoSupper implements IPpmKqInfoService, IPpmKqInfoCalculateJobService {
    /**
     * 实体类路径
     */
    private static final String PPMKQINFO = "com.work.ppm.application.model.integratedmanager.PpmKqInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 考勤信息服务
     */
    @Autowired
    private IPpmKqRecordService ppmKqRecordService;

    /**
     * 外包人员信息服务
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    /**
     * 用户信息服务
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 考勤日历服务接口
     */
    @Autowired
    IPpmKqCalendarService ppmKqCalendarService;

    /**
     * 加班开始时间配置
     */
    @Autowired
    private IPpmKqOvertimeConfigService ppmKqOvertimeConfigService;

    /**
     * 加班开始时间配置
     */
    @Autowired
    private IPpmKqAnnualVacationService ppmKqAnnualVacationService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;


    private static final String UNIQUE_NAME_VIEW_LIST = "queryBaseInfoList";

    @Override
    public void queryPager(PpmKqInfoDto ppmKqInfoDto, Pager pager,CommOperatorDoLoginDto user) {
        if(ppmKqInfoDto.getAttendanceMonth() != null){
            ppmKqInfoDto.setAttendanceMonth(ppmKqInfoDto.getAttendanceMonth().replace("-",""));
        }

        if(ppmKqInfoDto.getDeptId() != null){
            ppmKqInfoDto.setDeptId(ppmKqInfoDto.getDeptId().replace("dep",""));
        }

        handleAuthCode(ppmKqInfoDto, user);
        PpmKqInfoDto kqInfoDto = JsonUtil.jsonToClass(ppmKqInfoDto,PpmKqInfoDto.class);
        Map<String, Object> map = JsonUtil.objectTOMap(kqInfoDto, PpmKqInfoDto.class);



        if (StringUtil.isStringNotEmpty(ppmKqInfoDto.getAttendanceMonth()) && ppmKqInfoDto.getAttendanceMonth().contains(",")){
            String[] split = ppmKqInfoDto.getAttendanceMonth().split(",");
            String startDate = split[0];
            String endDate = split[1];
            map.put("startDate", startDate);
            map.put("endDate", endDate);
        }
        myBatisDao.selectPager(PPMKQINFO, map, pager, UNIQUE_NAME_VIEW_LIST);
        List<PpmKqInfoView> list = JsonUtil.jsonToList(pager.getList(),PpmKqInfoView.class);
        List<PpmKqInfoDto> infoDtos = new ArrayList<>();
        for(PpmKqInfoView obj : list){
            PpmKqInfoDto ppmKqInfoDto1 = JsonUtil.jsonToClass(obj, PpmKqInfoDto.class);
            if(ppmKqInfoDto1.getDeptName() != null){
                String[] s = ppmKqInfoDto1.getDeptName().split("-");
                if(s.length > 1){
                    ppmKqInfoDto1.setDeptName(s[1]);
                }
            }

            infoDtos.add(ppmKqInfoDto1);
        }
        pager.setList(infoDtos);
    }

    @Override
    public PpmKqInfoDto queryDetail(PpmKqInfoDto ppmKqInfoDto){
        PpmKqInfoDto ppmKqInfoDto1 = queryDetailObject(ppmKqInfoDto,"queryBaseInfo");
        //计算异常次数
        ppmKqInfoDto1.setExceptionNumber(StringUtil.objToLong(ppmKqInfoDto1.getForgetClockInNumber()) +
                StringUtil.objToLong(ppmKqInfoDto1.getBelateNumber()) +
                StringUtil.objToLong(ppmKqInfoDto1.getLeaveEarlyNumber()));
        if(ppmKqInfoDto1 == null){
            return ppmKqInfoDto;
        }
        if(ppmKqInfoDto1.getDeptName() != null){
            String[] s = ppmKqInfoDto1.getDeptName().split("-");
            if(s.length > 1){
                ppmKqInfoDto1.setDeptName(s[1]);
            }
        }
        //现有剩余年假
        ppmKqInfoDto1.setAnnualVacationNumber(
                ppmKqInfoDto1.getAnnualVacationDays()+ppmKqInfoDto1.getAnnualVacationSurplus());

        List<PpmKqRecordDto> recordDtos = ppmKqRecordService.queryDetailList(ppmKqInfoDto.getId());
        recordDtos.forEach(recordDto ->{
            recordDto.setActionType(false);
            recordDto.setPmName(ppmKqInfoDto1.getPmName());
            recordDto.setDeptName(ppmKqInfoDto1.getDeptName());
            //返回第五个工作日
            Date fifthWorkingDate = ppmKqInfoDto1.getFifthWorkingDate();
            if (fifthWorkingDate != null){
                LocalDate now = LocalDate.now();
                LocalDate fifthWorking = LocalDate.parse(DateUtil.dateToStr(fifthWorkingDate,"yyyy-MM-dd"));
                recordDto.setWhetherUpdate(now.isAfter(fifthWorking));
            }else {
                recordDto.setWhetherUpdate(false);
            }

            if(StringUtils.isEmpty(recordDto.getPmWhetherAbnormal())){
                recordDto.setPmWhetherAbnormal("");
            }

            if(StringUtils.isEmpty(recordDto.getAmWhetherAbnormal())){
                recordDto.setAmWhetherAbnormal("");
            }
            if(recordDto.getAmAbnormalReason() != null){
                recordDto.setAmAbnormalReasonList(recordDto.getAmAbnormalReason().split("/"));
            }else {
                recordDto.setAmAbnormalReasonList(null);
            }
            if(recordDto.getPmAbnormalReason() != null){
                recordDto.setPmAbnormalReasonList(recordDto.getPmAbnormalReason().split("/"));
            }else {
                recordDto.setPmAbnormalReasonList(null);
            }
        });
        ppmKqInfoDto1.setKqRecordDtoList(recordDtos);
        return ppmKqInfoDto1;
    }

    @Override
    public int addPpmKqInfo(PpmKqInfoDto ppmKqInfoDto) {
        PpmKqInfo ppmKqInfo = JsonUtil.jsonToClass(ppmKqInfoDto, PpmKqInfo.class);
        myBatisDao.executionPojo(PPMKQINFO, ppmKqInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmKqInfoDto queryDetailObject(PpmKqInfoDto ppmKqInfoDto,String uniqueName) {
        PpmKqInfo ppmKqInfo = JsonUtil.jsonToClass(ppmKqInfoDto, PpmKqInfo.class);
        PpmKqInfo data = myBatisDao.selectClass(PPMKQINFO, ppmKqInfo, uniqueName);
        return JsonUtil.jsonToClass(data, PpmKqInfoDto.class);
    }

    @Override
    public List<PpmKqInfoDto> queryDetailList(PpmKqInfoDto ppmKqInfoDto) {
        PpmKqInfo ppmKqInfo = JsonUtil.jsonToClass(ppmKqInfoDto, PpmKqInfo.class);
        List<PpmKqInfo> data = myBatisDao.selectListT(PPMKQINFO, ppmKqInfo, "getList");
        return JsonUtil.jsonToList(data, PpmKqInfoDto.class);
    }

    @Override
    public int updatePpmKqInfo(PpmKqInfoDto ppmKqInfoDto) {
        PpmKqInfo ppmKqInfo = JsonUtil.jsonToClass(ppmKqInfoDto, PpmKqInfo.class);
        return myBatisDao.executionPojo(PPMKQINFO, ppmKqInfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int updatePpmKqInfoAdd(PpmKqInfoDto ppmKqInfoDto) {
        PpmKqInfo ppmKqInfo = JsonUtil.jsonToClass(ppmKqInfoDto, PpmKqInfo.class);
        return myBatisDao.executionPojo(PPMKQINFO, ppmKqInfo, IMyBatisDao.SQL.UPDATE, "updatePojoAdd");
    }

    @Override
    public int updatePpmKqInfoWhetherUpdate(PpmKqInfoDto ppmKqInfoDto) {
        PpmKqInfo ppmKqInfo = JsonUtil.jsonToClass(ppmKqInfoDto, PpmKqInfo.class);
        return myBatisDao.executionPojo(PPMKQINFO, ppmKqInfo, IMyBatisDao.SQL.UPDATE, "updatePojoWhetherUpdate");
    }

    /**
     * 计算考勤相关次数、天数
     * @param attendanceMonth 考勤年月
     * @param list 考勤记录
     * @return map
     */
    public PpmKqInfoDto getKqCalaulateNumberMap(
            PpmKqInfoDto ppmKqInfoDto,String attendanceMonth,List<PpmKqRecordDto> list){
        //忘记打卡次数
        long forgetClockInNumber = 0;
        //新忘记打卡次数
        long forgetClockInNumberNew = 0;
        //迟到次数
        long belateNumber = 0;
        //新迟到次数
        long belateNumberNew = 0;
        //早退次数
        long leaveEarlyNumber = 0;
        //新早退次数
        long leaveEarlyNumberNew = 0;
        //旷工半天次数
        long absentNumber = 0;
        //旷工一天次数
        long absentDoubleNumber = 0;
        //请假半天次数
        long leaveNumber = 0;
        //请假一天次数
        long leaveDoubleNumber = 0;
        //应出勤天数
        double requiredAttendanceDays = 0.0;
        //实际出勤天数
        double actualAttendanceDays = 0.0;
        //加班时长 小时
        double overtimeHours = 0.0;
        //豁免起始次数
//        long exemptionNumber = 2;

        if(attendanceMonth != null && attendanceMonth.length() == 6){
            Long month = Long.valueOf(attendanceMonth.substring(4,6));
            Long years = Long.valueOf(attendanceMonth.substring(0,4));
            requiredAttendanceDays = this.queryCalendarListSize(month,years,"N");
        }

        if(list != null && !list.isEmpty()){
            // 计算迟到次数，早退次数，旷工次数/半天
            forgetClockInNumber = judgeAbnormalNumber(list,"0");

            belateNumber = judgeAbnormalNumber(list,"1");

            leaveEarlyNumber = judgeAbnormalNumber(list,"2");

            leaveNumber = judgeAbnormalNumber(list,"5");

            leaveDoubleNumber = judgeAbnormalNumber(list,"6");

            absentNumber = judgeAbnormalNumber(list,"7");

            absentDoubleNumber = judgeAbnormalNumber(list,"8");

            forgetClockInNumberNew = judgeAbnormalNumber(list,"12");

            belateNumberNew = judgeAbnormalNumber(list,"13");

            leaveEarlyNumberNew = judgeAbnormalNumber(list,"14");

            overtimeHours = list.stream().filter(x -> x.getOvertimeHours() != null).
                    mapToDouble(PpmKqRecordDto::getOvertimeHours).sum();

            //工作日天数
            double amWhetherDays = list.stream().filter(x -> "0".equals(x.getIsHoliday())).
                    filter(x -> "0".equals(x.getAmWhetherAbnormal())).
                    filter(x -> !"11".equals(x.getAmAbnormalReason())).
                    filter(x -> !"15".equals(x.getAmAbnormalReason())).count();

            double pmWhetherDays = list.stream().filter(x -> "0".equals(x.getIsHoliday())).
                    filter(x -> "0".equals(x.getPmWhetherAbnormal())).
                    filter(x -> !"11".equals(x.getPmAbnormalReason())).
                    filter(x -> !"15".equals(x.getPmAbnormalReason())).count();

            //实际出勤天数 添加上已处理的忘记打卡的数据
            actualAttendanceDays = (amWhetherDays + pmWhetherDays + forgetClockInNumberNew) / 2 ;

            //  0 不处理 0.5 1 >1
//            double errorDays = requiredAttendanceDays - actualAttendanceDays;
//            // 豁免次数 errorDays 0.5 1 >1 异常天数  exemptionNumber 0 1 2 剩余豁免次数 actualAttendanceDays 出勤天数
//            if (errorDays >= exemptionNumber * 0.5){
//                actualAttendanceDays += exemptionNumber * 0.5;
//                exemptionNumber -= exemptionNumber;
//            }
//
//            if(errorDays < exemptionNumber * 0.5){
//                actualAttendanceDays += errorDays;
//                exemptionNumber -= errorDays / 0.5;
//            }
        }

        ppmKqInfoDto.setAbsentNumber(absentNumber+absentDoubleNumber * 2);
        ppmKqInfoDto.setForgetClockInNumber(forgetClockInNumber + forgetClockInNumberNew);
        ppmKqInfoDto.setBelateNumber(belateNumber + belateNumberNew);
        ppmKqInfoDto.setLeaveEarlyNumber(leaveEarlyNumber + leaveEarlyNumberNew);
        ppmKqInfoDto.setLeaveNumber(leaveNumber+leaveDoubleNumber * 2);
        ppmKqInfoDto.setRequiredAttendanceDays(requiredAttendanceDays);
        ppmKqInfoDto.setActualAttendanceDays(actualAttendanceDays);
//        ppmKqInfoDto.setExemptionNumber(exemptionNumber);
        ppmKqInfoDto.setOvertimeHours(overtimeHours / 60);
        return ppmKqInfoDto;
    }

    /**
     * 判断异常原因是否包含参数
     * @param abnormalReason 异常原因
     * @param key 参数
     * @return boolean
     */
    public boolean judgeAbnormalReason(String abnormalReason,String key){
        if(StringUtils.isNotEmpty(abnormalReason)){
            if (abnormalReason.contains("/")){
                return abnormalReason.contains(key);
            }else {
                return abnormalReason.equals(key);
            }

        }
        return false;
    }

    /**
     * 判断是否处理是否为已处理
     * @param whetherHandle 是否处理
     * @param key 参数
     * @return boolean
     */
    public boolean judgeWhetherHandle(String whetherHandle,String key){
        if(StringUtils.isNotEmpty(whetherHandle)){
            return whetherHandle.equals(key);
        }
        return false;
    }

    /**
     * 计算次数
     * @param list 集合
     * @param dictId 字典dictId
     * @return long
     */
    private long judgeAbnormalNumber(List<PpmKqRecordDto> list,final String dictId){
        return
                list.stream().filter(x -> "0".equals(x.getIsHoliday()))
                        .filter(x -> judgeAbnormalReason(x.getAmAbnormalReason(),dictId))
                        .filter(x -> judgeWhetherHandle(x.getAmWhetherHandle(),"2"))//已处理
                        .count() +
                list.stream().filter(x -> "0".equals(x.getIsHoliday()))
                        .filter(x -> judgeAbnormalReason(x.getPmAbnormalReason(),dictId))
                        .filter(x -> judgeWhetherHandle(x.getPmWhetherHandle(),"2"))//已处理
                        .count();
    }

    @Override
    public Long queryCalendarListSize(Long month, Long years, String isHoliday) {
        String dataSource = "com.work.ppm.application.model.integratedmanager.PpmKqCalendar";
        Map<String, Object> map = new HashMap<>();
        map.put("monthOfYear", month);
        map.put("yearOfCalendar", years);
        if(StringUtils.isNotEmpty(isHoliday)){
            map.put("isHoliday", isHoliday);
        }
        List<PpmKqCalendar> list = myBatisDao.selectListT(dataSource, map, "getList");
        List<PpmKqCalendarDto> dtos = JsonUtil.jsonToList(list, PpmKqCalendarDto.class);
        return (long) dtos.size();
    }

    @Override
    public Long queryCalendarPeriod(Date entryStartDate, Date entryEndDate, String isHoliday) {
        String dataSource = "com.work.ppm.application.model.integratedmanager.PpmKqCalendar";
        Map<String, Object> map = new HashMap<>();
        map.put("entryStartDate", entryStartDate);
        map.put("entryEndDate", entryEndDate);
        if (StringUtils.isNotEmpty(isHoliday)) {
            map.put("isHoliday", isHoliday);
        }
        List<PpmKqCalendar> list = myBatisDao.selectListT(dataSource, map, "getCalendarList");
        List<PpmKqCalendarDto> dtos = JsonUtil.jsonToList(list, PpmKqCalendarDto.class);
        return (long) dtos.size();
    }

    @Override
    @Transactional
    public void infoCalaulate() {
        log.info("定时计算考勤次数 开始");
        PpmKqInfoDto kqInfoDto = new PpmKqInfoDto();
        kqInfoDto.setWhetherUpdate("0");
        List<PpmKqInfoDto> kqInfoList = queryDetailList(kqInfoDto);

        if(!kqInfoList.isEmpty()){
            kqInfoList.forEach(kqInfo->{
                if(kqInfo != null){
                    List<PpmKqRecordDto> recordDtos = ppmKqRecordService.queryDetailList(kqInfo.getId());
                    kqInfo = getKqCalaulateNumberMap(kqInfo,kqInfo.getAttendanceMonth(),recordDtos);
                    kqInfo.setWhetherUpdate("1");
                    updatePpmKqInfo(kqInfo);
                }
            });
        }
        log.info("定时计算考勤次数 结束");
    }

    @Override
    public List<PpmKqInfoExportDto> exportExcel(PpmKqInfoDto ppmKqInfoDto, CommOperatorDoLoginDto user) {
        handleAuthCode(ppmKqInfoDto, user);
        if(StringUtils.isNotEmpty(ppmKqInfoDto.getAttendanceMonth())){
            ppmKqInfoDto.setAttendanceMonth(ppmKqInfoDto.getAttendanceMonth().replace("-",""));
        }

        if(StringUtils.isNotEmpty(ppmKqInfoDto.getDeptId())){
            ppmKqInfoDto.setDeptId(ppmKqInfoDto.getDeptId().replace("dep",""));
        }

        Map<String,Object> map = JsonUtil.objectTOMap(ppmKqInfoDto, PpmKqInfoDto.class);

        if (StringUtil.isStringNotEmpty(ppmKqInfoDto.getAttendanceMonth()) && ppmKqInfoDto.getAttendanceMonth().contains(",")){
            String[] split = ppmKqInfoDto.getAttendanceMonth().split(",");
            String startDate = split[0];
            String endDate = split[1];
            map.put("startDate", startDate);
            map.put("endDate", endDate);
        }
        List<PpmKqInfoView> list = myBatisDao.selectListT(PPMKQINFO, map, UNIQUE_NAME_VIEW_LIST);
        List<PpmKqInfoDto> ppmKqInfoDtos = JsonUtil.jsonToList(list, PpmKqInfoDto.class);
        ppmKqInfoDtos.forEach(ppmKqInfoDto1 -> {
            if(ppmKqInfoDto1.getDeptName() != null){
                String[] s = ppmKqInfoDto1.getDeptName().split("-");
                if(s.length > 1){
                    ppmKqInfoDto1.setDeptName(s[1]);
                }
            }
            ppmKqInfoDto1.setExceptionNumber(StringUtil.objToLong(ppmKqInfoDto1.getForgetClockInNumber()) +
                    StringUtil.objToLong(ppmKqInfoDto1.getBelateNumber()) +
                    StringUtil.objToLong(ppmKqInfoDto1.getLeaveEarlyNumber()));
        });
        return JsonUtil.jsonToList(ppmKqInfoDtos, PpmKqInfoExportDto.class);
    }

    @Override
    public List<PpmKqRecordExportAllDto> exportRecordExcel(PpmKqInfoDto ppmKqInfoDto, CommOperatorDoLoginDto user) {
        handleAuthCode(ppmKqInfoDto, user);
        if(StringUtils.isNotEmpty(ppmKqInfoDto.getAttendanceMonth())){
            ppmKqInfoDto.setAttendanceMonth(ppmKqInfoDto.getAttendanceMonth().replace("-",""));
        }

        if(StringUtils.isNotEmpty(ppmKqInfoDto.getDeptId())){
            ppmKqInfoDto.setDeptId(ppmKqInfoDto.getDeptId().replace("dep",""));
        }

        Map<String,Object> map = JsonUtil.objectTOMap(ppmKqInfoDto, PpmKqInfoDto.class);

        if (StringUtil.isStringNotEmpty(ppmKqInfoDto.getAttendanceMonth()) && ppmKqInfoDto.getAttendanceMonth().contains(",")){
            String[] split = ppmKqInfoDto.getAttendanceMonth().split(",");
            String startDate = split[0];
            String endDate = split[1];
            map.put("startDate", startDate);
            map.put("endDate", endDate);
        }
        List<PpmKqInfoView> list = myBatisDao.selectListT(PPMKQINFO, map, "queryBaseRecordList");
        List<PpmKqInfoDto> ppmKqInfoDtos = JsonUtil.jsonToList(list, PpmKqInfoDto.class);

        List<EosDictEntry> dictList = eosDictEntryService.getTypeidForList("KQ_RECORD_REASON_DICT");
        Map<String,Object> dictMap = new HashMap<>();
        dictList.forEach(obj -> dictMap.put(obj.getDictid(),obj.getDictname()));

        ppmKqInfoDtos.forEach(ppmKqInfoDto1 -> {
//            if(ppmKqInfoDto1.getDeptName() != null){
//                String[] s = ppmKqInfoDto1.getDeptName().split("-");
//                if(s.length > 1){
//                    ppmKqInfoDto1.setDeptName(s[1]);
//                }
//            }
            ppmKqInfoDto1.setDeptName(BusinessUtil.getDeptNameValue(ppmKqInfoDto1.getDeptName(),1));

            if(ppmKqInfoDto1.getAmWhetherAbnormal() == null){
                ppmKqInfoDto1.setAmWhetherAbnormal("");
            }

            if(ppmKqInfoDto1.getPmWhetherAbnormal() == null){
                ppmKqInfoDto1.setPmWhetherAbnormal("");
            }

            String amAbnormalReason = ppmKqInfoDto1.getAmAbnormalReason();
            if(amAbnormalReason == null){
                ppmKqInfoDto1.setAmAbnormalReason("");
            }else if(amAbnormalReason.contains("/")){
                ppmKqInfoDto1.setAmAbnormalReason(BusinessUtil.replaceArrays(dictMap,amAbnormalReason,"/"));
            }

            String pmAbnormalReason = ppmKqInfoDto1.getPmAbnormalReason();
            if(pmAbnormalReason == null){
                ppmKqInfoDto1.setPmAbnormalReason("");
            }else if(pmAbnormalReason.contains("/")){
                ppmKqInfoDto1.setPmAbnormalReason(BusinessUtil.replaceArrays(dictMap,pmAbnormalReason,"/"));
            }

            if (ppmKqInfoDto1.getAttendanceDate() != null){
                ppmKqInfoDto1.setAttendanceDate(
                        DateUtil.strToStr(ppmKqInfoDto1.getAttendanceDate(),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"));
            }

            if (ppmKqInfoDto1.getWorkingTime() != null){
                ppmKqInfoDto1.setWorkingTime(
                        DateUtil.strToStr(ppmKqInfoDto1.getWorkingTime(),"yyyy-MM-dd HH:mm:ss","HH:mm:ss"));
            }

            if (ppmKqInfoDto1.getClosingTime() != null){
                String closingTime =
                        DateUtil.strToStr(ppmKqInfoDto1.getClosingTime(),"yyyy-MM-dd HH:mm:ss","HH:mm:ss");

                if ("1".equals(ppmKqInfoDto1.getWhetherOvernight())){
                    closingTime += "+";
                }
                ppmKqInfoDto1.setClosingTime(closingTime);
            }
        });
        return JsonUtil.jsonToList(ppmKqInfoDtos, PpmKqRecordExportAllDto.class);
    }

    @Override
    public List<PpmKqRecordExportNoVerifyDto> exportNoVerify(PpmKqInfoDto ppmKqInfoDto, CommOperatorDoLoginDto user) {
        String [] list1 = ppmKqInfoDto.getOtherExportTime().split(BusinessUtil.REGEX_COMMA);
        ppmKqInfoDto.setAttendanceDateSt(list1[0]);
        ppmKqInfoDto.setAttendanceDateEnd(list1[1]);

        Map<String,Object> map = JsonUtil.objectTOMap(ppmKqInfoDto, PpmKqInfoDto.class);
        List<PpmKqInfoView> list = myBatisDao.selectListT(PPMKQINFO, map, "queryNoVerifyList");
        List<PpmKqRecordExportNoVerifyDto> ppmKqInfoDtos =
                JsonUtil.jsonToList(list, PpmKqRecordExportNoVerifyDto.class);
        ppmKqInfoDtos.forEach(ppmKqInfoDto1 ->
                ppmKqInfoDto1.setDeptName(BusinessUtil.getDeptNameValue(ppmKqInfoDto1.getDeptName(),1)));
        return ppmKqInfoDtos;
    }


    /**
     * 处理权限
     * @param ppmKqInfoDto ppmKqInfoDto
     * @param user user
     */
    public void handleAuthCode(PpmKqInfoDto ppmKqInfoDto, CommOperatorDoLoginDto user){
        // 权限控制
        String authCode = ppmOutsourcingBasicService.getPermissionByUserid(user);
        // 二级部经理需要加入部门限制
        if ("managers".equals(authCode)) {
            ppmKqInfoDto.setDeptValue(user.getDeptId());
            // 项目经理需要加入人员限制
        } else if ("common".equals(authCode)) {
            ppmKqInfoDto.setPmId(user.getUserid());
            //系统管理员和一级部经理不做限制
        }
    }

    @Override
    @Transactional
    public void addInfoAndRecord() {
        Map<String,Object> map = new HashMap<>();
        map.put("state","0");
        //先查出有多少外包人员
        List<PpmOutsourcingBasic> ppmOutsourcingBasics =  ppmOutsourcingBasicService.queryOutsList(map);
        for(PpmOutsourcingBasic ppmOutsourcingBasic : ppmOutsourcingBasics){
            PpmKqInfoDto ppmKqInfo = new PpmKqInfoDto();
            ppmKqInfo.setJobId(ppmOutsourcingBasic.getJobId());
            ppmKqInfo.setUserName(ppmOutsourcingBasic.getUserName());
            String attendanceYearMonth = DateUtil.dateToStr(new Date(),"yyyyMM");
            ppmKqInfo.setAttendanceMonth(attendanceYearMonth);
            ppmKqInfo = queryDetailObject(ppmKqInfo,"queryDetailObject");
            if(ppmKqInfo == null){
                ppmKqInfo = new PpmKqInfoDto();
                ppmKqInfo.setId(SnowflakeIdWorker.getSnowId());
                ppmKqInfo.setCreatedTime(new Date());
                ppmKqInfo.setCreatedBy("autoNormal");
                ppmKqInfo.setJobId(ppmOutsourcingBasic.getJobId());
                ppmKqInfo.setUserName(ppmOutsourcingBasic.getUserName());
                ppmKqInfo.setAttendanceMonth(attendanceYearMonth);
                //固定初始化豁免次数=2
                ppmKqInfo.setExemptionNumber(2L);
                ppmKqInfo.setWhetherUpdate("0");
                addPpmKqInfo(ppmKqInfo);
            }

            String format = "yyyy-MM-dd";
            boolean isHoliday =
                    judgingDateIsWorkingDay(DateUtil.strToDate(DateUtil.dateToStr(new Date(),format),format));
            PpmKqRecordDto ppmKqRecord = new PpmKqRecordDto();
            ppmKqRecord.setUserName(ppmKqInfo.getUserName());
            ppmKqRecord.setJobId(ppmKqInfo.getJobId());
            Date now = DateUtil.strToDate(DateUtil.dateToStr(new Date(),format),format);
            ppmKqRecord.setAttendanceDate(now);
            ppmKqRecord = ppmKqRecordService.queryDetailObject(ppmKqRecord,ppmKqInfo.getId());
            if(ppmKqRecord == null){
                ppmKqRecord = new PpmKqRecordDto();
                ppmKqRecord.setId(SnowflakeIdWorker.getSnowId());
                ppmKqRecord.setInfoId(ppmKqInfo.getId());
                ppmKqRecord.setJobId(ppmKqInfo.getJobId());
                ppmKqRecord.setUserName(ppmKqInfo.getUserName());
                ppmKqRecord.setAttendanceDate(now);
                ppmKqRecord.setOutsourceType(ppmOutsourcingBasic.getOutsourceType());
                ppmKqRecord.setIsHoliday(judgingDateIsWorkingDay(now)?"1":"0");
                ppmKqRecord.setPrices(ppmOutsourcingBasic.getPrice());
                ppmKqRecord.setIsCalOvertime("0");
                ppmKqRecord.setOvertimeStartTime(ppmKqOvertimeConfigService.getOvertimeStartTime());
                ppmKqRecord.setExemptionState(0L);
                ppmKqRecord.setDataEntryType("normal");
                ppmKqRecord.setCreatedBy("autoNormal");
                ppmKqRecord.setCreatedTime(new Date());
                if(isHoliday){
                    ppmKqRecord.setAmWhetherHandle("3");
                    ppmKqRecord.setAmWhetherAbnormal("0");
                    ppmKqRecord.setPmWhetherHandle("3");
                    ppmKqRecord.setPmWhetherAbnormal("0");
                }else{
                    ppmKqRecord.setAmWhetherHandle("0");
                    ppmKqRecord.setPmWhetherHandle("0");
                }

                ppmKqRecordService.addPpmKqRecord(ppmKqRecord);
            }
        }
    }

    @Override
    public boolean judgingDateIsWorkingDay(Date attendanceDate){
        PpmKqCalendarDto kqCalendarDto = new PpmKqCalendarDto();
        kqCalendarDto.setCalendarDate(attendanceDate);
        PpmKqCalendarDto ppmKqCalendarDto = ppmKqCalendarService.queryDetail(kqCalendarDto);
        return ppmKqCalendarDto != null && "Y".equals(ppmKqCalendarDto.getIsHoliday());
    }

    /**
     * 比较当前日期是否在次月第5个工作日之后
     * @param attendanceDate attendanceDate
     * @return boolean
     */
    @Override
    public Date compareFifthWorkingDate(Date attendanceDate){
        List<PpmKqCalendarDto> ppmKqCalendarDtos = ppmKqRecordService.calIsFifthWoringTime(attendanceDate,2);
        if(ppmKqCalendarDtos!= null && ppmKqCalendarDtos.size()>5){
            return ppmKqCalendarDtos.get(4).getCalendarDate();
//            LocalDate fifthWorking = LocalDate.parse(DateUtil.dateToStr(fifthWorkingDate,"yyyy-MM-dd"));
//            LocalDate now = LocalDate.now();
//            return now.isAfter(fifthWorking);
        }

        return null;
    }

//    public void testVacation(){
//        Map<String,Object> map = new HashMap<>();
//        map.put("state","0");
//        //先查出有多少外包人员
//        List<PpmOutsourcingBasic> ppmOutsourcingBasics =  ppmOutsourcingBasicService.queryOutsList(map);
//        for (PpmOutsourcingBasic ppmOutsourcingBasic : ppmOutsourcingBasics ){
//            PpmKqAnnualVacationDto ppmKqAnnualVacationDto = new PpmKqAnnualVacationDto();
//            ppmKqAnnualVacationDto.setJobId(ppmOutsourcingBasic.getJobId());
//            ppmKqAnnualVacationDto = ppmKqAnnualVacationService.queryDetail(ppmKqAnnualVacationDto);
//            if (ppmKqAnnualVacationDto == null){
//                ppmKqAnnualVacationDto = new PpmKqAnnualVacationDto();
//                ppmKqAnnualVacationDto.setJobId(ppmOutsourcingBasic.getJobId());
//                ppmKqAnnualVacationDto.setAnnualVacationDate(
//                  DateUtil.intervalMonth(ppmOutsourcingBasic.getEntryTime(),6));
//                ppmKqAnnualVacationDto.setAnnualVacationDays(2.0);
//                ppmKqAnnualVacationDto.setAnnualVacationSurplus(0.0);
//                ppmKqAnnualVacationService.addPpmKqAnnualVacation(ppmKqAnnualVacationDto);
//            }else {
//                ppmKqAnnualVacationDto.setJobId(ppmOutsourcingBasic.getJobId());
//                ppmKqAnnualVacationDto.setAnnualVacationDate(
//                  DateUtil.intervalMonth(ppmOutsourcingBasic.getEntryTime(),6));
//                ppmKqAnnualVacationDto.setAnnualVacationDays(2.0);
//                ppmKqAnnualVacationDto.setAnnualVacationSurplus(0.0);
//                ppmKqAnnualVacationService.updatePpmKqAnnualVacation(ppmKqAnnualVacationDto);
//            }
//        }
//    }



}
