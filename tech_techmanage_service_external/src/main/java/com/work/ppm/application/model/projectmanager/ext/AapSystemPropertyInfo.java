package com.work.ppm.application.model.projectmanager.ext;

import com.work.ppm.application.model.projectmanager.AapSystemProperty;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class AapSystemPropertyInfo extends AapSystemProperty {
    /**
     * 系统名称
     */
    private String sysName;
}
