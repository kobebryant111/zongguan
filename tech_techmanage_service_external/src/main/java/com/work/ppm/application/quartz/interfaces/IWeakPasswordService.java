package com.work.ppm.application.quartz.interfaces;

import java.io.IOException;

public interface IWeakPasswordService {
    /**
     * 同步弱口令服务接口
     */
    void syncWeakPasswordJob() throws IOException;
}
