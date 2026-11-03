package com.work.ppm.application.model.systemmanager.ext;

import com.work.ppm.application.model.systemmanager.PpmDaCenter;
import lombok.Data;

/**
 * 中心扩展
 */
@Data
public class PpmDaCenterInfo extends PpmDaCenter {
    /**
     * 部门id
     */
    private String depId;
    /**
     * 部门名称
     */
    private String depName;
}
