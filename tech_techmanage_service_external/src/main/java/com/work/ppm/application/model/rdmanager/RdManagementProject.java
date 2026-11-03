package com.work.ppm.application.model.rdmanager;


import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import lombok.Data;

import java.util.List;

@Data
public class RdManagementProject {
    /**
     *  研发人员
     */
    private List<CommRdEmployeeDto> commRdEmployeeDtoList;
    /**
     * 日期
     */
    private PpmKqRdCalendar ppmKqRdCalendar;
}
