package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.application.model.flowmanager.PpmFlowShouquanLog;
import com.work.ppm.application.services.flowmanager.IPpmFlowShouquanLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *  流程授权日志
 */
@Service("ppmFlowShouquanLogService")
public class PpmFlowShouquanLogService extends BaseDaoSupper implements IPpmFlowShouquanLogService {
    /**
     * 实体类路径
     */
    private static String PPMFLOWSHOUQUANLOG = "com.work.ppm.application.model.flowmanager.PpmFlowShouquanLog";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmFlowShouquanLog ppmFlowShouquanLog, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmFlowShouquanLog,PpmFlowShouquanLog.class);
        myBatisDao.selectPager(PPMFLOWSHOUQUANLOG, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmFlowShouquanLog(PpmFlowShouquanLog ppmFlowShouquanLog) {
        Long key = getNextSequence(PpmFlowShouquanLog.class);
        // 如果需要请set 主键id1
        ppmFlowShouquanLog.setId(key);
        myBatisDao.executionPojo(PPMFLOWSHOUQUANLOG, ppmFlowShouquanLog, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmFlowShouquanLog queryDetail(PpmFlowShouquanLog ppmFlowShouquanLog) {
        return myBatisDao.selectClass(PPMFLOWSHOUQUANLOG, ppmFlowShouquanLog,"queryDetail");
    }

    @Override
    public Integer updatePpmFlowShouquanLog(PpmFlowShouquanLog ppmFlowShouquanLog) {
        return myBatisDao.executionPojo(PPMFLOWSHOUQUANLOG, ppmFlowShouquanLog, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmFlowShouquanLog(PpmFlowShouquanLog ppmFlowShouquanLog) {
        return myBatisDao.executionPojo(PPMFLOWSHOUQUANLOG, ppmFlowShouquanLog, IMyBatisDao.SQL.DELETE,"deleteById");
    }

}
