package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.PpmPwdHqmodelDto;
import com.work.ppm.application.model.projectmanager.PpmPwdHqmodel;
import com.work.ppm.application.services.projectmanager.IPpmPwdHqmodelService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
  * 密管密码获取模式
  */
@Service("ppmPwdHqmodelService")
public class PpmPwdHqmodelService extends BaseDaoSupper implements IPpmPwdHqmodelService {
    /**
      * 实体类路径
      */
    private static String PPMPWDHQMODEL = "com.work.ppm.application.model.projectmanager.PpmPwdHqmodel";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public PpmPwdHqmodelDto queryDetail(PpmPwdHqmodelDto ppmPwdHqmodelDto) {
        PpmPwdHqmodel ppmPwdHqmodel = JsonUtil.jsonToClass(ppmPwdHqmodelDto, PpmPwdHqmodel.class);
        PpmPwdHqmodel data = myBatisDao.selectClass(PPMPWDHQMODEL, ppmPwdHqmodel,
        "queryDetail");
        PpmPwdHqmodelDto item = JsonUtil.jsonToClass(data, PpmPwdHqmodelDto
        .class);
        return item;
    }


}
