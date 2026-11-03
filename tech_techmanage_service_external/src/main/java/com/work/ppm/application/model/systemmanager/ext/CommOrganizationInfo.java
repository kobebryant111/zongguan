package com.work.ppm.application.model.systemmanager.ext;

import com.work.ppm.application.model.systemmanager.CommOrganization;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class CommOrganizationInfo extends CommOrganization {
    /**
     * 父类名称
     */
    private String parentName;
    /**
     * 机构名称
     */
    private String compName;
}
