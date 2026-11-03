package com.work.ppm.application.services.rdmanager;

import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.HrProject;
import com.work.ppm.application.model.rdmanager.RdEmployeeDto;

import java.util.List;
import java.util.Map;

public interface IHumanResourceService {
    Integer jobTransfer(RdEmployeeDto rdEmployeeDto);

    List<String> currentAllYear();

    List<Map<Object, Object>> importYearAll(PpmKqRdCalendar ppmKqRdCalendar);
}
