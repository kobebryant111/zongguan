package com.work.ppm.application.services.bankmanager.impl;

import com.work.ppm.application.dto.bankmamager.PpmEmpKqInfoDto;
import com.work.ppm.application.dto.bankmamager.PpmEmpKqRecordDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.bankmanager.PpmEmpKqInfo;
import com.work.ppm.application.model.bankmanager.ext.PpmEmpKqInfoView;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendar;
import com.work.ppm.application.quartz.bankmanager.IPpmEmpKqInfoCalculateJobService;
import com.work.ppm.application.services.bankmanager.IPpmEmpKqInfoService;
import com.work.ppm.application.services.bankmanager.IPpmEmpKqRecordService;
import com.work.ppm.application.services.bankmanager.IPpmEmpService;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarService;
import com.work.ppm.application.services.integratedmanager.IPpmKqOvertimeConfigService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
public class PpmEmpKqInfoService implements IPpmEmpKqInfoService, IPpmEmpKqInfoCalculateJobService {

    /**
     * 实体类路径
     */
    private static final String PPMEMPKQINFO = "com.work.ppm.application.model.bankmanager.PpmEmpKqInfo";

    private static final String UNIQUE_NAME_VIEW_LIST = "empQueryBaseInfoList";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 考勤信息服务
     */
    @Autowired
    private IPpmEmpKqRecordService ppmEmpKqRecordService;

    /**
     * 考勤日历服务接口
     */
    @Autowired
    private IPpmKqCalendarService ppmKqCalendarService;

    @Autowired
    private ICommEmployeeService commEmployeeService;

    /**
     * 加班开始时间配置
     */
    @Autowired
    private IPpmKqOvertimeConfigService ppmKqOvertimeConfigService;

    /**
     * 行员信息服务
     */
    @Autowired
    private IPpmEmpService ppmEmpService;

    @Override
    public void queryPager(PpmEmpKqInfoDto ppmEmpKqInfoDto, Pager pager, CommOperatorDoLoginDto user) {
        if (ppmEmpKqInfoDto.getAttendanceMonth() != null) {
            ppmEmpKqInfoDto.setAttendanceMonth(ppmEmpKqInfoDto.getAttendanceMonth().replace("-", ""));
        }

        if (ppmEmpKqInfoDto.getDeptId() != null) {
            ppmEmpKqInfoDto.setDeptId(ppmEmpKqInfoDto.getDeptId().replace("dep", ""));
        }

        handleAuthCode(ppmEmpKqInfoDto, user);
        ppmEmpKqInfoDto.setJobId(user.getDeptId());
        PpmEmpKqInfoDto empKqInfoDto = JsonUtil.jsonToClass(ppmEmpKqInfoDto, PpmEmpKqInfoDto.class);
        Map<String, Object> map = JsonUtil.objectTOMap(empKqInfoDto, PpmEmpKqInfoDto.class);
        myBatisDao.selectPager(PPMEMPKQINFO, map, pager, UNIQUE_NAME_VIEW_LIST);
        List<PpmEmpKqInfoView> list = JsonUtil.jsonToList(pager.getList(), PpmEmpKqInfoView.class);
        List<PpmEmpKqInfoDto> infoDtos = new ArrayList<>();
        for (PpmEmpKqInfoView obj : list) {
            PpmEmpKqInfoDto ppmEmpKqInfoDto1 = JsonUtil.jsonToClass(obj, PpmEmpKqInfoDto.class);
            if (ppmEmpKqInfoDto1.getDeptName() != null) {
                String[] s = ppmEmpKqInfoDto1.getDeptName().split("-");
                if (s.length > 1) {
                    ppmEmpKqInfoDto1.setDeptName(s[1]);
                }
            }

            infoDtos.add(ppmEmpKqInfoDto1);
        }
        pager.setList(infoDtos);
    }

