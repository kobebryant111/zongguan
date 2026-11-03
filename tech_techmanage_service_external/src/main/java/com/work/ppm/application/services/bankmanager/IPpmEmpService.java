package com.work.ppm.application.services.bankmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;

/**
 * 外包人员基础接口
 *
 */
public interface IPpmEmpService {

    /**
     * getPermissionByUserid
     *
     * @param user
     * @return String
     */
    String getPermissionByUserid(CommOperatorDoLoginDto user);
}
