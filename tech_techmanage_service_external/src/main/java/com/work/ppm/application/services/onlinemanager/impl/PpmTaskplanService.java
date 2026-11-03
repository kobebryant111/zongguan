package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.application.model.onlinemanager.ext.PpmTaskplanInfo;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.onlinemanager.PpmTaskplan;
import com.work.ppm.application.dto.onlinemanager.PpmTaskplanDto;
import com.work.ppm.application.services.onlinemanager.IPpmTaskplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmTaskplanService")
public class PpmTaskplanService extends BaseDaoSupper implements IPpmTaskplanService {
    /**
      * 实体类路径
      */
    private static String PPMTASKPLAN = "com.work.ppm.application.model.onlinemanager.PpmTaskplan";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmTaskplanDto ppmTaskplanDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmTaskplanDto,PpmTaskplanDto.class);
        myBatisDao.selectPager(PPMTASKPLAN, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmTaskplan(PpmTaskplanDto ppmTaskplanDto) {
        PpmTaskplan ppmTaskplan = JsonUtil.jsonToClass(ppmTaskplanDto, PpmTaskplan.class);
        // 如果需要请set 主键id
        ppmTaskplan.setId(getNextSequence(PpmTaskplan.class));
        myBatisDao.executionPojo(PPMTASKPLAN, ppmTaskplan, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmTaskplanDto queryDetail(PpmTaskplanDto ppmTaskplanDto) {
        PpmTaskplan ppmTaskplan = JsonUtil.jsonToClass(ppmTaskplanDto, PpmTaskplan.class);
        PpmTaskplan data = myBatisDao.selectClass(PPMTASKPLAN, ppmTaskplan,
        "queryDetail");
        PpmTaskplanDto item = JsonUtil.jsonToClass(data, PpmTaskplanDto
        .class);
        return item;
    }

    @Override
    public int updatePpmTaskplan(PpmTaskplanDto ppmTaskplanDto) {
        PpmTaskplan ppmTaskplan = JsonUtil.jsonToClass(ppmTaskplanDto, PpmTaskplan.class);
        return myBatisDao.executionPojo(PPMTASKPLAN, ppmTaskplan, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmTaskplan(PpmTaskplanDto ppmTaskplanDto) {
        PpmTaskplan ppmTaskplan = JsonUtil.jsonToClass(ppmTaskplanDto, PpmTaskplan.class);
        return myBatisDao.executionPojo(PPMTASKPLAN, ppmTaskplan, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmTaskplanDto> queryTaskPlan(String planno) {
        Map<String ,Object> queryMap = new HashMap<>();
        queryMap.put("plannoppm", planno);
        List<PpmTaskplanInfo> ppmTaskplans = myBatisDao.selectListT(PPMTASKPLAN, queryMap, "queryTaskPlan");
        return JsonUtil.jsonToList(ppmTaskplans, PpmTaskplanDto.class);
    }
}
