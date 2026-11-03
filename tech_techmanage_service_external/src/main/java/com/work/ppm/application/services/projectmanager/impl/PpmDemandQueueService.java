package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.PpmDemandQueueDto;
import com.work.ppm.application.model.projectmanager.PpmDemandQueue;
import com.work.ppm.application.services.projectmanager.IPpmDemandQueueService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
  * 奋进号流程队列
  */
@Service("ppmDemandQueueService")
public class PpmDemandQueueService extends BaseDaoSupper implements IPpmDemandQueueService {
    /**
      * 实体类路径
      */
    private static String PPMDEMANDQUEUE = "com.work.ppm.application.model.projectmanager.PpmDemandQueue";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmDemandQueue(PpmDemandQueueDto ppmDemandQueueDto) {
        PpmDemandQueue ppmDemandQueue = JsonUtil.jsonToClass(ppmDemandQueueDto, PpmDemandQueue.class);
        Long id = getNextSequence(PpmDemandQueue.class);
        ppmDemandQueue.setId(id);
        myBatisDao.executionPojo(PPMDEMANDQUEUE, ppmDemandQueue, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

}
