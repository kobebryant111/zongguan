package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmOnlineGndDto;

/**
  * 接口描述
  */
public interface IPpmOnlineGndService {

    /**
     * 分页
     * @param ppmOnlineGndDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOnlineGndDto ppmOnlineGndDto, Pager pager) ;

    /**
     * 添加
     * @param ppmOnlineGndDto 对象
     * @return int
     */
    int addPpmOnlineGnd(PpmOnlineGndDto ppmOnlineGndDto) ;

    /**
     * 查询详情
     * @param ppmOnlineGndDto 对象
     * @return PpmOnlineGndDto
     */
    @Deprecated
    PpmOnlineGndDto queryDetail(PpmOnlineGndDto ppmOnlineGndDto) ;

    /**
     * 更新
     * @param ppmOnlineGndDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmOnlineGnd(PpmOnlineGndDto ppmOnlineGndDto) ;

    /**
     * 删除
     * @param ppmOnlineGndDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmOnlineGnd(PpmOnlineGndDto ppmOnlineGndDto) ;
}
