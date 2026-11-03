package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiParams;
import com.work.ppm.application.dto.kpi.PpmKpiParamsDto;
import com.work.ppm.application.services.kpi.IPpmKpiParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiParamsService")
public class PpmKpiParamsService extends BaseDaoSupper implements IPpmKpiParamsService {
    /**
      * 实体类路径
      */
    private static String PPMKPIPARAMS = "com.work.ppm.application.model.kpi.PpmKpiParams";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmKpiParamsDto ppmKpiParamsDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiParamsDto,PpmKpiParamsDto.class);
        myBatisDao.selectPager(PPMKPIPARAMS, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiParams(PpmKpiParamsDto ppmKpiParamsDto) {
        PpmKpiParams ppmKpiParams = JsonUtil.jsonToClass(ppmKpiParamsDto, PpmKpiParams.class);
        Long id = getNextSequence(PpmKpiParams.class);
        // 如果需要请set 主键id
        ppmKpiParams.setId(id);
        ppmKpiParams.setCreatedate(new Date());
        myBatisDao.executionPojo(PPMKPIPARAMS, ppmKpiParams, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKpiParamsDto queryDetail(PpmKpiParamsDto ppmKpiParamsDto) {
        PpmKpiParams ppmKpiParams = JsonUtil.jsonToClass(ppmKpiParamsDto, PpmKpiParams.class);
        PpmKpiParams data = myBatisDao.selectClass(PPMKPIPARAMS, ppmKpiParams,
        "queryDetail");
        PpmKpiParamsDto item = JsonUtil.jsonToClass(data, PpmKpiParamsDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiParams(PpmKpiParamsDto ppmKpiParamsDto) {
        PpmKpiParams ppmKpiParams = JsonUtil.jsonToClass(ppmKpiParamsDto, PpmKpiParams.class);
        return myBatisDao.executionPojo(PPMKPIPARAMS, ppmKpiParams, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKpiParams(PpmKpiParamsDto ppmKpiParamsDto) {
        PpmKpiParams ppmKpiParams = JsonUtil.jsonToClass(ppmKpiParamsDto, PpmKpiParams.class);
        return myBatisDao.executionPojo(PPMKPIPARAMS, ppmKpiParams, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmKpiParamsDto> queryLists(PpmKpiParamsDto ppmKpiParamsDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiParamsDto,PpmKpiParamsDto.class);
        List<PpmKpiParamsDto> dtos=myBatisDao.selectListT(PPMKPIPARAMS, map,"queryPagerList");
        return dtos;

    }
}
