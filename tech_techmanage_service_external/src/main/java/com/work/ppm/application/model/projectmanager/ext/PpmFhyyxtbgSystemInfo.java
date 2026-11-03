package com.work.ppm.application.model.projectmanager.ext;

import com.work.ppm.application.model.projectmanager.PpmFhyyxtbgSystem;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class PpmFhyyxtbgSystemInfo extends PpmFhyyxtbgSystem {
    /**
     * 系统名称
     */
    private String sysName;
    /**
     * 系统编号
     */
    private String systemId;
    /**
     * 负责人名称
     */
    private String principalaName;
    /**
     * 负责人编号
     */
    private String principala;
    /**
     * 变更状态
     */
    private String ischange;
}
