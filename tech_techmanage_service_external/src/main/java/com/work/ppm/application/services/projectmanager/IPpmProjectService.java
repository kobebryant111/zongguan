package com.work.ppm.application.services.projectmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.projectmanager.PpmProjectDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmProjectService {

    /**
     * 分页
     * @param ppmProjectDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmProjectDto ppmProjectDto, Pager pager) ;

    /**
     * 查询详情
     * @param ppmProjectDto 对象
     * @return PpmProjectDto
     */
    PpmProjectDto queryDetail(PpmProjectDto ppmProjectDto) ;

    /**
     * 更新
     * @param ppmProjectDto  对象
     * @return int
     */
    int updatePpmProject(PpmProjectDto ppmProjectDto) ;

    /**
     * 获取项目信息组
     * @param ppmprojectno
     * @return List<PpmprojectDto>
     */
    List<PpmProjectDto> getPpmSubProjectView(String ppmprojectno, String planonline);

    /**
     * 根据userid更新项目
     * @param ppmProjectDto
     * @param userid
     */
    void updatePpmProjectByUserid(PpmProjectDto ppmProjectDto, String userid);

    /**
     * 根据project，planonline, onlineno获取项目列表信息
     * @param ppmProjectDto
     * @return List<PpmProjectDto>
     */
    List<PpmProjectDto> queryPpmProjectList(PpmProjectDto ppmProjectDto);

    /**
     * 根据项目编号获取项目信息
     * @param projectno
     * @return PpmProjectDto
     */
    List<PpmProjectDto> queryProjectListByProjectno(String projectno);
}
