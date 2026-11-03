package com.work.ppm.application.services.usermanager;

import com.work.ppm.application.model.usermanager.PpmDaTeamUser;
import com.work.ppm.commons.pager.Pager;

/**
 * 小组人员接口
 */
public interface IPpmDaTeamUserService {

    /**
     * 分页
     * @param ppmDaTeamUser 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmDaTeamUser ppmDaTeamUser, Pager pager) ;

    /**
     * 添加
     * @param ppmDaTeamUser 对象
     * @return int
     */
    int addPpmDaTeamUser(PpmDaTeamUser ppmDaTeamUser) ;

    /**
     * 查询详情
     * @param ppmDaTeamUser 对象
     * @return PpmDaTeamUser
     */
    PpmDaTeamUser queryDetail(PpmDaTeamUser ppmDaTeamUser) ;

    /**
     * 更新
     * @param ppmDaTeamUser 对象
     * @return Integer
     */
    Integer updatePpmDaTeamUser(PpmDaTeamUser ppmDaTeamUser) ;

    /**
     * 删除
     * @param ppmDaTeamUser 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmDaTeamUser(PpmDaTeamUser ppmDaTeamUser) ;

    /**
     * 根据工号查询详情
     * @param ppmDaTeamUser
     * @return PpmDaTeamUser
     */
    PpmDaTeamUser queryDetailByUserid(PpmDaTeamUser ppmDaTeamUser);


}
