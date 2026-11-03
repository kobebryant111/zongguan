package com.work.ppm.application.services.kpi.impl;

import com.esotericsoftware.minlog.Log;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailDto;
import com.work.ppm.application.dto.kpi.PpmKpiPostevaluationInfoDto;
import com.work.ppm.application.model.kpi.ext.PpmKpiPostevaluationInfo;
import com.work.ppm.application.services.kpi.IPpmKpiPm1Service;
import com.work.ppm.application.services.kpi.IPpmKpiPmDetailService;
import com.work.ppm.application.services.kpi.IPpmKpiPmService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiPostevaluation;
import com.work.ppm.application.dto.kpi.PpmKpiPostevaluationDto;
import com.work.ppm.application.services.kpi.IPpmKpiPostevaluationService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiPostevaluationService")
public class PpmKpiPostevaluationService extends BaseDaoSupper implements IPpmKpiPostevaluationService {
    /**
      * 实体类路径
      */
    private static String PPMKPIPOSTEVALUATION = "com.work.ppm.application.model.kpi.PpmKpiPostevaluation";
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
     * 考核主表接口
     */
    @Autowired
    private IPpmKpiPmService ppmKpiPmService;


    /**
     * 考核主表接口
     */
    @Autowired
    private IPpmKpiPm1Service ppmKpiPm1Service;

