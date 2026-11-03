package com.work.ppm.application.services.systemmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.systemmanager.PpmSystemUserInfoDto;

/**
  *  系统详情用户关系表 接口描述
  */
public interface IPpmSystemUserInfoService {

    /**
     * 分页
     * @param ppmSystemUserInfoDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmSystemUserInfoDto ppmSystemUserInfoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmSystemUserInfoDto 对象
     * @return int
     */
    int addPpmSystemUserInfo(PpmSystemUserInfoDto ppmSystemUserInfoDto) ;

    /**
     * 查询详情
     * @param ppmSystemUserInfoDto 对象
     * @return PpmSystemUserInfoDto
     */
    PpmSystemUserInfoDto queryDetail(PpmSystemUserInfoDto ppmSystemUserInfoDto) ;

    /**
     * 更新
     * @param ppmSystemUserInfoDto  对象
     * @return int
     */
    int updatePpmSystemUserInfo(PpmSystemUserInfoDto ppmSystemUserInfoDto) ;

    /**
     * 删除
     * @param ppmSystemUserInfoDto 对象
     * @return int
     */
    int deletePpmSystemUserInfo(PpmSystemUserInfoDto ppmSystemUserInfoDto) ;
}
