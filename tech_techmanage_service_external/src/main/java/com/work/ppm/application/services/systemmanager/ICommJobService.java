package com.work.ppm.application.services.systemmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.systemmanager.CommJobDto;
import com.work.ppm.application.model.systemmanager.CommJob;

import java.util.List;

/**
 * 岗位
 */
public interface ICommJobService {
    /**
     * 根据部门编号查询岗位
     * @param deptId 部门编号
     * @return List<CommJobDto>
     */
    List<CommJobDto> getJobsForDetpId(String deptId);
    /**
     * 分页
     * @param commJob 对象
     * @param pager 分页对象
     */
    void queryPager(CommJob commJob, Pager pager) ;

    /**
     * 查询详情
     * @param commJob 对象
     * @return CommJob
     */
    @Deprecated
    CommJob queryDetail(CommJob commJob) ;

    /**
     *  add
     * @param commJob 对象
     * @return Integer
     */
    Integer addCommJob(CommJob commJob) ;

    /**
     * 更新
     * @param commJob 对象
     * @return Integer
     */
    Integer updateCommJob(CommJob commJob) ;

    /**
     * 删除
     * @param commJob 对象
     * @return Integer
     */
    Integer deleteCommJob(CommJob commJob) ;


}
