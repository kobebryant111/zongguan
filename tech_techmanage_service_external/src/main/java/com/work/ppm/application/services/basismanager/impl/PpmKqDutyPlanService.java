package com.work.ppm.application.services.basismanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.basismanager.PpmKqDutyPlan;
import com.work.ppm.application.services.basismanager.IPpmKqDutyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤表
 */
@Service("ppmKqDutyPlanService")
public class PpmKqDutyPlanService extends BaseDaoSupper implements IPpmKqDutyPlanService {
    /**
     * 实体类路径
     */
    private static String PPMKQDUTYPLAN = "com.work.ppm.application.model.basismanager.PpmKqDutyPlan";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public List<PpmKqDutyPlan> getKqTypeList() {
        PpmKqDutyPlan entity = new PpmKqDutyPlan();
        return myBatisDao.selectListT(PPMKQDUTYPLAN, entity, "getKqTypeList");
    }

    @Override
    public PpmKqDutyPlan getKqTime(String kqType) {
        Map<String,Object> map = new HashMap<>();
        map.put("kqType", kqType);
        return myBatisDao.selectClass(PPMKQDUTYPLAN, map, "getKqTime");
    }
}
