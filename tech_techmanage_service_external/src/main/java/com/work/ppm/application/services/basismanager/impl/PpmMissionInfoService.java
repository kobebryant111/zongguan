package com.work.ppm.application.services.basismanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.basismanager.PpmMissionInfo;
import com.work.ppm.application.dto.basismanager.PpmMissionInfoDto;
import com.work.ppm.application.services.basismanager.IPpmMissionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 描述
  */
@Service("ppmMissionInfoService")
public class PpmMissionInfoService extends BaseDaoSupper implements IPpmMissionInfoService {
    /**
      * 实体类路径
      */
    private static final String PPMMISSIONINFO = "com.work.ppm.application.model.basismanager.PpmMissionInfo";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmMissionInfoDto ppmMissionInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmMissionInfoDto,PpmMissionInfoDto.class);
        myBatisDao.selectPager(PPMMISSIONINFO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmMissionInfo(PpmMissionInfoDto ppmMissionInfoDto) {
        PpmMissionInfo ppmMissionInfo = JsonUtil.jsonToClass(ppmMissionInfoDto, PpmMissionInfo.class);
        myBatisDao.executionPojo(PPMMISSIONINFO, ppmMissionInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmMissionInfoDto queryDetail(PpmMissionInfoDto ppmMissionInfoDto) {
        PpmMissionInfo ppmMissionInfo = JsonUtil.jsonToClass(ppmMissionInfoDto, PpmMissionInfo.class);
        PpmMissionInfo data = myBatisDao.selectClass(PPMMISSIONINFO, ppmMissionInfo,
        "queryDetail");
        return JsonUtil.jsonToClass(data, PpmMissionInfoDto.class);
    }

    @Override
    public int updatePpmMissionInfo(PpmMissionInfoDto ppmMissionInfoDto) {
        PpmMissionInfo ppmMissionInfo = JsonUtil.jsonToClass(ppmMissionInfoDto, PpmMissionInfo.class);
        return myBatisDao.executionPojo(PPMMISSIONINFO, ppmMissionInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmMissionInfo(PpmMissionInfoDto ppmMissionInfoDto) {
        PpmMissionInfo ppmMissionInfo = JsonUtil.jsonToClass(ppmMissionInfoDto, PpmMissionInfo.class);
        return myBatisDao.executionPojo(PPMMISSIONINFO, ppmMissionInfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public String saveMission(PpmMissionInfoDto ppmMissionInfoDto) {
        // 创建id，通过老科管方法
        String missionId = myBatisDao.selectClass(PPMMISSIONINFO,String.class,"nextMissionId");
        ppmMissionInfoDto.setMissionId(missionId);
        ppmMissionInfoDto.setMissionRefId(missionId);
        addPpmMissionInfo(ppmMissionInfoDto);
        return missionId;
    }
}
