package com.work.ppm.application.model.processmanager.ext;

import com.work.ppm.application.model.processmanager.PpmNewBranchBankAcceptance;
import lombok.Data;

/**
 * {需求名称}
 *
 */
@Data
public class PpmNewBranchBankAcceptanceInfo extends PpmNewBranchBankAcceptance {
    /**
     部门名称
     */
    private String deptName;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 状态名称
     */
    private String stateName;
}
