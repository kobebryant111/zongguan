package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.application.model.usermanager.PpmOutsourcingRiskTime;
import com.work.ppm.application.services.usermanager.IPpmOutsourcingRiskTimeService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * 外包风控排查时间接口
 */
@Service("ppmOutsourcingRiskTimeService")
public class PpmOutsourcingRiskTimeService implements IPpmOutsourcingRiskTimeService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGRISKTIME = "com.work.ppm.application.model.usermanager.PpmOutsourcingRiskTime";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOutsourcingRiskTime ppmOutsourcingRiskTime, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmOutsourcingRiskTime,PpmOutsourcingRiskTime.class);
        myBatisDao.selectPager(PPMOUTSOURCINGRISKTIME, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOutsourcingRiskTime(PpmOutsourcingRiskTime ppmOutsourcingRiskTime) {
        Integer i = myBatisDao.executionPojo(PPMOUTSOURCINGRISKTIME, ppmOutsourcingRiskTime, IMyBatisDao.SQL.UPDATE,"updatePojo");
        if (i == 0) {
            myBatisDao.executionPojo(PPMOUTSOURCINGRISKTIME, ppmOutsourcingRiskTime, IMyBatisDao.SQL.INSTER, "addPojo");
        }
        return 1;
    }
    @Override
    public PpmOutsourcingRiskTime queryDetail(PpmOutsourcingRiskTime ppmOutsourcingRiskTime) {
        return myBatisDao.selectClass(PPMOUTSOURCINGRISKTIME, ppmOutsourcingRiskTime,"queryDetail");
    }

    @Override
    public Integer updatePpmOutsourcingRiskTime(PpmOutsourcingRiskTime ppmOutsourcingRiskTime) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGRISKTIME, ppmOutsourcingRiskTime, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmOutsourcingRiskTime(PpmOutsourcingRiskTime ppmOutsourcingRiskTime) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGRISKTIME, ppmOutsourcingRiskTime, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
