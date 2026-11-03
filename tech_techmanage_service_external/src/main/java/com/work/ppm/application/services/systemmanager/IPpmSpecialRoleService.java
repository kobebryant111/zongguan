package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.PpmSpecialRoleDto;

/**
  * 特殊角色菜单关联
  */
public interface IPpmSpecialRoleService {

    /**
     * 添加
     * @param ppmSpecialRoleDto 对象
     * @return int
     */
    int addPpmSpecialRole(PpmSpecialRoleDto ppmSpecialRoleDto) ;

    /**
     * 查询详情
     * @param ppmSpecialRoleDto 对象
     * @return PpmSpecialRoleDto
     */
    @Deprecated
    PpmSpecialRoleDto queryDetail(PpmSpecialRoleDto ppmSpecialRoleDto) ;

    /**
     * 更新
     * @param ppmSpecialRoleDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmSpecialRole(PpmSpecialRoleDto ppmSpecialRoleDto) ;

    /**
     * 删除
     * @param ppmSpecialRoleDto 对象
     * @return int
     */
    int deletePpmSpecialRole(PpmSpecialRoleDto ppmSpecialRoleDto) ;
}
