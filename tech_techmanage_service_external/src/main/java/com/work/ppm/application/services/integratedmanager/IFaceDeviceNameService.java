package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.FaceDeviceNameDto;

/**
 * 考勤机配置 接口描述
 *
 */
public interface IFaceDeviceNameService {

    /**
     * 精准查询
     * @param faceDeviceNameDto 对象
     * @return FaceDeviceNameDto
     */
    FaceDeviceNameDto queryDetail(FaceDeviceNameDto faceDeviceNameDto);

    /**
     * 添加
     *
     * @param faceDeviceNameDto 对象
     * @return int
     */
    int addFaceDeviceName(FaceDeviceNameDto faceDeviceNameDto);

    /**
     * 更新
     *
     * @param faceDeviceNameDto 对象
     * @return int
     */
    int updateFaceDeviceName(FaceDeviceNameDto faceDeviceNameDto);

}
