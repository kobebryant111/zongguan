package com.work.ppm.application.services.integratedmanager.impl;

import com.alibaba.fastjson.JSONArray;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.EosDictEntryDto;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendar;
import com.work.ppm.application.model.integratedmanager.PpmKqRecord;
import com.work.ppm.application.model.integratedmanager.ext.PpmKqRecordView;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.quartz.interfaces.IPpmKqRecordCalculateJobService;
import com.work.ppm.application.services.integratedmanager.IPpmKqAnnualVacationService;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarService;
import com.work.ppm.application.services.integratedmanager.IPpmKqInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqOvertimeConfigService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordImportService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.workflowmanager.IAttendanceHandleService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 考勤信息表 接口实现类
 *
 */
@Slf4j
@Service("ppmKqRecordService")
public class PpmKqRecordService extends BaseDaoSupper implements IPpmKqRecordService, IPpmKqRecordCalculateJobService {
    /**
     * 实体类路径
     */
    private static final String PPMKQRECORD = "com.work.ppm.application.model.integratedmanager.PpmKqRecord";

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String AM_CODE = "AM";
    private static final String PM_CODE = "PM";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 导入数据服务接口
     */
    @Autowired
    private IPpmKqRecordImportService ppmKqRecordImportService;

    /**
     * 日历服务接口
     */
    @Autowired
    private IPpmKqCalendarService ppmKqCalendarService;

    /**
     * 导入数据服务接口
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    /**
     * 考勤信息服务接口
     */
    @Autowired
    private IPpmKqInfoService ppmKqInfoService;

    /**
     * 加班开始时间配置 Service
     */
    @Autowired
    private IPpmKqOvertimeConfigService ppmKqOvertimeConfigService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Autowired
    private IPpmKqAnnualVacationService annualVacationService;

    @Autowired
    private IAttendanceHandleService attendanceHandleService;


    @Override
    public void queryPager(PpmKqRecordDto ppmKqRecordDto, Pager pager,CommOperatorDoLoginDto user) {
//        if(null != user){
//            if(!"sysadmin".equals(user.getUserid())){ //这里添加就好了
//                ppmKqRecordDto.setPmId(user.getUserid());
//            }
//        }
        if(UserSessionUtil.queryRange()==1){
            ppmKqRecordDto.setPmId(user.getUserid());
        }
        if(null != ppmKqRecordDto.getDeptId()){
            ppmKqRecordDto.setDeptId(ppmKqRecordDto.getDeptId().replace("dep",""));
        }

        Map<String, Object> map = JsonUtil.objectTOMap(ppmKqRecordDto, PpmKqRecordDto.class);
        myBatisDao.selectPager(PPMKQRECORD, map, pager, "queryBaseInfoList");
        List<PpmKqRecordDto> ppmKqRecordDtos = JsonUtil.jsonToList(pager.getList(),PpmKqRecordDto.class);
        EosDictEntry entry = new EosDictEntry();
        entry.setDicttypeid("KQ_RECORD_REASON_DICT");
        entry.setStatus(1L);
        List<EosDictEntryDto> eosDictEntryDtos = eosDictEntryService.getList(entry);

        ppmKqRecordDtos.forEach(ppmKqRecordDto1 -> {
            ppmKqRecordDto1.setWhetherOvernight(ppmKqRecordDto1.getWhetherOvernight());
            if(AM_CODE.equals(ppmKqRecordDto1.getDataType())){
                ppmKqRecordDto1.setAmPunchTime(DateUtil.dateToStr(ppmKqRecordDto1.getPunchTime(),TIME_FORMAT));
                ppmKqRecordDto1.setPmPunchTime("/");
            }else if(PM_CODE.equals(ppmKqRecordDto1.getDataType())){
                ppmKqRecordDto1.setAmPunchTime("/");
                ppmKqRecordDto1.setPmPunchTime(DateUtil.dateToStr(ppmKqRecordDto1.getPunchTime(),TIME_FORMAT));
            }

            //获取有效年假天数
            double validAnnualVacation =
                    ppmKqRecordDto1.getAnnualVacationDays() + ppmKqRecordDto1.getAnnualVacationSurplus();

            String abnormalReason = ppmKqRecordDto1.getAutoAbnormalReason();
            PpmKqRecordDto ppmKqRecordDto2 = new PpmKqRecordDto();
            ppmKqRecordDto2.setAttendanceDate(DateUtil.addOrSubDate(ppmKqRecordDto1.getAttendanceDate(),-1));
            ppmKqRecordDto2.setIsHoliday("0");
            ppmKqRecordDto2.setWhetherDaysOff("Y");
            ppmKqRecordDto2.setJobId(ppmKqRecordDto1.getJobId());
            PpmKqRecordDto ppmKqRecordDto3 = queryDetail(ppmKqRecordDto2);
            boolean flag = ppmKqRecordDto3 != null && "AM".equals(ppmKqRecordDto1.getDataType());

            List<EosDictEntryDto> eosDictEntryDtosList = new ArrayList<>();
            for (EosDictEntryDto item : eosDictEntryDtos){
                EosDictEntryDto itemVal = new EosDictEntryDto();
                itemVal.setDicttypeid(item.getDicttypeid());
                itemVal.setDictid(item.getDictid());
                itemVal.setDictname(item.getDictname());
                itemVal.setSortno(item.getSortno());
                itemVal.setStatus(item.getStatus());

                boolean disableFlag = true;
                if ("12".equals(abnormalReason) && ("12".equals(item.getDictid()) ||
                        "15".equals(item.getDictid()) ||
                        ("13".equals(item.getDictid()) && "AM".equals(ppmKqRecordDto1.getDataType())))){
                    itemVal.setDisabled(false);
                    eosDictEntryDtosList.add(itemVal);
                    disableFlag = false;
                }
                if ("13".equals(abnormalReason) && ("13".equals(item.getDictid()) || "15".equals(item.getDictid()))){
                    itemVal.setDisabled(false);
                    eosDictEntryDtosList.add(itemVal);
                    disableFlag = false;
                }
                if ("14".equals(abnormalReason) && ("14".equals(item.getDictid()) ||
                        "15".equals(item.getDictid()) ||
                        ("12".equals(item.getDictid())))){
                    itemVal.setDisabled(false);
                    eosDictEntryDtosList.add(itemVal);
                    disableFlag = false;
                }

                if (flag && "11".equals(item.getDictid())){
                    itemVal.setDisabled(false);
                    eosDictEntryDtosList.add(itemVal);
                    disableFlag = false;
                }

                if (validAnnualVacation > 0 && "3".equals(item.getDictid())){
                    itemVal.setDisabled(false);
                    eosDictEntryDtosList.add(itemVal);
                    disableFlag = false;
                }

                if (disableFlag){
                    itemVal.setDisabled(true);
                    eosDictEntryDtosList.add(itemVal);
                }
            }
            ppmKqRecordDto1.setAbnormalReasons(eosDictEntryDtosList);

//            if(ppmKqRecordDto1.getAbnormalReason() != null){
//                ppmKqRecordDto1.setAbnormalReasonList(ppmKqRecordDto1.getAbnormalReason().split("/"));
//            }else {
//                ppmKqRecordDto1.setAbnormalReasonList(new String[0]);
//            }
            ppmKqRecordDto1.setDeptName(BusinessUtil.getDeptNameValue(ppmKqRecordDto1.getDeptName(),1));
        });
        pager.setList(ppmKqRecordDtos);
    }

