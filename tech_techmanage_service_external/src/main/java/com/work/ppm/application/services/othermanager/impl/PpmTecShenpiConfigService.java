package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.othermanager.PpmTecShenpiConfig;
import com.work.ppm.application.dto.othermanager.PpmTecShenpiConfigDto;
import com.work.ppm.application.services.othermanager.IPpmTecShenpiConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 描述
  */
@Service("ppmTecShenpiConfigService")
public class PpmTecShenpiConfigService extends BaseDaoSupper implements IPpmTecShenpiConfigService {
    /**
      * 实体类路径
      */
    private static String PPMTECSHENPICONFIG = "com.work.ppm.application.model.othermanager.PpmTecShenpiConfig";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmTecShenpiConfigDto ppmTecShenpiConfigDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmTecShenpiConfigDto,PpmTecShenpiConfigDto.class);
        myBatisDao.selectPager(PPMTECSHENPICONFIG, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmTecShenpiConfig(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) {
        PpmTecShenpiConfig ppmTecShenpiConfig = JsonUtil.jsonToClass(ppmTecShenpiConfigDto, PpmTecShenpiConfig.class);
        //Long id = getNextSequence(PpmTecShenpiConfig.class);

        // 如果需要请set 主键id
        //ppmTecShenpiConfig.setId(id.intValue());
        myBatisDao.executionPojo(PPMTECSHENPICONFIG, ppmTecShenpiConfig, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmTecShenpiConfigDto queryDetail(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) {
        PpmTecShenpiConfig ppmTecShenpiConfig = JsonUtil.jsonToClass(ppmTecShenpiConfigDto, PpmTecShenpiConfig.class);
        PpmTecShenpiConfig data = myBatisDao.selectClass(PPMTECSHENPICONFIG, ppmTecShenpiConfig,
        "queryDetail");
        PpmTecShenpiConfigDto item = JsonUtil.jsonToClass(data, PpmTecShenpiConfigDto
        .class);
        return item;
    }

    @Override
    public int updatePpmTecShenpiConfig(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) {
        PpmTecShenpiConfig ppmTecShenpiConfig = JsonUtil.jsonToClass(ppmTecShenpiConfigDto, PpmTecShenpiConfig.class);
        return myBatisDao.executionPojo(PPMTECSHENPICONFIG, ppmTecShenpiConfig, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmTecShenpiConfig(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) {
        PpmTecShenpiConfig ppmTecShenpiConfig = JsonUtil.jsonToClass(ppmTecShenpiConfigDto, PpmTecShenpiConfig.class);
        return myBatisDao.executionPojo(PPMTECSHENPICONFIG, ppmTecShenpiConfig, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public PpmTecShenpiConfigDto queryDetailByDeptno(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) {
        PpmTecShenpiConfig ppmTecShenpiConfig = JsonUtil.jsonToClass(ppmTecShenpiConfigDto, PpmTecShenpiConfig.class);
        PpmTecShenpiConfig data = myBatisDao.selectClass(PPMTECSHENPICONFIG, ppmTecShenpiConfig,
                "queryDetailByDeptno");
        PpmTecShenpiConfigDto item = JsonUtil.jsonToClass(data, PpmTecShenpiConfigDto
                .class);
        return item;
    }
}
