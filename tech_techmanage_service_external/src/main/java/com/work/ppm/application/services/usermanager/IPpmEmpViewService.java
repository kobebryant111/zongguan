package com.work.ppm.application.services.usermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.usermanager.PpmEmpView;

/**
 * 员工视图接口
 */
public interface IPpmEmpViewService {

    /**
     * 分页
     * @param ppmEmpView 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmEmpView ppmEmpView, Pager pager) ;

    /**
     * 添加
     * @param ppmEmpView 对象
     * @return int
     */
     @Deprecated
    int addPpmEmpView(PpmEmpView ppmEmpView) ;

    /**
     * 查询详情
     * @param ppmEmpView 对象
     * @return PpmEmpView
     */
    PpmEmpView queryDetail(PpmEmpView ppmEmpView) ;

    /**
     * 更新
     * @param ppmEmpView 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmEmpView(PpmEmpView ppmEmpView) ;

    /**
     * 删除
     * @param ppmEmpView 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmEmpView(PpmEmpView ppmEmpView) ;

    /**
     * 根据用户id查询用户名称
     * @param badge 工号
     * @return String
     */
    String getBadgeForName(String badge);
}
