package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.AapSystemPropertyDto;

import java.util.List;

/**
 * 系统属性
 */
public interface IAapSystemPropertyService {
    /**
     * 查询详情
     * @param userId 工号
     * @return AapSystemPropertyDto
     */
    List<AapSystemPropertyDto> queryList(String userId) ;


}
