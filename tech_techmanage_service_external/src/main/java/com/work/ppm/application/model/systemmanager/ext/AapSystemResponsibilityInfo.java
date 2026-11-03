package com.work.ppm.application.model.systemmanager.ext;

import com.work.ppm.application.model.systemmanager.AapSystemResponsibility;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class AapSystemResponsibilityInfo extends AapSystemResponsibility {
    /**
     * 系统名称
     */
    private String sysName;
    /**
     * 系统编号
     */
    private String systemId;
    /**
     * 变更状态
     */
    private String changeStatus;
}
