package com.work.ppm.application.services.rdmanager.impl;

import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.HrProject;
import com.work.ppm.application.services.rdmanager.IHrNewRequiresService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 研发项目管理
 *
 */
@Service
@Slf4j
public class HrNewRequiresService implements IHrNewRequiresService {

    private static String COMMRDEMPLOYEE = "com.work.ppm.application.model.rdmanager.CommRdEmployeeDto";
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW ,rollbackFor = Exception.class)
    public Integer keep(List<HrProject> hrProjects) {
        Integer attendanceRecord = 0;
        if (ObjectUtils.isEmpty(hrProjects)) {
            return attendanceRecord;
        }
        List<PpmKqRdCalendar> ppmKqRdCalendars = new ArrayList<>();
        for (HrProject hrProject : hrProjects) {
            String eidCode = hrProject.getEidCode();
            String username = hrProject.getUsername();
            String badge = hrProject.getBadge();
            for (PpmKqRdCalendar ppmKqRdCalendar : hrProject.getRdCalendarList()) {
                ppmKqRdCalendar.setEidCode(eidCode);
                ppmKqRdCalendar.setUsername(username);
                ppmKqRdCalendar.setBadge(badge);
                if ("N".equals(ppmKqRdCalendar.getIsHoliday())) {
                    ppmKqRdCalendars.add(ppmKqRdCalendar);
                }
            }
        }
        for (PpmKqRdCalendar ppmKqRdCalendar : ppmKqRdCalendars) {
            BigDecimal attendanceTime = ppmKqRdCalendar.getAttendanceTime();
            if (!ObjectUtils.isEmpty(attendanceTime)) {
                //attendanceTime需在【0,1】
                if (attendanceTime.compareTo(BigDecimal.ZERO) < 0 || attendanceTime.compareTo(BigDecimal.ONE) > 0) {
                    throw new BizException(ApiCode.ATTENDANCE_ERROR, ppmKqRdCalendar.getUsername() + "(" + ppmKqRdCalendar.getBadge() + ")" +
                            ppmKqRdCalendar.getDayOfMonth() + "号：考勤时长不在【0~1】");
                }
                int scale = attendanceTime.scale();
                if (attendanceTime.scale() > 4) {
                    throw new BizException(ApiCode.ATTENDANCE_ERROR, ppmKqRdCalendar.getUsername() + "(" + ppmKqRdCalendar.getBadge() + ")" +
                            ppmKqRdCalendar.getDayOfMonth() + "号：考勤时长数值小数应精确至小数点后四位");
                }
            }
        }
        // 将时间查询条件转换为日期对象"2024-04-17"
        attendanceRecord = myBatisDao.executionPojo(COMMRDEMPLOYEE, ppmKqRdCalendars, IMyBatisDao.SQL.UPDATE, "attendanceRecord");
        return attendanceRecord;
    }

}

