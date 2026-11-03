package com.work.ppm.application.services.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepmodelDetailTmpDto;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 部门模板临时详情表
 *
 */
public interface IPpmDepkpiDepmodelDetailTmpService {

    /**
     * 分页
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @param pager                         分页对象
     */
    void queryPager(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto, Pager pager);

    /**
     * 添加
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @return int
     */
    int addPpmDepkpiDepmodelDetailTmp(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto);

    /**
     * 查询详情
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @return PpmDepkpiDepmodelDetailTmpDto
     */
    PpmDepkpiDepmodelDetailTmpDto queryDetail(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto);

    /**
     * 查询部门模板详情数据（部门已分配指标查询接口）
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @return int
     */
    List<PpmDepkpiDepmodelDetailTmpDto> getDepkpiDepmodelDetailTmpList(PpmDepkpiDepmodelDetailTmpDto
                                                                               ppmDepkpiDepmodelDetailTmpDto);

    /**
     * 部门分配指标
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @param userid                        userid
     * @return int
     */
    int addDepkpiDepmodelDetailTmpDto(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto, String userid);


    /**
     * 部门删除已分配指标(并且删除其子集)
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @param userid                        userid
     * @return int
     */
    int deleteDepTarget(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto, String userid);

    /**
     * 更换模板请求 - 清空模板详情
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @return int
     */
    int deleteDepTargetByDepid(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto);

    /**
     * 部门修改已分配指标
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @return int
     */
    int updateDetail(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto);

    /**
     * 部门修改已分配指标
     * 根据模板id查询
     *
     * @param ppmDepkpiDepmodelDetailDto 对象
     * @return PpmDepkpiDepmodelDetailTmpDto
     */
    List<PpmDepkpiDepmodelDetailTmpDto> queryByDepId(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailDto);

    /**
     * 查询子节点没有指标类型的总数
     *
     * @param ppmDepkpiDepmodelDetailDto 对象
     * @return Long
     */
    Long queryCount(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailDto);
}