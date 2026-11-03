package com.work.ppm.application.services.depkpi;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.depkpi.PpmDepKpiLaunchCenterDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmDepKpiLaunchCenterService {

    /**
     * 分页
     * @param ppmDepKpiLaunchCenterDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto, Pager pager) ;

    /**
     * 添加
     * @param ppmDepKpiLaunchCenterDto 对象
     * @return int
     */
    int addPpmDepKpiLaunchCenter(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto) ;

    /**
     * 查询详情
     * @param ppmDepKpiLaunchCenterDto 对象
     * @return PpmDepKpiLaunchCenterDto
     */
    PpmDepKpiLaunchCenterDto queryDetail(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto) ;

    /**
     * 更新
     * @param ppmDepKpiLaunchCenterDto  对象
     * @return int
     */
    int updatePpmDepKpiLaunchCenter(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto) ;

    /**
     * 创建发起中心数据
     * @param ppmDepKpiLaunchCenterDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int createPpmDepKpiLaunchCenter(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto,String userid);

    /**
     *  获取发起中心数据
     * @param ppmDepKpiLaunchCenterDto 对象
     * @return  List<PpmDepKpiLaunchCenterDto>
     */
    List<PpmDepKpiLaunchCenterDto> getPpmDepKpiLaunchCenterList(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto);



}
