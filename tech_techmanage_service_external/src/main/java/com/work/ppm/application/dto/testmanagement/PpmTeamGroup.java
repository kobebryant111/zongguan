package com.work.ppm.application.dto.testmanagement;

import lombok.Data;

@Data
public class PpmTeamGroup {
    /**
     * 测试小组
     */
    private String id;
    /**
     * 测试团队
     */
    private String ownedTestTeam;
    /**
     * 项目经理
     */
    private String teamMansger;
    /**
     * 测试组长
     */
    private String testGroupLeader;
    /**
     * 小组成员
     */
    private String testGroupMember;
}
