package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 考勤信息表 接口描述
 *
 */
public interface IPpmKqRecordService {

    /**
     * 分页
     *
     * @param ppmKqRecordDto 对象
     * @param pager            分页对象
     */
    void queryPager(PpmKqRecordDto ppmKqRecordDto, Pager pager, CommOperatorDoLoginDto user);

    /**
     * 添加
     *
     * @param ppmKqRecordDto 对象
     * @return int
     */
    int addPpmKqRecord(PpmKqRecordDto ppmKqRecordDto);

    /**
     * 查询详情
     *
     * @param ppmKqRecordDto 对象
     * @param infoId 父编号
     * @return PpmKqRecordDto
     */
    PpmKqRecordDto queryDetailObject(PpmKqRecordDto ppmKqRecordDto, Long infoId);

    /**
     * 查询详情 简单查询
     *
     * @param ppmKqRecordDto 对象
     * @return PpmKqRecordDto
     */
    PpmKqRecordDto queryDetail(PpmKqRecordDto ppmKqRecordDto);

    /**
     * 修改考勤详情 同步修改考勤信息状态
     *
     * @param ppmKqRecordDto 对象
     * @return int
     */
    int updatePpmKqRecordAndInfo(PpmKqRecordDto ppmKqRecordDto);
    /**
     * 处理异常考勤时添加忘记打卡的出勤天数
     *
     * @param ppmKqRecordDto 对象
     * @return int
     */
    int updatePpmKqRecordAndInfoAdd(PpmKqRecordDto ppmKqRecordDto);
    /**
     * 更新
     *
     * @param ppmKqRecordDto 对象
     * @return int
     */
    int updatePpmKqRecord(PpmKqRecordDto ppmKqRecordDto);

    /**
     * 时间段更新
     *
     * @param ppmKqRecordDto 对象
     * @return int
     */
    int updatePpmKqRecordCus(PpmKqRecordDto ppmKqRecordDto);

    /**
     * 导入 更新
     *
     * @param ppmKqRecordDto 对象
     * @return int
     */
    int updateImportPpmKqRecord(PpmKqRecordDto ppmKqRecordDto);


    /**
     * 更新加班时间相关
     *
     * @param ppmKqRecordDto 对象
     * @return int
     */
    int updatePpmKqRecordOvertime(PpmKqRecordDto ppmKqRecordDto);

    /**
     * 获取月份考勤记录
     * @param infoId 考勤信息编号
     * @return List<PpmKqRecordDto>
     */
    List<PpmKqRecordDto> queryDetailList(Long infoId);

    /**
     * 根据条件查询
     * @param ppmKqRecordDto 对象
     * @return List<PpmKqRecordDto>
     */
    List<PpmKqRecordDto> queryWhereList(PpmKqRecordDto ppmKqRecordDto);

    /**
     * 根据考勤日期条件查询
     * @param ppmKqRecordDto 对象
     * @return List<PpmKqRecordDto>
     */
    List<PpmKqRecordDto> queryAttendanceList(PpmKqRecordDto ppmKqRecordDto);

    /**
     * 查询关联查询表
     * @param ppmKqRecordDto 对象
     * @return List<PpmKqRecordDto>
     */
    List<PpmKqRecordDto> queryBaseInfoList(PpmKqRecordDto ppmKqRecordDto);

    /**
     * 查询去重工号
     * @param ppmKqRecordDto 对象
     * @return List<PpmKqRecordDto>
     */
    List<PpmKqRecordDto> queryDistinctHandle(PpmKqRecordDto ppmKqRecordDto);

    /**
     *
     * @param attendanceDate 考勤日期
     * @param day 天
     * @return list
     */
    List<PpmKqCalendarDto> calIsFifthWoringTime(Date attendanceDate,int day);

    /**
     * 修改日历 - 修改考勤详情
     * @param attendanceDate 考勤日期
     */
    void updateRecordHoliday(Date attendanceDate,String isHoliday);

    /**
     * 查询时间范围内的考勤记录
     * @param ppmKqRecordDto 对象
     * @return List<PpmKqRecordDto>
     */
    List<PpmKqRecordDto> getAttendanceRecord(PpmKqRecordDto ppmKqRecordDto);

    void backRecordList(List<PpmKqRecordDto> ppmKqRecordDtos, CommOperatorDoLoginDto user);
}
