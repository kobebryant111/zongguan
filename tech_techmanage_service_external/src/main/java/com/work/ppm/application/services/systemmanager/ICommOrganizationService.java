package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.systemmanager.CommOrganizationDto;
import com.work.ppm.application.model.systemmanager.CommOrganization;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 机构管理
 */
public interface ICommOrganizationService {

    /**
     * 分页
     * @param commOrganization 对象
     * @param pager 分页对象
     */
    void queryPager(CommOrganization commOrganization, Pager pager) ;

    /**
     * 查询详情
     * @param commOrganization 对象
     * @return CommOrganization
     */
    CommOrganization queryDetail(CommOrganization commOrganization) ;

    /**
     * 更新
     * @param commOrganization 对象
     * @return Integer
     */
    @Deprecated
    Integer updateCommOrganization(CommOrganization commOrganization) ;

    /**
     * 删除
     * @param commOrganization 对象
     * @return Integer
     */
    @Deprecated
    Integer deleteCommOrganization(CommOrganization commOrganization) ;

    /**
     * 机构树
     * @return List<CommOrganizationDto>
     */
    List<CommOrganizationDto> getTree() ;

    /**
     * 机构树组件
     * 只查询机构树不包括其他
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryOrgTreeComponents();

    /**
     * 根据父编号查子机构
     * @param id 父级编号
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryOrgTree(String id);

    /**
     * 根据编号 返回 集合数
     * @param id id
     * @return int
     */
    int hasOrg(String id);

    /**
     * 根据机构名称查询信息
     * @param orgName 机构名称
     * @return CommOrganizationDto
     */
    CommOrganizationDto queryDateil(String orgName);

    /**
     * 根据机构名称查询信息
     * @param compid 机构id
     *
     * @return CommOrganizationDto
     */
    CommOrganizationDto queryDateil(long compid);

    /**
     * 根据CompCode 查询信息
     * @param compcode 结构编号
     * @return CommDepartmentDto
     */
    CommOrganizationDto queryDateilForCompCode(String compcode);

    /**
     * 根据用户id 查询分行id
     * @param userId 工号
     * @return CommOrganizationDto 分行编号
     */
    CommOrganizationDto queryFHIdforUserId(String userId);

    /**
     * 只查询分行的机构树
     * @return list
     */
    List<CommTreeDto> queryBranchThree();
}
