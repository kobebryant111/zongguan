package com.work.ppm.application.services.processmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.processmanager.PpmChagedeviceMaster;
import com.work.ppm.application.dto.processmanager.PpmChagedeviceMasterDto;
import com.work.ppm.application.services.processmanager.IPpmChagedeviceMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
  * 变更设备名称主表
  */
@Service("ppmChagedeviceMasterService")
public class PpmChagedeviceMasterService extends BaseDaoSupper implements IPpmChagedeviceMasterService {
    /**
      * 实体类路径
      */
    private static String PPMCHAGEDEVICEMASTER = "com.work.ppm.application.model.processmanager.PpmChagedeviceMaster";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmChagedeviceMasterDto ppmChagedeviceMasterDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmChagedeviceMasterDto,PpmChagedeviceMasterDto.class);
        myBatisDao.selectPager(PPMCHAGEDEVICEMASTER, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmChagedeviceMaster(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) {
        PpmChagedeviceMaster ppmChagedeviceMaster = JsonUtil.jsonToClass(ppmChagedeviceMasterDto, PpmChagedeviceMaster.class);
        Long id = getNextSequence(PpmChagedeviceMaster.class);

        // 如果需要请set 主键id
        //ppmChagedeviceMaster.setId(id.intValue());
        myBatisDao.executionPojo(PPMCHAGEDEVICEMASTER, ppmChagedeviceMaster, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmChagedeviceMasterDto queryDetail(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) {
        PpmChagedeviceMaster ppmChagedeviceMaster = JsonUtil.jsonToClass(ppmChagedeviceMasterDto, PpmChagedeviceMaster.class);
        PpmChagedeviceMaster data = myBatisDao.selectClass(PPMCHAGEDEVICEMASTER, ppmChagedeviceMaster,
        "queryDetail");
        PpmChagedeviceMasterDto item = JsonUtil.jsonToClass(data, PpmChagedeviceMasterDto
        .class);
        return item;
    }

    @Override
    public int updatePpmChagedeviceMaster(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) {
        PpmChagedeviceMaster ppmChagedeviceMaster = JsonUtil.jsonToClass(ppmChagedeviceMasterDto, PpmChagedeviceMaster.class);
        return myBatisDao.executionPojo(PPMCHAGEDEVICEMASTER, ppmChagedeviceMaster, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmChagedeviceMaster(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) {
        PpmChagedeviceMaster ppmChagedeviceMaster = JsonUtil.jsonToClass(ppmChagedeviceMasterDto, PpmChagedeviceMaster.class);
        return myBatisDao.executionPojo(PPMCHAGEDEVICEMASTER, ppmChagedeviceMaster, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmChagedeviceMasterDto> queryList() {
        List<PpmChagedeviceMaster> list = myBatisDao.selectListT(PPMCHAGEDEVICEMASTER, null, "getList");
        List<PpmChagedeviceMasterDto> dtos = JsonUtil.jsonToList(list, PpmChagedeviceMasterDto.class);
        return dtos;
    }
}
