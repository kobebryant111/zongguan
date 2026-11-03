package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingPrice;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingPriceDto;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 描述
  */
@Service("ppmOutsourcingPriceService")
public class PpmOutsourcingPriceService extends BaseDaoSupper implements IPpmOutsourcingPriceService {
    /**
      * 实体类路径
      */
    private static String PPMOUTSOURCINGPRICE = "com.work.ppm.application.model.integratedmanager.PpmOutsourcingPrice";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOutsourcingPriceDto ppmOutsourcingPriceDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOutsourcingPriceDto,PpmOutsourcingPriceDto.class);
        myBatisDao.selectPager(PPMOUTSOURCINGPRICE, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOutsourcingPrice(PpmOutsourcingPriceDto ppmOutsourcingPriceDto) {
        PpmOutsourcingPrice ppmOutsourcingPrice = JsonUtil.jsonToClass(ppmOutsourcingPriceDto, PpmOutsourcingPrice.class);
        Long id = getNextSequence(PpmOutsourcingPrice.class);

        // 如果需要请set 主键id
        //ppmOutsourcingPrice.setId(id.intValue());
        myBatisDao.executionPojo(PPMOUTSOURCINGPRICE, ppmOutsourcingPrice, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOutsourcingPriceDto queryDetail(PpmOutsourcingPriceDto ppmOutsourcingPriceDto) {
        PpmOutsourcingPrice ppmOutsourcingPrice = JsonUtil.jsonToClass(ppmOutsourcingPriceDto, PpmOutsourcingPrice.class);
        PpmOutsourcingPrice data = myBatisDao.selectClass(PPMOUTSOURCINGPRICE, ppmOutsourcingPrice,
        "queryDetail");
        PpmOutsourcingPriceDto item = JsonUtil.jsonToClass(data, PpmOutsourcingPriceDto
        .class);
        return item;
    }

    @Override
    public int updatePpmOutsourcingPrice(PpmOutsourcingPriceDto ppmOutsourcingPriceDto) {
        PpmOutsourcingPrice ppmOutsourcingPrice = JsonUtil.jsonToClass(ppmOutsourcingPriceDto, PpmOutsourcingPrice.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGPRICE, ppmOutsourcingPrice, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmOutsourcingPrice(PpmOutsourcingPriceDto ppmOutsourcingPriceDto) {
        PpmOutsourcingPrice ppmOutsourcingPrice = JsonUtil.jsonToClass(ppmOutsourcingPriceDto, PpmOutsourcingPrice.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGPRICE, ppmOutsourcingPrice, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
