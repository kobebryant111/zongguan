package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmStaffTransferInfoDto;
import java.util.List;

/**
  *  人员移交详情信息 接口描述
  */
public interface IPpmStaffTransferInfoService {


    /**
     * 添加
     * @param transferInfoDtos 对象
     * @return int
     */
    int addPpmStaffTransferInfo(List<PpmStaffTransferInfoDto> transferInfoDtos) ;

    /**
     * 添加
     * @param ppmStaffTransferInfoDto 对象
     * @return int
     */
    int insertPpmStaffTransferInfo(PpmStaffTransferInfoDto ppmStaffTransferInfoDto) ;

    /**
     * 查询详情 - 集合
     * @param ppmEarlyCourseApplyInfoDto 对象
     * @return List<PpmStaffTransferInfoDto>
     */
    List<PpmStaffTransferInfoDto> queryBaseList(PpmStaffTransferInfoDto ppmEarlyCourseApplyInfoDto) ;

    /**
     * 查询详情 - 集合
     * @param instanceId 流程编号
     * @return List<PpmStaffTransferInfoDto>
     */
    List<PpmStaffTransferInfoDto> queryListForInstanceId(String instanceId) ;

    /**
     * 查询集合
     * @param ppmStaffTransferInfoDto 对象
     * @return PpmStaffTransferInfoDto
     */
    List<PpmStaffTransferInfoDto> queryDetailList(PpmStaffTransferInfoDto ppmStaffTransferInfoDto) ;

    /**
     * 更新
     * @param ppmStaffTransferInfoDto  对象
     * @return int
     */
    int updatePpmStaffTransferInfo(PpmStaffTransferInfoDto ppmStaffTransferInfoDto) ;

    /**
     * 删除
     * @param instanceId 流程编号
     * @return Integer
     */
    Integer deleteByInstanceId(String instanceId);

    /**
     * 删除多余的数据
     * @param infoDtos 数据集合
     * @param instanceId 流程编号
     * @return Integer
     */
    Integer deleteInJobId(List<PpmStaffTransferInfoDto> infoDtos, String instanceId);



}
