package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingTrainDto;
import com.work.ppm.application.model.integratedmanager.OutTrainData;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingTrain;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingTrainService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmOutsourcingTrainService")
public class PpmOutsourcingTrainService extends BaseDaoSupper implements IPpmOutsourcingTrainService {
    /**
      * 实体类路径
      */
    private static String PPMOUTSOURCINGTRAIN = "com.work.ppm.application.model.integratedmanager.PpmOutsourcingTrain";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOutsourcingTrainDto ppmOutsourcingTrainDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOutsourcingTrainDto,PpmOutsourcingTrainDto.class);
        myBatisDao.selectPager(PPMOUTSOURCINGTRAIN, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOutsourcingTrain(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) {
        PpmOutsourcingTrain ppmOutsourcingTrain = JsonUtil.jsonToClass(ppmOutsourcingTrainDto, PpmOutsourcingTrain.class);
        Long id = getNextSequence(PpmOutsourcingTrain.class);

        // 如果需要请set 主键id
        //ppmOutsourcingTrain.setId(id.intValue());
        myBatisDao.executionPojo(PPMOUTSOURCINGTRAIN, ppmOutsourcingTrain, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOutsourcingTrainDto queryDetail(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) {
        PpmOutsourcingTrain ppmOutsourcingTrain = JsonUtil.jsonToClass(ppmOutsourcingTrainDto, PpmOutsourcingTrain.class);
        PpmOutsourcingTrain data = myBatisDao.selectClass(PPMOUTSOURCINGTRAIN, ppmOutsourcingTrain,
        "queryDetail");
        PpmOutsourcingTrainDto item = JsonUtil.jsonToClass(data, PpmOutsourcingTrainDto
        .class);
        return item;
    }

    @Override
    public int updatePpmOutsourcingTrain(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) {
        PpmOutsourcingTrain ppmOutsourcingTrain = JsonUtil.jsonToClass(ppmOutsourcingTrainDto, PpmOutsourcingTrain.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGTRAIN, ppmOutsourcingTrain, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmOutsourcingTrain(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) {
        PpmOutsourcingTrain ppmOutsourcingTrain = JsonUtil.jsonToClass(ppmOutsourcingTrainDto, PpmOutsourcingTrain.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGTRAIN, ppmOutsourcingTrain, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<OutTrainData> getOutTrainList(OutDataDto outDataDto) {
        Map param=new HashMap();
        param.put("jobid",outDataDto.getJobid());
        param.put("deptno",outDataDto.getDeptno());
        param.put("year",outDataDto.getYear());
        param.put("outsourceno",outDataDto.getOutsourceno());
        List<OutTrainData> trains=myBatisDao.selectListT(PPMOUTSOURCINGTRAIN, param,"getOutTrainList");
        return trains;
    }
}
