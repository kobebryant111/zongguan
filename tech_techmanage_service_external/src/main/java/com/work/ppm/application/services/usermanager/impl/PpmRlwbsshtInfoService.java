package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.application.model.usermanager.PpmRlwbsshtInfo;
import com.work.ppm.application.services.usermanager.IPpmRlwbsshtInfoService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * 人力合同
 */
@Service("ppmRlwbsshtInfoService")
public class PpmRlwbsshtInfoService implements IPpmRlwbsshtInfoService {
    /**
     * 实体类路径
     */
    private static String PPMRLWBSSHTINFO = "com.work.ppm.application.model.usermanager.PpmRlwbsshtInfo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmRlwbsshtInfo ppmRlwbsshtInfo, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmRlwbsshtInfo,PpmRlwbsshtInfo.class);
        myBatisDao.selectPager(PPMRLWBSSHTINFO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmRlwbsshtInfo(PpmRlwbsshtInfo ppmRlwbsshtInfo) {
        myBatisDao.executionPojo(PPMRLWBSSHTINFO, ppmRlwbsshtInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmRlwbsshtInfo queryDetail(PpmRlwbsshtInfo ppmRlwbsshtInfo) {
        return myBatisDao.selectClass(PPMRLWBSSHTINFO, ppmRlwbsshtInfo,"queryDetail");
    }

    @Override
    public Integer updatePpmRlwbsshtInfo(PpmRlwbsshtInfo ppmRlwbsshtInfo) {
        return myBatisDao.executionPojo(PPMRLWBSSHTINFO, ppmRlwbsshtInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmRlwbsshtInfo(PpmRlwbsshtInfo ppmRlwbsshtInfo) {
        return myBatisDao.executionPojo(PPMRLWBSSHTINFO, ppmRlwbsshtInfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
