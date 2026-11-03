package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarHistoryDto;

/**
  *  考勤日历修改记录 接口描述
  */
public interface IPpmKqCalendarHistoryService {

    /**
     * 分页
     * @param ppmKqCalendarHistoryDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKqCalendarHistoryDto 对象
     * @return int
     */
    int addPpmKqCalendarHistory(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) ;

    /**
     * 查询详情
     * @param ppmKqCalendarHistoryDto 对象
     * @return PpmKqCalendarHistoryDto
     */
    PpmKqCalendarHistoryDto queryDetail(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) ;

    /**
     * 更新
     * @param ppmKqCalendarHistoryDto  对象
     * @return int
     */
    int updatePpmKqCalendarHistory(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) ;

    /**
     * 删除
     * @param ppmKqCalendarHistoryDto 对象
     * @return int
     */
    int deletePpmKqCalendarHistory(PpmKqCalendarHistoryDto ppmKqCalendarHistoryDto) ;
}
