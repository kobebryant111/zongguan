package com.work.ppm.application.services.systemmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.systemmanager.CommOperatorrole;

/**
 * 员工登录角色
 */
public interface ICommOperatorroleService {

    /**
     * 分页
     * @param commOperatorrole 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(CommOperatorrole commOperatorrole, Pager pager) ;

    /**
     * 添加
     * @param commOperatorrole 对象
     * @return int
     */
    int addCommOperatorrole(CommOperatorrole commOperatorrole) ;
    /**
     * 查询详情
     * @param commOperatorrole 对象
     * @return CommOperatorrole
     */
    @Deprecated
    CommOperatorrole queryDetail(CommOperatorrole commOperatorrole) ;

    /**
     * 更新
     * @param commOperatorrole 对象
     * @return Integer
     */
    @Deprecated
    Integer updateCommOperatorrole(CommOperatorrole commOperatorrole) ;

    /**
     * 删除
     * @param commOperatorrole 对象
     * @return Integer
     */
    @Deprecated
    Integer deleteCommOperatorrole(CommOperatorrole commOperatorrole) ;
}
