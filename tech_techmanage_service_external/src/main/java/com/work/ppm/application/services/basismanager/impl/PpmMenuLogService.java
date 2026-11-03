package com.work.ppm.application.services.basismanager.impl;

import com.work.mercury.log.annotation.MercuryLog;
import com.work.ppm.application.dto.basismanager.PpmMenuLogDto;
import com.work.ppm.application.model.basismanager.PpmMenuLog;
import com.work.ppm.application.services.basismanager.IPpmMenuLogService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 菜单日志记录
 */
@MercuryLog
@Service("ppmMenuLogService")
@Slf4j
public class PpmMenuLogService extends BaseDaoSupper implements IPpmMenuLogService {
    /**
      * 实体类路径
      */
    private static String PPMMENULOG = "com.work.ppm.application.model.basismanager.PpmMenuLog";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmMenuLogDto ppmMenuLogDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmMenuLogDto,PpmMenuLogDto.class);
        myBatisDao.selectPager(PPMMENULOG, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmMenuLog(PpmMenuLogDto ppmMenuLogDto) {
        ppmMenuLogDto.setCreateTime(new Date());
        PpmMenuLog ppmMenuLog = JsonUtil.jsonToClass(ppmMenuLogDto, PpmMenuLog.class);
        Long id = getNextSequence(PpmMenuLog.class);
        // 如果需要请set 主键id
        ppmMenuLog.setId(id);
        myBatisDao.executionPojo(PPMMENULOG, ppmMenuLog, IMyBatisDao.SQL.INSTER, "addPojo");
        log.info("【新应用】菜单采集：{}", JsonUtil.toJSONString(ppmMenuLog));
        return 1;
    }
    @Override
    public PpmMenuLogDto queryDetail(PpmMenuLogDto ppmMenuLogDto) {
        PpmMenuLog ppmMenuLog = JsonUtil.jsonToClass(ppmMenuLogDto, PpmMenuLog.class);
        PpmMenuLog data = myBatisDao.selectClass(PPMMENULOG, ppmMenuLog,
        "queryDetail");
        PpmMenuLogDto item = JsonUtil.jsonToClass(data, PpmMenuLogDto
        .class);
        return item;
    }

}
