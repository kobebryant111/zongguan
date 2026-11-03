package com.work.ppm.application.services.depkpi;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.depkpi.PpmDepKpiConfigurationItemDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmDepKpiConfigurationItemService {

    /**
     * 分页
     * @param ppmDepKpiConfigurationItemDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto, Pager pager) ;

    /**
     * 添加
     * @param ppmDepKpiConfigurationItemDto 对象
     * @return int
     */
    int addPpmDepKpiConfigurationItem(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) ;

    /**
     * 查询详情
     * @param ppmDepKpiConfigurationItemDto 对象
     * @return PpmDepKpiConfigurationItemDto
     */
    PpmDepKpiConfigurationItemDto queryDetail(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) ;

    /**
     * 更新
     * @param ppmDepKpiConfigurationItemDto  对象
     * @return int
     */
    int updatePpmDepKpiConfigurationItem(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) ;

    /**
     * 删除
     * @param ppmDepKpiConfigurationItemDto 对象
     * @return int
     */
    int deletePpmDepKpiConfigurationItem(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) ;

    /**
     *  获得部门考核配置项数据
     * @param ppmDepKpiConfigurationItemDto 对象
     * @return  List<PpmDepKpiConfigurationItemDto>
     */
    List<PpmDepKpiConfigurationItemDto> getPpmDepKpiConfigurationItemList(
            PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto);

}
