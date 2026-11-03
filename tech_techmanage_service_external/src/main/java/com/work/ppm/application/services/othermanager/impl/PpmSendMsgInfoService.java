package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.othermanager.PpmSendMsgInfo;
import com.work.ppm.application.services.othermanager.IPpmSendMsgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
  * 发送消息
  */
@Service("ppmSendMsgInfoService")
public class PpmSendMsgInfoService extends BaseDaoSupper implements IPpmSendMsgInfoService {
    /**
      * 实体类路径
      */
    private static String PPMSENDMSGINFO = "com.work.ppm.application.model.othermanager.PpmSendMsgInfo";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmSendMsgInfo ppmSendMsgInfo, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmSendMsgInfo,PpmSendMsgInfo.class);
        myBatisDao.selectPager(PPMSENDMSGINFO, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmSendMsgInfo(PpmSendMsgInfo ppmSendMsgInfo) {
        Long id = getNextSequence(PpmSendMsgInfo.class);
        // 如果需要请set 主键id
        ppmSendMsgInfo.setId(id);
        ppmSendMsgInfo.setCreateTime(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        ppmSendMsgInfo.setModifyTime(ppmSendMsgInfo.getCreateTime());
        myBatisDao.executionPojo(PPMSENDMSGINFO, ppmSendMsgInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmSendMsgInfo queryDetail(PpmSendMsgInfo ppmSendMsgInfo) {
        return myBatisDao.selectClass(PPMSENDMSGINFO, ppmSendMsgInfo,"queryDetail");
    }

    @Override
    public int updatePpmSendMsgInfo(PpmSendMsgInfo ppmSendMsgInfo) {
        return myBatisDao.executionPojo(PPMSENDMSGINFO, ppmSendMsgInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmSendMsgInfo(PpmSendMsgInfo ppmSendMsgInfo) {
        return myBatisDao.executionPojo(PPMSENDMSGINFO, ppmSendMsgInfo, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
