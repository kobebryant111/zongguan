package com.work.ppm.application.model.flowmanager.ext;

import com.work.ppm.application.model.flowmanager.PpmNewflowHistorylog;
import lombok.Data;

/**
 * 历史流程日志扩展
 */
@Data
public class PpmNewflowHistorylogInfo extends PpmNewflowHistorylog {
    /**
     * 用户名称
     */
    private String userName;
}
