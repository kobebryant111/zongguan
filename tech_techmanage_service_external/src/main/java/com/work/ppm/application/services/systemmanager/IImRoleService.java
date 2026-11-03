package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.ImRoleDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleTreeDto;
import com.work.ppm.application.model.systemmanager.ImRole;

import java.util.List;

/**
 * 角色接口
 */
public interface IImRoleService {

    /**
     * 分页
     * @param imRole 对象
     * @param pager
     */
    @Deprecated
    void queryPager(ImRole imRole, Pager pager) ;

    /**
     * 查询详情
     * @param imRole 对象
     * @return ImRole
     */
    ImRole queryDetail(ImRole imRole) ;

    /**
     * 更新
     * @param imRole 对象
     * @return 1:添加，2：更新
     */
    int updateImRole(ImRoleDto imRole) ;

    /**
     * 删除
     * @param imRole 对象
     * @return Integer
     */
    Integer deleteImRole(ImRole imRole) ;

    /**
     * 根据用户id查询用户拥有几个角色
     * @param userid 工号
     * @return List<ImRoleDoLoginDto>
     */
    List<ImRoleDoLoginDto> getImRole(String userid) ;

    /**
     * 获取用户角色数
     * @return List<ImRoleTreeDto>
     */
    List<ImRoleTreeDto> queryRoleTree() ;

    /**
     * 获取操作权限角色树
     * @return List<ImRoleTreeDto>
     */
    List<ImRoleTreeDto> queryOperaRoleTree() ;
}
