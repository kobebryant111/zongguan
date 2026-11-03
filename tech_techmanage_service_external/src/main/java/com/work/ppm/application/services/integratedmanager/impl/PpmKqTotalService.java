package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqTotalDto;
import com.work.ppm.application.model.integratedmanager.OutOverTimeMonthData;
import com.work.ppm.application.model.integratedmanager.OutOvertimeOrderData;
import com.work.ppm.application.model.integratedmanager.PpmKqTotal;
import com.work.ppm.application.model.integratedmanager.ext.OutOvertimeOrderDataInfo;
import com.work.ppm.application.services.integratedmanager.IPpmKqTotalService;
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
@Service("ppmKqTotalService")
public class PpmKqTotalService extends BaseDaoSupper implements IPpmKqTotalService {
    /**
      * 实体类路径
      */
    private static String PPMKQTOTAL = "com.work.ppm.application.model.integratedmanager.PpmKqTotal";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmKqTotalDto ppmKqTotalDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKqTotalDto, PpmKqTotalDto.class);
        myBatisDao.selectPager(PPMKQTOTAL, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKqTotal(PpmKqTotalDto ppmKqTotalDto) {
        PpmKqTotal ppmKqTotal = JsonUtil.jsonToClass(ppmKqTotalDto, PpmKqTotal.class);
        Long id = getNextSequence(PpmKqTotal.class);

        // 如果需要请set 主键id
        //ppmKqTotal.setId(id.intValue());
        myBatisDao.executionPojo(PPMKQTOTAL, ppmKqTotal, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKqTotalDto queryDetail(PpmKqTotalDto ppmKqTotalDto) {
        PpmKqTotal ppmKqTotal = JsonUtil.jsonToClass(ppmKqTotalDto, PpmKqTotal.class);
        PpmKqTotal data = myBatisDao.selectClass(PPMKQTOTAL, ppmKqTotal,
        "queryDetail");
        PpmKqTotalDto item = JsonUtil.jsonToClass(data, PpmKqTotalDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKqTotal(PpmKqTotalDto ppmKqTotalDto) {
        PpmKqTotal ppmKqTotal = JsonUtil.jsonToClass(ppmKqTotalDto, PpmKqTotal.class);
        return myBatisDao.executionPojo(PPMKQTOTAL, ppmKqTotal, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKqTotal(PpmKqTotalDto ppmKqTotalDto) {
        PpmKqTotal ppmKqTotal = JsonUtil.jsonToClass(ppmKqTotalDto, PpmKqTotal.class);
        return myBatisDao.executionPojo(PPMKQTOTAL, ppmKqTotal, IMyBatisDao.SQL.DELETE,"deleteById");
    }
    /**
     * retRate
     * @param depDatas
     * @param jobid
     * @return int
     */
    private int retRate(List<OutOvertimeOrderData> depDatas, String jobid){
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
    public OutOverTimeMonthData getOutOvertimeMonthData(OutDataDto outDataDto) {
        Map param=new HashMap();
        param.put("jobid",outDataDto.getJobid());
        param.put("deptno",outDataDto.getDeptno());
        param.put("year",outDataDto.getYear());
        param.put("outsourceno",outDataDto.getOutsourceno());
        OutOverTimeMonthData data= myBatisDao.selectClass(PPMKQTOTAL, param,"getOutOvertimeMonthData");
        return data;
    }

    @Override
    public OutOvertimeOrderDataInfo getOutOvertimeOrderData(OutDataDto outDataDto) {
        OutOvertimeOrderDataInfo ret=new OutOvertimeOrderDataInfo();
        String jobid=outDataDto.getJobid();
        ret.setJobid(jobid);
        Map param=new HashMap();
        param.put("jobid",outDataDto.getJobid());
        param.put("deptno",outDataDto.getDeptno());
        param.put("year",outDataDto.getYear());
        param.put("outsourceno",outDataDto.getOutsourceno());
        List<OutOvertimeOrderData> depDatas= myBatisDao.selectListT(PPMKQTOTAL, param,"getOutOvertimeOrderByDep");
        if(depDatas.size()>0){
            int deprate=retRate(depDatas,jobid);
            ret.setDepRate(deprate+"");
        }
        List<OutOvertimeOrderData> outsourcenoDatas= myBatisDao.selectListT(PPMKQTOTAL, param,"getOutOvertimeOrderByOutsourceno");
        if(outsourcenoDatas.size()>0){
            int outsourcenorate=retRate(outsourcenoDatas,jobid);
            ret.setOutsourcenoRate(outsourcenorate+"");
        }
        List<OutOvertimeOrderData> tecDatas= myBatisDao.selectListT(PPMKQTOTAL, param,"getOutOvertimeOrder");
        if(tecDatas.size()>0){
            int tecrate=retRate(tecDatas,jobid);
            ret.setTecRate(tecrate+"");
        }
        return ret;

    }
}
