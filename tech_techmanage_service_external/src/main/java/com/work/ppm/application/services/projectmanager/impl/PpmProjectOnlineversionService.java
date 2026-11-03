package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.projectmanager.PpmProjectOnlineversion;
import com.work.ppm.application.dto.projectmanager.PpmProjectOnlineversionDto;
import com.work.ppm.application.services.projectmanager.IPpmProjectOnlineversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 描述
  */
@Service("ppmProjectOnlineversionService")
public class PpmProjectOnlineversionService extends BaseDaoSupper implements IPpmProjectOnlineversionService {
    /**
      * 实体类路径
      */
    private static String PPMPROJECTONLINEVERSION = "" +
            "com.work.ppm.application.model.projectmanager.PpmProjectOnlineversion";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmProjectOnlineversionDto ppmProjectOnlineversionDto, Pager pager) {
        Map<String,Object> map = JsonUtil.
                objectTOMap(ppmProjectOnlineversionDto,PpmProjectOnlineversionDto.class);
        myBatisDao.selectPager(PPMPROJECTONLINEVERSION, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmProjectOnlineversion(PpmProjectOnlineversionDto ppmProjectOnlineversionDto) {
        PpmProjectOnlineversion ppmProjectOnlineversion =
                JsonUtil.jsonToClass(ppmProjectOnlineversionDto, PpmProjectOnlineversion.class);
        Long id = getNextSequence(PpmProjectOnlineversion.class);

        // 如果需要请set 主键id
        ppmProjectOnlineversion.setId(id);
        myBatisDao.executionPojo(PPMPROJECTONLINEVERSION,
                ppmProjectOnlineversion, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmProjectOnlineversionDto queryDetail(PpmProjectOnlineversionDto ppmProjectOnlineversionDto) {
        PpmProjectOnlineversion ppmProjectOnlineversion =
                JsonUtil.jsonToClass(ppmProjectOnlineversionDto, PpmProjectOnlineversion.class);
        PpmProjectOnlineversion data = myBatisDao.selectClass(PPMPROJECTONLINEVERSION, ppmProjectOnlineversion,
        "queryDetail");
        PpmProjectOnlineversionDto item = JsonUtil.jsonToClass(data, PpmProjectOnlineversionDto
        .class);
        return item;
    }

    @Override
    public int updatePpmProjectOnlineversion(PpmProjectOnlineversionDto ppmProjectOnlineversionDto) {
        PpmProjectOnlineversion ppmProjectOnlineversion =
                JsonUtil.jsonToClass(ppmProjectOnlineversionDto, PpmProjectOnlineversion.class);
        return myBatisDao.executionPojo(PPMPROJECTONLINEVERSION,
                ppmProjectOnlineversion, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmProjectOnlineversion(PpmProjectOnlineversionDto ppmProjectOnlineversionDto) {
        PpmProjectOnlineversion ppmProjectOnlineversion =
                JsonUtil.jsonToClass(ppmProjectOnlineversionDto, PpmProjectOnlineversion.class);
        return myBatisDao.executionPojo(PPMPROJECTONLINEVERSION,
                ppmProjectOnlineversion, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public int addPpmProjectOnlineversionBatch(PpmProjectOnlineversionDto[] ppmProjectOnlineversionDtos) {
        int success = 0;
        for (int i = 0; i < ppmProjectOnlineversionDtos.length; i++) {
            addPpmProjectOnlineversion(ppmProjectOnlineversionDtos[i]);
            success++;
        }

        return success;
    }
}
