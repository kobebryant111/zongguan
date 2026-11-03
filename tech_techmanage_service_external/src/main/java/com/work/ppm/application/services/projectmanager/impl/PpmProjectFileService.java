package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.projectmanager.PpmProjectFile;
import com.work.ppm.application.dto.projectmanager.PpmProjectFileDto;
import com.work.ppm.application.services.projectmanager.IPpmProjectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * 描述
  */
@Service("ppmProjectFileService")
public class PpmProjectFileService extends BaseDaoSupper implements IPpmProjectFileService {
    /**
      * 实体类路径
      */
    private static String PPMPROJECTFILE = "com.work.ppm.application.model.projectmanager.PpmProjectFile";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmProjectFileDto ppmProjectFileDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmProjectFileDto,PpmProjectFileDto.class);
        myBatisDao.selectPager(PPMPROJECTFILE, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmProjectFile(PpmProjectFileDto ppmProjectFileDto) {
        PpmProjectFile ppmProjectFile = JsonUtil.jsonToClass(ppmProjectFileDto, PpmProjectFile.class);
        Long id = getNextSequence(PpmProjectFile.class);

        // 如果需要请set 主键id
        ppmProjectFile.setId(id);
        myBatisDao.executionPojo(PPMPROJECTFILE, ppmProjectFile, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmProjectFileDto queryDetail(PpmProjectFileDto ppmProjectFileDto) {
        PpmProjectFile ppmProjectFile = JsonUtil.jsonToClass(ppmProjectFileDto, PpmProjectFile.class);
        PpmProjectFile data = myBatisDao.selectClass(PPMPROJECTFILE, ppmProjectFile,
        "queryDetail");
        PpmProjectFileDto item = JsonUtil.jsonToClass(data, PpmProjectFileDto
        .class);
        return item;
    }

    @Override
    public int updatePpmProjectFile(PpmProjectFileDto ppmProjectFileDto) {
        PpmProjectFile ppmProjectFile = JsonUtil.jsonToClass(ppmProjectFileDto, PpmProjectFile.class);
        return myBatisDao.executionPojo(PPMPROJECTFILE, ppmProjectFile, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmProjectFile(PpmProjectFileDto ppmProjectFileDto) {
        PpmProjectFile ppmProjectFile = JsonUtil.jsonToClass(ppmProjectFileDto, PpmProjectFile.class);
        return myBatisDao.executionPojo(PPMPROJECTFILE, ppmProjectFile, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