    @Override
    public PpmEmpKqInfoDto queryDetail(PpmEmpKqInfoDto ppmKqInfoDto) {
        PpmEmpKqInfoDto ppmEmpKqInfoDto = queryDetailObject(ppmKqInfoDto, "empQueryBaseInfo");
        if (ppmEmpKqInfoDto == null) {
            return ppmKqInfoDto;
        }
        if (ppmEmpKqInfoDto.getDeptName() != null) {
            String[] s = ppmEmpKqInfoDto.getDeptName().split("-");
            if (s.length > 1) {
                ppmEmpKqInfoDto.setDeptName(s[1]);
            }
        }

        List<PpmEmpKqRecordDto> recordDtos = ppmEmpKqRecordService.queryDetailList(ppmKqInfoDto.getId());
        recordDtos.forEach(recordDto -> {
            recordDto.setActionType(false);
            recordDto.setPmName(ppmEmpKqInfoDto.getPmName());
            recordDto.setDeptName(ppmEmpKqInfoDto.getDeptName());
            recordDto.setWhetherUpdate(false);
        });
        ppmEmpKqInfoDto.setKqRecordDtoList(recordDtos);
        return ppmEmpKqInfoDto;
    }

    @Override
    public PpmEmpKqInfoDto queryDetailObject(PpmEmpKqInfoDto ppmKqInfoDto, String uniqueName) {
        PpmEmpKqInfo ppmKqInfo = JsonUtil.jsonToClass(ppmKqInfoDto, PpmEmpKqInfo.class);
        PpmEmpKqInfo data = myBatisDao.selectClass(PPMEMPKQINFO, ppmKqInfo, uniqueName);
        return JsonUtil.jsonToClass(data, PpmEmpKqInfoDto.class);
    }

