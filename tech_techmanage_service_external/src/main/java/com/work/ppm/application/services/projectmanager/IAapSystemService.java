package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.projectmanager.AapSystemDto;
import com.work.ppm.application.model.projectmanager.AapSystem;
import com.work.ppm.application.model.projectmanager.ext.AapSystemInfo;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 系统管理
 */
public interface IAapSystemService {

    /**
     * 分页
     * @param aapSystem 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(AapSystem aapSystem, Pager pager) ;

    /**
     * 添加
     * @param aapSystem 对象
     * @return int
     */
     @Deprecated
    int addAapSystem(AapSystem aapSystem) ;

    /**
     * 查询详情
     * @param aapSystem 对象
     * @return AapSystem
     */
    AapSystemDto queryDetail(AapSystemDto aapSystem) ;

    /**
     * 更新
     * @param aapSystem 对象
     * @return Integer
     */
    @Deprecated
    Integer updateAapSystem(AapSystem aapSystem) ;

    /**
     * 删除
     * @param aapSystem 对象
     * @return Integer
     */
    @Deprecated
    Integer deleteAapSystem(AapSystem aapSystem) ;

    /**
     * 部门系统树
     * @param objectCode 编号
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> querySystemTree(String objectCode);

    /**
     * queryPasBySystems
     * @param systemids 编号
     * @return AapSystemInfo
     */
    AapSystemInfo queryPasBySystems(String systemids);

    /**
     * 根据名称查询系统信息
     * @param systemName 系统名称
     * @return AapSystem
     */
    AapSystemDto queryDetail(String systemName);

    /**
     * 获取负责人系统编号
     * @param userId 工号
     * @return 系统编号 多个逗号分开
     */
    String getFZRSysno(String userId);

    /**
     * 查询详情
     * @param sysId 系统id
     * @return AapSystem
     */
    List<AapSystemDto> queryDetailsForSysId(String sysId) ;

    /**
     * 根据系统编号查询系统信息
     * @param sysno
     * @return AapSystemDto
     */
    AapSystemDto queryPlansystem(String sysno);

    /**
     * 根据sysno查询总行系统
     * @param sysno
     * @return AapSystemDto
     */
    AapSystemDto querySystemIsZH(String sysno);
}
