package com.work.ppm.application.services.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiTargetDto;
import com.work.ppm.commons.pager.Pager;

import java.util.List;


/**
 * 指标
 *
 */
public interface IPpmDepkpiTargetService {

    /**
     * 新增指标数据
     *
     * @param ppmDepkpiTargetDto 对象
     * @param userid
     * @return int
     */
    int addPpmDepkpiTarget(PpmDepkpiTargetDto ppmDepkpiTargetDto, String userid);

    /**
     * 修改指标
     *
     * @param ppmDepkpiTargetDto 对象
     * @param userid             考核人
     * @return int
     */
    int updatePpmDepkpiTarget(PpmDepkpiTargetDto ppmDepkpiTargetDto, String userid);

    /**
     * 软删除
     *
     * @param ppmDepkpiTargetDto 对象
     * @return int
     */
    int deletePpmDepkpiTarget(PpmDepkpiTargetDto ppmDepkpiTargetDto, String userid);

    /**
     * 查询指标数据分页
     *
     * @param ppmDepkpiTargetDto 对象
     * @param pager
     */
    void queryPagerList(PpmDepkpiTargetDto ppmDepkpiTargetDto, Pager pager);

    /**
     * 查询指标列表
     *
     * @param ppmDepkpiTargetDto 对象
     * @return List
     */
    List<PpmDepkpiTargetDto> getPpmDepkpiTargetList(PpmDepkpiTargetDto ppmDepkpiTargetDto);
}
