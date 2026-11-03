package com.work.ppm.application.quartz.interfaces;

/**
 * 人力合同信息定时器接口
 */
public interface IPpmOutsourcingEtxRLJobService {
    /**
     * 每年一月一号生成人力合同
     */
    void createRlHt();
}
