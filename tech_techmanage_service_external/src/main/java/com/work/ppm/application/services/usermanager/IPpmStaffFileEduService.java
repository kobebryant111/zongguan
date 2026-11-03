package com.work.ppm.application.services.usermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.usermanager.PpmStaffFileEdu;

import java.util.List;

/**
 * 行员档案教育接口
 */
public interface IPpmStaffFileEduService {
    /**
     * 获取教育信息
     * @param badge 工号
     * @return List<PpmStaffFileEdu>
     */
    List<PpmStaffFileEdu> getBadgeForList(String badge) ;
    /**
     * 分页
     * @param ppmStaffFileEdu 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmStaffFileEdu ppmStaffFileEdu, Pager pager) ;

    /**
     * 查询详情
     * @param ppmStaffFileEdu
     * @return PpmStaffFileEdu
     */
    @Deprecated
    PpmStaffFileEdu queryDetail(PpmStaffFileEdu ppmStaffFileEdu) ;

    /**
     * 更新
     * @param ppmStaffFileEdu
     * @return Integer
     */
    @Deprecated
    Integer updatePpmStaffFileEdu(PpmStaffFileEdu ppmStaffFileEdu) ;

    /**
     * 删除
     * @param ppmStaffFileEdu
     * @return Integer
     */
    @Deprecated
    Integer deletePpmStaffFileEdu(PpmStaffFileEdu ppmStaffFileEdu) ;
}
