package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingSystemnoDto;

/**
  * 接口描述
  */
public interface IPpmOutsourcingSystemnoService {

    /**
     * 分页
     * @param ppmOutsourcingSystemnoDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingSystemnoDto 对象
     * @return int
     */
     @Deprecated
    int addPpmOutsourcingSystemno(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) ;

    /**
     * 查询详情
     * @param ppmOutsourcingSystemnoDto 对象
     * @return PpmOutsourcingSystemnoDto
     */
    @Deprecated
    PpmOutsourcingSystemnoDto queryDetail(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) ;

    /**
     * 更新
     * @param ppmOutsourcingSystemnoDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmOutsourcingSystemno(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) ;

    /**
     * 删除
     * @param ppmOutsourcingSystemnoDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmOutsourcingSystemno(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) ;
}
