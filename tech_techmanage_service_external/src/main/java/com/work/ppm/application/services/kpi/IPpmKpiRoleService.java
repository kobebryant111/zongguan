package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.JudgeKpiStatusDto;
import com.work.ppm.application.dto.kpi.PpmKpiRoleInfoDto;
import com.work.ppm.application.model.kpi.PpmKpiRole;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiRoleDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKpiRoleService {

    /**
     * 分页
     * @param ppmKpiRoleDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiRoleDto ppmKpiRoleDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiRoleDto 对象
     * @param userid 工号
     * @return int
     */
    int addPpmKpiRole(PpmKpiRoleDto ppmKpiRoleDto,String userid) ;

    /**
     * 查询详情
     * @param ppmKpiRoleDto 对象
     * @return PpmKpiRoleDto
     */
    @Deprecated
    PpmKpiRoleDto queryDetail(PpmKpiRoleDto ppmKpiRoleDto) ;

    /**
     * 更新
     * @param ppmKpiRoleDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmKpiRole(PpmKpiRoleDto ppmKpiRoleDto) ;

    /**
     * 删除
     * @param ppmKpiRoleDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKpiRole(PpmKpiRoleDto ppmKpiRoleDto) ;

    /**
     * insertPpmKpiRoles
     * @param ppmKpiRoleDto
     * @param userid
     * @return int
     */
    int insertPpmKpiRoles(PpmKpiRoleDto ppmKpiRoleDto,String userid) ;

    /**
     * updatePpmKpiRoles
     * @param ppmKpiRoleDtos
     * @param userid
     * @return int
     */
    int updatePpmKpiRoles(List<PpmKpiRoleDto> ppmKpiRoleDtos, String userid);

    /**
     * 查询考核角色数据
     * @param ppmKpiRoleInfoDto
     * @return List<PpmKpiRoleInfoDto>
     */
    List<PpmKpiRoleInfoDto> getPpmKpiRoleView(PpmKpiRoleInfoDto ppmKpiRoleInfoDto);


    /**
     * Excel导入
     * @param file 上传的excel
     * @return true:成功，false：失败
     */
    boolean importExcel( MultipartFile file,String userid);

    /**
     * 分页查询数据
     * @param ppmKpiRoleInfoDto
     * @param pager
     */
    void queryPagerList(PpmKpiRoleInfoDto ppmKpiRoleInfoDto, Pager pager);


    /**
     * 批量删除数据
     * @param ppmKpiRoleInfoDtos
     * @return int
     */
    int deletePpmKpiRoleList(List<PpmKpiRoleInfoDto> ppmKpiRoleInfoDtos) ;

    /**
     * updatePpmKpiRoleList
     * @param ppmKpiRoleInfoDto
     * @return int
     */
    int updatePpmKpiRoleList(PpmKpiRoleInfoDto ppmKpiRoleInfoDto);
    /**
     * 删除
     * @param ids id字符串 多个 ‘,’ 分开
     * @return int
     */
    int deletePpmKpiRoleList(String ids) ;

    /**
     * 更新数据
     * @param ppmKpiRoleDto
     * @param userid
     * @return int
     */
    int updatePpmKpiRoleDto(PpmKpiRoleDto ppmKpiRoleDto,String userid);

    /**
     * 多个数据同时更新
     * @param ppmKpiRoleDtos
     * @param userid
     * @return int
     */
    int updatePpmKpiRoleDtos(PpmKpiRoleDto ppmKpiRoleDtos, String userid);

    /**
     * 根据部门名称转为id
     * @param depname
     * @return String
     */
    String retDepid(String depname);

    /**
     * 根据中心名称查询中心id
     * @param centername
     * @return String
     */
    String retCenterid(String centername);

    /**
     * 返回权限
     * @param badge String
     * @return String
     */
    String retRole(String badge);

    /**
     * 同步给考核表权限数据
     * @param ppmKpiRoleDto
     * @return int
     */
    int updatePpmKpiPm(PpmKpiRoleDto ppmKpiRoleDto);


    /**
     *  根据badge获得数据
     * @param badge
     * @return PpmKpiRole
     */
     PpmKpiRole getPpmKpiRoleByBadge(String badge);


    /**
     * 获取角色信息包含名称数据
     * @param ppmKpiRoleInfoDto
     * @return PpmKpiRoleInfoDto
     */
    PpmKpiRoleInfoDto getPpmKpiRoleInfo(PpmKpiRoleInfoDto ppmKpiRoleInfoDto);

    /**
     * 根据工号查询角色审批信息
     * @param ppmKpiRoleDto
     * @return PpmKpiRoleDto
     */
    PpmKpiRoleDto getPpmKpiRoleDtoByBadge(PpmKpiRoleDto ppmKpiRoleDto);

    /**
     * 通过当前人和审批人判断考核所处阶段
     * @param dto
     * @return int
     */
    int judgeStatus(JudgeKpiStatusDto dto);

    /**
     * 查询分管部门下面的人员
     * @param ppmKpiRoleDto
     * @return List<PpmKpiRoleDto>
     */
    List<PpmKpiRoleDto> getPpmKpiRoleListByDirector(PpmKpiRoleDto ppmKpiRoleDto);
}
