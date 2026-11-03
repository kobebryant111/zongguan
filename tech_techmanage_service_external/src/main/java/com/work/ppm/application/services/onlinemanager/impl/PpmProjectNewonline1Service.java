package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.application.dto.onlinemanager.PpmProjectNewonlineDto;
import com.work.ppm.application.model.onlinemanager.PpmProjectNewonline;
import com.work.ppm.application.services.onlinemanager.IPpmProjectNewonline1Service;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务意向上线服务类（扩展类）
 */
@Service("ppmProjectNewonlineService1")
@Slf4j
public class PpmProjectNewonline1Service extends BaseDaoSupper implements IPpmProjectNewonline1Service {

    /**
     * 实体类路径
     */
    protected static String PPMPROJECTNEWONLINE = "com.work.ppm.application.model.onlinemanager.PpmProjectNewonline";
    /**
     * 分行业务意向上线
     */
    protected final static String FLOW_NAME = "com.work.ppm.bps.newfhxmsxFJH";
    /**
     * 数据接口
     */
    @Autowired
    protected IMyBatisDao myBatisDao;

    @Override
    public PpmProjectNewonlineDto queryDetailByOnlineno(String onlineno) {
        Map<String, Object> query = new HashMap<>();
        query.put("onlineno", onlineno);
        PpmProjectNewonline data = myBatisDao.selectClass(PPMPROJECTNEWONLINE, query,
                "queryDetailByOnlineno");
        PpmProjectNewonlineDto item = JsonUtil.jsonToClass(data, PpmProjectNewonlineDto
                .class);
        return item;
    }
}
