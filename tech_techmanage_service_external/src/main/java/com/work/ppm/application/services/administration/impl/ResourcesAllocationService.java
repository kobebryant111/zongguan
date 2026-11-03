package com.work.ppm.application.services.administration.impl;

import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.CommRdEmployeeDto;
import com.work.ppm.application.services.administration.ResourcesAllocationServiceImpl;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.StringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ResourcesAllocationService implements ResourcesAllocationServiceImpl {
    @Autowired
    private IMyBatisDao myBatisDao;

    private static final String PPMKQRDCALENDAR = "com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar";
    @Override
    public void editAttenStatus(PpmKqRdCalendar ppmKqRdCalendar) {
        if (ObjectUtils.allNotNull(ppmKqRdCalendar)) {
            final String eidCode = ppmKqRdCalendar.getEidCode().split("_")[0];
            ppmKqRdCalendar.setEidCode(eidCode);
            Map<String, Object> map = StringUtil.objectTOMap(ppmKqRdCalendar, PpmKqRdCalendar.class);
            myBatisDao.executionMap(PPMKQRDCALENDAR, map, IMyBatisDao.SQL.UPDATE, "editAttenStatus");
        }
    }

    @Override
    public void editPojo(Map<String, Object> map) {
        if ("0".equals(map.get("empstatus"))) {
            myBatisDao.executionMap(PPMKQRDCALENDAR, map, IMyBatisDao.SQL.UPDATE, "editPojoAttenStatus");
        } else {
            myBatisDao.executionMap(PPMKQRDCALENDAR, map, IMyBatisDao.SQL.UPDATE, "editPojoLevetStatus");
        }

    }
}
