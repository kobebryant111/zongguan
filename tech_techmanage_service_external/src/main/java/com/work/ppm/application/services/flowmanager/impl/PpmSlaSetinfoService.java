package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.flowmanager.PpmSlaSetinfo;
import com.work.ppm.application.dto.flowmanager.PpmSlaSetinfoDto;
import com.work.ppm.application.services.flowmanager.IPpmSlaSetinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
  * sla 配置信息
  */
@Service("ppmSlaSetinfoService")
public class PpmSlaSetinfoService extends BaseDaoSupper implements IPpmSlaSetinfoService {
    /**
      * 实体类路径
      */
    private static String PPMSLASETINFO = "com.work.ppm.application.model.flowmanager.PpmSlaSetinfo";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmSlaSetinfoDto ppmSlaSetinfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmSlaSetinfoDto,PpmSlaSetinfoDto.class);
        myBatisDao.selectPager(PPMSLASETINFO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmSlaSetinfo(PpmSlaSetinfoDto ppmSlaSetinfoDto) {
        PpmSlaSetinfo ppmSlaSetinfo = JsonUtil.jsonToClass(ppmSlaSetinfoDto, PpmSlaSetinfo.class);
        Long id = getNextSequence(PpmSlaSetinfo.class);

        // 如果需要请set 主键id
        //ppmSlaSetinfo.setId(id.intValue());
        myBatisDao.executionPojo(PPMSLASETINFO, ppmSlaSetinfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmSlaSetinfoDto queryDetail(String flowdraftno,String flownodecode) {
        PpmSlaSetinfo info= new PpmSlaSetinfo();
        info.setFlowdraftno(flowdraftno);
        info.setFlownodecode(flownodecode);
        PpmSlaSetinfo data = myBatisDao.selectClass(PPMSLASETINFO, info,
        "queryDetail");
        PpmSlaSetinfoDto item = JsonUtil.jsonToClass(data, PpmSlaSetinfoDto
        .class);
        return item;
    }

    @Override
    public int updatePpmSlaSetinfo(PpmSlaSetinfoDto ppmSlaSetinfoDto) {
        PpmSlaSetinfo ppmSlaSetinfo = JsonUtil.jsonToClass(ppmSlaSetinfoDto, PpmSlaSetinfo.class);
        return myBatisDao.executionPojo(PPMSLASETINFO, ppmSlaSetinfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmSlaSetinfo(PpmSlaSetinfoDto ppmSlaSetinfoDto) {
        PpmSlaSetinfo ppmSlaSetinfo = JsonUtil.jsonToClass(ppmSlaSetinfoDto, PpmSlaSetinfo.class);
        return myBatisDao.executionPojo(PPMSLASETINFO, ppmSlaSetinfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
