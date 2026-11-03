package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.dto.othermanager.PpmProcessWorkdayDto;
import com.work.ppm.application.model.othermanager.PpmProcessWorkday;
import com.work.ppm.application.services.othermanager.IPpmProcessWorkdayService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
  * 处理工作日
  */
@Service("ppmProcessWorkdayService")
public class PpmProcessWorkdayService extends BaseDaoSupper implements IPpmProcessWorkdayService {
    /**
      * 实体类路径
      */
    private static String PPMPROCESSWORKDAY = "com.work.ppm.application.model.othermanager.PpmProcessWorkday";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public PpmProcessWorkdayDto queryDetail(String unworkdate) {
        PpmProcessWorkday ppmProcessWorkday = new PpmProcessWorkday();
        ppmProcessWorkday.setUnworkdate(unworkdate);
        PpmProcessWorkday data = myBatisDao.selectClass(PPMPROCESSWORKDAY, ppmProcessWorkday,
        "queryDetail");
        PpmProcessWorkdayDto item = JsonUtil.jsonToClass(data, PpmProcessWorkdayDto
        .class);
        return item;
    }


}
