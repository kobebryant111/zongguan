package com.work.ppm.application.services.depkpi;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDetailDto;

import java.util.List;

/**
 * 模板详情
 *
 */
public interface IPpmDepkpiModelDetailService {

    /**
     * 分页
     *
     * @param ppmDepkpiModelDetailDto 对象
     * @param pager                   分页对象
     */

    void queryPager(PpmDepkpiModelDetailDto ppmDepkpiModelDetailDto, Pager pager);

    /**
     * 查询详情
     *
     * @param ppmDepkpiModelDetailDto 对象
     * @return PpmDepkpiModelDetailDto
     */

    PpmDepkpiModelDetailDto queryDetail(PpmDepkpiModelDetailDto ppmDepkpiModelDetailDto);

    /**
     * 模板提交
     *
     * @param ppmDepkpiModelDetailDto 对象
     * @param userid                  对象
     * @return int
     */
    int commitModelTarget(PpmDepkpiModelDetailDto ppmDepkpiModelDetailDto, String userid);


    /**
     * 根据模板id查询
     *
     * @param ppmDepkpiModelDto 对象
     * @return list
     */
    List<PpmDepkpiModelDetailDto> queryByMid(PpmDepkpiModelDetailDto ppmDepkpiModelDto);
}
