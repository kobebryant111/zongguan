package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.model.othermanager.PpmEsbMsgerrlog;
import com.work.ppm.application.services.othermanager.IPpmEsbMsgerrlogService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.lang.other.CodeGenerater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * esb错误日志
 */
@Service("ppmEsbMsgerrlogService")
public class PpmEsbMsgerrlogService extends BaseDaoSupper implements IPpmEsbMsgerrlogService {
    /**
     * 实体类路径
     */
    private static String PPMESBMSGERRLOG = "com.work.ppm.application.model.othermanager.PpmEsbMsgerrlog";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmEsbMsgerrlog(PpmEsbMsgerrlog ppmEsbMsgerrlog) {
        // 如果需要请set 主键id
        ppmEsbMsgerrlog.setId(CodeGenerater.generate("ESB",18,"yyyyMMdd"));
        myBatisDao.executionPojo(PPMESBMSGERRLOG, ppmEsbMsgerrlog, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
}
