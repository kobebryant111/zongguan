package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.onlinemanager.PpmOnlineGnd;
import com.work.ppm.application.dto.onlinemanager.PpmOnlineGndDto;
import com.work.ppm.application.services.onlinemanager.IPpmOnlineGndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 描述
  */
@Service("ppmOnlineGndService")
public class PpmOnlineGndService extends BaseDaoSupper implements IPpmOnlineGndService {
    /**
      * 实体类路径
      */
    private static String PPMONLINEGND = "com.work.ppm.application.model.onlinemanager.PpmOnlineGnd";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOnlineGndDto ppmOnlineGndDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOnlineGndDto,PpmOnlineGndDto.class);
        myBatisDao.selectPager(PPMONLINEGND, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOnlineGnd(PpmOnlineGndDto ppmOnlineGndDto) {
        PpmOnlineGnd ppmOnlineGnd = JsonUtil.jsonToClass(ppmOnlineGndDto, PpmOnlineGnd.class);
        Long id = getNextSequence(PpmOnlineGnd.class);

        // 如果需要请set 主键id
        ppmOnlineGnd.setId(id);
        myBatisDao.executionPojo(PPMONLINEGND, ppmOnlineGnd, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOnlineGndDto queryDetail(PpmOnlineGndDto ppmOnlineGndDto) {
        PpmOnlineGnd ppmOnlineGnd = JsonUtil.jsonToClass(ppmOnlineGndDto, PpmOnlineGnd.class);
        PpmOnlineGnd data = myBatisDao.selectClass(PPMONLINEGND, ppmOnlineGnd,
        "queryDetail");
        PpmOnlineGndDto item = JsonUtil.jsonToClass(data, PpmOnlineGndDto
        .class);
        return item;
    }

    @Override
    public int updatePpmOnlineGnd(PpmOnlineGndDto ppmOnlineGndDto) {
        PpmOnlineGnd ppmOnlineGnd = JsonUtil.jsonToClass(ppmOnlineGndDto, PpmOnlineGnd.class);
        return myBatisDao.executionPojo(PPMONLINEGND, ppmOnlineGnd, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmOnlineGnd(PpmOnlineGndDto ppmOnlineGndDto) {
        PpmOnlineGnd ppmOnlineGnd = JsonUtil.jsonToClass(ppmOnlineGndDto, PpmOnlineGnd.class);
        return myBatisDao.executionPojo(PPMONLINEGND, ppmOnlineGnd, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
