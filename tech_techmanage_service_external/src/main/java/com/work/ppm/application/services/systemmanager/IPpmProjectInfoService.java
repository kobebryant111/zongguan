package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.PpmProjectInfo;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.systemmanager.PpmProjectInfoDto;

import java.util.List;

/**
  *  项目详情表 接口描述
  */
public interface IPpmProjectInfoService {

    /**
     * 分页
     * @param ppmProjectInfoDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmProjectInfoDto ppmProjectInfoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmProjectInfoDto 对象
     * @return int
     */
    int addPpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto) ;

    /**
     * 保存
     * @param ppmProjectInfoDto 对象
     * @param loginDto 对象
     * @return int
     */
    int savePpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto ,CommOperatorDoLoginDto loginDto);

    /**
     * 查询项目信息
     * @param ppmProjectInfoDto 对象
     * @return list
     */
    List<PpmProjectInfo> queryProject(PpmProjectInfoDto ppmProjectInfoDto);

    /**
     * 查询项目信息 带测试项目经理
     * @param ppmProjectInfoDto 对象
     * @return list
     */
    List<PpmProjectInfo> queryNewProject(PpmProjectInfoDto ppmProjectInfoDto);
    /**
     * 查询项目信息
     * @param ppmProjectInfoDto 对象
     * @return list
     */
    List<PpmProjectInfo> queryApplyProject(PpmProjectInfoDto ppmProjectInfoDto) ;
    /**
     * 查找行员信息
     * @param ppmProjectInfoDto
     * @return
     */
    List<CommEmployee> findByPpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto);

    /**
     * 查询详情
     * @param ppmProjectInfoDto 对象
     * @return PpmProjectInfoDto
     */
    PpmProjectInfoDto queryDetail(PpmProjectInfoDto ppmProjectInfoDto) ;

    /**
     * 查询详情集合
     * @param ppmProjectInfoDto 对象
     * @return PpmProjectInfoDto
     */
    List<PpmProjectInfoDto> queryDetailList(PpmProjectInfoDto ppmProjectInfoDto,String uniqueName);

    /**
     * 更新
     * @param ppmProjectInfoDto  对象
     * @return int
     */
    int updatePpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto) ;

    /**
     * 删除
     * @param ppmProjectInfoDto 对象
     * @return int
     */
    int deletePpmProjectInfo(PpmProjectInfoDto ppmProjectInfoDto) ;

    /**
     * 查询详情
     * @param name 系统名称
     * @param deptId 部门编号
     * @return PpmProjectInfoDto
     */
    PpmProjectInfoDto queryProjectDetail(String name, Long deptId,String projectNumber) ;

    /**
     * 查询项目信息
     * @param ppmProjectInfoDto 对象
     */
    List<PpmProjectInfoDto> queryTransferList(PpmProjectInfoDto ppmProjectInfoDto);

}
