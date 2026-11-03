package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmWbbljzhdqqrinfosDto;

import java.util.Map;

/**
  * 堡垒机用户报表-外包账号
  */
public interface IPpmWbbljzhdqqrinfosService {

    /**
     * 分页
     * @param ppmWbbljzhdqqrinfosDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto, Pager pager) ;

    /**
     * 添加
     * @param ppmWbbljzhdqqrinfosDto 对象
     * @return int
     */
     @Deprecated
    int addPpmWbbljzhdqqrinfos(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto) ;

    /**
     * 查询详情
     * @param ppmWbbljzhdqqrinfosDto 对象
     * @return PpmWbbljzhdqqrinfosDto
     */
    @Deprecated
    PpmWbbljzhdqqrinfosDto queryDetail(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto) ;

    /**
     * 更新
     * @param ppmWbbljzhdqqrinfosDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmWbbljzhdqqrinfos(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto) ;

    /**
     * 删除
     * @param ppmWbbljzhdqqrinfosDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmWbbljzhdqqrinfos(PpmWbbljzhdqqrinfosDto ppmWbbljzhdqqrinfosDto) ;

    /**
     * 更新使用状态
     * @param param 对象
     */
    void updateUseStatus(Map param);
}
