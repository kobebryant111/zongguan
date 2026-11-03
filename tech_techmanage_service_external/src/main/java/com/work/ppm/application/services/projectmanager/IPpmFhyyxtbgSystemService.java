package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmFhyyxtbgSystemDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmFhyyxtbgSystemService {


    /**
     * 添加
     * @param sysList 对象
     * @return int
     */
    int addPpmFhyyxtbgSystem( List<PpmFhyyxtbgSystemDto> sysList) ;

    /**
     * 查询集合
     * @param refinstanceid 流程id
     * @return PpmFhyyxtbgSystemDto
     */
    List<PpmFhyyxtbgSystemDto> queryDetail(String refinstanceid) ;

    /**
     * 更新
     * @param sysDto 对象
     */
    void updateFhyyxtbgSystem(PpmFhyyxtbgSystemDto sysDto);
}