    @Override
    public int addPpmKqRecord(PpmKqRecordDto ppmKqRecordDto) {
//        String jobId = ppmKqRecordDto.getJobId();
//        if ("k".equals(jobId.substring(0, 1))) {
//            if ("1".equals(ppmKqRecordDto.getAmWhetherHandle())) {
//                ppmKqRecordDto.setAmWhetherHandle("3");
//            }
//            if ("1".equals(ppmKqRecordDto.getPmWhetherHandle())) {
//                ppmKqRecordDto.setPmWhetherHandle("3");
//            }
//        }
        PpmKqRecord ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecord.class);
        myBatisDao.executionPojo(PPMKQRECORD, ppmKqRecord, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmKqRecordDto queryDetail(PpmKqRecordDto ppmKqRecordDto) {
        PpmKqRecord ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto,PpmKqRecord.class);
        PpmKqRecord ppmKqRecord1 = myBatisDao.selectClass(PPMKQRECORD,ppmKqRecord,"queryDetail");
        return JsonUtil.jsonToClass(ppmKqRecord1, PpmKqRecordDto.class);
    }

    @Override
    public PpmKqRecordDto queryDetailObject(PpmKqRecordDto ppmKqRecordDto,Long infoId) {
        Map<String,Object> recordMap = new HashMap<>();
        recordMap.put("jobId",ppmKqRecordDto.getJobId());
        recordMap.put("infoId",infoId);
        recordMap.put("userName",ppmKqRecordDto.getUserName());
        recordMap.put("attendanceDate",DateUtil.dateToStr(ppmKqRecordDto.getAttendanceDate(),DATE_FORMAT));
        PpmKqRecord ppmKqRecord = myBatisDao.selectClass(PPMKQRECORD,recordMap,"queryDetailObject");
        return JsonUtil.jsonToClass(ppmKqRecord, PpmKqRecordDto.class);
    }