    @Override
    public void queryPager(PpmKpiPostevaluationDto ppmKpiPostevaluationDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPostevaluationDto,PpmKpiPostevaluationDto.class);
        myBatisDao.selectPager(PPMKPIPOSTEVALUATION, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        PpmKpiPostevaluation ppmKpiPostevaluation = JsonUtil.jsonToClass(
                ppmKpiPostevaluationDto, PpmKpiPostevaluation.class);
        Long id = getNextSequence(PpmKpiPostevaluation.class);

        // 如果需要请set 主键id
        ppmKpiPostevaluation.setId(id);
        ppmKpiPostevaluation.setCreatetime(new Date());
        Log.info("id:{}",id+"");
        myBatisDao.executionPojo(PPMKPIPOSTEVALUATION,
                ppmKpiPostevaluation, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKpiPostevaluationDto queryDetail(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        PpmKpiPostevaluation ppmKpiPostevaluation = JsonUtil.jsonToClass(
                ppmKpiPostevaluationDto, PpmKpiPostevaluation.class);
        PpmKpiPostevaluation data = myBatisDao.selectClass(PPMKPIPOSTEVALUATION, ppmKpiPostevaluation,
        "queryDetail");
        PpmKpiPostevaluationDto item = JsonUtil.jsonToClass(data, PpmKpiPostevaluationDto
        .class);
        return item;
    }

    @Override
    @Transactional("defaultTx")
   public int updatePpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        PpmKpiPostevaluation ppmKpiPostevaluation = JsonUtil.jsonToClass(
                ppmKpiPostevaluationDto, PpmKpiPostevaluation.class);
        return myBatisDao.executionPojo(PPMKPIPOSTEVALUATION,
                ppmKpiPostevaluation, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        PpmKpiPostevaluation ppmKpiPostevaluation = JsonUtil.jsonToClass(
                ppmKpiPostevaluationDto, PpmKpiPostevaluation.class);
        return myBatisDao.executionPojo(PPMKPIPOSTEVALUATION,
                ppmKpiPostevaluation, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public long queryKpiPostevaluations(Map param) {
        long count=0L;
        List<PpmKpiPostevaluation> data = myBatisDao.selectListT(PPMKPIPOSTEVALUATION, param,
                "getList");
        List<PpmKpiPostevaluationDto> item = JsonUtil.jsonToList(data, PpmKpiPostevaluationDto
                .class);
        if(item.size()>0){
            count=item.size();
        }
        return count;
    }

    @Override
    public List<PpmKpiPostevaluationDto> queryPpmKpiPostevaluationInfosByCondition(
            PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(
                ppmKpiPostevaluationDto,PpmKpiPostevaluationDto.class);
        List<PpmKpiPostevaluation> lists = myBatisDao.selectListT(
                PPMKPIPOSTEVALUATION, map,"queryPagerList");
        List<PpmKpiPostevaluationDto> item = JsonUtil.jsonToList(lists, PpmKpiPostevaluationDto.class);
        return item;
    }

    @Override
    public List<PpmKpiPostevaluationInfoDto> queryPpmKpiPostevaluationInfos(
            PpmKpiPostevaluationInfoDto ppmKpiPostevaluationInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPostevaluationInfoDto,
                PpmKpiPostevaluationInfoDto.class);
        List<PpmKpiPostevaluationInfo> lists = myBatisDao.selectListT(
                PPMKPIPOSTEVALUATION, map,"getPpmKpiPostevaluations");
        List<PpmKpiPostevaluationInfoDto> item = JsonUtil.jsonToList(lists,
                PpmKpiPostevaluationInfoDto.class);
        return item;
    }

    @Override
    public Double getAvgScore(Map param){
        PpmKpiPostevaluation item = myBatisDao.selectClass(PPMKPIPOSTEVALUATION,
                param,"getAvgScore");
        Double score=item.getTscore();
        return score;
    }

    @Override
    public int updateSinglePpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        ppmKpiPostevaluationDto.setSecondevaltime(new Date());
        ppmKpiPostevaluationDto.setTscore(ppmKpiPostevaluationDto.getSecondevalscore());
        //更新分数
        return updatePpmKpiPostevaluation(ppmKpiPostevaluationDto);
    }

    /**
     * 提交更新状态数据
     * @param dtos
     * @return int
     */
    @Transactional("defaultTx")
    private int updatePpmKpiPostevaluationStatus(List<PpmKpiPostevaluationDto> dtos){
        for(int i=0;i<dtos.size();i++){
            PpmKpiPostevaluationDto dto=dtos.get(i);
            dto.setItemstatus(7L);
            dto.setSecondevaltime(new Date());
            updatePpmKpiPostevaluation(dto);
        }
        return 1;
    }

    @Override
    public List<PpmKpiPostevaluationDto> queryPpmKpiPostevaluationInfosByCondition(Map param) {
        List<PpmKpiPostevaluation> lists = myBatisDao.selectListT(
                PPMKPIPOSTEVALUATION, param,"queryPagerList");
        List<PpmKpiPostevaluationDto> item = JsonUtil.jsonToList(lists, PpmKpiPostevaluationDto.class);
        return item;
    }

    /**
     * 设置考核详细项分数
     * @param dtos
     * @return int
     */
    private int setPpmKpiPmDetailTscore(List<PpmKpiPostevaluationDto> dtos){
        Map param=new HashMap();
        for(int i=0;i<dtos.size();i++){
            PpmKpiPostevaluationDto dto=dtos.get(i);
            param.put("pid",dto.getPid());
            param.put("item",dto.getItem());
            param.put("plan",dto.getPlan());
            param.put("itemstatus","7");
            Double score=getAvgScore(param);
            PpmKpiPmDetailDto ppmKpiPmDetailDto=new PpmKpiPmDetailDto();
            ppmKpiPmDetailDto.setId(dto.getPid());
            PpmKpiPmDetailDto detail=ppmKpiPmDetailService.queryDetail(ppmKpiPmDetailDto);
            if(StringUtil.isNotEmpty(detail)){
                detail.setTscore(score);
                detail.setSecondevalscore(score);
                ppmKpiPmDetailService.updateDetailEval(detail);
            }
        }
        return 1;
    }

    /**
     *  设置考核详细项状态
     * @param dtos
     * @return int
     */
    private int setPpmKpiPmDetailSatus(List<PpmKpiPostevaluationDto> dtos){
        Map param=new HashMap();
        for(int i=0;i<dtos.size();i++){
            PpmKpiPostevaluationDto dto=dtos.get(i);
            param.put("pid",dto.getPid());
            param.put("notFinish","true");
            List<PpmKpiPostevaluationDto> evals=queryPpmKpiPostevaluationInfosByCondition(param);

            PpmKpiPmDetailDto ppmKpiPmDetailDto=new PpmKpiPmDetailDto();
            ppmKpiPmDetailDto.setId(dto.getPid());
            PpmKpiPmDetailDto detail=ppmKpiPmDetailService.queryDetail(ppmKpiPmDetailDto);
            if(StringUtil.isNotEmpty(detail)){
                if(evals.size()<=0){
                    detail.setItemstatus(7L);
                }
                ppmKpiPmDetailService.updateDetailEval(detail);

                //判断主记录状态
                ppmKpiPm1Service.updateMainStatusByItemstatus(detail.getPid());
            }
        }
        return 1;
    }

    @Override
    public int submitPpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto){
        List<PpmKpiPostevaluationDto> dtos=
                queryPpmKpiPostevaluationInfosByCondition(ppmKpiPostevaluationDto);
        if(dtos.size()>0){
            //判断是否有空的没有填写分数
            for(int i=0;i<dtos.size();i++){
                if(dtos.get(i).getTscore()==null){
                    return -1;
                }
            }
            //更新状态
            updatePpmKpiPostevaluationStatus(dtos);

            //计算平均分,修改ppm_kpi_pm_detail里面的分数
            setPpmKpiPmDetailTscore(dtos);

            //判断该条数据状态是否全部完成，修改ppm_kpi_pm_detail里面的状态
            setPpmKpiPmDetailSatus(dtos);
        }
        return 1;
    }

    @Override
    public PpmKpiPostevaluationDto queryPostEvaluationSumScore(
            PpmKpiPostevaluationDto ppmKpiPostevaluationDto) {
        PpmKpiPostevaluation ppmKpiPostevaluation =
                JsonUtil.jsonToClass(ppmKpiPostevaluationDto, PpmKpiPostevaluation.class);
        PpmKpiPostevaluation data = myBatisDao.selectClass(PPMKPIPOSTEVALUATION, ppmKpiPostevaluation,
                "queryPostEvaluationSumScore");
        PpmKpiPostevaluationDto item = JsonUtil.jsonToClass(data, PpmKpiPostevaluationDto
                .class);
        return item;
    }
}
