package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmWbbljzhdqqrDetailDto;

import java.util.Map;

/**
  * 外包堡垒机账号到期确认详情
  */
public interface IPpmWbbljzhdqqrDetailService {

    /**
     * 分页
     * @param ppmWbbljzhdqqrDetailDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto, Pager pager) ;

    /**
     * 添加
     * @param ppmWbbljzhdqqrDetailDto 对象
     * @return int
     */
     @Deprecated
    int addPpmWbbljzhdqqrDetail(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto) ;

    /**
     * 查询详情
     * @param ppmWbbljzhdqqrDetailDto 对象
     * @return PpmWbbljzhdqqrDetailDto
     */
    @Deprecated
    PpmWbbljzhdqqrDetailDto queryDetail(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto) ;

    /**
     * 更新
     * @param ppmWbbljzhdqqrDetailDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmWbbljzhdqqrDetail(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto) ;

    /**
     * 删除
     * @param ppmWbbljzhdqqrDetailDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmWbbljzhdqqrDetail(PpmWbbljzhdqqrDetailDto ppmWbbljzhdqqrDetailDto) ;

    /**
     * 更新状态为注销
     * @param param 对象
     */
    void updateUseStatus(Map param);
}
