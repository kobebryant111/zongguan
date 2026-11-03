package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.PpmDemandApplyDto;
import com.work.ppm.application.model.projectmanager.PpmDemandApply;
import com.work.ppm.application.services.projectmanager.IPpmDemandApplyService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
  * 老的业务意向
  */
@Service("ppmDemandApplyService")
public class PpmDemandApplyService extends BaseDaoSupper implements IPpmDemandApplyService {
    /**
      * 实体类路径
      */
    private static String PPMDEMANDAPPLY = "com.work.ppm.application.model.projectmanager.PpmDemandApply";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;



    @Override
    public int addPpmDemandApply(PpmDemandApplyDto ppmDemandApplyDto) {
        PpmDemandApply ppmDemandApply = JsonUtil.jsonToClass(ppmDemandApplyDto, PpmDemandApply.class);
        Long id = getNextSequence(PpmDemandApply.class);
        // 如果需要请set 主键id
        ppmDemandApply.setId(id);
        myBatisDao.executionPojo(PPMDEMANDAPPLY, ppmDemandApply, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmDemandApplyDto getdemandByProjectno(String projectno) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectno", projectno);
        PpmDemandApply data = myBatisDao.selectClass(PPMDEMANDAPPLY, map,"getdemandByProjectno");
        PpmDemandApplyDto item = JsonUtil.jsonToClass(data, PpmDemandApplyDto.class);
        return item;
    }

    @Override
    public PpmDemandApplyDto getdemandByDemandno(String demandno) {
        Map<String, Object> map = new HashMap<>();
        map.put("demandno", demandno);
        PpmDemandApply data = myBatisDao.selectClass(PPMDEMANDAPPLY, map,"getdemandByDemandno");
        PpmDemandApplyDto item = JsonUtil.jsonToClass(data, PpmDemandApplyDto.class);
        return item;
    }

}
