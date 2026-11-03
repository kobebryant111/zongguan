package com.work.ppm.application.services.otherdeptmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.otherdeptmanager.PpmKqRecordOtherDto;

import java.util.Date;
import java.util.List;

/**
  *  考勤信息表-其他部门 接口描述
  */
public interface IPpmKqRecordOtherService {

    /**
     * 分页
     * @param ppmKqRecordOtherDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmKqRecordOtherDto ppmKqRecordOtherDto, Pager pager, CommOperatorDoLoginDto user) ;

    /**
     * 添加
     * @param ppmKqRecordOtherDto 对象
     * @return int
     */
    int addPpmKqRecordOther(PpmKqRecordOtherDto  ppmKqRecordOtherDto) ;

    /**
     * 查询详情
     * @param ppmKqRecordOtherDto 对象
     * @return PpmKqRecordOtherDto
     */
    PpmKqRecordOtherDto queryDetail(PpmKqRecordOtherDto ppmKqRecordOtherDto) ;

    /**
     * 更新
     * @param ppmKqRecordOtherDto  对象
     * @return int
     */
    int updatePpmKqRecordOther(PpmKqRecordOtherDto ppmKqRecordOtherDto) ;

    /**
     * 导出
     * @param kqRecordOtherDto 导出的集合
     * @return boolean
     */
    List<PpmKqRecordOtherDto> exportExcel(PpmKqRecordOtherDto kqRecordOtherDto, CommOperatorDoLoginDto user);

    /**
     * 修改日历 - 修改考勤详情
     * @param attendanceDate 考勤日期
     */
    void updateRecordOtherHoliday(Date attendanceDate, String isHoliday);



}
