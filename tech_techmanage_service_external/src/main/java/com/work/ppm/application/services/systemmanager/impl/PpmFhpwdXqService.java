package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.PpmFhpwdXqDto;
import com.work.ppm.application.model.systemmanager.PpmFhpwdXq;
import com.work.ppm.application.services.systemmanager.IPpmFhpwdXqService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分行密码权限
 */
@Service("ppmFhpwdXqService")
public class PpmFhpwdXqService extends BaseDaoSupper implements IPpmFhpwdXqService {
    /**
      * 实体类路径
      */
    private static String PPMFHPWDXQ = "com.work.ppm.application.model.systemmanager.PpmFhpwdXq";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public PpmFhpwdXqDto queryForCompid(String compId) {
        PpmFhpwdXq xq = new PpmFhpwdXq();
        xq.setCompid(compId);
        String sqlName = "queryDetail";
        PpmFhpwdXq item = myBatisDao.selectClass(PPMFHPWDXQ,xq,sqlName);
        PpmFhpwdXqDto dto = JsonUtil.jsonToClass(item, PpmFhpwdXqDto.class);
        return dto;
    }
}
