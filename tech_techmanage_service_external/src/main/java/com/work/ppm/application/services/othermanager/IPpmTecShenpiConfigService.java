package com.work.ppm.application.services.othermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.othermanager.PpmTecShenpiConfigDto;

/**
  * 接口描述
  */
public interface IPpmTecShenpiConfigService {

    /**
     * 分页
     * @param ppmTecShenpiConfigDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmTecShenpiConfigDto ppmTecShenpiConfigDto, Pager pager) ;

    /**
     * 添加
     * @param ppmTecShenpiConfigDto 对象
     * @return int
     */
     @Deprecated
    int addPpmTecShenpiConfig(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) ;

    /**
     * 查询详情
     * @param ppmTecShenpiConfigDto 对象
     * @return PpmTecShenpiConfigDto
     */
    PpmTecShenpiConfigDto queryDetail(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) ;

    /**
     * 更新
     * @param ppmTecShenpiConfigDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmTecShenpiConfig(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) ;

    /**
     * 删除
     * @param ppmTecShenpiConfigDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmTecShenpiConfig(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) ;

    /**
     * 根据部门号查询详情
     * @param ppmTecShenpiConfigDto
     * @return PpmTecShenpiConfigDto
     */
    PpmTecShenpiConfigDto queryDetailByDeptno(PpmTecShenpiConfigDto ppmTecShenpiConfigDto) ;
}
