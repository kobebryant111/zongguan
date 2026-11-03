package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmDemandQueueDto;

/**
 * 奋进号流程队列
 */
public interface IPpmDemandQueueService {

    /**
     * 添加
     * @param ppmDemandQueueDto 对象
     * @return int
     */
    int addPpmDemandQueue(PpmDemandQueueDto ppmDemandQueueDto) ;



}
