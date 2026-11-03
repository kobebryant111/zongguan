package com.work.ppm.application.services.systemmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.systemmanager.CommRole;
import com.work.ppm.application.model.systemmanager.ext.CommRoleInfo;

/**
 * 角色
 */
public interface ICommRoleService {

    /**
     * 分页
     * @param commRole 对象
     * @param pager 分页对象
     */
    void queryPager(CommRoleInfo commRole, Pager pager) ;

    /**
     * 查询详情
     * @param commRole 对象
     * @return CommRole
     */
    @Deprecated
    CommRole queryDetail(CommRole commRole) ;

    /**
     * 更新
     * @param commRole 对象
     * @return Integer
     */
    @Deprecated
    Integer updateCommRole(CommRole commRole) ;

    /**
     * 删除
     * @param commRole 对象
     * @return Integer
     */
    @Deprecated
    Integer deleteCommRole(CommRole commRole) ;
}
