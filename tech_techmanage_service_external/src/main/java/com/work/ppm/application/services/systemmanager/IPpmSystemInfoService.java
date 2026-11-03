package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.PpmSystemInfo;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.systemmanager.PpmSystemInfoDto;

import java.util.List;

/**
  *  系统详情表 接口描述
  */
public interface IPpmSystemInfoService {

    /**
     * 分页
     * @param ppmSystemInfoDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmSystemInfoDto ppmSystemInfoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmSystemInfoDto 对象
     * @return int
     */
    int addPpmSystemInfo(PpmSystemInfoDto ppmSystemInfoDto) ;

    /**
     * 查询系统信息
     * @param ppmSystemInfoDto 对象
     * @return List
     */
    List<PpmSystemInfo> querySystem(PpmSystemInfoDto ppmSystemInfoDto);

    /**
     * 查找行员信息
     * @param ppmSystemInfoDto 对象
     * @return List
     */
    List<CommEmployee> findByPpmSystemInfo(PpmSystemInfoDto ppmSystemInfoDto);

    /**
     * 查询详情
     * @param ppmSystemInfoDto 对象
     * @return PpmSystemInfoDto
     */
    PpmSystemInfoDto queryDetail(PpmSystemInfoDto ppmSystemInfoDto) ;

    /**
     * 更新
     * @param ppmSystemInfoDto  对象
     * @return int
     */
    int updatePpmSystemInfo(PpmSystemInfoDto ppmSystemInfoDto) ;

    /**
     * 删除
     * @param ppmSystemInfoDto 对象
     * @return int
     */
    int deletePpmSystemInfo(PpmSystemInfoDto ppmSystemInfoDto) ;

    /**
     * 查询系统信息
     * @param ppmSystemInfoDto 对象
     */
    List<PpmSystemInfo> queryTransferList(PpmSystemInfoDto ppmSystemInfoDto);



    /**
     * 查询详情
     * @param name 系统名称
     * @param deptId 部门编号
     * @return PpmSystemInfoDto
     */
    PpmSystemInfoDto querySystemDetail(String name,Long deptId) ;

}
