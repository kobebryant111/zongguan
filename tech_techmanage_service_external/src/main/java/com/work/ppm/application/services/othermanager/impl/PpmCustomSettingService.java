package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.model.othermanager.PpmCustomSetting;
import com.work.ppm.application.services.othermanager.IPpmCustomSettingService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知自定义设置
 */
@Service("ppmCustomSettingService")
public class PpmCustomSettingService extends BaseDaoSupper implements IPpmCustomSettingService {
    /**
     * 实体类路径
     */
    private static String PPMCUSTOMSETTING = "com.work.ppm.application.model.othermanager.PpmCustomSetting";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public PpmCustomSetting queryDetail(String userId,String flowid) {
        PpmCustomSetting ppmCustomSetting = new PpmCustomSetting();
        ppmCustomSetting.setUserid(userId);
        ppmCustomSetting.setFlowid(flowid);
        return myBatisDao.selectClass(PPMCUSTOMSETTING, ppmCustomSetting,"queryDetail");
    }

}
