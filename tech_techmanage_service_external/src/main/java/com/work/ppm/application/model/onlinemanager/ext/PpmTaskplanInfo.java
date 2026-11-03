package com.work.ppm.application.model.onlinemanager.ext;

import com.work.ppm.application.model.onlinemanager.PpmTaskplan;
import lombok.Data;

/**
 * 计划上线窗口和版本扩展类
 */
@Data
public class PpmTaskplanInfo extends PpmTaskplan {
    /**
     * 系统名称
     */
    private String sysname;
    /**
     * 上线用户名称
     */
    private String planusername;
    /**
     * 创建用户名称
     */
    private String createusername;
}
