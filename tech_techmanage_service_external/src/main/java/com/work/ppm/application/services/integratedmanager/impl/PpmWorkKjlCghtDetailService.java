package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmWorkKjlCghtDetail;
import com.work.ppm.application.dto.integratedmanager.PpmWorkKjlCghtDetailDto;
import com.work.ppm.application.services.integratedmanager.IPpmWorkKjlCghtDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 描述
  */
@Service("ppmWorkKjlCghtDetailService")
public class PpmWorkKjlCghtDetailService extends BaseDaoSupper implements IPpmWorkKjlCghtDetailService {
    /**
      * 实体类路径
      */
    private static String PPMWORKKJLCGHTDETAIL = "" +
            "com.work.ppm.application.model.integratedmanager.PpmWorkKjlCghtDetail";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmWorkKjlCghtDetailDto,PpmWorkKjlCghtDetailDto.class);
        myBatisDao.selectPager(PPMWORKKJLCGHTDETAIL, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmWorkKjlCghtDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) {
        PpmWorkKjlCghtDetail ppmWorkKjlCghtDetail = JsonUtil.jsonToClass(ppmWorkKjlCghtDetailDto, PpmWorkKjlCghtDetail.class);
        Long id = getNextSequence(PpmWorkKjlCghtDetail.class);

        // 如果需要请set 主键id
        //ppmWorkKjlCghtDetail.setId(id.intValue());
        myBatisDao.executionPojo(PPMWORKKJLCGHTDETAIL, ppmWorkKjlCghtDetail, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmWorkKjlCghtDetailDto queryDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) {
        PpmWorkKjlCghtDetail ppmWorkKjlCghtDetail = JsonUtil.jsonToClass(ppmWorkKjlCghtDetailDto, PpmWorkKjlCghtDetail.class);
        PpmWorkKjlCghtDetail data = myBatisDao.selectClass(PPMWORKKJLCGHTDETAIL, ppmWorkKjlCghtDetail,
        "queryDetail");
        PpmWorkKjlCghtDetailDto item = JsonUtil.jsonToClass(data, PpmWorkKjlCghtDetailDto
        .class);
        return item;
    }

    @Override
    public int updatePpmWorkKjlCghtDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) {
        PpmWorkKjlCghtDetail ppmWorkKjlCghtDetail = JsonUtil.jsonToClass(ppmWorkKjlCghtDetailDto, PpmWorkKjlCghtDetail.class);
        return myBatisDao.executionPojo(PPMWORKKJLCGHTDETAIL, ppmWorkKjlCghtDetail, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmWorkKjlCghtDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) {
        PpmWorkKjlCghtDetail ppmWorkKjlCghtDetail = JsonUtil.jsonToClass(ppmWorkKjlCghtDetailDto, PpmWorkKjlCghtDetail.class);
        return myBatisDao.executionPojo(PPMWORKKJLCGHTDETAIL, ppmWorkKjlCghtDetail, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
