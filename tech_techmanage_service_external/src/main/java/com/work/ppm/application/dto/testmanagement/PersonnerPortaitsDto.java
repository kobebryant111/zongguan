package com.work.ppm.application.dto.testmanagement;

import lombok.Data;

@Data
public class PersonnerPortaitsDto {
    /**
     * 所属团队
     */
    private String textTeam;
    /**
     * 所属小组
     */
    private String textGroup;
    /**
     * 工号
     */
    private String badge;
    /**
     * 姓名
     */
    private String username;
    /**
     * 开始/结束日期
     */
    private String[] entryTime;
    /**
     * 供应商名称
     */
    private String outsourcname;
    /**
     * 是否包含自建项目
     */
    private boolean whetherIncludeSelfProject;
}
