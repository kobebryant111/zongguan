package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.PpmKpiDepRoleInfoDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiDepRoleDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKpiDepRoleService {

    /**
     * 分页
     * @param ppmKpiDepRoleDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiDepRoleDto ppmKpiDepRoleDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiDepRoleDto 对象
     * @return int
     */
    int addPpmKpiDepRole(PpmKpiDepRoleDto ppmKpiDepRoleDto) ;

    /**
     * 查询详情
     * @param ppmKpiDepRoleDto 对象
     * @return PpmKpiDepRoleDto
     */
    @Deprecated
    PpmKpiDepRoleDto queryDetail(PpmKpiDepRoleDto ppmKpiDepRoleDto) ;

    /**
     * 更新
     * @param ppmKpiDepRoleDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmKpiDepRole(PpmKpiDepRoleDto ppmKpiDepRoleDto) ;

    /**
     * 删除
     * @param ppmKpiDepRoleDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKpiDepRole(PpmKpiDepRoleDto ppmKpiDepRoleDto) ;

    /**
     * 插入部门中心角色数据
     * @param ppmKpiDepRoleDto 对象
     * @param userid 对象
     * @return int
     */
    int insertPpmKpiDepRoles(PpmKpiDepRoleDto ppmKpiDepRoleDto,String userid);

    /**
     * 查询列表数据
     * @param ppmKpiDepRoleInfoDto 对象
     * @return List<PpmKpiDepRoleInfoDto>
     */
    List<PpmKpiDepRoleInfoDto> getPpmKpiDepRoleView(PpmKpiDepRoleInfoDto ppmKpiDepRoleInfoDto);

    /**
     * 查询页面page数据
     * @param ppmKpiDepRoleInfoDto 对象
     * @param pager 对象
     */
    void queryPagerList(PpmKpiDepRoleInfoDto ppmKpiDepRoleInfoDto, Pager pager);

    /**
     * 批量删除
     * @param ids 对象
     * @return int
     */
    int deletePpmKpiDepRoleList(String ids);

    /**
     * 导入文件
     * @param file 对象
     * @param userid 对象
     * @return boolean
     */
    boolean importExcel(MultipartFile file, String userid);

    /**
     * 多条数据同时修改
     * @param ppmKpiDepRoleDtos 对象
     * @param userid 对象
     * @return int
     */
    int updatePpmKpiDepRoleDtos(PpmKpiDepRoleDto ppmKpiDepRoleDtos, String userid);

    /**
     * 页面更新数据
     * @param ppmKpiDepRoleDto 对象
     * @param userid 对象
     * @return int
     */
    int updatePpmKpiDepRoleDto(PpmKpiDepRoleDto ppmKpiDepRoleDto,String userid);

    /**
     * 根据条件查询数据
     * @param ppmKpiDepRoleDto 对象
     * @return List<PpmKpiDepRoleDto>
     */
    List<PpmKpiDepRoleDto> getPpmKpiDepRoleList(PpmKpiDepRoleDto ppmKpiDepRoleDto);

}
