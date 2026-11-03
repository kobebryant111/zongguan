package com.work.ppm.application.model.rdmanager;

import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import lombok.Data;

import java.util.List;
@Data
public class RdManagementHrProject {


        /**
         *  研发人员
         */
        private List<HrProject> commRdEmployeeDtoList;
        /**
         * 日期
         */
        private PpmKqRdCalendar ppmKqRdCalendar;
}
