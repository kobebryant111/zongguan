package com.work.ppm.application.quartz.interfaces;

import java.io.IOException;
import java.text.ParseException;
/**
 * 同步外包人员数据到测试人员池
 */
public interface ISyncTestEmployeePoolJobService {

    /**
     * 同步外包人员数据到测试人员池
     */
    void syncEmployeeToPool() throws IOException, ParseException;
}
