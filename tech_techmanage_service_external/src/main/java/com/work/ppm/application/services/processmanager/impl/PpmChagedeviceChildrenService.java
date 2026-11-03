package com.work.ppm.application.services.processmanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.processmanager.PpmChagedeviceChildren;
import com.work.ppm.application.dto.processmanager.PpmChagedeviceChildrenDto;
import com.work.ppm.application.services.processmanager.IPpmChagedeviceChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * 变更设备名称子表
  */
@Service("ppmChagedeviceChildrenService")
public class PpmChagedeviceChildrenService extends BaseDaoSupper implements IPpmChagedeviceChildrenService {
    /**
      * 实体类路径
      */
    private static String PPMCHAGEDEVICECHILDREN = "" +
            "com.work.ppm.application.model.processmanager.PpmChagedeviceChildren";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmChagedeviceChildren(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) {
        PpmChagedeviceChildren ppmChagedeviceChildren = JsonUtil.jsonToClass(ppmChagedeviceChildrenDto, PpmChagedeviceChildren.class);
        Long id = getNextSequence(PpmChagedeviceChildren.class);

        // 如果需要请set 主键id
        //ppmChagedeviceChildren.setId(id.intValue());
        myBatisDao.executionPojo(PPMCHAGEDEVICECHILDREN, ppmChagedeviceChildren, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public List<PpmChagedeviceChildrenDto> queryList(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) {
        AssertUtil.notNull(ppmChagedeviceChildrenDto.getMid(), "[mid]不能为空");
        PpmChagedeviceChildren ppmChagedeviceChildren = JsonUtil.jsonToClass(ppmChagedeviceChildrenDto, PpmChagedeviceChildren.class);
        List<PpmChagedeviceChildren> data = myBatisDao.selectListT(PPMCHAGEDEVICECHILDREN, ppmChagedeviceChildren,
        "getList");
        List<PpmChagedeviceChildrenDto> item = JsonUtil.jsonToList(data, PpmChagedeviceChildrenDto
        .class);
        return item;
    }

    @Override
    public List<PpmChagedeviceChildrenDto> queryListAll() {
        List<PpmChagedeviceChildren> list = myBatisDao.selectListT(PPMCHAGEDEVICECHILDREN, null,"getList");
        List<PpmChagedeviceChildrenDto> dtos = JsonUtil.jsonToList(list, PpmChagedeviceChildrenDto
                .class);
        return dtos;
    }

    @Override
    public int updatePpmChagedeviceChildren(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) {
        PpmChagedeviceChildren ppmChagedeviceChildren = JsonUtil.jsonToClass(ppmChagedeviceChildrenDto, PpmChagedeviceChildren.class);
        return myBatisDao.executionPojo(PPMCHAGEDEVICECHILDREN, ppmChagedeviceChildren, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmChagedeviceChildren(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) {
        PpmChagedeviceChildren ppmChagedeviceChildren = JsonUtil.jsonToClass(ppmChagedeviceChildrenDto, PpmChagedeviceChildren.class);
        return myBatisDao.executionPojo(PPMCHAGEDEVICECHILDREN, ppmChagedeviceChildren, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
