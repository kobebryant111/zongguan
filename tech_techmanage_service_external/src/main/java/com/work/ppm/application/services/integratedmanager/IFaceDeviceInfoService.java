package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.esb.req.PpmKqFaceEsbReqDto;
import com.work.ppm.application.dto.integratedmanager.FaceDeviceInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqFaceConfigDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.commons.pager.Pager;

import java.util.List;
import java.util.Map;

/**
 * 考勤机配置 接口描述
 *
 */
public interface IFaceDeviceInfoService {

    /**
     * 分页
     *
     * @param faceDeviceInfoDto 对象
     * @param pager            分页对象
     */
    void queryPager(FaceDeviceInfoDto faceDeviceInfoDto, Pager pager);

    /**
     * 关联查询
     *
     * @param faceDeviceInfoDto 对象
     * @return list
     */
    List<FaceDeviceInfoDto> queryBaseList(FaceDeviceInfoDto faceDeviceInfoDto);

    /**
     * 精准查询
     * @param faceDeviceInfoDto 对象
     * @return FaceDeviceInfoDto
     */
    FaceDeviceInfoDto queryDetail(FaceDeviceInfoDto faceDeviceInfoDto,String uniqueName);

    /**
     * 下发考勤机 外部接口调用
     * @param faceDeviceInfoDto 数组
     * @param ppmKqFaceConfigDtos 对象list
     * @return map
     */
    Map<String,Object> handFaceDevice(List<FaceDeviceInfoDto> faceDeviceInfoDto,
                                      List<PpmKqFaceConfigDto> ppmKqFaceConfigDtos,
                                      boolean type);
    /**
     * 添加
     *
     * @param faceDeviceInfoDto 对象
     * @return int
     */
    int addFaceDeviceInfo(FaceDeviceInfoDto faceDeviceInfoDto);
    /**
     * 删除
     * @param faceDeviceInfoDto 对象
     * @return int
     */
    int deleteFaceDeviceInfo(FaceDeviceInfoDto faceDeviceInfoDto) ;

    /**
     * 离岗定时跑批人脸照片
     * @param infoDtoList 对象
     * @param outsourcingLzsqs 离岗
     */
    void pushPhotoAfterLeaving(List<FaceDeviceInfoDto> infoDtoList,List<PpmOutsourcingLzsq> outsourcingLzsqs);

    /**
     * 存储考勤人脸配置信息
     * @param faceConfigDto 对象
     * @param faceId 考勤机id
     * @param documentNo 照片编号
     */
    void saveFaceConfig(PpmKqFaceConfigDto faceConfigDto,Long faceId,String documentNo);

    /**
     * 下发考勤机
     * @param faceDeviceInfoDto 对象
     * @param empList list
     * @return map
     */
    boolean handFaceDevice(FaceDeviceInfoDto faceDeviceInfoDto, PpmKqFaceEsbReqDto empList);

}
