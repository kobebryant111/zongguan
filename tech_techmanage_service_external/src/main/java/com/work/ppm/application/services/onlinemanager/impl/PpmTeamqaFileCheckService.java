package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.onlinemanager.PpmTeamqaFileCheck;
import com.work.ppm.application.dto.onlinemanager.PpmTeamqaFileCheckDto;
import com.work.ppm.application.services.onlinemanager.IPpmTeamqaFileCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 描述
  */
@Service("ppmTeamqaFileCheckService")
public class PpmTeamqaFileCheckService extends BaseDaoSupper implements IPpmTeamqaFileCheckService {
    /**
      * 实体类路径
      */
    private static String PPMTEAMQAFILECHECK = "com.work.ppm.application.model.onlinemanager.PpmTeamqaFileCheck";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmTeamqaFileCheckDto,PpmTeamqaFileCheckDto.class);
        myBatisDao.selectPager(PPMTEAMQAFILECHECK, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmTeamqaFileCheck(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) {
        PpmTeamqaFileCheck ppmTeamqaFileCheck = JsonUtil.jsonToClass(ppmTeamqaFileCheckDto, PpmTeamqaFileCheck.class);
        //Long id = getNextSequence(PpmTeamqaFileCheck.class);

        // 如果需要请set 主键id
        //ppmTeamqaFileCheck.setId(id.intValue());
        myBatisDao.executionPojo(PPMTEAMQAFILECHECK, ppmTeamqaFileCheck, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmTeamqaFileCheckDto queryDetail(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) {
        PpmTeamqaFileCheck ppmTeamqaFileCheck = JsonUtil.jsonToClass(ppmTeamqaFileCheckDto, PpmTeamqaFileCheck.class);
        PpmTeamqaFileCheck data = myBatisDao.selectClass(PPMTEAMQAFILECHECK, ppmTeamqaFileCheck,
        "queryDetail");
        PpmTeamqaFileCheckDto item = JsonUtil.jsonToClass(data, PpmTeamqaFileCheckDto
        .class);
        return item;
    }

    @Override
    public int updatePpmTeamqaFileCheck(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) {
        PpmTeamqaFileCheck ppmTeamqaFileCheck = JsonUtil.jsonToClass(ppmTeamqaFileCheckDto, PpmTeamqaFileCheck.class);
        return myBatisDao.executionPojo(PPMTEAMQAFILECHECK, ppmTeamqaFileCheck, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmTeamqaFileCheck(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) {
        PpmTeamqaFileCheck ppmTeamqaFileCheck = JsonUtil.jsonToClass(ppmTeamqaFileCheckDto, PpmTeamqaFileCheck.class);
        return myBatisDao.executionPojo(PPMTEAMQAFILECHECK, ppmTeamqaFileCheck, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
