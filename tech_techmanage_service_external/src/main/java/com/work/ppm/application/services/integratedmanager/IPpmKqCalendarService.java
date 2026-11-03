package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;
import org.springframework.web.multipart.MultipartFile;

/**
 * 考勤日历表 接口描述
 *
 */
public interface IPpmKqCalendarService {

    /**
     * 分页
     *
     * @param ppmKqCalendarDto 对象
     * @param pager            分页对象
     */
    void queryPager(PpmKqCalendarDto ppmKqCalendarDto, Pager pager);

    /**
     * 添加
     *
     * @param ppmKqCalendarDto 对象
     * @return int
     */
    int addPpmKqCalendar(PpmKqCalendarDto ppmKqCalendarDto);

    /**
     * 查询详情
     *
     * @param ppmKqCalendarDto 对象
     * @return PpmKqCalendarDto
     */
    PpmKqCalendarDto queryDetail(PpmKqCalendarDto ppmKqCalendarDto);

    /**
     * 更新
     *
     * @param ppmKqCalendarDto 对象
     * @return int
     */
    int updatePpmKqCalendar(PpmKqCalendarDto ppmKqCalendarDto);

    /**
     * 删除
     *
     * @param ppmKqCalendarDto 对象
     * @return int
     */
    int deletePpmKqCalendar(PpmKqCalendarDto ppmKqCalendarDto);

    /**
     * 导入日历信息
     *
     * @param file
     * @param user
     * @return
     */
    boolean importExcel(MultipartFile file, CommOperatorDoLoginDto user);

    /**
     * 添加操作历史
     * @param user
     * @param content
     */
    void addPpmKqCalendarHistory(CommOperatorDoLoginDto user, String content);
}
