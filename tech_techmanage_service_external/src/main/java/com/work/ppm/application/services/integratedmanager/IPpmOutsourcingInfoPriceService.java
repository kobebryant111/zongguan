package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingInfoPriceDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingInfoPrice;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 外包人员价格
 */
public interface IPpmOutsourcingInfoPriceService {

    /**
     * 分页
     * @param ppmOutsourcingInfoPrice 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingInfoPrice 对象
     * @return int
     */
    int addPpmOutsourcingInfoPrice(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) ;

    /**
     * 查询详情
     * @param ppmOutsourcingInfoPrice 对象
     * @return PpmOutsourcingInfoPrice
     */
    PpmOutsourcingInfoPrice queryDetail(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) ;

    /**
     * 更新
     * @param ppmOutsourcingInfoPrice 对象
     * @return Integer
     */
    Integer updatePpmOutsourcingInfoPrice(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) ;

    /**
     * 删除
     * @param ppmOutsourcingInfoPrice 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmOutsourcingInfoPrice(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) ;

    /**
     * 查询外包商单价
     * @param id 外包商编号
     * @param position 人员类型编号
     * @return PpmOutsourcingInfoPriceDto
     */
    PpmOutsourcingInfoPriceDto getOutsourcPrices(Long id, String position);


    /**
     * 查询外包商单价数据
     * @param ppmOutsourcingInfoPrice 对象
     * @return List<PpmOutsourcingInfoPriceDto>
     */
    List<PpmOutsourcingInfoPriceDto> queryList(PpmOutsourcingInfoPriceDto ppmOutsourcingInfoPrice);

    /**
     * 查询外包商单价数据
     * @param ppmOutsourcingInfoPrice 对象
     * @return List<PpmOutsourcingInfoPriceDto>
     */
    List<PpmOutsourcingInfoPriceDto> exportExcel(PpmOutsourcingInfoPriceDto ppmOutsourcingInfoPrice);


    /**
     * 删除多余数据
     * @param infoDtos list
     * @param orgCode 供应商编号
     * @return int
     */
    int deleteInId(List<PpmOutsourcingInfoPriceDto> infoDtos, String orgCode );
}
