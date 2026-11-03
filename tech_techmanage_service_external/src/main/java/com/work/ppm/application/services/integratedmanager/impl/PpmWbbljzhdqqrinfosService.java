package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmWbbljzhdqqrinfos;
import com.work.ppm.application.dto.integratedmanager.PpmWbbljzhdqqrinfosDto;
import com.work.ppm.application.services.integratedmanager.IPpmWbbljzhdqqrinfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 堡垒机用户报表-外包账号
  */
@Service("ppmWbbljzhdqqrinfosService")
public class PpmWbbljzhdqqrinfosService extends BaseDaoSupper implements IPpmWbbljzhdqqrinfosService {
    /**
      * 实体类路径
      */
    private static String PPMWBBLJZHDQQRINFOS =
            "com.work.ppm.application.model.integratedmanager.PpmWbbljzhdqqrinfos";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmWbbljzhdqqrinfosDto,PpmWbbljzhdqqrinfosDto.class);
        myBatisDao.selectPager(PPMWBBLJZHDQQRINFOS, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmWbbljzhdqqrinfos(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto) {
        PpmWbbljzhdqqrinfos ppmWbbljzhdqqrinfos = JsonUtil.jsonToClass(ppmWbbljzhdqqrinfosDto,
                PpmWbbljzhdqqrinfos.class);
        //Long id = getNextSequence(PpmWbbljzhdqqrinfos.class);
        // 如果需要请set 主键id
        //ppmWbbljzhdqqrinfos.setId(id.intValue());
        myBatisDao.executionPojo(PPMWBBLJZHDQQRINFOS, ppmWbbljzhdqqrinfos, IMyBatisDao.SQL.INSTER,
                "addPojo");
        return 1;
    }
    @Override
    public PpmWbbljzhdqqrinfosDto queryDetail(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto) {
        PpmWbbljzhdqqrinfos ppmWbbljzhdqqrinfos = JsonUtil.jsonToClass(ppmWbbljzhdqqrinfosDto,
                PpmWbbljzhdqqrinfos.class);
        PpmWbbljzhdqqrinfos data = myBatisDao.selectClass(PPMWBBLJZHDQQRINFOS, ppmWbbljzhdqqrinfos,
        "queryDetail");
        PpmWbbljzhdqqrinfosDto item = JsonUtil.jsonToClass(data, PpmWbbljzhdqqrinfosDto
        .class);
        return item;
    }

    @Override
    public int updatePpmWbbljzhdqqrinfos(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto) {
        PpmWbbljzhdqqrinfos ppmWbbljzhdqqrinfos = JsonUtil.jsonToClass(ppmWbbljzhdqqrinfosDto,
                PpmWbbljzhdqqrinfos.class);
        return myBatisDao.executionPojo(PPMWBBLJZHDQQRINFOS, ppmWbbljzhdqqrinfos,
                IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmWbbljzhdqqrinfos(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto) {
        PpmWbbljzhdqqrinfos ppmWbbljzhdqqrinfos = JsonUtil.jsonToClass(ppmWbbljzhdqqrinfosDto,
                PpmWbbljzhdqqrinfos.class);
        return myBatisDao.executionPojo(PPMWBBLJZHDQQRINFOS, ppmWbbljzhdqqrinfos,
                IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public void updateUseStatus(Map param){
        myBatisDao.executionPojo(PPMWBBLJZHDQQRINFOS, param, IMyBatisDao.SQL.DELETE,
                "updateUseState");
    }
}
