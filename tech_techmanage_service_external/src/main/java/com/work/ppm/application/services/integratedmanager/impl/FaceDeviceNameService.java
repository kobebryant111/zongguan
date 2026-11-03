package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.FaceDeviceNameDto;
import com.work.ppm.application.model.integratedmanager.FaceDeviceName;
import com.work.ppm.application.services.integratedmanager.IFaceDeviceNameService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 人脸考勤数据 接口实现类
 *
 */
@Slf4j
@Service("FaceDeviceNameService")
public class FaceDeviceNameService extends BaseDaoSupper implements IFaceDeviceNameService {
    /**
     * 实体类路径
     */
    private static final String FACEDEVICENAME = "com.work.ppm.application.model.integratedmanager.FaceDeviceName";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public FaceDeviceNameDto queryDetail(FaceDeviceNameDto faceDeviceNameDto) {
        FaceDeviceName faceDeviceName = JsonUtil.jsonToClass(faceDeviceNameDto, FaceDeviceName.class);
        FaceDeviceName data = myBatisDao.selectClass(FACEDEVICENAME, faceDeviceName, "queryDetail");
        return JsonUtil.jsonToClass(data, FaceDeviceNameDto.class);
    }

    @Override
    public int addFaceDeviceName(FaceDeviceNameDto faceDeviceNameDto) {
        FaceDeviceName faceDeviceName = JsonUtil.jsonToClass(faceDeviceNameDto, FaceDeviceName.class);
        myBatisDao.executionPojo(FACEDEVICENAME, faceDeviceName, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public int updateFaceDeviceName(FaceDeviceNameDto faceDeviceNameDto) {
        FaceDeviceName faceDeviceName = JsonUtil.jsonToClass(faceDeviceNameDto, FaceDeviceName.class);
        return myBatisDao.executionPojo(FACEDEVICENAME, faceDeviceName, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

}
