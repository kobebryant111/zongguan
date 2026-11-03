package com.work.ppm.application.services.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepModelDto;
import com.work.ppm.commons.pager.Pager;

/**
 * 部门模板分配接口
 *
 */
public interface IPpmDepkpiDepModelService {

    /**
     * 分页
     *
     * @param ppmDepkpiDepModelDto 对象
     * @param pager                分页对象
     */
    void queryPager(PpmDepkpiDepModelDto ppmDepkpiDepModelDto, Pager pager);


    /**
     * 部门模板分配分页查询
     *
     * @param ppmDepKpiDepModeDto 对象
     * @param pager                分页对象
     */
    void queryList(PpmDepkpiDepModelDto ppmDepKpiDepModeDto, Pager pager);


    /**
     * 修改模板id（部门模板更换）
     *
     * @param ppmDepkpiDepModelDto 对象
     * @param userid               userid
     * @return int
     */
    int updateDepModel(PpmDepkpiDepModelDto ppmDepkpiDepModelDto, String userid);


    /**
     * 修改部门模板分配表提交状态
     *
     * @param ppmDepkpiModelDepDto 对象
     * @param userid               userid
     * @return int
     */
    int updateDepModelCommitFlag(PpmDepkpiDepModelDto ppmDepkpiModelDepDto, String userid);
}