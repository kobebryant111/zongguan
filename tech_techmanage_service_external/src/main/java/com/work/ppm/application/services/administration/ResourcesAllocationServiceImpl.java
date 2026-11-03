package com.work.ppm.application.services.administration;

import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;

import java.util.Map;

public interface ResourcesAllocationServiceImpl {

    /**
     *
     * @param ppmKqRdCalendar 对象
     */
    void editAttenStatus(PpmKqRdCalendar ppmKqRdCalendar);

    /**
     * 修改考勤日期出勤
     * @param map 对象
     */

    void editPojo(Map<String, Object> map);
}
