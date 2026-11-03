package com.work.ppm.application.services.bankmanager.impl;

import com.work.ppm.application.dto.bankmamager.PpmEmpKqInfoDto;
import com.work.ppm.application.dto.bankmamager.PpmEmpKqRecordDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqFaceDataDto;
import com.work.ppm.application.model.integratedmanager.PpmKqFaceData;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.bankmanager.IPpmEmpKqFaceDataService;
import com.work.ppm.application.services.bankmanager.IPpmEmpKqInfoService;
import com.work.ppm.application.services.bankmanager.IPpmEmpKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmKqOvertimeConfigService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class PpmEmpKqFaceDataService implements IPpmEmpKqFaceDataService {
    /**
     * 外包考勤实体类路径
     */
    private static final String PPMEMPKQFACEDATA = "com.work.ppm.application.model.bankmanager.PpmEmpKqFaceData";
    /**
     * 错误编码
     */
    private static final String ERRORCODE = "999999";
    /**
     * 返回编码 code
     */
    private static final String CODEKEY = "code";
    /**
     * 返回信息 retMsg
     */
    private static final String MSGKEY = "retMsg";

    /**
     * 同步标志
     */
    private static final String FACE_SYNC_BY = "faceSync";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 行员信息详情 Service
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;

    /**
     * 行员考勤信息详情 Service
     */
    @Autowired
    private IPpmEmpKqInfoService ppmEmpKqInfoService;

    /**
     * 行员考勤记录详情 Service
     */
    @Autowired
    private IPpmEmpKqRecordService ppmEmpKqRecordService;

    /**
     * 加班开始时间配置 Service
     */
    @Autowired
    private IPpmKqOvertimeConfigService ppmKqOvertimeConfigService;

    @Override
    public Map<String, Object> syncKqFaceData(PpmKqFaceDataDto ppmKqFaceDataDto) {
        //定义返回参数集合
        Map<String, Object> map = new HashMap<>();
        //存储数据
        ppmKqFaceDataDto.setId(SnowflakeIdWorker.getSnowId());
        ppmKqFaceDataDto.setCreatedDate(new Date());
        ppmKqFaceDataDto.setType("OTHER");
        addPpmKqFaceData(ppmKqFaceDataDto);

        String punchTime = ppmKqFaceDataDto.getPunchTime();
        //校验入参
        if (StringUtils.isEmpty(punchTime)
                || StringUtils.isEmpty(ppmKqFaceDataDto.getIpAddress())) {
            return returnMapValue(map, ERRORCODE, "必输参数缺失。");
        }

        String jobId = ppmKqFaceDataDto.getCardNO();

        if (StringUtils.isEmpty(jobId)) {
            return returnMapValue(map, ERRORCODE, "工号为空");
        }

        CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(jobId);
        if (commEmployee == null) {
            return returnMapValue(map, ERRORCODE, "工号不存在");
        }

        //查询是否为空 2022-10-13 5:55
        Date punchTimeDate = DateUtil.strToDateTime(punchTime, "yyyy/MM/dd HH:mm:ss");

        //转换成LocalDateTime
        LocalDateTime localDateTime = punchTimeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localDateTime1 = localDateTime;
        //获取
        int day = localDateTime.getHour();
        if (day < 6) {
            localDateTime1 = localDateTime.minusDays(1);
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");

        PpmEmpKqInfoDto ppmEmpKqInfoDto = new PpmEmpKqInfoDto();
        ppmEmpKqInfoDto.setJobId(jobId);
        ppmEmpKqInfoDto.setUserName(commEmployee.getEmpname());
        String attendanceYearMonth = dateTimeFormatter.format(localDateTime1);
        ppmEmpKqInfoDto.setAttendanceMonth(attendanceYearMonth);
        log.info("------ppmEmpKqInfo考勤信息表开始处理------");
        ppmEmpKqInfoDto = ppmEmpKqInfoService.queryDetailObject(ppmEmpKqInfoDto, "empQueryDetailObject");
        if (ppmEmpKqInfoDto == null) {
            ppmEmpKqInfoDto = new PpmEmpKqInfoDto();
            ppmEmpKqInfoDto.setId(SnowflakeIdWorker.getSnowId());
            ppmEmpKqInfoDto.setJobId(jobId);
            ppmEmpKqInfoDto.setUserName(commEmployee.getEmpname());
            ppmEmpKqInfoDto.setAttendanceMonth(attendanceYearMonth);
            ppmEmpKqInfoDto.setWhetherUpdate("0");
            ppmEmpKqInfoDto.setExemptionNumber(2L);
            ppmEmpKqInfoDto.setCreatedBy(FACE_SYNC_BY);
            ppmEmpKqInfoDto.setCreatedTime(new Date());
            ppmEmpKqInfoService.addPpmKqInfo(ppmEmpKqInfoDto);
        } else {
            ppmEmpKqInfoDto.setWhetherUpdate("0");
            ppmEmpKqInfoService.updatePpmKqInfo(ppmEmpKqInfoDto);
        }

        Date attendanceDate = Date.from(localDateTime1.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        //判断日期是否节假日
        boolean isHoliday = ppmEmpKqInfoService.judgingDateIsWorkingDay(attendanceDate);

        PpmEmpKqRecordDto ppmEmpKqRecordDto = new PpmEmpKqRecordDto();
        ppmEmpKqRecordDto.setJobId(jobId);
        ppmEmpKqRecordDto.setInfoId(ppmEmpKqInfoDto.getId());
        ppmEmpKqRecordDto.setUserName(ppmEmpKqInfoDto.getUserName());
        ppmEmpKqRecordDto.setAttendanceDate(attendanceDate);
        ppmEmpKqRecordDto = ppmEmpKqRecordService.queryDetailObject(ppmEmpKqRecordDto, ppmEmpKqInfoDto.getId());

        if (ppmEmpKqRecordDto == null) {
            ppmEmpKqRecordDto = new PpmEmpKqRecordDto();
            addEmpRecordInfo(ppmEmpKqRecordDto, ppmEmpKqInfoDto, jobId, attendanceDate);
            setAbnormalReasonValue(isHoliday, ppmEmpKqRecordDto, true);
            addAndupdateRecordInfo(ppmEmpKqRecordDto, localDateTime1, punchTimeDate);
            ppmEmpKqRecordService.addPpmKqRecord(ppmEmpKqRecordDto);
        } else {
            setAbnormalReasonValue(isHoliday, ppmEmpKqRecordDto, false);
            addAndupdateRecordInfo(ppmEmpKqRecordDto, localDateTime1, punchTimeDate);
            ppmEmpKqRecordDto.setModifiedTime(new Date());
            ppmEmpKqRecordDto.setModifiedBy(FACE_SYNC_BY);
            ppmEmpKqRecordService.updatePpmKqRecord(ppmEmpKqRecordDto);
        }

        return returnMapValue(map, "000000", "成功");
    }

    @Override
    public int addPpmKqFaceData(PpmKqFaceDataDto ppmKqFaceDataDto) {
        PpmKqFaceData ppmKqFaceData = JsonUtil.jsonToClass(ppmKqFaceDataDto, PpmKqFaceData.class);
        myBatisDao.executionPojo(PPMEMPKQFACEDATA, ppmKqFaceData, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    /**
     * 同步工号转换
     *
     * @param cardNo 入参工号
     * @return String
     */
    private String syncJobIdValue(String cardNo) {
        String jobId = null;
        if (StringUtils.isNotEmpty(cardNo)) {
            if (cardNo.contains("j")) {
                jobId = cardNo.replace("j", "ywwb");
            } else {
                jobId = cardNo;
            }
        }
        return jobId;
    }

    /**
     * 是否是工作日 存储是否处理
     *
     * @param isHoliday         是否工作日
     * @param ppmEmpKqRecordDto 对象
     */
    private void setAbnormalReasonValue(boolean isHoliday, PpmEmpKqRecordDto ppmEmpKqRecordDto, boolean flag) {
        ppmEmpKqRecordDto.setDataEntryType("sync");
        ppmEmpKqRecordDto.setIsHoliday(isHoliday ? "1" : "0");
    }

    /**
     * 修改详情
     *
     * @param ppmEmpKqRecordDto 对象
     * @param localDateTime1    localDateTime
     * @param punchTimeDate     打卡时间
     */
    private void addAndupdateRecordInfo(PpmEmpKqRecordDto ppmEmpKqRecordDto, LocalDateTime localDateTime1, Date punchTimeDate) {
        LocalTime workTime = localDateTime1.toLocalTime();
        Date workingTime = ppmEmpKqRecordDto.getWorkingTime();
        Date closingTime = ppmEmpKqRecordDto.getClosingTime();
        //当上班时间和下班时间都为空时，6:00:00 <= 打卡时间 < 13:30:00 填充在上班时间内
        if (workingTime == null && closingTime == null) {
            if (workTime.isBefore(LocalTime.parse("13:30:00")) &&
                    workTime.isAfter(LocalTime.parse("06:00:00").minusSeconds(1))) {
                ppmEmpKqRecordDto.setWorkingTime(punchTimeDate);
            } else {
                //否则将打卡时间填充到下班时间内
                ppmEmpKqRecordDto.setClosingTime(punchTimeDate);
            }
        } else {
            ppmEmpKqRecordDto.setClosingTime(punchTimeDate);
        }

        //如果23:00:00 <= 打卡时间 < 6:00:00 是否可调休存储 => Y
        if (workTime.isAfter(LocalTime.parse("23:29:59")) ||
                workTime.isBefore(LocalTime.parse("06:00:00"))) {
            ppmEmpKqRecordDto.setWhetherDaysOff("Y");
        } else {
            // 否则是否可调休存储 => N
            ppmEmpKqRecordDto.setWhetherDaysOff("N");
        }

        ppmEmpKqRecordDto.setOvertimeStartTime(ppmKqOvertimeConfigService.getOvertimeStartTime());
        ppmEmpKqRecordDto.setIsCalOvertime("0");
        ppmEmpKqRecordDto.setWhetherAbnormal("0");
    }

    /**
     * 行员新增详情
     *
     * @param ppmEmpKqRecordDto 详情对象
     * @param ppmEmpKqInfoDto   信息对象
     * @param jobId             工号
     * @param attendanceDate    考勤日期
     */
    private void addEmpRecordInfo(PpmEmpKqRecordDto ppmEmpKqRecordDto, PpmEmpKqInfoDto ppmEmpKqInfoDto,
                                  String jobId, Date attendanceDate) {
        ppmEmpKqRecordDto.setId(SnowflakeIdWorker.getSnowId());
        ppmEmpKqRecordDto.setInfoId(ppmEmpKqInfoDto.getId());
        ppmEmpKqRecordDto.setJobId(jobId);
        ppmEmpKqRecordDto.setUserName(ppmEmpKqInfoDto.getUserName());
        ppmEmpKqRecordDto.setAttendanceDate(attendanceDate);
        ppmEmpKqRecordDto.setCreatedTime(new Date());
        ppmEmpKqRecordDto.setCreatedBy(FACE_SYNC_BY);
    }

    /**
     * 返回参数
     *
     * @param map       返回参数集合
     * @param errorCode 编码
     * @param message   message
     * @return map
     */
    private Map<String, Object> returnMapValue(Map<String, Object> map, String errorCode, String message) {
        map.put(CODEKEY, errorCode);
        map.put(MSGKEY, message);
        return map;
    }
}
