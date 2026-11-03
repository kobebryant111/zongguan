package com.work.ppm.application.services.kpi;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiItemSwitchItemDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKpiItemSwitchItemService {

    /**
     * 分页
     * @param ppmKpiItemSwitchItemDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiItemSwitchItemDto 对象
     * @return int
     */
     @Deprecated
    int addPpmKpiItemSwitchItem(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) ;

    /**
     * 查询详情
     * @param ppmKpiItemSwitchItemDto 对象
     * @return PpmKpiItemSwitchItemDto
     */
    @Deprecated
    PpmKpiItemSwitchItemDto queryDetail(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) ;

    /**
     * 更新
     * @param ppmKpiItemSwitchItemDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmKpiItemSwitchItem(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) ;

    /**
     * 删除
     * @param ppmKpiItemSwitchItemDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKpiItemSwitchItem(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) ;

    /**
     * 获取考核开关配置项表表数据
     * @param ppmKpiItemSwitchItemDto
     * @return List<PpmKpiItemSwitchItemDto>
     */
    List<PpmKpiItemSwitchItemDto> getPpmKpiItemSwitchItemList(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto);
}
