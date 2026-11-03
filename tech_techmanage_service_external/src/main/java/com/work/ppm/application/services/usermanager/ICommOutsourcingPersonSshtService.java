package com.work.ppm.application.services.usermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.usermanager.CommOutsourcingPersonSsht;

/**
 * 项目外包所属合同
 */
public interface ICommOutsourcingPersonSshtService {

    /**
     * 分页
     * @param commOutsourcingPersonSsht 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(CommOutsourcingPersonSsht commOutsourcingPersonSsht, Pager pager) ;

    /**
     * 添加
     * @param commOutsourcingPersonSsht 对象
     * @return int
     */
    int addCommOutsourcingPersonSsht(CommOutsourcingPersonSsht commOutsourcingPersonSsht) ;

    /**
     * 查询详情
     * @param commOutsourcingPersonSsht 对象
     * @return CommOutsourcingPersonSsht
     */
    CommOutsourcingPersonSsht queryDetail(CommOutsourcingPersonSsht commOutsourcingPersonSsht) ;

    /**
     * 更新
     * @param commOutsourcingPersonSsht 对象
     * @return Integer
     */
    Integer updateCommOutsourcingPersonSsht(CommOutsourcingPersonSsht commOutsourcingPersonSsht) ;

    /**
     * 删除
     * @param commOutsourcingPersonSsht 对象
     * @return Integer
     */
    @Deprecated
    Integer deleteCommOutsourcingPersonSsht(CommOutsourcingPersonSsht commOutsourcingPersonSsht) ;
}
