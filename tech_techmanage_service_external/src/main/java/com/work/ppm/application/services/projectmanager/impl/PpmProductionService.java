package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.PpmProductionDto;
import com.work.ppm.application.services.projectmanager.IPpmProductionService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目投产表
 */
@Service("ppmProductionService")
public class PpmProductionService extends BaseDaoSupper implements IPpmProductionService {
    /**
      * 实体类路径
      */
//    private static final String PPMPRODUCTION = "com.work.ppm.application.model.projectmanager.PpmProduction";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public boolean checkedIsOnline(PpmProductionDto ppmProductionDto) {

        return false;
    }
}
