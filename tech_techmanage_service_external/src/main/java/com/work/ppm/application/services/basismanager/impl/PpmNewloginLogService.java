package com.work.ppm.application.services.basismanager.impl;

import com.work.ppm.application.dto.basismanager.PpmNewloginLogDto;
import com.work.ppm.application.model.basismanager.PpmNewloginLog;
import com.work.ppm.application.services.basismanager.IPpmNewloginLogService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 登录日志
 */
@Service("ppmNewloginLogService")
public class PpmNewloginLogService extends BaseDaoSupper implements IPpmNewloginLogService {
    /**
      * 实体类路径
      */
    private static String PPMNEWLOGINLOG = "com.work.ppm.application.model.basismanager.PpmNewloginLog";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmNewloginLogDto ppmNewloginLogDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmNewloginLogDto,PpmNewloginLogDto.class);
        myBatisDao.selectPager(PPMNEWLOGINLOG, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmNewloginLog(PpmNewloginLogDto ppmNewloginLogDto) {
        PpmNewloginLog ppmNewloginLog = JsonUtil.jsonToClass(ppmNewloginLogDto, PpmNewloginLog.class);
        Long id = getNextSequence(PpmNewloginLog.class);

        // 如果需要请set 主键id
        ppmNewloginLog.setId(id);
        myBatisDao.executionPojo(PPMNEWLOGINLOG, ppmNewloginLog, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmNewloginLogDto queryDetail(PpmNewloginLogDto ppmNewloginLogDto) {
        PpmNewloginLog ppmNewloginLog = JsonUtil.jsonToClass(ppmNewloginLogDto, PpmNewloginLog.class);
        PpmNewloginLog data = myBatisDao.selectClass(PPMNEWLOGINLOG, ppmNewloginLog,
        "queryDetail");
        PpmNewloginLogDto item = JsonUtil.jsonToClass(data, PpmNewloginLogDto
        .class);
        return item;
    }
}
