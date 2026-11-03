package com.work.ppm.application.dto.testmanagement;

import lombok.Data;

import java.util.List;

@Data
public class PpmTestTeamDto {
    /**
     * 团队ID
     */
    private String teamId;
    /**
     * 团队名称
     */
    private String teamName;
    /**
     * 团队项目经理
     */
    private List<String> teamMansger;
    /**
     * 团队成员
     */
    private List<String> teamMember;
    /**
     * 创建人
     */
    private String teamCreateUser;
    /**
     * 创建时间
     */
    private String createTime;

}
