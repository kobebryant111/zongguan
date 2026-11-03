package com.work.ppm.application.services.otherdeptmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.otherdeptmanager.PpmDevelopGroupInfoDto;

import java.util.List;

/**
  *  对接手机平台信息 接口描述
  */
public interface IPpmDevelopGroupInfoService {

    /**
     * 分页
     * @param ppmDevelopGroupInfoDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmDevelopGroupInfoDto 对象
     * @return int
     */
    int addPpmDevelopGroupInfo(PpmDevelopGroupInfoDto  ppmDevelopGroupInfoDto) ;

//    /**
//     * 查询详情
//     * @param ppmDevelopGroupInfoDto 对象
//     * @return PpmDevelopGroupInfoDto
//     */
//    PpmDevelopGroupInfoDto queryDetail(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) ;

    /**
     * 查询详情
     * @param ppmDevelopGroupInfoDto 对象
     * @return PpmDevelopGroupInfoDto
     */
    List<PpmDevelopGroupInfoDto> queryDetailList(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) ;

    /**
     * 更新
     * @param ppmDevelopGroupInfoDto  对象
     * @return int
     */
    int updatePpmDevelopGroupInfo(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) ;

    /**
     * 删除
     * @param ppmDevelopGroupInfoDto 对象
     * @return int
     */
    int deletePpmDevelopGroupInfo(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) ;

    /**
     * 导出
     * @param ppmDevelopGroupInfoDto 对象
     * @return int
     */
    List<PpmDevelopGroupInfoDto> exportPpmDevelopGroupInfo(PpmDevelopGroupInfoDto ppmDevelopGroupInfoDto) ;

}
