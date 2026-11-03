package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingExtInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingExt;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingExtInfo;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingExtService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.application.quartz.interfaces.IPpmOutsourcingEtxRLJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 外包人员扩展
 */
@Service("ppmOutsourcingExtService")
public class PpmOutsourcingExtService extends BaseDaoSupper implements IPpmOutsourcingExtService, IPpmOutsourcingEtxRLJobService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGEXT = "com.work.ppm.application.model.integratedmanager.PpmOutsourcingExt";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOutsourcingExt ppmOutsourcingExt, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOutsourcingExt,PpmOutsourcingExt.class);
        myBatisDao.selectPager(PPMOUTSOURCINGEXT, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOutsourcingExt(PpmOutsourcingExt ppmOutsourcingExt) {
//        Long id = getNextSequence(PpmOutsourcingExt.class);
//        // 如果需要请set 主键id
//        ppmOutsourcingExt.setId(id.intValue());
        Integer i = myBatisDao.executionPojo(PPMOUTSOURCINGEXT, ppmOutsourcingExt, IMyBatisDao.SQL.UPDATE, "updatePojo");
        if (i == 0) {
            myBatisDao.executionPojo(PPMOUTSOURCINGEXT, ppmOutsourcingExt, IMyBatisDao.SQL.INSTER, "addPojo");
        }
        return 1;
    }
    @Override
    public PpmOutsourcingExt queryDetail(PpmOutsourcingExt ppmOutsourcingExt) {
        return myBatisDao.selectClass(PPMOUTSOURCINGEXT, ppmOutsourcingExt,"queryDetail");
    }

    @Override
    public Integer updatePpmOutsourcingExt(PpmOutsourcingExt ppmOutsourcingExt) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGEXT, ppmOutsourcingExt, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmOutsourcingExt(PpmOutsourcingExt ppmOutsourcingExt) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGEXT, ppmOutsourcingExt, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public PpmOutsourcingExtInfo queryDetailAll(PpmOutsourcingExtInfo PpmOutsourcingExtInfo) {
        PpmOutsourcingExt ext=  JsonUtil.jsonToClass(PpmOutsourcingExtInfo, PpmOutsourcingExt.class);
        return myBatisDao.selectClass(PPMOUTSOURCINGEXT, ext,"queryDetailAll");
    }

    @Override
    public Integer updatePpmOutsourcingExt(PpmOutsourcingExtInfoDto ppmOutsourcingExtInfo) {
        PpmOutsourcingExt ext=  JsonUtil.jsonToClass(ppmOutsourcingExtInfo, PpmOutsourcingExt.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGEXT, ext, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public void createRlHt() {
        myBatisDao.executionPojo(PPMOUTSOURCINGEXT, null, IMyBatisDao.SQL.UPDATE,"createRlHt");
    }
}
