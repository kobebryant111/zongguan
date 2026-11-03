package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmKhOutsourcepersonDto;
import com.work.ppm.application.model.integratedmanager.OutEvalMonthData;
import com.work.ppm.application.model.integratedmanager.OutEvalOrderData;
import com.work.ppm.application.model.integratedmanager.PpmKhOutsourceperson;
import com.work.ppm.application.model.integratedmanager.ext.OutEvalOrderDataInfo;
import com.work.ppm.application.services.integratedmanager.IPpmKhOutsourcepersonService;
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
@Service("ppmKhOutsourcepersonService")
public class PpmKhOutsourcepersonService extends BaseDaoSupper implements IPpmKhOutsourcepersonService {
    /**
      * 实体类路径
      */
    private static String PPMKHOUTSOURCEPERSON = "com.work.ppm.application.model.integratedmanager.PpmKhOutsourceperson";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKhOutsourcepersonDto,PpmKhOutsourcepersonDto.class);
        myBatisDao.selectPager(PPMKHOUTSOURCEPERSON, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKhOutsourceperson(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) {
        PpmKhOutsourceperson ppmKhOutsourceperson = JsonUtil.jsonToClass(ppmKhOutsourcepersonDto, PpmKhOutsourceperson.class);
        Long id = getNextSequence(PpmKhOutsourceperson.class);

        // 如果需要请set 主键id
        //ppmKhOutsourceperson.setId(id.intValue());
        myBatisDao.executionPojo(PPMKHOUTSOURCEPERSON, ppmKhOutsourceperson, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKhOutsourcepersonDto queryDetail(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) {
        PpmKhOutsourceperson ppmKhOutsourceperson = JsonUtil.jsonToClass(ppmKhOutsourcepersonDto, PpmKhOutsourceperson.class);
        PpmKhOutsourceperson data = myBatisDao.selectClass(PPMKHOUTSOURCEPERSON, ppmKhOutsourceperson,
        "queryDetail");
        PpmKhOutsourcepersonDto item = JsonUtil.jsonToClass(data, PpmKhOutsourcepersonDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKhOutsourceperson(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) {
        PpmKhOutsourceperson ppmKhOutsourceperson = JsonUtil.jsonToClass(ppmKhOutsourcepersonDto, PpmKhOutsourceperson.class);
        return myBatisDao.executionPojo(PPMKHOUTSOURCEPERSON, ppmKhOutsourceperson, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKhOutsourceperson(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) {
        PpmKhOutsourceperson ppmKhOutsourceperson = JsonUtil.jsonToClass(ppmKhOutsourcepersonDto, PpmKhOutsourceperson.class);
        return myBatisDao.executionPojo(PPMKHOUTSOURCEPERSON, ppmKhOutsourceperson, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public OutEvalMonthData getOutEvalMonthData(OutDataDto outDataDto) {
        Map param=new HashMap();
        param.put("jobid",outDataDto.getJobid());
        param.put("deptno",outDataDto.getDeptno());
        param.put("year",outDataDto.getYear());
        param.put("outsourceno",outDataDto.getOutsourceno());
        OutEvalMonthData data= myBatisDao.selectClass(PPMKHOUTSOURCEPERSON, param,"getOutEvalMonthData");
        return data;
    }

    /**
     * retRate
     * @param depDatas
     * @param jobid
     * @return int
     */
    private int retRate( List<OutEvalOrderData> depDatas,String jobid){
        double total=0L;
        double order=0L;
        double rate=0L;
        total=depDatas.size();
        for (int i=0;i<depDatas.size();i++){
            String jobid_r=depDatas.get(i).getJobid();
            if(jobid.equals(jobid_r)){
                order=i+1;
                break;
            }
        }
        if(total==0){
            return 0;
        }else{
            return (new Double((order/total)*100)).intValue();
        }
    }
    @Override
    public OutEvalOrderDataInfo getOutEvalOrderData(OutDataDto outDataDto) {
        OutEvalOrderDataInfo ret=new OutEvalOrderDataInfo();
        String jobid=outDataDto.getJobid();
        ret.setJobid(jobid);
        Map param=new HashMap();
        param.put("jobid",outDataDto.getJobid());
        param.put("deptno",outDataDto.getDeptno());
        param.put("year",outDataDto.getYear());
        param.put("outsourceno",outDataDto.getOutsourceno());
        List<OutEvalOrderData> depDatas= myBatisDao.selectListT(PPMKHOUTSOURCEPERSON, param,"getOutEvalOrderByDep");
        if(depDatas.size()>0){
            int deprate=retRate(depDatas,jobid);
            ret.setDepRate(deprate+"");
        }
        List<OutEvalOrderData> outsourcenoDatas= myBatisDao.selectListT(PPMKHOUTSOURCEPERSON, param,"getOutEvalOrderByOutsourceno");
        if(outsourcenoDatas.size()>0){
            int outsourcenorate=retRate(outsourcenoDatas,jobid);
            ret.setOutsourcenoRate(outsourcenorate+"");
        }
        List<OutEvalOrderData> tecDatas= myBatisDao.selectListT(PPMKHOUTSOURCEPERSON, param,"getOutEvalOrder");
        if(tecDatas.size()>0){
            int tecrate=retRate(tecDatas,jobid);
            ret.setTecRate(tecrate+"");
        }
        return ret;

    }
}
