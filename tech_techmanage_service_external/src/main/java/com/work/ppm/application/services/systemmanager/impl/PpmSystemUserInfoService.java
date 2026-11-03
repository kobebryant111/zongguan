package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.systemmanager.PpmSystemUserInfo;
import com.work.ppm.application.dto.systemmanager.PpmSystemUserInfoDto;
import com.work.ppm.application.services.systemmanager.IPpmSystemUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
  *  系统详情用户关系表 接口实现类
  */
@Service("ppmSystemUserInfoService")
public class PpmSystemUserInfoService extends BaseDaoSupper implements IPpmSystemUserInfoService {
    /**
      * 实体类路径
      */
    private static String PPMSYSTEMUSERINFO = "com.work.ppm.application.model.systemmanager.PpmSystemUserInfo";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmSystemUserInfoDto ppmSystemUserInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmSystemUserInfoDto,PpmSystemUserInfoDto.class);
        myBatisDao.selectPager(PPMSYSTEMUSERINFO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmSystemUserInfo(PpmSystemUserInfoDto ppmSystemUserInfoDto) {
        PpmSystemUserInfo ppmSystemUserInfo = JsonUtil.jsonToClass(ppmSystemUserInfoDto, PpmSystemUserInfo.class);
        Long id = getNextSequence(PpmSystemUserInfo.class);

        // 如果需要请set 主键id
        //ppmSystemUserInfo.setId(id);
        myBatisDao.executionPojo(PPMSYSTEMUSERINFO, ppmSystemUserInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmSystemUserInfoDto queryDetail(PpmSystemUserInfoDto ppmSystemUserInfoDto) {
        PpmSystemUserInfo ppmSystemUserInfo = JsonUtil.jsonToClass(ppmSystemUserInfoDto, PpmSystemUserInfo.class);
        PpmSystemUserInfo data = myBatisDao.selectClass(PPMSYSTEMUSERINFO, ppmSystemUserInfo, "queryDetail");
        PpmSystemUserInfoDto item = JsonUtil.jsonToClass(data, PpmSystemUserInfoDto
        .class);
        return item;
    }

    @Override
    public int updatePpmSystemUserInfo(PpmSystemUserInfoDto ppmSystemUserInfoDto) {
        PpmSystemUserInfo ppmSystemUserInfo = JsonUtil.jsonToClass(ppmSystemUserInfoDto, PpmSystemUserInfo.class);
        return myBatisDao.executionPojo(PPMSYSTEMUSERINFO, ppmSystemUserInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmSystemUserInfo(PpmSystemUserInfoDto ppmSystemUserInfoDto) {
        PpmSystemUserInfo ppmSystemUserInfo = JsonUtil.jsonToClass(ppmSystemUserInfoDto, PpmSystemUserInfo.class);
        return myBatisDao.executionPojo(PPMSYSTEMUSERINFO, ppmSystemUserInfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
