package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmKqInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportAllDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportNoVerifyDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

import java.util.Date;
import java.util.List;

/**
 * 考勤信息表 接口描述
 *
 */
public interface IPpmKqInfoService {

    /**
     * 分页
     *
     * @param ppmKqInfoDto 对象
     * @param pager            分页对象
     */
    void queryPager(PpmKqInfoDto ppmKqInfoDto, Pager pager,CommOperatorDoLoginDto user);

    /**
     * 查询详情 根据id
     *
     * @param ppmKqInfoDto 对象
     * @return PpmKqInfoDto
     */
    PpmKqInfoDto queryDetail(PpmKqInfoDto ppmKqInfoDto);

    /**
     * 添加
     *
     * @param ppmKqInfoDto 对象
     * @return int
     */
    int addPpmKqInfo(PpmKqInfoDto ppmKqInfoDto);

    /**
     * 查询详情
     *
     * @param ppmKqInfoDto 对象
     * @param uniqueName id
     * @return PpmKqInfoDto
     */
    PpmKqInfoDto queryDetailObject(PpmKqInfoDto ppmKqInfoDto,String uniqueName);

    /**
     *
     * @param ppmKqInfoDto 对象
     * @return list
     */
    List<PpmKqInfoDto> queryDetailList(PpmKqInfoDto ppmKqInfoDto);
    /**
     * 更新
     *
     * @param ppmKqInfoDto 对象
     * @return int
     */
    int updatePpmKqInfo(PpmKqInfoDto ppmKqInfoDto);
    /**
     * 更新
     *
     * @param ppmKqInfoDto 对象
     * @return int
     */
    int updatePpmKqInfoAdd(PpmKqInfoDto ppmKqInfoDto);

    /**
     * 修改日历 - 批量更新
     *
     * @param ppmKqInfoDto 对象
     * @return int
     */
    int updatePpmKqInfoWhetherUpdate(PpmKqInfoDto ppmKqInfoDto);

    /**
     * 查询某月工作日/假期天数
     * @param mouth 月份
     * @param years 年份
     * @param isHoliday 是否工作日
     * @return Long
     */
    Long queryCalendarListSize(Long mouth,Long years,String isHoliday);

    /**
     * 查询日期范围工作日/假期天数
     *
     * @param entryStartDate 开始日期
     * @param entryEndDate   结束日期
     * @param isHoliday      是否工作日
     * @return Long
     */
    Long queryCalendarPeriod(Date entryStartDate, Date entryEndDate, String isHoliday);

    /**
     * 导出月明细
     * @param ppmKqInfoDto 导出的集合
     * @return boolean
     */
    List<PpmKqInfoExportDto> exportExcel(PpmKqInfoDto ppmKqInfoDto, CommOperatorDoLoginDto user);

    /**
     * 导出日明细
     * @param ppmKqInfoDto 导出的集合
     * @return boolean
     */
    List<PpmKqRecordExportAllDto> exportRecordExcel(PpmKqInfoDto ppmKqInfoDto, CommOperatorDoLoginDto user);

    /**
     * 导出日明细
     * @param ppmKqInfoDto 导出的集合
     * @return boolean
     */
    List<PpmKqRecordExportNoVerifyDto> exportNoVerify(PpmKqInfoDto ppmKqInfoDto, CommOperatorDoLoginDto user);

    /**
     * 判断日期是否节假日
     * @param attendanceDate attendanceDate
     * @return boolean
     */
    boolean judgingDateIsWorkingDay(Date attendanceDate);

    /**
     * 获取第五个工作日
     * @param attendanceDate 考勤日期
     * @return date
     */
    Date compareFifthWorkingDate(Date attendanceDate);

//    void testVacation();

}
