package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.application.dto.onlinemanager.PpmProjectNewonlineDto;

/**
 * 项目上线接口类（扩展类）
 */
public interface IPpmProjectNewonline1Service {

    /**
     * 根据上线编号查询上线流程
     * @param onlineno
     * @return PpmProjectNewonlineDto
     */
    PpmProjectNewonlineDto queryDetailByOnlineno(String onlineno);
}
