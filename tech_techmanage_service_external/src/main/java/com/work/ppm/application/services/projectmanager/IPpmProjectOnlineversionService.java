package com.work.ppm.application.services.projectmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.projectmanager.PpmProjectOnlineversionDto;

/**
  * 接口描述
  */
public interface IPpmProjectOnlineversionService {

    /**
     * 分页
     * @param ppmProjectOnlineversionDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmProjectOnlineversionDto ppmProjectOnlineversionDto, Pager pager) ;

    /**
     * 添加
     * @param ppmProjectOnlineversionDto 对象
     * @return int
     */
    int addPpmProjectOnlineversion(PpmProjectOnlineversionDto ppmProjectOnlineversionDto) ;

    /**
     * 查询详情
     * @param ppmProjectOnlineversionDto 对象
     * @return PpmProjectOnlineversionDto
     */
    @Deprecated
    PpmProjectOnlineversionDto queryDetail(PpmProjectOnlineversionDto ppmProjectOnlineversionDto) ;

    /**
     * 更新
     * @param ppmProjectOnlineversionDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmProjectOnlineversion(PpmProjectOnlineversionDto ppmProjectOnlineversionDto) ;

    /**
     * 删除
     * @param ppmProjectOnlineversionDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmProjectOnlineversion(PpmProjectOnlineversionDto ppmProjectOnlineversionDto) ;

    /**
     * 添加(批量)
     * @param ppmProjectOnlineversionDtos 对象
     * @return int
     */
    int addPpmProjectOnlineversionBatch(PpmProjectOnlineversionDto[] ppmProjectOnlineversionDtos) ;
}
