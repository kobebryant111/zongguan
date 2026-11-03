package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendarHistory;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarHistoryDto;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
  *  考勤日历修改记录 接口实现类
  */
@Service("ppmKqCalendarHistoryService")
public class PpmKqCalendarHistoryService extends BaseDaoSupper implements IPpmKqCalendarHistoryService {
    /**
      * 实体类路径
      */
    private static String PPMKQCALENDARHISTORY = "com.work.ppm.application.model.integratedmanager.PpmKqCalendarHistory";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKqCalendarHistoryDto,PpmKqCalendarHistoryDto.class);
        myBatisDao.selectPager(PPMKQCALENDARHISTORY, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKqCalendarHistory(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) {
        PpmKqCalendarHistory ppmKqCalendarHistory = JsonUtil.jsonToClass(ppmKqCalendarHistoryDto, PpmKqCalendarHistory.class);
        ppmKqCalendarHistory.setId(getNextSequence(PpmKqCalendarHistory.class));
        myBatisDao.executionPojo(PPMKQCALENDARHISTORY, ppmKqCalendarHistory, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKqCalendarHistoryDto queryDetail(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) {
        PpmKqCalendarHistory ppmKqCalendarHistory = JsonUtil.jsonToClass(ppmKqCalendarHistoryDto, PpmKqCalendarHistory.class);
        PpmKqCalendarHistory data = myBatisDao.selectClass(PPMKQCALENDARHISTORY, ppmKqCalendarHistory,
        "queryDetail");
        PpmKqCalendarHistoryDto item = JsonUtil.jsonToClass(data, PpmKqCalendarHistoryDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKqCalendarHistory(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) {
        PpmKqCalendarHistory ppmKqCalendarHistory = JsonUtil.jsonToClass(ppmKqCalendarHistoryDto, PpmKqCalendarHistory.class);
        return myBatisDao.executionPojo(PPMKQCALENDARHISTORY, ppmKqCalendarHistory, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKqCalendarHistory(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) {
        PpmKqCalendarHistory ppmKqCalendarHistory = JsonUtil.jsonToClass(ppmKqCalendarHistoryDto, PpmKqCalendarHistory.class);
        return myBatisDao.executionPojo(PPMKQCALENDARHISTORY, ppmKqCalendarHistory, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
