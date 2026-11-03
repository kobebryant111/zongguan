package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmPwdHqmodelDto;

/**
 * 密管密码获取模式
 */
public interface IPpmPwdHqmodelService {
    /**
     * 查询详情
     * @param ppmPwdHqmodelDto 对象
     * @return PpmPwdHqmodelDto
     */
    PpmPwdHqmodelDto queryDetail(PpmPwdHqmodelDto ppmPwdHqmodelDto) ;


}