    @Override
    public int addPpmKqInfo(PpmEmpKqInfoDto ppmKqInfoDto) {
        PpmEmpKqInfo ppmKqInfo = JsonUtil.jsonToClass(ppmKqInfoDto, PpmEmpKqInfo.class);
        myBatisDao.executionPojo(PPMEMPKQINFO, ppmKqInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public int updatePpmKqInfo(PpmEmpKqInfoDto ppmKqInfoDto) {
        PpmEmpKqInfo ppmKqInfo = JsonUtil.jsonToClass(ppmKqInfoDto, PpmEmpKqInfo.class);
        return myBatisDao.executionPojo(PPMEMPKQINFO, ppmKqInfo, IMyBatisDao.SQL.UPDATE, "updateEmpPojo");
    }

    @Override
    public boolean judgingDateIsWorkingDay(Date attendanceDate) {
        PpmKqCalendarDto kqCalendarDto = new PpmKqCalendarDto();
        kqCalendarDto.setCalendarDate(attendanceDate);
        PpmKqCalendarDto ppmKqCalendarDto = ppmKqCalendarService.queryDetail(kqCalendarDto);
        return ppmKqCalendarDto != null && "Y".equals(ppmKqCalendarDto.getIsHoliday());
    }

    @Override
    public List<PpmEmpKqInfoDto> queryDetailList(PpmEmpKqInfoDto ppmEmpKqInfoDto) {
        PpmEmpKqInfo ppmEmpKqInfo = JsonUtil.jsonToClass(ppmEmpKqInfoDto, PpmEmpKqInfo.class);
        List<PpmEmpKqInfo> data = myBatisDao.selectListT(PPMEMPKQINFO, ppmEmpKqInfo, "getEmpList");
        return JsonUtil.jsonToList(data, PpmEmpKqInfoDto.class);
    }

    @Override
    public Long queryCalendarListSize(Long mouth, Long years, String isHoliday) {
        String dataSource = "com.work.ppm.application.model.integratedmanager.PpmKqCalendar";
        Map<String, Object> map = new HashMap<>();
        map.put("monthOfYear", mouth);
        map.put("yearOfCalendar", years);
        if (StringUtils.isNotEmpty(isHoliday)) {
            map.put("isHoliday", isHoliday);
        }
        List<PpmKqCalendar> list = myBatisDao.selectListT(dataSource, map, "getList");
        List<PpmKqCalendarDto> dtos = JsonUtil.jsonToList(list, PpmKqCalendarDto.class);
        return (long) dtos.size();
    }


    /**
     * 处理权限
     *
     * @param ppmKqInfoDto ppmKqInfoDto
     * @param user         user
     */
    public void handleAuthCode(PpmEmpKqInfoDto ppmKqInfoDto, CommOperatorDoLoginDto user) {
        // 权限控制
        String authCode = ppmEmpService.getPermissionByUserid(user);
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
    public void infoCalaulate() {
        log.info("定时计算考勤次数 开始");
        PpmEmpKqInfoDto kqInfoDto = new PpmEmpKqInfoDto();
        kqInfoDto.setWhetherUpdate("0");
        List<PpmEmpKqInfoDto> kqInfoList = queryDetailList(kqInfoDto);

        if (!kqInfoList.isEmpty()) {
            kqInfoList.forEach(kqInfo -> {
                if (kqInfo != null) {
                    List<PpmEmpKqRecordDto> recordDtos = ppmEmpKqRecordService.queryDetailList(kqInfo.getId());
                    kqInfo = getKqCalaulateNumberMap(kqInfo, kqInfo.getAttendanceMonth(), recordDtos);
                    kqInfo.setWhetherUpdate("1");
                    updatePpmKqInfo(kqInfo);
                }
            });
        }
        log.info("定时计算考勤次数 结束");
    }


    /**
     * 计算考勤相关次数、天数
     *
     * @param attendanceMonth 考勤年月
     * @param list            考勤记录
     * @return map
     */
    public PpmEmpKqInfoDto getKqCalaulateNumberMap(
            PpmEmpKqInfoDto ppmEmpKqInfoDto, String attendanceMonth, List<PpmEmpKqRecordDto> list) {
        //忘记打卡次数
        long forgetClockInNumber = 0;
        //早退次数
        long leaveEarlyNumber = 0;
        //应出勤天数
        double requiredAttendanceDays = 0.0;
        //实际出勤天数
        double actualAttendanceDays = 0.0;
        //加班时长 小时
        double overtimeHours = 0.0;
        //工作时长 小时
        double worktimeHours = 0.0;

        if (attendanceMonth != null && attendanceMonth.length() == 6) {
            Long month = Long.valueOf(attendanceMonth.substring(4, 6));
            Long years = Long.valueOf(attendanceMonth.substring(0, 4));
            requiredAttendanceDays = this.queryCalendarListSize(month, years, "N");
        }
        LocalTime startedTime = LocalTime.parse("06:00:00");
        LocalTime endedTime = LocalTime.parse("17:30:00");
        if (list != null && !list.isEmpty()) {
            // 计算迟到次数，早退次数，旷工次数/半天
            forgetClockInNumber = list.stream().filter(x -> "0".equals(x.getIsHoliday()))
                    .filter(x -> x.getWorkingTime() == null || x.getClosingTime() == null).count();

            leaveEarlyNumber = list.stream().filter(x -> "0".equals(x.getIsHoliday()) && x.getClosingTime() != null)
                    .filter(x -> x.getClosingTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
                            .isBefore(endedTime) && x.getClosingTime().toInstant().atZone(ZoneId.systemDefault())
                            .toLocalTime().isAfter(startedTime)).count();

            overtimeHours = list.stream().filter(x -> x.getOvertimeHours() != null).
                    mapToDouble(PpmEmpKqRecordDto::getOvertimeHours).sum();

            worktimeHours = list.stream().filter(x -> x.getWorktimeHours() != null).
                    mapToDouble(PpmEmpKqRecordDto::getWorktimeHours).sum();
            //工作日天数
            double amWhetherDays = list.stream().filter(x -> "0".equals(x.getIsHoliday())).
                    filter(x -> x.getWorkingTime() != null).count();

            double pmWhetherDays = list.stream().filter(x -> "0".equals(x.getIsHoliday())).
                    filter(x -> x.getClosingTime() != null).count();
            //实际出勤天数
            actualAttendanceDays = (amWhetherDays + pmWhetherDays) / 2;
        }

        //忘打卡
        ppmEmpKqInfoDto.setForgetClockInNumber(forgetClockInNumber);
        //早退
        ppmEmpKqInfoDto.setLeaveEarlyNumber(leaveEarlyNumber);
        //当月应出勤
        ppmEmpKqInfoDto.setRequiredAttendanceDays(requiredAttendanceDays);
        //实际出勤天数
        ppmEmpKqInfoDto.setActualAttendanceDays(actualAttendanceDays);
        //加班时长
        ppmEmpKqInfoDto.setOvertimeHours(overtimeHours / 60);
        //工作时长
        ppmEmpKqInfoDto.setWorktimeHours(worktimeHours / 60);
        return ppmEmpKqInfoDto;
    }

}
