package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmProductionDto;

/**
  * 项目投产表
  */
public interface IPpmProductionService {
    /**
     * 检查是否上线
     * @param ppmProductionDto 对象
     * @return true：已经上线，false：为上线
     */
    boolean checkedIsOnline(PpmProductionDto ppmProductionDto);
}
