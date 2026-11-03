package com.work.ppm.application.services.bankmanager;

import com.work.ppm.application.dto.integratedmanager.PpmKqFaceDataDto;

import java.util.Map;

public interface IPpmEmpKqFaceDataService {
    /**
     * 行员考勤信息同步
     *
     * @param ppmKqFaceDataDto
     * @return
     */
    Map<String, Object> syncKqFaceData(PpmKqFaceDataDto ppmKqFaceDataDto);

    /**
     * 添加
     *
     * @param ppmKqFaceDataDto 对象
     * @return int
     */
    int addPpmKqFaceData(PpmKqFaceDataDto ppmKqFaceDataDto);
}
