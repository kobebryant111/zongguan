package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.model.othermanager.PpmDocumentLog;
import com.work.ppm.application.services.othermanager.IPpmDocumentLogService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 文档日志
 */
@Service("ppmDocumentLogService")
public class PpmDocumentLogService extends BaseDaoSupper implements IPpmDocumentLogService {
    /**
     * 实体类路径
     */
    private static String PPMDOCUMENTLOG = "com.work.ppm.application.model.othermanager.PpmDocumentLog";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmDocumentLog ppmDocumentLog, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmDocumentLog,PpmDocumentLog.class);
        myBatisDao.selectPager(PPMDOCUMENTLOG, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmDocumentLog(PpmDocumentLog ppmDocumentLog) {
        Long id = getNextSequence(PpmDocumentLog.class);
        // 如果需要请set 主键id
        ppmDocumentLog.setId(id);
        ppmDocumentLog.setOprdate(new Date());
        myBatisDao.executionPojo(PPMDOCUMENTLOG, ppmDocumentLog, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmDocumentLog queryDetail(PpmDocumentLog ppmDocumentLog) {
        return myBatisDao.selectClass(PPMDOCUMENTLOG, ppmDocumentLog,"queryDetail");
    }

    @Override
    public Integer updatePpmDocumentLog(PpmDocumentLog ppmDocumentLog) {
        return myBatisDao.executionPojo(PPMDOCUMENTLOG, ppmDocumentLog, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmDocumentLog(PpmDocumentLog ppmDocumentLog) {
        return myBatisDao.executionPojo(PPMDOCUMENTLOG, ppmDocumentLog, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