    @Override
    public int updatePpmKqRecordAndInfo(PpmKqRecordDto ppmKqRecordDto) {
        if(updatePpmKqRecord(ppmKqRecordDto) != 0){
            PpmKqInfoDto ppmKqInfoDto = new PpmKqInfoDto();
            ppmKqInfoDto.setId(ppmKqRecordDto.getInfoId());
            ppmKqInfoDto.setWhetherUpdate("0");
            return ppmKqInfoService.updatePpmKqInfo(ppmKqInfoDto);
        }
        return 0;
    }

    @Override
    public int updatePpmKqRecordAndInfoAdd(PpmKqRecordDto ppmKqRecordDto) {
        if(updatePpmKqRecord(ppmKqRecordDto) != 0){
            PpmKqInfoDto ppmKqInfoDto = new PpmKqInfoDto();
            ppmKqInfoDto.setId(ppmKqRecordDto.getInfoId());
            ppmKqInfoDto.setWhetherUpdate("0");
            if ((ppmKqRecordDto.getAmAbnormalReason() != null && "12".equals(ppmKqRecordDto.getAmAbnormalReason()))
            || (ppmKqRecordDto.getPmAbnormalReason() != null && "12".equals(ppmKqRecordDto.getPmAbnormalReason()))) {
                ppmKqInfoDto.setActualAttendanceDays(0.5);
            }
            if ((ppmKqRecordDto.getAmAbnormalReason() != null && "17".equals(ppmKqRecordDto.getAmAbnormalReason()))
                    || (ppmKqRecordDto.getPmAbnormalReason() != null && "17".equals(ppmKqRecordDto.getPmAbnormalReason()))) {
                ppmKqInfoDto.setActualAttendanceDays(0.5);
            }
            return ppmKqInfoService.updatePpmKqInfoAdd(ppmKqInfoDto);
        }
        return 0;
    }

    @Override
    public int updatePpmKqRecord(PpmKqRecordDto ppmKqRecordDto) {
        PpmKqRecord ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecord.class);
        return myBatisDao.executionPojo(PPMKQRECORD, ppmKqRecord, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int updatePpmKqRecordCus(PpmKqRecordDto ppmKqRecordDto) {
        // PpmKqRecord ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecord.class);
        return myBatisDao.executionPojo(PPMKQRECORD, ppmKqRecordDto, IMyBatisDao.SQL.UPDATE, "updatePojoCus");
    }

    @Override
    public int updateImportPpmKqRecord(PpmKqRecordDto ppmKqRecordDto) {
        PpmKqRecord ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecord.class);
        return myBatisDao.executionPojo(PPMKQRECORD, ppmKqRecord, IMyBatisDao.SQL.UPDATE, "updatePojoImport");
    }

