package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmOnboardDistribution;
import lombok.Data;

/**
 * 入职部门分配子表
 */
@Data
public class PpmOnboardDistributionInfo extends PpmOnboardDistribution {
    /**
     * 性别
     */
    private String sexName;
    /**
     * 中心名称
     */
    private String centerName;

    /**
     * 方向名称
     */
    private String directionName;
    /**
     * 部门名称
     */
    private String depName;
    /**
     *	简历id（唯一）多个用“,” 分割
     **/
    private String resumeIds;

}
