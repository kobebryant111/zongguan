package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingSystemno;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingSystemnoDto;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingSystemnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 描述
  */
@Service("ppmOutsourcingSystemnoService")
public class PpmOutsourcingSystemnoService extends BaseDaoSupper implements IPpmOutsourcingSystemnoService {
    /**
      * 实体类路径
      */
    private static String PPMOUTSOURCINGSYSTEMNO = "" +
            "com.work.ppm.application.model.integratedmanager.PpmOutsourcingSystemno";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOutsourcingSystemnoDto,PpmOutsourcingSystemnoDto.class);
        myBatisDao.selectPager(PPMOUTSOURCINGSYSTEMNO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOutsourcingSystemno(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) {
        PpmOutsourcingSystemno ppmOutsourcingSystemno = JsonUtil.jsonToClass(ppmOutsourcingSystemnoDto, PpmOutsourcingSystemno.class);
        Long id = getNextSequence(PpmOutsourcingSystemno.class);

        // 如果需要请set 主键id
        //ppmOutsourcingSystemno.setId(id.intValue());
        myBatisDao.executionPojo(PPMOUTSOURCINGSYSTEMNO, ppmOutsourcingSystemno, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOutsourcingSystemnoDto queryDetail(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) {
        PpmOutsourcingSystemno ppmOutsourcingSystemno = JsonUtil.jsonToClass(ppmOutsourcingSystemnoDto, PpmOutsourcingSystemno.class);
        PpmOutsourcingSystemno data = myBatisDao.selectClass(PPMOUTSOURCINGSYSTEMNO, ppmOutsourcingSystemno,
        "queryDetail");
        PpmOutsourcingSystemnoDto item = JsonUtil.jsonToClass(data, PpmOutsourcingSystemnoDto
        .class);
        return item;
    }

    @Override
    public int updatePpmOutsourcingSystemno(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) {
        PpmOutsourcingSystemno ppmOutsourcingSystemno = JsonUtil.jsonToClass(ppmOutsourcingSystemnoDto, PpmOutsourcingSystemno.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGSYSTEMNO, ppmOutsourcingSystemno, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmOutsourcingSystemno(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) {
        PpmOutsourcingSystemno ppmOutsourcingSystemno = JsonUtil.jsonToClass(ppmOutsourcingSystemnoDto, PpmOutsourcingSystemno.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGSYSTEMNO, ppmOutsourcingSystemno, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
