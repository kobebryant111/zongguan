package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingRiskResultDto;
import com.work.ppm.application.model.integratedmanager.OutRiskResultData;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingRiskResult;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingRiskResultService;
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
@Service("ppmOutsourcingRiskResultService")
public class PpmOutsourcingRiskResultService extends BaseDaoSupper implements IPpmOutsourcingRiskResultService {
    /**
      * 实体类路径
      */
    private static String PPMOUTSOURCINGRISKRESULT =  "com.work.ppm.application.model.integratedmanager.PpmOutsourcingRiskResult";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOutsourcingRiskResultDto, PpmOutsourcingRiskResultDto.class);
        myBatisDao.selectPager(PPMOUTSOURCINGRISKRESULT, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOutsourcingRiskResult(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) {
        PpmOutsourcingRiskResult ppmOutsourcingRiskResult = JsonUtil.jsonToClass(ppmOutsourcingRiskResultDto, PpmOutsourcingRiskResult.class);
        Long id = getNextSequence(PpmOutsourcingRiskResult.class);

        // 如果需要请set 主键id
        //ppmOutsourcingRiskResult.setId(id.intValue());
        myBatisDao.executionPojo(PPMOUTSOURCINGRISKRESULT, ppmOutsourcingRiskResult, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOutsourcingRiskResultDto queryDetail(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) {
        PpmOutsourcingRiskResult ppmOutsourcingRiskResult = JsonUtil.jsonToClass(ppmOutsourcingRiskResultDto, PpmOutsourcingRiskResult.class);
        PpmOutsourcingRiskResult data = myBatisDao.selectClass(PPMOUTSOURCINGRISKRESULT, ppmOutsourcingRiskResult,
        "queryDetail");
        PpmOutsourcingRiskResultDto item = JsonUtil.jsonToClass(data, PpmOutsourcingRiskResultDto
        .class);
        return item;
    }

    @Override
    public int updatePpmOutsourcingRiskResult(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) {
        PpmOutsourcingRiskResult ppmOutsourcingRiskResult = JsonUtil.jsonToClass(ppmOutsourcingRiskResultDto, PpmOutsourcingRiskResult.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGRISKRESULT, ppmOutsourcingRiskResult, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmOutsourcingRiskResult(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) {
        PpmOutsourcingRiskResult ppmOutsourcingRiskResult = JsonUtil.jsonToClass(ppmOutsourcingRiskResultDto, PpmOutsourcingRiskResult.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGRISKRESULT, ppmOutsourcingRiskResult, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<OutRiskResultData> getOutRiskResults(OutDataDto outDataDto) {
        Map param=new HashMap();
        param.put("jobid",outDataDto.getJobid());
        param.put("deptno",outDataDto.getDeptno());
        param.put("year",outDataDto.getYear());
        param.put("outsourceno",outDataDto.getOutsourceno());
        List<OutRiskResultData> riskResults= myBatisDao.selectListT(PPMOUTSOURCINGRISKRESULT, param,"getRiskResultData");
        if(riskResults.size()>0){
            return riskResults;
        }
        return null;

    }
}
