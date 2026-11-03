package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.PpmSystParameterDto;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.systemmanager.PpmSystParameter;
import com.work.ppm.application.services.systemmanager.IPpmSystParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * 通用参数配置接口
 */
@Service("ppmSystParameterService")
public class PpmSystParameterService extends BaseDaoSupper implements IPpmSystParameterService {
    /**
     * 实体类路径
     */
    private static String PPMSYSTPARAMETER = "com.work.ppm.application.model.systemmanager.PpmSystParameter";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 文档接口
     */
    @Autowired
    private IPpmDocumentService ppmDocumentService;
    @Override
    public void queryPager(PpmSystParameter ppmSystParameter, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmSystParameter,PpmSystParameter.class);
        myBatisDao.selectPager(PPMSYSTPARAMETER, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmSystParameter(PpmSystParameter ppmSystParameter) {
        //Long id = getNextSequence(PpmSystParameter.class);
        // 如果需要请set 主键id
        //ppmSystParameter.setId(id.intValue());
        myBatisDao.executionPojo(PPMSYSTPARAMETER, ppmSystParameter, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmSystParameter queryDetail(String key) {
        PpmSystParameter ppmSystParameter = new PpmSystParameter();
        ppmSystParameter.setKey(key);
        ppmSystParameter.setIsdel(0L);
        PpmSystParameter item = myBatisDao.selectClass(PPMSYSTPARAMETER, ppmSystParameter,"queryDetail");
        return item;
    }

    @Override
    public Integer updatePpmSystParameter(PpmSystParameter ppmSystParameter) {
        return myBatisDao.executionPojo(PPMSYSTPARAMETER, ppmSystParameter, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmSystParameter(PpmSystParameter ppmSystParameter) {
        return myBatisDao.executionPojo(PPMSYSTPARAMETER, ppmSystParameter, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public String getValueByKey(String syaparamkey) {
        PpmSystParameter ppmSystParameter = new PpmSystParameter();
        ppmSystParameter.setKey(syaparamkey);
        PpmSystParameter data = myBatisDao.selectClass(PPMSYSTPARAMETER, ppmSystParameter,
                "getValueByKey");
        PpmSystParameterDto item = JsonUtil.jsonToClass(data, PpmSystParameterDto.class);
        return item.getValue();
    }
}
