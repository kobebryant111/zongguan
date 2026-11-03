package com.work.ppm.application.services.bankmanager.impl;

import com.work.ppm.application.dto.bankmamager.PpmEmpKqInfoDto;
import com.work.ppm.application.dto.bankmamager.PpmEmpKqRecordDto;
import com.work.ppm.application.model.bankmanager.PpmEmpKqRecord;
import com.work.ppm.application.quartz.bankmanager.IPpmEmpKqRecordCalculateJobService;
import com.work.ppm.application.services.bankmanager.IPpmEmpKqInfoService;
import com.work.ppm.application.services.bankmanager.IPpmEmpKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmKqOvertimeConfigService;
import com.work.ppm.application.services.integratedmanager.impl.PpmKqRecordImportService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PpmEmpKqRecordService implements IPpmEmpKqRecordService, IPpmEmpKqRecordCalculateJobService {
    /**
     * 实体类路径
     */
    private static final String PPMEMPKQRECORD = "com.work.ppm.application.model.bankmanager.PpmEmpKqRecord";

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 加班开始时间配置 Service
     */
    @Autowired
    private IPpmKqOvertimeConfigService ppmKqOvertimeConfigService;

    @Autowired
    private IPpmEmpKqInfoService ppmEmpKqInfoService;


    @Override
    public PpmEmpKqRecordDto queryDetailObject(PpmEmpKqRecordDto ppmEmpKqRecordDto, Long infoId) {
        Map<String, Object> recordMap = new HashMap<>();
        recordMap.put("jobId", ppmEmpKqRecordDto.getJobId());
        recordMap.put("infoId", infoId);
        recordMap.put("userName", ppmEmpKqRecordDto.getUserName());
        recordMap.put("attendanceDate", DateUtil.dateToStr(ppmEmpKqRecordDto.getAttendanceDate(), DATE_FORMAT));
        PpmEmpKqRecord ppmEmpKqRecord = myBatisDao.selectClass(PPMEMPKQRECORD, recordMap, "empQueryDetailObject");
        return JsonUtil.jsonToClass(ppmEmpKqRecord, PpmEmpKqRecordDto.class);
    }

    @Override
    public int addPpmKqRecord(PpmEmpKqRecordDto ppmEmpKqRecordDto) {
        PpmEmpKqRecord ppmEmpKqRecord = JsonUtil.jsonToClass(ppmEmpKqRecordDto, PpmEmpKqRecord.class);
        myBatisDao.executionPojo(PPMEMPKQRECORD, ppmEmpKqRecord, IMyBatisDao.SQL.INSTER, "addEmpPojo");
        return 1;
    }

    @Override
    public int updatePpmKqRecord(PpmEmpKqRecordDto ppmEmpKqRecordDto) {
        PpmEmpKqRecord ppmEmpKqRecord = JsonUtil.jsonToClass(ppmEmpKqRecordDto, PpmEmpKqRecord.class);
        return myBatisDao.executionPojo(PPMEMPKQRECORD, ppmEmpKqRecord, IMyBatisDao.SQL.UPDATE, "updateEmpPojo");
    }

    @Override
    public List<PpmEmpKqRecordDto> queryDetailList(Long infoId) {
        PpmEmpKqRecordDto ppmEmpKqRecord = new PpmEmpKqRecordDto();
        ppmEmpKqRecord.setInfoId(infoId);
        return queryWhereList(ppmEmpKqRecord);
    }

    @Override
    public List<PpmEmpKqRecordDto> queryWhereList(PpmEmpKqRecordDto ppmKqRecordDto) {
        PpmEmpKqRecordDto ppmEmpKqRecord = JsonUtil.jsonToClass(ppmKqRecordDto, PpmEmpKqRecordDto.class);
        List<PpmEmpKqRecordDto> list = myBatisDao.selectListT(PPMEMPKQRECORD, ppmEmpKqRecord, "getEmpList");
        return JsonUtil.jsonToList(list, PpmEmpKqRecordDto.class);
    }

    @Override
    public int updatePpmKqRecordAndInfo(PpmEmpKqRecordDto ppmEmpKqRecordDto) {
        if (updatePpmKqRecord(ppmEmpKqRecordDto) != 0) {
            PpmEmpKqInfoDto ppmEmpKqInfoDto = new PpmEmpKqInfoDto();
            ppmEmpKqInfoDto.setId(ppmEmpKqRecordDto.getInfoId());
            ppmEmpKqInfoDto.setWhetherUpdate("0");
            return ppmEmpKqInfoService.updatePpmKqInfo(ppmEmpKqInfoDto);
        }
        return 0;
    }

    @Override
    public void recordCalaulate() {
        //1、查询处理状态为0的数据
        PpmEmpKqRecordDto ppmEmpKqRecordDto = new PpmEmpKqRecordDto();
        ppmEmpKqRecordDto.setWhetherAbnormal("0");
        List<PpmEmpKqRecordDto> kqRecordDtoList = queryWhereList(ppmEmpKqRecordDto);
        LocalTime endedTime = LocalTime.parse("17:30:00");
        LocalTime startedTime = LocalTime.parse("06:00:00");
        kqRecordDtoList.forEach(kqRecordDto -> {
            if ("1".equals(kqRecordDto.getIsHoliday())) {
                return;
            }
            if (kqRecordDto.getWorkingTime() == null || kqRecordDto.getClosingTime() == null
                    || (kqRecordDto.getClosingTime().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalTime().isBefore(endedTime)
                    && kqRecordDto.getClosingTime().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalTime().isAfter(startedTime))) {
                kqRecordDto.setWhetherAbnormal("1");
            }

            updatePpmKqRecordAndInfo(kqRecordDto);
        });
        log.info("考勤异常数据计算完成");
    }

    @Override
    public void overtimeCalaulate() {
        PpmEmpKqRecordDto empKqRecordDto = new PpmEmpKqRecordDto();
        empKqRecordDto.setIsCalOvertime("0");
        List<PpmEmpKqRecordDto> ppmEmpKqRecordDtos = queryWhereList(empKqRecordDto);
        if (ppmEmpKqRecordDtos != null && !ppmEmpKqRecordDtos.isEmpty()) {
            String overTimeStart = ppmKqOvertimeConfigService.getOvertimeStartTime();
            LocalDate now = LocalDate.now();
            ppmEmpKqRecordDtos.forEach(ppmEmpKqRecordDto -> {
                LocalDate localDate =
                        ppmEmpKqRecordDto.getAttendanceDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (now.isEqual(localDate)) {
                    return;
                }

                Map<String, Object> map = new HashMap<>();
                map.put("overtimeTimeFlag", false);
                map.put("overtimeMinuts", 0);
                map.put("worktimeMinuts", 0);
                // 工作日
                if ("0".equals(ppmEmpKqRecordDto.getIsHoliday())) {
                    //如果下班时间为空则默认赋值为当天下午17:30:00
                    if (ppmEmpKqRecordDto.getClosingTime() == null) {
                        String workDay = DateUtil.dateToStr(ppmEmpKqRecordDto.getWorkingTime(), DATE_FORMAT);
                        ppmEmpKqRecordDto.setClosingTime(
                                DateUtil.dataFormat(workDay + " 17:30:00", "YYYY-MM-DD HH:mm:ss"));
                    }
                    String endTime = DateUtil.dateToStr(ppmEmpKqRecordDto.getClosingTime(), TIME_FORMAT);
                    map = PpmKqRecordImportService.calculateWorkOvertimeHours(endTime, overTimeStart);
                } else {
                    //节假日
                    if (ppmEmpKqRecordDto.getWorkingTime() != null && ppmEmpKqRecordDto.getClosingTime() != null) {
                        String firstTime = DateUtil.dateToStr(ppmEmpKqRecordDto.getWorkingTime(), TIME_FORMAT);
                        String endTime = DateUtil.dateToStr(ppmEmpKqRecordDto.getClosingTime(), TIME_FORMAT);
                        map = PpmKqRecordImportService.calculateHolidayOvertimeHours(firstTime, endTime);
                    }
                }

                Long id = ppmEmpKqRecordDto.getId();
                Long infoId = ppmEmpKqRecordDto.getInfoId();
                ppmEmpKqRecordDto = new PpmEmpKqRecordDto();
                ppmEmpKqRecordDto.setId(id);
                ppmEmpKqRecordDto.setInfoId(infoId);
                ppmEmpKqRecordDto.setWhetherOvernight(((boolean) map.get("overtimeTimeFlag") ? "1" : "0"));
                ppmEmpKqRecordDto.setOvertimeHours(Long.valueOf(map.get("overtimeMinuts").toString()));
                ppmEmpKqRecordDto.setWorktimeHours(Long.valueOf(map.get("worktimeMinuts").toString()));
                ppmEmpKqRecordDto.setIsCalOvertime("1");
                updatePpmKqRecordAndInfo(ppmEmpKqRecordDto);
            });
        }
    }

}
