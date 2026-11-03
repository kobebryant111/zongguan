package com.work.ppm.application.services.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepmodelDetailDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiDepmodelDetail;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 部门模板服务
 *
 */
public interface IPpmDepkpiDepmodelDetailService {

    /**
     * 分页
     *
     * @param ppmDepkpiDepmodelDetailDto 对象
     * @param pager                      分页对象
     */
    void queryPager(PpmDepkpiDepmodelDetailDto ppmDepkpiDepmodelDetailDto, Pager pager);

    /**
     * 部门提交接口
     *
     * @param ppmDepkpiDepmodelDetailDto 对象
     * @param userid                     userid
     * @return int
     */
    int commitDepModelTarget(PpmDepkpiDepmodelDetailDto ppmDepkpiDepmodelDetailDto, String userid);

    /**
     * 通过
     *
     * @param ppmDepkpiDepmodelDetailDto 对象
     * @return List<PpmDepkpiDepRelDto>
     */
    List<PpmDepkpiDepmodelDetail> queryPpmDepkpiTargetList(PpmDepkpiDepmodelDetailDto ppmDepkpiDepmodelDetailDto);

}