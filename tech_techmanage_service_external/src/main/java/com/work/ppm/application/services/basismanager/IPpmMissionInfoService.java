package com.work.ppm.application.services.basismanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.basismanager.PpmMissionInfoDto;

/**
  * 接口描述
  */
public interface IPpmMissionInfoService {

    /**
     * 分页
     * @param ppmMissionInfoDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmMissionInfoDto ppmMissionInfoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmMissionInfoDto 对象
     * @return int
     */
    int addPpmMissionInfo(PpmMissionInfoDto  ppmMissionInfoDto) ;

    /**
     * 查询详情
     * @param ppmMissionInfoDto 对象
     * @return PpmMissionInfoDto
     */
    @Deprecated
    PpmMissionInfoDto queryDetail(PpmMissionInfoDto ppmMissionInfoDto) ;

    /**
     * 更新
     * @param ppmMissionInfoDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmMissionInfo(PpmMissionInfoDto ppmMissionInfoDto) ;

    /**
     * 删除
     * @param ppmMissionInfoDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmMissionInfo(PpmMissionInfoDto ppmMissionInfoDto) ;

    /**
     * 删除
     * @param ppmMissionInfoDto 对象
     * @return int
     */
    String saveMission(PpmMissionInfoDto ppmMissionInfoDto) ;
}
