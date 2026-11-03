package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.onlinemanager.PpmProductionDay;
import com.work.ppm.application.dto.onlinemanager.PpmProductionDayDto;
import com.work.ppm.application.services.onlinemanager.IPpmProductionDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmProductionDayService")
public class PpmProductionDayService extends BaseDaoSupper implements IPpmProductionDayService {
    /**
      * 实体类路径
      */
    private static String PPMPRODUCTIONDAY = "com.work.ppm.application.model.onlinemanager.PpmProductionDay";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmProductionDayDto ppmProductionDayDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmProductionDayDto, PpmProductionDayDto.class);
        myBatisDao.selectPager(PPMPRODUCTIONDAY, map,pager,"queryPagerList");
    }

    @Override
    public PpmProductionDayDto queryDetail(PpmProductionDayDto ppmProductionDayDto) {
        PpmProductionDay ppmProductionDay = JsonUtil.jsonToClass(ppmProductionDayDto, PpmProductionDay.class);
        PpmProductionDay data = myBatisDao.selectClass(PPMPRODUCTIONDAY, ppmProductionDay,
        "queryDetail");
        PpmProductionDayDto item = JsonUtil.jsonToClass(data, PpmProductionDayDto
        .class);
        return item;
    }

    @Override
    public List<PpmProductionDayDto> queryDayList(String applyDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("tdate", applyDate);
        List<PpmProductionDay> dayList = myBatisDao.selectListT(PPMPRODUCTIONDAY, map,
                "queryProductionDay");
        List<PpmProductionDayDto> items = JsonUtil.jsonToList(dayList, PpmProductionDayDto.class);
        return items;
    }

    @Override
    public PpmProductionDayDto queryChangeDate(String date) {
        Map<String, Object> map = new HashMap<>();
        map.put("productDate", date);
        PpmProductionDay item = myBatisDao.selectClass(PPMPRODUCTIONDAY, map, "queryChangeDate");
        PpmProductionDayDto dto = JsonUtil.jsonToClass(item, PpmProductionDayDto.class);
        return dto;
    }

}
