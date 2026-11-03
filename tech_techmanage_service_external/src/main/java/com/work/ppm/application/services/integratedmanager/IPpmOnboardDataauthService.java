package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmOnboardDataauthDto;

import java.util.List;

/**
  * 入职部门分配数据权限
  */
public interface IPpmOnboardDataauthService {

    /**
     * 添加
     * @param ppmOnboardDataauthDto 对象
     * @return int
     */

    int addPpmOnboardDataauth(PpmOnboardDataauthDto ppmOnboardDataauthDto) ;

    /**
     * 查询详情
     * @param ppmOnboardDataauthDto 对象
     * @return PpmOnboardDataauthDto
     */
    @Deprecated
    PpmOnboardDataauthDto queryDetail(PpmOnboardDataauthDto ppmOnboardDataauthDto) ;

    /**
     * 更新
     * @param ppmOnboardDataauthDto  对象
     * @return int
     */

    int updatePpmOnboardDataauth(List<PpmOnboardDataauthDto> ppmOnboardDataauthDto) ;


    /**
     * 根据用户id查看权限
     * @param userid  工号
     * @return list
     */
    List<PpmOnboardDataauthDto> checkedAuth(String userid);
}
