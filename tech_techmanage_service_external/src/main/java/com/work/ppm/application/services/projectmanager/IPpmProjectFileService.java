package com.work.ppm.application.services.projectmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.projectmanager.PpmProjectFileDto;

/**
  * 接口描述
  */
public interface IPpmProjectFileService {

    /**
     * 分页
     * @param ppmProjectFileDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmProjectFileDto ppmProjectFileDto, Pager pager) ;

    /**
     * 添加
     * @param ppmProjectFileDto 对象
     * @return int
     */
    int addPpmProjectFile(PpmProjectFileDto ppmProjectFileDto) ;

    /**
     * 查询详情
     * @param ppmProjectFileDto 对象
     * @return PpmProjectFileDto
     */
    @Deprecated
    PpmProjectFileDto queryDetail(PpmProjectFileDto ppmProjectFileDto) ;

    /**
     * 更新
     * @param ppmProjectFileDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmProjectFile(PpmProjectFileDto ppmProjectFileDto) ;

    /**
     * 删除
     * @param ppmProjectFileDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmProjectFile(PpmProjectFileDto ppmProjectFileDto) ;
}
