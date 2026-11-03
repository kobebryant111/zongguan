package com.work.ppm.application.services.kpi;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiParamsDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKpiParamsService {

    /**
     * 分页
     * @param ppmKpiParamsDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmKpiParamsDto ppmKpiParamsDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiParamsDto 对象
     * @return int
     */
    int addPpmKpiParams(PpmKpiParamsDto ppmKpiParamsDto) ;

    /**
     * 查询详情
     * @param ppmKpiParamsDto 对象
     * @return PpmKpiParamsDto
     */
    PpmKpiParamsDto queryDetail(PpmKpiParamsDto ppmKpiParamsDto) ;

    /**
     * 更新
     * @param ppmKpiParamsDto  对象
     * @return int
     */
    int updatePpmKpiParams(PpmKpiParamsDto ppmKpiParamsDto) ;

    /**
     * 删除
     * @param ppmKpiParamsDto 对象
     * @return int
     */
    int deletePpmKpiParams(PpmKpiParamsDto ppmKpiParamsDto) ;

    /**
     * 拆线呢参数
     * @param ppmKpiParamsDto
     * @return List<PpmKpiParamsDto>
     */
    List<PpmKpiParamsDto> queryLists(PpmKpiParamsDto ppmKpiParamsDto);
}
