package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmTaskplanDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmTaskplanService {

    /**
     * 分页
     * @param ppmTaskplanDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmTaskplanDto ppmTaskplanDto, Pager pager) ;

    /**
     * 添加
     * @param ppmTaskplanDto 对象
     * @return int
     */
    int addPpmTaskplan(PpmTaskplanDto ppmTaskplanDto) ;

    /**
     * 查询详情
     * @param ppmTaskplanDto 对象
     * @return PpmTaskplanDto
     */
    @Deprecated
    PpmTaskplanDto queryDetail(PpmTaskplanDto ppmTaskplanDto) ;

    /**
     * 更新
     * @param ppmTaskplanDto  对象
     * @return int
     */
    int updatePpmTaskplan(PpmTaskplanDto ppmTaskplanDto) ;

    /**
     * 删除
     * @param ppmTaskplanDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmTaskplan(PpmTaskplanDto ppmTaskplanDto) ;

    /**
     * 根据上线编号查询是否有上线版本
     * @param planno
     * @return List<PpmTaskplanDto>
     */
    List<PpmTaskplanDto> queryTaskPlan(String planno);
}