    @Override
    public int updatePpmKqRecordOvertime(PpmKqRecordDto ppmKqRecordDto) {
        PpmKqRecordView ppmKqRecordView = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecordView.class);
        return myBatisDao.executionPojo(PPMKQRECORD, ppmKqRecordView, IMyBatisDao.SQL.UPDATE, "updatePojoOvertime");
    }

    @Override
    public List<PpmKqRecordDto> queryDetailList(Long infoId) {
        PpmKqRecordDto ppmKqRecord = new PpmKqRecordDto();
        ppmKqRecord.setInfoId(infoId);
        return  queryWhereList(ppmKqRecord);
    }

    @Override
    public List<PpmKqRecordDto> queryWhereList(PpmKqRecordDto ppmKqRecordDto) {
        PpmKqRecord ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecord.class);
        List<PpmKqRecord> list = myBatisDao.selectListT(PPMKQRECORD, ppmKqRecord, "getList");
        return  JsonUtil.jsonToList(list, PpmKqRecordDto.class);
    }

    @Override
    public List<PpmKqRecordDto> queryBaseInfoList(PpmKqRecordDto ppmKqRecordDto) {
        PpmKqRecordView ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecordView.class);
        List<PpmKqRecordView> list = myBatisDao.selectListT(PPMKQRECORD,ppmKqRecord,"queryBaseInfoList");
        return JsonUtil.jsonToList(list, PpmKqRecordDto.class);
    }

    @Override
    public List<PpmKqRecordDto> queryDistinctHandle(PpmKqRecordDto ppmKqRecordDto) {
        PpmKqRecordView ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecordView.class);
        List<PpmKqRecordView> list = myBatisDao.selectListT(PPMKQRECORD,ppmKqRecord,"queryDistinctHandle");
        return JsonUtil.jsonToList(list, PpmKqRecordDto.class);
    }

    @Override
    public List<PpmKqRecordDto> queryAttendanceList(PpmKqRecordDto ppmKqRecordDto) {
        Map<String,Object> recordMap = new HashMap<>();
        recordMap.put("attendanceDateValue",DateUtil.dateToStr(ppmKqRecordDto.getAttendanceDate(),DATE_FORMAT));
        List<PpmKqRecord> list = myBatisDao.selectListT(PPMKQRECORD,recordMap,"getAttendanceList");
        return JsonUtil.jsonToList(list, PpmKqRecordDto.class);
    }

    @Override
    public void overtimeCalaulate(){
        PpmKqRecordDto kqRecordDto = new PpmKqRecordDto();
        kqRecordDto.setIsCalOvertime("0");
        List<PpmKqRecordDto> ppmKqRecordDtos = queryWhereList(kqRecordDto);
        if(ppmKqRecordDtos != null && !ppmKqRecordDtos.isEmpty()){
            String overTimeStart = ppmKqOvertimeConfigService.getOvertimeStartTime();
            LocalDate now = LocalDate.now();
            ppmKqRecordDtos.forEach(ppmKqRecordDto ->{
                LocalDate localDate =
                        ppmKqRecordDto.getAttendanceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(now.isEqual(localDate)){
                    return;
                }

                Map<String,Object> map = new HashMap<>();
                map.put("overtimeTimeFlag",false);
                map.put("overtimeMinuts",0);
                // 工作日
                if("0".equals(ppmKqRecordDto.getIsHoliday())){
                    if(ppmKqRecordDto.getClosingTime() != null){
                        String endTime = DateUtil.dateToStr(ppmKqRecordDto.getClosingTime(),TIME_FORMAT);
                        map = PpmKqRecordImportService.calculateWorkOvertimeHours(endTime,overTimeStart);
                    }
                }else{
                    //节假日
                    if(ppmKqRecordDto.getWorkingTime() != null && ppmKqRecordDto.getClosingTime() != null){
                        String firstTime = DateUtil.dateToStr(ppmKqRecordDto.getWorkingTime(),TIME_FORMAT);
                        String endTime = DateUtil.dateToStr(ppmKqRecordDto.getClosingTime(),TIME_FORMAT);
                        map = PpmKqRecordImportService.calculateHolidayOvertimeHours(firstTime,endTime);
                    }
                }

                Long id = ppmKqRecordDto.getId();
                Long infoId = ppmKqRecordDto.getInfoId();
                ppmKqRecordDto = new PpmKqRecordDto();
                ppmKqRecordDto.setId(id);
                ppmKqRecordDto.setInfoId(infoId);
                ppmKqRecordDto.setWhetherOvernight(((boolean)map.get("overtimeTimeFlag")?"1":"0"));
                ppmKqRecordDto.setOvertimeHours(Long.valueOf(map.get("overtimeMinuts").toString()));
                ppmKqRecordDto.setIsCalOvertime("1");
                updatePpmKqRecordAndInfo(ppmKqRecordDto);
            });
        }
    }

    @Override
    public void sendMessage() {
        PpmKqRecordDto kqRecordDto = new PpmKqRecordDto();
        kqRecordDto.setWhetherHandle("1");
        List<PpmKqRecordDto> infoList = queryDistinctHandle(kqRecordDto);
        JSONArray badges = new JSONArray();
        infoList.forEach(info -> badges.add(info.getPmId()));
        attendanceHandleService.sendMessage(badges);
    }

    @Override
    public int cipherRecordList(List<PpmKqRecordDto> ppmKqRecordDtos){
        LocalTime nowTime = LocalTime.now();
        LocalTime amTime = LocalTime.parse("08:31:00");
        LocalDate nowDate = LocalDate.now();
        boolean flag = nowTime.isBefore(amTime);

        List<PpmKqRecord> list = myBatisDao.selectListT(PPMKQRECORD, ppmKqRecordDtos, "cipherList");
        List<PpmKqRecordDto> kqRecordDtoList = JsonUtil.jsonToList(list, PpmKqRecordDto.class);
        kqRecordDtoList.forEach(kqRecordDto -> {
            //1、判断今天这天是不是工作日
            Date workingDate = kqRecordDto.getWorkingTime();
            Date closingDate = kqRecordDto.getClosingTime();
            String attendanceDate = DateUtil.dateToStr(kqRecordDto.getAttendanceDate(),DATE_FORMAT);
            if(flag && nowDate.isEqual(LocalDate.parse(attendanceDate))){
                return;
            }
            //工作日
            if ("0".equals(kqRecordDto.getIsHoliday())){
                recordEasyCalaulateAssist(workingDate,closingDate,AM_CODE,kqRecordDto);
            }else{
                setAbnormalValue(kqRecordDto,AM_CODE,"0","3","");
            }
            updatePpmKqRecordAndInfo(kqRecordDto);
        });
        return myBatisDao.selectClass(PPMKQRECORD, ppmKqRecordDtos, "countList");
    }

    @Override
    public void recordCalaulate() {
        LocalTime nowTime = LocalTime.now();
        LocalTime amTime = LocalTime.parse("08:31:00");
        LocalDate nowDate = LocalDate.now();
        boolean flag = nowTime.isBefore(amTime);

        //1、查询处理状态为0的数据
        PpmKqRecordDto ppmKqRecordDto = new PpmKqRecordDto();
        ppmKqRecordDto.setAmWhetherHandle("0");
        List<PpmKqRecordDto> kqRecordDtoList = queryWhereList(ppmKqRecordDto);
        kqRecordDtoList.forEach(kqRecordDto -> {
            //1、判断今天这天是不是工作日
            Date workingDate = kqRecordDto.getWorkingTime();
            Date closingDate = kqRecordDto.getClosingTime();
            String attendanceDate = DateUtil.dateToStr(kqRecordDto.getAttendanceDate(),DATE_FORMAT);
            if(flag && nowDate.isEqual(LocalDate.parse(attendanceDate))){
                return;
            }

            //工作日
            if ("0".equals(kqRecordDto.getIsHoliday())){
                recordEasyCalaulateAssist(workingDate,closingDate,AM_CODE,kqRecordDto);
            }else{
                setAbnormalValue(kqRecordDto,AM_CODE,"0","3","");
            }
            updatePpmKqRecordAndInfo(kqRecordDto);
        });

        PpmKqRecordDto pmPpmKqRecordDto = new PpmKqRecordDto();
        pmPpmKqRecordDto.setPmWhetherHandle("0");
        List<PpmKqRecordDto> pmKqRecordDtoList = queryWhereList(pmPpmKqRecordDto);
        pmKqRecordDtoList.forEach(kqRecordDto -> {
            //1、判断今天是不是工作日
            Date workingDate = kqRecordDto.getWorkingTime();
            Date closingDate = kqRecordDto.getClosingTime();
            String attendanceDate = DateUtil.dateToStr(kqRecordDto.getAttendanceDate(),DATE_FORMAT);
            if(nowDate.isEqual(LocalDate.parse(attendanceDate))){
                return;
            }
            //工作日
            if ("0".equals(kqRecordDto.getIsHoliday())){
                recordEasyCalaulateAssist(workingDate,closingDate,PM_CODE,kqRecordDto);
            }else{
                setAbnormalValue(kqRecordDto,PM_CODE,"0","3","");
            }
            updatePpmKqRecordAndInfo(kqRecordDto);
        });
        log.info("考勤异常数据计算完成");
    }

    @Override
    public void autoRecordHandleCalaulate() {
        //首先判断今天是不是第5个工作日
        List<PpmKqCalendarDto> ppmKqCalendarDtos = calIsFifthWoringTime(new Date(),1);
        if(ppmKqCalendarDtos == null || ppmKqCalendarDtos.size() <= 5){
            return;
        }
        Date fifthWorkingDate = ppmKqCalendarDtos.get(4).getCalendarDate();
        LocalDate fifthWorking =
//                LocalDate.now();
                LocalDate.parse(DateUtil.dateToStr(fifthWorkingDate,DATE_FORMAT));
        LocalDate now = LocalDate.now();
        if(!now.isEqual(fifthWorking)){
            return;
        }

        LocalDate lastNow = now.minusMonths(1);
        //如果是的话则去查询上月未处理的数据
        PpmKqRecordDto ppmKqRecordDto = new PpmKqRecordDto();
        ppmKqRecordDto.setWhetherHandle("1");
        ppmKqRecordDto.setDataType("AM");
        ppmKqRecordDto.setAttendanceDateValue(DateTimeFormatter.ofPattern("yyyy-MM").format(lastNow));
//        ppmKqRecordDto.setJobId("wb22227");
        List<PpmKqRecordDto> amList = queryBaseInfoList(ppmKqRecordDto);
        amList.forEach(ppmKqRecordDto1 -> {
            ppmKqRecordDto1.setAmWhetherHandle("4");
            ppmKqRecordDto1.setModifiedBy("autoFifthWorking");
            ppmKqRecordDto1.setModifiedTime(new Date());
            updatePpmKqRecordAndInfo(ppmKqRecordDto1);
        });

        //下午
        ppmKqRecordDto = new PpmKqRecordDto();
        ppmKqRecordDto.setWhetherHandle("1");
        ppmKqRecordDto.setDataType("PM");
        ppmKqRecordDto.setAttendanceDateValue(DateTimeFormatter.ofPattern("yyyy-MM").format(lastNow));
//        ppmKqRecordDto.setJobId("wb22227");
        List<PpmKqRecordDto> pmList = queryBaseInfoList(ppmKqRecordDto);
        pmList.forEach(ppmKqRecordDto1 -> {
            ppmKqRecordDto1.setPmWhetherHandle("4");
            ppmKqRecordDto1.setModifiedBy("autoFifthWorking");
            ppmKqRecordDto1.setModifiedTime(new Date());
            updatePpmKqRecordAndInfo(ppmKqRecordDto1);
        });

        //计算豁免次数
        String attendanceMonth = DateTimeFormatter.ofPattern("yyyyMM").format(lastNow);
        //查询上月的考勤数据
        PpmKqInfoDto ppmKqInfoDto = new PpmKqInfoDto();
        ppmKqInfoDto.setAttendanceMonth(attendanceMonth);
        List<PpmKqInfoDto> kqInfoDtoList = ppmKqInfoService.queryDetailList(ppmKqInfoDto);
        //获取异常原因字典
        List<EosDictEntry> dictList = eosDictEntryService.getTypeidForList("KQ_RECORD_REASON_DICT");
        Map<String,Object> dictMap = new HashMap<>();
        dictList.forEach(obj -> dictMap.put(obj.getDictid(),obj.getDictname()));
        //如果kqInfoDtoList不可空，则循环判断
        kqInfoDtoList.forEach(kqInfoDto -> {
            //初始化豁免次数=2
            long exemptionNumber = 2L;
            //查询特定人同一个月份的考勤详情数据
            List<PpmKqRecordDto> kqRecordDtoList = queryDetailList(kqInfoDto.getId());
            //过滤豁免状态为空的数据，得到已使用豁免的次数
            long exemptionStateNumber = kqRecordDtoList.stream().filter(x -> x.getExemptionState() != null).
                    mapToLong(PpmKqRecordDto::getExemptionState).sum();
            //若已使用的豁免状态>= 2，则返回
            if (exemptionStateNumber >= 2){
                return;
            }

            int i = 1;
            for (PpmKqRecordDto kqRecordDto : kqRecordDtoList){
                Long exemptionState = 0L;
                //如果豁免次数为0时则跳出循环
                if (exemptionNumber == 0){
                    PpmKqInfoDto ppmKqInfoDto1 = new PpmKqInfoDto();
                    ppmKqInfoDto1.setId(kqInfoDto.getId());
                    ppmKqInfoDto1.setExemptionNumber(exemptionNumber);
                    ppmKqInfoDto1.setWhetherUpdate("0");
                    ppmKqInfoService.updatePpmKqInfo(ppmKqInfoDto1);
                    log.info("第五工作日 跑批 豁免次数 =0 跳出循环.");
                    break;
                }

                boolean flag = false;
                PpmKqRecordDto kqRecordDto1 = new PpmKqRecordDto();
                if ("1".equals(kqRecordDto.getAmWhetherAbnormal()) &&
                        !Objects.equals("15",kqRecordDto.getAmAbnormalReason()) && exemptionNumber > 0) {
                    kqRecordDto1.setId(kqRecordDto.getId());
                    kqRecordDto1.setAmWhetherAbnormal("0");
                    String str = "(此异常已豁免)";

                    String amAbnormalReason = kqRecordDto.getAmAbnormalReason();
                    if(StringUtils.isNotEmpty(amAbnormalReason)){
                        if(amAbnormalReason.contains("/")){
                            str += BusinessUtil.replaceArrays(dictMap,amAbnormalReason,"/");
                        }else {
                            str += dictMap.get(amAbnormalReason);
                        }
                    }

                    if (StringUtils.isNotEmpty(kqRecordDto.getAmRemark())){
                        str += ":" + kqRecordDto.getAmRemark();
                    }

                    kqRecordDto1.setAmRemark(str);
                    kqRecordDto1.setAmAbnormalReason("");
                    exemptionNumber--;
                    exemptionState++;
                    flag = true;
                }

                if ("1".equals(kqRecordDto.getPmWhetherAbnormal()) &&
                        !Objects.equals("15",kqRecordDto.getPmAbnormalReason()) && exemptionNumber > 0) {
                    kqRecordDto1.setId(kqRecordDto.getId());
                    kqRecordDto1.setPmWhetherAbnormal("0");
                    String str = "(此异常已豁免)";

                    String pmAbnormalReason = kqRecordDto.getPmAbnormalReason();
                    if(StringUtils.isNotEmpty(pmAbnormalReason)){
                        if(pmAbnormalReason.contains("/")){
                            str += BusinessUtil.replaceArrays(dictMap,pmAbnormalReason,"/");
                        }else {
                            str += dictMap.get(pmAbnormalReason);
                        }
                    }

                    if (StringUtils.isNotEmpty(kqRecordDto.getPmRemark())){
                        str += ":" + kqRecordDto.getPmRemark();
                    }

                    kqRecordDto1.setPmRemark(str);
                    kqRecordDto1.setPmAbnormalReason("");
                    exemptionNumber--;
                    exemptionState++;
                    flag = true;
                }

                //修改考勤
                if (flag){
                    kqRecordDto1.setModifiedTime(new Date());
                    kqRecordDto1.setExemptionState(exemptionState);
                    updatePpmKqRecord(kqRecordDto1);
                }

                if (kqRecordDtoList.size() == i){
                    PpmKqInfoDto ppmKqInfoDto1 = new PpmKqInfoDto();
                    ppmKqInfoDto1.setId(kqInfoDto.getId());
                    ppmKqInfoDto1.setExemptionNumber(exemptionNumber);
                    ppmKqInfoDto1.setWhetherUpdate("0");
                    ppmKqInfoService.updatePpmKqInfo(ppmKqInfoDto1);
                    log.info("第五工作日 跑批 豁免 修改info状态.");
                }
                i++;
            }
        });



        log.info("自动处理结束");
    }


    /**
     * 简易处理是否异常
     * @param workingDate 上班时间
     * @param closingDate 下班时间
     * @param dataType 上下午类型
     * @param kqRecordDto 对象
     */
    public void recordEasyCalaulateAssist(Date workingDate,Date closingDate,String dataType,PpmKqRecordDto kqRecordDto){
        LocalTime workTime = LocalTime.parse("08:30:00");
        LocalTime closeTime = LocalTime.parse("17:30:00");
//        LocalTime amCloseTime = LocalTime.parse("11:30:00");
        LocalTime pmWorkingTime = LocalTime.parse("13:30:00");
        LocalTime endTime = LocalTime.parse("06:00:00");
        //如果是上午的数据
        if(AM_CODE.equals(dataType) ){
            //并且上班打卡时间不为空
            if(workingDate != null) {
                //转换时间
                LocalTime workingTime = LocalTime.parse(DateUtil.dateToStr(workingDate, TIME_FORMAT));
                //若上班时间在8:30:59之后,13:30:00之前，存储上午异常原因=> '迟到' => key:13
                if (workingTime.isAfter(workTime) && workingTime.isBefore(pmWorkingTime)){
                    setAbnormalValue(kqRecordDto, AM_CODE, "1", "1", "13");
                    return;
                }

                //若上班时间在8:31:00之前,无异常原因
                if (workingTime.isBefore(workTime.plusSeconds(1))) {
                    setAbnormalValue(kqRecordDto, AM_CODE, "0", "3", "");
                }
            }else{
                //若上班打卡时间为空，存储上午异常原因=> '忘打卡' => key:12
                setAbnormalValue(kqRecordDto,AM_CODE,"1","1","12");
            }
        }else if(PM_CODE.equals(dataType)){
            if(closingDate != null){
                LocalTime closingTime = LocalTime.parse(DateUtil.dateToStr(closingDate,TIME_FORMAT));
                //若下班时间在06:00:00之前、或者、下班时间在17:30:00之后，无异常原因  0:00>x>6:00  24:00>x>17:30
                if(closingTime.isBefore(endTime) || closingTime.isAfter(closeTime.minusSeconds(1))){
                    setAbnormalValue(kqRecordDto,PM_CODE,"0","3","");
                    return;
                }

                //若下班时间在5:59:59 之后、13:30:00之前，存储下午异常原因=> '早退' => key:12 13:00>x>=6:00
                if(closingTime.isAfter(endTime.minusSeconds(1)) && closingTime.isBefore(closeTime)){
                    setAbnormalValue(kqRecordDto,PM_CODE,"1","1","14");
                }
            }else {
                //若下班打卡时间为空，存储下午异常原因=> '忘打卡' => key:12
                setAbnormalValue(kqRecordDto,PM_CODE,"1","1","12");
            }
        }
    }

    /**
     * 存储考勤异常相关字段
     * @param ppmKqRecordDto 对象
     * @param dataType 上下午
     * @param whetherAbnormal 是否异常
     * @param whetherHandle 是否处理
     * @param abnormalReason 异常原因
     */
    public void setAbnormalValue(PpmKqRecordDto ppmKqRecordDto,String dataType,
                                 String whetherAbnormal,String whetherHandle,String abnormalReason){
        if(AM_CODE.equals(dataType)){
            ppmKqRecordDto.setAmWhetherAbnormal(whetherAbnormal);
            ppmKqRecordDto.setAmWhetherHandle(whetherHandle);
            ppmKqRecordDto.setAmAbnormalReason(abnormalReason);
            ppmKqRecordDto.setAutoAmAbnormalReason(abnormalReason);
        }else if(PM_CODE.equals(dataType)){
            ppmKqRecordDto.setPmWhetherAbnormal(whetherAbnormal);
            ppmKqRecordDto.setPmWhetherHandle(whetherHandle);
            ppmKqRecordDto.setPmAbnormalReason(abnormalReason);
            ppmKqRecordDto.setAutoPmAbnormalReason(abnormalReason);
        }
    }

    /**
     * 判断是否是第五个工作日
     * @param attendanceDate 查询日期
     * @return list
     */
    public List<PpmKqCalendarDto> calIsFifthWoringTime(Date attendanceDate,int val){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(attendanceDate);

        long year = calendar.get(Calendar.YEAR);
        long month = (long)calendar.get(Calendar.MONTH) + val;
        PpmKqCalendarDto kqCalendarDto = new PpmKqCalendarDto();
        kqCalendarDto.setYearOfCalendar(year);
        kqCalendarDto.setMonthOfYear(month);
        kqCalendarDto.setIsHoliday("N");
        String ppmKqCalendarClassPath = "com.work.ppm.application.model.integratedmanager.PpmKqCalendar";
        List<PpmKqCalendar> ppmKqCalendars =
                myBatisDao.selectListT(ppmKqCalendarClassPath,kqCalendarDto,"getCompareList");
        return JsonUtil.jsonToList(ppmKqCalendars,PpmKqCalendarDto.class);
    }

    @Override
    public void updateRecordHoliday(Date attendanceDate,String isHoliday) {
        PpmKqRecordDto ppmKqRecordDto = new PpmKqRecordDto();
        ppmKqRecordDto.setAttendanceDate(attendanceDate);
        List<PpmKqRecordDto> list = queryAttendanceList(ppmKqRecordDto);
        if(list != null && !list.isEmpty()){
            list.forEach(ppmKqRecordDto1 -> {
                if("N".equals(isHoliday)){
                    ppmKqRecordDto1.setIsHoliday("0");
                    ppmKqRecordDto1.setAmWhetherHandle("0");
                    ppmKqRecordDto1.setPmWhetherHandle("0");
                    ppmKqRecordDto1.setIsCalOvertime("0");
                }else if("Y".equals(isHoliday)){
                    ppmKqRecordDto1.setIsHoliday("1");
                    ppmKqRecordDto1.setAmWhetherHandle("3");
                    ppmKqRecordDto1.setAmWhetherAbnormal("0");
                    ppmKqRecordDto1.setPmWhetherHandle("3");
                    ppmKqRecordDto1.setPmWhetherAbnormal("0");
                    ppmKqRecordDto1.setIsCalOvertime("0");
                }
                updatePpmKqRecordAndInfo(ppmKqRecordDto1);
            });
        }
    }

    @Override
    public List<PpmKqRecordDto> getAttendanceRecord(PpmKqRecordDto ppmKqRecordDto) {
        PpmKqRecordView ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecordView.class);
        List<PpmKqRecordView> list = myBatisDao.selectListT(PPMKQRECORD,ppmKqRecord,"getAttendanceRecord");
        return JsonUtil.jsonToList(list, PpmKqRecordDto.class);
	}
	
    @Transactional("defaultTx")
    public void backRecordList(List<PpmKqRecordDto> ppmKqRecordDtos, CommOperatorDoLoginDto user) {
        ppmKqRecordDtos.forEach(ppmKqRecordDto -> {
            if(user != null){
                ppmKqRecordDto.setModifiedBy(user.getUserid());
            }
            if ("AM".equals(ppmKqRecordDto.getDataType())){
                ppmKqRecordDto.setAmWhetherHandle("1");
                ppmKqRecordDto.setAmWhetherAbnormal("1");
            } else if("PM".equals(ppmKqRecordDto.getDataType())){
                ppmKqRecordDto.setPmWhetherHandle("1");
                ppmKqRecordDto.setPmWhetherAbnormal("1");
            }
            PpmKqRecord ppmKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmKqRecord.class);
            int i =myBatisDao.executionPojo(PPMKQRECORD, ppmKqRecord, IMyBatisDao.SQL.UPDATE, "backPojoCus");
            if (i != 0) {
                PpmKqInfoDto ppmKqInfoDto = new PpmKqInfoDto();
                ppmKqInfoDto.setWhetherUpdate("0");
                ppmKqInfoDto.setId(ppmKqRecordDto.getInfoId());
                if ((ppmKqRecordDto.getAmAbnormalReason() != null && "12".equals(ppmKqRecordDto.getAmAbnormalReason()))
                        || (ppmKqRecordDto.getPmAbnormalReason() != null && "12".equals(ppmKqRecordDto.getPmAbnormalReason()))) {
                    ppmKqInfoDto.setActualAttendanceDays(-0.5);
                }
                ppmKqInfoService.updatePpmKqInfoAdd(ppmKqInfoDto);
            }
        });
    }

}
