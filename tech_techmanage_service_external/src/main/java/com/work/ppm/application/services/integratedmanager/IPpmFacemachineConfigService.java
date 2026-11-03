package com.work.ppm.application.services.integratedmanager;

/**
 * 外包准入流程图片处理类
 */
public interface IPpmFacemachineConfigService {

    /**
     * 查找发送照片文件
     * @param badge 工号
     * @param empName 名称
     * @param faceimage 图片编号
     * @return String
     */
    String sendPhotoToDevice(String badge,String empName,String faceimage);
}
