package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.dto.othermanager.PpmProcessWorkdayDto;

/**
  * 处理工作日
  */
public interface IPpmProcessWorkdayService {


    /**
     * 查询详情
     * @param unworkdate 时间
     * @return PpmProcessWorkdayDto
     */
    PpmProcessWorkdayDto queryDetail(String unworkdate) ;


}
