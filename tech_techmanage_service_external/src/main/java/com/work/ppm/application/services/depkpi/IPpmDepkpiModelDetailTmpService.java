package com.work.ppm.application.services.depkpi;

import com.work.ppm.application.model.depkpi.ext.PpmDepkpiModelDetailTmpInfo;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDetailTmpDto;

import java.util.List;

/**
 * 模板详情临时
 *
 */
public interface IPpmDepkpiModelDetailTmpService {

    /**
     * 分页
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @param pager                      分页对象
     */
    void queryPager(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto, Pager pager);

    /**
     * 查询详情
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @return PpmDepkpiModelDetailTmpDto
     */

    PpmDepkpiModelDetailTmpDto queryDetail(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto);

    /**
     * 更新
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @param userid                     对象
     * @return int
     */

    int updateModelTarget(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto, String userid);

    /**
     * 模板已分配指标查询
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @return list
     */
    List<PpmDepkpiModelDetailTmpInfo> queryDepModelTargetList(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto);

    /**
     * 添加模板已分配指标
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @param userid                     对象
     * @return int
     */
    int modelAssignTarget(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto, String userid);

    /**
     * 模板删除已分配指标
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @param userid                     对象
     * @return int
     */
    int deleteModelTarget(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto, String userid);

    /**
     * 根据模板id查询
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @return PpmDepkpiModelDetailTmpDto
     */

    List<PpmDepkpiModelDetailTmpDto> queryByModelId(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto);

    /**
     * 查询子节点没有指标类型的总数
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @return Long
     */
    Long queryCount(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto);
}
