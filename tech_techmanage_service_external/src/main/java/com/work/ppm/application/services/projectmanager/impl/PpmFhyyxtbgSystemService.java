package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.PpmFhyyxtbgSystemDto;
import com.work.ppm.application.model.projectmanager.PpmFhyyxtbgSystem;
import com.work.ppm.application.model.projectmanager.ext.PpmFhyyxtbgSystemInfo;
import com.work.ppm.application.services.projectmanager.IPpmFhyyxtbgSystemService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmFhyyxtbgSystemService")
public class PpmFhyyxtbgSystemService extends BaseDaoSupper implements IPpmFhyyxtbgSystemService {
    /**
      * 实体类路径
      */
    private static String PPMFHYYXTBGSYSTEM = "com.work.ppm.application.model.projectmanager.PpmFhyyxtbgSystem";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public int addPpmFhyyxtbgSystem( List<PpmFhyyxtbgSystemDto> sysList) {
        if(null == sysList && sysList.isEmpty() ) {
            return -1;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("refinstanceid",sysList.get(0).getRefinstanceid());
        // 先删除 后添加
        myBatisDao.executionMap(PPMFHYYXTBGSYSTEM, map, IMyBatisDao.SQL.DELETE,"deleteById");

        sysList.stream().forEach(item -> {
            long id = getNextSequence(PpmFhyyxtbgSystem.class);
            item.setId(id);
        });

        myBatisDao.executionPojo(PPMFHYYXTBGSYSTEM, sysList, IMyBatisDao.SQL.INSTER, "batchAdd");
        return 1;
    }

    @Override
    public List<PpmFhyyxtbgSystemDto> queryDetail(String refinstanceid) {
        PpmFhyyxtbgSystem system = new PpmFhyyxtbgSystem();
        system.setRefinstanceid(refinstanceid);
        List<PpmFhyyxtbgSystemInfo> list =  myBatisDao.selectListT(PPMFHYYXTBGSYSTEM, system, "getListSysInfo");
        List<PpmFhyyxtbgSystemDto> dtos = JsonUtil.jsonToList(list, PpmFhyyxtbgSystemDto.class);
        return dtos;
    }

    @Override
    public void updateFhyyxtbgSystem(PpmFhyyxtbgSystemDto sysDto) {
        PpmFhyyxtbgSystem system = JsonUtil.jsonToClass(sysDto, PpmFhyyxtbgSystem.class);
        myBatisDao.executionPojo(PPMFHYYXTBGSYSTEM, system, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }


}
