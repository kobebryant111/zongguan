package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendar;
import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.RdProjectEmployee;
import com.work.ppm.application.quartz.interfaces.ISyncManagementJobService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("syncManagementJobService")
public class SyncManagementJobService implements ISyncManagementJobService {
    /**
     * 实体类路径
     */
    private static String PPMKQCALENDAR = "com.work.ppm.application.model.integratedmanager.PpmKqCalendar";

    /**
     * 实体类路径
     */
    private static String COMMRDEMPLOYEE = "com.work.ppm.application.model.rdmanager.CommRdEmployeeDto";
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncManagementJob() throws IOException, ParseException {
        PpmKqCalendarDto ppmKqCalendarDto = new PpmKqCalendarDto();

        ppmKqCalendarDto.setEntryStartDate(DateUtil.getFirstDayByMonth());
        ppmKqCalendarDto.setEntryEndDate(DateUtil.getLasttDayByMonth());

        Map<String, Object> map = JsonUtil.objectTOMap(ppmKqCalendarDto, PpmKqCalendarDto.class);
        List<PpmKqCalendar> queryPagerList = myBatisDao.selectListT(PPMKQCALENDAR, map, "queryPagerList");
        final String firstDay = DateUtil.getFirstDay();
        final String lastDay = DateUtil.getLastDay();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("fistDay", firstDay);
        hashMap.put("lastDay", lastDay);
        List<RdProjectEmployee> dataList = myBatisDao.selectListT(COMMRDEMPLOYEE, hashMap, "rdProjectEmployee");

        if (dataList.isEmpty()) {
            return;
        }
        List<PpmKqRdCalendar> ppmKqRdCalendarList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (RdProjectEmployee hrProject : dataList) {
            boolean arrivalBool = false;
            boolean leaveBool = false;
            Date arrivalParse = new Date();
            Date leaveParse = new Date();
            if (!ObjectUtils.isEmpty(hrProject.getArrivalTime())) {
                arrivalBool = true;
                arrivalParse = simpleDateFormat.parse(hrProject.getArrivalTime());
            }
            if (!ObjectUtils.isEmpty(hrProject.getLeaveTime())) {
                leaveBool = true;
                leaveParse = simpleDateFormat.parse(hrProject.getLeaveTime());
            }
            for (PpmKqCalendar ppmKqCalendar : queryPagerList) {
                PpmKqRdCalendar ppmKqRdCalendar = new PpmKqRdCalendar();
                final Date calendarDate = ppmKqCalendar.getCalendarDate();
                int i = 0;
                int l = -1;
                if (arrivalBool) {
                    i = calendarDate.compareTo(arrivalParse);
                }
                if (leaveBool) {
                    l = calendarDate.compareTo(leaveParse);
                }
                if (i >= 0 && l < 0 && "N".equals(ppmKqCalendar.getIsHoliday())) {
                    ppmKqRdCalendar.setAttendanceOrno("1");
                } else if ((i < 0 || l >= 0) && "N".equals(ppmKqCalendar.getIsHoliday())) {
                    ppmKqRdCalendar.setAttendanceOrno("0");
                } else {
                    ppmKqRdCalendar.setAttendanceOrno("2");
                }
                ppmKqRdCalendar.setEidCode(hrProject.getEidCode());
                ppmKqRdCalendar.setId(hrProject.getEidCode() + "_" + simpleDateFormat.format(ppmKqCalendar.getCalendarDate()));
                BeanUtils.copyProperties(ppmKqCalendar, ppmKqRdCalendar);
                ppmKqRdCalendarList.add(ppmKqRdCalendar);
            }
        }
        int batchSize = 1000;
        for (int i = 0; i < ppmKqRdCalendarList.size(); i += batchSize) {
            int end = Math.min(i + batchSize, ppmKqRdCalendarList.size());
            List<PpmKqRdCalendar> ppmKqRdCalendars = ppmKqRdCalendarList.subList(i, end);
            myBatisDao.executionPojo(COMMRDEMPLOYEE, ppmKqRdCalendars, IMyBatisDao.SQL.INSTER, "atManagement");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncAttendanceJob() throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String format = localDate.format(dateTimeFormatter);
        map.put("startDate", format);
        myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.INSTER, "atAttendance");
    }

}
