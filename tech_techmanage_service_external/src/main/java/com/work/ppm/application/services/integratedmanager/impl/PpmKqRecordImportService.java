package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmKqInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordImportDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmKqRecordImport;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.integratedmanager.IPpmKqInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqOvertimeConfigService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordImportService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤信息导入数据接收表 接口实现类
 *
 */
@Slf4j
@Service("ppmKqRecordImportService")
public class PpmKqRecordImportService extends BaseDaoSupper implements IPpmKqRecordImportService {
    /**
     * 实体类路径
     */
    private static final String PPMKQRECORDIMPORT =
            "com.work.ppm.application.model.integratedmanager.PpmKqRecordImport";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;


    /**
     * 外包人员信息服务
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    /**
     * 字典详情表
     */
    @Autowired
    IEosDictEntryService eosDictEntryService;

    /**
     * 考勤信息服务接口
     */
    @Autowired
    IPpmKqRecordService ppmKqRecordService;

    /**
     * 考勤记录服务接口
     */
    @Autowired
    IPpmKqInfoService ppmKqInfoService;

    /**
     * 加班开始时间
     */
    @Autowired
    IPpmKqOvertimeConfigService ppmKqOvertimeConfigService;

    private static final String OVERTIME_MINUTS = "overtimeMinuts";
    private static final String WORKTIME_MINUTS = "worktimeMinuts";
    private static final String OVERTIME_TIME_FLAG = "overtimeTimeFlag";

