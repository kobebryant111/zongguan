package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmWorkKjlCghtDetailDto;

/**
  * 接口描述
  */
public interface IPpmWorkKjlCghtDetailService {

    /**
     * 分页
     * @param ppmWorkKjlCghtDetailDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto, Pager pager) ;

    /**
     * 添加
     * @param ppmWorkKjlCghtDetailDto 对象
     * @return int
     */
     @Deprecated
    int addPpmWorkKjlCghtDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) ;

    /**
     * 查询详情
     * @param ppmWorkKjlCghtDetailDto 对象
     * @return PpmWorkKjlCghtDetailDto
     */
    @Deprecated
    PpmWorkKjlCghtDetailDto queryDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) ;

    /**
     * 更新
     * @param ppmWorkKjlCghtDetailDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmWorkKjlCghtDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) ;

    /**
     * 删除
     * @param ppmWorkKjlCghtDetailDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmWorkKjlCghtDetail(PpmWorkKjlCghtDetailDto ppmWorkKjlCghtDetailDto) ;
}
