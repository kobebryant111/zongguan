package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmTeamqaFileCheckDto;

/**
  * 接口描述
  */
public interface IPpmTeamqaFileCheckService {

    /**
     * 分页
     * @param ppmTeamqaFileCheckDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto, Pager pager) ;

    /**
     * 添加
     * @param ppmTeamqaFileCheckDto 对象
     * @return int
     */
     @Deprecated
    int addPpmTeamqaFileCheck(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) ;

    /**
     * 查询详情
     * @param ppmTeamqaFileCheckDto 对象
     * @return PpmTeamqaFileCheckDto
     */
    @Deprecated
    PpmTeamqaFileCheckDto queryDetail(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) ;

    /**
     * 更新
     * @param ppmTeamqaFileCheckDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmTeamqaFileCheck(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) ;

    /**
     * 删除
     * @param ppmTeamqaFileCheckDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmTeamqaFileCheck(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) ;
}
