package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.model.othermanager.PpmCustomSetting;

/**
 * 通知自定义设置
 */
public interface IPpmCustomSettingService {
    /**
     * 查询详情
     * @param userId 用户编号
     * @param flowid 流程定义名称
     * @return PpmCustomSetting
     */
    PpmCustomSetting queryDetail(String userId,String flowid) ;

}
