package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.application.dto.flowmanager.PpmFlowGrantDto;
import com.work.ppm.application.model.flowmanager.PpmFlowGrant;
import com.work.ppm.application.services.flowmanager.IPpmFlowGrantService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 流程授权
 */
@Service("ppmFlowGrantService")
public class PpmFlowGrantService extends BaseDaoSupper implements IPpmFlowGrantService {
    /**
     * 实体类路径
     */
    private static String PPMFLOWGRANT = "com.work.ppm.application.model.flowmanager.PpmFlowGrant";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmFlowGrant ppmFlowGrant, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmFlowGrant,PpmFlowGrant.class);
        myBatisDao.selectPager(PPMFLOWGRANT, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmFlowGrant(PpmFlowGrant ppmFlowGrant) {
        Long key = this.getNextSequence(PpmFlowGrant.class);
        // 如果需要请set 主键id
        //ppmFlowLog.setId(key);
        myBatisDao.executionPojo(PPMFLOWGRANT, ppmFlowGrant, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmFlowGrant queryDetail(PpmFlowGrantDto ppmFlowGrant) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmFlowGrant,PpmFlowGrantDto.class);
        return myBatisDao.selectClass(PPMFLOWGRANT, map,"queryDetail");
    }

    @Override
    public Integer updatePpmFlowGrant(PpmFlowGrant ppmFlowGrant) {
        return myBatisDao.executionPojo(PPMFLOWGRANT, ppmFlowGrant, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmFlowGrant(PpmFlowGrant ppmFlowGrant) {
        return myBatisDao.executionPojo(PPMFLOWGRANT, ppmFlowGrant, IMyBatisDao.SQL.DELETE,"deleteById");
    }

}
