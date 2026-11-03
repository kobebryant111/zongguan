package com.work.ppm.application.quartz.interfaces;

import java.io.IOException;
import java.text.ParseException;

/**
 * 同步数据服务接口
 */
public interface ISyncIntegInfoJobService {

    /**
     * 同步考勤信息到积分信息表
     */
    void syncKqInfoJob() throws IOException, ParseException;

}
