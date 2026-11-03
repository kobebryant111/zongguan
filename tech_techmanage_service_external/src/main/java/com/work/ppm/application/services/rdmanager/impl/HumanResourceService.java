package com.work.ppm.application.services.rdmanager.impl;

import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.HrProject;
import com.work.ppm.application.model.rdmanager.HrProjectYear;
import com.work.ppm.application.model.rdmanager.RdEmployeeDto;
import com.work.ppm.application.model.rdmanager.RdProjectAttendance;
import com.work.ppm.application.services.rdmanager.IHumanResourceService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HumanResourceService implements IHumanResourceService {

    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 实体类路径
     */
    private static String RDEMPLOYEE = "com.work.ppm.application.model.rdmanager.RdEmployeeDto";

    @Override
    public Integer jobTransfer(RdEmployeeDto rdEmployeeDto) {
        Map<String, Object> map = StringUtil.objectTOMap(rdEmployeeDto, RdEmployeeDto.class);
        return myBatisDao.executionMap(RDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "jobTransfer");
    }

    @Override
    public List<String> currentAllYear() {
        Map hashMap = new HashMap<>();
        return myBatisDao.selectListT(RDEMPLOYEE, hashMap, "currentAllYear");
    }

    @Override
    public List<Map<Object, Object>> importYearAll(PpmKqRdCalendar ppmKqRdCalendar) {
        Map<String, Object> map = StringUtil.objectTOMap(ppmKqRdCalendar, PpmKqRdCalendar.class);
        List<RdProjectAttendance> rdProjectAttendances = myBatisDao.selectListT(RDEMPLOYEE, map, "queryYearAttendce");
        List<Map<Object, Object>> HrProjectYears = new ArrayList<>();
        for (RdProjectAttendance rdProjectAttendance : rdProjectAttendances) {
            this.attendanceYear(HrProjectYears, rdProjectAttendance);
        }
        return HrProjectYears;
    }

    private void attendanceYear(List<Map<Object, Object>> yearList, RdProjectAttendance rdProjectAttendance) {
        String attendanceDate = rdProjectAttendance.getAttendanceDate();
        String attendanceStatus = rdProjectAttendance.getAttendanceStatus();
        YearMonth parse = YearMonth.parse(attendanceDate);
        String firstDay = parse.atDay(1).toString();
        String lastDay = parse.atEndOfMonth().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("firstDay", firstDay);
        map.put("lastDay", lastDay);
        map.put("attendanceDate", attendanceDate);

        switch (attendanceStatus) {

            case "0":
            case "1": {
                List<HrProjectYear> list = myBatisDao.selectListT(RDEMPLOYEE, map, "rdCase01Year");
                filterAndList(yearList, list);
                break;
            }
            case "2": {
                List<HrProjectYear> list = myBatisDao.selectListT(RDEMPLOYEE, map, "rdCase2Year");
                filterAndList(yearList, list);
                break;
            }
            default:
                break;
        }
    }

    private static void filterAndList(List<Map<Object, Object>> yearList, List<HrProjectYear> list) {
        List<Map<String, Object>> collect = new ArrayList<>(list.stream().collect(
                Collectors.toMap(
                        HrProjectYear::getBadge,
                        p -> {
                            Map<String, Object> m = new HashMap<>();
                            m.put("username", p.getUsername());
                            m.put("badge", p.getBadge());
                            m.put("chargename", p.getChargename());
                            m.put("monthOfYear", p.getMonthOfYear());
                            m.put("sumMonth", p.getSumMonth());
                            return m;
                        },
                        (a, b) -> {
                            a.put("sumMonth", ((BigDecimal) a.get("sumMonth")).add((BigDecimal) b.get("sumMonth")));
                            return a;
                        }
                )
        ).values());
        for (Map<String, Object> objectMap : collect) {
            boolean exists = yearList.stream().anyMatch(m -> objectMap.get("badge").equals(m.get("badge")));
            if (!exists) {
                Map<Object, Object> hashMap = new HashMap<>();
                hashMap.put("username", objectMap.get("username"));
                hashMap.put("badge", objectMap.get("badge"));
                hashMap.put("chargename", objectMap.get("chargename"));
                hashMap.put(objectMap.get("monthOfYear"), objectMap.get("sumMonth"));
                yearList.add(hashMap);
            } else {
                yearList.stream().filter(m -> objectMap.get("badge").equals(m.get("badge")))
                        .findFirst().ifPresent(
                                m -> {
                                    m.put(objectMap.get("monthOfYear"), objectMap.get("sumMonth"));
                                }
                        );
            }
        }
    }

}
