package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmProductionDayDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmProductionDayService {

    /**
     * 分页
     * @param ppmProducationDayDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmProductionDayDto ppmProducationDayDto, Pager pager) ;

    /**
     * 查询详情
     * @param ppmProducationDayDto 对象
     * @return PpmProducationDayDto
     */
    PpmProductionDayDto queryDetail(PpmProductionDayDto ppmProducationDayDto) ;

    /**
     * 查询上线日期
     * @param applyDate
     * @return List<PpmProducationDayDto>
     */
    List<PpmProductionDayDto> queryDayList(String applyDate);

    /**
     * 查询变更日
     * @param date 当前时间
     * @return PpmProductionDayDto
     */
    PpmProductionDayDto queryChangeDate(String date);
}
