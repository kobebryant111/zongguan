package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmKqRecordExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordImportDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;

import java.util.List;

/**
 * 考勤日历表 接口描述
 *
 */
public interface IPpmKqRecordImportService {

    /**
     * 添加
     *
     * @param ppmKqRecordImportDto 对象
     * @return int
     */
    int addPpmKqRecordImport(PpmKqRecordImportDto ppmKqRecordImportDto);

    /**
     * 更新
     *
     * @param ppmKqRecordImportDto 对象
     * @return int
     */
    int updatePpmKqRecordImport(PpmKqRecordImportDto ppmKqRecordImportDto);

    /**
     * 导入
     * @param recordImportDtos list
     * @param user user
     * @return List<PpmKqRecordImportDto>
     */
    List<PpmKqRecordImportDto> importExcel(List<PpmKqRecordImportDto> recordImportDtos,CommOperatorDoLoginDto user);

    /**
     * 导出
     * @param exList 导入的集合
     * @return boolean
     */
    List<PpmKqRecordExportDto> exportExcel(List<PpmKqRecordImportDto> exList);

    /**
     * 查询
     * @param ppmKqRecordImportDto ppmKqRecordImportDto
     * @return int
     */
    int queryOutJobName(PpmKqRecordImportDto ppmKqRecordImportDto);


}
