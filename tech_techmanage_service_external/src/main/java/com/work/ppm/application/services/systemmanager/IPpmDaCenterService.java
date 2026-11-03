package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.PpmDaCenterDto;

import java.util.List;

/**
  * 中心接口
  */
public interface IPpmDaCenterService {

    /**
     * 获取中心
     * @return list
     */
    List<PpmDaCenterDto> getList() ;

    /**
     * 根据中心id查询部门集合
     * @param centerId 中心id
     * @return List
     */
    List<PpmDaCenterDto> queryDeptList(String centerId);
    /**
     * 根据中心ids查询部门集合
     * @param centerIds 中心id
     * @return List
     */
    List<PpmDaCenterDto> queryDeptListBycenterIds(String centerIds);
    /**
     * 根据部门id查询中心
     * @param depId 部门id
     * @return PpmDaCenterDto
     */
    PpmDaCenterDto queryCenter(String depId);

    /**
     * 查询中心数据
     * @param ppmDaCenterDto
     * @return List<PpmDaCenterDto>
     */
     List<PpmDaCenterDto> queryLists(PpmDaCenterDto ppmDaCenterDto);
}
