package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmFhspbmpzDto;

import java.util.List;

/**
 * 分行审批部门配置
 */
public interface IPpmFhspbmpzService {

    /**
     * 查询集合
     * @param ppmFhspbmpzDto 对象
     * @return list
     */
    List<PpmFhspbmpzDto> queryList(PpmFhspbmpzDto ppmFhspbmpzDto) ;

    /**
     * 添加
     * @param ppmFhspbmpzDto 对象
     * @return int
     */
    int addPpmFhspbmpz(PpmFhspbmpzDto ppmFhspbmpzDto) ;

    /**
     * 查询详情
     * @param deptno 部门编号
     * @return PpmFhspbmpzDto
     */
    PpmFhspbmpzDto queryDetail(String  deptno) ;

    /**
     * 更新
     * @param ppmFhspbmpzDto  对象
     * @return int
     */
    int updatePpmFhspbmpz(PpmFhspbmpzDto ppmFhspbmpzDto) ;

    /**
     * 删除
     * @param ppmFhspbmpzDto 对象
     * @return int
     */
    int deletePpmFhspbmpz(PpmFhspbmpzDto ppmFhspbmpzDto) ;
}
