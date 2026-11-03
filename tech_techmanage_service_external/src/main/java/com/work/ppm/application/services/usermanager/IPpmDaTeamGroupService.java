package com.work.ppm.application.services.usermanager;

import com.work.ppm.application.dto.usermanager.PpmDaTeamGroupDto;
import com.work.ppm.application.model.usermanager.PpmDaTeamGroup;

import java.util.List;

/**
 * 小组接口
 */
public interface IPpmDaTeamGroupService {

    /**
     * 添加
     * @param ppmDaTeamGroup 对象
     * @return int
     */
     @Deprecated
    int addPpmDaTeamGroup(PpmDaTeamGroup ppmDaTeamGroup) ;

    /**
     * 查询详情
     * @param id id
     * @return PpmDaTeamGroup
     */
    PpmDaTeamGroup queryDetail(String id) ;

    /**
     * 更新
     * @param ppmDaTeamGroup 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmDaTeamGroup(PpmDaTeamGroup ppmDaTeamGroup) ;

    /**
     * 查询部门分组
     * @param deptNo 部门编号
     * @return List<PpmDaTeamGroupDto>
     */
    List<PpmDaTeamGroupDto> queryGroup(String deptNo);

    /**
     * 查询部门团队小组信息科技
     * @param ppmDaTeamGroup 部门小组信息
     * @return List<PpmDaTeamGroupDto>
     */
    List<PpmDaTeamGroupDto> queryGroups(PpmDaTeamGroupDto ppmDaTeamGroup);

    /**
     * 根据工号获取分管经理
     * @param userid
     * @return String
     */
    public String queryGroupManager(String userid);
}
