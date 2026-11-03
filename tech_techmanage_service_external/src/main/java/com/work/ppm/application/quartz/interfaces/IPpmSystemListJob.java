package com.work.ppm.application.quartz.interfaces;

import java.io.IOException;

public interface IPpmSystemListJob {
    /**
     * 同步系统列表
     */
    void syncSystemListJob() throws IOException;

    /**
     * 更新密钥key
     */
    void syncRenewKeyJob() throws Exception;
}
