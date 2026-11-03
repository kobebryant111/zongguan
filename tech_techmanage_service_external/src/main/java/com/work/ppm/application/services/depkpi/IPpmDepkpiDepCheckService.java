package com.work.ppm.application.services.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepCheckDto;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiDepCheckInfo;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 考核详情存放数据接口
 *
 */
public interface IPpmDepkpiDepCheckService {

    /**
     * 管理员考核分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     */
    void queryCheckPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager);

    /**
     * 查询详情
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return PpmDepkpiDepCheckDto
     */
    PpmDepkpiDepCheckDto queryDetail(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 自定义考核制定提交（修改状态为执行中） 公共提交使用同个接口
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return int
     */
    int updatePpmDepkpiDepCheck(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 考核制定提交（修改状态为执行中）
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return int
     */
    int commitZbzd(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 我的待办数量查询
     *
     * @param userid userid
     * @return list
     */
    List<PpmDepkpiDepCheckInfo> queryMyDbNum(String userid);

    /**
     * 自定义指标制定分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     */
    void queryZdyzbzdPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager);

    /**
     * 自定义指标自评分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     */
    void queryZdyzbzpPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager);

    /**
     * 公共指标制定分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     */
    void queryGgzbzdPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager);

    /**
     * 自评提交
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return int
     */
    int zpCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 自定义评价提交
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return int
     */
    int zdyPjCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 添加信息到check表 （二级部制定发起）
     *
     * @param pdd 对象
     * @return int
     */
    int insertCheck(PpmDepkpiDepCheckDto pdd);

    /**
     * 修改考核项状态
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return int
     */
    int upCheckStatus(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 评价分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     */
    void queryPjPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager);

    /**
     * 部门已提交指标查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager
     */
    void getListPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager);

    /**
     * 获取考核项树列表
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return list
     */
    List<PpmDepkpiDepCheckDto> getCheckTree(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 修改check表代办人和状态 （二级部评价发起）
     *
     * @param pdd 对象
     * @return int
     */
    int updateCheck(PpmDepkpiDepCheckDto pdd);

    /**
     * 自定义评价提交
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return int
     */
    int ggPjCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 公共考核数据查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return 对象
     */
    PpmDepkpiDepCheckInfo queryCheckSum(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 公共考核分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     */
    void queryGgCheckPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager);

    /**
     * 公共考核明细分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     */
    void queryGgCheckDetailPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager);

    /**
     * 考核数据查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return 对象
     */
    PpmDepkpiDepCheckDto queryCheckSumAndPoint(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);

    /**
     * 查询单个指标汇总
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return PpmDepkpiContentInfo
     */
    PpmDepkpiDepCheckInfo querySumPointById(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto);
}
