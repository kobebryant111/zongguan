package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.model.integratedmanager.PpmRlwbhtInfo;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmRlwbhtInfoDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmRlwbhtInfoService {

    /**
     * 分页
     * @param ppmRlwbhtInfoDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmRlwbhtInfoDto ppmRlwbhtInfoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmRlwbhtInfoDto 对象
     * @return int
     */
     @Deprecated
    int addPpmRlwbhtInfo(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) ;

    /**
     * 查询详情
     * @param ppmRlwbhtInfoDto 对象
     * @return PpmRlwbhtInfoDto
     */
    @Deprecated
    PpmRlwbhtInfoDto queryDetail(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) ;

    /**
     * 更新
     * @param ppmRlwbhtInfoDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmRlwbhtInfo(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) ;

    /**
     * 删除
     * @param ppmRlwbhtInfoDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmRlwbhtInfo(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) ;

    /**
     * 查询或者插入外包人力合同
     * @param ppmRlwbhtInfoDto 对象
     * @return  List<PpmRlwbhtInfoDto>
     */
    List<PpmRlwbhtInfoDto> queryOrInsertPpmRlwbhtInfo(PpmRlwbhtInfoDto ppmRlwbhtInfoDto);

    /**
     * 查询外包人力合同
     * @param ppmRlwbhtInfo
     * @return PpmRlwbhtInfo
     */
    PpmRlwbhtInfo queryDetail(PpmRlwbhtInfo ppmRlwbhtInfo);

}
