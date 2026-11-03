package com.work.ppm.application.services.bankmanager;

import com.work.ppm.application.dto.bankmamager.PpmEmpKqRecordDto;

import java.util.List;

/**
 * 行员考勤信息表 接口描述
 *
 */
public interface IPpmEmpKqRecordService {

    /**
     * 查询详情
     *
     * @param ppmEmpKqRecordDto 对象
     * @param infoId            父编号
     * @return ppmEmpKqRecordDto
     */
    PpmEmpKqRecordDto queryDetailObject(PpmEmpKqRecordDto ppmEmpKqRecordDto, Long infoId);

    /**
     * 添加
     *
     * @param ppmEmpKqRecordDto 对象
     * @return int
     */
    int addPpmKqRecord(PpmEmpKqRecordDto ppmEmpKqRecordDto);

    /**
     * 更新
     *
     * @param ppmEmpKqRecordDto 对象
     * @return int
     */
    int updatePpmKqRecord(PpmEmpKqRecordDto ppmEmpKqRecordDto);

    /**
     * 获取月份考勤记录
     *
     * @param infoId 考勤信息编号
     * @return List<ppmEmpKqRecordDto>
     */
    List<PpmEmpKqRecordDto> queryDetailList(Long infoId);

    /**
     * 根据条件查询
     *
     * @param ppmEmpKqRecordDto 对象
     * @return List<PpmEmpKqRecordDto>
     */
    List<PpmEmpKqRecordDto> queryWhereList(PpmEmpKqRecordDto ppmEmpKqRecordDto);

    /**
     * 修改考勤详情 同步修改考勤信息状态
     *
     * @param ppmKqRecordDto 对象
     * @return int
     */
    int updatePpmKqRecordAndInfo(PpmEmpKqRecordDto ppmKqRecordDto);

}