    @Override
    public int addPpmKqRecordImport(PpmKqRecordImportDto ppmKqRecordImportDto) {
        PpmKqRecordImport ppmKqRecordImport = JsonUtil.jsonToClass(ppmKqRecordImportDto, PpmKqRecordImport.class);
        myBatisDao.executionPojo(PPMKQRECORDIMPORT, ppmKqRecordImport, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public int updatePpmKqRecordImport(PpmKqRecordImportDto ppmKqRecordImportDto) {
        PpmKqRecordImport ppmKqRecordImport = JsonUtil.jsonToClass(ppmKqRecordImportDto, PpmKqRecordImport.class);
        return myBatisDao.executionPojo(PPMKQRECORDIMPORT, ppmKqRecordImport, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public List<PpmKqRecordImportDto> importExcel(List<PpmKqRecordImportDto> list, CommOperatorDoLoginDto user) {
        List<PpmKqRecordImportDto> exList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            Date now = new Date();
            Map<String, String> map = new HashMap<>();
            List<EosDictEntry> eosDictEntries = eosDictEntryService.getTypeidForList("KQ_RECORD_REASON_DICT");
            eosDictEntries.forEach(eosDictEntry -> map.put(eosDictEntry.getDictname(),eosDictEntry.getDictid()));
            for(PpmKqRecordImportDto recordImportDto : list){
                if(recordImportDto.getAttendanceDate() == null && recordImportDto.getJobId() == null
                        && recordImportDto.getUserName() == null && recordImportDto.getOutsourceType() == null){
                    continue;
                }

                boolean flag = true;
                String userName = recordImportDto.getUserName();
                Date attendanceDate = recordImportDto.getAttendanceDate();
                if (!verifyImportData(now,recordImportDto)) {
                    exList.add(recordImportDto);
                    flag = false;
                }

                //重复性校验
                String mapKey = DateUtil.dateToStr(attendanceDate, "yyyyMMdd") + userName;
                String mapValue = map.get(mapKey);
                if (StringUtils.isNotEmpty(mapValue) && flag) {
                    recordImportDto.setId(Long.valueOf(mapValue));
                    recordImportDto.setModifiedBy(user.getUserid());
                    recordImportDto.setModifiedTime(new Date());
                    recordImportDto.setAmAbnormalReason(
                            convertAbnormalReason(recordImportDto.getAmAbnormalReason(),map));
                    recordImportDto.setPmAbnormalReason(
                            convertAbnormalReason(recordImportDto.getPmAbnormalReason(),map));
                    //这里存储只会有值得替换
                    updatePpmKqRecordImport(recordImportDto);
                    calculateWorkingTime(recordImportDto);
                    flag = false;
                }

                //存入数据
                if(flag){
                    recordImportDto.setId(getNextSequence(PpmKqRecordImport.class));
                    recordImportDto.setImportState("1");
                    recordImportDto.setCreatedBy(user.getUserid());
                    recordImportDto.setCreatedTime(now);
                    recordImportDto.setAmAbnormalReason(
                            convertAbnormalReason(recordImportDto.getAmAbnormalReason(),map));
                    recordImportDto.setPmAbnormalReason(
                            convertAbnormalReason(recordImportDto.getPmAbnormalReason(),map));
                    addPpmKqRecordImport(recordImportDto);
                    map.put(mapKey, String.valueOf(recordImportDto.getId()));

                    //存储考勤记录表
                    try {
                        calculateWorkingTime(recordImportDto);
                    }catch (Exception e){
                        e.printStackTrace();
                        exList.add(recordImportDto);
                    }

                }
            }
        }
        return exList;
    }

    /**
     * 校验导入数据是否符合规则
     * @param now 当前时间
     * @param recordImportDto 导入对象
     * @return boolean
     */
    private boolean verifyImportData(Date now,PpmKqRecordImportDto recordImportDto){
        if (StringUtils.isEmpty(recordImportDto.getJobId())){
            return false;
        }

        if(StringUtils.isEmpty(recordImportDto.getUserName())){
            return false;
        }

        if(recordImportDto.getAttendanceDate() == null){
            return false;
        }

        //判断姓名工号外包类型是否能够查到
        if (queryOutJobName(recordImportDto) == 0){
            return false;
        }

        EosDictEntry eosDictEntry = eosDictEntryService.getTypeidForDetail("KQ_RECORD_IMPORT_VERIFY_SWITCH", "1");
        if (eosDictEntry != null && "1".equals(eosDictEntry.getDictid())) {
            //判断是否为当月数据
            String format = "yyyyMM";
            String nowYearsMonth = DateUtil.dateToStr(now, format);
            String theYearsMonth = DateUtil.dateToStr(recordImportDto.getAttendanceDate(), format);
            return nowYearsMonth.equals(theYearsMonth);
        }
        return true;
    }

    /**
     * 转换 异常原因名称转码值
     * @param abnormalReason 异常原因
     * @return String
     */
    private String convertAbnormalReason(String abnormalReason,Map<String,String> map){
        StringBuilder value = new StringBuilder();
        if(StringUtils.isNotEmpty(abnormalReason) && abnormalReason.contains("/")){
            String [] reasonArray = abnormalReason.split("/");
            for(String key : reasonArray){
                value.append(map.get(key)).append("/");
            }
            return value.substring(0,value.length()-1);
        }
        return map.get(abnormalReason);
    }

    @Override
    public List<PpmKqRecordExportDto> exportExcel(List<PpmKqRecordImportDto> exList) {
        List<PpmKqRecordExportDto> exportDtoList = new ArrayList<>();
        exList.forEach(recordImportDto -> {
            PpmKqRecordExportDto ppmKqRecordExportDto = new PpmKqRecordExportDto();
            ppmKqRecordExportDto.setJobId(recordImportDto.getJobId());
            ppmKqRecordExportDto.setAttendanceDate(recordImportDto.getAttendanceDate());
            if(StringUtils.isNotEmpty(recordImportDto.getOutsourceType())){
                String outSourceType = recordImportDto.getOutsourceType();
                if ("1".equals(recordImportDto.getOutsourceType())){
                    outSourceType = "人力外包";
                }
                if ("0".equals(recordImportDto.getOutsourceType())){
                    outSourceType = "项目外包";
                }
                ppmKqRecordExportDto.setOutsourceType(outSourceType);
            }else {
                ppmKqRecordExportDto.setOutsourceType(recordImportDto.getOutsourceType());
            }

            ppmKqRecordExportDto.setUserName(recordImportDto.getUserName());
            ppmKqRecordExportDto.setAmAbnormalReason(recordImportDto.getAmAbnormalReason());
            ppmKqRecordExportDto.setPmAbnormalReason(recordImportDto.getPmAbnormalReason());
            convertExportDateFormat(recordImportDto, ppmKqRecordExportDto);
            exportDtoList.add(ppmKqRecordExportDto);
        });
        return exportDtoList;
    }

    /**
     * 转换导入 -> 导出 时间格式
     * @param recordImportDto 导入对象
     * @param ppmKqRecordExportDto 导出对象
     */
    private void convertExportDateFormat(
            PpmKqRecordImportDto recordImportDto,PpmKqRecordExportDto ppmKqRecordExportDto){
        String timeFormat = "HH:mm:ss";
        String morningWork = recordImportDto.getMorningWorkingTime();
        if(StringUtils.isNotEmpty(morningWork)){
            ppmKqRecordExportDto.setMorningWorkingTime(DateUtil.dataFormat(morningWork,timeFormat));
        }else{
            ppmKqRecordExportDto.setMorningWorkingTime(null);
        }

        String morningClosing = recordImportDto.getMorningClosingTime();
        if(StringUtils.isNotEmpty(morningClosing)){
            ppmKqRecordExportDto.setMorningClosingTime(DateUtil.dataFormat(morningClosing,timeFormat));
        }else{
            ppmKqRecordExportDto.setMorningClosingTime(null);
        }

        String afternoonWork = recordImportDto.getAfternoonWorkingTime();
        if(StringUtils.isNotEmpty(afternoonWork)){
            ppmKqRecordExportDto.setAfternoonWorkingTime(DateUtil.dataFormat(afternoonWork,timeFormat));
        }else{
            ppmKqRecordExportDto.setAfternoonWorkingTime(null);
        }

        String afternoonClosing = recordImportDto.getAfternoonClosingTime();
        if(StringUtils.isNotEmpty(afternoonClosing)){
            ppmKqRecordExportDto.setAfternoonClosingTime(DateUtil.dataFormat(afternoonClosing,timeFormat));
        }else{
            ppmKqRecordExportDto.setAfternoonClosingTime(null);
        }

        String overtimeTime = recordImportDto.getOvertimeTime();
        if(StringUtils.isNotEmpty(overtimeTime)){
            ppmKqRecordExportDto.setOvertimeTime(DateUtil.dataFormat(overtimeTime,timeFormat));
        }else{
            ppmKqRecordExportDto.setOvertimeTime(null);
        }

    }

    @Override
    public int queryOutJobName(PpmKqRecordImportDto ppmKqRecordImportDto) {
        Map<String,Object> map = new HashMap<>();
        map.put("jobId",ppmKqRecordImportDto.getJobId());
        map.put("userName",ppmKqRecordImportDto.getUserName());
//        map.put("outsourceType",ppmKqRecordImportDto.getOutsourceType());
        List<PpmOutsourcingBasic> list = ppmOutsourcingBasicService.queryOutsList(map);
        if(list != null && !list.isEmpty()){
            return list.size();
        }
        return 0;
    }

    /**
     * 计算上班时间
     * @param ppmKqRecordImportDto ppmKqRecordImportDto
     */
    public void calculateWorkingTime(PpmKqRecordImportDto ppmKqRecordImportDto){
        String timeFormat = "yyyy/MM/dd HH:mm:ss";
        String dateFormat = "yyyy/MM/dd";

        Date attendanceDate = ppmKqRecordImportDto.getAttendanceDate();
        //判断是否是节假日 true 节假日 false 工作日
        boolean isHoliday = ppmKqInfoService.judgingDateIsWorkingDay(attendanceDate);
        //整合上下班时间
        Map<String,String> map = handleWorkOrEndTime(ppmKqRecordImportDto,isHoliday);
        String firstTime = map.get("firstTime");
        String endTime = map.get("endTime");
        //获取加班开始时间
        String overTimeStart = ppmKqOvertimeConfigService.getOvertimeStartTime();
        //计算加班时长/minu
        Map<String,Object> calMap;
        if(isHoliday){
            calMap = calculateHolidayOvertimeHours(firstTime,endTime);
        }else {
            calMap = calculateWorkOvertimeHours(endTime,overTimeStart);
        }

        boolean overtimeTimeFlag = (boolean) calMap.get(OVERTIME_TIME_FLAG);
        Long overtimeMinuts = Long.valueOf(calMap.get(OVERTIME_MINUTS).toString());

        //将上班时间和下班时间转成date类型
        String attendanceStr = DateUtil.dateToStr(attendanceDate,dateFormat);

        Date workingTime = null;
        if(StringUtils.isNotEmpty(firstTime)){
            workingTime = DateUtil.dataFormat(attendanceStr + " " + firstTime,timeFormat);
        }

        //下班时间
        Date endingTime = getAttendanceDateValue(endTime,attendanceDate,overtimeTimeFlag,timeFormat);
        String whetherDaysOff = "N";
        if (endingTime != null){
            LocalTime endingLocalTime = LocalTime.parse(DateUtil.dateToStr(endingTime, "HH:mm:ss"));
            if (endingLocalTime.isBefore(LocalTime.parse("06:00:00")) ||
                    endingLocalTime.isAfter(LocalTime.parse("23:29:59"))){
                whetherDaysOff = "Y";
            }
        }

        // 存储数据
        String jobId = ppmKqRecordImportDto.getJobId();
        String attendanceYearMonth = DateUtil.dateToStr(attendanceDate,"yyyyMM");
        PpmOutsourcingBasicInfo info = ppmOutsourcingBasicService.getBaseInfo(jobId);

        PpmKqInfoDto ppmKqInfoDto =  new PpmKqInfoDto();
        ppmKqInfoDto.setJobId(jobId);
        ppmKqInfoDto.setUserName(ppmKqRecordImportDto.getUserName());
        ppmKqInfoDto.setAttendanceMonth(attendanceYearMonth);
        PpmKqInfoDto ppmKqInfo = ppmKqInfoService.queryDetailObject(ppmKqInfoDto,"queryDetailObject");
        if(ppmKqInfo == null ){
            ppmKqInfo = new PpmKqInfoDto();
            ppmKqInfo.setId(SnowflakeIdWorker.getSnowId());
            ppmKqInfo.setJobId(jobId);
            ppmKqInfo.setUserName(info.getUserName());
            ppmKqInfo.setAttendanceMonth(attendanceYearMonth);
            ppmKqInfo.setWhetherUpdate("0");
            ppmKqInfo.setExemptionNumber(2L);
            ppmKqInfoService.addPpmKqInfo(ppmKqInfo);
        }else {
            ppmKqInfo.setWhetherUpdate("0");
            ppmKqInfoService.updatePpmKqInfo(ppmKqInfo);
        }

        PpmKqRecordDto ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordImportDto,PpmKqRecordDto.class);
        ppmKqRecord = ppmKqRecordService.queryDetailObject(ppmKqRecord,ppmKqInfo.getId());
        if(ppmKqRecord == null){
            ppmKqRecord = new PpmKqRecordDto();
            ppmKqRecord.setId(SnowflakeIdWorker.getSnowId());
            ppmKqRecord.setInfoId(ppmKqInfo.getId());
            ppmKqRecord.setJobId(jobId);
            ppmKqRecord.setUserName(info.getUserName());
            ppmKqRecord.setAttendanceDate(attendanceDate);
            ppmKqRecord.setWorkingTime(workingTime);
            ppmKqRecord.setClosingTime(endingTime);
            ppmKqRecord.setOvertimeHours(overtimeMinuts);
            ppmKqRecord.setOutsourceType(ppmKqRecordImportDto.getOutsourceType());
            ppmKqRecord.setWhetherOvernight(overtimeTimeFlag?"1":"0");
            ppmKqRecord.setOvertimeStartTime(overTimeStart);
            ppmKqRecord.setPrices(info.getPrice());
            ppmKqRecord.setWhetherDaysOff(whetherDaysOff);
            ppmKqRecord.setExemptionState(0L);
            calculateWorkingTimeUpd(ppmKqRecord,isHoliday);
            ppmKqRecord.setIsHoliday(isHoliday?"1":"0");
            ppmKqRecord.setDataEntryType("import");
            ppmKqRecord.setCreatedBy("autoImport");
            ppmKqRecord.setCreatedTime(new Date());
            ppmKqRecordService.addPpmKqRecord(ppmKqRecord);
        }else{
            ppmKqRecord.setWorkingTime(workingTime);
            ppmKqRecord.setClosingTime(endingTime);
            ppmKqRecord.setOvertimeHours(overtimeMinuts);
            ppmKqRecord.setWhetherOvernight(overtimeTimeFlag?"1":"0");
            ppmKqRecord.setOvertimeStartTime(overTimeStart);
            ppmKqRecord.setOutsourceType(ppmKqRecordImportDto.getOutsourceType());
            ppmKqRecord.setIsHoliday(isHoliday?"1":"0");
            ppmKqRecord.setWhetherDaysOff(whetherDaysOff);
            calculateWorkingTimeUpd(ppmKqRecord,isHoliday);
            ppmKqRecord.setModifiedBy("autoImport");
            ppmKqRecord.setModifiedTime(new Date());
            ppmKqRecord.setExemptionState(0L);
            ppmKqRecordService.updateImportPpmKqRecord(ppmKqRecord);
        }
    }

    /**
     * 获取考勤下班时间
     * @param endTime str 下班时间
     * @param attendanceDate 考勤日期
     * @param overtimeTimeFlag 是否
     * @param timeFormat 时间format
     * @return Date
     */
    private Date getAttendanceDateValue(String endTime, Date attendanceDate,boolean overtimeTimeFlag,String timeFormat){
        String dateFormat = "yyyy/MM/dd";
        Date endingTime = null;
        if(StringUtils.isNotEmpty(endTime)){
            if(overtimeTimeFlag){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(attendanceDate);
                calendar.add(Calendar.DATE,1);
                Date overTimeDate = calendar.getTime();
                String overTimeDateStr = DateUtil.dateToStr(overTimeDate,dateFormat);
                endingTime = DateUtil.dataFormat(overTimeDateStr + " " + endTime,timeFormat);
            }else {
                endingTime = DateUtil.dataFormat(
                        DateUtil.dateToStr(attendanceDate,dateFormat) + " " + endTime,timeFormat);
            }
        }
        return endingTime;
    }

    /**
     * 计算上班时间 辅助方法
     * @param ppmKqRecord ppmKqRecord
     */
    private void calculateWorkingTimeUpd(PpmKqRecordDto ppmKqRecord,boolean isHoliday){
        //若异常原因不为空，是否异常填是/1 是否处理填不处理/3
        if(isHoliday){
            ppmKqRecord.setAmWhetherHandle("3");
            ppmKqRecord.setAmWhetherAbnormal("0");
            ppmKqRecord.setPmWhetherHandle("3");
            ppmKqRecord.setPmWhetherAbnormal("0");
        }else {
            ppmKqRecord.setAmWhetherHandle("0");
            ppmKqRecord.setPmWhetherHandle("0");
        }

        ppmKqRecord.setAmRemark("");
        ppmKqRecord.setPmRemark("");
        ppmKqRecord.setDataEntryType("import");
    }

    /**
     * 整合导入模板记录为一条记录
     * @param ppmKqRecordImportDto ppmKqRecordImportDto
     * @param isHoliday isHoliday
     * @return Map
     */
    private Map<String,String> handleWorkOrEndTime(PpmKqRecordImportDto ppmKqRecordImportDto,boolean isHoliday){

        String firstTime = "";
        String endTime = "";
        //设定标志，是否已有上班时间
        boolean flag = false;
        //2、判断是否有打卡记录，即上班时间到加班时间是否为空
        //当上午上班时间不为空时，以上午上班时间作为上班时间
        String morningWorkingTime = ppmKqRecordImportDto.getMorningWorkingTime();
        if(StringUtils.isNotEmpty(morningWorkingTime)){
            firstTime = morningWorkingTime;
            flag = true;
        }

        //当上午上班时间为空，上午下班时间不为空时，以上班下班时间作为上班时间
        String morningClosingTime = ppmKqRecordImportDto.getMorningClosingTime();
        if(StringUtils.isNotEmpty(morningClosingTime)){
            if(flag){
                endTime = morningClosingTime;
            }else {
                firstTime = morningClosingTime;
                flag = true;
            }
        }

        //当上午上班时间、上午下班时间均为空时，以下午上班时间作为上班时间
        String afternoonWorkingTime = ppmKqRecordImportDto.getAfternoonWorkingTime();
        if(StringUtils.isNotEmpty(afternoonWorkingTime)){
            if(flag){
                endTime = afternoonWorkingTime;
            }else {
                firstTime = afternoonWorkingTime;
            }
        }

        //无论上午上班时间、上午下班时间、下午上班时间是否为空，将下午下班时间作为下班时间
        String afternoonClosingTime = ppmKqRecordImportDto.getAfternoonClosingTime();
        if(StringUtils.isNotEmpty(afternoonClosingTime)){
            if(!isHoliday){
                endTime = afternoonClosingTime;
            }else{
                if(flag){
                    endTime = afternoonClosingTime;
                }else{
                    firstTime = afternoonClosingTime;
                }
            }
        }

        //如果加班时间不为空时，将加班时间作为下班时间
        if(StringUtils.isNotEmpty(ppmKqRecordImportDto.getOvertimeTime())){
            endTime = ppmKqRecordImportDto.getOvertimeTime();
        }

        Map<String,String> map = new HashMap<>();
        map.put("firstTime",firstTime);
        map.put("endTime",endTime);
        return map;
    }

    /**
     * 计算加班时长 - 工作日
     * @param endTime endTime
     * @param overTimeStart overTimeStart
     * @return Map
     */
    public static Map<String,Object> calculateWorkOvertimeHours(String endTime,String overTimeStart){
        String timeFormat = "HH:mm:ss";
        Map<String,Object> map = new HashMap<>();
        log.info("数据处理开始");
        if(StringUtils.isEmpty(endTime)){
            log.info("======下班时间为空=====");
            map.put(OVERTIME_MINUTS, 0);
            map.put(OVERTIME_TIME_FLAG, false);
            map.put(WORKTIME_MINUTS, 0);
            return map;
        }

        //是否跨夜标志
        boolean overtimeTimeFlag = false;
        long overtimeMinuts = 0;
        long worktimeMinuts = 0;
        LocalTime todayStartTime = LocalTime.parse("00:00:00");
        //加班开始时间
        LocalTime overTimeTime = LocalTime.parse(overTimeStart);
        String endTim = endTime.replace("+","");
        Date endTi = DateUtil.dataFormat(endTim,timeFormat);
        LocalTime endedTime = LocalTime.parse(DateUtil.dateToStr(endTi,timeFormat));
        LocalTime todayEndTime = LocalTime.parse("23:59:59");
        LocalTime endDayTime = LocalTime.parse("06:00:00");
        LocalTime startedTime = LocalTime.parse("08:30:00");

        if(endedTime.isAfter(startedTime)){
            //工作时间=下班时间-8.30上班时间
            log.info("========》工作时间：{}",worktimeMinuts);
            worktimeMinuts += Duration.between(startedTime,endedTime).toMinutes();
        }

        if((endTime.contains("+") && endedTime.isBefore(endDayTime)) ||
                (endedTime.isAfter(todayStartTime) && endedTime.isBefore(endDayTime)) ||
                (endedTime == todayStartTime)){
            //表示跨夜加班
            overtimeTimeFlag = true;
            //跨夜加班需要加上加班开始时间至当天23:59:59的时长。
            overtimeMinuts += Duration.between(overTimeTime,todayEndTime).toMinutes() + 1 +
                    Duration.between(todayStartTime,endedTime).toMinutes();
            worktimeMinuts += Duration.between(startedTime,todayEndTime).toMinutes() + 1 +
                    Duration.between(todayStartTime,endedTime).toMinutes();
        }else if (endedTime.isAfter(overTimeTime)){
            //下班时间和加班开始时间的分钟差即为加班时长
            overtimeMinuts += Duration.between(overTimeTime,endedTime).toMinutes();
        }
        log.info("========》工作时间：{}",worktimeMinuts);

        map.put(OVERTIME_MINUTS,overtimeMinuts);
        map.put(WORKTIME_MINUTS,worktimeMinuts);
        map.put(OVERTIME_TIME_FLAG,overtimeTimeFlag);
        return map;
    }

    /**
     * 计算加班时长 - 节假日
     * @param startTime startTime
     * @param endTime endTime
     * @return Map
     */
    public static Map<String,Object> calculateHolidayOvertimeHours(String startTime,String endTime){
        Map<String,Object> map = new HashMap<>();

        if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            log.info("======上班或者下班时间为空=====");
            map.put(OVERTIME_MINUTS, 0);
            map.put(OVERTIME_TIME_FLAG, false);
            map.put(WORKTIME_MINUTS, 0);
            return map;
        }

        //是否跨夜标志
        boolean overtimeTimeFlag = false;
        long overtimeMinuts = 0;
        LocalTime todayStartTime = LocalTime.parse("00:00:00");
        LocalTime overTimeTime = LocalTime.parse(startTime);
        LocalTime endedTime = LocalTime.parse(endTime.replace("+",""));
        LocalTime todayEndTime = LocalTime.parse("23:59:59");
        LocalTime endDayTime = LocalTime.parse("06:00:00");

        //跨夜
        if((endTime.contains("+") && endedTime.isBefore(endDayTime)) ||
                (endedTime.isAfter(todayStartTime) && endedTime.isBefore(endDayTime)) ||
                (endedTime == todayStartTime)){
            overtimeTimeFlag = true;
            overtimeMinuts += Duration.between(overTimeTime,todayEndTime).toMinutes() + 1 +
                    Duration.between(todayStartTime,endedTime).toMinutes();
        }else{
            //没跨夜
            overtimeMinuts += Duration.between(overTimeTime,endedTime).toMinutes();
        }
        log.info("========》工作时间：{}",overtimeMinuts);
        map.put(OVERTIME_MINUTS,overtimeMinuts);
        map.put(OVERTIME_TIME_FLAG,overtimeTimeFlag);
        map.put(WORKTIME_MINUTS,overtimeMinuts);
        return map;
    }


}
