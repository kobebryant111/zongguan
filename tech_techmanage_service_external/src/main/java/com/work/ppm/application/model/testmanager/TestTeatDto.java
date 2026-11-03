package com.work.ppm.application.model.testmanager;

import lombok.Data;

@Data
public class TestTeatDto {
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
    private String teamMansger;
    /**
     * 团队成员数
     */
    private int teamMemberNo;
    /**
     * 测试小组数
     */
    private int testGroupNo;
    /**
     * 创建人
     */
    private String teamCreateUser;
    /**
     * 创建时间
     */
    private String createTime;
}
