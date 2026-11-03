package com.work.ppm.application.model.flowmanager.ext;

import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class PpmFlowLogInfo extends PpmFlowLog {
    /**
     * 名称
     */
    private String userName;
    /**
     * 历史类型 非历史，历史
     */
    private String hisType;
}
