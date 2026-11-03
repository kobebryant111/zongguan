package com.work.ppm.application.services.testmanager;

import com.work.ppm.application.dto.testmanagement.PersonnerPortaitsDto;
import com.work.ppm.application.dto.testmanagement.PpmTeamGroup;
import com.work.ppm.application.dto.testmanagement.PpmTestGroupDto;
import com.work.ppm.application.dto.testmanagement.PpmTestGroupInfo;
import com.work.ppm.application.dto.testmanagement.PpmTestTeamDto;
import com.work.ppm.application.model.systemmanager.ext.CommEmployeeInfo;
import com.work.ppm.application.dto.testmanagement.PpmTestTeamInfo;
import com.work.ppm.application.model.testmanager.TestEmployeePool;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.application.model.testmanager.TestGroupExcel;
import com.work.ppm.application.model.testmanager.TestTeatDto;
import com.work.ppm.application.model.testmanager.TestTeatDtoExcel;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

public interface IPersonnerPortaitService {
    /**
     * 查询
     *
     * @param personnerPortaitsDto 对象
     * @param pager                分页
     */
    void queryPager(PersonnerPortaitsDto personnerPortaitsDto, Pager pager);

    /**
     * 下载
     *
     * @param personnerPortaitsDto 对象
     * @param pager                分页
     */
    void queryPagerInfo(PersonnerPortaitsDto personnerPortaitsDto, Pager pager);

    /**
     * 查询项目经理列表
     *
     * @return List<CommEmployeeInfo>
     */
    List<CommEmployeeInfo> selectTeamMansgerList();


    /**
     * 查询团队成员列表
     *
     * @return List<TestEmployeePool>
     */
    List<TestEmployeePool> selectTeamMemberList();

    /**
     * 查询团队成员列表
     *
     * @param ppmTestTeamDto 对象
     * @param userid         ID
     * @return ApiResult<List>
     */
    ApiResult<List> creatOfTeam(PpmTestTeamDto ppmTestTeamDto, String userid);


    /**
     * 查询项目经理列表
     *
     * @param ppmTestTeamInfo 对象
     * @param pager           分页
     */
    void teamNameListInfo(PpmTestTeamInfo ppmTestTeamInfo, Pager pager);

    /**
     * 查询团队成员做下拉框
     *
     * @return List<TestEmployeePool>
     */
    List<TestEmployeePool> teamMemberListinfo();

    /**
     * @param ppmTestTeamDto 编辑对象
     * @param userid         修改人
     */
    void edit(PpmTestTeamDto ppmTestTeamDto, String userid);

    /**
     * 修改团队成员
     * @param ppmTestTeamDto 对象
     * @param userid 修改人
     * @return List<TestEmployeePool>
     */
    List<TestEmployeePool> editTemMeb(PpmTestTeamDto ppmTestTeamDto, String userid);

    /**
     * 查询无团队的成员和当前团队所属成员
     * @param ppmTestTeamDto 对象
     * @return List<TestEmployeePool>
     */
    List<TestEmployeePool> queryTemMeb(PpmTestTeamDto ppmTestTeamDto);

    /**
     * 修改团队成员
     * @param ppmTestTeamDto 修改对象
     */
    void updateTemMeb(PpmTestTeamDto ppmTestTeamDto);

    /**
     * 删除测试团队
     * @param ppmTestTeamDto 删除对象条件
     */
    void deleteTeam(PpmTestTeamDto ppmTestTeamDto);

    /**
     * 查询测试团队详情
     * @param ppmTestTeamDto 查询对象条件
     */
    List<TestGroup> temDetail(PpmTestTeamDto ppmTestTeamDto);

    /**
     * 下载
     * @param ppmTestTeamInfo 查询对象
     * @param pager 分页
     * @return List<TestTeatDto>
     */
    List<TestTeatDtoExcel> excelInfo(PpmTestTeamInfo ppmTestTeamInfo, Pager pager);

    /**
     * 测试小组下拉框查询
     * @return List<TestGroup>
     */
    List<TestGroup> secltGroup();

    /**
     * 查询测试小组列表
     * @param ppmTeamGroup 查询对象
     * @param pager 分页
     */
    void queryPagerGrop(PpmTeamGroup ppmTeamGroup, Pager pager);

    /**
     * 下载
     * @param ppmTeamGroup 查询对象
     * @param pager 分页对象
     * @return List<TestTeatDtoExcel>
     */
    List<TestGroupExcel> secltGroupExcel(PpmTeamGroup ppmTeamGroup, Pager pager);

    /**
     * 删除测试小组
     * @param ppmTestGroupDto
     */
    void deleteGroupInfo(PpmTestGroupDto ppmTestGroupDto);

    /**
     * 测试小组成员下拉框
     * @param ppmTestGroupDto 对象
     * @return List<TestEmployeePool>
     */
    List<TestEmployeePool> groupDateInfo(PpmTestGroupDto ppmTestGroupDto);

    /**
     * 编辑测试小组确认
     * @param ppmTestGroupInfo 编辑条件对象
     */
    void updateTestGroup(PpmTestGroupInfo ppmTestGroupInfo);
}
