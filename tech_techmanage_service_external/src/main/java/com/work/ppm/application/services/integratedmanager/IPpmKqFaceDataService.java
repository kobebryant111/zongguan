package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmKqFaceDataDto;

import java.util.Map;

/**
 * 人脸考勤数据 接口描述
 *
 */
public interface IPpmKqFaceDataService {

    /**
     * 添加
     *
     * @param ppmKqFaceDataDto 对象
     * @return int
     */
    int addPpmKqFaceData(PpmKqFaceDataDto ppmKqFaceDataDto);

    /**
     * 同步人脸考勤机
     *
     * @param ppmKqFaceDataDto 对象
     * @return Map
     */
    Map<String,Object> syncKqFaceData(PpmKqFaceDataDto ppmKqFaceDataDto);

}
