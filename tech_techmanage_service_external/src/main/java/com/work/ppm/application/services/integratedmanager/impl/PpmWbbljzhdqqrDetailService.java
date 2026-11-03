package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmWbbljzhdqqrDetail;
import com.work.ppm.application.dto.integratedmanager.PpmWbbljzhdqqrDetailDto;
import com.work.ppm.application.services.integratedmanager.IPpmWbbljzhdqqrDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
  * 外包堡垒机账号到期确认详情
  */
@Service("ppmWbbljzhdqqrDetailService")
public class PpmWbbljzhdqqrDetailService extends BaseDaoSupper implements IPpmWbbljzhdqqrDetailService {
    /**
      * 实体类路径
      */
    private static String PPMWBBLJZHDQQRDETAIL =
            "com.work.ppm.application.model.integratedmanager.PpmWbbljzhdqqrDetail";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmWbbljzhdqqrDetailDto,
                PpmWbbljzhdqqrDetailDto.class);
        myBatisDao.selectPager(PPMWBBLJZHDQQRDETAIL, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmWbbljzhdqqrDetail(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto) {
        PpmWbbljzhdqqrDetail ppmWbbljzhdqqrDetail = JsonUtil.jsonToClass(ppmWbbljzhdqqrDetailDto,
                PpmWbbljzhdqqrDetail.class);
        //Long id = getNextSequence(PpmWbbljzhdqqrDetail.class);
        // 如果需要请set 主键id
        //ppmWbbljzhdqqrDetail.setId(id.intValue());
        myBatisDao.executionPojo(PPMWBBLJZHDQQRDETAIL, ppmWbbljzhdqqrDetail, IMyBatisDao.SQL.INSTER,
                "addPojo");
        return 1;
    }
    @Override
    public PpmWbbljzhdqqrDetailDto queryDetail(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto) {
        PpmWbbljzhdqqrDetail ppmWbbljzhdqqrDetail = JsonUtil.jsonToClass(ppmWbbljzhdqqrDetailDto,
                PpmWbbljzhdqqrDetail.class);
        PpmWbbljzhdqqrDetail data = myBatisDao.selectClass(PPMWBBLJZHDQQRDETAIL, ppmWbbljzhdqqrDetail,
        "queryDetail");
        PpmWbbljzhdqqrDetailDto item = JsonUtil.jsonToClass(data, PpmWbbljzhdqqrDetailDto
        .class);
        return item;
    }

    @Override
    public int updatePpmWbbljzhdqqrDetail(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto) {
        PpmWbbljzhdqqrDetail ppmWbbljzhdqqrDetail = JsonUtil.jsonToClass(ppmWbbljzhdqqrDetailDto,
                PpmWbbljzhdqqrDetail.class);
        return myBatisDao.executionPojo(PPMWBBLJZHDQQRDETAIL, ppmWbbljzhdqqrDetail, IMyBatisDao.SQL.UPDATE,
                "updatePojo");
    }

    @Override
    public int deletePpmWbbljzhdqqrDetail(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto) {
        PpmWbbljzhdqqrDetail ppmWbbljzhdqqrDetail = JsonUtil.jsonToClass(ppmWbbljzhdqqrDetailDto,
                PpmWbbljzhdqqrDetail.class);
        return myBatisDao.executionPojo(PPMWBBLJZHDQQRDETAIL, ppmWbbljzhdqqrDetail,
                IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public void updateUseStatus(Map param){
        myBatisDao.executionPojo(PPMWBBLJZHDQQRDETAIL, param, IMyBatisDao.SQL.UPDATE,
                "updateUseState");
    }
}
