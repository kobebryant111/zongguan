package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingPriceDto;

/**
  * 接口描述
  */
public interface IPpmOutsourcingPriceService {

    /**
     * 分页
     * @param ppmOutsourcingPriceDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmOutsourcingPriceDto ppmOutsourcingPriceDto, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingPriceDto 对象
     * @return int
     */
     @Deprecated
    int addPpmOutsourcingPrice(PpmOutsourcingPriceDto ppmOutsourcingPriceDto) ;

    /**
     * 查询详情
     * @param ppmOutsourcingPriceDto 对象
     * @return PpmOutsourcingPriceDto
     */
    @Deprecated
    PpmOutsourcingPriceDto queryDetail(PpmOutsourcingPriceDto ppmOutsourcingPriceDto) ;

    /**
     * 更新
     * @param ppmOutsourcingPriceDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmOutsourcingPrice(PpmOutsourcingPriceDto ppmOutsourcingPriceDto) ;

    /**
     * 删除
     * @param ppmOutsourcingPriceDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmOutsourcingPrice(PpmOutsourcingPriceDto ppmOutsourcingPriceDto) ;
}
