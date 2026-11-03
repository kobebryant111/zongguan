package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.model.othermanager.PpmUtilCodegenerate;
import com.work.ppm.application.services.othermanager.IPpmUtilCodegenerateService;
import com.work.ppm.commons.dao.IMyBatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流水号生成接口
 */
@Service("ppmUtilCodegenerateService")
public class PpmUtilCodegenerateService implements IPpmUtilCodegenerateService {
    /**
     * 实体类路径
     */
    private static String PPMUTILCODEGENERATE = "com.work.ppm.application.model.othermanager.PpmUtilCodegenerate";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public int addPpmUtilCodegenerate(String code, long value) {
        PpmUtilCodegenerate ppmUtilCodegenerate = new PpmUtilCodegenerate();
        ppmUtilCodegenerate.setCode(code);
        ppmUtilCodegenerate.setValue(value);
        myBatisDao.executionPojo(PPMUTILCODEGENERATE, ppmUtilCodegenerate, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmUtilCodegenerate queryDetail(String code) {
        PpmUtilCodegenerate ppmUtilCodegenerate = new PpmUtilCodegenerate();
        ppmUtilCodegenerate.setCode(code);
        return myBatisDao.selectClass(PPMUTILCODEGENERATE, ppmUtilCodegenerate,"queryDetail");
    }

    @Override
    public Integer updatePpmUtilCodegenerate(String code, long value) {
        PpmUtilCodegenerate ppmUtilCodegenerate = new PpmUtilCodegenerate();
        ppmUtilCodegenerate.setCode(code);
        ppmUtilCodegenerate.setValue(value);
        return myBatisDao.executionPojo(PPMUTILCODEGENERATE, ppmUtilCodegenerate, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }
}
