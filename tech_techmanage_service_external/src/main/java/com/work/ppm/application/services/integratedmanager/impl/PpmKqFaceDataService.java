package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmKqFaceDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmKqRecordOtherDto;
import com.work.ppm.application.model.integratedmanager.PpmKqFaceData;
import com.work.ppm.application.model.integratedmanager.ext.PpmFaceEmpInfoByLease;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.services.bankmanager.IPpmEmpKqFaceDataService;
import com.work.ppm.application.services.integratedmanager.*;
import com.work.ppm.application.services.otherdeptmanager.IPpmKqRecordOtherService;
import com.work.ppm.application.services.otherdeptmanager.IPpmOutsourcePersonledgeService;
import com.work.ppm.commons.dao.BaseDaoSupper;
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

/**
 * 人脸考勤数据 接口实现类
 *
 */
@Slf4j
@Service("ppmKqFaceDataService")
public class PpmKqFaceDataService extends BaseDaoSupper implements IPpmKqFaceDataService {
    /**
     * 实体类路径
     */
    private static final String PPMKQFACEDATA = "com.work.ppm.application.model.integratedmanager.PpmKqFaceData";

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
     * 考勤信息Service
     */
    @Autowired
    private IPpmKqInfoService ppmKqInfoService;

    /**
     * 外包花名册 Service
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    /**
     * 非科技外包台账 Service
     */
    @Autowired
    private IPpmOutsourcePersonledgeService ppmOutsourcePersonledgeService;

    /**
     * 考勤信息详情 Service
     */
    @Autowired
    private IPpmKqRecordService ppmKqRecordService;

    /**
     * 非科技考勤信息详情 Service
     */
    @Autowired
    private IPpmKqRecordOtherService ppmKqRecordOtherService;

    /**
     * 行员考勤数据同步 Service
     */
    @Autowired
    private IPpmEmpKqFaceDataService ppmEmpKqFaceDataService;

    /**
     * 加班开始时间配置 Service
     */
    @Autowired
    private IPpmKqOvertimeConfigService ppmKqOvertimeConfigService;

