package com.work.ppm.application.services.systemmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.systemmanager.CommParttimejob;

/**
 * 行员兼职
 */
public interface ICommParttimejobService {

    /**
     * 分页
     * @param commParttimejob 对象
     * @param pager 分页对象
     */
    void queryPager(CommParttimejob commParttimejob, Pager pager) ;

    /**
     * 查询兼职部门编号
     * @param badge 工号
     * @return 拼接后的部门编号，'1800','2831'
     */
    String queryDeptIdByBadge(String badge);

    /**
     * 查询详情
     * @param commParttimejob 对象
     * @return CommParttimejob
     */
    @Deprecated
    CommParttimejob queryDetail(CommParttimejob commParttimejob) ;

    /**
     * 更新
     * @param commParttimejob 对象
     * @return Integer
     */
    @Deprecated
    Integer updateCommParttimejob(CommParttimejob commParttimejob) ;

    /**
     * 删除
     * @param commParttimejob 对象
     * @return Integer
     */
    @Deprecated
    Integer deleteCommParttimejob(CommParttimejob commParttimejob) ;
}
