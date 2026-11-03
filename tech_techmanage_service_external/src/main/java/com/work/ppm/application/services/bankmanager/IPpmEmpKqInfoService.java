package com.work.ppm.application.services.bankmanager;

import com.work.ppm.application.dto.bankmamager.PpmEmpKqInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

import java.util.Date;
import java.util.List;

/**
 * 行员考勤信息表 接口描述
 *
 */
public interface IPpmEmpKqInfoService {

    /**
     * 分页
     *
     * @param ppmKqInfoDto 对象
     * @param pager        分页对象
     */
    void queryPager(PpmEmpKqInfoDto ppmKqInfoDto, Pager pager, CommOperatorDoLoginDto user);

    /**
     * 查询详情 根据id
     *
     * @param ppmKqInfoDto 对象
     * @return PpmKqInfoDto
     */
    PpmEmpKqInfoDto queryDetail(PpmEmpKqInfoDto ppmKqInfoDto);

    /**
     * 查询详情
     *
     * @param ppmKqInfoDto 对象
     * @param uniqueName   id
     * @return PpmKqInfoDto
     */
    PpmEmpKqInfoDto queryDetailObject(PpmEmpKqInfoDto ppmKqInfoDto, String uniqueName);

    /**
     * 添加
     *
     * @param ppmKqInfoDto 对象
     * @return int
     */
    int addPpmKqInfo(PpmEmpKqInfoDto ppmKqInfoDto);

    /**
     * 更新
     *
     * @param ppmKqInfoDto 对象
     * @return int
     */
    int updatePpmKqInfo(PpmEmpKqInfoDto ppmKqInfoDto);

    /**
     * 判断日期是否节假日
     *
     * @param attendanceDate attendanceDate
     * @return boolean
     */
    boolean judgingDateIsWorkingDay(Date attendanceDate);

    /**
     * @param ppmEmpKqInfoDto 对象
     * @return list
     */
    List<PpmEmpKqInfoDto> queryDetailList(PpmEmpKqInfoDto ppmEmpKqInfoDto);

    /**
     * 查询某月工作日/假期天数
     *
     * @param mouth     月份
     * @param years     年份
     * @param isHoliday 是否工作日
     * @return Long
     */
    Long queryCalendarListSize(Long mouth, Long years, String isHoliday);
}
