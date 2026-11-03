package com.work.ppm.application.dto.testmanagement;

import lombok.Data;

import java.util.List;

@Data
public class PpmTestGroupInfo {
    /**
     * 小组id
     */
    private String id;
    /**
     * 测试小组名称
     */
    private String testGroupName;
    /**
     * 所属测试团队
     */
    private String teamName;
    /**
     * 测试团队ID
     */
    private String ownedTestTeam;
    /**
     * 管理人员
     */
    private String testGroupComemployee;
    /**
     * 小组组长id
     */
    private String testGroupLeader;
    /**
     * 项目经理
     */
    private List<String> teamMansger;
    /**
     * 小组组长
     */
    private String testEmpName;
    /**
     * 小组成员数
     */
    private int groupMemberNo;
    /**
     * 小组成员
     */
    private String groupMember;
    /**
     * 小组成员Id
     */
    private List<String> paramGroup;
}
