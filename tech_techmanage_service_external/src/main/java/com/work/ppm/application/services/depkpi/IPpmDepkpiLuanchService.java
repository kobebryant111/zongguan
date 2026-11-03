package com.work.ppm.application.services.depkpi;


import com.work.ppm.application.dto.depkpi.PpmDepkpiLaunchDto;

/**
 * 发起中心接口
 *
 */
public interface IPpmDepkpiLuanchService {


    /**
     * 发起中心
     *
     * @param ppmDepkpiLuanchDto 对象
     * @return int
     */
    int luanch(PpmDepkpiLaunchDto ppmDepkpiLuanchDto);
}
