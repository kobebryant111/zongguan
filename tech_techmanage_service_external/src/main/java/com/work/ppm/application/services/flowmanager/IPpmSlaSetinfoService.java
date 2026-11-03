package com.work.ppm.application.services.flowmanager;

import com.work.ppm.application.dto.flowmanager.PpmSlaSetinfoDto;
import com.work.ppm.commons.pager.Pager;

/**
  * sla 配置信息
  */
public interface IPpmSlaSetinfoService {

    /**
     * 分页
     * @param ppmSlaSetinfoDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmSlaSetinfoDto ppmSlaSetinfoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmSlaSetinfoDto 对象
     * @return int
     */
     @Deprecated
    int addPpmSlaSetinfo(PpmSlaSetinfoDto ppmSlaSetinfoDto) ;

    /**
     * 查询详情
     * @param flowdraftno 流程定义id
     * @param flownodecode 活动id
     * @return PpmSlaSetinfoDto
     */
    PpmSlaSetinfoDto queryDetail(String flowdraftno,String flownodecode) ;

    /**
     * 更新
     * @param ppmSlaSetinfoDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmSlaSetinfo(PpmSlaSetinfoDto ppmSlaSetinfoDto) ;

    /**
     * 删除
     * @param ppmSlaSetinfoDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmSlaSetinfo(PpmSlaSetinfoDto ppmSlaSetinfoDto) ;
}
