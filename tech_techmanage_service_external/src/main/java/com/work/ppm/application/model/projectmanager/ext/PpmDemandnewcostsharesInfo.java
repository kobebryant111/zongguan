package com.work.ppm.application.model.projectmanager.ext;

import com.work.ppm.application.model.projectmanager.PpmDemandnewcostshares;
import lombok.Data;

/**
 * 分摊扩展
 */
@Data
public class PpmDemandnewcostsharesInfo extends PpmDemandnewcostshares {
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 确认人名称
     */
    private String managerUserName;
}

