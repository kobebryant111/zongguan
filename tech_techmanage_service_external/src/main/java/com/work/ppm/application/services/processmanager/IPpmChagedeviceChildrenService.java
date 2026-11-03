package com.work.ppm.application.services.processmanager;

import com.work.ppm.application.dto.processmanager.PpmChagedeviceChildrenDto;

import java.util.List;

/**
  * 变更设备名称子表
  */
public interface IPpmChagedeviceChildrenService {

    /**
     * 添加
     * @param ppmChagedeviceChildrenDto 对象
     * @return int
     */
     @Deprecated
    int addPpmChagedeviceChildren(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) ;

    /**
     * 查询详情
     * @param ppmChagedeviceChildrenDto 对象
     * @return list
     */
    List<PpmChagedeviceChildrenDto> queryList(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) ;
    /**
     * 查询所有集合
     * @return list
     */
    List<PpmChagedeviceChildrenDto> queryListAll() ;

    /**
     * 更新
     * @param ppmChagedeviceChildrenDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmChagedeviceChildren(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) ;

    /**
     * 删除
     * @param ppmChagedeviceChildrenDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmChagedeviceChildren(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) ;
}
