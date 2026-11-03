package com.work.ppm.application.quartz.interfaces;

import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;

import java.util.List;

/**
 * 外包人员考勤信息计算
 */
public interface IPpmKqRecordCalculateJobService {
    /**
     * 外包人员考勤信息计算
     */
    void recordCalaulate() ;

    /**
     * 自动处理异常考勤数据
     */
    void autoRecordHandleCalaulate() ;

    /**
     * 加班时长计算
     */
    void overtimeCalaulate() ;

    /**
     * 消息推送
     */
    void sendMessage() ;

    /**
     * 刷新异常考勤
     * @param ppmKqRecordDtos
     * @return
     */
    int cipherRecordList(List<PpmKqRecordDto> ppmKqRecordDtos);
}
