package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftTreeDto;
import com.work.ppm.application.model.projectmanager.PpmProjectBuildSoft;
import com.work.ppm.application.model.projectmanager.ext.PpmProjectBuildSoftInfo;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 项目管理
 */
public interface IPpmProjectBuildSoftService {

    /**
     * 分页
     * @param ppmProjectBuildSoft 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmProjectBuildSoft ppmProjectBuildSoft, Pager pager) ;

    /**
     * 添加
     * @param ppmProjectBuildSoft 对象
     * @return int
     */
     @Deprecated
    int addPpmProjectBuildSoft(PpmProjectBuildSoft ppmProjectBuildSoft) ;

    /**
     * 查询详情
     * @param ppmProjectBuildSoft 对象
     * @return PpmProjectBuildSoft
     */
    PpmProjectBuildSoft queryDetail(PpmProjectBuildSoft ppmProjectBuildSoft) ;

    /**
     * 更新
     * @param ppmProjectBuildSoft 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmProjectBuildSoft(PpmProjectBuildSoft ppmProjectBuildSoft) ;

    /**
     * 删除
     * @param ppmProjectBuildSoft 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmProjectBuildSoft(PpmProjectBuildSoft ppmProjectBuildSoft) ;

    /**
     * 所有上线的项目树
     * @return List<PpmProjectBuildSoftTreeDto>
     */
    List<PpmProjectBuildSoftTreeDto> queryProjectTree();

    /**
     * 系统负责人
     * @param projectno 项目编号
     * @return List<PpmProjectBuildSoftDto>
     */
    List<PpmProjectBuildSoftDto> queryMAS(String projectno);

    /**
     * 系统负责人
     * @param sysno 系统编号
     * @return List<PpmProjectBuildSoftDto>
     */
    List<PpmProjectBuildSoftDto> querySystemPrincipal(String sysno);

    /**
     * 根据项目编号 查询 项目属性
     * @param projectNo
     * @return string
     */
    String queryProjectNoForProattrbutes(String projectNo);


    /**
     * 根据需求获取项目信息
     *
     * @param projectNo
     * @return PpmProjectBuildSoftInfo
     */
    PpmProjectBuildSoftInfo getProjectBuildSoftByDenamdID(String projectNo);

    /**
     * 根据项目编号获取需求审核人bmfzr
     * @param projectno
     * @return String
     */
    String getBmfzrByProjectno(String projectno);

    /**
     * 根据上线编号查询项目信息
     * @param onlineno
     * @return PpmProjectBuildSoftDto
     */
    PpmProjectBuildSoftDto getProjectInfoByOnlineno(String onlineno);
}
