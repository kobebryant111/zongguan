package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarTodoDto;

/**
  *  考勤日历任务表 接口描述
  */
public interface IPpmKqCalendarTodoService {

    /**
     * 分页
     * @param ppmKqCalendarTodoDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmKqCalendarTodoDto ppmKqCalendarTodoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKqCalendarTodoDto 对象
     * @return int
     */
    int addPpmKqCalendarTodo(PpmKqCalendarTodoDto ppmKqCalendarTodoDto) ;

    /**
     * 查询详情
     * @param ppmKqCalendarTodoDto 对象
     * @return PpmKqCalendarTodoDto
     */
    PpmKqCalendarTodoDto queryDetail(PpmKqCalendarTodoDto ppmKqCalendarTodoDto) ;

    /**
     * 更新
     * @param ppmKqCalendarTodoDto  对象
     * @return int
     */
    int updatePpmKqCalendarTodo(PpmKqCalendarTodoDto ppmKqCalendarTodoDto) ;

    /**
     * 删除
     * @param ppmKqCalendarTodoDto 对象
     * @return int
     */
    int deletePpmKqCalendarTodo(PpmKqCalendarTodoDto ppmKqCalendarTodoDto) ;

    /**
     * 任务启动
     * @param ppmKqCalendarTodoDto
     * @param bps
     * @return
     */
    String start(PpmKqCalendarTodoDto ppmKqCalendarTodoDto, BpsWorkFlowDto bps);

    /**
     * 任务审批
     * @param ppmKqCalendarTodoDto
     * @param bps
     * @return
     */
    String approve(PpmKqCalendarTodoDto ppmKqCalendarTodoDto, BpsWorkFlowDto bps);
}
