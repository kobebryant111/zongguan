package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.model.kpi.PpmKpiPm;
import com.work.ppm.application.model.kpi.PpmKpiPmDetail;
import com.work.ppm.application.services.kpi.IPpmKpiPm2Service;
import com.work.ppm.application.services.kpi.IPpmKpiPmDetailService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiBalanceDeduce;
import com.work.ppm.application.dto.kpi.PpmKpiBalanceDeduceDto;
import com.work.ppm.application.services.kpi.IPpmKpiBalanceDeduceService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiBalanceDeduceService")
public class PpmKpiBalanceDeduceService extends BaseDaoSupper implements IPpmKpiBalanceDeduceService {
    /**
      * 实体类路径
      */
    private static String PPMKPIBALANCEDEDUCE = "com.work.ppm.application.model.kpi.PpmKpiBalanceDeduce";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 考核指标表接口
     */
    @Autowired
    private IPpmKpiPmDetailService ppmKpiPmDetailService;

    /**
     * 2接口
     */
    @Autowired
    private IPpmKpiPm2Service ppmKpiPm2Service;

    @Override
    public void queryPager(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiBalanceDeduceDto,PpmKpiBalanceDeduceDto.class);
        myBatisDao.selectPager(PPMKPIBALANCEDEDUCE, map,pager,"queryPagerList");
    }
    @Override
    public int addDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto){
        int i=addPpmKpiBalanceDeduce(ppmKpiBalanceDeduceDto);
        setBalanceValue(ppmKpiBalanceDeduceDto);
        return i;
    }

    @Override
    public int updateDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto){
        int i=updatePpmKpiBalanceDeduce(ppmKpiBalanceDeduceDto);
        setBalanceValue(ppmKpiBalanceDeduceDto);
        return i;
    }

    @Override
    public int deleteDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto){
        int i=deletePpmKpiBalanceDeduce(ppmKpiBalanceDeduceDto);
        setBalanceValue(ppmKpiBalanceDeduceDto);
        return i;
    }
    @Override
    @Transactional("defaultTx")
    public int addPpmKpiBalanceDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) {
        PpmKpiBalanceDeduce ppmKpiBalanceDeduce = JsonUtil.jsonToClass(ppmKpiBalanceDeduceDto,
                PpmKpiBalanceDeduce.class);
        Long id = getNextSequence(PpmKpiBalanceDeduce.class);
        // 如果需要请set 主键id
        ppmKpiBalanceDeduce.setId(id);
        ppmKpiBalanceDeduce.setRegtime(new Date());
        myBatisDao.executionPojo(PPMKPIBALANCEDEDUCE, ppmKpiBalanceDeduce, IMyBatisDao.SQL.INSTER,
                "addPojo");
        return 1;
    }
    @Override
    public PpmKpiBalanceDeduceDto queryDetail(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) {
        PpmKpiBalanceDeduce ppmKpiBalanceDeduce = JsonUtil.jsonToClass(ppmKpiBalanceDeduceDto,
                PpmKpiBalanceDeduce.class);
        PpmKpiBalanceDeduce data = myBatisDao.selectClass(PPMKPIBALANCEDEDUCE, ppmKpiBalanceDeduce,
        "queryDetail");
        PpmKpiBalanceDeduceDto item = JsonUtil.jsonToClass(data, PpmKpiBalanceDeduceDto
        .class);
        return item;
    }

    @Override
    @Transactional("defaultTx")
    public int updatePpmKpiBalanceDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) {
        PpmKpiBalanceDeduce ppmKpiBalanceDeduce = JsonUtil.jsonToClass(ppmKpiBalanceDeduceDto,
                PpmKpiBalanceDeduce.class);
        myBatisDao.executionPojo(PPMKPIBALANCEDEDUCE, ppmKpiBalanceDeduce, IMyBatisDao.SQL.UPDATE,
                "updatePojo");
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int deletePpmKpiBalanceDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) {
        PpmKpiBalanceDeduce ppmKpiBalanceDeduce = JsonUtil.jsonToClass(ppmKpiBalanceDeduceDto,
                PpmKpiBalanceDeduce.class);
        myBatisDao.executionPojo(PPMKPIBALANCEDEDUCE, ppmKpiBalanceDeduce, IMyBatisDao.SQL.DELETE,"deleteById");
        return 1;
    }

    @Override
    public List<PpmKpiBalanceDeduceDto> getBalanceDeduceItem(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) {
        PpmKpiBalanceDeduce ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiBalanceDeduceDto,
                PpmKpiBalanceDeduce.class);
        List<PpmKpiBalanceDeduce> data = myBatisDao.selectListT(PPMKPIBALANCEDEDUCE, ppmKpiPmDetail,
                "queryPagerList");
        List<PpmKpiBalanceDeduceDto> item = JsonUtil.jsonToList(data, PpmKpiBalanceDeduceDto.class);
        return item;
    }

    @Override
    public double getSummaryValue(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) {
        double value=0L;
        PpmKpiBalanceDeduce ppmKpiPmDetail = JsonUtil.jsonToClass(ppmKpiBalanceDeduceDto,
                PpmKpiBalanceDeduce.class);
        PpmKpiBalanceDeduce data = myBatisDao.selectClass(PPMKPIBALANCEDEDUCE, ppmKpiPmDetail,
                "querySummaryValue");
        if(StringUtil.isNotEmpty(data)){
            value=data.getValue();
        }
        return value;
    }
    @Override
    public int setBalanceValue(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto){
        PpmKpiPmDetail ppmKpiPmDetail=new PpmKpiPmDetail();
        ppmKpiPmDetail.setTyear(ppmKpiBalanceDeduceDto.getTyear());
        ppmKpiPmDetail.setEid(ppmKpiBalanceDeduceDto.getEid());
        ppmKpiPmDetail.setItem(ppmKpiBalanceDeduceDto.getItem());
        ppmKpiPmDetail.setPlan(ppmKpiBalanceDeduceDto.getPlan());
        PpmKpiPmDetail detail=ppmKpiPmDetailService.queryPpmKpiPmDetailByCondition(ppmKpiPmDetail);
        if(StringUtil.isNotEmpty(detail)){
            double tscore=getSummaryValue(ppmKpiBalanceDeduceDto);
            detail.setTscore(tscore);
            ppmKpiPmDetailService.updatePpmKpiPmDetail(detail);

            //计算分数
            PpmKpiPm ppmKpiPm=new PpmKpiPm();
            ppmKpiPm.setTyear(detail.getTyear());
            ppmKpiPm.setEid(detail.getEid());
            ppmKpiPm2Service.summaryScoreForBalance(ppmKpiPm);
        }
        return 1;
    }
}
