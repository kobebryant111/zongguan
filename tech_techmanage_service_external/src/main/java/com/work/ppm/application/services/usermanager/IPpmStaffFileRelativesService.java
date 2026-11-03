package com.work.ppm.application.services.usermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.usermanager.PpmStaffFileRelatives;

import java.util.List;

/**
 * 行员档案家庭接口
 */
public interface IPpmStaffFileRelativesService {
    /**
     * 查询家庭集合
     * @param badge 工号
     * @return List<PpmStaffFileRelatives>
     */
    List<PpmStaffFileRelatives> getBadgeForList(String badge) ;

    /**
     * 分页
     * @param ppmStaffFileRelatives 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmStaffFileRelatives ppmStaffFileRelatives, Pager pager) ;

    /**
     * 查询详情
     * @param ppmStaffFileRelatives 对象
     * @return PpmStaffFileRelatives
     */
    @Deprecated
    PpmStaffFileRelatives queryDetail(PpmStaffFileRelatives ppmStaffFileRelatives) ;

    /**
     * 更新
     * @param ppmStaffFileRelatives 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmStaffFileRelatives(PpmStaffFileRelatives ppmStaffFileRelatives) ;

    /**
     * 删除
     * @param ppmStaffFileRelatives 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmStaffFileRelatives(PpmStaffFileRelatives ppmStaffFileRelatives) ;
}