    @Override
    public int addPpmKqFaceData(PpmKqFaceDataDto ppmKqFaceDataDto) {
        PpmKqFaceData ppmKqFaceData = JsonUtil.jsonToClass(ppmKqFaceDataDto, PpmKqFaceData.class);
        myBatisDao.executionPojo(PPMKQFACEDATA, ppmKqFaceData, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    public String getPpmFaceEmpInfoLease(String cardNo) {
        if (StringUtils.isNotEmpty(cardNo)) {
            PpmFaceEmpInfoByLease ppmFaceEmpInfoByLease = new PpmFaceEmpInfoByLease();
            ppmFaceEmpInfoByLease.setCardNO(cardNo);
            PpmFaceEmpInfoByLease faceEmpInfoByLease =
                    myBatisDao.selectClass(PPMKQFACEDATA, ppmFaceEmpInfoByLease, "getPpmFaceEmpInfoLease");
            if (faceEmpInfoByLease != null) {
                return faceEmpInfoByLease.getViewType();
            }
        }

        return null;
    }

    @Override
    public Map<String, Object> syncKqFaceData(PpmKqFaceDataDto ppmKqFaceDataDto) {
        if ("BANK".equals(getPpmFaceEmpInfoLease(ppmKqFaceDataDto.getCardNO()))) {
            //行员数据同步
            return ppmEmpKqFaceDataService.syncKqFaceData(ppmKqFaceDataDto);
        } else {
            return syncPpmKqFaceData(ppmKqFaceDataDto);
        }
    }

    /**
     * 科技外包人员考勤打卡
     *
     * @param ppmKqFaceDataDto 对象
     * @return map
     */
    private Map<String, Object> syncPpmKqFaceData(PpmKqFaceDataDto ppmKqFaceDataDto) {
        //定义返回参数集合
        Map<String, Object> map = new HashMap<>();
        //存储数据
        ppmKqFaceDataDto.setId(SnowflakeIdWorker.getSnowId());
        ppmKqFaceDataDto.setCreatedDate(new Date());
        ppmKqFaceDataDto.setType("CADRE");
        addPpmKqFaceData(ppmKqFaceDataDto);

        String punchTime = ppmKqFaceDataDto.getPunchTime();
        //校验入参
        if (StringUtils.isEmpty(punchTime)
                || StringUtils.isEmpty(ppmKqFaceDataDto.getIpAddress())) {
            return returnMapValue(map, ERRORCODE, "必输参数缺失。");
        }

        //处理数据
        String cardNo = ppmKqFaceDataDto.getCardNO();
        String jobId = syncJobIdValue(cardNo);
        if (StringUtils.isEmpty(jobId)) {
            return returnMapValue(map, ERRORCODE, "工号为空");
        }

        PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo = ppmOutsourcingBasicService.getBaseInfo(jobId);
        if (ppmOutsourcingBasicInfo == null) {
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

        PpmKqInfoDto ppmKqInfoDto = new PpmKqInfoDto();
        ppmKqInfoDto.setJobId(jobId);
        ppmKqInfoDto.setUserName(ppmOutsourcingBasicInfo.getUserName());
        String attendanceYearMonth = dateTimeFormatter.format(localDateTime1);
        ppmKqInfoDto.setAttendanceMonth(attendanceYearMonth);
        ppmKqInfoDto = ppmKqInfoService.queryDetailObject(ppmKqInfoDto, "queryDetailObject");
        if (ppmKqInfoDto == null) {
            ppmKqInfoDto = new PpmKqInfoDto();
            ppmKqInfoDto.setId(SnowflakeIdWorker.getSnowId());
            ppmKqInfoDto.setJobId(jobId);
            ppmKqInfoDto.setUserName(ppmOutsourcingBasicInfo.getUserName());
            ppmKqInfoDto.setAttendanceMonth(attendanceYearMonth);
            ppmKqInfoDto.setWhetherUpdate("0");
            ppmKqInfoDto.setExemptionNumber(2L);
            ppmKqInfoDto.setCreatedBy(FACE_SYNC_BY);
            ppmKqInfoDto.setCreatedTime(new Date());
            ppmKqInfoService.addPpmKqInfo(ppmKqInfoDto);
        } else {
            ppmKqInfoDto.setWhetherUpdate("0");
            ppmKqInfoService.updatePpmKqInfo(ppmKqInfoDto);
        }

        Date attendanceDate = Date.from(localDateTime1.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        boolean isHoliday = ppmKqInfoService.judgingDateIsWorkingDay(attendanceDate);

        PpmKqRecordDto ppmKqRecordDto = new PpmKqRecordDto();
        ppmKqRecordDto.setJobId(jobId);
        ppmKqRecordDto.setInfoId(ppmKqInfoDto.getId());
        ppmKqRecordDto.setUserName(ppmKqInfoDto.getUserName());
        ppmKqRecordDto.setAttendanceDate(attendanceDate);
        ppmKqRecordDto = ppmKqRecordService.queryDetailObject(ppmKqRecordDto, ppmKqInfoDto.getId());
        if (ppmKqRecordDto == null) {
            ppmKqRecordDto = new PpmKqRecordDto();
            addRecordInfo(ppmKqRecordDto, ppmKqInfoDto, jobId, attendanceDate);
            setAbnormalReasonValue(isHoliday, ppmKqRecordDto, true);
            addAndupdateRecordInfo(ppmKqRecordDto, localDateTime1, punchTimeDate);
            ppmKqRecordDto.setOutsourceType(ppmOutsourcingBasicInfo.getOutsourceType());
            ppmKqRecordDto.setPrices(ppmOutsourcingBasicInfo.getPrice());
            ppmKqRecordDto.setExemptionState(0L);
            ppmKqRecordService.addPpmKqRecord(ppmKqRecordDto);
        } else {
            setAbnormalReasonValue(isHoliday, ppmKqRecordDto, false);
            addAndupdateRecordInfo(ppmKqRecordDto, localDateTime1, punchTimeDate);
            ppmKqRecordDto.setModifiedTime(new Date());
            ppmKqRecordDto.setModifiedBy(FACE_SYNC_BY);
            ppmKqRecordService.updatePpmKqRecord(ppmKqRecordDto);
        }

        return returnMapValue(map, "000000", "成功");
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
     * @param isHoliday      是否工作日
     * @param ppmKqRecordDto 对象
     */
    private void setAbnormalReasonValue(boolean isHoliday, PpmKqRecordDto ppmKqRecordDto, boolean flag) {
        if (isHoliday) {
            ppmKqRecordDto.setAmWhetherHandle("3");
            ppmKqRecordDto.setAmWhetherAbnormal("0");
            ppmKqRecordDto.setPmWhetherHandle("3");
            ppmKqRecordDto.setPmWhetherAbnormal("0");
        } else {
            //如果上午异常处理状态是已处理（2），则将考勤详情不存储 待跑批（0）
            if (flag) {
                ppmKqRecordDto.setAmWhetherHandle("0");
            }

            ppmKqRecordDto.setPmWhetherHandle("0");
        }

        ppmKqRecordDto.setDataEntryType("sync");
        ppmKqRecordDto.setIsHoliday(isHoliday ? "1" : "0");
    }

    /**
     * 修改详情
     *
     * @param ppmKqRecordOtherDto 对象
     * @param localDateTime1      localDateTime
     * @param punchTimeDate       打卡时间
     */
    private void addAndupdateRecordOtherInfo(
            PpmKqRecordOtherDto ppmKqRecordOtherDto, LocalDateTime localDateTime1, Date punchTimeDate) {
        LocalTime workTime = localDateTime1.toLocalTime();
        Date workingTime = ppmKqRecordOtherDto.getWorkingTime();
        Date closingTime = ppmKqRecordOtherDto.getClosingTime();
        if (workingTime == null && closingTime == null) {
            if (workTime.isBefore(LocalTime.parse("13:30:00")) &&
                    workTime.isAfter(LocalTime.parse("05:59:59"))) {
                ppmKqRecordOtherDto.setWorkingTime(punchTimeDate);
            } else {
                ppmKqRecordOtherDto.setClosingTime(punchTimeDate);
            }
        } else {
            ppmKqRecordOtherDto.setClosingTime(punchTimeDate);
        }
    }

    /**
     * 修改详情
     *
     * @param ppmKqRecordDto 对象
     * @param localDateTime1 localDateTime
     * @param punchTimeDate  打卡时间
     */
    private void addAndupdateRecordInfo(PpmKqRecordDto ppmKqRecordDto, LocalDateTime localDateTime1, Date punchTimeDate) {
        LocalTime workTime = localDateTime1.toLocalTime();
        Date workingTime = ppmKqRecordDto.getWorkingTime();
        Date closingTime = ppmKqRecordDto.getClosingTime();
        //当上班时间和下班时间都为空时，6:00:00 <= 打卡时间 < 13:30:00 填充在上班时间内
        if (workingTime == null && closingTime == null) {
            if (workTime.isBefore(LocalTime.parse("13:30:00")) &&
                    workTime.isAfter(LocalTime.parse("06:00:00").minusSeconds(1))) {
                ppmKqRecordDto.setWorkingTime(punchTimeDate);
            } else {
                //否则将打卡时间填充到下班时间内
                ppmKqRecordDto.setClosingTime(punchTimeDate);
            }
        } else {
            ppmKqRecordDto.setClosingTime(punchTimeDate);
        }

        //如果23:00:00 <= 打卡时间 < 6:00:00 是否可调休存储 => Y
        if (workTime.isAfter(LocalTime.parse("23:29:59")) ||
                workTime.isBefore(LocalTime.parse("06:00:00"))) {
            ppmKqRecordDto.setWhetherDaysOff("Y");
        } else {
            // 否则是否可调休存储 => N
            ppmKqRecordDto.setWhetherDaysOff("N");
        }

        ppmKqRecordDto.setOvertimeStartTime(ppmKqOvertimeConfigService.getOvertimeStartTime());
        ppmKqRecordDto.setIsCalOvertime("0");
    }

    /**
     * 新增详情
     *
     * @param ppmKqRecordDto 详情对象
     * @param ppmKqInfoDto   信息对象
     * @param jobId          工号
     * @param attendanceDate 考勤日期
     */
    private void addRecordInfo(
            PpmKqRecordDto ppmKqRecordDto, PpmKqInfoDto ppmKqInfoDto, String jobId, Date attendanceDate) {
        ppmKqRecordDto.setId(SnowflakeIdWorker.getSnowId());
        ppmKqRecordDto.setInfoId(ppmKqInfoDto.getId());
        ppmKqRecordDto.setJobId(jobId);
        ppmKqRecordDto.setUserName(ppmKqInfoDto.getUserName());
        ppmKqRecordDto.setAttendanceDate(attendanceDate);
        ppmKqRecordDto.setCreatedTime(new Date());
        ppmKqRecordDto.setCreatedBy(FACE_SYNC_BY);
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
