package com.work.ppm.application.services.othermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.othermanager.PpmSendMsgInfo;

/**
  * 发送消息API
  */
public interface IPpmSendMsgInfoService {

    /**
     * 分页
     * @param ppmSendMsgInfo 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmSendMsgInfo ppmSendMsgInfo, Pager pager) ;

    /**
     * 添加
     * @param ppmSendMsgInfo 对象
     * @return int
     */
    int addPpmSendMsgInfo(PpmSendMsgInfo ppmSendMsgInfo) ;

    /**
     * 查询详情
     * @param ppmSendMsgInfo 对象
     * @return PpmSendMsgInfo
     */
    @Deprecated
    PpmSendMsgInfo queryDetail(PpmSendMsgInfo ppmSendMsgInfo) ;

    /**
     * 更新
     * @param ppmSendMsgInfo  对象
     * @return int
     */
    @Deprecated
    int updatePpmSendMsgInfo(PpmSendMsgInfo ppmSendMsgInfo) ;

    /**
     * 删除
     * @param ppmSendMsgInfo 对象
     * @return int
     */
    @Deprecated
    int deletePpmSendMsgInfo(PpmSendMsgInfo ppmSendMsgInfo) ;
}
