package com.work.ppm.application.dto.testmanagement;

import lombok.Data;

@Data
public class PpmTestTeamInfo {
    /**
     * 所属团队:
     */
    private String teamId;
    /**
     * 团队项目经理
     */
    private String badge;
    /**
     * 团队成员
     */
    private String testEmpBadge;
}
