package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImMenusDto;
import com.work.ppm.application.model.systemmanager.ImMenus;

import java.util.List;

/**
 * 菜单
 */
public interface IImMenusService {

    /**
     * 菜单树
     * @param imMenus 对象
     * @return  List<ImMenusDto>
     */
    List<ImMenusDto> queryMenusTree(ImMenus imMenus) ;

    /**
     * 查询详情
     * @param imMenus 对象
     * @return ImMenus
     */
    @Deprecated
    ImMenus queryDetail(ImMenus imMenus) ;

    /**
     * 更新
     * @param imMenus 对象
     * @return Integer
     */
    Integer updateImMenus(ImMenus imMenus) ;

    /**
     * 删除
     * @param imMenus 对象
     * @return Integer
     */
    Integer deleteImMenus(ImMenus imMenus) ;

    /**
     * 获取登录权限
     * @param rid 角色id
     * @param user session
     * @return ImMenusDto
     */
    ImMenusDto getLoginMenus(String rid, CommOperatorDoLoginDto user) ;

    /**
     * 根据集合mid 查询多条菜单数据
     * @param mids 菜单id
     * @return list
     */
    List<ImMenus> queryMeunsList(List<String> mids);

    /**
     * 操作权限菜单树
     * @param imMenus 对象
     * @return  List<ImMenusDto>
     */
    List<ImMenusDto> queryOperaMenusTree(ImMenus imMenus) ;
}
