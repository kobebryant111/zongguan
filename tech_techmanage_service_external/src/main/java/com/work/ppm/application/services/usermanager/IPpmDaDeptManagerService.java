package com.work.ppm.application.services.usermanager;

import com.work.ppm.application.dto.integratedmanager.PpmDaDeptManagerDto;
import com.work.ppm.application.model.usermanager.PpmDaDeptManager;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 部门中心分管经理配置接口
 */
public interface IPpmDaDeptManagerService {

    /**
     * 分页
     * @param ppmDaDeptManager 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmDaDeptManager ppmDaDeptManager, Pager pager) ;

    /**
     * 添加
     * @param ppmDaDeptManager 对象
     * @return int
     */
     @Deprecated
    int addPpmDaDeptManager(PpmDaDeptManager ppmDaDeptManager) ;

    /**
     * 查询详情
     * @param ppmDaDeptManager 对象
     * @return PpmDaDeptManager
     */
    PpmDaDeptManager queryDetail(PpmDaDeptManager ppmDaDeptManager) ;

    /**
     * 更新
     * @param ppmDaDeptManager 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmDaDeptManager(PpmDaDeptManager ppmDaDeptManager) ;

    /**
     * 删除
     * @param ppmDaDeptManager 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmDaDeptManager(PpmDaDeptManager ppmDaDeptManager) ;

    /**
     * 根据部门编号查询工号
     * @param deptId  部门id
     * @return  String
     */
    String queryBadgeForDetpId(String deptId);

    /**
     * 根据用户id查询 总经理权限
     * @param userId 工号
     * @return list
     */
    List<PpmDaDeptManagerDto> queryGeneral(String userId);
}
