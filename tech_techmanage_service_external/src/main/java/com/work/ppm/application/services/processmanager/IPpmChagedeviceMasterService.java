package com.work.ppm.application.services.processmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.processmanager.PpmChagedeviceMasterDto;

import java.util.List;

/**
  * 变更设备名称主表
  */
public interface IPpmChagedeviceMasterService {

    /**
     * 分页
     * @param ppmChagedeviceMasterDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmChagedeviceMasterDto ppmChagedeviceMasterDto, Pager pager) ;

    /**
     * 添加
     * @param ppmChagedeviceMasterDto 对象
     * @return int
     */
    int addPpmChagedeviceMaster(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) ;

    /**
     * 查询详情
     * @param ppmChagedeviceMasterDto 对象
     * @return PpmChagedeviceMasterDto
     */
    PpmChagedeviceMasterDto queryDetail(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) ;

    /**
     * 更新
     * @param ppmChagedeviceMasterDto  对象
     * @return int
     */
    int updatePpmChagedeviceMaster(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) ;

    /**
     * 删除
     * @param ppmChagedeviceMasterDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmChagedeviceMaster(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) ;

    /**
     * 获取设备变更名称
     * @return list
     */
    List<PpmChagedeviceMasterDto> queryList();
}
