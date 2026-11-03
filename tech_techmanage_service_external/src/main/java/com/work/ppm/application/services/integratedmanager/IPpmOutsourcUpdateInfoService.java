package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmOutsourcUpdateInfoDto;

import java.util.List;

/**
  *  信息变更申请信息表 接口描述
  */
public interface IPpmOutsourcUpdateInfoService {

    /**
     * 添加
     * @param updateInfoDto 对象
     * @return int
     */
    int addPpmOutsourcUpdateInfo(PpmOutsourcUpdateInfoDto updateInfoDto) ;

    /**
     * 添加
     * @param updateInfoDtos 对象
     * @return int
     */
    int addPpmOutsourcUpdateInfos(List<PpmOutsourcUpdateInfoDto> updateInfoDtos) ;

    /**
     * 查询ext集合
     * @param infoDto 对象
     * @return PpmOutsourcUpdateInfoDto
     */
    List<PpmOutsourcUpdateInfoDto> queryDetailExtList(PpmOutsourcUpdateInfoDto infoDto,String uniqueName);

    /**
     * 查询ext
     * @param infoDto 对象
     * @return PpmOutsourcUpdateInfoDto
     */
    PpmOutsourcUpdateInfoDto queryDetailExt(PpmOutsourcUpdateInfoDto infoDto,String uniqueName);

    /**
     * 更新
     * @param ppmOutsourcUpdateInfoDto  对象
     * @return int
     */
    int updatePpmOutsourcUpdateInfo(PpmOutsourcUpdateInfoDto ppmOutsourcUpdateInfoDto) ;

    /**
     * 查询详情 - 集合
     * @param instanceId 流程编号
     * @return List<PpmEarlyCourseApplyInfoDto>
     */
    List<PpmOutsourcUpdateInfoDto> queryListForInstanceId(String instanceId) ;

    /**
     * 查询详情 - 集合
     * @param ppmOutsourcUpdateInfoDto 流程编号
     * @return List<PpmEarlyCourseApplyInfoDto>
     */
    List<PpmOutsourcUpdateInfoDto> queryBaseList(PpmOutsourcUpdateInfoDto ppmOutsourcUpdateInfoDto) ;
    /**
     * 删除
     * @param instanceId
     * @return Integer
     */
    Integer deleteByInstanceId(String instanceId) ;

}
